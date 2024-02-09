package cs3500.klondike;

import cs3500.klondike.model.hw02.KlondikeCard;
import cs3500.klondike.model.hw02.KlondikeModel;
import cs3500.klondike.model.hw02.BasicKlondike;
import cs3500.klondike.model.hw02.Card;
import cs3500.klondike.model.hw02.Suit;
import cs3500.klondike.model.hw02.Value;

import org.junit.Assert;
import org.junit.Test;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;


/**
 * tests all klondike public methods and more.
 */
public class TestsForKlondike {
  /**
   * the tests below test get deck in whichever way is specified in the title.
   */
  private KlondikeModel km;


  @Test
  public void testDeckSize() {
    km = new BasicKlondike();
    Assert.assertEquals(52, km.getDeck().size());
  }

  @Test
  public void testDeckUniqueness() {
    km = new BasicKlondike();
    List<Card> deck = km.getDeck();
    Set<Card> uniqueCards = new HashSet<>(deck);
    Assert.assertEquals(deck.size(), uniqueCards.size());
  }

  @Test
  public void testDeckContainsAllCards() {
    km = new BasicKlondike();
    List<Card> deck = km.getDeck();
    for (Suit suit : Suit.values()) {
      for (Value value : Value.values()) {
        Assert.assertTrue(deck.contains(new KlondikeCard(value, suit)));
      }
    }
  }

  @Test
  public void testDeckOrder() {
    km = new BasicKlondike();
    List<Card> deck = km.getDeck();
    int index = 0;
    for (Suit suit : Suit.values()) {
      for (Value value : Value.values()) {
        Assert.assertEquals(new KlondikeCard(value, suit), deck.get(index++));
      }
    }
  }

  /**
   * tests cascades and methods on them.
   */

  @Test
  public void testCascadeFirstItem() {
    KlondikeModel km = new BasicKlondike();
    List<Card> newDeck = km.getDeck();
    km.startGame(newDeck, false, 7, 3);
    Assert.assertEquals(new KlondikeCard(Value.ACE, Suit.SPADES),km.getCardAt(0,0));
  }

  @Test
  public void testCascadesNotOnEdgeOfCascade() {
    KlondikeModel km = new BasicKlondike();
    List<Card> newDeck = km.getDeck();
    km.startGame(newDeck, false, 7, 3);
    Assert.assertEquals(new KlondikeCard(Value.EIGHT, Suit.SPADES),km.getCardAt(1,1));
  }

  /**
   * The tests below test startGame.
   */
  @Test
  public void testStartGameWithZeroDraws() {
    KlondikeModel km = new BasicKlondike();
    List<Card> deck = km.getDeck();
    Assert.assertThrows(IllegalArgumentException.class, () -> {
      km.startGame(deck, false, 7, 0);
    });
  }

  @Test
  public void testStartGameHappyPath() {
    KlondikeModel km = new BasicKlondike();
    List<Card> deck = km.getDeck();
    km.startGame(deck, true, 7, 3);
    Assert.assertFalse(km.isGameOver());
  }


  // Test 5: Don't Shuffle Deck
  @Test
  public void testDontShuffleDeck() {
    KlondikeModel km = new BasicKlondike();
    List<Card> originalDeck = new ArrayList<>(km.getDeck());
    km.startGame(originalDeck, false, 7, 3);
    Assert.assertEquals(originalDeck, km.getDeck());
  }

  @Test
  public void testShuffleDeck() {
    KlondikeModel km = new BasicKlondike();
    List<Card> originalDeck = new ArrayList<>(km.getDeck());
    km.startGame(originalDeck, true, 7, 3);
    //true because mutation isn't occuring
    Assert.assertEquals(originalDeck, km.getDeck());
  }


  @Test
  public void testInvalidNumberOfPiles() {
    KlondikeModel km = new BasicKlondike();
    List<Card> deck = km.getDeck();
    int numPiles = 10; // A number that will make the sum exceed the deck size
    Assert.assertThrows(IllegalArgumentException.class, () -> {
      km.startGame(deck, false, numPiles, 3);
    });
  }

  @Test
  public void testInvalidNumberOfPilesNotEnough() {
    KlondikeModel km = new BasicKlondike();
    List<Card> deck = km.getDeck();
    Assert.assertThrows(IllegalArgumentException.class, () -> {
      km.startGame(deck, false, 0, 3);
    });
  }

