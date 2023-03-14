package com.ensiklopediaulos.ditenun.dtos.response.ulospedia;

import com.ensiklopediaulos.ditenun.models.ulospedia.Weaver;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class AllWeaverData {

    private Integer pageNo;

    private Integer pageSize;

    private Integer totalElementOnPage;

    private Long totalAllElement;

    private Integer totalPages;

    private Boolean lastPage;

    private List<SpecificWeaverDataResponse> weaverDataList;

}
