package com.proyect.forrajeria.service.impl;

import com.proyect.forrajeria.entity.Maker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.proyect.forrajeria.repository.IMakerRepository;
import com.proyect.forrajeria.service.IMakerService;

import java.util.List;
import java.util.Optional;

@Service
public class MakerService implements IMakerService {

    @Autowired
    private IMakerRepository makerRepository;


    @Override
    public List<Maker> findAllMaker() {
        List<Maker> makerList = makerRepository.findAll();
        return makerList;
    }

    @Override
    public Optional<Maker> findMakerById(Long id) {
        return makerRepository.findById(id);
    }

    @Override
    public void saveMaker(Maker maker) {
        makerRepository.save(maker);
    }

    @Override
    public void updateMaker(Long id, String newMakerName) {
        Maker maker = this.findMakerById(id).orElse(null);
        maker.setMakerName(newMakerName);
        makerRepository.save(maker);
    }


    @Override
    public void deleteMaker(Long id) {
        makerRepository.deleteById(id);
    }
}
