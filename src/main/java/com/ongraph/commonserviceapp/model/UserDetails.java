package com.ongraph.commonserviceapp.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDetails implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Data
	@Builder
	public static class UserLoginHistoryDetails implements Serializable{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		private LocalDateTime logInAttemptTime;
		
		private String ipAddress;
		
		private boolean loggedIn;
	}
	
	private UUID id;

	private String firstName;

	private String lastName;
	
	@JsonIgnore
	private String password;

	private String userName;

	private String email;

	private String phoneNo;

	private boolean enabled;

	@Builder.Default
	private Set<UserRoles> roles = new HashSet<>();

	@Builder.Default
	private Set<UserLoginHistoryDetails> loginHistoryList=new HashSet<>();
}
