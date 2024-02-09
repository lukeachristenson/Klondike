package cs3500.klondike;

import org.junit.Assert;
import org.junit.Test;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import cs3500.klondike.controller.KlondikeController;
import cs3500.klondike.controller.KlondikeTextualController;
import cs3500.klondike.model.hw02.BasicKlondike;
import cs3500.klondike.model.hw02.Card;
import cs3500.klondike.model.hw02.KlondikeModel;

/**
 * New tests for the controller. The titles of tests describe what they test.
 */
public class NewControllerTests {

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
   * many tests below use a mock to test controller output.
   */
  @Test
  public void testMovePile() {
    StringBuilder log = new StringBuilder();
    MockModel mockModel = new MockModel(log);
    StringBuilder out = new StringBuilder();
    StringReader in = new StringReader("MPP 1 2 3 q");

    KlondikeTextualController controller = new KlondikeTextualController(in, out);
    controller.playGame(mockModel, null, false, 7, 3);

    String expected = "movePile(srcPile=0, numCards=3, destPile=2) called\n";
    Assert.assertEquals(expected, log.toString());
  }

  @Test
  public void testMoveDraw() {
    StringBuilder log = new StringBuilder();
    MockModel mockModel = new MockModel(log);
    StringBuilder out = new StringBuilder();
    StringReader in = new StringReader("MD 1 q");

    KlondikeTextualController controller = new KlondikeTextualController(in, out);
    controller.playGame(mockModel, null, false, 7, 3);

    String expected = "moveDraw(destPile=0) called\n";
    Assert.assertEquals(expected, log.toString());
  }

  @Test
  public void testMoveToFoundation() {
    StringBuilder log = new StringBuilder();
    MockModel mockModel = new MockModel(log);
    StringBuilder out = new StringBuilder();
    StringReader in = new StringReader("MPF 1 2 q");

    KlondikeTextualController controller = new KlondikeTextualController(in, out);
    controller.playGame(mockModel, null, false, 7, 3);

    String expected = "moveToFoundation(srcPile=0, foundationPile=1) called\n";
    Assert.assertEquals(expected, log.toString());
  }

  @Test
  public void testMoveDrawToFoundation() {
    StringBuilder log = new StringBuilder();
    MockModel mockModel = new MockModel(log);
    StringBuilder out = new StringBuilder();
    StringReader in = new StringReader("MDF 1 q");

    KlondikeTextualController controller = new KlondikeTextualController(in, out);
    controller.playGame(mockModel, null, false, 7, 3);

    String expected = "moveDrawToFoundation(foundationPile=0) called\n";
    Assert.assertEquals(expected, log.toString());
  }

  @Test
  public void testDiscardDraw() {
    StringBuilder log = new StringBuilder();
    MockModel mockModel = new MockModel(log);
    StringBuilder out = new StringBuilder();
    StringReader in = new StringReader("DD q");

    KlondikeTextualController controller = new KlondikeTextualController(in, out);
    controller.playGame(mockModel, null, false, 7, 3);

    String expected = "discardDraw() called\n";
    Assert.assertEquals(expected, log.toString());
  }

  @Test
  public void testIsGameOver() {
    StringBuilder log = new StringBuilder();
    MockModel mockModel = new MockModel(log) {
      @Override
      public boolean isGameOver() {
        log.append("isGameOver() called\n");
        return true;
      }
    };
    StringBuilder out = new StringBuilder();
    StringReader in = new StringReader("q");

    KlondikeTextualController controller = new KlondikeTextualController(in, out);
    controller.playGame(mockModel, null, false, 7, 3);

    String expected = "isGameOver() called\n";
    Assert.assertEquals(expected, log.toString());
  }

  @Test
  public void testNullInput() {
    Assert.assertThrows(IllegalArgumentException.class, () -> {
      new KlondikeTextualController(null, new StringWriter());
    });
  }

  @Test
  public void testNullOutput() {
    Assert.assertThrows(IllegalArgumentException.class, () -> {
      new KlondikeTextualController(new StringReader(""), null);
    });
  }

  @Test
  public void testNullModel() {
    KlondikeTextualController controller =
            new KlondikeTextualController(new StringReader(""), new StringWriter());
    Assert.assertThrows(IllegalArgumentException.class, () -> {
      controller.playGame(null, new ArrayList<>(), false, 7, 3);
    });
  }

  @Test
  public void testNoMoreInputGiven() {
    KlondikeTextualController controller =
            new KlondikeTextualController(new StringReader(""), new StringWriter());
    BasicKlondike model = new BasicKlondike();
    IllegalStateException e = Assert.assertThrows(IllegalStateException.class, () -> {
      controller.playGame(model, new ArrayList<>(), false, 7, 3);
    });
    Assert.assertTrue(e.getMessage().contains("no more input given"));
  }

  @Test
  public void testBadInputForMppCommand() {
    Readable in = new StringReader("mpp a 1 2 q");
    Appendable out = new StringBuilder();
    KlondikeController controller = new KlondikeTextualController(in, out);
    KlondikeModel km = new BasicKlondike();
    List<Card> deck = km.getDeck();
    controller.playGame(km, deck, false, 7, 3);
    Assert.assertTrue(out.toString().contains("Invalid move."));
  }

