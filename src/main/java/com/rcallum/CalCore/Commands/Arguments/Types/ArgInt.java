package com.rcallum.CalCore.Commands.Arguments.Types;

import com.rcallum.CalCore.Commands.Arguments.CommandArgument;

public class ArgInt extends CommandArgument<Integer> {
    public ArgInt() {
        setIdentity("int");
        setMapper((input)-> {
            try {
                int integer = Integer.parseInt(input);
                return integer;
            } catch (Exception failed) {
                return null;
            }
        });
    }
}
