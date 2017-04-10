package main.java.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.BiConsumer;

public class Board {
    private int size;
    private BoardNode[] board;

    public Board(int size) {
        this.size = size;

        board = new BoardNode[size * size];

        forEach( (i,j) ->
            this.set(i, j, new BoardNode(newChar(), new CoordPair(i,j), null))
        );

        updateNeighbours();
    }

    public BoardNode get(int i, int j) {
        return board[size * i + j];
    }

    public ArrayList<BoardNode> getNodes() {
        return new ArrayList<>(Arrays.asList(board));
    }

    private void set(int i, int j, BoardNode in) {
        board[size * i + j] = in;
    }

    public String toPrintString() {
        String str = "";

        for (int i = 0; i < size; i++) {
            str += "[";

            for (int j = 0; j < size - 1; j++)
                str += this.get(i, j) + ", ";

            str += this.get(i, size - 1) + "]\n";
        }


        return str;
    }

    private void updateNeighbours() {
        forEach((Integer i, Integer j) -> {
            BoardNode node = this.get(i,j);

            // Well at least it's not hardcoded
            for (int k = i - 1; k <= i + 1; k++) {
                for(int l = j - 1; l <= j + 1; l++) {
                    if ( (k >= 0 && k < size) && (l >= 0 && l < size) && !(k == i && l == j) )
                        node.addNeighbour(this.get(k,l));
                }
            }
        });
    }

    public void forEach(BiConsumer<Integer, Integer> f) {
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                f.accept(i, j);
    }

    public static Board boardFromString(String chars) {
        chars = chars.toLowerCase();
        int size = new Double(Math.sqrt(chars.length())).intValue();

        if ((size * size) == chars.length() ) {
            Board board = new Board(size);

            int x = 0;
            int y = 0;
            for (int i = 0; i < chars.length(); i++) {
                char c = chars.charAt(i);

                board.set(x, y, new BoardNode(c, new CoordPair(x, y), null));

                y++;
                if (y >= size) {
                    y = 0;
                    x++;
                }
            }

            board.updateNeighbours();
            return board;
        }

        return null;
    }

    public int getSize() {
        return size;
    }

    private static char newChar() {
        return "abcdefghijklmnopqrstuvwxyz".charAt((int) (Math.random() * 26));
    }
}
