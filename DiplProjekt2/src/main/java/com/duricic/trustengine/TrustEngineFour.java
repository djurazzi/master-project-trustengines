package com.duricic.trustengine;

import com.duricic.domain.User;

/**
 * This class implements the algorithm of calculating the trust implicitly
 * between two users based on the assets they've rated. This algorithm is
 * described in the article
 * "A trust-semantic fusion-based recommendation approach for e-business applications"
 * published by Qusai Shambour and Jie Lu
 * 
 * @author Tomislav Duricic
 * @version 1.0
 */
public class TrustEngineFour extends AbstractTrustEngine {

	public TrustEngineFour() {
		super();
	}

	public TrustEngineFour(User user1, User user2) {
		super(user1, user2);
		this.meanRatingUser1 = calculateUserMeanRating(assetsRatingsUser1);
		this.meanRatingUser2 = calculateUserMeanRating(assetsRatingsUser2);
	}
	
	@Override
	public void initialize(User user1, User user2){
		this.meanRatingUser1 = calculateUserMeanRating(assetsRatingsUser1);
		this.meanRatingUser2 = calculateUserMeanRating(assetsRatingsUser2);
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
		double predictedValue;
		double tempSum = 0;
		int numOfCorratedAssets = coRatedAssetIds.size();

		for (int assetId : coRatedAssetIds) {
			predictedValue = meanRatingUser1
					+ (assetsRatingsUser2.get(assetId) - meanRatingUser2);
			tempSum += Math.pow(
					(predictedValue - assetsRatingsUser1.get(assetId))
							/ MAX_RATING_VALUE, 2);
		}

		return (numOfCorratedAssets / (assetsRatingsUser1.size()
				+ assetsRatingsUser2.size() - numOfCorratedAssets))
				* (1 - tempSum / numOfCorratedAssets);
	}
}
