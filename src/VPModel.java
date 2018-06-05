import java.util.Arrays;

class VPModel
{
   private int bet;
   private int credits;
   private Deck deck;
   final Hand playerHand;
   private boolean dealt;

   VPModel()
   {
      bet = HandEvaluator.MAX_BET;
      credits = 1000;
      dealt = false;
      playerHand = new Hand();

   }

   void newHand()
   {
      dealt = true;
      deck = new Deck(true);
      playerHand.resetHand();
      credits -= bet;
      //resets all hold states to false;
      Arrays.fill(playerHand.switchCard, true);
      for (int k = 0; k < Hand.MAX_CARDS; k++)
      {
         try
         {
            playerHand.takeCard(deck.dealCard());
         }
         catch (EmptyDeckException e)
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
            catch(EmptyDeckException e)
            {
               System.out.println(e.getMessage());
            }
         }

      }
   }

   String getHandVal()
   {
      return HandEvaluator.getHandVal(playerHand).toString();
   }

   int evaluateHand()
   {
      int creditsWon = HandEvaluator.getHandVal(playerHand).winVal(bet);
      credits += creditsWon;
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

   static Hand getStartHand()
   {
      Hand royal = new Hand();
      royal.takeCard(new Card(Card.Rank.ten,Card.Suit.spades));
      royal.takeCard(new Card(Card.Rank.jack,Card.Suit.spades));
      royal.takeCard(new Card(Card.Rank.queen,Card.Suit.spades));
      royal.takeCard(new Card(Card.Rank.king,Card.Suit.spades));
      royal.takeCard(new Card(Card.Rank.ace,Card.Suit.spades));

      return royal;
   }

   boolean getDealt()
   {
      return dealt;
   }
}