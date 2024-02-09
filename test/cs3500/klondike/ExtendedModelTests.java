package cs3500.klondike;

import org.junit.Assert;
import org.junit.Test;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cs3500.klondike.controller.KlondikeController;
import cs3500.klondike.controller.KlondikeTextualController;
import cs3500.klondike.model.hw02.BasicKlondike;
import cs3500.klondike.model.hw02.Card;
import cs3500.klondike.model.hw02.KlondikeModel;
import cs3500.klondike.model.hw04.KlondikeCreator;
import cs3500.klondike.model.hw04.LimitedDrawKlondike;
import cs3500.klondike.model.hw04.WhiteheadKlondike;
import cs3500.klondike.view.KlondikeTextualView;


/**
 * additional tests for extended model.
 */
public class ExtendedModelTests {
  KlondikeModel wh;
  KlondikeModel ld;

  List<Card> deckBySuit;
  List<Card> deckByValue;

  private void initWH() {
    wh = KlondikeCreator.create(KlondikeCreator.GameType.WHITEHEAD);
    deckBySuit = wh.getDeck();
    Collections.sort(deckBySuit, new CardComparator());
    wh.startGame(deckBySuit, false, 7, 3);
  }


  /*
   * Draw: 3♣, 4♣, 5♣
   * Foundation: <none>, <none>, <none>, <none>
   *  A♠  2  3  4  5  6  7
   *     8♠  9 10  J  Q  K
   *        A♢  2  3  4  5
   *           6♢  7  8  9
   *             10♢  J  Q
   *                 K♢  A
   *                    2♣
   */
  private void initLD() {
    ld = KlondikeCreator.create(KlondikeCreator.GameType.LIMITED);
    deckByValue = ld.getDeck();
    Collections.sort(deckByValue, new CardComparator());
    ld.startGame(deckByValue, false, 7, 3);
  }

  /*
   * the tests below need to be done with comparator number 2
   * so there is a different setup
   *
   * Draw: 8♠, 8♢, 8♣
   * Foundation: <none>, <none>, <none>, <none>
   *  A♠  A  A  A  2  2  2
   *     2♡  3  3  3  3  4
   *        4♢  4  4  5  5
   *           5♣  5  6  6
   *              6♣  6  7
   *                 7♢  7
   *                    7♡
   *
   */
  private void init2() {
    wh = KlondikeCreator.create(KlondikeCreator.GameType.WHITEHEAD);
    deckByValue = wh.getDeck();
    Collections.sort(deckByValue, new CardComparator2());
    wh.startGame(deckByValue, false, 7, 3);
  }

  @Test
  public void testMovingCardToEmptyPileLD() {
    this.initLD();
    ld.moveToFoundation(0, 0);
    Card expected = ld.getCardAt(5, ld.getPileHeight(5) - 1);
    ld.movePile(5, 1, 0);
    Assert.assertEquals(ld.getCardAt(0, ld.getPileHeight(0) - 1), expected);
  }

  @Test
  public void testMovingCardToEmptyPileWHKing() {
    this.initWH();
    wh.moveToFoundation(0, 0);
    Card expected = wh.getCardAt(5, wh.getPileHeight(5) - 1);
    wh.movePile(5, 1, 0);
    Assert.assertEquals(wh.getCardAt(0, wh.getPileHeight(0) - 1), expected);

  }

  @Test
  public void testMovePileDifferentSuitsWHAgain() {
    initWH();
    Card expected = wh.getCardAt(0, wh.getPileHeight(0) - 1);
    wh.movePile(0, 1, 6);
    Assert.assertEquals(wh.getCardAt(6, wh.getPileHeight(6) - 1), expected);

  }

  @Test
  public void testMovingCardToEmptyPileWH() {
    this.initWH();
    Card expected = wh.getCardAt(5, wh.getPileHeight(5) - 1);
    wh.moveToFoundation(0, 0);
    wh.movePile(5, 1, 0);
    Assert.assertEquals(wh.getCardAt(0, wh.getPileHeight(0) - 1), expected);

  }


  @Test
  public void testMovingCardToEmptyPileWHNotKing() {
    this.initWH();
    Card expected = wh.getCardAt(4, wh.getPileHeight(4) - 1);
    wh.moveToFoundation(0, 0);
    wh.movePile(4, 1, 0);
    Assert.assertEquals(wh.getCardAt(0, wh.getPileHeight(0) - 1), expected);

  }


