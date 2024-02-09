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

/**
 * controller tests for Examplar. The titles of tests describe what they test.
 */
public class ExamplarControllerTests {

  //  @Test
  //  public void doingThingsForExamplar() {
  //    KlondikeModel km = new BasicKlondike();
  //    List<Card> deck = km.getDeck();
  //    km.startGame(deck, false, 7, 3);
  //    System.out.println(new KlondikeTextualView(km).toString());
  //  }

  //catches chaff 4,9
  @Test
  public void testMPPInvalidMove() {
    Readable in = new StringReader("mpp 0 1 1 q");
    Appendable out = new StringBuilder();
    KlondikeController controller = new KlondikeTextualController(in, out);
    KlondikeModel km = new BasicKlondike();
    List<Card> deck = km.getDeck();
    controller.playGame(km, deck, false, 7, 3);
    Assert.assertTrue(out.toString().contains("Invalid move."));
  }

  //catches chaffs 2,8,9
  @Test
  public void testBadRandomInputStillHasEndStateWithQAtEnd() {
    Readable in = new StringReader("hi 1 how are 2 you q");
    Appendable out = new StringBuilder();
    KlondikeController controller = new KlondikeTextualController(in, out);
    KlondikeModel km = new BasicKlondike();
    List<Card> deck = km.getDeck();
    Collections.sort(deck, new CardComparator());
    controller.playGame(km, deck, false, 7, 3);
    //    System.out.print((out.toString()));
    Assert.assertTrue(out.toString().contains("State of game when quit:\n"));
  }


  //catches chaff 3,8
  @Test
  public void testMDInvalidMove() {
    Readable in = new StringReader("md 4 q");
    Appendable out = new StringBuilder();
    KlondikeController controller = new KlondikeTextualController(in, out);
    KlondikeModel km = new BasicKlondike();
    List<Card> deck = km.getDeck();
    controller.playGame(km, deck, false, 7, 3);
    Assert.assertTrue(out.toString().contains("Invalid move."));
  }

  //catches chaff 0,7,8
  @Test
  public void testMppValidMove() {
    Readable in = new StringReader("mpf 1 1 mpf 3 2 mpp 6 1 1 q");
    Appendable out = new StringBuilder();
    KlondikeController controller = new KlondikeTextualController(in, out);
    KlondikeModel km = new BasicKlondike();
    List<Card> deck = km.getDeck();
    controller.playGame(km, deck, false, 7, 3);
    //    System.out.print(out.toString());
    Assert.assertTrue(out.toString().contains("Draw: 3♣, 4♣, 5♣\n" +
            "Foundation: A♠, A♢, <none>, <none>\n" +
            " K♢  ?  ?  ?  ?  ?  ?\n" +
            "    8♠ 9♠  ?  ?  ?  ?\n" +
            "           ?  ?  ?  ?\n" +
            "          6♢  ?  ?  ?\n" +
            "            10♢ J♢  ?\n" +
            "                    ?\n" +
            "                   2♣\n"));
  }


  @Test
  public void testDiscardDrawWithAnInvalidCommand() {
    //need to check that correct card is still on top of draw pile
    // chaff 2 and 5
    Readable in = new StringReader("dd $ dd q");
    Appendable out = new StringBuilder();
    KlondikeController controller = new KlondikeTextualController(in, out);
    KlondikeModel km = new BasicKlondike();
    List<Card> deck = km.getDeck();
    Collections.sort(deck, new CardComparator());
    controller.playGame(km, deck, false, 7, 3);
    Assert.assertTrue(out.toString().contains("Game quit!\n" +
            "State of game when quit:\n" +
            "Draw: 5♣, 6♣, 7♣\n" +
            "Foundation: <none>, <none>, <none>, <none>\n" +
            " A♠  ?  ?  ?  ?  ?  ?\n" +
            "    8♠  ?  ?  ?  ?  ?\n" +
            "       A♢  ?  ?  ?  ?\n" +
            "          6♢  ?  ?  ?\n" +
            "            10♢  ?  ?\n" +
            "                K♢  ?\n" +
            "                   2♣"));
  }

  @Test
  public void testDiscardDrawWithAnInvalidNumberToken() {
    //need to check that correct card is still on top of draw pile
    // chaff 2 and 5
    Readable in = new StringReader("dd 4 dd q");
    Appendable out = new StringBuilder();
    KlondikeController controller = new KlondikeTextualController(in, out);
    KlondikeModel km = new BasicKlondike();
    List<Card> deck = km.getDeck();
    Collections.sort(deck, new CardComparator());
    controller.playGame(km, deck, false, 7, 3);
    Assert.assertTrue(out.toString().contains("Draw: 5♣, 6♣, 7♣\n" +
            "Foundation: <none>, <none>, <none>, <none>\n" +
            " A♠  ?  ?  ?  ?  ?  ?\n" +
            "    8♠  ?  ?  ?  ?  ?\n" +
            "       A♢  ?  ?  ?  ?\n" +
            "          6♢  ?  ?  ?\n" +
            "            10♢  ?  ?\n" +
            "                K♢  ?\n" +
            "                   2♣\n" +
            "Score: 0\n"));
  }


  @Test
  public void testDiscardDrawWithAnInvalidCommandTwo() {
    //need to check that correct card is still on top of draw pile
    // chaff 5 differently with 5,6 and 8
    Readable in = new StringReader("dd dd md 4 q");
    Appendable out = new StringBuilder();
    KlondikeController controller = new KlondikeTextualController(in, out);
    KlondikeModel km = new BasicKlondike();
    List<Card> deck = km.getDeck();
    Collections.sort(deck, new CardComparator());
    controller.playGame(km, deck, false, 7, 3);
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
  public void testDiscardDrawWithAnInvalidCommandThree() {
    //need to check that correct card is still on top of draw pile
    // chaff 5 differently with 5,6 and 8
    Readable in = new StringReader("dd dd md x 4 dd q");
    Appendable out = new StringBuilder();
    KlondikeController controller = new KlondikeTextualController(in, out);
    KlondikeModel km = new BasicKlondike();
    List<Card> deck = km.getDeck();
    Collections.sort(deck, new CardComparator());
    controller.playGame(km, deck, false, 7, 3);
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
   * comparator to sort cards by suit.
   */
  class CardComparator implements Comparator<Card> {

    private final List<String> FACE_VALUES =
            new ArrayList<>(Arrays.asList("A", "2", "3", "4", "5", "6", "7", "8"
                    , "9", "10", "J", "Q", "K"));
    private final List<String> SUITS =
            new ArrayList<>(Arrays.asList("♠","♢","♣","♡"));

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
