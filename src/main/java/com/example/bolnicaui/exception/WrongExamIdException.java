package com.example.bolnicaui.exception;

public class WrongExamIdException extends HospitalException{
    public WrongExamIdException(String message) {
//        String examId = message.substring(0, message.indexOf(" "));
//        String patientId = message.substring(message.indexOf(" ")+1);
        super("Exam with Id = " + message.substring(0, message.indexOf(" ")) + " does not match patient Id = " + message.substring(message.indexOf(" ")+1));
    }
}
