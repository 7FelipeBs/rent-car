package com.rentcar.security.service;

import com.rentcar.security.entity.CookieUser;
import com.rentcar.security.entity.RefreshToken;
import com.rentcar.security.repository.ICookieUserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CookieUsersService {

	@Value("${security.app.jwtRefreshExpirationMs}")
	private Long refreshTokenDurationMs;

	private final ICookieUserRepository cookieUserRepository;
	private final PasswordEncoder cryptEncoder;
	
	public CookieUsersService(ICookieUserRepository cookieUserRepository, PasswordEncoder cryptEncoder) {
		this.cookieUserRepository = cookieUserRepository;
		this.cryptEncoder = cryptEncoder;
	}
	
	public CookieUser create(CookieUser entity) {
		return cookieUserRepository.save(entity);
	}
	
	public CookieUser update(RefreshToken refreshToken, String oldToken, ResponseCookie cookie) {
		
		var cookieUser = cookieUserRepository.findByToken(oldToken);
		if(cookieUser != null) {
			
			if(cookieUser.getExpireDateCookie().isBefore(LocalDateTime.now())) {
				cookieUser.setExpireDateCookie(LocalDateTime.now().plus(cookie.getMaxAge()));
				cookieUser.setCookieTokenGuid(cryptEncoder.encode(cookie.getValue()));
			} 
			
			cookieUser.setRefreshTokenGuid(refreshToken.getToken());
			return cookieUserRepository.save(cookieUser);
		}
		
		return null;
	}
	


	@Transactional
	public void deleteById(Long id) {
		if(cookieUserRepository.findById(id).isPresent()) cookieUserRepository.deleteCookieById(id);
	}

	@Transactional
	public void deleteByUser(Long userId) {
		cookieUserRepository.deleteByUser(userId);
	}
}
