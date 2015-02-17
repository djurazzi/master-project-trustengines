package com.duricic.trustengine;

import com.duricic.domain.User;

/**
 * TODO this trust calculation algorithm is currently not implemented. Future
 * work.
 * 
 * This class implements the algorithm of calculating the trust implicitly
 * between two users based on the assets they've rated. This algorithm is
 * described in the article "Alleviating the Sparsity Problem of Collaborative
 * Filtering Using Trust Inferences " published by Manos Papagelis, Dimitris
 * Plexousakis and Themistoklis Kutsuras 
 * 
 * @author Tomislav Duricic
 * @version 1.0
 */
public class TrustEngineFive extends AbstractTrustEngine {

	public TrustEngineFive(){
		super();
	}
	
	public TrustEngineFive(User user1, User user2) {
		super(user1, user2);
	}

	@Override
	public double calculateTrust() {
		return 0;
	}

}
