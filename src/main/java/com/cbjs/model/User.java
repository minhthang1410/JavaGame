package com.cbjs.model;

public class User {
	private String id;
	private String username;
	private String password;
	private String email;
	private String bio;
	private int money;
	private String creditCard;
	private String avatar;
	private String role;
	private boolean kyc;
	
	public User() {
		
	}
	
	public User(String username, String password) {
	    this.username = username;
	    this.password = password;
	}
	
	public User(String id, String username, int money) {
		this.id = id;
		this.username = username;
		this.money = money;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
	    return username;
	}
	public void setUsername(String username) {
	    this.username = username;
	}
	public String getPassword() {
	    return password;
	}
	public void setPassword(String password) {
	    this.password = password;
	}
	public String getEmail() {
	    return email;
	}
	public void setEmail(String email) {
	    this.email = email;
	}
	public String getBio() {
	    return bio;
	}
	public void setBio(String bio) {
	    this.bio = bio;
	}
	public int getMoney() {
		return money;
	}
	public void setMoney(int money) {
		this.money = money;
	}
	public String getCreditCard() {
		return creditCard;
	}
	public void setCreditCard(String creditCard) {
		this.creditCard = creditCard;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public boolean isKyc() {
		return kyc;
	}
	public void setKyc(boolean kyc) {
		this.kyc = kyc;
	}
}
