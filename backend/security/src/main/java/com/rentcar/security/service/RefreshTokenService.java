package com.rentcar.security.service;

import com.rentcar.security.entity.RefreshToken;
import com.rentcar.security.exception.TokenRefreshException;
import com.rentcar.security.repository.IRefreshTokenRepository;
import com.rentcar.security.repository.IUserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class RefreshTokenService {

	@Value("${security.app.jwtRefreshExpirationMs}")
	private Long refreshTokenDurationMs;

	private final IRefreshTokenRepository refreshTokenRepository;
	private final IUserRepository userRepository;
	private final CookieUsersService cookieUsersService;
	
	public RefreshTokenService(
			IUserRepository userRepository,
			IRefreshTokenRepository refreshTokenRepository,
			CookieUsersService cookieUsersService) {

		this.userRepository = userRepository;
		this.refreshTokenRepository = refreshTokenRepository;
		this.cookieUsersService = cookieUsersService;
	}

	public RefreshToken findByToken(String token) {
		return refreshTokenRepository.findByToken(token);
	}

	public RefreshToken create(Long userId) {
		RefreshToken refreshToken = new RefreshToken();

		var users = userRepository.findById(userId).orElse(null);

		if (users == null)
			return null;

		refreshToken.setUser(users);
		refreshToken.setExpiryDate(LocalDateTime.now().plusSeconds(refreshTokenDurationMs));
		refreshToken.setToken(UUID.randomUUID().toString());

		return refreshTokenRepository.save(refreshToken);
	}
	
	public RefreshToken update(RefreshToken refreshToken) {
		if(refreshToken.getId() == null)  {
			throw new TokenRefreshException(refreshToken.getToken(),
					"Error Refresh token. Please make a new signin request!");
		}
		
		refreshToken.setExpiryDate(LocalDateTime.now().plusSeconds(refreshTokenDurationMs));
		refreshToken.setToken(UUID.randomUUID().toString());

		return refreshTokenRepository.save(refreshToken);
	}

	public void verifyExpiration(RefreshToken entity) {
		if (entity.getExpiryDate().isBefore(LocalDateTime.now())) {
			
			cookieUsersService.deleteByUser(entity.getUser().getId());
			refreshTokenRepository.deleteById(entity.getId());
			
			throw new TokenRefreshException(entity.getToken(),
					"Refresh token was expired. Please make a new signin request!");
		}
	}

	@Transactional
	public void deleteByUserId(Long userId) {
		userRepository.findById(userId).ifPresent(refreshTokenRepository::deleteByUser);
	}
}
