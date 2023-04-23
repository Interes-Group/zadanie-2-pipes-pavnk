package sk.stuba.fei.uim.oop.pipes;

import sk.stuba.fei.uim.oop.blocks.*;
import sk.stuba.fei.uim.oop.logic.GameLogic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Hashtable;
import java.util.Random;

public class Pipes extends JFrame implements ActionListener, KeyListener {

    private final JPanel canvas;
    private final JLabel levelLabel;
    private final JSlider sizeSlider;
    private final JButton resetButton;
    private final JButton checkButton;
    private GameLogic logic;
    private int boardSize;
    private Pipe lastHighlightedPipe;
    private final Random random;

    public Pipes(){
        boardSize = 8;
        setTitle("Pipes");
        setSize(625,700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        this.random = new Random();

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
                int gridX = x / logic.getPipeSize();
                int gridY = y / logic.getPipeSize();
                Pipe clickedPipe = logic.getPipeAt(gridX, gridY);
                if (clickedPipe != null) {
                    clickedPipe.rotate();
                    logic.getPipeAt(gridX,gridY).draw(canvas.getGraphics());
                }
            }
        });

        canvas.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                int gridX = x / logic.getPipeSize();
                int gridY = y / logic.getPipeSize();
                Pipe pipe = null;
                try {
                    pipe = logic.getPipeAt(gridX, gridY);
                } catch (ArrayIndexOutOfBoundsException ex) {
                    System.out.println("Mouse pointer out of bounds");
                }

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


        JPanel topMenu = new JPanel();
        topMenu.setLayout(new FlowLayout());

        levelLabel = new JLabel("Level: 1");
        topMenu.add(levelLabel);

        JLabel sizeLabel = new JLabel("Size:");
        topMenu.add(sizeLabel);

        sizeSlider = new JSlider(JSlider.HORIZONTAL, 0, 2, 0);
        sizeSlider.setMajorTickSpacing(1);
        sizeSlider.setPaintTicks(true);
        sizeSlider.setSnapToTicks(true);
        BoundedRangeModel sliderModel = sizeSlider.getModel();
        sliderModel.addChangeListener(e -> {
            if (!sliderModel.getValueIsAdjusting()) {
                resetKeyboardListener();
                levelLabel.setText("Level: 1");
                lastHighlightedPipe=null;
                boardSize = 8 + sizeSlider.getValue() * 2;
                logic = new GameLogic(boardSize, random);
                canvas.repaint();
            }
        });
        topMenu.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                if (lastHighlightedPipe != null) {
                    lastHighlightedPipe.setHighlighted(false);
                    lastHighlightedPipe.draw(canvas.getGraphics());
                    lastHighlightedPipe = null;
                }
            }
        });

        Hashtable<Integer, JLabel> labelTable = new Hashtable<>();
        labelTable.put(0, new JLabel("8"));
        labelTable.put(1, new JLabel("10"));
        labelTable.put(2, new JLabel("12"));

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

        setFocusable(true);
        addKeyListener(this);
        this.setVisible(true);

        logic = new GameLogic(boardSize, random);
    }

    private void resetKeyboardListener() {
        canvas.removeKeyListener(this);
        canvas.requestFocus();
        canvas.addKeyListener(this);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        switch (keyCode) {
            case KeyEvent.VK_ESCAPE:
                System.exit(0);
                break;
            case KeyEvent.VK_R:
                resetGame();
                break;
            case KeyEvent.VK_ENTER:
                checkCorrectPath();
                break;
            default:
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }


    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == checkButton) {
            checkCorrectPath();
        }
        if (e.getSource() == resetButton) {
            resetGame();
        }
    }

    private void checkCorrectPath(){
        logic.resetPipeFlow();
        canvas.repaint();
        if (logic.checkPath()) {
            Timer timer = new Timer(500, e -> {
                resetKeyboardListener();
                int currentLevel = Integer.parseInt(levelLabel.getText().split(" ")[1]);
                int newLevel = currentLevel + 1;
                levelLabel.setText("Level: " + newLevel);
                lastHighlightedPipe = null;
                logic = new GameLogic(boardSize, random);
                canvas.repaint();
            });
            timer.setRepeats(false);
            timer.start();
        }
    }

    private void resetGame(){
        levelLabel.setText("Level: 1");
        lastHighlightedPipe=null;
        logic = new GameLogic(boardSize,random);
        resetKeyboardListener();
        canvas.repaint();
    }

    private void drawCanvas(Graphics g) {
        for(int i=0;i<logic.getSize();++i){
            for(int j=0;j<logic.getSize();++j){
                try {
                    if (logic.getPipe(j, i) != null) {
                        logic.getPipe(j, i).draw(g);
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("Array index out of bounds: " + e.getMessage());
                }
            }
        }
    }
}
