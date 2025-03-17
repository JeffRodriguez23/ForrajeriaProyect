package com.proyect.forrajeria.repository;

import com.proyect.forrajeria.entity.Maker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IMakerRepository extends JpaRepository<Maker,Long> {

   /* @Query("SELECT m FROM Maker m WHERE m.makerName = :makerName")
    Optional<Maker> findMakerByName(@Param("makerName") String makerName);*/

    //Metodo para buscar un maker  ingresando una vocal u letra
    @Query("SELECT m FROM Maker m WHERE LOWER(m.makerName) LIKE LOWER(CONCAT('%', :letter, '%'))")
    List<Maker> findMakerByLetter(@Param("letter") String letter);

}
