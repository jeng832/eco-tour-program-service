package com.kakaopay.ecotour.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kakaopay.ecotour.dao.repository.UserRepository;
import com.kakaopay.ecotour.exception.UserNotFoundException;
import com.kakaopay.ecotour.service.UserDetailService;

@Service
public class UserDetailServiceImpl implements UserDetailService {

	@Autowired
	UserRepository userRepo;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepo.findByUserId(username).orElseThrow(() -> new UserNotFoundException());
	}

}
