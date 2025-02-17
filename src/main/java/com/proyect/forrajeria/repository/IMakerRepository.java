package com.proyect.forrajeria.repository;

import com.proyect.forrajeria.entity.Maker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMakerRepository extends JpaRepository<Maker,Long> {
}
