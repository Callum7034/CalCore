package com.rcallum.CalCore.Utils;

public class CPair<F, S> {

    private F first;

    private S second;

    public F getKey(){
        return first;
    }

    public S getValue() {
        return second;
    }

    public void set(F first, S second) {
        this.first = first;
        this.second = second;
    }

}
