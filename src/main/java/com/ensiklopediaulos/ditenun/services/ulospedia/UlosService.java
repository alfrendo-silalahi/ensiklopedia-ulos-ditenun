package com.ensiklopediaulos.ditenun.services.ulospedia;

import com.ensiklopediaulos.ditenun.dtos.request.ulospedia.UlosRequest;
import com.ensiklopediaulos.ditenun.dtos.request.ulospedia.UlosSize;
import com.ensiklopediaulos.ditenun.dtos.response.ulospedia.UlosIdUuidResponse;
import com.ensiklopediaulos.ditenun.dtos.response.ulospedia.UlosMainImageResponse;
import com.ensiklopediaulos.ditenun.dtos.response.ulospedia.UlosResponse;
import com.ensiklopediaulos.ditenun.exceptions.ResourceNotFoundException;
import com.ensiklopediaulos.ditenun.models.ulospedia.Color;
import com.ensiklopediaulos.ditenun.models.ulospedia.Ulos;
import com.ensiklopediaulos.ditenun.repositories.ulospedia.ColorRepository;
import com.ensiklopediaulos.ditenun.repositories.ulospedia.UlosRepository;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Slf4j
@Service
public class UlosService {

    private final ResourceLoader resourceLoader;

    private final UlosRepository ulosRepository;

    private final ColorRepository colorRepository;

    public UlosService(ResourceLoader resourceLoader, UlosRepository ulosRepository, ColorRepository colorRepository) {
        this.resourceLoader = resourceLoader;
        this.ulosRepository = ulosRepository;
        this.colorRepository = colorRepository;
    }

    /**
     * create ulos id and uuid
     */
    public UlosIdUuidResponse getUlosIdUuid() {
        var ulos = new Ulos();
        ulos.setUuid(UUID.randomUUID().toString());
        Ulos newUlos = saveUlos(ulos);

        log.trace(String.format("getUlosIdUuid() -> id {%s} | uuid {%s}", newUlos.getId(), ulos.getUuid()));

        var ulosIdUuidResponse = new UlosIdUuidResponse();
        ulosIdUuidResponse.setId(newUlos.getId());
        ulosIdUuidResponse.setUuid(newUlos.getUuid());
        return ulosIdUuidResponse;
    }

    /**
     * update ulos text-data
     */
    public UlosResponse updateUlosTextData(String uuid, UlosRequest ulosRequest) {
        var ulos = findUlosByUuid(uuid);
        mapUlosRequestToUlos(ulos, ulosRequest);
        log.trace("updateUlosData() : ulos before save to db -> " + ulos);

        var updatedUlos = saveUlos(ulos);
        log.trace("updateUlosData() : updated ulos -> " + updatedUlos);

        return mapUlosToUlosResponse(updatedUlos);
    }

    /**
     *
     */

    /**
     * save ulos
     */
    private Ulos saveUlos(Ulos ulos) {
        return ulosRepository.save(ulos);
    }

    /**
     * find ulos by uuid
     */
    private Ulos findUlosByUuid(String uuid) {
        Optional<Ulos> optionalUlos = ulosRepository.findByUuid(uuid);
        if (optionalUlos.isEmpty()) {
            throw new ResourceNotFoundException("ulos", "uuid", uuid);
        }
        return optionalUlos.get();
    }

    /**
     * find color by id
     */
    private Color findColorById(Long id) {
        Optional<Color> optionalColor = colorRepository.findById(id);
        if (optionalColor.isEmpty()) {
            throw new ResourceNotFoundException("color", "id", id.toString());
        }
        return optionalColor.get();
    }

    /**
     * mapping ulos-request -> ulos-model
     */
    private void mapUlosRequestToUlos(Ulos ulos, UlosRequest ulosRequest) {
        ulos.setName(ulosRequest.getName());
        ulos.setEthnic(ulosRequest.getEthnic());
        ulos.setLocation(ulosRequest.getLocation());
        ulos.setLength(ulosRequest.getSize().getLength());
        ulos.setWidth(ulosRequest.getSize().getWidth());
        ulos.setTechnique(ulosRequest.getTechnique());
        ulos.setMeaning(ulosRequest.getMeaning());
        ulos.setFunc(ulosRequest.getFunc());
//        ulos.setAvailableInEcommerce(ulosRequest.getAvailableInEcommerce());
//        ulos.setLinkToEcommerce(ulosRequest.getLinkToEcommerce());

        List<Color> colors = new ArrayList<>();
        for (var id : ulosRequest.getColors()) {
            var color = findColorById(id);
            colors.add(color);
        }
        ulos.setColors(colors);

        for (var id : ulosRequest.getColors()) {
            var color = findColorById(id);
            color.getUlos().add(ulos);
            colorRepository.save(color);
        }
    }

