package Hand;

import Deck.Card;
import Deck.Deck;
import Deck.EmptyDeckException;
import Deck.CardImageUtils;

import java.util.*;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Hand
{
   public static final int MAX_CARDS = 5;

   private final List<Card> hand;
   private final List<Card> sortedHand;
   private final boolean[] switchCard;


   //constructor
   public Hand()
   {
      hand = new java.util.ArrayList<>();
      sortedHand = new LinkedList<>();
      switchCard = new boolean[Hand.MAX_CARDS];
      resetHand();
   }

   public void resetHand()
   {
      hand.removeAll(hand);  //intentional remove all cards from hand
      Arrays.fill(switchCard, true);
   }

   public void takeCard(Card card)
   {
      if (hand.size() == MAX_CARDS) { return; }

      hand.add(card);
      insert(sortedHand, card);
   }

   public void replaceCard(int cardNum, Card newCard)
   {
      if (cardNum >= 0 && cardNum < hand.size())
      {
         sortedHand.remove(hand.get(cardNum));
         hand.set(cardNum,newCard);
         insert(sortedHand, newCard);
      }
   }

   public void draw(Deck deck)
   {
      for (int k = 0; k < MAX_CARDS; k++)
      {
         if (switchCard[k])
         {
            Card tempCard = null;
            try
            {
               tempCard = deck.dealCard();
            } catch (EmptyDeckException e)
            {
               System.out.println(e.getMessage());
            }
            remove(sortedHand, this.inspectCard(k));
            this.replaceCard(k, tempCard);
            insert(sortedHand, tempCard);
         }
      }
   }

   @Override
   public String toString()
   {
      ListIterator<Card> listIterator = hand.listIterator();
      StringBuilder retStr = new StringBuilder("Hand.Hand = ( ");
      do
      {
         retStr.append(listIterator.hasNext() ? listIterator.next().toString() + ", " : " )");
      }while (listIterator.hasNext());

      return retStr.toString();
   }


   public ArrayList<String> cardImgStrList()
   {
      return hand.stream().map(CardImageUtils::getImageStr).collect(Collectors.toCollection(ArrayList::new));
   }

   //accessor for numCards
   public int getNumCards()
   {
      return hand.size();
   }

   public Card inspectCard(int k) { return hand.get(k); }

   //helper methods for LinkedList
   private static void insert(List<Card> cardList, Card card)
   {
      ListIterator<Card> iterator;
      Card listX;
      if (card == null)
      {
         return;
      }

      for (iterator = cardList.listIterator(); iterator.hasNext(); )
      {
         listX = iterator.next();
         if (card.compareTo(listX) < 0)
         {
            iterator.previous(); // back up one
            break;
         }
      }
      iterator.add(card);     //add copy to LinkedList+
   }

   //REMOVE
   private static boolean remove(List<Card> cardList, Card card)
   {
      ListIterator<Card> iterator;
      for (iterator = cardList.listIterator(); iterator.hasNext(); )
      {
         if (card.compareTo(iterator.next()) == 0)
         {
            iterator.remove();
            return true;   // we found, we removed, we return
         }
      }
      return false;
   }

   //REMOVEALL
   static boolean removeAll(LinkedList<Card> cardList, Card card)
   {
      boolean retFlag = false, shouldContinue = true;

      while (shouldContinue)
      {
         if (remove(cardList, card))
            retFlag = true;
         else shouldContinue = false;
      }
      return retFlag;
   }

   public boolean getSwitchCard(int i) throws IndexOutOfBoundsException
   {
         return switchCard[i];
   }

   public boolean setSwitchCard(int i, boolean setRemove)
   {
      switchCard[i] = setRemove;
      return switchCard[i];
   }
}