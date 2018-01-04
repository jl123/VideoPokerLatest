import java.util.*;

public class VideoPoker
{
   public static void main (String[] args)
   {
      final boolean shuffle = false;

      Scanner gameScanner = new Scanner(System.in);
      Deck theDeck = new Deck(shuffle);
      Hand playerHand = new Hand();
      
      for (int k = 0; k < Hand.MAX_CARDS; k++)
      {
         playerHand.takeCard(theDeck.dealCard());
         System.out.println("Card " +  (k + 1) + ": "
               + playerHand.inspectCard(k).toString());
      }

      System.out.println("HAND VALUE: " + HandEvaluator.getHandVal(playerHand));

      System.out.println(playerHand.toString());
      String muckCard = "";
      int muckInt;
      do
      {
         try
         {
         muckCard = gameScanner.nextLine();
         muckInt = Integer.parseInt(muckCard);
         muckInt--;
         if ( ( muckInt >= 0 && muckInt < Hand.MAX_CARDS  ) )
            playerHand.switchCard[muckInt] = true;
         else if ( muckCard != "" )
            throw new NoCardException();
         }
         catch (NumberFormatException|NoCardException ex)
         {
            System.out.println("Enter a card value between 1 and 5 to muck that "
                  + "card or 6 to stand");
         }
      }while (!muckCard.equals(""));
      
      playerHand.draw(theDeck);

      System.out.println("HAND VALUE: " + HandEvaluator.getHandVal(playerHand));
      System.out.println(playerHand.toString());

   }
}

class NoCardException extends Exception
{
   public NoCardException()
   {
      super("-----Int must be between 1 and 5------");
   }
}