  @Test
  public void testWhiteheadFirstPile() {
    KlondikeModel wh = new WhiteheadKlondike();
    List<Card> deck = wh.getDeck();
    Collections.sort(deck, new CardComparator());
    wh.startGame(deck, false, 7, 3);
    Assert.assertEquals("A♠", wh.getCardAt(0, 0).toString());
  }

  @Test
  public void testWhiteheadFirstPileWithInit() {
    this.initWH();
    Assert.assertEquals("A♠", wh.getCardAt(0, 0).toString());
  }

  @Test
  public void testWhiteheadNotWorkingMovePile() {
    this.initWH();
    Assert.assertThrows(IllegalStateException.class, () -> {
      wh.movePile(0, 1, 1);
    });
  }


  @Test
  public void testWhiteheadIncorrectMovePileSameValue() {
    this.init2();
    Assert.assertThrows(IllegalStateException.class, () -> {
      wh.movePile(5, 1, 6);
    });
  }

  /*
   * Draw: 3♣, 4♣, 5♣
   * Foundation: <none>, <none>, <none>, <none>
   *  A♠  2  3  4  5  6  7
   *     8♠  9 10  J  Q  K
   *        A♢  2  3  4  5
   *           6♢  7  8  9
   *             10♢  J  Q
   *                 K♢  A
   *                    2♣
   */


  //  printer thingy
  @Test
  public void testNothingLeftInLimitedDrawDoesntCrash() {
    KlondikeModel lm = new LimitedDrawKlondike(1);
    List<Card> deck = lm.getDeck();
    Collections.sort(deck, new CardComparator());
    lm.startGame(deck, false, 7, 3);
    for (int i = 0; i < 48; i++) {
      lm.discardDraw();
      for (Card card : lm.getDrawCards()) {
        System.out.println(card.toString());
      }
      System.out.println("\n");
    }
    System.out.println(new KlondikeTextualView(lm));
    Assert.assertEquals(lm.getDrawCards().size(), 0);
  }

  //  @Test
  //  public void doingThingsForExamplar() {
  //    KlondikeModel lm = new LimitedDrawKlondike(1);
  //    List<Card> deck = lm.getDeck();
  //    Collections.sort(deck, new CardComparator());
  //    lm.startGame(deck, false, 7, 3);
  //    for (int i= 0; i <48; i++) {
  //      lm.discardDraw();
  //      for (Card card: lm.getDrawCards()) {
  //        System.out.println(card.toString());
  //      }
  //      System.out.println("\n");
  //    }
  //    System.out.println(new KlondikeTextualView(lm).toString());
  //  }

  @Test
  public void testDiscardDrawDealsWithLastDiscardDrawCorrectly() {
    this.initLD();
    for (int i = 0; i < 72; i++) {
      ld.discardDraw();
    }
    Assert.assertThrows(IllegalStateException.class, () -> {
      ld.discardDraw();
    });
  }

  @Test
  public void testLimitedDrawDiscardsCorrectly() {
    KlondikeModel lm = KlondikeCreator.create(KlondikeCreator.GameType.LIMITED);
    List<Card> deck = lm.getDeck();
    lm.startGame(deck, false, 7, 3);
    lm.discardDraw();
    lm.discardDraw();
    lm.moveDraw(3);
    for (int i = 0; i < 49; i++) {
      lm.discardDraw();
    }
    lm.moveDraw(4);
    for (int i = 0; i < 14; i++) {
      lm.discardDraw();
    }
    Assert.assertEquals(deck.subList(49, 52), lm.getDrawCards());
    lm.discardDraw();
    Assert.assertEquals(deck.subList(50, 52), lm.getDrawCards());
    lm.discardDraw();
    Assert.assertEquals(deck.subList(51, 52), lm.getDrawCards());
    lm.discardDraw();
    Assert.assertEquals(0, lm.getDrawCards().size());
  }

  @Test
  public void testDuplicateCardsWithDiscardDraw() {
    KlondikeModel lm = KlondikeCreator.create(KlondikeCreator.GameType.LIMITED);
    List<Card> deck = lm.getDeck();
    //deck with 4 aces of spades.
    deck = deck.subList(0, 1);
    deck.addAll(deck);
    deck.addAll(deck);
    deck.addAll(deck);
    lm.startGame(deck, false, 1, 1);
    lm.discardDraw();
    lm.moveToFoundation(0, 0);
    Assert.assertEquals(lm.getDrawCards().get(0), lm.getCardAt(0));
  }

