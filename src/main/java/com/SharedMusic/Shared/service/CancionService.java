package com.SharedMusic.Shared.service;

import javax.transaction.Transactional; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

import com.SharedMusic.Shared.entity.Cancion;
import com.SharedMusic.Shared.repository.CancionRepository;

@Service
@Transactional
public class CancionService {
	
	@Autowired
	CancionRepository cancionRepository;

	public List<Cancion> list() {
		return cancionRepository.findAll();
	}

	public Optional<Cancion> getOne(int id) {
		return cancionRepository.findById(id);
	}

	public Optional<Cancion> getByNombre(String nombre) {
		return cancionRepository.findByNombre(nombre);
	}

	public void save(Cancion cancion) {
		cancionRepository.save(cancion);
	}

	public void delete(int id) {
		cancionRepository.deleteById(id);
	}

	public boolean existsById(int id) {
		return cancionRepository.existsById(id);
	}

	public boolean existsByNombre(String nombre) {
		return cancionRepository.existsByNombre(nombre);
	}

}
