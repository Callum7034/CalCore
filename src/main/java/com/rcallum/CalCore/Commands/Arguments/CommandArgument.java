package com.rcallum.CalCore.Commands.Arguments;

public abstract class CommandArgument<T> {
    private String identity = "";
    private ArgumentMapper<T> mapper;
    private boolean isOptional = false;

    public void setIdentity(String identity) {
        this.identity = identity;
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

    public ArgumentMapper<T> getMapper() {
        return mapper;
    }
}
