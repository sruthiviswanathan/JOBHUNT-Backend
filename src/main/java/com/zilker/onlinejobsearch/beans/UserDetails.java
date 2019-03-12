package com.zilker.onlinejobsearch.beans;

import java.util.ArrayList;

public class UserDetails {

	private ArrayList<UserTechnologyMapping> userTechnology;
	private ArrayList<User> user;
	
	public ArrayList<UserTechnologyMapping> getUserTechnology() {
		return userTechnology;
	}
	public void setUserTechnology(ArrayList<UserTechnologyMapping> userTechnology) {
		this.userTechnology = userTechnology;
	}
	public ArrayList<User> getUser() {
		return user;
	}
	public void setUser(ArrayList<User> user) {
		this.user = user;
	}
	
	
}
