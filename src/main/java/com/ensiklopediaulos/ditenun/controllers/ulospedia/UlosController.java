package com.ensiklopediaulos.ditenun.controllers.ulospedia;

import com.ensiklopediaulos.ditenun.dtos.request.ulospedia.UlosTextDataRequest;
import com.ensiklopediaulos.ditenun.dtos.response.SuccessBaseResponse;
import com.ensiklopediaulos.ditenun.dtos.response.ulospedia.UlosIdUuidResponse;
import com.ensiklopediaulos.ditenun.dtos.response.ulospedia.UlosMainImageResponse;
import com.ensiklopediaulos.ditenun.dtos.response.ulospedia.UlosTextDataResponse;
import com.ensiklopediaulos.ditenun.services.ulospedia.UlosService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.apache.tomcat.util.json.ParseException;
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
    @Operation(
            summary = "GENERATE NEW ULOS ID AND UUID",
            description = "test test test"
    )
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
    public ResponseEntity<SuccessBaseResponse<UlosTextDataResponse>> updateUlosTextData(
            @PathVariable(name = "uuid") String uuid,
            @Valid @RequestBody UlosTextDataRequest ulosRequest) {
        UlosTextDataResponse data = ulosService.updateUlosTextData(uuid, ulosRequest);

        SuccessBaseResponse<UlosTextDataResponse> response = new SuccessBaseResponse<>();
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
        UlosMainImageResponse data = ulosService.createAndUpdateUlosMainImage(uuid, mainImage);

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

    /**
     * end point get ulos total count
     */
    @GetMapping(path = "/count")
    public ResponseEntity<SuccessBaseResponse<Long>> getUlosTotalCount() {
        Long data = ulosService.getUlosTotalCount();

        SuccessBaseResponse<Long> response = new SuccessBaseResponse<>();
        response.setCode(HttpStatus.OK.value());
        response.setStatus("success");
        response.setData(data);
        response.setMessage("ulos total count retrieved successfully");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * end point save product data that relate to ulos
     */
    @PutMapping(path = "/products")
    public void saveProducts() throws ParseException, org.json.simple.parser.ParseException {
        ulosService.saveProduct("{\"products\": [{\"name\": \"A\",\"address\": \"B\"},{\"name\": \"C\",\"address\": \"D\"}]}");
    }

}
