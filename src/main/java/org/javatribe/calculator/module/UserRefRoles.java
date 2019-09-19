package org.javatribe.calculator.module;


import java.util.List;

public class UserRefRoles extends TUsers {
	
	private static final long serialVersionUID = 1L;
	
	private List<TRoles> roles;

	public List<TRoles> getRoles() {
		return roles;
	}

	public void setRoles(List<TRoles> roles) {
		this.roles = roles;
	}
	
	
}
