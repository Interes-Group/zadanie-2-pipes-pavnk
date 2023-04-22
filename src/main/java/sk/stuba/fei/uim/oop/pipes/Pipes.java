package sk.stuba.fei.uim.oop.pipes;

import sk.stuba.fei.uim.oop.blocks.*;
import sk.stuba.fei.uim.oop.board.Board;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLOutput;
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
    private int boardSize = 8;

    public Pipes(){
        setTitle("Pipes");
        setSize(650,725);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

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
                int x = e.getX();
                int y = e.getY();
                int gridX = x / board.getPipeSize();
                int gridY = y / board.getPipeSize();
                Pipe clickedPipe = board.getPipeAt(gridX, gridY);
                if (clickedPipe != null) {
                    clickedPipe.rotate();
                    board.getPipeAt(gridX,gridY).draw(canvas.getGraphics());
                }
            }
        });

        canvas.addMouseMotionListener(new MouseAdapter() {
            private Pipe lastHighlightedPipe;
            @Override
            public void mouseMoved(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                int gridX = x / board.getPipeSize();
                int gridY = y / board.getPipeSize();
                Pipe pipe = board.getPipeAt(gridX, gridY);

                if (pipe != null && pipe != lastHighlightedPipe) {
                    if (lastHighlightedPipe != null) {
                        lastHighlightedPipe.setHighlighted(false);
                        lastHighlightedPipe.draw(canvas.getGraphics());
                    }
                    pipe.setHighlighted(true);
                    pipe.draw(canvas.getGraphics());
                    lastHighlightedPipe = pipe;
                } else if (pipe == null && lastHighlightedPipe != null) {
                    lastHighlightedPipe.setHighlighted(false);
                    lastHighlightedPipe.draw(canvas.getGraphics());
                    lastHighlightedPipe = null;
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
        sizeSlider.addChangeListener(this);

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

        canvas.setBackground(Color.BLACK);
        canvas.setPreferredSize(new Dimension(600,600));
        setLayout(new BorderLayout());
        add(canvas, BorderLayout.CENTER);
        add(topMenu, BorderLayout.NORTH);
        System.out.println(topMenu.getHeight());
        System.out.println(topMenu.getWidth());
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                // ovládanie klávesnicou
            }
        });

        setFocusable(true);
        this.setVisible(true);

        board = new Board(boardSize);
    }


    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == resetButton) {
            board = new Board(boardSize);
            canvas.repaint();
        }
    }

    private void drawCanvas(Graphics g) {
        for(int i=0;i<board.getSize();++i){
            for(int j=0;j<board.getSize();++j){
                if(board.getPipe(j,i) != null) {
                    board.getPipe(j, i).draw(g);
                } else {
                    System.out.println("Error in:");
                    System.out.println(j + " - x " + i + " - y ");
                }
            }
        }
    }


    @Override
    public void stateChanged(ChangeEvent e) {
        if (e.getSource() == sizeSlider) {
            boardSize = sizeSlider.getValue();
            board = new Board(boardSize);
            canvas.repaint();
        }
    }
}
