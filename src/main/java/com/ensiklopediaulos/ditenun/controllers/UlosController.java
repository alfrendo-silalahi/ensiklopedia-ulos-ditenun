package com.ensiklopediaulos.ditenun.controllers;

import com.ensiklopediaulos.ditenun.dtos.request.UlosRequest;
import com.ensiklopediaulos.ditenun.dtos.response.BaseResponse;
import com.ensiklopediaulos.ditenun.dtos.response.UlosIdUuidResponse;
import com.ensiklopediaulos.ditenun.dtos.response.UlosResponse;
import com.ensiklopediaulos.ditenun.services.UlosService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/ulos")
public class UlosController {

    private final UlosService ulosService;

    public UlosController(UlosService ulosService) {
        this.ulosService = ulosService;
    }

    /**
     * end point get new ulos id and uuid
     */
    @PostMapping(path = "/id-uuid")
    public ResponseEntity<BaseResponse<UlosIdUuidResponse>> generateUlosIdUuid() {
        UlosIdUuidResponse data = ulosService.getUlosIdUuid();

        BaseResponse<UlosIdUuidResponse> response = new BaseResponse<>();
        response.setCode(HttpStatus.CREATED.value());
        response.setStatus("success");
        response.setData(data);
        response.setMessage("new ulos id and uuid successfully created");

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * end point create ulos data
     */
    @PostMapping(path = "/data/{uuid}")
    public ResponseEntity<BaseResponse<UlosResponse>> createUlosData(
            @PathVariable(name = "uuid") String uuid,
            @RequestBody UlosRequest ulosRequest) {
        UlosResponse data = ulosService.createUlosData(uuid, ulosRequest);

        BaseResponse<UlosResponse> response = new BaseResponse<>();
        response.setCode(HttpStatus.CREATED.value());
        response.setStatus("success");
        response.setData(data);
        response.setMessage("ulos data successfully added");

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * end point create ulos main image
     */
    // @PostMapping(path = "/main-image")

    /**
     * end point create list of ulos images
     */
    // @PostMapping(path = "/images")

}
