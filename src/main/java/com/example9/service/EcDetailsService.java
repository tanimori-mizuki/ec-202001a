package com.example9.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example9.domain.LoginUser;
import com.example9.domain.User;
import com.example9.repository.UserRepository;

@Service
public class EcDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		try {
			User user = userRepository.findByEmail(email);
			return new LoginUser(user);
		} catch (Exception e) {
			throw new UsernameNotFoundException("not found : " + email);
		}

	}

}