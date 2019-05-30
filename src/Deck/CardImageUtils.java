package Deck;

import Deck.Card.Rank;
import Deck.Card.Suit;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CardImageUtils
{
   private static Map<Rank, String> rankHM;
   private static Map<Suit, Character> suitHM;
   private static final String CARD_BACK_STR = "\\Cards\\background.gif";

   private CardImageUtils()
   {
      throw new RuntimeException("No no Peanut");
   }

   public static String getImageStr(Card card)
   {
      if (rankHM == null) { populateRankHM(); }
      if (suitHM == null) { populateSuitHM(); }

      return "Cards\\" + rankHM.get(card.getRank()) + suitHM.get(card.getSuit()) + ".gif";
   }

   public static String getCardBAckImgStr() {return CARD_BACK_STR; }

   public static ArrayList<String> getCardBackImageStringList(int i) {
      return IntStream.range(0, i).mapToObj(x -> CARD_BACK_STR).collect(Collectors.toCollection(ArrayList::new));
   }

   private static void populateRankHM()
   {
      rankHM = new HashMap<>();
      List<String> letterRanks = new ArrayList<>();

      letterRanks.add("T"); letterRanks.add("J"); letterRanks.add("Q");
      letterRanks.add("K"); letterRanks.add("A");

      Arrays.stream(Rank.values()).forEach(rank -> rankHM.put(rank, rank.ordinal() < 8 ? String.valueOf(rank.getValue()) :
            letterRanks.get(rank.ordinal() - 8)));

   }

   private static void populateSuitHM()
   {
      suitHM = new HashMap<>();
      Arrays.stream(Suit.values()).forEach(suit ->
            suitHM.put(suit, suit.toString().toUpperCase().charAt(0)));
   }
}
