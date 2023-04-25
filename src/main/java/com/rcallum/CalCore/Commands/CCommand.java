package com.rcallum.CalCore.Commands;


import org.bukkit.command.Command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class CCommand {

    private String cmd;
    private String permission;
    private Consumer<WrappedCommand> execute;
    private String description = "";
    private List<String> aliases = new ArrayList<>();
    private String useMessage = "none";
    private Command bukkitCommand;
    private Map<String, CCommand> subCommands = new HashMap<>();

    public CCommand() {
    }

    public CCommand setCmd(String command) {
        cmd = command;
        return this;
    }

    public CCommand setPermission(String permission) {
        this.permission = permission;
        return this;
    }

    public CCommand onCommand(Consumer<WrappedCommand> consumer) {
        this.execute = consumer;
        return this;
    }

    public CCommand setDescription(String desc) {
        this.description = desc;
        return this;
    }

    public CCommand addAlias(String alias) {
        this.aliases.add(alias);
        return this;
    }

    public CCommand setUseMessage(String message) {
        this.useMessage = message;
        return this;
    }

    public CCommand addSubCommand(CCommand cmd) {
        subCommands.put(cmd.getCmd(), cmd);
        return this;
    }

    public String getCmd() {
        return cmd;
    }

    public String getPermission() {
        return permission;
    }

    public Consumer<WrappedCommand> getExecute() {
        return execute;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getAliases() {
        return aliases;
    }

    public String getUseMessage() {
        return useMessage;
    }

    public Command getBukkitCommand() {
        return bukkitCommand;
    }

    public void setBukkitCommand(Command bukkitCommand) {
        this.bukkitCommand = bukkitCommand;
    }

    public Map<String, CCommand> getSubCommands() {
        return subCommands;
    }

}
