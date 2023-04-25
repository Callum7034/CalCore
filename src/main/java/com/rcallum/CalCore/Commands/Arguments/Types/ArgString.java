package com.rcallum.CalCore.Commands.Arguments.Types;

import com.rcallum.CalCore.Commands.Arguments.CommandArgument;

public class ArgString extends CommandArgument<String> {
    public ArgString() {
        setIdentity("string");
        setMapper((input)-> input);
    }
}
