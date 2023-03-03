package com.ensiklopediaulos.ditenun.services;

import com.ensiklopediaulos.ditenun.dtos.request.UlosRequest;
import com.ensiklopediaulos.ditenun.dtos.request.UlosSize;
import com.ensiklopediaulos.ditenun.dtos.response.UlosIdUuidResponse;
import com.ensiklopediaulos.ditenun.dtos.response.UlosResponse;
import com.ensiklopediaulos.ditenun.models.ensiklopedia.Color;
import com.ensiklopediaulos.ditenun.models.ensiklopedia.Ulos;
import com.ensiklopediaulos.ditenun.repositories.ColorRepository;
import com.ensiklopediaulos.ditenun.repositories.UlosRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UlosService {

    private final ModelMapper modelMapper;

    private final UlosRepository ulosRepository;

    private final ColorRepository colorRepository;

    public UlosService(ModelMapper modelMapper, UlosRepository ulosRepository, ColorRepository colorRepository) {
        this.modelMapper = modelMapper;
        this.ulosRepository = ulosRepository;
        this.colorRepository = colorRepository;
    }

    /**
     * create ulos id and uuid
     */
    public UlosIdUuidResponse getUlosIdUuid() {
        var ulos = new Ulos();
        ulos.setUuid(UUID.randomUUID().toString());
        Ulos newUlos = ulosRepository.save(ulos);

        var ulosIdUuidResponse = new UlosIdUuidResponse();
        ulosIdUuidResponse.setId(newUlos.getId());
        ulosIdUuidResponse.setUuid(newUlos.getUuid());
        return ulosIdUuidResponse;
    }

    /**
     * create ulos data
     */
    public UlosResponse createUlosData(String uuid, UlosRequest ulosRequest) {
        Optional<Ulos> optionalUlos = ulosRepository.findByUuid(uuid);
        if (optionalUlos.isEmpty()) {
            throw new RuntimeException("ULOS NOT FOUND!");
        }
        Ulos foundedUlos = optionalUlos.get();

        modelMapper.map(ulosRequest, optionalUlos.get());
        optionalUlos.get().setLength(ulosRequest.getSize().getLength());
        optionalUlos.get().setWidth(ulosRequest.getSize().getWidth());

        List<Color> colors = new ArrayList<>();

        for (var id : ulosRequest.getColors()) {
            System.out.println(id);
            Color color = colorRepository.findById(id).orElseThrow(() -> new RuntimeException("NOT FOUND"));
            colors.add(color);
        }
        optionalUlos.get().setColors(colors);

        for (var id : ulosRequest.getColors()) {
            Color color = colorRepository.findById(id).orElseThrow(() -> new RuntimeException("NOT FOUND"));
            color.getUlos().add(foundedUlos);
            colorRepository.save(color);
        }

        // Ulos updatedUlos = ulosRepository.save(optionalUlos.get());
        Ulos updatedUlos = saveUlos(optionalUlos.get());

        UlosResponse response = new UlosResponse();
        modelMapper.map(updatedUlos, response);
        response.setSize(new UlosSize(updatedUlos.getLength(), updatedUlos.getWidth()));

        List<String> listOfColors = new ArrayList<>();

        for (var color : updatedUlos.getColors()) {
            listOfColors.add(color.getColor());
        }
        response.setColors(listOfColors);

        return response;
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
            throw new RuntimeException("NOT FOUND!");
        }
        return optionalUlos.get();
    }

    /**
     * mapping
     */

}
