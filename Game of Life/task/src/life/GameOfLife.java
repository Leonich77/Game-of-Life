package life;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;



//test for trst pr
public class GameOfLife extends JFrame {
    private final JPanel livePanel;
    private char[][] currentGeneration;
    public final JLabel generationLabel;
    public final JLabel aliveLabel;
    private final Main.Universe universe;
    private LiveThread lth;
    private final int numGenerations = 10;
    private final JPanel commandPanel;
    private final JPanel buttonPanel;
    private final JPanel labelPanel;
    private final JPanel sliderPanel;
    private final JPanel gofPanel;
    private int speed = 1000;

    public GameOfLife() throws InterruptedException {
        super("Game Of Life");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(710, 720);
        setResizable(false);
        setLocationRelativeTo(null);

        int universeSize = 30;

        universe = new Main.Universe(universeSize);
        currentGeneration = universe.initUniverse();

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel,BoxLayout.X_AXIS));
        Icon iconStart =  new ImageIcon("icons/play.png");
        Icon iconPause =  new ImageIcon("icons/pause.png");
        Icon iconRestart =  new ImageIcon("icons/reload.png");
        JButton btnStart = new JButton(iconStart);
        JToggleButton playToggleButton = new JToggleButton(iconPause);
        playToggleButton.setName("PlayToggleButton");
        JButton resetButton = new JButton(iconRestart);
        resetButton.setName("ResetButton");

        btnStart.addActionListener(e -> {
            startLive();
            btnStart.setEnabled(false);
            playToggleButton.setSelected(false);
        });

        playToggleButton.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                stopLive();
            } else {
                btnStart.setEnabled(false);
                startLive();
            }
        });

        resetButton.addActionListener(e -> {
            stopLive();
            btnStart.setEnabled(true);
            currentGeneration = universe.initUniverse();
            if (!playToggleButton.isSelected()) {
                startLive();
            }
            repaint();
        });
        buttonPanel.add(btnStart);
        buttonPanel.add(Box.createRigidArea(new Dimension(10,0)));
        buttonPanel.add(playToggleButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(10,0)));
        buttonPanel.add(resetButton);

        labelPanel = new JPanel();
        labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));
        labelPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        generationLabel = new JLabel();
        generationLabel.setName("GenerationLabel");
        aliveLabel = new JLabel();
        aliveLabel.setName("AliveLabel");
        labelPanel.add(generationLabel);
        labelPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        labelPanel.add(aliveLabel);

        sliderPanel = new JPanel();
        sliderPanel.setLayout(new BoxLayout(sliderPanel, BoxLayout.Y_AXIS));
        sliderPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel sliderLabel = new JLabel("Speed mode:");
        sliderPanel.add(sliderLabel);
        sliderPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        JSlider speedSlider = new JSlider(1, 10, 1 );
        speedSlider.setMaximumSize(new Dimension(180, 50));
        speedSlider.setPreferredSize(new Dimension(180,50));
        speedSlider.setMinimumSize(new Dimension(100,50));
        speedSlider.setMajorTickSpacing(1);
        speedSlider.setPaintTicks(true);
        speedSlider.setPaintLabels(true);
        speedSlider.addChangeListener(e -> {
            JSlider source = (JSlider)e.getSource();
            if (!source.getValueIsAdjusting()) {
                int spd = (int)source.getValue();
                speed = 1000 / spd;
            }
        });
        sliderPanel.add(speedSlider);

        commandPanel = new JPanel();
        commandPanel.setLayout(new BoxLayout(commandPanel,BoxLayout.Y_AXIS));
        commandPanel.setAlignmentY(Component.TOP_ALIGNMENT);
        commandPanel.add(buttonPanel);
        commandPanel.add(Box.createRigidArea(new Dimension(0,30)));
        commandPanel.add(labelPanel);
        commandPanel.add(Box.createRigidArea(new Dimension(0,30)));
        commandPanel.add(sliderPanel);
        commandPanel.add(Box.createVerticalGlue());

        livePanel = new LivePanel();
        livePanel.setLayout(new BoxLayout(livePanel, BoxLayout.X_AXIS));
        livePanel.add(Box.createVerticalGlue());
        livePanel.add(Box.createHorizontalGlue());
        livePanel.setBorder(BorderFactory.createLineBorder(Color.black));

        gofPanel = new JPanel();
        gofPanel.setLayout(new BoxLayout(gofPanel, BoxLayout.X_AXIS));
        gofPanel.setBorder(BorderFactory.createLineBorder(SystemColor.control, 10));
        gofPanel.add(commandPanel);
        gofPanel.add(Box.createRigidArea(new Dimension(10,0)));
        gofPanel.add(livePanel);
        add(gofPanel);

        setVisible(true);
    }

    private void startLive() {
        if (lth == null) {
            lth = new LiveThread();
            lth.start();
        }
    }

    private void stopLive() {
        if (lth != null) {
            lth.interrupt();
            try {
                lth.join();
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        }
        lth = null;
    }

    private class LiveThread extends Thread {

        @Override
        public void run() {
            while (!isInterrupted()){
                try {
                    Thread.sleep(speed);
                } catch (InterruptedException interruptedException) {
                    break;
                }
                currentGeneration = universe.liveUniverse();
                repaint();
            }
        }
    }

    private class LivePanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            int sizeLive = currentGeneration.length;
            int sizeFieldX = livePanel.getWidth();
            int sizeFieldY = livePanel.getHeight();
            int sizeCellX = sizeFieldX / sizeLive;
            int sizeCellY = sizeFieldY / sizeLive;

            generationLabel.setText("Generation #".concat(String.valueOf(universe.getCountGenerations())));
            aliveLabel.setText("Alive: ".concat(String.valueOf(universe.getAliveCount())));
            for (int i = 0; i < sizeLive; i++) {
                g.drawLine(i * sizeCellX, 0, i * sizeCellX, sizeFieldY);
                g.drawLine(0,i * sizeCellY,  sizeFieldX, i * sizeCellY);
            }

            g.setColor(Color.BLUE);
            for (int i = 0; i < sizeLive; i++) {
                for (int j = 0; j < sizeLive; j++) {
                    if (currentGeneration[i][j] == 'O'){
                        g.fillOval(j * sizeCellX, i * sizeCellY, sizeCellX, sizeCellY);
                    }
                }
            }
        }
    }
}