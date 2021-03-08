package blackjack.domain.participant;

import blackjack.domain.card.Card;
import blackjack.domain.card.CardDeck;
import blackjack.domain.card.CardLetter;
import blackjack.domain.card.CardSuit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class ParticipantTest {
    private Participant player;
    private Participant dealer;

    @BeforeEach
    void setUp() {
        player = new Player("joel");
        dealer = new Dealer();
    }

    @Test
    @DisplayName("블랙잭 게임을 위해 최초 2장을 받는지 테스트")
    void receiveInitialCard() {
        final CardDeck cardDeck = new CardDeck();
        player.receiveInitialCard(cardDeck);
        dealer.receiveInitialCard(cardDeck);

        assertThat(player.getHand().getCards()).hasSize(2);
        assertThat(dealer.getHand().getCards()).hasSize(2);
    }

    @Test
    @DisplayName("추가적으로 카드를 지급 받는지 테스트")
    void receiveAdditionalCard() {
        player.receiveAdditionalCard(new Card(CardLetter.EIGHT, CardSuit.DIAMOND));
        dealer.receiveAdditionalCard(new Card(CardLetter.FIVE, CardSuit.DIAMOND));

        assertThat(player.getHand().getCards()).hasSize(1);
        assertThat(dealer.getHand().getCards()).hasSize(1);
    }

    @Test
    @DisplayName("Bust가 되는지 테스트")
    void bustTest() {
        player.receiveAdditionalCard(new Card(CardLetter.EIGHT, CardSuit.DIAMOND));
        player.receiveAdditionalCard(new Card(CardLetter.NINE, CardSuit.DIAMOND));
        player.receiveAdditionalCard(new Card(CardLetter.TEN, CardSuit.DIAMOND));

        dealer.receiveAdditionalCard(new Card(CardLetter.FIVE, CardSuit.DIAMOND));
        dealer.receiveAdditionalCard(new Card(CardLetter.FOUR, CardSuit.DIAMOND));

        assertThat(player.isBust()).isTrue();
        assertThat(dealer.isBust()).isFalse();
    }
}
