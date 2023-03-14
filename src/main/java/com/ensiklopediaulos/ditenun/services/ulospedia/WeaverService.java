package com.ensiklopediaulos.ditenun.services.ulospedia;

import com.ensiklopediaulos.ditenun.dtos.request.ulospedia.WeaverTextDataRequest;
import static com.ensiklopediaulos.ditenun.utils.GlobalConstant.*;

import com.ensiklopediaulos.ditenun.dtos.response.ulospedia.AllWeaverData;
import com.ensiklopediaulos.ditenun.dtos.response.ulospedia.SpecificWeaverDataResponse;
import com.ensiklopediaulos.ditenun.dtos.response.ulospedia.WeaverImageDataResponse;
import com.ensiklopediaulos.ditenun.dtos.response.ulospedia.WeaverTextDataResponse;
import com.ensiklopediaulos.ditenun.exceptions.ResourceNotFoundException;
import com.ensiklopediaulos.ditenun.models.ulospedia.Weaver;
import com.ensiklopediaulos.ditenun.repositories.ulospedia.WeaverRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.Year;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Log4j2
@Service
public class WeaverService {

    private final WeaverRepository weaverRepository;

    public WeaverService(WeaverRepository weaverRepository) {
        this.weaverRepository = weaverRepository;
    }

    /**
     * create weaver text data
     */
    public WeaverTextDataResponse createWeaverTextData(WeaverTextDataRequest weaverTextDataRequest) {
        Weaver weaver = mappingWeaverTextDataRequestToWeaverModel(weaverTextDataRequest);
        Weaver newWeaver = saveWeaver(weaver);
        return mappingWeaverModelToWeaverTextDataResponse(newWeaver);
    }

    /**
     * mapping weaver text data request -> weaver
     */
    private Weaver mappingWeaverTextDataRequestToWeaverModel(WeaverTextDataRequest weaverTextDataRequest) {
        Weaver weaver = new Weaver();

        weaver.setUuid(UUID.randomUUID().toString());
        weaver.setName(weaverTextDataRequest.getName());
        weaver.setAge(Year.now().getValue() - weaverTextDataRequest.getYearOfBirth());
        weaver.setEthnic(weaverTextDataRequest.getEthnic());
        weaver.setDomicile(weaverTextDataRequest.getDomicile());
        weaver.setTheLoom(weaverTextDataRequest.getTheLoom());
        weaver.setTechnique(weaverTextDataRequest.getTechnique());
        weaver.setStory(weaverTextDataRequest.getStory());

        return weaver;
    }

    /**
     * mapping weaver model to weaver text data response
     */
    private WeaverTextDataResponse mappingWeaverModelToWeaverTextDataResponse(Weaver weaver) {
        WeaverTextDataResponse response = new WeaverTextDataResponse();

        response.setUuid(weaver.getUuid());
        response.setName(weaver.getName());
        response.setAge(weaver.getAge());
        response.setEthnic(weaver.getEthnic());
        response.setDomicile(weaver.getDomicile());
        response.setTheLoom(weaver.getTheLoom());
        response.setTechnique(weaver.getTechnique());
        response.setTechnique(weaver.getTechnique());
        response.setStory(weaver.getStory());

        return response;
    }

    /**
     * save weaver to db
     */
    private Weaver saveWeaver(Weaver weaver) {
        return weaverRepository.save(weaver);
    }

    /**
     * create weaver image
     */
    public WeaverImageDataResponse createWeaverImage(String weaverUuid, MultipartFile weaverImage) throws IOException {
        Weaver weaver = findWeaverByUuid(weaverUuid);

        // file to deleted
        File fileToDelete = new File(NEW_FORMAT_CURRENT_PROJECT_DIRECTORY + WEAVER_IMAGES + weaver.getImageReference());
        fileToDelete.delete();

        weaver.setImageReference(UUID.randomUUID() + weaverImage.getOriginalFilename());
        Weaver updatedWeaver = weaverRepository.save(weaver);

        File file = new File(NEW_FORMAT_CURRENT_PROJECT_DIRECTORY + WEAVER_IMAGES + updatedWeaver.getImageReference());
        weaverImage.transferTo(file);

        WeaverImageDataResponse response = new WeaverImageDataResponse();
        response.setUuid(updatedWeaver.getUuid());
        response.setImageReference(updatedWeaver.getImageReference());

        return response;
    }

