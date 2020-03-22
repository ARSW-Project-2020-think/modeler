package com.modeler.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.modeler.model.Version;
import com.modeler.repositories.VersionRepository;

@Service
public class VersionServices {
	@Autowired
	private VersionRepository repo;
	
	public void save(Version v) {
		repo.save(v);
	}
}
