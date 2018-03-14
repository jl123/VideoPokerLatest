import java.util.*;

public class VideoPoker
{
   public static void main (String[] args)
   {
      final boolean shuffle = false;

      String bet;
      int betInt;
      int credits = 1000;
      Scanner gameScanner = new Scanner(System.in);
      Deck theDeck = new Deck(shuffle);
      Hand playerHand = new Hand();

      System.out.println("Welcome to Video Poker. Enjoy!\n\n");
      HandEvaluator.oddsTable();
      System.out.println("YOUR CREDITS: " + credits);
      System.out.println("\n Enter bet amount:");
      bet = gameScanner.nextLine();
      betInt = Integer.parseInt(bet);
      credits = credits - betInt;
      for (int k = 0; k < Hand.MAX_CARDS; k++)
      {
         playerHand.takeCard(theDeck.dealCard());
         System.out.println("Card " +  (k + 1) + ": "
               + playerHand.inspectCard(k).toString());
      }

      System.out.println("HAND VALUE: " + HandEvaluator.getHandVal(playerHand));
      credits = credits + HandEvaluator.getHandVal(playerHand).winVal(betInt);
      System.out.println(playerHand.toString());
      String muckCard = "";

      int muckInt = 0;
      do
      {
         try
         {
            muckCard = gameScanner.nextLine();
            if ( !muckCard.isEmpty() )
               muckInt = Integer.parseInt(muckCard);
            muckInt--;
            if ( ( muckInt >= 0 && muckInt < Hand.MAX_CARDS  ) && !muckCard.isEmpty() )
               playerHand.switchCard[muckInt] = true;
            else if ( !muckCard.isEmpty() )
            {
               throw new NoCardException();
            }
         }
         catch (NumberFormatException|NoCardException ex)
         {
            System.out.println("Enter a card value between 1 and 5 to muck that "
                  + "card or 6 to stand");
         }
      }while (!muckCard.isEmpty());

      playerHand.draw(theDeck);

      System.out.println("HAND VALUE: " + HandEvaluator.getHandVal(playerHand));
      System.out.println(playerHand.toString());
      System.out.println("YOU WON: " +
            HandEvaluator.betReturn(HandEvaluator.getHandVal(playerHand), 5));
      System.out.println("REMAINING CREDITS: " + credits);

   }
}

class NoCardException extends Exception
{
   public NoCardException()
   {
      super("-----Int must be between 1 and 5------");
   }
}
