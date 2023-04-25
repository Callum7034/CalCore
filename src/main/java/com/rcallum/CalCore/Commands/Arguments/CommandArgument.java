package com.rcallum.CalCore.Commands.Arguments;

public abstract class CommandArgument<T> {
    private String identity = "";
    private ArgumentMapper<T> mapper;

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public void setMapper(ArgumentMapper<T> mapper) {
        this.mapper = mapper;
    }

    public String getIdentity() {
        return identity;
    }

    public ArgumentMapper<T> getMapper() {
        return mapper;
    }
}
