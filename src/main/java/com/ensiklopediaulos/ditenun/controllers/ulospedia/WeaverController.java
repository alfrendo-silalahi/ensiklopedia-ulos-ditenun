package com.ensiklopediaulos.ditenun.controllers.ulospedia;

import com.ensiklopediaulos.ditenun.dtos.request.ulospedia.WeaverTextDataRequest;
import com.ensiklopediaulos.ditenun.dtos.response.SuccessBaseResponse;
import com.ensiklopediaulos.ditenun.dtos.response.ulospedia.AllWeaverData;
import com.ensiklopediaulos.ditenun.dtos.response.ulospedia.SpecificWeaverDataResponse;
import com.ensiklopediaulos.ditenun.dtos.response.ulospedia.WeaverImageDataResponse;
import com.ensiklopediaulos.ditenun.dtos.response.ulospedia.WeaverTextDataResponse;
import com.ensiklopediaulos.ditenun.services.ulospedia.WeaverService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/v1/ulospedia/weavers")
public class WeaverController {

    private final WeaverService weaverService;

    private final Map<String, Object> dataObj = new HashMap<>();

    public WeaverController(WeaverService weaverService) {
        this.weaverService = weaverService;
    }

    /**
     * end point create weaver text data
     */
    @PostMapping
    @Operation(summary = "CREATE WEAVER TEXT DATA")
    public ResponseEntity<SuccessBaseResponse<Map<String, Object>>> createWeaverTextData(@RequestBody WeaverTextDataRequest weaverTextDataRequest) {
        WeaverTextDataResponse data = weaverService.createWeaverTextData(weaverTextDataRequest);

        SuccessBaseResponse<Map<String, Object>> response = new SuccessBaseResponse<>();
        response.setCode(HttpStatus.CREATED.value());
        response.setStatus("success");

        dataObj.clear();
        dataObj.put("weaver", data);
        response.setData(dataObj);

        response.setMessage("new weaver text data successfully created");

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * end point create weaver image
     */
    @PutMapping(path = "/{uuid}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "CREATE WEAVER IMAGE")
    public ResponseEntity<SuccessBaseResponse<Map<String, Object>>> createWeaverImage(
            @RequestParam("weaver-image")MultipartFile weaverImage,
            @PathVariable("uuid") String weaverUuid) throws IOException {
        WeaverImageDataResponse data = weaverService.createWeaverImage(weaverUuid, weaverImage);

        SuccessBaseResponse<Map<String, Object>> response = new SuccessBaseResponse<>();
        response.setCode(HttpStatus.OK.value());
        response.setStatus("success");

        dataObj.clear();
        dataObj.put("weaverImageData", data);
        response.setData(dataObj);
        response.setMessage("image successfully added");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * get weaver image as response
     */
    @GetMapping(path = "/{uuid}/image", produces = MediaType.IMAGE_JPEG_VALUE)
    @Operation(summary = "GET WEAVER IMAGES AS A RESPONSE")
    public byte[] getWeaverImage(@PathVariable("uuid") String uuid) throws IOException {
        return weaverService.getWeaverImage(uuid);
    }

    /**
     * get all weaver data
     */
    @GetMapping
    @Operation(summary = "GET ALL WEAVERS DATA")
    public ResponseEntity<SuccessBaseResponse<Map<String, Object>>> getAllWeavers(
            @RequestParam(name = "pageNo", required = false, defaultValue = "1") Integer pageNo,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pagesSize,
            @RequestParam(name = "sortBy", required = false, defaultValue = "id") String sortBy,
            @RequestParam(name = "sortDir", required = false, defaultValue = "asc") String sortDir
    ) {
        AllWeaverData data = weaverService.getAllWeaverData(pageNo, pagesSize, sortBy, sortDir);

        SuccessBaseResponse<Map<String, Object>> response = new SuccessBaseResponse<>();
        response.setCode(HttpStatus.OK.value());
        response.setStatus("success");

        dataObj.clear();
        dataObj.put("weavers", data);
        response.setData(dataObj);

        response.setMessage("all weavers successfully fetched");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * end point delete weaver
     */
    @DeleteMapping("/{uuid}")
    @Operation(summary = "DELETE WEAVER {text data + image}")
    public ResponseEntity<SuccessBaseResponse<Object>> deleteWeaver(@PathVariable(name = "uuid") String uuid) {
        weaverService.deleteWeaverData(uuid);

        SuccessBaseResponse<Object> response = new SuccessBaseResponse<>();
        response.setCode(HttpStatus.OK.value());
        response.setStatus("success");

        response.setData(null);

        response.setMessage("weaver with uuid " + uuid + " successfully deleted");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * end point get weaver by uuid
     */
    @GetMapping(path = "/{uuid}")
    @Operation(summary = "GET WEAVER BY UUID")
    public ResponseEntity<SuccessBaseResponse<Map<String, Object>>> getWeaverByUuid(
            @PathVariable(name = "uuid") String uuid
    ) {
        SpecificWeaverDataResponse data = weaverService.getWeaverByUuid(uuid);

        SuccessBaseResponse<Map<String, Object>> response = new SuccessBaseResponse<>();
        response.setCode(HttpStatus.OK.value());
        response.setStatus("success");

        dataObj.clear();
        dataObj.put("weaver", data);
        response.setData(dataObj);

        response.setMessage("weaver with uuid " + uuid + " successfully fetched");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * end point update text-data weaver
     */
    @PutMapping(path = "/{uuid}")
    @Operation(summary = "UPDATE WEAVER TEXT DATA")
    public ResponseEntity<SuccessBaseResponse<Map<String, Object>>> updateWeaverTextData(
            @PathVariable(name = "uuid") String uuid,
            @RequestBody WeaverTextDataRequest request
    ) {
        SpecificWeaverDataResponse data = weaverService.updateWeaverTextData(uuid, request);

        SuccessBaseResponse<Map<String, Object>> response = new SuccessBaseResponse<>();
        response.setCode(HttpStatus.OK.value());
        response.setStatus("success");

        dataObj.clear();
        dataObj.put("weaver", data);
        response.setData(dataObj);

        response.setMessage("weaver with uuid {" + uuid + "} successfully updated");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * end point get count of weaver data
     */
    @GetMapping(path = "/count")
    @Operation(summary = "GET COUNT OF WEAVER")
    public ResponseEntity<SuccessBaseResponse<Map<String, Object>>> getCountWeaver() {
        Long count = weaverService.getCountWeaver();

        SuccessBaseResponse<Map<String, Object>> response = new SuccessBaseResponse<>();
        response.setCode(HttpStatus.OK.value());
        response.setStatus("success");

        dataObj.clear();
        dataObj.put("countWeaver", count);
        response.setData(dataObj);

        response.setMessage("count of weaver successfully retrieved");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
