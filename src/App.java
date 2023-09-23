import service.BlackJack;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;


public class App {
    public static  List<List<Integer>> deck = BlackJack.createDeck();
    public static List<List<List<Integer>>> pioched_deck =new ArrayList<>();
    public static Integer dealer_points = 0;
    public static Integer player_points = 0;
    public static  Integer player_mony = 1000;
    public static Integer selectedChipValue = 0;
    public static Integer round = 1;
    public static Integer round_status = 0;
    public static List<List<Integer>> dealer_hand = new ArrayList<>();
    public static List<List<Integer>> player_hand = new ArrayList<>();
    public static List<List<Integer>> remaining_cards = new ArrayList<>();
    public static void main(String[] args) {
        App.start();
    }
    public static void start(){
                    JFrame frame = new JFrame("BlackJack");
                    JPanel buttonPanel = new JPanel();
                    JButton hitButton = new JButton("Hit");
                    JButton stayButton = new JButton("Stay");
                    JButton startButton = new JButton("Start");
                    JButton exitButton = new JButton("Exit");
                    JButton newRoundButton = new JButton("New Round");
                    JPanel gamePanel = new JPanel() {
                        @Override
                        public void paintComponent(Graphics g) {
                            super.paintComponent(g);
                            if(hitButton.getParent() == null){
                                g.setColor(Color.WHITE);
                                g.setFont(new Font("TimesRoman", Font.HANGING_BASELINE, 30));
                                g.drawString("Wecome To Black Jack", 340, 260);
                                g.drawString(" Start The Game", 365, 300);
                            }else{
                                for (int i = 0; i < dealer_hand.size(); i++) {
                                    List<Integer> card = dealer_hand.get(i);
                                    g.drawImage(new ImageIcon(getClass().getResource("cards/"+card.get(0)+"-"+card.get(1)+".png")).getImage(), 110 + 25 + (110 - 50)*i, 20, 110, 154, null);
                                    if (stayButton.isEnabled() && i+1==dealer_hand.size()) {
                                        g.drawImage(new ImageIcon(getClass().getResource("cards/BACK.png")).getImage(), 110 + 25 + (110 - 50)*(i+1), 20, 110, 154, null);
                                    }
                                }
                                for (int i = 0; i < player_hand.size(); i++) {
                                    List<Integer> card = player_hand.get(i);
                                    g.drawImage(new ImageIcon(getClass().getResource("cards/"+card.get(0)+"-"+card.get(1)+".png")).getImage(), 135 + (110 -50 )*i, 320, 110, 154, null);
                                }
                                if(player_points > 21){
                                    round_status= 1;
                                } else if (player_points == 21) {
                                    round_status= 2;
                                }
                                if (!stayButton.isEnabled()) {
                                    round_status = getRoundStatusAfterStand();
                                }
                                g.setColor(Color.WHITE);
                                g.setFont(new Font("TimesRoman", Font.HANGING_BASELINE, 17));
                                g.drawString("Pioche:" +pioched_deck.get(0).size(), 800, 60);
                                g.drawImage(new ImageIcon(getClass().getResource("cards/BACK.png")).getImage(), 826, 100, 60, 84, null);
                                g.drawImage(new ImageIcon(getClass().getResource("cards/BACK.png")).getImage(), 823, 100, 60, 84, null);
                                g.drawImage(new ImageIcon(getClass().getResource("cards/BACK.png")).getImage(), 820, 100, 60, 84, null);


                                if(round_status != 0 ){
                                    g.setFont(new Font("Arial", Font.ROMAN_BASELINE, 20));
                                    if(round_status == 1){
                                        g.setColor(Color.RED);g.drawString("Dealer Win", 410, 220);
                                    }else if(round_status == 2){
                                        g.setColor(Color.green);g.drawString("You Win", 410, 220);
                                        player_mony+=selectedChipValue*2;
                                    }else if(round_status == 3){
                                        g.setColor(Color.orange);g.drawString("Draw", 410, 220);
                                        player_mony+=selectedChipValue;
                                    }
                                    buttonPanel.remove(hitButton);buttonPanel.remove(stayButton);
                                    if(player_mony>=10){buttonPanel.add(newRoundButton);
                                    }else{
                                        buttonPanel.add(startButton);buttonPanel.add(exitButton);
                                        deck = BlackJack.defausserCards(remaining_cards,deck);
                                        g.setFont(new Font("Arial", Font.BOLD, 20));
                                        g.setColor(Color.ORANGE);
                                        g.drawString("No more Balance", 400, 200);
                                    }
                                    buttonPanel.revalidate();
                                    buttonPanel.repaint();
                                    round_status = 0;
                                }
                                g.setFont(new Font("Arial", Font.PLAIN, 20));
                                g.setColor(Color.white);
                                g.drawString("Dealer Points:"+dealer_points+" | "+"Player Points:"+player_points, 340, 250);
                                g.drawString("Round: "+round , 800, 450);
                                g.drawString( "Your Balance: "+player_mony, 800, 500);
                            }
                        }
                    };
                    gamePanel.setBackground(Color.GREEN);
                    frame.setVisible(true);
                    frame.setSize(1000, 600);
                    frame.setLocationRelativeTo(null);
                    frame.setResizable(false);
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    gamePanel.setLayout(new BorderLayout());
                    gamePanel.setBackground(new Color(53, 101, 77));
                    hitButton.setFocusable(false);
                    stayButton.setFocusable(false);
                    startButton.setFocusable(false);
                    exitButton.setFocusable(false);
                    buttonPanel.add(startButton);
                    buttonPanel.add(exitButton);
                    frame.add(buttonPanel, BorderLayout.SOUTH);
                    frame.add(gamePanel, BorderLayout.CENTER);
                    startButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            player_mony = 1000;
                            selectedChipValue = BlackJack.showChipSelectionDialog(player_mony);
                            player_mony -= selectedChipValue;
                            frame.setVisible(true);
                            round = 1;
                            deck = BlackJack.shuffleCards(deck);
                            pioched_deck = BlackJack.piocheDeck(deck);
                            givePlayersStartingCards();
                            buttonPanel.remove(startButton);
                            buttonPanel.remove(exitButton);
                            buttonPanel.add(hitButton);
                            buttonPanel.add(stayButton);
                            repaintPanels(gamePanel, buttonPanel);
                        }
                    });
                    newRoundButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            selectedChipValue = BlackJack.showChipSelectionDialog(player_mony);
                            player_mony -= selectedChipValue;
                            round++;
                            givePlayersStartingCards();
                            buttonPanel.remove(newRoundButton);
                            buttonPanel.add(hitButton);
                            buttonPanel.add(stayButton);
                            hitButton.setEnabled(true);
                            stayButton.setEnabled(true);
                            repaintPanels(gamePanel, buttonPanel);

                        }
                    });
                    exitButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            System.exit(0);
                        }
                    });
                    hitButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {givePlayerCard(gamePanel, hitButton);}
                    });
                    stayButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {giveDealerCards(gamePanel, hitButton, stayButton);}
                    });
    }
    public static Integer getRoundStatusAfterStand(){
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
    public static void givePlayersStartingCards(){
        round_status = 0;
        dealer_points = 0;
        player_points = 0;
        remaining_cards.addAll(dealer_hand);
        remaining_cards.addAll(player_hand);
        dealer_hand = new ArrayList<>();
        player_hand = new ArrayList<>();
        if(pioched_deck.get(0).size() == 0){
            deck = BlackJack.defausserCards(remaining_cards,deck);
            pioched_deck = BlackJack.piocheDeck(deck);
        }
        List<Integer> card = pioched_deck.get(0).remove(0);

        dealer_hand.add(card);
        dealer_points = BlackJack.getCardValueConform(dealer_hand);
        for(int i = 0; i < 2; i++){
            card = pioched_deck.get(0).remove(0);

            player_hand.add(card);
            player_points = BlackJack.getCardValueConform(player_hand);

        }
    }
    public static void repaintPanels(JPanel gamePanel, JPanel buttonPanel){
        buttonPanel.revalidate();
        buttonPanel.repaint();
        gamePanel.repaint();
    }
    public static void givePlayerCard(JPanel gamePanel, JButton hitButton){
        if(pioched_deck.get(0).size() == 0){
                deck = BlackJack.defausserCards(remaining_cards,deck);
                pioched_deck = BlackJack.piocheDeck(deck);
        }
            List<Integer> card = pioched_deck.get(0).remove(0);

            player_hand.add(card);
            player_points = BlackJack.getCardValueConform(player_hand);
            if (player_points >= 21) {
                hitButton.setEnabled(false);
            }

        gamePanel.repaint();

    }
    public static void giveDealerCards( JPanel gamePanel, JButton hitButton, JButton stayButton){
        hitButton.setEnabled(false);
        stayButton.setEnabled(false);
        while (dealer_points < 17) {
            if(pioched_deck.get(0).size() == 0){
                deck = BlackJack.defausserCards(remaining_cards,deck);
                pioched_deck = BlackJack.piocheDeck(deck);
            }
                List<Integer> card = pioched_deck.get(0).remove(0);

                dealer_hand.add(card);
                dealer_points = BlackJack.getCardValueConform(dealer_hand);
                System.out.println(dealer_points);
                System.out.println(dealer_hand);
                gamePanel.repaint();

        }

    }
}