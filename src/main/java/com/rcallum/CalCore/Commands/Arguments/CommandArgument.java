package com.rcallum.CalCore.Commands.Arguments;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class CommandArgument<T> {
    private String identity = "";
    private ArgumentMapper<T> mapper;
    private Function<String, Collection<String>> argForTabFunction;
    private boolean isOptional = false;
    private String description = "";

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTabCompletion(Function function) {
        argForTabFunction = function;
    }

    public void setMapper(ArgumentMapper<T> mapper) {
        this.mapper = mapper;
    }

    public CommandArgument<T> setOptional(boolean optional) {
        isOptional = optional;
        return this;
    }

    public boolean isOptional() {
        return isOptional;
    }

    public String getIdentity() {
        return identity;
    }

    public String getDescription() {
        return description;
    }

    public ArgumentMapper<T> getMapper() {
        return mapper;
    }

    public Collection<String> getArgForTab(String input) {
        List<String> tabCompletion = new ArrayList<>();
        tabCompletion.addAll(argForTabFunction.apply(input));
        return tabCompletion;
    }

    public Collection<String> getArgForTab() {
        List<String> tabCompletion = new ArrayList<>();
        tabCompletion.addAll(argForTabFunction.apply(""));
        return tabCompletion;
    }

    @Override
    public String toString() {
        return identity;
    }
}
