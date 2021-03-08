package blackjack.domain.participant;

import blackjack.domain.result.Result;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Dealer extends Participant {
    private static final int MAX_SUM_FOR_MORE_CARD = 16;
    private static final String FIXED_DEALER_NAME = "딜러";

    public Dealer() {
        super(FIXED_DEALER_NAME);
    }

    public boolean checkMoreCardAvailable() {
        return hand.calculateScore() <= MAX_SUM_FOR_MORE_CARD;
    }

    @Override
    public Result checkResult(final Participant participant) {
        if (participant.isBust()) {
            return Result.WIN;
        }
        if (this.isBust()) {
            return Result.LOSE;
        }
        return checkResultByScore(participant);
    }

    public Map<Result, Integer> checkEveryResult(final Players players) {
        final Map<Result, Integer> dealerResult = new HashMap<>();
        for (Player player : players.getPlayers()) {
            Result result = this.checkResult(player);
            dealerResult.put(result, dealerResult.getOrDefault(result, 0) + 1);
        }
        return Collections.unmodifiableMap(dealerResult);
    }
}
