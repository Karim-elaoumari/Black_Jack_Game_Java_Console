import controller.FunctionalController;
import service.BlackJack;
import service.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;
import java.util.Timer;


public class App {

    public static void main(String[] args) {
        FunctionalController functionalController = new FunctionalController();
        functionalController.start();
    }

}