package com.rcallum.CalCore.Commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.Optional;

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

    public <T> Optional<T> getArg(String arg) {
        return Optional.ofNullable((T) arguments.get(arg));
    }

    public <T> Optional<T> getArg(String arg, Class<T> type) {
        return Optional.ofNullable((T) arguments.get(arg));
    }

    public <T> T getArgAsReq(String arg) {
        return (T) arguments.get(arg);
    }

    public <T> T getArgAsReq(String arg, Class<T> type) {
        return type.cast(arguments.get(arg));
    }

    public void sendMessage(String message, Map<String, String> placeholders) {
        msg = message;
        placeholders.forEach((s, s2) -> {
            msg = msg.replaceAll(s, s2);
        });
        sender.sendMessage(message);
    }
}