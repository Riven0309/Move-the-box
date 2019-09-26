package com.AI.mtb.data;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class GameData {

    private int maxStep;
    private Board starterBoard;
    private int N, M;

    private Board showBoard;

    public GameData(String filename) {

        if (filename == null)
            throw new IllegalArgumentException("Filename can not be null");

        Scanner scanner = null;

        try {
            File file = new File(filename);
            if (!file.exists())
                throw new IllegalArgumentException("file doesn't exists ");

            FileInputStream fis = new FileInputStream(file);
            scanner = new Scanner(new BufferedInputStream(fis), "UTF-8");

            String stepLine = scanner.nextLine();
            this.maxStep = Integer.parseInt(stepLine);

            ArrayList<String> lines = new ArrayList<>();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                lines.add(line);
            }
            starterBoard = new Board(lines.toArray(new String[lines.size()]));
            this.N = starterBoard.N();
            this.M = starterBoard.M();

            starterBoard.print();

            showBoard = new Board(starterBoard);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (scanner != null)
                scanner.close();
        }
    }

    public int N() {
        return N;
    }

    public int M() {
        return M;
    }

    public Board getShowBoard() {
        return showBoard;
    }

    public boolean inArea(int x, int y) {
        return x >= 0 && x < N && y >= 0 && y < M;
    }

    public boolean solve() {

        if (maxStep < 0)
            return false;

        return solve(starterBoard, maxStep);
    }

    private static int[][] d = {{1, 0}, {0, 1}, {0, -1}};
    // 通过盘面board，使用step次move，解决问题
    private boolean solve(Board board, int step) {

        if (board == null || step < 0)
            throw new IllegalArgumentException("error");

        if (step == 0)
            return board.isWin();

        if (board.isWin())
            return true;

        for (int x = 0; x < N; x++) {
            for (int y = 0; y < M; y++) {
                if (board.getData(x, y) != Board.EMPTY) {
                    for (int i = 0; i < 3; i++) {
                        int newX = x + d[i][0];
                        int newY = y + d[i][1];
                        if (inArea(newX, newY)) {
                            String swapString = String.format("swap (%d, %d) and (%d, %d)", x, y, newX, newY);
                            Board nextBoard = new Board(board, board, swapString);
                            nextBoard.swap(x, y, newX, newY);
                            nextBoard.run();
                            if (solve(nextBoard, step - 1))
                                return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
