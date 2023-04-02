package com.rcallum.CalCore.Commands;

public class TestCommand extends CCommand{
    public TestCommand() {
        setCmd("Test");
        setPermission("Hello");
        onCommand( cmd -> cmd.getSenderAsPlayer().sendMessage("The command has worked"));
    }
}
