package com.ensiklopediaulos.ditenun.dtos.request;

import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class UlosRequest {

    private String name;
    
    private String ethnic;

    private String location;

    private List<Long> colors;

    private UlosSize size;

    private String technique;

    private String meaning;

    private String func;

    private String history;

    private Boolean availableInEcommerce;

    private String linkToEcommerce;

}
