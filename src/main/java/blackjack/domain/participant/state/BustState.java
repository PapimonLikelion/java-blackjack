package blackjack.domain.participant.state;

import blackjack.domain.card.Card;

import java.util.ArrayList;
import java.util.List;

public class BustState extends HandState {
    public BustState(final List<Card> cards) {
        this.cards = new ArrayList<>(cards);
    }

    @Override
    public HandState add(final Card cards) {
        throw new IllegalStateException("버스트는 더 이상 카드를 받을 수 없습니다.");
    }

    @Override
    public boolean isBust() {
        return true;
    }

    @Override
    public boolean isBlackjack() {
        return false;
    }
}