    /**
     * mapping ulos-model -> ulos-response
     */
    private UlosResponse mapUlosToUlosResponse(Ulos ulos) {
        log.trace("mapUlosToUlosResponse() : ulos from param -> " + ulos);

        var ulosResponse = new UlosResponse();

        ulosResponse.setId(ulos.getId());
        ulosResponse.setUuid(ulos.getUuid());
        ulosResponse.setName(ulos.getName());
        ulosResponse.setEthnic(ulos.getEthnic());
        ulosResponse.setLocation(ulos.getLocation());
        ulosResponse.setSize(new UlosSize(ulos.getLength(), ulos.getWidth()));
        ulosResponse.setTechnique(ulos.getTechnique());
        ulosResponse.setMeaning(ulos.getMeaning());
        ulosResponse.setFunc(ulos.getFunc());
//        ulosResponse.setAvailableInEcommerce(ulos.getAvailableInEcommerce());
//        ulosResponse.setLinkToEcommerce(ulos.getLinkToEcommerce());

        List<String> listOfColors = new ArrayList<>();
        for (var color : ulos.getColors()) {
            listOfColors.add(color.getColor());
        }
        ulosResponse.setColors(listOfColors);

        log.trace("mapUlosToUlosResponse() : ulos response -> " + ulosResponse);
        return ulosResponse;
    }

    /**
     * seed data color
     */
    @PostConstruct
    public void seedColorData() {
        if (colorRepository.count() == 0) {
            colorRepository.save(new Color(null, "merah", null));
            colorRepository.save(new Color(null, "hijau", null));
            colorRepository.save(new Color(null, "biru", null));
            colorRepository.save(new Color(null, "hitam", null));
        }
    }

    /**
     * update ulos main image
     */
    public UlosMainImageResponse updateUlosMainImage(String uuid, MultipartFile mainImage) throws IOException {
        var ulos = findUlosByUuid(uuid);

        ulos.setMainImageReference(UUID.randomUUID() + mainImage.getOriginalFilename());
        var updatedUlos = ulosRepository.save(ulos);

        var currentProjectDirectory = System.getProperty("user.dir");
        log.trace(currentProjectDirectory);

        var newFormatCurrentProjectDirectory = String.join("\\", currentProjectDirectory.split("/"));
        log.trace(newFormatCurrentProjectDirectory);

        var ulosMainImagePath = "\\src\\main\\resources\\static\\images\\ulospedia\\ulos\\main-images\\";

        mainImage
                .transferTo(new File(newFormatCurrentProjectDirectory + ulosMainImagePath + updatedUlos.getMainImageReference()));

        var response = new UlosMainImageResponse();
        response.setId(updatedUlos.getId());
        response.setUuid(updatedUlos.getUuid());
        response.setMainImageReference(updatedUlos.getMainImageReference());

        return response;
    }

    /**
     * get ulos main image
     */
    public byte[] getUlosMainImage(String uuid) throws IOException {
        var ulos = findUlosByUuid(uuid);
//        InputStream response = getClass().getResourceAsStream("/static/images/ulospedia/ulos/main-images/" + ulos.getMainImageReference());
//        log.trace(String.valueOf(response != null));
//        return IOUtils.toByteArray(response);
//        File file = resourceLoader.getResource("classpath:static/images/ulospedia/ulos/main-images/" + ulos.getMainImageReference()).getFile();

        var currentProjectDirectory = System.getProperty("user.dir");
        log.trace(currentProjectDirectory);

        var newFormatCurrentProjectDirectory = String.join("\\", currentProjectDirectory.split("/"));
        log.trace(newFormatCurrentProjectDirectory);

        var ulosMainImagePath = "\\src\\main\\resources\\static\\images\\ulospedia\\ulos\\main-images\\";

        File file = new File(newFormatCurrentProjectDirectory +  ulosMainImagePath + ulos.getMainImageReference());

        return FileCopyUtils.copyToByteArray(file);
        // harus reload terlebih dahulu
//        Resource imageResource =
//                resourceLoader.getResource("classpath:static/images/ulospedia/ulos/main-images/" + ulos.getMainImageReference());
//        return imageResource;
    }

    public String uploadImage(String path, MultipartFile file) throws IOException {
        // file name
        String name = file.getOriginalFilename();

        // full path
        String filePath = path + File.separator + name;

        // create folder if not created
        File f = new File(path);
        if (f.exists()) {
            f.mkdir();
        }

        // file copy
        Files.copy(file.getInputStream(), Paths.get(filePath));

        return name;
    }

}

// jTenun2018