  @Test
  public void testBasicKlondikeDiscardDrawNotLimited() {
    KlondikeModel km = KlondikeCreator.create(KlondikeCreator.GameType.BASIC);
    List<Card> deck = km.getDeck();
    km.startGame(deck, false, 7, 3);
    Card firstDraw = km.getDrawCards().get(0);
    for (int i = 0; i < 72; i++) {
      km.discardDraw();
    }
    Card curDraw = km.getDrawCards().get(0);
    Assert.assertEquals(firstDraw, curDraw);
  }

  @Test
  public void testWhiteheadKlondikeDiscardDrawNotLimited() {
    initWH();
    Card firstDraw = wh.getDrawCards().get(0);
    for (int i = 0; i < 72; i++) {
      wh.discardDraw();
    }
    Card curDraw = wh.getDrawCards().get(0);
    Assert.assertEquals(firstDraw, curDraw);
  }


  /**
   * tests for Whitehead Klondike.
   */
  @Test
  public void testMovingSingleCardWhiteheadDifferentSuit() {
    this.initWH();
    wh.movePile(0, 1, 6);
    Assert.assertEquals("A♠", wh.getCardAt(6, 7).toString());
  }

  /**
   * tests for KlondikeCreator.
   */

  @Test
  public void testCreateBasicKlondike() {
    KlondikeModel game = KlondikeCreator.create(KlondikeCreator.GameType.BASIC);
    Assert.assertTrue(game instanceof BasicKlondike);
  }

  @Test
  public void testCreateLimitedDrawKlondike() {
    KlondikeModel game = KlondikeCreator.create(KlondikeCreator.GameType.LIMITED);
    Assert.assertTrue(game instanceof LimitedDrawKlondike);
  }

  @Test
  public void testCreateWhiteheadKlondike() {
    KlondikeModel game = KlondikeCreator.create(KlondikeCreator.GameType.WHITEHEAD);
    Assert.assertTrue(game instanceof WhiteheadKlondike);
  }

  /**
   * examplar tests below.
   */
  @Test
  public void testWhiteheadEnforcesSameSuitWhenMovingPiles() {
    KlondikeModel wh = KlondikeCreator.create(KlondikeCreator.GameType.WHITEHEAD);
    deckBySuit = wh.getDeck();
    Collections.sort(deckBySuit, new ExamplarExtendedModelTests.CardComparatorDifSuitOrder());
    wh.startGame(deckBySuit, false, 7, 3);
    wh.moveToFoundation(0, 0);
    wh.moveToFoundation(2, 1);
    wh.movePile(5, 1, 0);
    wh.movePile(2, 1, 4);
    Assert.assertThrows(IllegalStateException.class, () -> {
      wh.movePile(4, 2, 5);
    });
  }

  @Test
  public void testWhiteheadMovePileWorks() {
    this.initWH();
    wh.movePile(0, 1, 6);
    Assert.assertEquals("A♠", wh.getCardAt(6, 7).toString());
  }

  //todo make a test for movetoFoundation with a different suit
  @Test
  public void testMoveToFoundationDifferentSuit() {
    this.initWH();
    wh.moveToFoundation(0, 0);
    wh.moveToFoundation(2, 1);
    wh.movePile(5, 1, 0);
    Assert.assertThrows(IllegalStateException.class, () -> {
      wh.movePile(4, 2, 5);
    });
  }

  @Test
  public void testLimitedDrawMovePileAndDiscardDraw() {
    this.initWH();
    wh.movePile(0, 1, 6);
    wh.discardDraw();
    Assert.assertEquals("A♠", wh.getCardAt(6, 7).toString());
  }

  @Test
  public void testLimitedDrawMovePile() {
    this.initWH();
    wh.movePile(0, 1, 6);
    Assert.assertEquals("A♠", wh.getCardAt(6, 7).toString());
  }




  @Test
  public void testLimitedDrawDrawingTooMuch() {
    this.initLD();
    Assert.assertThrows(IllegalStateException.class, () -> {
      for (int i = 0; i < 100; i++) {
        ld.discardDraw();
      }
    });
  }
  @Test
  public void testLimitedDrawRecyclesDeck() {
    this.initLD();
    for (int i = 0; i < 24; i++) {
      ld.discardDraw();
    }
    Assert.assertEquals(ld.getDrawCards().get(0).toString(), "3♣");
  }

