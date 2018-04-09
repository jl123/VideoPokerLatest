import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class GuiGame
{
   final boolean shouldShuffle = true;

   private int bet;
   private int credits;
   private Deck deck;
   Hand playerHand;
   private boolean dealt;


   public GuiGame()
   {
      bet = 5;
      credits = 1000;
      playerHand = new Hand();
      dealt = false;
   }

   public Hand newHand()
   {
      dealt = true;
      deck = new Deck(shouldShuffle);
      playerHand.resetHand();
      credits -= bet;
      for (int k = 0; k < Hand.MAX_CARDS; k++)
      {
         playerHand.switchCard[k] = true;
         playerHand.takeCard(deck.dealCard());
      }
      return playerHand;
   }

   public Hand draw()
   {
      dealt = false;
      for (int k = 0; k < Hand.MAX_CARDS; k++)
      {
         if (playerHand.switchCard[k])
            playerHand.replaceCard(k, deck.dealCard());
      }
      return playerHand;
   }

   public int evaluateHand()
   {
      int creditsWon = HandEvaluator.getHandVal(playerHand).winVal(bet);
      System.out.println("WON: " + String.valueOf(creditsWon));
      credits += HandEvaluator.getHandVal(playerHand).winVal(bet);
      return creditsWon;
   }

   public int getCredits(){ return credits; }

   public int getBet(){ return bet; }

   public boolean setBet(int bet)
   {
      if ( bet >= HandEvaluator.MIN_BET && bet <= HandEvaluator.MAX_BET)
      {
         this.bet = bet;
         return true;
      }
      return false;
   }


   public boolean getDealt()
   {
      return dealt;
   }
}