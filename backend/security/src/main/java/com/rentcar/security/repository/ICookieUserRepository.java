package com.rentcar.security.repository;

import com.rentcar.security.entity.CookieUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ICookieUserRepository extends JpaRepository<CookieUser, Long> {

	@Query("FROM CookieUser WHERE refreshTokenGuid = :refreshTokenGuid")
	CookieUser findByToken(String refreshTokenGuid);

	@Modifying
	@Transactional
	@Query("DELETE FROM CookieUser WHERE id = :id")
	void deleteCookieById(Long id);

	@Modifying
	@Query("DELETE FROM CookieUser WHERE userId = :userId")
	void deleteByUser(Long userId);
}