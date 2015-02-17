package com.duricic.domain;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Entity class used for implementing and mapping a UserAsset object to
 * user_asset table in the database.
 * 
 * @author Tomislav Duricic
 * @version 1.0
 */
@Entity
@Table(name = "user_asset", catalog = "mydb")
@AssociationOverrides({
		@AssociationOverride(name = "pk.user", joinColumns = @JoinColumn(name = "USER_ID")),
		@AssociationOverride(name = "pk.asset", joinColumns = @JoinColumn(name = "ASSET_ID")) })
public class UserAsset implements java.io.Serializable {

	private UserAssetId pk = new UserAssetId();
	private Integer rating;

	public UserAsset() {
	}

	@EmbeddedId
	public UserAssetId getPk() {
		return pk;
	}

	public void setPk(UserAssetId pk) {
		this.pk = pk;
	}

	@Transient
	public User getUser() {
		return getPk().getUser();
	}

	public void setUser(User user) {
		getPk().setUser(user);
	}

	@Transient
	public Asset getAsset() {
		return getPk().getAsset();
	}

	public void setAsset(Asset asset) {
		getPk().setAsset(asset);
	}

	@Column(name = "RATING", nullable = false)
	public Integer getRating() {
		return this.rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		UserAsset that = (UserAsset) o;

		/*
		 * if (getAsset().getAssetId().equals(that.getAsset().getAssetId())){
		 * return true; }
		 */

		if (getPk() != null ? !getPk().equals(that.getPk())
				: that.getPk() != null)
			return false;

		return false;
	}

	public int hashCode() {
		return (getPk() != null ? getPk().hashCode() : 0);
	}
}