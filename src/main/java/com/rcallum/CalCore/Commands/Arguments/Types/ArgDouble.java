package com.rcallum.CalCore.Commands.Arguments.Types;

import com.rcallum.CalCore.Commands.Arguments.CommandArgument;

import java.util.Arrays;

public class ArgDouble extends CommandArgument<Double> {
    public ArgDouble() {
        setIdentity("double");
        setDescription("amount");
        setMapper((input) -> {
            try {
                double number = Double.parseDouble(input);
                return number;
            } catch (Exception failed) {
                return null;
            }
        });
        setTabCompletion(input -> {
            return Arrays.asList(getDescription());
        });
    }
}
