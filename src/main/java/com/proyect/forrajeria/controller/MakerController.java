package com.proyect.forrajeria.controller;

import com.proyect.forrajeria.entity.Maker;
import com.proyect.forrajeria.service.IMakerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/forrajeria/maker")
public class MakerController {

    @Autowired
    private IMakerService makerService;

    @GetMapping("/find/{id}")
    public ResponseEntity<?> findMakerById(@PathVariable Long id) {
        Optional<Maker> makerOptional = makerService.findMakerById(id);
        if (makerOptional.isPresent()) {
            Maker maker = makerOptional.get();
            return ResponseEntity.ok(maker);
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/findAll")
    public ResponseEntity<?> findAllMakers() {
        List<Maker> makerList = makerService.findAllMaker();
        return ResponseEntity.ok(makerList);
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveMaker(@RequestBody Maker maker) throws URISyntaxException {
        if (maker.getMakerName().isBlank()) {
            return ResponseEntity.badRequest().build();
        } else {
            makerService.saveMaker(maker);
        }

        return ResponseEntity.created(new URI("/forrajeria/maker/save")).build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteMaker(@PathVariable Long id) {
        if (id != null) {
            makerService.deleteMaker(id);
            return ResponseEntity.ok("Registro Eliminado");
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?>updateMaker(@PathVariable Long id, @RequestBody String newMakerName){
        Optional<Maker>makerOptional=makerService.findMakerById(id);
        if (makerOptional.isPresent()){
            Maker maker=makerOptional.get();
            maker.setMakerName(newMakerName);
            makerService.saveMaker(maker);
            return ResponseEntity.ok("Datos Actualizados");
        }
        return ResponseEntity.notFound().build();
    }

}
