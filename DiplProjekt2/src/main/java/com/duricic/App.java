package com.duricic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import org.hibernate.Session;

import com.duricic.domain.User;
import com.duricic.trustengine.*;
import com.duricic.util.HibernateUtil;

/**
 * This class reads the trust_data.txt file which contains values of trust
 * stated explicitly by users. After it reads a line in the file, it calculates
 * the trust between users implicitly if possible. If a user with a certain
 * user_id doesn't exist in the database, trust cannot be calculated, also if
 * the number of correlated items is less than two, trust cannot be calculated.
 * When this implicit trust value between users is calculated, it is then stored
 * in the calculated_ratings.txt file.
 * 
 * @author Tomislav Duricic
 * @version 1.0
 */
public class App {

	private static AbstractTrustEngine trustEngine;
	private static String fileWritePath;

	public App(AbstractTrustEngine trustEngine, String fileWritePath) {
		this.trustEngine = trustEngine;
		this.fileWritePath = fileWritePath;
	}

	public static void main(String[] args) {
		int num = 0;
		Scanner in = new Scanner(System.in);
		
		System.out.println("Choose your trust engine:\n"
				+ " 1)TrustEngineOne \n" + " 2)TrustEngineTwo \n"
				+ " 3)TrustEngineThree \n" + " 4)TrustEngineFour \n"
				+ " -99)Exit \n\n");
		num = in.nextInt();
		
		switch (num) {
		case 1:
			trustEngine = new TrustEngineOne();
			fileWritePath = "src/main/resources/calculated_ratings1.txt";
			break;
		case 2:
			trustEngine = new TrustEngineTwo();
			fileWritePath = "src/main/resources/calculated_ratings2.txt";
			break;
		case 3:
			trustEngine = new TrustEngineThree();
			fileWritePath = "src/main/resources/calculated_ratings3.txt";
			break;
		case 4:
			trustEngine = new TrustEngineFour();
			fileWritePath = "src/main/resources/calculated_ratings4.txt";
			break;
		default:
			break;
		}

		App obj = new App(trustEngine, fileWritePath);
		obj.run();
	}

	public void run() {

		String fileReadPath = new File("src/main/resources/trust_data.txt")
				.getAbsolutePath();
		BufferedReader br = null;
		PrintWriter writer = null;
		String line = "";
		String lineSplitBy = " ";

		try {

			Session session = HibernateUtil.getSessionFactory().openSession();

			br = new BufferedReader(new FileReader(fileReadPath));
			writer = new PrintWriter(fileWritePath, "UTF-8");
			int counter = 0;
			String[] values;
			/*
			 * User user1 = (User) session.get(User.class, 16); User user2 =
			 * (User) session.get(User.class, 181); trustCalculator = new
			 * TrustCalculator(user1, user2); writer.println(user1.getUserId() +
			 * " " + user2.getUserId() + " " + trustCalculator.calculateTrust()
			 * + " " + trustCalculator.getCoRatedAssetIds().size());
			 */

			long startTime = System.nanoTime();
			User user1 = new User(-1, "nullUser");
			User user2 = new User(-1, "nullUser");
			int allCalculated = 0;
			int correctlyCalculated = 0;
			double trustValue = 0;

			while ((line = br.readLine()) != null && counter < 1500) {
				values = line.split(lineSplitBy);
				int userId1 = Integer.parseInt(values[1]);
				int userId2 = Integer.parseInt(values[2]);
				if (userId1 != user1.getUserId()) {
					user1 = (User) session.get(User.class, userId1);
				}
				user2 = (User) session.get(User.class, userId2);

				if (user1 == null) {
					user1 = new User(-1, "nullUser");
				}
				if (user2 == null) {
					user2 = new User(-1, "nullUser");
				}
				if (user1.getUserId() == -1 || user2.getUserId() == -1) {
					writer.println(user1.getUserId() + " " + user2.getUserId()
							+ " " + "null");
					counter++;
					continue;
				}

				trustEngine.reset();
				trustEngine.initialize(user1, user2);
				if (trustEngine.getCoRatedAssetIds().size() < 2) {
					writer.println(user1.getUserId() + " " + user2.getUserId()
							+ " " + "null");
					counter++;
					continue;
				} else {
					trustValue = trustEngine.calculateTrust();
					writer.println(user1.getUserId() + " " + user2.getUserId()
							+ " " + trustEngine.getCoRatedAssetIds().size()
							+ " " + String.format("%.3f", trustValue));
					allCalculated++;
					if (trustValue > 0.5) {
						correctlyCalculated++;
					}
				}
				counter++;
			}

			long endTime = System.nanoTime();
			long duration = (endTime - startTime) / 1000000000;
			System.out.println("Execution time: " + duration + "s");
			System.out
					.println("Correct percentage: "
							+ String.format(
									"%.3f",
									(((double) correctlyCalculated * 100) / allCalculated))
							+ "%");

			session.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null || writer != null) {
				try {
					br.close();
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		System.out.println("Done");
	}
}