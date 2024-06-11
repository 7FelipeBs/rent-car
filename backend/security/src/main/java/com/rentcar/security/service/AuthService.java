package com.rentcar.security.service;

import com.rentcar.security.dto.MessageResponseDto;
import com.rentcar.security.dto.UserDetailsDto;
import com.rentcar.security.dto.login.LoginRequestDto;
import com.rentcar.security.dto.login.SignupRequestDto;
import com.rentcar.security.entity.CookieUser;
import com.rentcar.security.entity.RefreshToken;
import com.rentcar.security.entity.User;
import com.rentcar.security.repository.IUserRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import util.JwtUtil;

import java.time.LocalDateTime;

@Service
public class AuthService {

	private static final Logger log = LoggerFactory.getLogger(AuthService.class);

	private final AuthenticationManager authenticationManager;
	private final IUserRepository userRepository;

	private final PasswordEncoder encoder;
	private final RefreshTokenService refreshTokenService;
	private final CookieUsersService cookieUsersService;

	// private final @NonNull IRoleRepository rolesRepository;

	private final JwtUtil jwtUtil;

	public AuthService(
			AuthenticationManager authenticationManager,
			JwtUtil jwtUtil,
			IUserRepository userRepository,
			//IRolesRepository rolesRepository,
			PasswordEncoder encoder,
			RefreshTokenService refreshTokenService,
			CookieUsersService cookieUsersService) {

		this.userRepository = userRepository;
		//this.rolesRepository = rolesRepository;

		this.refreshTokenService = refreshTokenService;
		this.cookieUsersService = cookieUsersService;

		this.authenticationManager = authenticationManager;
		this.jwtUtil = jwtUtil;
		this.encoder = encoder;
	}

	@Transactional
	public ResponseEntity<?> signup(SignupRequestDto signUpRequest) {
		if (Boolean.TRUE.equals(userRepository.existsByUsername(signUpRequest.getUsername()))) {
			return ResponseEntity.badRequest().body(new MessageResponseDto("Error: Username is already taken!"));
		}

		if (Boolean.TRUE.equals(userRepository.existsByEmail(signUpRequest.getEmail()))) {
			return ResponseEntity.badRequest().body(new MessageResponseDto("Error: Email is already in use!"));
		}

		User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(), encoder.encode(signUpRequest.getPassword()));

//		Set<Roles> roles = new HashSet<>();
//		Roles userRole = rolesRepository.findByName(ERole.ROLE_USER).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//		roles.add(userRole);
//
//		user.setRoles(roles);
		userRepository.save(user);

		return ResponseEntity.ok(new MessageResponseDto("User registered successfully!"));
	}

	@Transactional
	public ResponseEntity<?> signin(LoginRequestDto loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);

		UserDetailsDto userDetails = (UserDetailsDto) authentication.getPrincipal();
		
		deleteTokens(userDetails.getId());
		ResponseCookie jwtCookie = jwtUtil.generateJwtCookie(userDetails);

		RefreshToken refreshToken = refreshTokenService.create(userDetails.getId());

		var refreshTokenCookie = jwtUtil.generateRefreshJwtCookie(refreshToken.getToken());

		var cryptEncoder = new BCryptPasswordEncoder();
		cookieUsersService
				.create(new CookieUser(refreshTokenCookie.getValue(), cryptEncoder.encode(jwtCookie.getValue()),
						LocalDateTime.now().plus(jwtCookie.getMaxAge()), userDetails.getId()));
		
		var tokenCookies = refreshToken.getToken().concat(";").concat(refreshToken.getExpiryDate().toString());
		return ResponseEntity.ok().body(tokenCookies);
	}

	@Transactional
	public ResponseEntity<?> logoutUser(String token) {
		String msg = "You've been signed out!";
		try {
			var entity = refreshTokenService.findByToken(token.substring(1, token.length() - 1));
			if (entity == null) {
				return ResponseEntity.ok().body(new MessageResponseDto(msg));
			}
			
			deleteTokens(entity.getUser().getId());

		} catch (Exception e) {
			log.debug(e.getMessage());
		}

		return ResponseEntity.ok().body(new MessageResponseDto(msg));
	}
	
	
	void deleteTokens(Long idUser) {
		refreshTokenService.deleteByUserId(idUser);
		cookieUsersService.deleteByUser(idUser);
	}
	
	

	@Transactional
	public ResponseEntity<?> refreshtoken(String token) {
		try {
			if (token == null) return null;

			RefreshToken refresh = refreshTokenService.findByToken(token.substring(1, token.length() - 1));

			if (refresh == null) return null;

			// valid date expiration token
			refreshTokenService.verifyExpiration(refresh);

			// if refresh is valid, generate new cookie token
			var cookiesJwt = jwtUtil.generateJwtCookie(refresh.getUser());

			// update refresh together with cookieUser
			var oldToken = refresh.getToken();
			refresh = refreshTokenService.update(refresh);
			cookieUsersService.update(refresh, oldToken, cookiesJwt);
			

			var tokenCookies = refresh.getToken().concat(";").concat(refresh.getExpiryDate().toString());
			return ResponseEntity.ok().body(tokenCookies);
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		return ResponseEntity.badRequest()
				.body(new MessageResponseDto("Refresh Token is invalid! Please make a signin!"));
	}

}
