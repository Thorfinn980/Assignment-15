import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class App extends Application {

    private static final int BOARD_SIZE = 3;
    private char currentPlayer = 'X';
    private Cell[][] cells = new Cell[BOARD_SIZE][BOARD_SIZE];
    private Label statusLabel = new Label("X's turn to play");

    @Override
    public void start(Stage primaryStage) {

        GridPane board = new GridPane();
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                Cell cell = new Cell(row, col);
                cells[row][col] = cell;
                board.add(cell, col, row);
            }
        }

        Scene scene = new Scene(new StackPane(board, statusLabel), 450, 450);
        primaryStage.setScene(scene);
        primaryStage.setTitle("TicTacToe");
        primaryStage.show();
    }

    public boolean isFull() {
        for (Cell[] row : cells) {
            for (Cell cell : row) {
                if (cell.getToken() == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isWon(char token) {
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (cells[i][0].getToken() == token
                    && cells[i][1].getToken() == token
                    && cells[i][2].getToken() == token) {
                return true;
            }
            if (cells[0][i].getToken() == token
                    && cells[1][i].getToken() == token
                    && cells[2][i].getToken() == token) {
                return true;
            }
        }
        if (cells[0][0].getToken() == token
                && cells[1][1].getToken() == token
                && cells[2][2].getToken() == token) {
            return true;
        }
        if (cells[0][2].getToken() == token
                && cells[1][1].getToken() == token
                && cells[2][0].getToken() == token) {
            return true;
        }
        return false;
    }

    private void endGame(String message) {
        statusLabel.setText(message);
        currentPlayer = ' ';
        for (Cell[] row : cells) {
            for (Cell cell : row) {
                cell.setDisable(true);
            }
        }
    }

    private class Cell extends StackPane {

        private char token = ' ';
        private final int row;
        private final int col;

        public Cell(int row, int col) {
            this.row = row;
            this.col = col;
            setStyle("-fx-border-color: black");
            setPrefSize(150, 150);
            setOnMouseClicked(event -> handleMouseClick());
        }

        public char getToken() {
            return token;
        }

        public void setToken(char c) {
            token = c;
            if (token == 'X') {
                Line line1 = new Line(10, 10, this.getWidth() - 10, this.getHeight() - 10);
                line1.endXProperty().bind(this.widthProperty().subtract(10));
                line1.endYProperty().bind(this.heightProperty().subtract(10));
                Line line2 = new Line(10, this.getHeight() - 10, this.getWidth() - 10, 10);
                line2.startYProperty().bind(this.heightProperty().subtract(10));
                line2.endXProperty().bind(this.widthProperty().subtract(10));
                this.getChildren().addAll(line1, line2);
            } else if (token == 'O') {
                Ellipse ellipse = new Ellipse(this.getWidth() / 2, this.getHeight() / 2, this.getWidth() / 2 - 10,
                        this.getHeight() / 2 - 10);
                ellipse.centerXProperty().bind(this.widthProperty().divide(2));
                ellipse.centerYProperty().bind(this.heightProperty().divide(2));
                ellipse.radiusXProperty().bind(this.widthProperty().divide(2).subtract(10));
                ellipse.radiusYProperty().bind(this.heightProperty().divide(2).subtract(10));
                ellipse.setStroke(Color.BLACK);
                ellipse.setFill(Color.WHITE);
                getChildren().add(ellipse);
            }
        }

        private void handleMouseClick() {
            if (token == ' ' && currentPlayer != ' ') {
              setToken(currentPlayer);
              if (isWon(currentPlayer)) {
                statusLabel.setText(currentPlayer + " won! The game is over");
                currentPlayer = ' '; 
              }
              else if (isFull()) {
                statusLabel.setText("Draw! The game is over");
                currentPlayer = ' '; 
              }
              else {
                currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
                statusLabel.setText(currentPlayer + "'s turn");
              }
            }
          }
    }
}        
