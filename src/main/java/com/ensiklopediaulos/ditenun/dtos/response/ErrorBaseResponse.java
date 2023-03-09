package com.ensiklopediaulos.ditenun.dtos.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ErrorBaseResponse<T> {

    private Integer code;

    private String status;

    private T message;

}
