package colMatch;

import javax.swing.*;
import java.awt.BorderLayout;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException{
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Colour Match");
        JTextField player1Score = new JTextField();
        JTextField player2Score = new JTextField();
        Table t = new Table(6, player1Score, player2Score);
        frame.add(t);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
