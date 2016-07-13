package com.theironyard.jdblack;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Main {

    static HashSet<Card> createDeck() {
        HashSet<Card> deck = new HashSet<>();
        for (Card.Suit suit : Card.Suit.values()) {
            for (Card.Rank rank : Card.Rank.values()) {
                Card c = new Card(suit, rank);
                deck.add(c);
            }
        }
        return deck;
    }

    static HashSet<HashSet<Card>> createHands(HashSet<Card> deck) {
        HashSet<HashSet<Card>> hands = new HashSet<>();
        for (Card c1 : deck) {
            HashSet<Card> deck2 = (HashSet<Card>) deck.clone();
            deck2.remove(c1);
            for(Card c2 : deck2) {
                HashSet<Card> deck3 = (HashSet<Card>) deck2.clone();
                deck3.remove(c2);
                for (Card c3 : deck3) {
                    HashSet<Card> deck4 = (HashSet<Card>) deck3.clone();
                    deck4.remove(c3);
                    for (Card c4 : deck4) {
                        HashSet<Card> hand = new HashSet<>();
                        hand.add(c1);
                        hand.add(c2);
                        hand.add(c3);
                        hand.add(c4);
                        hands.add(hand);
                    }
                }
            }
        }
        return hands;
    }
    static boolean isFlush(HashSet<Card> hand) {
        HashSet<Card.Suit> suits = hand.stream()
                .map(card -> card.suit)
                .collect(Collectors.toCollection(HashSet<Card.Suit>::new));

        return suits.size() == 1;
    }
    static boolean isFourOfAKind(HashSet<Card> hand) {
        HashSet<Card.Rank> fourOfAKind = hand.stream()
                .map(card -> card.rank)
                .collect(Collectors.toCollection(HashSet<Card.Rank>::new));
        return fourOfAKind.size() == 1;
    }
    static boolean isStraight(HashSet<Card> hand) {
        List<Card.Rank> straight = hand.stream()
                .map(card -> card.rank)
                .sorted()
                .collect(Collectors.toCollection(ArrayList<Card.Rank>::new));

        for (int i = 0; i < straight.size() -1; i++) {
            Card.Rank current = straight.get(i);
            Card.Rank next = straight.get(i + 1);
            if ((current.ordinal() + 1) % 13 != (next.ordinal()) % 13) {
                return false;
            }
        }
        return true;
    }
    static boolean isStraightFlush(HashSet<Card> hand) {
        return isStraight(hand) && isFlush(hand);
    }
    //got help from john crooks on this method
    //still need to work on my problem solving
    static boolean isThreeOfAKind(HashSet<Card> hand) {
        ArrayList<Integer> threeOfAKind = hand.stream()
                .map(card -> card.rank.ordinal())
                .collect(Collectors.toCollection(ArrayList<Integer>::new));
        int [] ranks = new int[13];

        for (Integer i : threeOfAKind) {
            ranks [i] = ranks[i] + 1;
        }
        for (int i : ranks) {
            if (i == 3) {
                return true;
            }
        }
        return false;
    }
    //got help from a friend who is a developer on this
    static boolean isTwoPair(HashSet<Card> hand) {
        int count = 0;
        List<Card.Rank> straight = hand.stream()
                .map(card -> card.rank)
                .collect(Collectors.toCollection(ArrayList<Card.Rank>::new));
        int size = hand.size() - 1;
        for (int i = 0; i < size; i ++) {
            Card.Rank current = straight.remove(straight.size() - 1);

            boolean foundDuplicate = straight.stream()
                    .anyMatch(rank -> rank == current);
            if (foundDuplicate) {
                count++;
            }
        }
        return count >= 2;
    }


    public static void main(String[] args) {
        //calculate every possibility of 4 card hands in a 52 card deck
        HashSet<Card> deck = createDeck();
        HashSet<HashSet<Card>> hands = createHands(deck);
        HashSet<HashSet<Card>> flushes = hands.stream()
                .filter(Main::isFlush)
                .collect(Collectors.toCollection(HashSet<HashSet<Card>>::new));
        HashSet<HashSet<Card>> straights = hands.stream()
                .filter(Main::isStraight)
//                .filter(Main::isFlush)
                .collect(Collectors.toCollection(HashSet<HashSet<Card>>::new));
        HashSet<HashSet<Card>> fourOfAKinds = hands.stream()
                .filter(Main::isFourOfAKind)
                .collect(Collectors.toCollection(HashSet<HashSet<Card>>::new));
        HashSet<HashSet<Card>> threeOfAKinds = hands.stream()
                .filter(Main::isThreeOfAKind)
                .collect(Collectors.toCollection(HashSet<HashSet<Card>>::new));
        HashSet<HashSet<Card>> twoPair = hands.stream()
                .filter(Main::isTwoPair)
                .collect(Collectors.toCollection(HashSet<HashSet<Card>>::new));
        HashSet<HashSet<Card>> straightFlush = hands.stream()
                .filter(Main::isStraightFlush)
                .collect(Collectors.toCollection(HashSet<HashSet<Card>>::new));

        System.out.println(straightFlush.size());
        
    }
}