  @Test
  public void testMovingWhiteHeadBuildsDifferentSuitsSecondTry() {
    this.init2();
    wh.moveToFoundation(0, 0);
    wh.movePile(5, 1, 0);
    Assert.assertThrows(IllegalStateException.class, () -> {
      wh.movePile(3, 1, 5);
    });
  }


  @Test
  public void testWhiteheadIncorrectMovePileWrongColor() {
    this.init2();
    Assert.assertThrows(IllegalStateException.class, () -> {
      wh.movePile(4, 1, 5);
    });
  }


  @Test
  public void testWhiteheadCorrectMovePile() {
    this.init2();
    wh.movePile(3, 1, 4);
    Assert.assertTrue(wh.getCardAt(0, 0).toString().contains("A"));
  }

  @Test
  public void testNonKingToEmptyCascadeInWhitehead() {
    this.initWH();
    wh.moveToFoundation(0, 0);
    Card expected = wh.getCardAt(6, wh.getPileHeight(6) - 1);
    wh.movePile(6, 1, 0);
    Assert.assertEquals(expected, wh.getCardAt(0, 0));
  }

  /**
   * tests for controller to test for maintained functionality.
   */

  @Test
  public void testDiscardDrawWithAnInvalidCommandThreeLimitedDrawController() {
    //need to check that correct card is still on top of draw pile
    // chaff 5 differently with 5,6 and 8
    Readable in = new StringReader("dd dd md x 4 dd q");
    Appendable out = new StringBuilder();
    KlondikeController controller = new KlondikeTextualController(in, out);
    KlondikeModel lm = KlondikeCreator.create(KlondikeCreator.GameType.LIMITED);
    List<Card> deck = lm.getDeck();
    Collections.sort(deck, new CardComparator());
    controller.playGame(lm, deck, false, 7, 3);
    Assert.assertTrue(out.toString().contains("Draw: 6♣, 7♣, 8♣\n" +
            "Foundation: <none>, <none>, <none>, <none>\n" +
            " A♠  ?  ?  ?  ?  ?  ?\n" +
            "    8♠  ?  ?  ?  ?  ?\n" +
            "       A♢  ?  ?  ?  ?\n" +
            "          6♢  ?  ?  ?\n" +
            "          5♣10♢  ?  ?\n" +
            "                K♢  ?\n" +
            "                   2♣"));
  }

  @Test
  public void testBadInputForMppCommand() {
    Readable in = new StringReader("mpp a 1 2 q");
    Appendable out = new StringBuilder();
    KlondikeController controller = new KlondikeTextualController(in, out);
    KlondikeModel lm = KlondikeCreator.create(KlondikeCreator.GameType.LIMITED);
    List<Card> deck = lm.getDeck();
    controller.playGame(lm, deck, false, 7, 3);
    Assert.assertTrue(out.toString().contains("Invalid move."));
  }

  @Test
  public void testIncompleteInputForMpfCommandWhiteHead() {
    Readable in = new StringReader("mpf 1 q");
    Appendable out = new StringBuilder();
    KlondikeController controller = new KlondikeTextualController(in, out);
    KlondikeModel wh = KlondikeCreator.create(KlondikeCreator.GameType.WHITEHEAD);
    List<Card> deck = wh.getDeck();
    controller.playGame(wh, deck, false, 7, 3);
    Assert.assertTrue(out.toString().contains("Invalid move."));
  }

  /**
   * comparators to sort cards by value.
   */
  public static class CardComparator2 implements Comparator<Card> {

    private final List<String> FACE_VALUES =
            new ArrayList<>(Arrays.asList("A", "2", "3", "4", "5", "6", "7"
                    , "8", "9", "10", "J", "Q", "K"));
    private final List<String> SUITS =
            new ArrayList<>(Arrays.asList("♠", "♢", "♣", "♡"));

