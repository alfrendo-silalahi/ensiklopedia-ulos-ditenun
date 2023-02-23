package com.ensiklopediaulos.ditenun.controller;

import com.ensiklopediaulos.ditenun.service.UlosEnsiklopediaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static com.ensiklopediaulos.ditenun.util.GlobalConstant.*;

@Slf4j
@RestController
@RequestMapping(path = "/api/v1/ensiklopedia/ulos")
public class UlosEnsiklopediaController {

    private final UlosEnsiklopediaService ulosEnsiklopediaService;

    public UlosEnsiklopediaController(UlosEnsiklopediaService ulosEnsiklopediaService) {
        this.ulosEnsiklopediaService = ulosEnsiklopediaService;
    }

    @GetMapping
    public String test() {
        return "TEST";
    }

    @PostMapping
    public ResponseEntity<Object> saveMultipleImage(
            @RequestParam(value = "ulos_main_image_ensiklopedia", required = false) MultipartFile ulosMainImageEnsiklopedia,
            @RequestParam("ulos_images_ensiklopedia") List<MultipartFile> ulosImagesEnsiklopedia,
            @RequestParam("ulos_cut_images_ensiklopedia") List<MultipartFile> ulosCutImagesEnsiklopedia,
            @RequestParam(value = "ulos_motive_images_ensiklopedia", required = false) List<MultipartFile> ulosMotiveImagesEnsiklopedia,
            @RequestParam(value = "ulos_data_ensiklopedia", required = false) String ulosDataEnsiklopedia)
            throws IOException {

        ulosEnsiklopediaService.createUlosDataEnsiklopedia(
                ulosMainImageEnsiklopedia,
                ulosImagesEnsiklopedia,
                ulosCutImagesEnsiklopedia,
                ulosMotiveImagesEnsiklopedia,
                ulosDataEnsiklopedia);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

}
