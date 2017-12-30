import java.util.*;

public class VideoPoker
{
   public static void main (String[] args)
   {
      Scanner gameScanner = new Scanner(System.in);
      Deck theDeck = new Deck(false);
      Hand playerHand = new Hand();
      boolean[] switchCard = new boolean[Hand.MAX_CARDS];
      LinkedList<Card> sortedHand = new LinkedList<Card>();
      int cardNum;
      Card tempCard;
      int a = 1, b =1, c = 3, d = 4, asa = 5;
      
      System.out.println(a == b && c == d || asa == 5);
      
      for (int k = 0; k < Hand.MAX_CARDS; k++)
      {
         cardNum = k + 1;
         tempCard = theDeck.dealCard();
         playerHand.takeCard(tempCard);
         insert(sortedHand, tempCard);
         System.out.println("Card " +  cardNum + ": " 
               + playerHand.inspectCard(k).toString());
         
      }
      
      Card[] testCard = new Card[5];
      
      testCard[0] = new Card(Card.Rank.ten, Card.Suit.clubs);
      testCard[1] = new Card(Card.Rank.queen, Card.Suit.diamonds);
      testCard[2] = new Card(Card.Rank.queen, Card.Suit.diamonds);
      testCard[3] = new Card(Card.Rank.queen, Card.Suit.clubs);
      testCard[4] = new Card(Card.Rank.queen, Card.Suit.hearts);
      
      remove(sortedHand, playerHand.inspectCard(0));
      playerHand.replaceCard(0, testCard[0]);
      insert(sortedHand, testCard[0]);
      
      remove(sortedHand, playerHand.inspectCard(1));
      playerHand.replaceCard(1, testCard[1]);
      insert(sortedHand, testCard[1]);
      
      remove(sortedHand, playerHand.inspectCard(2));
      playerHand.replaceCard(2, testCard[2]);
      insert(sortedHand, testCard[2]);
      
      remove(sortedHand, playerHand.inspectCard(3));
      playerHand.replaceCard(3, testCard[3]);
      insert(sortedHand, testCard[3]);
      
      remove(sortedHand, playerHand.inspectCard(4));
      playerHand.replaceCard(4, testCard[4]);
      insert(sortedHand, testCard[4]);
      
      
      
      System.out.println("HAND VALUE: " + HandVal.getHandVal(sortedHand));
      System.out.println("Full House? " +  HandVal.isFullHouse(sortedHand));
      System.out.println("Staight? " +  HandVal.isStraight(sortedHand));
      System.out.println("Flush? " +  HandVal.isFlush(sortedHand));
      System.out.println("Royal? " +  HandVal.isRoyalFlush(sortedHand));
      System.out.println("Set? " +  HandVal.isSet(sortedHand));
      System.out.println("Pair? " +  HandVal.isPairJorHigher(sortedHand));
      System.out.println(playerHand.toString());
      System.out.println(sortedHand.toString());
      String muckCard = "0";
      int muckInt;
      do
      {
         try
         {
         muckCard = gameScanner.nextLine();
         if (muckCard.equals("6"))
            muckInt = Integer.parseInt(muckCard);
         else muckInt = Integer.parseInt(muckCard);
         muckInt--;
         if (muckInt >= 0 && muckInt < Hand.MAX_CARDS )
            switchCard[muckInt] = true;
         else if (muckCard.equals("6"))
            throw new NoCardException();
         }
         catch (NumberFormatException|NoCardException ex)
         {
            System.out.println("Enter a card value between 1 and 5 to muck that "
                  + "card or 6 to stand");
         }
      }while (!muckCard.equals("6"));
      
      for (int k = 0; k < Hand.MAX_CARDS; k++)
      {
         if (switchCard[k])
         {
            tempCard = theDeck.dealCard();
            remove(sortedHand, playerHand.inspectCard(k));
            playerHand.replaceCard(k, tempCard);
            insert(sortedHand, tempCard);
         }
      }
      System.out.println(playerHand.toString());
      System.out.println(sortedHand.toString());
   }
   static void insert(LinkedList<Card> cardList, Card card)
   {
      ListIterator<Card> iter;
      Card listX;
      if (card == null)
      {
         return;
      }
      
      for (iter = cardList.listIterator(); iter.hasNext(); )
      {
        listX = iter.next();
        if (card.compareTo(listX) < 0)
        {
           iter.previous(); // back up one
           break;
        }
      }
      iter.add(card);     //add copy to LinkedList+
   }
   
   //REMOVE
   static boolean remove(LinkedList<Card> cardList, Card card)
   {
      ListIterator<Card> iter;
      for (iter = cardList.listIterator(); iter.hasNext(); )
      {
         if (card.compareTo(iter.next()) == 0)
         {
            iter.remove();
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
}

class NoCardException extends Exception
{
   public NoCardException()
   {
      super("-----Int must be between 1 and 5------");
   }
}
