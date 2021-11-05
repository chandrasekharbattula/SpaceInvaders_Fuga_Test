package com.fuga.assignment.customfunctions;

import java.util.function.Function;

public interface Try<A> {

    static <B> Try<B> success(B result) {
        return new Success<>(result);
    }

    static <B> Try<B> failure(Exception exception) {
        return new Failure<>(exception);
    }

    static <B> Try<B> failure(String message) {
        return new Failure<>(new RuntimeException(message));
    }

    static <A, B> Function<A, Try<B>> lift(CheckedFunction<A, B> func) {
        return input -> {
            try {
                return new Success<>(func.apply(input));
            } catch (Exception e) {
                return new Failure<>(e);
            }
        };
    }

    boolean isSuccess();

    boolean isFailure();

    A get();
}