package com.theironyard.jdblack;

/**
 * Created by jonathandavidblack on 7/12/16.
 */
public class Card implements Comparable<Card> {

    @Override
    public int compareTo(Card c) {

        return this.rank.ordinal() -c.rank.ordinal();
    }

    enum Suit {
        CLUBS,
        DIAMONDS,      //now in main we can use Card.Suit.CLUBS
        HEARTS,
        SPADES        //equivalent to static final fields
    }
    enum Rank {
        ACE,
        TWO,
        THREE,
        FOUR,
        FIVE,
        SIX,
        SEVEN,
        EIGHT,
        NINE,
        TEN,
        JACK,
        QUEEN,
        KING
    }
    Suit suit;
    Rank rank;

    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Card card = (Card) o;

        if (suit != card.suit) return false;
        return rank == card.rank;

    }

    @Override
    public int hashCode() {
        int result = suit.hashCode();
        result = 31 * result + rank.hashCode();
        return result;
    }
}
