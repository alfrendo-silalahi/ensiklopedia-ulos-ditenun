package com.ensiklopediaulos.ditenun.controllers.ulospedia;

import com.ensiklopediaulos.ditenun.dtos.request.ulospedia.UlosRequest;
import com.ensiklopediaulos.ditenun.dtos.response.SuccessBaseResponse;
import com.ensiklopediaulos.ditenun.dtos.response.ulospedia.UlosIdUuidResponse;
import com.ensiklopediaulos.ditenun.dtos.response.ulospedia.UlosMainImageResponse;
import com.ensiklopediaulos.ditenun.dtos.response.ulospedia.UlosResponse;
import com.ensiklopediaulos.ditenun.services.ulospedia.UlosService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(path = "/api/v1/ulospedia/ulos")
public class UlosController {

    private final UlosService ulosService;

    public UlosController(UlosService ulosService) {
        this.ulosService = ulosService;
    }

    /**
     * end point get new ulos id and uuid
     */
    @PostMapping(path = "/id-uuid")
    public ResponseEntity<SuccessBaseResponse<UlosIdUuidResponse>> generateUlosIdUuid() {
        UlosIdUuidResponse data = ulosService.getUlosIdUuid();

        SuccessBaseResponse<UlosIdUuidResponse> response = new SuccessBaseResponse<>();
        response.setCode(HttpStatus.CREATED.value());
        response.setStatus("success");
        response.setData(data);
        response.setMessage("new ulos id and uuid successfully created");

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * end point update ulos text-data
     */
    @PutMapping(path = "/text-data/{uuid}")
    public ResponseEntity<SuccessBaseResponse<UlosResponse>> updateUlosTextData(
            @PathVariable(name = "uuid") String uuid,
            @RequestBody UlosRequest ulosRequest) {
        UlosResponse data = ulosService.updateUlosTextData(uuid, ulosRequest);

        SuccessBaseResponse<UlosResponse> response = new SuccessBaseResponse<>();
        response.setCode(HttpStatus.OK.value());
        response.setStatus("success");
        response.setData(data);
        response.setMessage("ulos data successfully updated");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * end point create ulos main image
     */
    @PutMapping(path = "/main-image/{uuid}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<SuccessBaseResponse<UlosMainImageResponse>> updateUlosMainImage(
            @RequestParam(name = "main-image") MultipartFile mainImage,
            @PathVariable(name = "uuid") String uuid) throws IOException {
        UlosMainImageResponse data = ulosService.updateUlosMainImage(uuid, mainImage);

        SuccessBaseResponse<UlosMainImageResponse> response = new SuccessBaseResponse<>();
        response.setCode(HttpStatus.OK.value());
        response.setStatus("success");
        response.setData(data);
        response.setMessage("ulos main image successfully updated");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * end point get ulos main image
     */
    @GetMapping(path = "/main-image/{uuid}", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getUlosMainImage(@PathVariable(name = "uuid") String uuid) throws IOException {
        return ulosService.getUlosMainImage(uuid);
    }


    /**
     * end point create list of ulos images
     */
    // @PostMapping(path = "/images")



}