  @Test
  public void testDrawPileInitialization() {
    KlondikeModel km = new BasicKlondike();
    List<Card> deck = km.getDeck();
    km.startGame(deck, false, 7, 3);
    // Assuming you have a getter for the draw pile
    Assert.assertEquals(3, km.getDrawCards().size());
  }

  /**
   * The following tests below test movePile, details of how it is being tested is included
   * in the titles of the test.
   */


  @Test
  public void testMoveNonKingToEmptyFirstCascadePile() {
    KlondikeModel km = new BasicKlondike();
    List<Card> newDeck = km.getDeck();
    km.startGame(newDeck, false, 7, 3);
    km.moveToFoundation(0,0);
    Assert.assertThrows(IllegalStateException.class, () -> {
      km.movePile(4,1,0);
    });
  }

  @Test
  public void testMoveKingToEmptyFirstCascadePile() {
    KlondikeModel km = new BasicKlondike();
    List<Card> newDeck = km.getDeck();
    km.startGame(newDeck, false, 7, 3);
    km.moveToFoundation(0,0);
    km.movePile(5,1,0);
    Assert.assertEquals(km.getCardAt(0, 0)
            ,new KlondikeCard(Value.KING,Suit.DIAMONDS));
  }

  @Test
  public void testMoveWorks() {
    KlondikeModel km = new BasicKlondike();
    List<Card> newDeck = km.getDeck();
    km.startGame(newDeck, false, 7, 3);
    km.moveToFoundation(0,0);
    Assert.assertEquals(km.getCardAt(0),new KlondikeCard(Value.ACE, Suit.SPADES));
  }

  /**
   * the tests below test get card at in the ways specified in the title.
   */
  @Test
  public void testGetCardAtNormalFunction() {
    KlondikeModel km = new BasicKlondike();
    List<Card> newDeck = km.getDeck();
    km.startGame(newDeck, false, 7, 3);
    Assert.assertEquals(km.getCardAt(0,0),new KlondikeCard(Value.ACE, Suit.SPADES));
  }

  @Test
  public void testGetCardAtGameNotStarted() {
    KlondikeModel km2 = new BasicKlondike();
    Assert.assertThrows(IllegalStateException.class, () -> {
      km2.getCardAt(0, 0);
    });
  }

  @Test
  public void testGetCardAtInvalidPile() {
    KlondikeModel km = new BasicKlondike();
    List<Card> deck = km.getDeck();
    km.startGame(deck, false, 7, 3);
    Assert.assertThrows(IllegalArgumentException.class, () -> {
      km.getCardAt(10, 0); // Assuming you have 7 cascades only
    });
  }

  @Test
  public void testGetCardAtValidCase() {
    KlondikeModel km = new BasicKlondike();
    List<Card> deck = km.getDeck();
    km.startGame(deck, false, 7, 3);
    Card card = km.getCardAt(6, 6); // 7th cascade, 7th card, should be valid
    Assert.assertNotNull(card);
  }

  @Test
  public void testGetCardAtCascadeEmpty() {
    KlondikeModel km = new BasicKlondike();
    List<Card> deck = km.getDeck();
    km.startGame(deck, false, 7, 3);
    // Assume a method to empty the cascade for testing
    km.moveToFoundation(0,0);
    Assert.assertEquals(null, km.getCardAt(0, 0));
  }

  /**
   * tests isGameOver.
   */

  @Test
  public void testsIsGameOverInGame() {
    KlondikeModel km = new BasicKlondike();
    List<Card> deck = km.getDeck();
    km.startGame(deck, false, 7, 3);
    Assert.assertFalse(km.isGameOver());
  }

  @Test
  public void testsIsGameOverWithCompletedCondition() {
    KlondikeModel km = new BasicKlondike();
    List<Card> deck = km.getDeck();
    deck = deck.subList(0,1);
    km.startGame(deck, false, 1, 1);
    Assert.assertTrue(km.isGameOver());
  }

  /**
   * tests getCardAt With Other fields. test descriptions in titles.
   *
   */

