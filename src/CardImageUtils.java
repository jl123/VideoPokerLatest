import javafx.scene.image.Image;
public class CardImageUtils
{
   private CardImageUtils()
   {
      throw new RuntimeException("No no Peanut");
   }

   public static String getImage(Card card)
   {
      return rankToChar(card.getRank()) + suitToChar(card.getSuit()) + ".gif";
   }

   private static char suitToChar(Card.Suit suit)
   {
      if (suit == Card.Suit.hearts)
      {
         return 'H';
      }
      else if (suit == Card.Suit.diamonds)
      {
         return 'D';
      }
      else if (suit == Card.Suit.spades)
      {
         return 'S';
      }
      else if (suit == Card.Suit.clubs)
      {
         return 'C';
      }
      return 0;
   }

   private static String rankToChar(Card.Rank rank)
   {
      if (rank.ordinal() < 8)
      {
         return String.valueOf(rank.getValue());
      }
      else if (rank == Card.Rank.ten)
      {
         return "T";
      }
      else if (rank == Card.Rank.jack)
      {
         return "J";
      }
      else if (rank == Card.Rank.queen)
      {
         return "Q";
      }
      else if (rank == Card.Rank.king)
      {
         return "K";
      }
      else if (rank == Card.Rank.ace)
      {
         return "A";
      }
      return "0";
   }
}
