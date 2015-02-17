package com.duricic.trustengine;

import java.util.HashMap;
import java.util.Map;

import com.duricic.domain.User;

/**
 * This class implements the algorithm of calculating the trust implicitly
 * between two users based on the assets they've rated. This algorithm is
 * described in the article "Trust in recommender systems" published by John
 * O'Donovan and Barry Smyth
 * 
 * @author Tomislav Duricic
 * @version 1.0
 */
public class TrustEngineOne extends AbstractTrustEngine {

	private static final double EPSILON = 1.8f;
	private Map<Integer, Double> predictedRatingsUser2 = new HashMap<Integer, Double>();

	public TrustEngineOne() {
		super();
	}

	public TrustEngineOne(User user1, User user2) {
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
	public double calculateTrust() {
		int numOfCorrectTrust = 0;
		double predictedValue;
		this.userSimilarity = calculateUserSimilarity();

		for (int assetId : coRatedAssetIds) {
			predictedValue = meanRatingUser2
					+ (((assetsRatingsUser1.get(assetId) - meanRatingUser1) * userSimilarity) / Math
							.abs(userSimilarity));
			predictedRatingsUser2.put(assetId, predictedValue);
			if (Math.abs(predictedValue - assetsRatingsUser2.get(assetId)) < EPSILON) {
				numOfCorrectTrust++;
			}
		}

		return (double) numOfCorrectTrust / coRatedAssetIds.size();
	}

}
