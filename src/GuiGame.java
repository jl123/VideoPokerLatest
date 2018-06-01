import java.util.Arrays;
//Game model for later use.
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

   int evaluateHand()
   {
      int creditsWon = HandEvaluator.getHandVal(playerHand).winVal(bet);
      credits += HandEvaluator.getHandVal(playerHand).winVal(bet);
      return creditsWon;
   }

   int getCredits(){ return credits; }

   int getBet(){ return bet; }

   void setBet(int bet)
   {
      if ( bet >= HandEvaluator.MIN_BET && bet <= HandEvaluator.MAX_BET)
      {
         this.bet = bet;
      }
   }


   boolean getDealt()
   {
      return dealt;
   }
}