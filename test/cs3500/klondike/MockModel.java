package cs3500.klondike;

import java.util.List;

import cs3500.klondike.model.hw02.Card;
import cs3500.klondike.model.hw02.KlondikeModel;


/**
 * mock model used to test the outputs of the model.
 */
public class MockModel implements KlondikeModel {

  private StringBuilder log;

  /**
   * mock model constructor to create a log.
   * @param log log of the controller commands.
   */
  public MockModel(StringBuilder log) {
    this.log = log;
  }

  @Override
  public List<Card> getDeck() {
    return null;
  }

  @Override
  public void startGame(List<Card> deck, boolean shuffle, int numPiles, int numDraw)
          throws IllegalArgumentException, IllegalStateException {
    log.append("startGame called with shuffle=" + shuffle
            + ", numPiles=" + numPiles + ", numDraw=" + numDraw + "\n");
  }

  @Override
  public void movePile(int srcPile, int numCards, int destPile)
          throws IllegalArgumentException, IllegalStateException {
    log.append(String.format("movePile(srcPile=" + srcPile + ", numCards="
                    + numCards + ", destPile=" + destPile + ")" + "called\n"));
  }

  @Override
  public void moveDraw(int destPile)
          throws IllegalArgumentException, IllegalStateException {
    log.append("moveDraw(destPile=" + destPile + ") called\n");
  }

  @Override
  public void moveToFoundation(int srcPile, int foundationPile)
          throws IllegalArgumentException, IllegalStateException {
    log.append(String.format("moveToFoundation(srcPile=%d, foundationPile=%d) called\n",
            srcPile, foundationPile));
  }

  @Override
  public void moveDrawToFoundation(int foundationPile)
          throws IllegalArgumentException, IllegalStateException {
    log.append("moveDrawToFoundation(foundationPile=" + foundationPile + ") called\n");
  }

  @Override
  public void discardDraw() throws IllegalStateException {
    log.append("discardDraw() called\n");
  }

  @Override
  public int getNumRows() throws IllegalStateException {
    return 0;
  }

  @Override
  public int getNumPiles() throws IllegalStateException {
    return 0;
  }

  @Override
  public int getNumDraw() throws IllegalStateException {
    return 0;
  }

  // ... Other methods are left unchanged, returning default values as you had them ...

  @Override
  public boolean isGameOver() throws IllegalStateException {
    log.append("isGameOver() called\n");
    return false;  // or true if you want to end the game immediately
  }

  @Override
  public int getScore() throws IllegalStateException {
    return 0;
  }

  @Override
  public int getPileHeight(int pileNum) throws IllegalArgumentException, IllegalStateException {
    return 0;
  }

  @Override
  public boolean isCardVisible(int pileNum, int card)
          throws IllegalArgumentException, IllegalStateException {
    return false;
  }

  @Override
  public Card getCardAt(int pileNum, int card)
          throws IllegalArgumentException, IllegalStateException {
    return null;
  }

  @Override
  public Card getCardAt(int foundationPile)
          throws IllegalArgumentException, IllegalStateException {
    return null;
  }

  @Override
  public List<Card> getDrawCards() throws IllegalStateException {
    return null;
  }

  @Override
  public int getNumFoundations() throws IllegalStateException {
    return 0;
  }
}
