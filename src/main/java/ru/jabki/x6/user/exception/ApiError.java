package ru.jabki.x6.user.exception;

import lombok.Data;

@Data
public class ApiError {

    final boolean isSuccess;
    final String message;
}