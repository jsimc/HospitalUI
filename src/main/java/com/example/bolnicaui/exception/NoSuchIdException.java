package com.example.bolnicaui.exception;

import java.util.NoSuchElementException;

public class NoSuchIdException extends HospitalException {
    public NoSuchIdException(String message) {
        super(message);
    }
}
