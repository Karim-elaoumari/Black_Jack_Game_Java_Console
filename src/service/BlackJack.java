package service;

import javax.print.DocFlavor;
import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.*;
import java.util.List;

public class BlackJack {
    public static List<List<Integer>> createDeck(){
        List<List<Integer>> deck = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            for (int j = 1; j <= 13; j++) {
                List<Integer> card = new ArrayList<>();
                card.add(j);
                card.add(i);
                deck.add(card);
            }
        }
        return deck;
    }
    public static List<List<Integer>> retrunDeckBasedOnCard(List<Integer> card){
        List<List<Integer>> deck = new ArrayList<>();
        for (int i = card.get(1); i <= card.get(1)+4; i++) {
            int step = card.get(1) == i ? card.get(0) : 1;
            for(int j=step; j<= 13;j++){
                deck.add(new ArrayList<>(List.of(j,i)));
            }
        }
        return deck;

    }
    public static HashMap<List<Integer>, List<List<Integer>>>  getIemeCard(List<List<Integer>> deck,Integer card_index){
        HashMap<List<Integer>, List<List<Integer>>> card_deck = new HashMap<>(Map.of(deck.remove((int)card_index), deck));
        return card_deck;
    }
    public static HashMap<List<Integer>, List<List<Integer>>>  getRandCard(List<List<Integer>> deck){
        int card_index = (int) (Math.random() * deck.size());
        return getIemeCard(deck, card_index);
    }
    public static List<List<Integer>> shuffleCards(List<List<Integer>> deck){
//        List<List<Integer>> shuffled_deck = deck;
        for(int i = 0; i < deck.size(); i++){
            HashMap<List<Integer>, List<List<Integer>>> card_deck = getRandCard(deck);
            List<Integer> card = new ArrayList<>(card_deck.keySet()).get(0);
            deck = new ArrayList<>();
            deck.add(card);
            deck.addAll(card_deck.get(card));
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
    public static void drawCard(Graphics g, URL imagePath, int x, int y, int width, int height) {
        try{
            Image cardImg = new ImageIcon(imagePath).getImage();
            g.drawImage(cardImg, x, y, width, height, null); // Adjust coordinates and dimensions as needed
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static  Integer getCardValue(List<Integer> card){
        Integer card_value = card.get(0);
        if(card_value > 10){
            card_value = 10;
        }
        return card_value;
    }
    public static Integer getCardValueConform(List<Integer> card, Integer points){
        Integer card_value = card.get(0);
        if(card_value > 10){
            card_value = 10;
        }
        else if(card_value == 1){
            if( points + 11 <= 21){
                card_value = 11;
            }else{
                card_value = 1;
            }
        }
        else{
            card_value = card.get(0);
        }
        return card_value;
    }
    public static List<List<Integer>> defausserCards(List<List<Integer>> remaining_cards,List<List<Integer>>  deck){
        List<List<Integer>> defausse = new ArrayList<>();
        defausse.addAll(remaining_cards);
        defausse.addAll(deck);
        return defausse;
    }

}
