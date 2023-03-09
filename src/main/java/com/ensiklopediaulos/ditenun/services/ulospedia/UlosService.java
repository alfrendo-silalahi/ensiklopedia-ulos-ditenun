package com.ensiklopediaulos.ditenun.services.ulospedia;

import com.ensiklopediaulos.ditenun.dtos.request.ulospedia.UlosTextDataRequest;
import com.ensiklopediaulos.ditenun.dtos.response.ulospedia.UlosIdUuidResponse;
import com.ensiklopediaulos.ditenun.dtos.response.ulospedia.UlosMainImageResponse;
import com.ensiklopediaulos.ditenun.dtos.response.ulospedia.UlosSizeResponse;
import com.ensiklopediaulos.ditenun.dtos.response.ulospedia.UlosTextDataResponse;
import com.ensiklopediaulos.ditenun.exceptions.ResourceNotFoundException;
import com.ensiklopediaulos.ditenun.models.ulospedia.Color;
import com.ensiklopediaulos.ditenun.models.ulospedia.Product;
import com.ensiklopediaulos.ditenun.models.ulospedia.Ulos;
import com.ensiklopediaulos.ditenun.repositories.ulospedia.ColorRepository;
import com.ensiklopediaulos.ditenun.repositories.ulospedia.UlosRepository;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.json.ParseException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.ensiklopediaulos.ditenun.utils.GlobalConstant.NEW_FORMAT_CURRENT_PROJECT_DIRECTORY;
import static com.ensiklopediaulos.ditenun.utils.GlobalConstant.ULOS_MAIN_IMAGES_PATH;

@Slf4j
@Service
public class UlosService {

    private final UlosRepository ulosRepository;

    private final ColorRepository colorRepository;

    public UlosService(UlosRepository ulosRepository, ColorRepository colorRepository) {
        this.ulosRepository = ulosRepository;
        this.colorRepository = colorRepository;
    }

    /**
     * create ulos id and uuid
     */
    public UlosIdUuidResponse getUlosIdUuid() {
        Ulos ulos = new Ulos();
        ulos.setUuid(UUID.randomUUID().toString());
        Ulos newUlos = saveUlos(ulos);

        UlosIdUuidResponse ulosIdUuidResponse = new UlosIdUuidResponse();
        ulosIdUuidResponse.setUuid(newUlos.getUuid());
        return ulosIdUuidResponse;
    }

    /**
     * update ulos text-data
     */
    public UlosTextDataResponse updateUlosTextData(String uuid, UlosTextDataRequest ulosRequest) {
        Ulos ulos = findUlosByUuid(uuid);
        mapUlosRequestToUlos(ulos, ulosRequest);

        Ulos updatedUlos = saveUlos(ulos);

        return mapUlosToUlosResponse(updatedUlos);
    }

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
    private void mapUlosRequestToUlos(Ulos ulos, UlosTextDataRequest ulosRequest) {
        ulos.setName(ulosRequest.getName());
        ulos.setEthnic(ulosRequest.getEthnic());
        ulos.setLocation(ulosRequest.getLocation());
        ulos.setLength(ulosRequest.getSize().getLength());
        ulos.setWidth(ulosRequest.getSize().getWidth());
        ulos.setTechnique(ulosRequest.getTechnique());
        ulos.setMeaning(ulosRequest.getMeaning());
        ulos.setFunc(ulosRequest.getFunc());

        List<Color> colors = new ArrayList<>();
        for (Long id : ulosRequest.getColors()) {
            var color = findColorById(id);
            colors.add(color);
        }
        ulos.setColors(colors);

        for (Long id : ulosRequest.getColors()) {
            var color = findColorById(id);
            color.getUlos().add(ulos);
            colorRepository.save(color);
        }
    }

    /**
     * mapping ulos-model -> ulos-response
     */
    private UlosTextDataResponse mapUlosToUlosResponse(Ulos ulos) {
        UlosTextDataResponse ulosResponse = new UlosTextDataResponse();

        ulosResponse.setUuid(ulos.getUuid());
        ulosResponse.setName(ulos.getName());
        ulosResponse.setEthnic(ulos.getEthnic());
        ulosResponse.setLocation(ulos.getLocation());
        ulosResponse.setSize(new UlosSizeResponse(ulos.getLength(), ulos.getWidth()));
        ulosResponse.setTechnique(ulos.getTechnique());
        ulosResponse.setMeaning(ulos.getMeaning());
        ulosResponse.setFunc(ulos.getFunc());

        List<String> listOfColors = new ArrayList<>();
        for (Color color : ulos.getColors()) {
            listOfColors.add(color.getColor());
        }
        ulosResponse.setColors(listOfColors);

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
    public UlosMainImageResponse createAndUpdateUlosMainImage(String uuid, MultipartFile mainImage) throws IOException {
        Ulos ulos = findUlosByUuid(uuid);

        // delete existing main-image
        File fileToDelete = new File(NEW_FORMAT_CURRENT_PROJECT_DIRECTORY + ULOS_MAIN_IMAGES_PATH + ulos.getMainImageReference());
        fileToDelete.delete();

        // update main-image reference
        ulos.setMainImageReference(UUID.randomUUID() + mainImage.getOriginalFilename());
        Ulos updatedUlos = ulosRepository.save(ulos);

        // save to new main-image to local folder
        File file = new File(NEW_FORMAT_CURRENT_PROJECT_DIRECTORY + ULOS_MAIN_IMAGES_PATH + updatedUlos.getMainImageReference());
        mainImage.transferTo(file);

        UlosMainImageResponse response = new UlosMainImageResponse();
        response.setUuid(updatedUlos.getUuid());
        response.setMainImageReference(updatedUlos.getMainImageReference());

        return response;
    }

    /**
     * get ulos main image
     */
    public byte[] getUlosMainImage(String uuid) throws IOException {
        Ulos ulos = findUlosByUuid(uuid);
        File file = new File(NEW_FORMAT_CURRENT_PROJECT_DIRECTORY +  ULOS_MAIN_IMAGES_PATH + ulos.getMainImageReference());
        return FileCopyUtils.copyToByteArray(file);
    }

    /**
     * get ulos total count
     */
    public Long getUlosTotalCount() {
        return ulosRepository.count();
    }

    /**
     * save product data that relate to ulos
     */
    public void saveProduct(String json) throws ParseException, org.json.simple.parser.ParseException {
        JSONParser parser = new JSONParser();
        JSONObject j = (JSONObject) parser.parse(json);
        JSONArray products = (JSONArray) j.get("products");
        for (int i = 0; i < products.size(); i++) {
            JSONObject JSONproduct = (JSONObject) products.get(i);
            Product product = new Product();
            product.setName((String) JSONproduct.get("name"));
        }
        System.out.println(products);

    }

}
