package com.SharedMusic.Shared.repository;

import java.util.Optional; 

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.SharedMusic.Shared.entity.Cancion;

@Repository
public interface CancionRepository extends JpaRepository<Cancion, Integer>{
	Optional<Cancion> findByNombre(String nombre);
    boolean existsByNombre(String nombre);
}
