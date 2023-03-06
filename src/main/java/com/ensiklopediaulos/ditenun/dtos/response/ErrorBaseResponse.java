package com.ensiklopediaulos.ditenun.dtos.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ErrorBaseResponse {

    private Integer code;

    private String status;

    private String message;

}
