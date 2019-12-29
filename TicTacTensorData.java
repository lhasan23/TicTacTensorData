import java.util.Arrays;
import java.util.Random;

/**
 * Created by Latifa on 3/4/18.
 */
public class TicTacTensorData {

    // main method
    // either generate all data and print or do some random data
    public static void main(String[] args) {
        Board b = new Board();

        if (args.length < 1) {
            backtrack(b, true);
            return;
        }

        // FIGURE OUT WHAT THIS SECTION IS DOING. START
        if (args[0].equals("random")) { // play 10 random games and print
            Random random = new Random();
            for (int i = 0; i < 10; ++i) {
                while (!b.wonOrDone()) {
                    b.place(random.nextInt(3), random.nextInt(3));
                }
                System.out.println(b);
                System.out.println(b.getWinner());
                b = new Board();
            }
        }
        // END
        else if (args[0].equals("data")) { // prints all possibilities
            backtrack(b, false);
        }
        else {
            System.out.println("Invalid command");
        }
    }

    // backtracking method, recursively goes through all possibilities
    public static void backtrack(Board b, boolean verbose) {
        if (b.wonOrDone()) {
            if (verbose) {
                System.out.println("Done: " + b.getWinner());
                System.out.println(b.toString());
            }
            System.out.println(b.placements());
            return;
        }

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                Board board = new Board(b);
                if (board.place(i, j)) backtrack(board, verbose);
            }
        }
    }

    public enum Piece {
        BLANK, X, O
    }

    public static class Board {
        Piece[][] board;
        boolean xTurn; // true if x is to place, false if o is to place
        StringBuilder placements;

        // constructor
        public Board() {
            board = new Piece[3][3];
            for (Piece[] row : board) Arrays.fill(row, Piece.BLANK);
            xTurn = true;
            placements = new StringBuilder();
        }

        // constructor
        public Board(Board other) {
            board = new Piece[3][3];
            for (int i = 0; i < 3; ++i) {
                for (int j = 0; j < 3; ++j) {
                    board[i][j] = other.board[i][j];
                }
            }
            xTurn = other.xTurn;
            placements = new StringBuilder();
            placements.append(other.placements.toString());
        }

        // places the next move
        public boolean place(int row, int col) {
            if (board[row][col] != Piece.BLANK) return false;

            board[row][col] = xTurn ? Piece.X : Piece.O;
            xTurn = !xTurn;
            placements.append(row * 3 + col);
            placements.append(" ");
            return true;
        }

        // true if completed game
        public boolean wonOrDone() {
            boolean blanks = false; // are there any blank spaces left?
            for (int i = 0; i < 3; ++i) {
                for (int j = 0; j < 3; ++j) {
                    if (board[i][j] == Piece.BLANK) blanks = true;
                }
            }
            if (!blanks) return true;

            for (int i = 0; i < 3; ++i) {
                if (board[i][0] != Piece.BLANK && board[i][0] == board[i][1] && board[i][0] == board[i][2]) return true;
                if (board[0][i] != Piece.BLANK && board[0][i] == board[1][i] && board[0][i] == board[2][i]) return true;
            }
            if (board[0][0] != Piece.BLANK && board[0][0] == board[1][1] && board[0][0] == board[2][2]) return true;
            if (board[0][2] != Piece.BLANK && board[0][2] == board[1][1] && board[0][2] == board[2][0]) return true;
            return false;
        }

        // returns winner
        public Piece getWinner() {
            for (int i = 0; i < 3; ++i) {
                if (board[i][0] != Piece.BLANK && board[i][0] == board[i][1] && board[i][0] == board[i][2]) return board[i][0];
                if (board[0][i] != Piece.BLANK && board[0][i] == board[1][i] && board[0][i] == board[2][i]) return board[0][i];
            }
            if (board[0][0] != Piece.BLANK && board[0][0] == board[1][1] && board[0][0] == board[2][2]) return board[1][1];
            if (board[0][2] != Piece.BLANK && board[0][2] == board[1][1] && board[0][2] == board[2][0]) return board[1][1];

            return Piece.BLANK;
        }

        // helper to debug
        public String placements() {
            return placements.toString() + ", " + getWinner();
        }

        // prints board
        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 3; ++i) {
                for (int j = 0; j < 3; ++j) {
                    switch (board[i][j]) {
                        case BLANK: sb.append(" "); break;
                        case X: sb.append("X"); break;
                        case O: sb.append("O"); break;
                        default: sb.append(" "); break;
                    }
                    sb.append("|");
                }
                sb.deleteCharAt(sb.length()-1);
                sb.append("\n");
                sb.append(i < 2 ? "-----\n" : "\n");
            }
            return sb.toString();
        }
    }
}
