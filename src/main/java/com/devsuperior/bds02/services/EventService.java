package com.devsuperior.bds02.services;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.bds02.dto.EventDTO;
import com.devsuperior.bds02.entities.City;
import com.devsuperior.bds02.repositories.EventRepository;

@Service
public class EventService {

	@Autowired
	private EventRepository eventRepository;
	
	@Transactional
	public EventDTO update(Long id, EventDTO eventDTO) {
		try {
			var event = this.eventRepository.getOne(id);
			
			event.setId(id);
			event.setName(eventDTO.getName());
			event.setDate(eventDTO.getDate());
			event.setUrl(eventDTO.getUrl());
			
			var city = new City();
			
			city.setId(eventDTO.getCityId());
			
			event.setCity(city);
			
			event = this.eventRepository.save(event);
			
			return new EventDTO(event);
		} catch (EntityNotFoundException e) {
			throw new EntityNotFoundException("Entity not found. Id: " + id);
		}
	}
	
}
