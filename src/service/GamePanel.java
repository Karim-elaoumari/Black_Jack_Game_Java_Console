package service;

import javax.swing.*;
import java.util.List;
import java.awt.*;

public class GamePanel {
    public  void repaintPanels(JPanel gamePanel, JPanel buttonPanel){
        buttonPanel.revalidate();
        buttonPanel.repaint();
        gamePanel.repaint();
    }
    public  void paintStartGame(Graphics g){
        g.setColor(Color.WHITE);
        g.setFont(new Font("TimesRoman", Font.HANGING_BASELINE, 30));
        g.drawString("Wecome To Black Jack", 340, 260);
        g.drawString(" Start The Game", 365, 300);
    }
    public void paintCards(Graphics g,List<List<Integer>> player_hand, List<List<Integer>> dealer_hand, JButton stayButton){
        for (int i = 0; i < dealer_hand.size(); i++) {
            List<Integer> card = dealer_hand.get(i);
            g.drawImage(new ImageIcon(getClass().getResource("../cards/"+card.get(0)+"-"+card.get(1)+".png")).getImage(), 110 + 25 + (110 - 50)*i, 20, 110, 154, null);
            if (stayButton.isEnabled() && i+1==dealer_hand.size()) {
                g.drawImage(new ImageIcon(getClass().getResource("../cards/BACK.png")).getImage(), 110 + 25 + (110 - 50)*(i+1), 20, 110, 154, null);
            }
        }
        for (int i = 0; i < player_hand.size(); i++){
            List<Integer> card = player_hand.get(i);
            g.drawImage(new ImageIcon(getClass().getResource("../cards/"+card.get(0)+"-"+card.get(1)+".png")).getImage(), 135 + (110 -50 )*i, 320, 110, 154, null);
        }
    }
    public void paintPioche(Graphics g,Integer pioche_count){
        g.setColor(Color.WHITE);
        g.setFont(new Font("TimesRoman", Font.HANGING_BASELINE, 17));
        g.drawString("Pioche:" +pioche_count, 800, 60);
        g.drawImage(new ImageIcon(getClass().getResource("../cards/BACK.png")).getImage(), 826, 100, 60, 84, null);
        g.drawImage(new ImageIcon(getClass().getResource("../cards/BACK.png")).getImage(), 823, 100, 60, 84, null);
        g.drawImage(new ImageIcon(getClass().getResource("../cards/BACK.png")).getImage(), 820, 100, 60, 84, null);

    }
    public void paintRoundResult(Graphics g,Integer round_status, Integer selectedChipValue){
        g.setFont(new Font("Arial", Font.ROMAN_BASELINE, 30));
        if(round_status == 1){
            g.setColor(Color.RED);g.drawString("Dealer Win", 410, 260);
            this.paintChips(g, 1, selectedChipValue);

        }else if(round_status == 2){
            g.setColor(Color.green);g.drawString("You Win", 410, 260);
            this.paintChips(g, 2, selectedChipValue);

        }else if(round_status == 3){
            g.setColor(Color.orange);g.drawString("Draw", 410, 250);
            this.paintChips(g, 3, selectedChipValue);
        }

    }
    public void paintChips(Graphics g,Integer status,Integer selectedChipValue){
        if(status == 2) {
            g.drawImage(new ImageIcon(getClass().getResource("../cards/chip.png")).getImage(), 10, 280, 120, 120, null);
            g.drawImage(new ImageIcon(getClass().getResource("../cards/chip.png")).getImage(), 6, 290, 120, 120, null);
            g.setColor(Color.WHITE);
            g.setFont(new Font("TimesRoman", Font.HANGING_BASELINE, 17));
            g.drawString(selectedChipValue.toString(), 44, 355);
        }
        else if(status == 1){
            g.drawImage(new ImageIcon(getClass().getResource("../cards/chip.png")).getImage(), 10, 160, 120, 120, null);
            g.drawImage(new ImageIcon(getClass().getResource("../cards/chip.png")).getImage(), 6, 170, 120, 120, null);
            g.setColor(Color.WHITE);
            g.setFont(new Font("TimesRoman", Font.HANGING_BASELINE, 17));
            g.drawString(selectedChipValue.toString(), 44, 235);
        }
        else if(status == 3){
            g.drawImage(new ImageIcon(getClass().getResource("../cards/chip.png")).getImage(), 10, 160, 120, 120, null);
            g.drawImage(new ImageIcon(getClass().getResource("../cards/chip.png")).getImage(), 10, 280, 120, 120, null);
            g.setColor(Color.WHITE);
            g.setFont(new Font("TimesRoman", Font.HANGING_BASELINE, 17));
            g.drawString(selectedChipValue.toString(), 50, 225);
            g.drawString(selectedChipValue.toString(), 50, 345);
        }
    }
    public void paintStates(Graphics g,Integer dealer_points, Integer player_points, Integer round, Integer player_mony){
        g.setFont(new Font("Arial", Font.PLAIN, 30));
        g.setColor(Color.white);
        g.drawString(player_points.toString(), 430, 200);
        g.drawString(dealer_points.toString(), 430, 310);
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        g.drawString("Round: "+round , 800, 450);
        g.drawString( "Your Balance: "+player_mony, 800, 500);

    }
    public  int showChipSelectionDialog(int availableBalance,int selectedChipp) {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);

        // Display available balance
        JLabel balanceLabel = new JLabel("Available Balance: $" + String.valueOf(availableBalance));
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        panel.add(balanceLabel, constraints);

        String[] chipOptions =  getAvailableChipOptions(availableBalance,selectedChipp); // Get chip options based on available balance

        JLabel label = new JLabel("Select a chip denomination:");
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 2;
        panel.add(label, constraints);

        JComboBox<String> chipComboBox = new JComboBox<>(chipOptions);
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        panel.add(chipComboBox, constraints);

        int result = JOptionPane.showConfirmDialog(null, panel, "Chip Selection", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String selectedChip = (String) chipComboBox.getSelectedItem();
            selectedChip = selectedChip.replace("$", "");
            int chipValue = Integer.parseInt(selectedChip);
            return chipValue;
        } else {
            return 10;
        }
    }
    private  String[] getAvailableChipOptions(int availableBalance,int selectedChip) {

        String[] chipOptions;
        if (availableBalance >= 1000) {
            chipOptions = new String[]{"$10","$50","$100", "$200", "$500", "$1000"};
        } else if (availableBalance >= 500) {
            chipOptions = new String[]{"$10","$50","$100", "$200", "$500"};
        } else if (availableBalance >= 200) {
            chipOptions = new String[]{"$10","$50","$100", "$200"};
        } else if (availableBalance >= 100) {
            chipOptions = new String[]{"$10","$50","$100"};
        }  else if (availableBalance >= 50) {
            chipOptions = new String[]{"$10","$50"};
        } else {
            chipOptions = new String[]{"$10"};
        }
        if(availableBalance>=selectedChip) {
            for (int i = 0; i < chipOptions.length; i++) {
                if (chipOptions[i].equals("$" + selectedChip)) {
                    String temp = chipOptions[0];
                    chipOptions[0] = chipOptions[i];
                    chipOptions[i] = temp;
                    break;
                }
            }
        }
        return chipOptions;
    }

}
