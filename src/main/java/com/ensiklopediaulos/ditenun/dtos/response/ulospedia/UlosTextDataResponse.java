package com.ensiklopediaulos.ditenun.dtos.response.ulospedia;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString
public class UlosTextDataResponse {

    private String uuid;

    private String name;

    private String ethnic;

    private String location;

    private List<String> colors;

    private UlosSizeResponse size;

    private String technique;

    private String meaning;

    private String func;

//    private Boolean availableInEcommerce;
//
//    private String linkToEcommerce;

}
