package com.proyect.forrajeria.service;

import com.proyect.forrajeria.entity.Maker;

import java.util.List;
import java.util.Optional;

public interface IMakerService {

    public List<Maker> findAllMaker();
    public Optional<Maker> findMakerById(Long id);
    public void saveMaker(Maker maker);
    public void updateMaker(Long id , String newName);
    public void deleteMaker(Long id);

}
