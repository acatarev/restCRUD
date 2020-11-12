package com.rest.payments.exceptions;

import org.springframework.core.MethodParameter;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;

public class MethodArgumentInvalidException extends MethodArgumentNotValidException {

    public MethodArgumentInvalidException(final MethodParameter parameter, final BindingResult bindingResult) {
        super(parameter, bindingResult);
    }

    @Override
    public String getMessage() {
        StringBuilder sb = new StringBuilder("Validation failed :");
        for (ObjectError error : getBindingResult().getAllErrors()) {
            sb.append('\n').append('\t').append("[").append(error.getDefaultMessage()).append("]");
        }
        return sb.toString();
    }
}
