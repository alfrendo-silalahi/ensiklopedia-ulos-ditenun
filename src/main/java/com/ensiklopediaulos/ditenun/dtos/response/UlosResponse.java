package com.ensiklopediaulos.ditenun.dtos.response;

import com.ensiklopediaulos.ditenun.dtos.request.UlosSize;
import lombok.Data;

import java.util.List;

@Data
public class UlosResponse {

    private Long id;

    private String uuid;

    private String name;

    private String ethnic;

    private String location;

    private List<String> colors;

    private UlosSize size;

    private String technique;

    private String meaning;

    private String func;

    private String history;

    private Boolean availableInEcommerce;

    private String linkToEcommerce;

}