  @Test
  public void testIncompleteInputForMpfCommand() {
    Readable in = new StringReader("mpf 1 q");
    Appendable out = new StringBuilder();
    KlondikeController controller = new KlondikeTextualController(in, out);
    KlondikeModel km = new BasicKlondike();
    List<Card> deck = km.getDeck();
    controller.playGame(km, deck, false, 7, 3);
    Assert.assertTrue(out.toString().contains("Invalid move."));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullReadableConstructor() {
    new KlondikeTextualController(null, new StringBuilder());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullAppendableConstructor() {
    new KlondikeTextualController(new StringReader(""), null);
  }

  @Test
  public void testQuitCommand() {
    Readable in = new StringReader("q");
    Appendable out = new StringBuilder();
    KlondikeTextualController controller = new KlondikeTextualController(in, out);
    KlondikeModel km = new BasicKlondike();
    List<Card> deck = km.getDeck();
    controller.playGame(km, deck, false, 7, 3);
    Assert.assertTrue(out.toString().contains("Game quit!"));
  }

  @Test
  public void testQuitGame() {
    Readable in = new StringReader("q");
    StringBuilder out = new StringBuilder();
    KlondikeController controller = new KlondikeTextualController(in, out);
    KlondikeModel km = new BasicKlondike();
    List<Card> deck = km.getDeck();
    controller.playGame(km, deck, false, 7, 3);
    Assert.assertTrue(out.toString().contains("Game quit!"));
  }

  @Test
  public void testInputAfterQuit() {
    Readable in = new StringReader("q MPP 1 2 3");
    StringBuilder out = new StringBuilder();
    KlondikeController controller = new KlondikeTextualController(in, out);
    KlondikeModel km = new BasicKlondike();
    List<Card> deck = km.getDeck();
    controller.playGame(km, deck, false, 7, 3);
    Assert.assertTrue(out.toString().contains("Game quit!"));
    Assert.assertFalse(out.toString().contains("Invalid move. Play again"));
  }

  @Test
  public void testGameOverNotWon() {
    Readable in =
            new StringReader("MPP 1 2 3 MD 1 MPF 2 1 MPP 2 3 2 " +
                    "MD 1 MPF 1 1 DD MPP 4 3 3 MD 4 MDF 4 q");
    StringBuilder out = new StringBuilder();
    KlondikeController controller = new KlondikeTextualController(in, out);
    KlondikeModel km = new BasicKlondike();
    List<Card> deck = km.getDeck().subList(0,10);
    controller.playGame(km, deck, false, 3, 3);
    Assert.assertTrue(out.toString().contains("Game over. Score:"));
    Assert.assertFalse(out.toString().contains("You win!"));
  }

  @Test
  public void testGameWon() {
    Readable in =
            new StringReader("MPP 1 2 3 MD 4 MDF 2 MPF 3 4 " +
                    "MPP 3 2 2 MD 2 DD MPP 4 2 3 MD 3 q");
    StringBuilder out = new StringBuilder();
    KlondikeController controller = new KlondikeTextualController(in, out);
    KlondikeModel km = new BasicKlondike();
    List<Card> deck = km.getDeck().subList(0,5);
    controller.playGame(km, deck, false, 3, 3);
    Assert.assertTrue(out.toString().contains("You win!"));
  }

  @Test
  public void testMovePileCascadeToCascade() {
    Readable in = new StringReader("mpp 2 3 4 q");
    StringBuilder out = new StringBuilder();
    KlondikeController controller = new KlondikeTextualController(in, out);
    KlondikeModel km = new BasicKlondike();
    List<Card> deck = km.getDeck();
    controller.playGame(km, deck, false, 7, 3);
    Assert.assertFalse(out.toString().contains("Invalid move. Play again"));
  }

  @Test
  public void testMoveDrawToCascade() {
    Readable in = new StringReader("md 3 q");
    StringBuilder out = new StringBuilder();
    KlondikeController controller = new KlondikeTextualController(in, out);
    KlondikeModel km = new BasicKlondike();
    List<Card> deck = km.getDeck();
    controller.playGame(km, deck, false, 7, 3);
    Assert.assertFalse(out.toString().contains("Invalid move. Play again"));
  }

  @Test
  public void testMoveCascadeToFoundation() {
    Readable in = new StringReader("mpf 1 2 q");
    StringBuilder out = new StringBuilder();
    KlondikeController controller = new KlondikeTextualController(in, out);
    KlondikeModel km = new BasicKlondike();
    List<Card> deck = km.getDeck();
    controller.playGame(km, deck, false, 7, 3);
    Assert.assertFalse(out.toString().contains("Invalid move. Play again"));
  }

  @Test
  public void testMoveDrawToFoundationTwo() {
    Readable in = new StringReader("mdf 2 q");
    StringBuilder out = new StringBuilder();
    KlondikeController controller = new KlondikeTextualController(in, out);
    KlondikeModel km = new BasicKlondike();
    List<Card> deck = km.getDeck();
    controller.playGame(km, deck, false, 7, 3);
    Assert.assertFalse(out.toString().contains("Invalid move. Play again"));
  }

  @Test
  public void testDiscardDrawWorkingWithoutGarbage() {
    Readable in = new StringReader("dd");
    StringBuilder out = new StringBuilder();
    KlondikeController controller = new KlondikeTextualController(in, out);
    KlondikeModel km = new BasicKlondike();
    List<Card> deck = km.getDeck();
    controller.playGame(km, deck, false, 7, 3);
    Assert.assertFalse(out.toString().contains("Invalid move. Play again"));
  }

  @Test
  public void testInvalidInput() {
    Readable in = new StringReader("xyz 1 2");
    StringBuilder out = new StringBuilder();
    KlondikeController controller = new KlondikeTextualController(in, out);
    KlondikeModel km = new BasicKlondike();
    List<Card> deck = km.getDeck();
    controller.playGame(km, deck, false, 7, 3);
    Assert.assertTrue(out.toString().contains("Invalid move. Play again"));
  }
}
