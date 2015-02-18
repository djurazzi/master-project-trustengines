package com.duricic.trustengine;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.duricic.domain.User;
import com.duricic.domain.UserAsset;

/**
 * This is an abstract TrustEngine class containing all the basic functions and
 * variables which every other class implementing TrustEngine has to offer.
 * 
 * @author Tomislav Duricic
 * @version 1.0
 */
public abstract class AbstractTrustEngine {

	protected double userSimilarity;
	protected double meanRatingUser1;
	protected double meanRatingUser2;
	protected Set<Integer> coRatedAssetIds = new HashSet<Integer>();
	protected Map<Integer, Integer> assetsRatingsUser1 = new HashMap<Integer, Integer>();
	protected Map<Integer, Integer> assetsRatingsUser2 = new HashMap<Integer, Integer>();

	/**
	 * Default constructor with no parameters.
	 * 
	 */
	public AbstractTrustEngine() {

	}

	/**
	 * Constructor which takes two users as parameters, same as calling a
	 * constructor with no parameters and then calling the initialize() method.
	 * 
	 * @param user1
	 * @param user2
	 */
	public AbstractTrustEngine(User user1, User user2) {
		for (UserAsset ua : user1.getUserAssets()) {
			assetsRatingsUser1.put(ua.getAsset().getAssetId(), ua.getRating());
		}
		for (UserAsset ua : user2.getUserAssets()) {
			assetsRatingsUser2.put(ua.getAsset().getAssetId(), ua.getRating());
		}
		setCoRatedAssetIds();
	}

	/**
	 * This method takes two users as parameters and initializes all the
	 * necessary variables.
	 * 
	 * @param user1
	 * @param user2
	 */
	public void initialize(User user1, User user2) {
		for (UserAsset ua : user1.getUserAssets()) {
			assetsRatingsUser1.put(ua.getAsset().getAssetId(), ua.getRating());
		}
		for (UserAsset ua : user2.getUserAssets()) {
			assetsRatingsUser2.put(ua.getAsset().getAssetId(), ua.getRating());
		}
		setCoRatedAssetIds();
	}
	
	/**
	 * Resets the all the internal variables of the trust engine.
	 * 
	 */
	public void reset(){
		userSimilarity = Double.NaN;
		meanRatingUser1 = Double.NaN;
		meanRatingUser2 = Double.NaN;
		coRatedAssetIds = new HashSet<Integer>();
		assetsRatingsUser1 = new HashMap<Integer, Integer>();
		assetsRatingsUser2 = new HashMap<Integer, Integer>();
	}

	/**
	 * This is an abstract method which every class extending TrustEngine has to
	 * implement
	 * 
	 * @return Calculated trust value
	 */
	public abstract double calculateTrust();

	/**
	 * This method calculates the Pearson's correlation coefficient for two
	 * users.
	 * 
	 * @return Pearson's correlation coefficient
	 */
	protected double calculateUserSimilarity() {
		Map<Integer, Integer> assetsRatingsUser1Copy = assetsRatingsUser1;
		Map<Integer, Integer> assetsRatingsUser2Copy = assetsRatingsUser2;
		assetsRatingsUser1Copy.keySet().retainAll(coRatedAssetIds);
		assetsRatingsUser2Copy.keySet().retainAll(coRatedAssetIds);

		double sum1 = 0, sum2 = 0, sum3 = 0;
		double meanRating1 = calculateUserMeanRating(assetsRatingsUser1Copy);
		double meanRating2 = calculateUserMeanRating(assetsRatingsUser2Copy);
		double userSimilarity = 0;

		for (int id : coRatedAssetIds) {
			sum1 += (assetsRatingsUser1Copy.get(id) - meanRating1)
					* (assetsRatingsUser2Copy.get(id) - meanRating2);
			sum2 += Math.pow(assetsRatingsUser1Copy.get(id) - meanRating1, 2);
			sum3 += Math.pow(assetsRatingsUser2Copy.get(id) - meanRating2, 2);
		}

		userSimilarity = sum1 / (Math.sqrt(sum2 * sum3));
		return userSimilarity;
	}

	/**
	 * This method calculates the average rating of a user.
	 * 
	 * @param assetsRatingsUser
	 * @return mean rating of a user
	 */
	protected double calculateUserMeanRating(
			Map<Integer, Integer> assetsRatingsUser) {
		int sum = 0;
		int numberOfRatedAssets = assetsRatingsUser.size();
		for (Map.Entry<Integer, Integer> entry : assetsRatingsUser.entrySet()) {
			sum += entry.getValue();
		}
		return (double) sum / numberOfRatedAssets;
	}

	/**
	 * This method checks if two users have corellated assets (assets which they
	 * both rated) and saves id's of those assets in a Set.
	 * 
	 */
	private void setCoRatedAssetIds() {
		coRatedAssetIds = (Set<Integer>) assetsRatingsUser1.keySet();
		coRatedAssetIds.retainAll((Set<Integer>) assetsRatingsUser2.keySet());
	}

	/**
	 * 
	 * @return Set of id's of correlated assets
	 */
	public Set<Integer> getCoRatedAssetIds() {
		return this.coRatedAssetIds;
	}

}
