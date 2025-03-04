package me.vaan.clickTpa.common;

@FunctionalInterface
public interface ErrorHandler {
    void severe(String message);
}
