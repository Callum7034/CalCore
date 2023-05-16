package com.rcallum.CalCore.Commands.Arguments.Types;

import com.rcallum.CalCore.Commands.Arguments.CommandArgument;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.function.Supplier;
import java.util.stream.Collectors;

public class ArgPlayer extends CommandArgument<Player> {
    public ArgPlayer() {
        setIdentity("player");
        setMapper((input) -> {
            try {
                return Bukkit.getPlayer(input);
            } catch (Exception failed) {
                return null;
            }
        });
        setTabCompletion(input -> {
            return Bukkit.getServer().getOnlinePlayers().stream().map(player ->
                    player.getName()).collect(Collectors.toList());
        });
    }
}
