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
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/v1/ulospedia/ulos")
public class UlosController {

    private final UlosService ulosService;

    private final Map<String, Object> dataObj = new HashMap<>();

    public UlosController(UlosService ulosService) {
        this.ulosService = ulosService;
    }

    /**
     * end point get new ulos id and uuid
     * deprecated
     */
    @Deprecated(forRemoval = true)
    @PostMapping(path = "/id-uuid/deprecated")
    @Operation(
            summary = "GENERATE NEW ULOS ID AND UUID"
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
     * deprecated
     */
    @Deprecated(forRemoval = true)
    @PutMapping(path = "/text-data/{uuid}/deprecated")
    @Operation(summary = "UPDATE ULOS TEXT DATA")
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
     * end point create ulos text data
     */
    @PostMapping(path = "/text-data")
    @Operation(summary = "CREATE ULOS TEXT DATA")
    public ResponseEntity<SuccessBaseResponse<Map<String, Object>>> createUlosTextData(@Valid @RequestBody UlosTextDataRequest ulosTextDataRequest) {
        UlosTextDataResponse data = ulosService.createUlosTextData(ulosTextDataRequest);

        SuccessBaseResponse<Map<String, Object>> response = new SuccessBaseResponse<>();
        response.setCode(HttpStatus.OK.value());
        response.setStatus("success");

        dataObj.clear();
        dataObj.put("ulosTextData", data);
        response.setData(dataObj);

        response.setMessage("ulos id, uuid, and text data successfully added");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * end point create ulos main image
     */
    @PutMapping(path = "/{uuid}/main-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "UPDATE MAIN IMAGE")
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
    @GetMapping(path = "/{uuid}/main-image", produces = MediaType.IMAGE_JPEG_VALUE)
    @Operation(summary = "GET ULOS MAIN IMAGE")
    public byte[] getUlosMainImage(@PathVariable(name = "uuid") String uuid) throws IOException {
        return ulosService.getUlosMainImage(uuid);
    }

    /**
     * end point get ulos total count
     */
    @GetMapping(path = "/count")
    @Operation(summary = "GET ULOS TOTAL COUNT")
    public ResponseEntity<SuccessBaseResponse<Map<String, Object>>> getUlosTotalCount() {
        Long data = ulosService.getUlosTotalCount();

        SuccessBaseResponse<Map<String, Object>> response = new SuccessBaseResponse<>();
        response.setCode(HttpStatus.OK.value());
        response.setStatus("success");

        dataObj.clear();
        dataObj.put("totalUlos", data);
        response.setData(dataObj);

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
