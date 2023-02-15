package com.example.bolnicaui.exception;

import jakarta.validation.constraints.Null;

public class DoNothingException {
    public static void ignoringExc(RunnableExc r) {
        try {
            r.run();
        }
        catch (NullPointerException nullPointerException) {/* do Nothing*/}
        catch (Exception ignored) {}
    }

}
