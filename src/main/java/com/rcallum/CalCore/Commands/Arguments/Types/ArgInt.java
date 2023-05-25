package com.rcallum.CalCore.Commands.Arguments.Types;

import com.rcallum.CalCore.Commands.Arguments.CommandArgument;

import java.util.Arrays;

public class ArgInt extends CommandArgument<Integer> {
    public ArgInt() {
        setIdentity("int");
        setDescription("amount");
        setMapper((input)-> {
            try {
                int integer = Integer.parseInt(input);
                return integer;
            } catch (Exception failed) {
                return null;
            }
        });
        setTabCompletion(input -> {
            return Arrays.asList(getDescription());
        });
    }
}
