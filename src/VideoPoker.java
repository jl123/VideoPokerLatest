import java.util.*;

class VideoPoker
{
   public static void main (String[] args)
   {
      final boolean shuffle = true;
      Deck theDeck;
      String bet;
      int betInt = 5;
      int credits = 1000;
      int gameNum = 0;
      Scanner gameScanner = new Scanner(System.in);

      Hand playerHand;

      System.out.println("Welcome to Video Poker. Enjoy!\n\n");
      System.out.println(HandEvaluator.oddsTable());

      while( true )
      {
         gameNum++;
         theDeck = new Deck(shuffle);
         playerHand = new Hand();
         System.out.println("GAME NUMBER: " + gameNum);
         System.out.println("YOUR CREDITS: " + credits);
         System.out.println("CURRENT BET: " + betInt);

         do
         {
            System.out.println("\nHit ENTER to continue with current bet amount, or enter amount (1-5) to change bet amount, or 'X' to exit, then press ENTER to play:");
            try
            {
                     bet = gameScanner.nextLine();
                     if ( bet.equals("X") || bet.equals("x"))
                        System.exit(0);
                     else if(bet.isEmpty())
                        break;
                     else
                        betInt = Integer.parseInt(bet);

            } catch (NumberFormatException e)
            {
               System.out.println("Enter a card value between 1 and 5, then press ENTER to HOLD it. "
                     + "Press ENTER again when ready to draw.");
            }
         } while (betInt < 1 || betInt > 5);
         credits = credits - betInt;
         for (int k = 0; k < Hand.MAX_CARDS; k++)
         {
            try
            {
               playerHand.takeCard(theDeck.dealCard());
            }
            catch (EmptyDeckException e)
            {
               System.out.println(e.getMessage());
            }
            System.out.println("Card " + (k + 1) + ": "
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
               System.out.println("Choose a card to discard (1-5) and hit ENTER. HIT ENTER WITH NO NUMBER TO DRAW CARDS.");
               muckCard = gameScanner.nextLine();
               if (!muckCard.isEmpty())
                  muckInt = Integer.parseInt(muckCard);
               muckInt--;
               if ((muckInt >= 0 && muckInt < Hand.MAX_CARDS) && !muckCard.isEmpty())
               {
                  playerHand.switchCard[muckInt] = !playerHand.switchCard[muckInt];
                  for (int k = 0; k < Hand.MAX_CARDS; k++)
                  System.out.println("Card " + (k + 1) + ": "
                        + playerHand.inspectCard(k).toString() + "   DISCARD: "
                        + playerHand.switchCard[k]);
               }
               else if (!muckCard.isEmpty())
               {
                  throw new NoCardException();
               }
            } catch (NumberFormatException | NoCardException ex)
            {
               System.out.println("Enter a card value between 1 and 5 to muck that "
                     + "card or 6 to stand");
            }
         } while ( !muckCard.isEmpty() );

         playerHand.draw(theDeck);

         System.out.println("HAND VALUE: " + HandEvaluator.getHandVal(playerHand));
         System.out.println(playerHand.toString());
         System.out.println("YOU WON: " +
               HandEvaluator.getHandVal(playerHand).winVal(betInt));
         System.out.println("REMAINING CREDITS: " + credits + "\n");
      }
   }
}

class NoCardException extends Exception
{
   NoCardException()
   {
      super( "-----Int must be between 1 and 5------" );
   }
}
