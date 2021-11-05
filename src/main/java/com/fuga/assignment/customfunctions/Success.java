package com.fuga.assignment.customfunctions;

public class Success<A> implements Try<A> {

    private final A result;

    public Success(A result) {
        this.result = result;
    }

    @Override
    public boolean isSuccess() {
        return true;
    }

    @Override
    public boolean isFailure() {
        return false;
    }

    @Override
    public A get() {
        return result;
    }
}
