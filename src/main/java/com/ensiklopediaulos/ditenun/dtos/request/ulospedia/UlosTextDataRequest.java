package com.ensiklopediaulos.ditenun.dtos.request.ulospedia;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class UlosTextDataRequest {

    @NotBlank(message = "name cannot be empty")
    private String name;

    @NotBlank(message = "ethnic cannot be empty")
    private String ethnic;

    private String location;

    @NotNull(message = "colors cannot be null")
    private List<Long> colors;

    private UlosSizeRequest size;

    private String technique;

    @NotBlank(message = "cannot be empty")
    private String meaning;

    @NotBlank(message = "cannot be empty")
    private String func;

    @NotBlank(message = "cannot be empty")
    private String history;

}
