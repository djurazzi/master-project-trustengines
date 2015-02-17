package com.duricic.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;

import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Entity class used for implementing a User object and mapping it to user
 * table in the database.
 * 
 * @author Tomislav Duricic
 * @version 1.0
 */
@Entity
@Table(name = "user", catalog = "mydb", uniqueConstraints = { @UniqueConstraint(columnNames = "USER_NAME") })
public class User implements java.io.Serializable {

	private Integer userId;
	private String userName;
	private Set<UserAsset> userAssets = new HashSet<UserAsset>(0);

	public User() {
	}

	public User(String userName) {
		this.userName = userName;
	}

	public User(Integer userId, String userName) {
		this.userId = userId;
		this.userName = userName;
	}

	public User(String userName, Set<UserAsset> userAssets) {
		this.userName = userName;
		this.userAssets = userAssets;
	}

	@Id
	@Column(name = "USER_ID", unique = true, nullable = false)
	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Column(name = "USER_NAME", unique = true, nullable = false, length = 20)
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.user", cascade = CascadeType.ALL)
	public Set<UserAsset> getUserAssets() {
		return this.userAssets;
	}

	public void setUserAssets(Set<UserAsset> userAssets) {
		this.userAssets = userAssets;
	}

}