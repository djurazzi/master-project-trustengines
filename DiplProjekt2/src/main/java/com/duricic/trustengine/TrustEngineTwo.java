package com.duricic.trustengine;

import com.duricic.domain.User;

/**
 * This class implements the algorithm of calculating the trust implicitly
 * between two users based on the assets they've rated. This algorithm is
 * described in the article "Trust-based Collaborative Filtering" published by
 * Neal Lathia, Stephen Hailes and Licia Capra
 * 
 * @author Tomislav Duricic
 * @version 1.0
 */
public class TrustEngineTwo extends AbstractTrustEngine {

	public TrustEngineTwo() {
		super();
	}

	public TrustEngineTwo(User user1, User user2) {
		super(user1, user2);
	}

	/**
	 * This method calculates trust between two users based on given variables
	 * and returns it as a double value.
	 * 
	 * @return
	 */
	@Override
	public double calculateTrust() {
		final int MAX_RATING_VALUE = 5;
		Integer itemRatingUser1;
		Integer itemRatingUser2;
		double tempValue;
		double tempSum = 0;
		int numOfRatedItemsUser1 = assetsRatingsUser1.size();

		for (int assetId : assetsRatingsUser1.keySet()) {
			itemRatingUser1 = assetsRatingsUser1.get(assetId);
			itemRatingUser2 = assetsRatingsUser2.get(assetId);
			if (itemRatingUser2.equals(null)) {
				itemRatingUser2 = 0;
			}
			tempValue = ((double)(-1) / MAX_RATING_VALUE)
					* Math.abs(itemRatingUser1 - itemRatingUser2) + 1;
			tempSum += tempValue;
		}

		return tempSum / numOfRatedItemsUser1;
	}

}
