import java.util.Arrays;

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
      Arrays.fill(playerHand.switchCard, true);
      for (int k = 0; k < Hand.MAX_CARDS; k++)
      {
         //playerHand.switchCard[k] = true;
         try
         {
            playerHand.takeCard(deck.dealCard());
         }
         catch (OutOfCardsException e)
         {
            System.out.println(e.getMessage());
         }
      }
      return playerHand;
   }

   public Hand draw()
   {
      dealt = false;
      for (int k = 0; k < Hand.MAX_CARDS; k++)
      {
         if (playerHand.switchCard[k])
         {
            try
            {
               playerHand.replaceCard(k, deck.dealCard());
            }
            catch(OutOfCardsException e)
            {
               System.out.println(e.getMessage());
            }
         }

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