package Deck;

import java.util.Objects;

public class Card implements Comparable<Card>
{   
   private final Suit suit;
   private final Rank rank;
   
   public Card(Rank rank, Suit suit)
   {
      this.rank = rank;
      this.suit = suit;
   }

   // static class constants (for suits)
   public enum Suit { clubs , diamonds, hearts, spades }

   public enum Rank
   {
      two (2), three (3), four(4), five (5), six (6), seven (7), eight (8),
      nine (9), ten (10), jack (11), queen (12), king (13), ace (14);

      public final int value;

      Rank(final int value) { this.value = value; }

      public int getValue() { return this.value; }
   }

   // accessors
   public Rank getRank() { return this.rank; }

   public Suit getSuit() { return this.suit; }
   
   @Override
   public String toString()
   {
   if (rank.value < 11)
      return this.rank.value + " of " + this.suit;
   else return this.rank + " of " + this.suit;
   }

   @Override
   public boolean equals(Object o)
   {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Card card = (Card) o;
      return this.suit == card.suit &&
            this.rank == card.rank;
   }

   @Override
   public int hashCode()
   {
      return Objects.hash(suit, rank);
   }

   @Override
   public int compareTo(Card other)
   {
      // suit comparison for games where suits have value
      //if (this.getRank() == other.getRank())
      //   return this.suit.ordinal() - other.suit.ordinal();
      return this.rank.value - other.rank.value;
   }
}