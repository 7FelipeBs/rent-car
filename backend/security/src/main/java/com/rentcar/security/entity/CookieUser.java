package com.rentcar.security.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cookie_user")
public class CookieUser {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "cookie_user_id")
	private long id;

	@Column(name = "refresh_token_guid", nullable = false, unique = true)
	private String refreshTokenGuid;

	@Column(name = "cookie_token_guid", nullable = false, unique = true)
	private String cookieTokenGuid;

	@Column(name = "user_id", nullable = false, unique = true)
	private long userId;

	@Column(nullable = false, name = "expire_date_cookie")
	private LocalDateTime expireDateCookie;

	public CookieUser(String refreshTokenGuid, String cookieTokenGuid, LocalDateTime expireDateCookie, long userId) {
		this.refreshTokenGuid = refreshTokenGuid;
		this.cookieTokenGuid = cookieTokenGuid;
		this.expireDateCookie = expireDateCookie;
		this.userId = userId;
	}

}
