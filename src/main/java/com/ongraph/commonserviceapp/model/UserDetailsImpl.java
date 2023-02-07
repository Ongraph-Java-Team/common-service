package com.ongraph.commonserviceapp.model;

import java.util.Collection;
import java.util.Objects;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(value = {"credentialsNonExpired","accountNonExpired","accountNonLocked","authorities"})
public class UserDetailsImpl implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private UUID id;
	
	private String username;
	
	private String email;
	
	@JsonIgnore
	private String password;

	@JsonIgnore
	private Collection<? extends GrantedAuthority> authorites;

	@JsonIgnore
	private boolean enabled;
	
	public UserDetailsImpl(UUID id,String username,String email,String password,
			boolean enabled,
			Collection<? extends GrantedAuthority> authorities) {
		this.id=id;
		this.username=username;
		this.email=email;
		this.password=password;
		this.enabled=enabled;
		this.authorites=authorities;
	}
	
	public static UserDetailsImpl build(com.ongraph.commonserviceapp.model.UserDetails userDetails) {
		var authorities=userDetails.getRoles().stream()
				.map(role->new SimpleGrantedAuthority(role.name()))
				.toList();
		
		return new UserDetailsImpl(userDetails.getId(),
				userDetails.getUserName(),
				userDetails.getEmail(),
				userDetails.getPassword(),
				userDetails.isEnabled(),
				authorities);
	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorites;
	}
	
	public UUID getId() {
		return id;
	}
	
	public String getEmail() {
		return email;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this==obj)
			return true;
		if(obj==null || getClass()!=obj.getClass())
			return false;
		
		var userDetails=(UserDetailsImpl)obj;
		return Objects.equals(this.id, userDetails.id);	
	}
	
	@Override
	public int hashCode() {
		return super.hashCode();
	}

}
