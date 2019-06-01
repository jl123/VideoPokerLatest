import Deck.CardImageUtils;

import java.util.ArrayList;

import static java.lang.Thread.sleep;

class VPController
{
   private final VPModel game;

   private final VPView view;
   private static ArrayList<String> cardBacks;

   VPController(VPModel game, VPView view)
   {
      this.game = game;
      this.view = view;;
   }

   public void dealDraw()
   {
      if (game.getInGameStatus())
      {
         processDraw();
      }
      else
      {
         processDeal();
      }
   }

   private void processDeal()
   {
      view.resetTable();
      int oldCredits = getCredits();
      if (game.newHand())
      {
         view.updateCredits(getCredits(), oldCredits, getMinBet(), null);
         view.disableHoldButtons(false);
         view.disableBetButtons(true);

         try
         {
            sleep(300);
         } catch (InterruptedException e)
         {
            e.printStackTrace();
         }

         view.updateCards(game.getHand());
      }

   }

   private void processDraw()
   {
      int oldCredits = getCredits();
      int won = game.draw();
      view.updateWon(won, getMinBet());
      view.updateCredits(getCredits(), oldCredits, getMinBet(), null);

      view.disableHoldButtons(true);
      view.disableBetButtons(false);
      view.updateCards(game.getHand());


      view.endGameLabelsOn(getHandVal());
   }

   boolean processHold(int cardNum)
   {
      return game.holdCard(cardNum);
   }

   boolean incrementBet()
   {
      view.resetTable();
      int oldCredits = getCredits();
      if (game.incrementBet())
      {
         view.updateBet(game.getBet());
         view.updateCredits(game.getCredits(), oldCredits, VPModel.MIN_BET, "aaa");
         return true;
      }
      return false;
   }

   boolean maxBet()
   {
      boolean incremented = incrementBet();
      while (true)
      {
         if (!incrementBet()) break;
      }
      return incremented;
   }

   static int getMaxCards() { return VPModel.getMaxCards(); }
   static int getMinBet() { return VPModel.MIN_BET; }
   static int getMaxBet() { return VPModel.MAX_BET; }
   static int getNumBets() { return  VPModel.NUM_BETS; }

   boolean getInGameStatus() { return game.getInGameStatus(); }
   int getBet() { return game.getBet(); }
   int getCredits() { return game.getCredits(); }


   static ArrayList<String> getCardBacks()
   {
      cardBacks = cardBacks == null ?
            CardImageUtils.getCardBackImageStringList(VPController.getMaxCards()) : cardBacks;
      return  cardBacks;
   }
   protected ArrayList<ArrayList<String>> getWinTable()
   {
      return game.getWinValList();
   }

   protected String getHandVal() {  return game.getHandValStr(); }
}