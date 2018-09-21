package ai2018.group31;

import genius.core.boaframework.*;
import genius.core.boaframework.Actions;
import genius.core.boaframework.NegotiationSession;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Group31_AS extends AcceptanceStrategy {
	private double a;
	private double b;

    public Group31_AS() {
    }

	public Group31_AS(NegotiationSession negoSession, OfferingStrategy strat, double alpha, double beta) {
		this.negotiationSession = negoSession;
		this.offeringStrategy = strat;
		this.a = alpha;
		this.b = beta;
	}

	@Override
	public void init(NegotiationSession negoSession, OfferingStrategy strat, OpponentModel opponentModel,
                     Map<String, Double> parameters) {
		this.negotiationSession = negoSession;
		this.offeringStrategy = strat;

		if (parameters.get("a") != null || parameters.get("b") != null) {
			a = parameters.get("a");
			b = parameters.get("b");
		} else {
			a = 1;
			b = 0;
		}
	}

	@Override
	public String printParameters() {
		String str = "[a: " + a + " b: " + b + "]";
		return str;
	}

	@Override
	public Actions determineAcceptability() {
		double nextMyBidUtil = offeringStrategy.getNextBid().getMyUndiscountedUtil();
		double lastOpponentBidUtil = negotiationSession.getOpponentBidHistory().getLastBidDetails()
				.getMyUndiscountedUtil();

		if (a * lastOpponentBidUtil + b >= nextMyBidUtil) {
			return Actions.Accept;
		}
		return Actions.Reject;
	}

	@Override
	public Set<BOAparameter> getParameterSpec() {

		Set<BOAparameter> set = new HashSet<BOAparameter>();
		set.add(new BOAparameter("a", 1.0,
				"Accept when the opponent's utility * a + b is greater than the utility of our current bid"));
		set.add(new BOAparameter("b", 0.0,
				"Accept when the opponent's utility * a + b is greater than the utility of our current bid"));

		return set;
	}

	@Override
	public String getName() {
        return "2018 - Group31 AS";
	}
}
