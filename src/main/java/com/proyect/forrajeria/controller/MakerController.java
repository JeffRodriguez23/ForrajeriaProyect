package com.proyect.forrajeria.controller;

import com.proyect.forrajeria.configuration.ModelMapperConfig;
import com.proyect.forrajeria.dto.MakerCustomDTO;
import com.proyect.forrajeria.dto.MakerDTO;
import com.proyect.forrajeria.entity.Maker;
import com.proyect.forrajeria.service.IMakerService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/forrajeria/maker")
public class MakerController {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private IMakerService makerService;


    //traer registro de un maker atravez del id
    @GetMapping("/find/{id}")
    public ResponseEntity<MakerCustomDTO> findMakerById(@PathVariable Long id) {
        Optional<Maker> makerOptional = makerService.findMakerById(id);
        if (makerOptional.isPresent()) {
            Maker maker = makerOptional.get();
            TypeMap<Maker, MakerCustomDTO> makerDto = modelMapper.typeMap(Maker.class, MakerCustomDTO.class)
                    .addMappings(mapper -> {
                        mapper.map(Maker::getMakerName, MakerCustomDTO::setMakerName);
                        mapper.map(src -> src.getProductList(), MakerCustomDTO::setProductDTOList);
                    });
            MakerCustomDTO makerCustomDTO = modelMapper.map(maker, MakerCustomDTO.class);

            return ResponseEntity.ok(makerCustomDTO);
        }
        return ResponseEntity.notFound().build();
    }

    //Traer todos los registros de maker
    @GetMapping("/findAll")
    public ResponseEntity<?> findAllMakers() {
        List<MakerCustomDTO> makerCustomDTOList = makerService.findAllMaker().stream()
                .map(maker -> {
                    TypeMap<Maker, MakerCustomDTO> makerCustomList = modelMapper
                            .typeMap(Maker.class, MakerCustomDTO.class)
                            .addMappings(mapper -> {
                                mapper.map(src -> src.getProductList(), MakerCustomDTO::setProductDTOList);
                            });
                    MakerCustomDTO makerCustomDTO = modelMapper.map(maker, MakerCustomDTO.class);
                    return makerCustomDTO;
                }).collect(Collectors.toList());

        return ResponseEntity.ok(makerCustomDTOList);
    }

    //Crear un maker
    @PostMapping("/create")
    public ResponseEntity<?> createMaker(@RequestBody MakerDTO makerDto) throws URISyntaxException {
        if (makerDto.getMakerName().isBlank()) {
            return ResponseEntity.badRequest().build();
        } else {
            Maker maker = modelMapper.map(makerDto, Maker.class);
            makerService.saveMaker(maker);
        }

        return ResponseEntity.created(new URI("/forrajeria/maker/create")).build();
    }

    //Eliminar un Registro de maker
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteMaker(@PathVariable Long id) {
        if (id != null) {
            makerService.deleteMaker(id);
            return ResponseEntity.ok("Registro Eliminado");
        }
        return ResponseEntity.badRequest().build();
    }

    //Actualizar registro de maker
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateMaker(@PathVariable Long id, @RequestBody MakerDTO makerDTO) {
        Optional<Maker> makerOptional = makerService.findMakerById(id);
        if (makerOptional.isPresent()) {
            Maker maker = makerOptional.get();
            maker.setMakerName(makerDTO.getMakerName());
            makerService.saveMaker(maker);
            return ResponseEntity.ok("Datos Actualizados");
        }
        return ResponseEntity.notFound().build();
    }

    //Endpoint Para solicitar un maker atravez de una letra u vocal
    @GetMapping("/searchmaker/{letter}")
    public ResponseEntity<List<MakerCustomDTO>> findMakerByName(@PathVariable String letter) {
            List<MakerCustomDTO> makerCustomDTOList = makerService.findMakerByLetter(letter).stream()
                    .map(maker -> {
                        TypeMap<Maker, MakerCustomDTO> makerCustomList = modelMapper
                                .typeMap(Maker.class, MakerCustomDTO.class)
                                .addMappings(mapper -> {
                                    mapper.map(src -> src.getProductList(), MakerCustomDTO::setProductDTOList);
                                });
                        MakerCustomDTO makerCustomDTO = modelMapper.map(maker, MakerCustomDTO.class);
                        return makerCustomDTO;
                    }).collect(Collectors.toList());

        return ResponseEntity.ok( makerCustomDTOList);

    }


}


