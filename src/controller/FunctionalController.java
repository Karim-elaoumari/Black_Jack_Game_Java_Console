package controller;

import service.BlackJack;
import service.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class FunctionalController {
    public   static List<List<Integer>> deck = BlackJack.createDeck();
    public  static List<List<List<Integer>>> pioched_deck = new ArrayList<>();
    public  static Integer dealer_points = 0;
    public static Integer player_points = 0;
    public   static Integer player_mony = 1000;
    public  static Integer selectedChipValue = 0;
    public static Integer round = 1;
    public  static Integer round_status = 0;
    public  static List<List<Integer>> dealer_hand = new ArrayList<>();
    public  static List<List<Integer>> player_hand = new ArrayList<>();
    public static List<List<Integer>> remaining_cards = new ArrayList<>();
    public static GamePanel gamePanelService = new GamePanel();
    public  static void start(){
        JFrame frame = new JFrame("BlackJack");
        JPanel buttonPanel = new JPanel();
        JButton hitButton = new JButton("Hit");
        JButton stayButton = new JButton("Stay");
        JButton startButton = new JButton("Start");
        JButton exitButton = new JButton("Exit");
        JButton newRoundButton = new JButton("New Round");
        frame.setVisible(true);
        frame.setSize(1000, 600);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        hitButton.setFocusable(false);
        stayButton.setFocusable(false);
        startButton.setFocusable(false);
        exitButton.setFocusable(false);
        buttonPanel.add(startButton);
        buttonPanel.add(exitButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        JPanel gamePanel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                if(hitButton.getParent() == null){
                    gamePanelService.paintStartGame(g);
                }else{
                    gamePanelService.paintCards(g,player_hand,dealer_hand,stayButton);
                    if(player_points > 21){round_status= 1;} else if (player_points == 21) {round_status= 2;}
                    if (!stayButton.isEnabled()) {
                        round_status = BlackJack.getRoundStatusAfterStand(dealer_points, player_points,round_status);
                    }
                    gamePanelService.paintPioche(g,pioched_deck.get(0).size());
                    if(round_status != 0 ){
                        gamePanelService.paintRoundResult(g,round_status,selectedChipValue,player_mony);
                        buttonPanel.remove(hitButton);buttonPanel.remove(stayButton);
                        buttonPanel.revalidate();
                        buttonPanel.repaint();
                        round_status = 0;
                        if(player_mony>=10){buttonPanel.add(newRoundButton);
                        }else{
                            buttonPanel.add(startButton);buttonPanel.add(exitButton);
                            g.setFont(new Font("Arial", Font.BOLD, 20));g.setColor(Color.ORANGE);g.drawString("No more Balance", 400, 200);
                        }
                    }else {
                        gamePanelService.paintChips(g, 3, selectedChipValue);
                    }
                    gamePanelService.paintStates(g,player_points,dealer_points,round,player_mony);
                }
            }
        };
        gamePanel.setBackground(Color.GREEN);
        gamePanel.setLayout(new BorderLayout());
        gamePanel.setBackground(new Color(53, 101, 77));
        frame.add(gamePanel, BorderLayout.CENTER);
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {hundleStartPress(gamePanel,buttonPanel,hitButton,stayButton,startButton,exitButton,frame);}
        });
        newRoundButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {hudleNewRoundPress(gamePanel, buttonPanel, hitButton, stayButton, newRoundButton);}
        });
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){System.exit(0);}
        });
        hitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {givePlayerCard(gamePanel, hitButton);}
        });
        stayButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {giveDealerCards(gamePanel, hitButton, stayButton);}
        });
    }
    public static void givePlayersStartingCards(){
        round_status = 0;
        dealer_points = 0;
        player_points = 0;
        remaining_cards.addAll(dealer_hand);
        remaining_cards.addAll(player_hand);
        dealer_hand = new ArrayList<>();
        player_hand = new ArrayList<>();
        if(pioched_deck.get(0).size() < 3){
            deck = BlackJack.defausserCards(remaining_cards,deck);
            pioched_deck = BlackJack.piocheDeck(deck);
        }
        java.util.List<Integer> card = pioched_deck.get(0).remove(0);
        dealer_hand.add(card);
        dealer_points = BlackJack.getCardValueConform(dealer_hand);
        for(int i = 0; i < 2; i++){
            card = pioched_deck.get(0).remove(0);

            player_hand.add(card);
            player_points = BlackJack.getCardValueConform(player_hand);

        }
    }
    public static void givePlayerCard(JPanel gamePanel, JButton hitButton){
        if(pioched_deck.get(0).size() == 0){
            deck = BlackJack.defausserCards(remaining_cards,deck);
            pioched_deck = BlackJack.piocheDeck(deck);
        }
        java.util.List<Integer> card = pioched_deck.get(0).remove(0);
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
        while (dealer_points < 17 && dealer_points < player_points) {
            if(pioched_deck.get(0).size() == 0){
                deck = BlackJack.defausserCards(remaining_cards,deck);
                pioched_deck = BlackJack.piocheDeck(deck);
            }
            List<Integer> card = pioched_deck.get(0).remove(0);
            dealer_hand.add(card);
            dealer_points = BlackJack.getCardValueConform(dealer_hand);
            gamePanel.repaint();
        }
    }
    public static void hundleStartPress(JPanel gamePanel, JPanel buttonPanel, JButton hitButton, JButton stayButton,JButton startButton, JButton exitButton, JFrame frame){
        player_mony = 1000;
        selectedChipValue = BlackJack.showChipSelectionDialog(player_mony,selectedChipValue);
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
        gamePanelService.repaintPanels(gamePanel, buttonPanel);

    }
    public static void hudleNewRoundPress(JPanel gamePanel, JPanel buttonPanel, JButton hitButton, JButton stayButton, JButton newRoundButton){
        selectedChipValue = BlackJack.showChipSelectionDialog(player_mony,selectedChipValue);
        player_mony -= selectedChipValue;
        round++;
        givePlayersStartingCards();
        buttonPanel.remove(newRoundButton);
        buttonPanel.add(hitButton);
        buttonPanel.add(stayButton);
        hitButton.setEnabled(true);
        stayButton.setEnabled(true);
        gamePanelService.repaintPanels(gamePanel, buttonPanel);

    }

}
