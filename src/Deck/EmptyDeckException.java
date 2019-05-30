package Deck;

public class  EmptyDeckException extends Exception
{
   EmptyDeckException(){ super("Deck is empty."); }
}