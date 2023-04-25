package com.rcallum.CalCore.Commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;

public class WrappedCommand {

    private CommandSender sender;
    private Map<String, Object> arguments;
    private String msg;

    public WrappedCommand(CommandSender sender, Map<String, Object> arguments) {
        this.sender = sender;
        this.arguments = arguments;
    }

    public Player getSenderAsPlayer() {
        return (Player) sender;
    }

    public CommandSender getSender() {
        return sender;
    }

    public Map<String, Object> getArguments() {
        return arguments;
    }

    public void sendMessage(String message, Map<String, String> placeholders) {
        msg = message;
        placeholders.forEach((s, s2) -> {
            msg = msg.replaceAll(s, s2);
        });
        sender.sendMessage(msg);
    }

    public <T> T getArgAsReq(String arg, Class<T> type) {
        return type.cast(arguments.get(arg));
    }
}