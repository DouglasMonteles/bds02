package com.devsuperior.bds02.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.bds02.dto.CityDTO;
import com.devsuperior.bds02.entities.City;
import com.devsuperior.bds02.repositories.CityRepository;
import com.devsuperior.bds02.services.exceptions.DatabaseException;
import com.devsuperior.bds02.services.exceptions.ResourceNotFoundException;

@Service
public class CityService {

	@Autowired
	private CityRepository cityRepository;
	
	@Transactional(readOnly = true)
	public List<CityDTO> findAllSortedByName() {
		var cities = this.cityRepository.findAll(Sort.by("name"));
		var citiesDTO = cities.stream()
				.map(city -> new CityDTO(city))
				.collect(Collectors.toList());
		
		return citiesDTO;
	}
	
	@Transactional
	public CityDTO insert(CityDTO cityDTO) {
		var city = new City();
		
		city.setId(null);
		city.setName(cityDTO.getName());
		
		city = this.cityRepository.save(city);
		
		return new CityDTO(city);
	}
	
	public void delete(Long id) {
		try {
			this.cityRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id not exists: " + id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}
	}
	
}
