package com.onebit.barbearia_fernandes.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ResourceNotFoundException extends RuntimeException {
    private final String message;
}
