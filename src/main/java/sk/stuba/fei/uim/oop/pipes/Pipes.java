package sk.stuba.fei.uim.oop.pipes;

import sk.stuba.fei.uim.oop.board.Board;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.util.Hashtable;

public class Pipes extends JFrame implements ActionListener, ChangeListener {

    private JPanel canvas;
    private JPanel topMenu;
    private JLabel levelLabel;
    private JLabel sizeLabel;
    private JSlider sizeSlider;
    private JButton resetButton;
    private JButton checkButton;
    private int currentLevel;
    private int gridSize;
    private boolean[][] pipes;
    private boolean[][] correctPipes;
    private Board board;

    public Pipes(){
        setTitle("Pipes");
        setSize(800,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        canvas = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawCanvas(g);
            }
        };
        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = e.getY() / (canvas.getHeight() / gridSize);
                int col = e.getX() / (canvas.getWidth() / gridSize);
                if (row >= 0 && row < gridSize && col >= 0 && col < gridSize) {
                    pipes[row][col] = !pipes[row][col];
                    repaint();
                }
            }
        });

        topMenu = new JPanel();
        topMenu.setLayout(new FlowLayout());

        levelLabel = new JLabel("Level: 1");
        topMenu.add(levelLabel);

        sizeLabel = new JLabel("Size:");
        topMenu.add(sizeLabel);

        sizeSlider = new JSlider(JSlider.HORIZONTAL, 8, 12, 8);
        sizeSlider.setMajorTickSpacing(2);
        sizeSlider.setPaintTicks(true);
        sizeSlider.setSnapToTicks(true);

        Hashtable<Integer, JLabel> labelTable = new Hashtable<>();
        labelTable.put(8, new JLabel("8"));
        labelTable.put(10, new JLabel("10"));
        labelTable.put(12, new JLabel("12"));

        sizeSlider.setLabelTable(labelTable);
        sizeSlider.setPaintLabels(true);
        topMenu.add(sizeSlider);

        resetButton = new JButton("Reset");
        topMenu.add(resetButton);
        resetButton.addActionListener(this);

        checkButton = new JButton("Check");
        topMenu.add(checkButton);
        checkButton.addActionListener(this);

        setLayout(new BorderLayout());
        add(canvas, BorderLayout.CENTER);
        add(topMenu, BorderLayout.NORTH);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                // ovládanie klávesnicou
            }
        });
        setFocusable(true);
        this.setVisible(true);

        int boardSize = 8; // Desired size of the game board
        board = new Board(boardSize);
    }

    private void drawCanvas(Graphics g) {
        // kód pre vykreslenie hracej plochy
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == resetButton) {
            // obsluha tlačidla reset
        } else if (e.getSource() == checkButton) {
            // obsluha tlačidla check
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if (e.getSource() == sizeSlider) {
            gridSize = sizeSlider.getValue();
            // obsluha zmeny hodnoty slideru
        }
    }
}
