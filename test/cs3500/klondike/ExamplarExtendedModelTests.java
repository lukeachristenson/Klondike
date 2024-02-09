package cs3500.klondike;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cs3500.klondike.model.hw02.Card;
import cs3500.klondike.model.hw02.KlondikeModel;
import cs3500.klondike.model.hw04.LimitedDrawKlondike;
import cs3500.klondike.model.hw04.WhiteheadKlondike;


/**
 * tests to find Examplar Chaffs.
 */
public class ExamplarExtendedModelTests {
  KlondikeModel wh;
  KlondikeModel ld;

  List<Card> deckBySuit;
  List<Card> deckByValue;

  private void initWH() {
    wh = new WhiteheadKlondike();
    deckBySuit = wh.getDeck();
    Collections.sort(deckBySuit, new CardComparator());
    wh.startGame(deckBySuit, false, 7, 3);
  }

  private void initLD() {
    ld = new LimitedDrawKlondike(1);
    deckByValue = ld.getDeck();
    Collections.sort(deckByValue, new CardComparator());
    ld.startGame(deckByValue, false, 7, 3);
  }


  /*
   * different suit order
   *
   * Draw: A♡, 2♡, 3♡
   * Foundation: <none>, <none>, <none>, <none>
   *  A♠  ?  ?  ?  ?  ?  ?
   *     8♠  ?  ?  ?  ?  ?
   *        A♣  ?  ?  ?  ?
   *           6♣  ?  ?  ?
   *             10♣  ?  ?
   *                 K♣  ?
   *                    2♢
   */

  //this should catch the last chaff
  @Test
  public void testWhiteheadEnforcesSameSuitWhenMovingPiles() {
    wh = new WhiteheadKlondike();
    deckBySuit = wh.getDeck();
    Collections.sort(deckBySuit, new CardComparatorDifSuitOrder());
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
  public void testLimitedDrawDrawingTooMuch() {
    this.initLD();
    for (int i = 0; i < 48; i++) {
      ld.discardDraw();
    }
    Assert.assertThrows(IllegalStateException.class, () -> {
      ld.discardDraw();
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


  /*
   * the tests below need to be done with comparator number 2
   * so there is a different setup
   * <p>
   * Draw: 8♠, 8♢, 8♣
   * Foundation: <none>, <none>, <none>, <none>
   * A♠  A  A  A  2  2  2
   * 2♡  3  3  3  3  4
   * 4♢  4  4  5  5
   * 5♣  5  6  6
   * 6♣ 6h  7
   * 7♢  7
   * 7♡
   */
  private void init2() {
    wh = new WhiteheadKlondike();
    deckByValue = wh.getDeck();
    Collections.sort(deckByValue, new CardComparator2());
    wh.startGame(deckByValue, false, 7, 3);
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

  /**
   * comparator to sort cards by value.
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
   * comparator to sort cards by value.
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
   * comparator to sort cards by value.
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
