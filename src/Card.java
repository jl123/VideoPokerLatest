class Card implements Comparable<Card>
{   
   private final Suit suit;
   private final Rank rank;

   
   // static class constants (for suits)
   static enum Suit { clubs , diamonds, hearts, spades };
   
   static enum Rank 
   {
      two (2), three (3), four(4), five (5), six (6), seven (7), eight (8), 
            nine (9), ten (10), jack (11), queen (12), king (13), ace (14);
         
      int value;
      
      Rank(int value)
      {
         this.value = value;
      }
      
      public int getValue()
      {
         return this.value;
      }
   };
   
   public Card(Rank rank, Suit suit)
   {
      this.rank = rank;
      this.suit = suit;
   }
   
   // accessors
   public Rank getRank()
   {
      return this.rank;
   }
   public Suit getSuit()
   {
      return this.suit;
   }
   
   @Override
   public String toString()
   {
   if (rank.value < 11)
      return this.rank.value + " of " + this.suit;
   else return this.rank + " of " + this.suit;
   }
   
   // sort member methods
   @Override
   public int compareTo(Card other) 
   {
      if (this.getRank() == other.getRank())
         return this.suit.ordinal() - other.suit.ordinal();
      return this.rank.value - other.rank.value;
   }
}