package com.duricic.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Entity class used for implementing an Asset object and mapping it to asset
 * table in the database.
 * 
 * @author Tomislav Duricic
 * @version 1.0
 */
@Entity
@Table(name = "asset", catalog = "mydb")
public class Asset implements java.io.Serializable {

	private Integer assetId;
	private String assetName;
	private Set<UserAsset> userAssets = new HashSet<UserAsset>(0);

	public Asset() {
	}

	public Asset(String assetName) {
		this.assetName = assetName;
	}

	public Asset(Integer assetId, String assetName) {
		this.assetId = assetId;
		this.assetName = assetName;
	}

	public Asset(String assetName, Set<UserAsset> userAssets) {
		this.assetName = assetName;
		this.userAssets = userAssets;
	}

	@Id
	@Column(name = "ASSET_ID", unique = true, nullable = false)
	public Integer getAssetId() {
		return this.assetId;
	}

	public void setAssetId(Integer assetId) {
		this.assetId = assetId;
	}

	@Column(name = "ASSET_NAME", nullable = false, length = 10)
	public String getAssetName() {
		return this.assetName;
	}

	public void setAssetName(String assetName) {
		this.assetName = assetName;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.asset")
	public Set<UserAsset> getUserAssets() {
		return this.userAssets;
	}

	public void setUserAssets(Set<UserAsset> userAssets) {
		this.userAssets = userAssets;
	}

}