package com.duricic.trustengine;

import com.duricic.domain.User;

/**
 * This class implements the algorithm of calculating the trust implicitly
 * between two users based on the assets they've rated. This algorithm is
 * described in the article
 * "Using Trust in Collaborative Filtering Recommendation" published by
 * Chein-Shung Hwang and Yu-Pin Chen
 * 
 * @author Tomislav Duricic
 * @version 1.0
 */
public class TrustEngineThree extends AbstractTrustEngine {

	public TrustEngineThree() {
		super();
	}

	public TrustEngineThree(User user1, User user2) {
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
			tempSum += 1 - (Math.abs(predictedValue
					- assetsRatingsUser1.get(assetId)) / MAX_RATING_VALUE);
		}
		
		return tempSum / numOfCorratedAssets;
	}

}
