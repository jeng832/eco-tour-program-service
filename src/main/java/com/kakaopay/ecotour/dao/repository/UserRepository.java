package com.kakaopay.ecotour.dao.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kakaopay.ecotour.dao.entity.User;

public interface UserRepository  extends JpaRepository<User, Long> {
	Optional<User> findByUserId(String userId);
}
