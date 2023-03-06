package com.ensiklopediaulos.ditenun.dtos.response;

import lombok.Data;

@Data
public class SuccessBaseResponse<T> {

    private Integer code;

    private String status;

    private T data;

    private String message;

}
