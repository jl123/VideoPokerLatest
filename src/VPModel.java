import Deck.CardImageUtils;
import Deck.Deck;
import Deck.EmptyDeckException;
import Hand.Hand;
import Hand.HandEvaluator;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class VPModel
{
   static final int NUM_BETS = 5;
   static final int MAX_BET = 5;
   static final int MIN_BET = MAX_BET / NUM_BETS;


   private int bet;
   private int credits;
   private Deck deck;
   private final Hand playerHand;
   private boolean inGame = false;
   private boolean usedIncrement;
   private boolean betChanged;
   private int creditsWon;

   VPModel()
   {
      bet = 0;
      credits = 1000;
      playerHand = new Hand();
   }
/////////////////////////////////////////NEED TO ADD CHECK TO SEE IF CREDITS ALREADY DEDUCTED WHEN PLAY PUSHED
   boolean newHand()
   {
      inGame = true;

      creditsWon = -1;
      deck = new Deck();
      playerHand.resetHand();
      if (!usedIncrement)
      {

         if (bet <= credits)
         {
            credits -= bet;
         } else
         {
            bet = credits;
            credits = 0;
         }
      }

      if (bet == 0)
      {
         inGame = false;
         return false;
      }
      //resets all hold states to false;

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
      return true;
   }

   int draw()
   {
      if (!inGame) {return 0;}

      for (int k = 0; k < Hand.MAX_CARDS; k++)
      {
         if (playerHand.getSwitchCard(k))
         {
            try
            {
               playerHand.replaceCard(k, deck.dealCard());
               playerHand.setSwitchCard(k,false);
            }
            catch(EmptyDeckException e)
            {
               System.out.println(e.getMessage());
            }
         }
      }

      int won = evaluateHand();
      inGame = false;
      usedIncrement = false;
      return won;
   }

   String getHandValStr()
   {
      HandEvaluator.handVal handV = HandEvaluator.getHandVal(playerHand);
      return handV == HandEvaluator.handVal.LOSER ? "" : handV.toString();
   }

   private int evaluateHand()
   {
      int creditsWon = HandEvaluator.getHandVal(playerHand).winVal(bet);
      credits += creditsWon;
      return creditsWon;
   }

   boolean incrementBet()
   {
      if (inGame || getCredits() < MIN_BET || (usedIncrement && bet == MAX_BET ))
      {
         return false;
      }

      bet = usedIncrement ? bet += MIN_BET : MIN_BET;
      credits -= MIN_BET;
      usedIncrement = true;

      return true;
   }

   boolean maxBet()
   {
      boolean incr = false;
      while (incrementBet()) { incr = true; }
      return incr;
   }

   ArrayList<String> getHand()
   {
      return Stream.iterate(0, i -> i + 1).limit(Hand.MAX_CARDS)
            .map(i -> CardImageUtils.getImageStr(playerHand.inspectCard(i)))
            .collect(Collectors.toCollection(ArrayList::new));
   }

   public boolean holdCard(int i)
   {
      if (!inGame) { return true; }

      return !playerHand.setSwitchCard(i, !playerHand.getSwitchCard(i));
   }
   
   int getCredits(){ return credits; }
   int getBet(){ return bet; }
   boolean getInGameStatus() { return inGame; }
   public int getCreditsWon() { return creditsWon; }

   static int getMaxCards() { return Hand.MAX_CARDS; }
   
   ArrayList<ArrayList<String>> getWinValList()
   {
      //consider using HashMap
      ArrayList<ArrayList<String>> winTableList = new ArrayList<>();
      for (HandEvaluator.handVal hVal : HandEvaluator.handVal.values())
      {
         if (hVal != HandEvaluator.handVal.LOSER)
         {
            ArrayList<String> winList = new ArrayList<>();
            winTableList.add(winList);
            winList.add(hVal.toString());

            for (int n = 1; n <= NUM_BETS; n++)
            {
               winList.add(String.valueOf(hVal.winVal(n * MIN_BET)));
            }
         }
      }
      return winTableList;
   }
}

