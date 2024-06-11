package com.rentcar.security.repository;

import com.rentcar.security.entity.RefreshToken;
import com.rentcar.security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface IRefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
	
	
	@Query("FROM RefreshToken WHERE token = :token")
	RefreshToken findByToken(String token);

	@Modifying
	@Transactional
	void deleteByUser(User user);

	@Modifying
	@Query("DELETE FROM RefreshToken WHERE token = :token")
	void deleteByToken(String token);
}