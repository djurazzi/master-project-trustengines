package com.duricic.domain;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

/**
 * Entity class used for implementing a composed primary key. This primary key
 * is composed of userId and assetId.
 * 
 * @author Tomislav Duricic
 * @version 1.0
 */
@Embeddable
public class UserAssetId implements java.io.Serializable {

	private User user;
	private Asset asset;

	@ManyToOne
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@ManyToOne
	public Asset getAsset() {
		return asset;
	}

	public void setAsset(Asset asset) {
		this.asset = asset;
	}

	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		UserAssetId that = (UserAssetId) o;

		if (user != null ? !user.equals(that.user) : that.user != null)
			return false;
		if (asset != null ? !asset.equals(that.asset) : that.asset != null)
			return false;

		return true;
	}

	public int hashCode() {
		int result;
		result = (user != null ? user.hashCode() : 0);
		result = 31 * result + (asset != null ? asset.hashCode() : 0);
		return result;
	}

}