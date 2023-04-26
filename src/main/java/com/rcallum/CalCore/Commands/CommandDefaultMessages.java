package com.rcallum.CalCore.Commands;

import com.rcallum.CalCore.Utils.Colour;

public class CommandDefaultMessages {

    public String noPermission = "&4You do not have permission to use this command";
    public String incorrectArgumentAmount = "&4You haven't provided enough arguments";
    public String incorrectArgumentProvided = "&4The arguments you provided were incorrect";
    public String correctUsage = "&aCorrect usage: %correct_usage%";
    public String playerOnly = "&4You must be a player to use this command";

    public String getCorrectUsage(CCommand command) {
        String toReturn = correctUsage;
        toReturn = toReturn.replaceAll("%correct_usage%", command.getCorrectUsage());
        toReturn = Colour.c(toReturn);
        return toReturn;
    }
}
