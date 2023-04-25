package com.rcallum.CalCore.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.SimplePluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CommandRegister {

    private Map<String, CCommand> registeredCommands = new ConcurrentHashMap<>();
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
        };

        addCommand(cmd);
        commandMap.register(plugin.getName(), bukkitCommand);
        cmd.setBukkitCommand(bukkitCommand);

    }

    private void executeCommand(CommandSender sender, String[] args, CCommand cmd) {
        if (!sender.hasPermission(cmd.getPermission())) return;
        cmd.getExecute().accept(new WrappedCommand(sender, args));
    }
}