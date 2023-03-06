package com.ensiklopediaulos.ditenun.dtos.response.ulospedia;

import com.ensiklopediaulos.ditenun.dtos.request.ulospedia.UlosSize;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString
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

//    private Boolean availableInEcommerce;
//
//    private String linkToEcommerce;

}
