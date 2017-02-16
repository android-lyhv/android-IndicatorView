package com.example.indincator;

public class PagesException extends Exception {
    @Override
    public String getMessage() {
        return "Pages must equal or larger than 2";
    }
}