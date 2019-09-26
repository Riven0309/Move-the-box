package com.AI.mtb.data;

public class Board {

    public static final char EMPTY = '.';

    private int N, M;
    private char[][] data;

    private Board preBoard = null;
    private String swapString = "";

    public Board(String[] lines) {

        if (lines == null)
            throw new IllegalArgumentException("error");

        this.N = lines.length;
        if (N == 0)
            throw new IllegalArgumentException("error");

        this.M = lines[0].length();

        this.data = new char[N][M];
        for (int i = 0; i < N; i++) {
            if (lines[i].length() != M)
                throw new IllegalArgumentException("error");

            for (int j = 0; j < M; j++) {
                data[i][j] = lines[i].charAt(j);
            }
        }
    }

    public Board(Board board, Board preBoard, String swapString) {

        if (board == null)
            throw new IllegalArgumentException("error");

        this.N = board.N;
        this.M = board.M;
        this.data = new char[N][M];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                this.data[i][j] = board.data[i][j];
            }
        }

        this.preBoard = preBoard;
        this.swapString = swapString;
    }

    public int N() {
        return N;
    }

    public int M() {
        return M;
    }

    public Board(Board board) {
        this(board, null, "");
    }

    public char getData(int x, int y) {

        if (!inArea(x, y))
            throw new IllegalArgumentException("error");

        return data[x][y];
    }

    public boolean inArea(int x, int y) {
        return x >= 0 && x < N && y >= 0 && y < M;
    }

    public void print() {
        for (int i = 0; i < N; i++) {
            System.out.println(String.valueOf(data[i]));
        }
    }

    public boolean isWin() {

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (data[i][j] != EMPTY)
                    return false;
            }
        }
        printSwapInfo();
        return true;
    }

    public void swap(int x1, int y1, int x2, int y2) {

        if (!inArea(x1, y1) || !inArea(x2, y2))
            throw new IllegalArgumentException("error");

        char temp = data[x1][y1];
        data[x1][y1] = data[x2][y2];
        data[x2][y2] = temp;

        return;
    }

    public void run() {

        do {
            drop();
        } while (match());

        return;
    }

    // 箱子掉落处理
    private void drop() {

        for (int j = 0; j < M; j++) {
            int cur = N - 1;
            for (int i = N - 1; i >= 0; i--) {
                if (data[i][j] != EMPTY) {
                    swap(i, j, cur, j);
                    cur --;
                }
            }
        }
        return;
    }

    private static int[][] d = {{0, 1}, {1, 0}};
    // 箱子消除处理
    private boolean match() {

        boolean isMatched = false;

        boolean[][] flag = new boolean[N][M];
        for (int x = 0; x < N; x++) {
            for (int y = 0; y < M; y++) {
                if (data[x][y] != EMPTY) {
                    for (int i = 0; i < 2; i++) {
                        int newX1 = x + d[i][0];
                        int newY1 = y + d[i][1];
                        int newX2 = newX1 + d[i][0];
                        int newY2 = newY1 + d[i][1];
                        if (inArea(newX1, newY1) && inArea(newX2, newY2) &&
                                data[newX1][newY1] == data[x][y] &&
                                data[newX2][newY2] == data[x][y]) {
                            flag[x][y] = true;
                            flag[newX1][newY1] = true;
                            flag[newX2][newY2] = true;

                            isMatched = true;
                        }
                    }
                }
            }
        }

        for (int x = 0; x < N; x++) {
            for (int y = 0; y < M; y++) {
                if (flag[x][y])
                    data[x][y] = EMPTY;
            }
        }

        return isMatched;
    }

    private void printSwapInfo() {

        if (preBoard != null)
            preBoard.printSwapInfo();
        System.out.println(swapString);

        return;
    }
}
