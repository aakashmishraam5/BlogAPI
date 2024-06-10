package com.springboot.blog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{
    private String resouceName;
    private String fieldName;
    private long fieldValue;

    public ResourceNotFoundException(String resouceName, String fieldName, long fieldValue) {
        super(String.format("%s not found  with %s: '%s'",resouceName,fieldName,fieldValue));
        this.resouceName = resouceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public long getFieldValue() {
        return fieldValue;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getResouceName() {
        return resouceName;
    }
}