    /**
     * find weaver by uuid
     */
    private Weaver findWeaverByUuid(String uuid) {
        return weaverRepository.findWeaverByUuid(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("weaver", "uuid", uuid));
    }

    /**
     * get image as a response
     */
    public byte[] getWeaverImage(String uuid) throws IOException {
        Weaver weaver = findWeaverByUuid(uuid);
        File file = new File(NEW_FORMAT_CURRENT_PROJECT_DIRECTORY + WEAVER_IMAGES + weaver.getImageReference());
        return FileCopyUtils.copyToByteArray(file);
    }

    /**
     * get all weaver data
     */
    public AllWeaverData getAllWeaverData(Integer pageNo, Integer pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        PageRequest pageRequest = PageRequest.of(pageNo - 1, pageSize, sort);

        Page<Weaver> weaverPage = weaverRepository.findAll(pageRequest);
        List<Weaver> weaverList = weaverPage.getContent();

        AllWeaverData data = new AllWeaverData();
        data.setPageNo(weaverPage.getNumber() + 1);
        data.setPageSize(weaverPage.getSize());
        data.setWeaverDataList(weaverList.stream()
                .map(el -> mapWeaverModelToSpecificWeaverDataResponse(el)).collect(Collectors.toList()));
        data.setTotalElementOnPage(weaverPage.getNumberOfElements());
        data.setTotalAllElement(weaverPage.getTotalElements());
        data.setTotalPages(weaverPage.getTotalPages());
        data.setLastPage(weaverPage.isLast());

        return data;
    }

    /**
     * mapping weaver model to specific weaver data response
     */
    private SpecificWeaverDataResponse mapWeaverModelToSpecificWeaverDataResponse(Weaver weaver) {
        SpecificWeaverDataResponse response = new SpecificWeaverDataResponse();
        response.setUuid(weaver.getUuid());
        response.setName(weaver.getName());
        response.setEthnic(weaver.getEthnic());
        response.setImageReference(weaver.getImageReference());
        response.setAge(weaver.getAge());
        response.setDomicile(weaver.getDomicile());
        response.setTechnique(weaver.getTechnique());
        response.setStory(weaver.getStory());
        response.setTheLoom(weaver.getTheLoom());
        response.setImageUrl(
                weaver.getImageReference() == null ? null
                        : "http://localhost:8080/api/v1/ulospedia/weavers/" + weaver.getUuid() + "/image");

        return response;
    }

    /**
     * delete weaver data + image
     */
    public void deleteWeaverData(String uuid) {
        Weaver weaver = findWeaverByUuid(uuid);

        // file to deleted
        if (weaver.getImageReference() != null) {
            File fileToDelete = new File(NEW_FORMAT_CURRENT_PROJECT_DIRECTORY + WEAVER_IMAGES + weaver.getImageReference());
            fileToDelete.delete();
        }

        weaverRepository.delete(weaver);
    }

    /**
     * get weaver data by uuid
     */
    public SpecificWeaverDataResponse getWeaverByUuid(String uuid) {
        Weaver weaver = findWeaverByUuid(uuid);
        return mapWeaverModelToSpecificWeaverDataResponse(weaver);
    }

    /**
     * update weaver text data
     */
    public SpecificWeaverDataResponse updateWeaverTextData(String uuid, WeaverTextDataRequest request) {
        Weaver weaver = findWeaverByUuid(uuid);

        weaver.setName(request.getName());
        weaver.setAge(Year.now().getValue() - request.getYearOfBirth());
        weaver.setEthnic(request.getEthnic());
        weaver.setDomicile(request.getDomicile());
        weaver.setTheLoom(request.getTheLoom());
        weaver.setTechnique(request.getTechnique());
        weaver.setStory(request.getStory());

        return mapWeaverModelToSpecificWeaverDataResponse(weaver);
    }

    /**
     * get count of weaver data
     */
    public Long getCountWeaver() {
        return weaverRepository.count();
    }

}
