package com.rcallum.CalCore.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CommandRegister {

    private Set<CCommand> commandSet;
    private JavaPlugin plugin;

    public CommandRegister(JavaPlugin pl) {
        commandSet = new HashSet<>();
        plugin = pl;
    }

    public void addCommand(CCommand cmd) {
        commandSet.add(cmd);
    }

    private void register(CCommand cmd) {
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
    }

    private void executeCommand(CommandSender sender, String[] args, CCommand cmd) {
        if (!sender.hasPermission(cmd.getPermission())) return;
        Map<String, Object> arguments = new HashMap<>();
        cmd.getExecute().accept(new WrappedCommand(sender, arguments));
    }
}
