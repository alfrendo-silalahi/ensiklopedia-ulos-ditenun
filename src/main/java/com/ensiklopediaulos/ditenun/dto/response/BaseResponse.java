package com.ensiklopediaulos.ditenun.dto.response;

import lombok.Data;

@Data
public class BaseResponse<T> {

    private Integer code;

    private String status;

    private T data;

    private String message;

}
