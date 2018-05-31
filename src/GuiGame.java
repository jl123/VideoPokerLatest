import java.util.Arrays;

class GuiGame
{

   private int bet;
   private int credits;
   private Deck deck;
   final Hand playerHand = new Hand();
   private boolean dealt;


   GuiGame()
   {
      bet = 5;
      credits = 1000;
      dealt = false;
   }

   void newHand()
   {
      dealt = true;
      deck = new Deck(true);
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
   }

   void draw()
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

   public void setBet(int bet)
   {
      if ( bet >= HandEvaluator.MIN_BET && bet <= HandEvaluator.MAX_BET)
      {
         this.bet = bet;
      }
   }


   public boolean getDealt()
   {
      return dealt;
   }
}