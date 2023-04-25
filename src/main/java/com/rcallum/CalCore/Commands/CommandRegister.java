package com.rcallum.CalCore.Commands;

import com.rcallum.CalCore.Commands.Arguments.CommandArgument;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.SimplePluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class CommandRegister {

    private Map<String, CCommand> registeredCommands;
    private JavaPlugin plugin;
    private CommandMap commandMap;

    public CommandRegister(JavaPlugin pl) {
        registeredCommands = new HashMap<>();
        plugin = pl;

        try {

            Field cMap = SimplePluginManager.class.getDeclaredField("commandMap");
            cMap.setAccessible(true);
            commandMap = (CommandMap) cMap.get(Bukkit.getPluginManager());

        } catch (Throwable threw) {
            throw new IllegalStateException("Failed to initialize CommandMap", threw);
        }
    }

    private void addCommand(CCommand cmd) {
        registeredCommands.put(cmd.getCmd(), cmd);
    }

    public void register(CCommand cmd) {
        Command bukkitCommand = new WrappedBukkitCommand(cmd.getCmd(), cmd.getDescription(), cmd.getUseMessage(), cmd.getAliases()) {
            @Override
            public boolean execute(CommandSender sender, String commandLabel, String[] args) {
                try {
                    executeCommand(sender, args, cmd);
                } catch (Throwable t) {
                    t.printStackTrace();
                }
                return false;
            }
            @Override
            public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
                List<String> completion = new ArrayList<>();

                if (cmd.getSubCommands().isEmpty()) return completion;

                if (args.length == 1) {
                    completion.addAll(getSubCommandsFor(cmd, sender));
                }

                return completion;
            }
        };

        addCommand(cmd);
        commandMap.register(plugin.getName(), bukkitCommand);
        cmd.setBukkitCommand(bukkitCommand);

    }

    private void executeCommand(CommandSender sender, String[] args, CCommand cmd) {
        if (!sender.hasPermission(cmd.getPermission())) {
            // TODO: Send no permission
            sender.sendMessage("No permission");
            return;
        }

        String[] argumentsToFill = args.clone();
        List<CommandArgument> requiredArguments = new ArrayList<>(cmd.getArgumentMap().values());
        Map<String, Object> arguments = new HashMap<>();
        Map<String, CCommand> subCommands = cmd.getSubCommands();

        if (args.length == 0 && requiredArguments.size() == 0 && subCommands.size() == 0) {
            cmd.getExecute().accept(new WrappedCommand(sender, new HashMap<>()));
            return;
        }

        // Check for subcommands
        for (String subCmd : subCommands.keySet()) {
            if (argumentsToFill.length >= 1) {
                if(argumentsToFill[0].equalsIgnoreCase(subCmd)) {
                    executeCommand(sender, cutArray(argumentsToFill, 1), subCommands.get(subCmd));
                    return;
                }
            }
        }

        // Check for any arguments
        if (argumentsToFill.length != requiredArguments.size()) {
            // TODO: Send incorrect usage
            sender.sendMessage("Wrong amount of arguments");
            return;
        }

        for (CommandArgument arg : requiredArguments) {
            Object argObject = arg.getMapper().parse(argumentsToFill[0]);
            if (argObject == null) {
                // TODO: Send mapping failed (incorrect usage)
                sender.sendMessage("The arguments you supplied were incorrect");
                return;
            }
            arguments.put(arg.getIdentity(), argObject);
            argumentsToFill = cutArray(argumentsToFill, 1);
        }

        // Argument requirements met!
        cmd.getExecute().accept(new WrappedCommand(sender, arguments));



    }
    private String[] cutArray(String[] array, int amount) {
        if (array.length <= amount)
            return new String[0];
        else
            return Arrays.copyOfRange(array, amount, array.length);
    }

    private Collection<String> getSubCommandsFor(CCommand command, CommandSender sender) {
        return command.getSubCommands().values()
                .stream()
                .filter(subCommand -> !subCommand.isSecret())
                .filter(subCommand -> subCommand.getPermission() == null || sender.hasPermission(subCommand.getPermission()))
                .map(CCommand::getCmd)
                .collect(Collectors.toList());
    }
}
