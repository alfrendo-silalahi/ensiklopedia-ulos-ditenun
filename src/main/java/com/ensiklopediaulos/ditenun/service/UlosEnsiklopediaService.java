package com.ensiklopediaulos.ditenun.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static com.ensiklopediaulos.ditenun.util.GlobalConstant.*;

@Service
public class UlosEnsiklopediaService {

    // create new data ulos for ensiklopedia
    public void createUlosDataEnsiklopedia(
            MultipartFile ulosMainImageEnsiklopedia,
            List<MultipartFile> ulosImagesEnsiklopedia,
            List<MultipartFile> ulosCutImagesEnsiklopedia,
            List<MultipartFile> ulosMotiveImagesEnsiklopedia,
            String ulosDataEnsiklopedia
    ) throws IOException {
        for (MultipartFile ulosImage : ulosImagesEnsiklopedia) {
            ulosImage.transferTo(new File(NEW_FORMAT_CURRENT_PROJECT_DIRECTORY + ULOS_IMAGES_PATH + UUID.randomUUID() + "-" + ulosImage.getOriginalFilename()));
        }
        for (MultipartFile ulosCutImage : ulosCutImagesEnsiklopedia) {
            ulosCutImage.transferTo(new File(NEW_FORMAT_CURRENT_PROJECT_DIRECTORY + ULOS_CUT_IMAGES_PATH + UUID.randomUUID() + "-" + ulosCutImage.getOriginalFilename()));
        }
    }

}
