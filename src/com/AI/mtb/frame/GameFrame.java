package com.AI.mtb.frame;

import com.AI.mtb.data.Board;
import com.AI.mtb.data.GameData;
import com.AI.mtb.util.GameTool;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class GameFrame extends JFrame {

    private int frameWidth;
    private int frameHeight;

    public GameFrame(String title, int frameWidth, int frameHeight) {

        super(title);

        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;

        GamePanel panel = new GamePanel();
        this.setContentPane(panel);
        this.pack();
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setVisible(true);
    }

    public GameFrame(String title) {
        this(title, 1920, 1080);
    }

    public int getFrameWidth() {
        return frameWidth;
    }

    public int getFrameHeight() {
        return frameHeight;
    }

    private GameData data;
    public void render(GameData data) {
        this.data = data;
        this.repaint();
    }

    private class GamePanel extends JPanel {

        private HashMap<Character, Color> colorMap;
        private ArrayList<Color> colorList;

        public GamePanel() {

            super(true); // 双缓存

            colorMap = new HashMap<Character, Color>();

            colorList = new ArrayList<Color>();
            colorList.add(GameTool.Red);
            colorList.add(GameTool.Purple);
            colorList.add(GameTool.Blue);
            colorList.add(GameTool.Teal);
            colorList.add(GameTool.LightGreen);
            colorList.add(GameTool.Lime);
            colorList.add(GameTool.Amber);
            colorList.add(GameTool.DeepOrange);
            colorList.add(GameTool.Brown);
            colorList.add(GameTool.BlueGrey);
        }

        @Override
        protected void paintComponent(Graphics g) {

            super.paintComponent(g);

            Graphics2D g2d = (Graphics2D) g;

            // 抗锯齿
            RenderingHints hints = new RenderingHints(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );
            g2d.addRenderingHints(hints);

            // 具体绘制
            int w = frameWidth / data.M();
            int h = frameHeight / data.N();

            Board showBoard = data.getShowBoard();
            for (int i = 0; i < showBoard.N(); i++) {
                for (int j = 0; j < showBoard.M(); j++) {
                    char c = showBoard.getData(i ,j);
                    if (c != Board.EMPTY) {

                        if (!colorMap.containsKey(c)) {
                            int size = colorMap.size();
                            colorMap.put(c, colorList.get(size));
                        }

                        Color color = colorMap.get(c);
                        GameTool.setColor(g2d, color);
                        GameTool.fillRectangle(g2d, j*h+2, i*w+2, w-4, h-4);

                        GameTool.setColor(g2d, GameTool.White);
                        String text = String.format("(%d, %d)", i, j);
                        GameTool.drawText(g2d, text, j*h+h/2, i*w+w/2);
                    }
                }
            }
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(frameWidth, frameHeight);
        }
    }
}
