package com.rcallum.CalCore.Commands.Arguments;

public interface ArgumentMapper<T> {
    T parse(String input);
}
