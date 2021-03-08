package blackjack.domain.participant;

import blackjack.domain.card.Card;
import blackjack.domain.card.CardLetter;
import blackjack.domain.card.CardSuit;
import blackjack.domain.result.Result;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

public class DealerTest {
    private Dealer dealer;
    private Player player;

    @BeforeEach
    void setUp() {
        dealer = new Dealer();
        player = new Player("joel");
    }

    @Test
    @DisplayName("딜러가 잘 생성되었는지 확인")
    void create() {
        assertThatCode(() -> new Dealer())
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("딜러가 Participant로 부터 상속받았는지 확인")
    void extend() {
        final Participant participant = new Dealer();
        participant.receiveAdditionalCard(new Card(CardLetter.ACE, CardSuit.HEART));
        assertThat(participant.getName()).isEqualTo("딜러");
    }

    @Test
    @DisplayName("딜러가 카드를 더 받을 수 있는지 확인")
    void checkMoreCardAvailable() {
        dealer.receiveAdditionalCard(new Card(CardLetter.TWO, CardSuit.CLOVER));
        dealer.receiveAdditionalCard(new Card(CardLetter.TEN, CardSuit.CLOVER));
        assertThat(dealer.checkMoreCardAvailable()).isTrue();
    }

    @Test
    @DisplayName("상대가 버스트라면 무조건 딜러는 승리한다.")
    void checkResultWhenOpponentIsBust() {
        player.receiveAdditionalCard(new Card(CardLetter.JACK, CardSuit.CLOVER));
        player.receiveAdditionalCard(new Card(CardLetter.QUEEN, CardSuit.CLOVER));
        player.receiveAdditionalCard(new Card(CardLetter.KING, CardSuit.CLOVER));

        dealer.receiveAdditionalCard(new Card(CardLetter.EIGHT, CardSuit.DIAMOND));
        dealer.receiveAdditionalCard(new Card(CardLetter.NINE, CardSuit.DIAMOND));

        assertThat(dealer.checkResult(player)).isEqualTo(Result.WIN);
    }

    @Test
    @DisplayName("상대가 버스트가 아니고 딜러가 버스트라면 패배한다.")
    void checkResultWhenDealerIsBust() {
        player.receiveAdditionalCard(new Card(CardLetter.JACK, CardSuit.CLOVER));
        player.receiveAdditionalCard(new Card(CardLetter.QUEEN, CardSuit.CLOVER));

        dealer.receiveAdditionalCard(new Card(CardLetter.EIGHT, CardSuit.DIAMOND));
        dealer.receiveAdditionalCard(new Card(CardLetter.NINE, CardSuit.DIAMOND));
        dealer.receiveAdditionalCard(new Card(CardLetter.TEN, CardSuit.DIAMOND));

        assertThat(dealer.checkResult(player)).isEqualTo(Result.LOSE);
    }
    
    @ParameterizedTest
    @CsvSource(value = {"TEN,NINE:승", "TEN,SEVEN:무", "SIX,SEVEN:패"}, delimiter = ':')
    @DisplayName("상대와 딜러 모두 버스트가 아니라면, 점수 합계로 승무패를 가린다")
    void checkResultByScore(final String dealerCardInput, final String expectedResult) {
        player.receiveAdditionalCard(new Card(CardLetter.TEN, CardSuit.CLOVER));
        player.receiveAdditionalCard(new Card(CardLetter.SEVEN, CardSuit.CLOVER));

        final String[] dealerCards = dealerCardInput.split(",");
        for (final String dealerCard : dealerCards) {
            final CardLetter cardLetter = CardLetter.valueOf(dealerCard);
            dealer.receiveAdditionalCard(new Card(cardLetter, CardSuit.DIAMOND));
        }
        assertThat(dealer.checkResult(player)).isEqualTo(expectedResult);
    }
}
