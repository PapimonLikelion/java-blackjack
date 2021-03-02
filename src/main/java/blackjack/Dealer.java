package blackjack;

import java.util.ArrayList;
import java.util.List;

public class Dealer {
    private static final int MAX_SUM_FOR_MORE_CARD = 16;

    private final String name;
    private final List<Card> myCards;

    public Dealer(final String name) {
        this.name = name;
        this.myCards = new ArrayList<>();
    }

    public void receiveCard(final Card card) {
        myCards.add(card);
    }

    public int getCardCount() {
        return myCards.size();
    }

    public boolean checkMoreCardAvailable() {
        return (calculateMyCardSum() < MAX_SUM_FOR_MORE_CARD);
    }

    private int calculateMyCardSum() {
        return myCards.stream()
            .mapToInt(Card::getCardNumber)
            .sum();
    }
}
