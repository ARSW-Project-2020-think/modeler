package com.modeler.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.modeler.repositories.UserRepository;
@Service
public class UserServices {
	@Autowired
	private UserRepository repo;
}