  @Test(expected = IllegalStateException.class)
  public void testGetCardAtBeforeGameStart() {
    KlondikeModel km = new BasicKlondike();
    km.getCardAt(0); // Game not started yet.
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetCardAtInvalidPileNumber() {
    KlondikeModel km = new BasicKlondike();
    List<Card> newDeck = km.getDeck();
    km.startGame(newDeck, false, 7, 3);
    km.getCardAt(-1); // Negative pile number.
  }

  @Test
  public void testGetCardAtEmptyFoundation() {
    KlondikeModel km = new BasicKlondike();
    List<Card> newDeck = km.getDeck();
    km.startGame(newDeck, false, 7, 3);
    Assert.assertNull(km.getCardAt(0));
    km.getCardAt(1); // Index out of bounds since foundation is empty.
  }


  @Test(expected = IllegalArgumentException.class)
  public void testGetCardAtInvalidPositionInFoundation() {
    KlondikeModel km = new BasicKlondike();
    List<Card> newDeck = km.getDeck();
    km.startGame(newDeck, false, 7, 3);
    km.moveToFoundation(0,0);
    km.getCardAt(1);
  }



  /**
   * The tests below test whether isCardVisible works, test descriptions are in the titles.
   */

  @Test
  public void testIsCardVisible() {
    KlondikeModel km = new BasicKlondike();
    List<Card> newDeck = km.getDeck();
    km.startGame(newDeck, false, 7, 3);
    Assert.assertTrue(km.isCardVisible(0,0));
  }

  @Test
  public void testGameNotStarted() {
    KlondikeModel km = new BasicKlondike();
    // Game has not been started yet, so this should throw an exception.
    Assert.assertThrows(IllegalStateException.class, () -> km.isCardVisible(0, 0));
  }

  @Test
  public void testInvalidPileNumber() {
    KlondikeModel km = new BasicKlondike();
    List<Card> newDeck = km.getDeck();
    km.startGame(newDeck, false, 7, 3);
    // Assuming there are 7 cascades, asking for the 8th should throw an exception.
    Assert.assertThrows(IllegalArgumentException.class, () -> km.isCardVisible(7, 0));
  }

  @Test
  public void testInvalidCardIndex() {
    KlondikeModel km = new BasicKlondike();
    List<Card> newDeck = km.getDeck();
    km.startGame(newDeck, false, 7, 3);
    // Assuming the first cascade has 1 card, asking for the 2nd should throw an exception.
    Assert.assertThrows(IllegalArgumentException.class, () -> km.isCardVisible(0, 1));
  }

  @Test
  public void testCardVisible() {
    KlondikeModel km = new BasicKlondike();
    List<Card> newDeck = km.getDeck();
    km.startGame(newDeck, false, 7, 3);
    // Assuming only the bottom card in the first cascade is visible.
    Assert.assertTrue(km.isCardVisible(0, 0));
  }

  @Test
  public void testCardNotVisible() {
    KlondikeModel km = new BasicKlondike();
    List<Card> newDeck = km.getDeck();
    km.startGame(newDeck, false, 7, 3);
    // Assuming only the bottom card in the second cascade (with 2 cards) is visible.
    Assert.assertFalse(km.isCardVisible(1, 0));
  }

  /**
   * The tests below test movePile. Details about the tests are in the name of the test.
   */

  @Test
  public void testMoveToInvalidPile() {
    KlondikeModel km = new BasicKlondike();
    List<Card> newDeck = km.getDeck();
    km.startGame(newDeck, false, 7, 3);
    Assert.assertThrows(IllegalArgumentException.class
            , () -> km.movePile(0, 1, 8));
  }

  @Test
  public void testMoveFromInvalidPile() {
    KlondikeModel km = new BasicKlondike();
    List<Card> newDeck = km.getDeck();
    km.startGame(newDeck, false, 7, 3);
    Assert.assertThrows(IllegalArgumentException.class
            , () -> km.movePile(8, 1, 0));
  }

  @Test
  public void testMoveToSamePile() {
    KlondikeModel km = new BasicKlondike();
    List<Card> newDeck = km.getDeck();
    km.startGame(newDeck, false, 7, 3);
    Assert.assertThrows(IllegalArgumentException.class
            , () -> km.movePile(0, 1, 0));
  }

  @Test
  public void testMoveMoreCardsThanAvailable() {
    KlondikeModel km = new BasicKlondike();
    List<Card> newDeck = km.getDeck();
    km.startGame(newDeck, false, 7, 3);
    Assert.assertThrows(IllegalArgumentException.class
            , () -> km.movePile(0, 10, 1));
  }

  @Test
  public void testMovePileWrongSuit() {
    KlondikeModel km = new BasicKlondike();
    List<Card> newDeck = km.getDeck();
    km.startGame(newDeck, false, 4, 1);
    Assert.assertThrows(IllegalArgumentException.class
            , () -> km.movePile(2,1,5));

  }

  /**
   * tests isGameOVer. descriptions are in the titles of each test.
   */
  @Test(expected = IllegalStateException.class)
  public void testIsGameOverBeforeStart() {
    KlondikeModel km = new BasicKlondike();
    km.isGameOver(); // Game not started yet.
  }

  @Test
  public void testGameNotOverAfterStart() {
    KlondikeModel km = new BasicKlondike();
    List<Card> newDeck = km.getDeck();
    km.startGame(newDeck, false, 7, 3);

    Assert.assertFalse(km.isGameOver()); // Assuming the game isn't over right after starting.
  }



  @Test
  public void testMoveToFoundationEmptyFoundationButNotAKing() {
    KlondikeModel km = new BasicKlondike();
    List<Card> newDeck = km.getDeck();
    km.startGame(newDeck, false, 7, 3);
    km.moveToFoundation(0,0);
    Assert.assertThrows(IllegalStateException.class, () -> {
      km.moveToFoundation(1,0);
    });
  }

  @Test
  public void testMoveBeforeGameStart() {
    KlondikeModel km = new BasicKlondike();
    List<Card> newDeck = km.getDeck();
    Assert.assertThrows(IllegalStateException.class
            , () -> km.movePile(0, 1, 1));
  }

  /**
   * tests for moveDraw. details about tests are in the titles.
   */

  @Test
  public void testMoveDrawBeforeGameStart() {
    KlondikeModel km = new BasicKlondike();
    Assert.assertThrows(IllegalStateException.class, () -> km.moveDraw(0));
  }

  @Test
  public void testMoveDrawInvalidPile() {
    KlondikeModel km = new BasicKlondike();
    List<Card> newDeck = km.getDeck();
    km.startGame(newDeck, false, 7, 3);
    Assert.assertThrows(IllegalArgumentException.class, () -> km.moveDraw(8));
  }

  @Test
  public void testMoveDrawEmptyDrawPile() {
    KlondikeModel km = new BasicKlondike();
    List<Card> newDeck = km.getDeck();
    km.startGame(newDeck, false, 7, 3);
    // Assuming the draw pile is empty for this test
    Assert.assertThrows(IllegalStateException.class, () -> km.moveDraw(0));
  }

  @Test
  public void testMoveNonKingToEmptyCascade() {
    KlondikeModel km = new BasicKlondike();
    List<Card> newDeck = km.getDeck();
    km.startGame(newDeck, false, 7, 3);
    // Assuming the top drawn card is NOT a King and cascade 0 is empty
    Assert.assertThrows(IllegalStateException.class, () -> km.moveDraw(0));
  }

  /**
   *following tests moveToFoundation. read titles for more details.
   *
   */

  @Test
  public void testMoveToFoundationWrongSuit() {
    KlondikeModel km = new BasicKlondike();
    List<Card> newDeck = km.getDeck();
    km.startGame(newDeck, false, 7, 3);
    km.moveToFoundation(2,0);
    Assert.assertThrows(IllegalStateException.class, () -> {
      km.moveToFoundation(6,0);
    });

  }

  @Test
  public void testMoveNonAceToFoundation() {
    KlondikeModel km = new BasicKlondike();
    List<Card> newDeck = km.getDeck();
    km.startGame(newDeck, false, 7, 3);
    Assert.assertThrows(IllegalStateException.class, () -> {
      km.moveToFoundation(5,0);
    });
  }

  @Test
  public void testMoveBeforeGameStarts() {
    KlondikeModel km = new BasicKlondike();
    Assert.assertThrows(IllegalStateException.class, () -> km.moveToFoundation(0, 0));
  }

  /**
   * tests below test discardDraw and getDrawCards.
   */

  @Test
  public void testInitialDrawnCardsVisibility() {
    KlondikeModel km = new BasicKlondike();
    List<Card> newDeck = km.getDeck();
    km.startGame(newDeck, false, 7, 3);

    Assert.assertEquals("3♣", km.getDrawCards().get(0).toString());
    Assert.assertEquals("4♣", km.getDrawCards().get(1).toString());
    Assert.assertEquals("5♣", km.getDrawCards().get(2).toString());
  }

  @Test
  public void testDiscardDrawOnce() {
    KlondikeModel km = new BasicKlondike();
    List<Card> newDeck = km.getDeck();
    km.startGame(newDeck, false, 7, 3);
    km.discardDraw();

    Assert.assertEquals("4♣", km.getDrawCards().get(0).toString());
    Assert.assertEquals("5♣", km.getDrawCards().get(1).toString());
    Assert.assertEquals("6♣", km.getDrawCards().get(2).toString());
  }

  @Test
  public void testDiscardDrawTwice() {
    KlondikeModel km = new BasicKlondike();
    List<Card> newDeck = km.getDeck();
    km.startGame(newDeck, false, 7, 3);
    km.discardDraw();
    km.discardDraw();

    Assert.assertEquals("5♣", km.getDrawCards().get(0).toString());
    Assert.assertEquals("6♣", km.getDrawCards().get(1).toString());
    Assert.assertEquals("7♣", km.getDrawCards().get(2).toString());
  }


  @Test
  public void testMoveNonAceToCascadePile() {
    KlondikeModel km = new BasicKlondike();
    List<Card> newDeck = km.getDeck();

    km.startGame(newDeck, false, 7, 3);
    Assert.assertThrows(IllegalStateException.class, () -> {
      km.movePile(2,1,0);
    });
  }

  @Test
  public void testMoveFromDrawWithToFirstPile() {
    KlondikeModel km = new BasicKlondike();
    List<Card> newDeck = km.getDeck();
    km.startGame(newDeck, false, 7, 3);
    Assert.assertThrows(IllegalStateException.class, () -> {
      km.moveDraw(0);
    });
  }


  @Test
  public void testGetDrawCardsShouldBeOne() {
    KlondikeModel km = new BasicKlondike();
    List<Card> newDeck = km.getDeck();
    km.startGame(newDeck, false, 7, 1);
    Assert.assertEquals(km.getDrawCards().size(),1);
  }

  @Test
  public void testMoveWrongCardToEmptyPile() {
    KlondikeModel km = new BasicKlondike();
    List<Card> newDeck = km.getDeck();
    km.startGame(newDeck, false, 7, 3);
    Assert.assertThrows(IllegalStateException.class, () -> {
      km.moveToFoundation(0,0);
      km.movePile(1,1,0);
    });
  }

  @Test
  public void testMoveWrongCardToEmptyPileFromDraw() {
    KlondikeModel km = new BasicKlondike();
    List<Card> newDeck = km.getDeck();
    km.startGame(newDeck, false, 7, 3);
    Assert.assertThrows(IllegalStateException.class, () -> {
      km.moveToFoundation(0,0);
      km.moveDraw(0);
    });
  }


  @Test
  public void testMoveFromDrawWithNoDrawnToOtherPile() {
    KlondikeModel km = new BasicKlondike();
    List<Card> newDeck = km.getDeck();
    km.startGame(newDeck, false, 7, 3);
    Assert.assertThrows(IllegalStateException.class, () -> {
      km.moveDraw(1);
    });
  }

  @Test
  public void testWrongSuitRightNumOnFoundationTwo() {
    KlondikeModel km = new BasicKlondike();
    List<Card> newDeck = km.getDeck();
    km.startGame(newDeck, false, 2, 1);
    Assert.assertThrows(IllegalStateException.class, () -> {
      km.moveToFoundation(0, 0);
      for (int i = 0; i < 11; i++) {
        km.discardDraw();
      }
      km.moveDrawToFoundation(0);
    });
  }

  @Test
  public void testWrongSuitRightNumOnFoundation() {
    KlondikeModel km = new BasicKlondike();
    List<Card> newDeck = km.getDeck();
    km.startGame(newDeck, false, 7, 1);
    Assert.assertThrows(IllegalStateException.class, () -> {
      km.moveToFoundation(0,0);
      km.moveToFoundation(6,0);
    });
  }

  @Test
  public void testMoveFromDrawToFoundationWithNoDrawn() {
    KlondikeModel km = new BasicKlondike();
    List<Card> newDeck = km.getDeck();
    km.startGame(newDeck, false, 7, 3);
    Assert.assertThrows(IllegalStateException.class, () -> {
      km.moveDrawToFoundation(0);
    });
  }
}
