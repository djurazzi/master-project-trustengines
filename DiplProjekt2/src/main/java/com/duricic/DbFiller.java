package com.duricic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.hibernate.Session;

import com.duricic.domain.Asset;
import com.duricic.domain.User;
import com.duricic.domain.UserAsset;
import com.duricic.util.HibernateUtil;

/**
 * This class is used for reading the ratings_data.txt file line by line,
 * tokenizing it and using these values to fill up the database.
 * 
 * @author Tomislav Duricic
 * @version 1.0
 */
public class DbFiller {
	public static void main(String[] args) {
		DbFiller obj = new DbFiller();
		obj.run();
	}

	public void run() {
		String filePath = new File("src/main/resources/ratings_data.txt")
				.getAbsolutePath();
		BufferedReader br = null;
		String line = "";
		String lineSplitBy = " ";

		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();

		try {

			br = new BufferedReader(new FileReader(filePath));

			line = br.readLine();
			String[] values = line.split(lineSplitBy);
			int userId = Integer.parseInt(values[0]);
			int assetId = Integer.parseInt(values[1]);
			int rating = Integer.parseInt(values[2]);

			User user = new User(userId, "User" + userId);
			Asset asset = new Asset(assetId, "A" + assetId);
			session.saveOrUpdate(asset);
			UserAsset userAsset = new UserAsset();
			userAsset.setUser(user);
			userAsset.setAsset(asset);
			userAsset.setRating(rating);
			user.getUserAssets().add(userAsset);

			while ((line = br.readLine()) != null) {
				System.out.println(line);
				values = line.split(lineSplitBy);
				userId = Integer.parseInt(values[0]);
				assetId = Integer.parseInt(values[1]);
				rating = Integer.parseInt(values[2]);

				if (user.getUserId() == userId) {
					asset = new Asset(assetId, "A" + assetId);
					session.merge(asset);
					userAsset = new UserAsset();
					userAsset.setUser(user);
					userAsset.setAsset(asset);
					userAsset.setRating(rating);
					user.getUserAssets().add(userAsset);
				} else {
					session.save(user);
					user = new User(userId, "User" + userId);
					asset = new Asset(assetId, "A" + assetId);
					session.merge(asset);
					userAsset = new UserAsset();
					userAsset.setUser(user);
					userAsset.setAsset(asset);
					userAsset.setRating(rating);
					user.getUserAssets().add(userAsset);
				}
			}
			session.save(user);
			session.getTransaction().commit();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		System.out.println("Done");
	}
}
