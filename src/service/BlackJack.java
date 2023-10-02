package service;

import javax.print.DocFlavor;
import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.*;
import java.util.List;

public class BlackJack {
    public static List<List<Integer>> createDeck(){
        List<List<Integer>> deck = retrunDeckBasedOnCard(List.of(1,1));
        return deck;
    }
    public static List<List<Integer>> retrunDeckBasedOnCard(List<Integer> card){
        if (card.size() != 2 || card.get(0) < 1 || card.get(0) > 13 || card.get(1) < 1 || card.get(1) > 4) {
            throw new IllegalArgumentException("Invalid card input");
        }
        List<List<Integer>> deck = new ArrayList<>();
        for (int suit = card.get(1); suit <= 4; suit++) {
            int startRank = (suit == card.get(1)) ? card.get(0) : 1;
            for (int rank = startRank; rank <= 13; rank++) {
                deck.add(List.of(rank, suit));
            }
        }
        return deck;
    }
    public static List<Integer>  getIemeCard(List<List<Integer>> deck,Integer card_index){
        return deck.remove((int)card_index);
    }
    public static List<Integer>   getRandCard(List<List<Integer>> deck){
        int card_index = (int) (Math.random() * deck.size());
        return getIemeCard(deck, card_index);
    }
    public static List<List<Integer>> shuffleCards(List<List<Integer>> deck){
        for(int i = 0; i < deck.size()+20; i++){
            deck.add(getRandCard(deck));
        }
        return deck;
    }
    public static List<List<List<Integer>>>  piocheDeck(List<List<Integer>> deck){
//        creating random number between 20 and 32
        int pioche_index = (int) (Math.random() * 12 + 20);
        List<List<List<Integer>>> pioche_deck = new ArrayList<>();
        pioche_deck.add(deck.subList(0, pioche_index));
        pioche_deck.add(deck.subList(pioche_index, deck.size()));
        return pioche_deck;
    }

    public static Integer getCardValueConform(List<List<Integer>> cards){
        Integer points = 0;
        List<List<Integer>> cardsTemp = new ArrayList<>(cards);
        cardsTemp.stream().forEach(card -> {
            if(card.get(0) == 1) {cards.add(cards.remove(cards.indexOf(card)));}
        });
        for(List<Integer> card : cards){
            Integer card_value = card.get(0);
            if(card_value > 10){card_value = 10;}
            else if(card_value == 1){
                if( points + 11 <= 21){card_value = 11;}
                else{card_value = 1;}
            }
            else{card_value = card.get(0);}
            points += card_value;
        }
        return points;
    }
    public static List<List<Integer>> defausserCards(List<List<Integer>> remaining_cards,List<List<Integer>>  deck){
        List<List<Integer>> defausse = new ArrayList<>();
        defausse.addAll(remaining_cards);
        defausse.addAll(deck);
        return defausse;
    }
    public static Integer getRoundStatusAfterStand(Integer dealer_points, Integer player_points, Integer round_status){
        if(dealer_points > 21){
            round_status= 2;
        }
        else if(dealer_points == 21){
            round_status= 1;
        }else if(player_points == dealer_points){
            round_status= 3;
        }else if(player_points > dealer_points){
            round_status= 2;
        }else if(player_points < dealer_points){
            round_status= 1;
        }
        return round_status;
    }

}
