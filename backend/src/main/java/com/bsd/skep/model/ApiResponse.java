package com.bsd.skep.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.function.Function;

@Getter
@Setter
public class ApiResponse<T> {

    private boolean success;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String errorMessage;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public ApiResponse(T data) {
        this.data = data;
        if (data != null) {
            this.success = true;
        }
    }

    public <E> ApiResponse(E data, Function<E, T> dtoConverter) {
        if (data != null) {
            this.data = dtoConverter.apply(data);
            this.success = true;
        }
    }

    public ApiResponse(boolean success, String errorMessage) {
        this.errorMessage = errorMessage;
        this.success = success;
    }

}
