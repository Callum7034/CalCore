package com.rcallum.CalCore.Commands;

import com.rcallum.CalCore.Commands.Arguments.CommandArgument;
import com.rcallum.CalCore.Utils.Colour;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.SimplePluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public class CommandRegister {

    private Map<String, CCommand> registeredCommands;
    private JavaPlugin plugin;
    private CommandMap commandMap;
    private CommandDefaultMessages language;

    public CommandRegister(JavaPlugin pl) {
        registeredCommands = new HashMap<>();
        plugin = pl;
        language = new CommandDefaultMessages();
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
                if (args.length == 2) {
                    completion.addAll(getSubCommandsFor(cmd.getSubCommands().get(args[0]), sender));
                }

                return completion;
            }
        };

        addCommand(cmd);
        commandMap.register(plugin.getName(), bukkitCommand);
        cmd.setBukkitCommand(bukkitCommand);

    }

    private void executeCommand(CommandSender sender, String[] args, CCommand cmd) {
        // Checks to see if the sender must be a player
        if (cmd.isRequirePlayer()) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(Colour.c(language.playerOnly));
                return;
            }
        }
        String[] argumentsToFill = args.clone();
        List<CommandArgument> requiredArguments = new ArrayList<>(cmd.getArgumentMap().values());
        int optionalArguments = (int) requiredArguments.stream()
                .filter(CommandArgument::isOptional)
                .collect(Collectors.toList())
                .size();
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

        // Check to see if the sender has the correct permission to run this command
        if (!sender.hasPermission(cmd.getPermission())) {
            sender.sendMessage(Colour.c(language.noPermission));
            return;
        }

        // Check for any arguments
        if (argumentsToFill.length < (requiredArguments.size() - optionalArguments)) {
            sender.sendMessage(Colour.c(language.incorrectArgumentAmount));
            sender.sendMessage(Colour.c(language.getCorrectUsage(cmd)));
            return;
        }

        for (CommandArgument arg : requiredArguments) {
            if (argumentsToFill.length == 0) {
                if (arg.isOptional()) {
                    continue;
                }
            }

            Object argObject = arg.getMapper().parse(argumentsToFill[0]);
            if (argObject == null) {
                sender.sendMessage(Colour.c(language.incorrectArgumentProvided));
                sender.sendMessage(Colour.c(language.getCorrectUsage(cmd)));
                return;
            }
            arguments.put(arg.getIdentity(), argObject);
            argumentsToFill = cutArray(argumentsToFill, 1);
        }

        // Argument requirements met!
        // Check if command has executable code
        if (cmd.getExecute() != null) {
            cmd.getExecute().accept(new WrappedCommand(sender, arguments));
        } else {
            sender.sendMessage(Colour.c(language.getCorrectUsage(cmd)));
            return;
        }




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

    public CommandDefaultMessages getLanguage() {
        return language;
    }
}
