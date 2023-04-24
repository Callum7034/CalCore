package com.rcallum.calcore.Commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;

public class WrappedCommand {

    private CommandSender sender;
    private String[] arguments;
    private String msg;

    public WrappedCommand(CommandSender sender, String[] arguments) {
        this.sender = sender;
        this.arguments = arguments;
    }

    public Player getSenderAsPlayer() {
        return (Player) sender;
    }

    public CommandSender getSender() {
        return sender;
    }

    public String[] getArguments() {
        return arguments;
    }

    public void sendMessage(String message, Map<String, String> placeholders) {
        msg = message;
        placeholders.forEach((s, s2) -> {
            msg = msg.replaceAll(s, s2);
        });
        sender.sendMessage(msg);
    }
}