package com.guru.login.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.guru.login.model.Lead;
import com.guru.login.repository.LeadRepository;

@Service
public class LeadService {
	@Autowired
	private LeadRepository leadRepository;

	public Lead saveLead(Lead lead) {
		return leadRepository.save(lead);
	}
	
	public List<Lead> getAllLead() {
		return leadRepository.findAll();
	}

	public Object findAll(PageRequest of) {
		return leadRepository.findAll(of);
	}
	

}