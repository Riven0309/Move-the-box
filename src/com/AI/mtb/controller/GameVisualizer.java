package com.AI.mtb.controller;

import com.AI.mtb.data.GameData;
import com.AI.mtb.frame.GameFrame;
import com.AI.mtb.util.GameTool;

import java.awt.*;

public class GameVisualizer {

    private GameData data;
    private GameFrame frame;

    private static int blockSide = 80;
    private static final int DELAY = 40;

    public GameVisualizer(String filename) {

        // 初始化数据
        data = new GameData(filename);
        int width = data.M() * blockSide;
        int height = data.N() * blockSide;

        // 初始化视图
        EventQueue.invokeLater(() -> {

            frame = new GameFrame("Move The Box", width, height);

            new Thread(() -> run()).start();
        });
    }

    private void run() {

        setData();

        if (data.solve())
            System.out.println("The game have solution");
        else
            System.out.println("The game have no solution");
    }

    private void setData() {

        frame.render(data);
        GameTool.pause(DELAY);
    }
}