    @Override
    public int compare(Card firstCard, Card secondCard) {
      String firstCardStr = firstCard.toString();
      String secondCardStr = secondCard.toString();

      String firstCardSuit = firstCardStr.substring(firstCardStr.length() - 1);
      String secondCardSuit = secondCardStr.substring(secondCardStr.length() - 1);

      if (!SUITS.contains(firstCardSuit) || !SUITS.contains(secondCardSuit)) {
        throw new IllegalArgumentException("Invalid card suit");
      }

      int firstCardSuitIndex = SUITS.indexOf(firstCardSuit);
      int secondCardSuitIndex = SUITS.indexOf(secondCardSuit);

      String firstCardFace = firstCardStr.substring(0, firstCardStr.length() - 1);
      String secondCardFace = secondCardStr.substring(0, secondCardStr.length() - 1);

      if (!FACE_VALUES.contains(firstCardFace) || !FACE_VALUES.contains(secondCardFace)) {
        throw new IllegalArgumentException("Invalid card face value");
      }

      int faceComparison = Integer.compare(FACE_VALUES.indexOf(firstCardFace)
              , FACE_VALUES.indexOf(secondCardFace));

      // If the face values are different, return their comparison result
      if (faceComparison != 0) {
        return faceComparison;
      }

      // Otherwise, compare based on the suit
      return Integer.compare(SUITS.indexOf(firstCardSuit), SUITS.indexOf(secondCardSuit));
    }
  }

  /**
   * comparators to sort cards by value.
   */
  public static class CardComparator implements Comparator<Card> {

    private final List<String> FACE_VALUES =
            new ArrayList<>(Arrays.asList("A", "2", "3", "4", "5", "6", "7", "8"
                    , "9", "10", "J", "Q", "K"));
    private final List<String> SUITS =
            new ArrayList<>(Arrays.asList("♠", "♢", "♣", "♡"));

    @Override
    public int compare(Card firstCard, Card secondCard) {
      String firstCardStr = firstCard.toString();
      String secondCardStr = secondCard.toString();

      String firstCardSuit = firstCardStr.substring(firstCardStr.length() - 1);
      String secondCardSuit = secondCardStr.substring(secondCardStr.length() - 1);

      if (!SUITS.contains(firstCardSuit) || !SUITS.contains(secondCardSuit)) {
        throw new IllegalArgumentException("Invalid card suit");
      }

      int firstCardSuitIndex = SUITS.indexOf(firstCardSuit);
      int secondCardSuitIndex = SUITS.indexOf(secondCardSuit);

      if (firstCardSuitIndex != secondCardSuitIndex) {
        return Integer.compare(firstCardSuitIndex, secondCardSuitIndex);
      }

      String firstCardFace = firstCardStr.substring(0, firstCardStr.length() - 1);
      String secondCardFace = secondCardStr.substring(0, secondCardStr.length() - 1);

      if (!FACE_VALUES.contains(firstCardFace) || !FACE_VALUES.contains(secondCardFace)) {
        throw new IllegalArgumentException("Invalid card face value");
      }

      return Integer.compare(FACE_VALUES.indexOf(firstCardFace)
              , FACE_VALUES.indexOf(secondCardFace));
    }
  }

  /**
   * comparators to sort cards by value.
   */
  public static class CardComparatorDifSuitOrder implements Comparator<Card> {

    private final List<String> FACE_VALUES =
            new ArrayList<>(Arrays.asList("A", "2", "3", "4", "5", "6", "7", "8"
                    , "9", "10", "J", "Q", "K"));
    private final List<String> SUITS =
            new ArrayList<>(Arrays.asList("♠", "♣", "♢", "♡"));

    @Override
    public int compare(Card firstCard, Card secondCard) {
      String firstCardStr = firstCard.toString();
      String secondCardStr = secondCard.toString();

      String firstCardSuit = firstCardStr.substring(firstCardStr.length() - 1);
      String secondCardSuit = secondCardStr.substring(secondCardStr.length() - 1);

      if (!SUITS.contains(firstCardSuit) || !SUITS.contains(secondCardSuit)) {
        throw new IllegalArgumentException("Invalid card suit");
      }

      int firstCardSuitIndex = SUITS.indexOf(firstCardSuit);
      int secondCardSuitIndex = SUITS.indexOf(secondCardSuit);

      if (firstCardSuitIndex != secondCardSuitIndex) {
        return Integer.compare(firstCardSuitIndex, secondCardSuitIndex);
      }

      String firstCardFace = firstCardStr.substring(0, firstCardStr.length() - 1);
      String secondCardFace = secondCardStr.substring(0, secondCardStr.length() - 1);

      if (!FACE_VALUES.contains(firstCardFace) || !FACE_VALUES.contains(secondCardFace)) {
        throw new IllegalArgumentException("Invalid card face value");
      }

      return Integer.compare(FACE_VALUES.indexOf(firstCardFace)
              , FACE_VALUES.indexOf(secondCardFace));
    }
  }
}
