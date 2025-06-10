package com.cts.CBLOS.service;
 
import com.cts.CBLOS.model.User;

import com.cts.CBLOS.model.User.UserRole; // <--- ADD THIS IMPORT

import com.cts.CBLOS.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
 
@Service

public class UserServiceImpl implements UserService {
 
	@Autowired
	private UserRepository userRepository;
 
	@Override
	public void saveUser(User user) {

		userRepository.save(user);

		}
 
	@Override
	public boolean isAdmin(String email) {
			return userRepository.findByEmail(email)
					.map(user -> user.getRole() == UserRole.ADMIN)
				.orElse(false);
		}

	@Override
	public User findByEmail(String email) {

		return userRepository.findByEmail(email).orElse(null);

	}

}
 