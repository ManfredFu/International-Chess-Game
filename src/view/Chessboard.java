package view;


import ExceptionHandle.InvalidChessException;
import ExceptionHandle.NoPlayerException;
import ExceptionHandle.SizeException;
import model.ChessColor;
import model.ChessComponent;
import model.EmptySlotComponent;
import model.RookChessComponent;
import model.BishopChessComponent;
import model.KnightChessComponent;
import model.KingChessComponent;
import model.QueenChessComponent;
import model.PawnChessComponent;
import controller.ClickController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 这个类表示面板上的棋盘组件对象
 */
public class Chessboard extends JComponent {
    /**
     * CHESSBOARD_SIZE： 棋盘是8 * 8的
     * <br>
     * BACKGROUND_COLORS: 棋盘的两种背景颜色
     * <br>
     * chessListener：棋盘监听棋子的行动
     * <br>
     * chessboard: 表示8 * 8的棋盘
     * <br>
     * currentColor: 当前行棋方
     */
    private static final int CHESSBOARD_SIZE = 8;

    private final ChessComponent[][] chessComponents = new ChessComponent[CHESSBOARD_SIZE][CHESSBOARD_SIZE];
    private ChessColor currentColor = ChessColor.BLACK;
    //all chessComponents in this chessboard are shared only one model controller
    private final ClickController clickController = new ClickController(this);
    private final int CHESS_SIZE;
    private JLabel statusLabel, countDown;
    private int stepsCounter = 0, maximumTime = 30, timeLeft = maximumTime;
    private Timer switchPlayer = new Timer(1000, null);

    public Chessboard(int width, int height) {
        setLayout(null); // Use absolute layout.
        setSize(width, height);
        CHESS_SIZE = width / 8;
        System.out.printf("chessboard size = %d, chess size = %d\n", width, CHESS_SIZE);
        initiateEmptyChessboard();
    }

    public void initializeGame() {
        initiateEmptyChessboard();
        initRookOnBoard(0, 0, ChessColor.BLACK);
        initRookOnBoard(0, CHESSBOARD_SIZE - 1, ChessColor.BLACK);
        initRookOnBoard(CHESSBOARD_SIZE - 1, 0, ChessColor.WHITE);
        initRookOnBoard(CHESSBOARD_SIZE - 1, CHESSBOARD_SIZE - 1, ChessColor.WHITE);
        initKnightOnBoard(0, 1, ChessColor.BLACK);
        initKnightOnBoard(0, CHESSBOARD_SIZE - 2, ChessColor.BLACK);
        initKnightOnBoard(CHESSBOARD_SIZE - 1, 1, ChessColor.WHITE);
        initKnightOnBoard(CHESSBOARD_SIZE - 1, CHESSBOARD_SIZE - 2, ChessColor.WHITE);
        initBishopOnBoard(0, 2, ChessColor.BLACK);
        initBishopOnBoard(0, CHESSBOARD_SIZE - 3, ChessColor.BLACK);
        initBishopOnBoard(CHESSBOARD_SIZE - 1, 2, ChessColor.WHITE);
        initBishopOnBoard(CHESSBOARD_SIZE - 1, CHESSBOARD_SIZE - 3, ChessColor.WHITE);
        initQueenOnBoard(0, 3, ChessColor.BLACK);
        initQueenOnBoard(CHESSBOARD_SIZE - 1, 3, ChessColor.WHITE);
        initKingOnBoard(0, 4, ChessColor.BLACK);
        initKingOnBoard(CHESSBOARD_SIZE - 1, 4, ChessColor.WHITE);
        for (int i = 0; i < 8; i++) {
            initPawnOnBoard(1, i, ChessColor.BLACK);
            initPawnOnBoard(CHESSBOARD_SIZE - 2, i, ChessColor.WHITE);
        }
        repaint();
        statusLabel.setText("Black is moving!");
        currentColor = ChessColor.BLACK;
        releaseCache();
        stepsCounter = 0;
        saveChessCache();
        countDown();
    }


    public ChessComponent[][] getChessComponents() {
        return chessComponents;
    }

    public ChessColor getCurrentColor() {
        return currentColor;
    }

    public void putChessOnBoard(ChessComponent chessComponent) {
        int row = chessComponent.getChessboardPoint().getX(), col = chessComponent.getChessboardPoint().getY();

        if (chessComponents[row][col] != null) {
            remove(chessComponents[row][col]);
        }
        add(chessComponents[row][col] = chessComponent);
    }

    public void swapChessComponents(ChessComponent chess1, ChessComponent chess2) {
        int winningRecord = 0;
        // Note that chess1 has higher priority, 'destroys' chess2 if exists.
        if (!(chess2 instanceof EmptySlotComponent)) {
            if (chess2 instanceof KingChessComponent) {
                if (currentColor == ChessColor.WHITE) {
                    winningRecord = 1;
                } else {
                    winningRecord = 2;
                }
            }
            remove(chess2);
            add(chess2 = new EmptySlotComponent(chess2.getChessboardPoint(), chess2.getLocation(), clickController, CHESS_SIZE));
        }
        chess1.swapLocation(chess2);
        int row1 = chess1.getChessboardPoint().getX(), col1 = chess1.getChessboardPoint().getY();
        chessComponents[row1][col1] = chess1;
        int row2 = chess2.getChessboardPoint().getX(), col2 = chess2.getChessboardPoint().getY();
        chessComponents[row2][col2] = chess2;

        chess1.repaint();
        chess2.repaint();
        if (winningRecord != 0) {
            if (winningRecord == 1) {
                JOptionPane.showMessageDialog(null, "Game over. White has won the game.", "EndGame", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Game over. Black has won the game.", "EndGame", JOptionPane.INFORMATION_MESSAGE);
            }
            initializeGame();
        }
    }

    public void initiateEmptyChessboard() {
        for (int i = 0; i < chessComponents.length; i++) {
            for (int j = 0; j < chessComponents[i].length; j++) {
                putChessOnBoard(new EmptySlotComponent(new ChessboardPoint(i, j), calculatePoint(i, j), clickController, CHESS_SIZE));
            }
        }
    }

    public void swapColor() {
        currentColor = currentColor == ChessColor.BLACK ? ChessColor.WHITE : ChessColor.BLACK;
        if (currentColor == ChessColor.WHITE) {
            statusLabel.setText("White is moving!");
        } else {
            statusLabel.setText("Black is moving!");
        }
        saveChessCache();
        timeLeft = maximumTime;
    }

    private void initRookOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new RookChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void initBishopOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new BishopChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void initKnightOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new KnightChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void initQueenOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new QueenChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void initKingOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new KingChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void initPawnOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new PawnChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }


    private Point calculatePoint(int row, int col) {
        return new Point(col * CHESS_SIZE, row * CHESS_SIZE);
    }

    public void loadGame(List<String> saveFile) {
        saveFile.forEach(System.out::println);
        initiateEmptyChessboard();
        try {
            for (int i = 0; i < saveFile.size(); i++) {
                if (i == saveFile.size() - 1) {
                    if (saveFile.get(saveFile.size() - 1).charAt(0) == 'w') {
                        currentColor = ChessColor.WHITE;
                        statusLabel.setText("White is moving!");
                    } else if (saveFile.get(saveFile.size() - 1).charAt(0) == 'b') {
                        currentColor = ChessColor.BLACK;
                        statusLabel.setText("Black is moving!");
                    } else {
                        throw new NoPlayerException("Can not get current player, please check");
                    }
                    if (i != 8) {
                        throw new SizeException("The chessboard is not 8*8, please check");
                    }
                    break;
                }
                if (saveFile.get(i).length() != 8) {
                    System.out.println(saveFile.get(i).length());
                    throw new SizeException("The chessboard is not 8*8, please check");
                }
                for (int j = 0; j < saveFile.get(i).length(); j++) {
                    switch (saveFile.get(i).charAt(j)) {
                        case '_':
                            break;
                        case 'K':
                            initKingOnBoard(i, j, ChessColor.BLACK);
                            break;
                        case 'k':
                            initKingOnBoard(i, j, ChessColor.WHITE);
                            break;
                        case 'N':
                            initKnightOnBoard(i, j, ChessColor.BLACK);
                            break;
                        case 'n':
                            initKnightOnBoard(i, j, ChessColor.WHITE);
                            break;
                        case 'P':
                            initPawnOnBoard(i, j, ChessColor.BLACK);
                            break;
                        case 'p':
                            initPawnOnBoard(i, j, ChessColor.WHITE);
                            break;
                        case 'Q':
                            initQueenOnBoard(i, j, ChessColor.BLACK);
                            break;
                        case 'q':
                            initQueenOnBoard(i, j, ChessColor.WHITE);
                            break;
                        case 'R':
                            initRookOnBoard(i, j, ChessColor.BLACK);
                            break;
                        case 'r':
                            initRookOnBoard(i, j, ChessColor.WHITE);
                            break;
                        case 'B':
                            initBishopOnBoard(i, j, ChessColor.BLACK);
                            break;
                        case 'b':
                            initBishopOnBoard(i, j, ChessColor.WHITE);
                            break;
                        default:
                            throw new InvalidChessException("Invalid chess type detected, please check");
                    }
                }
            }
        } catch (SizeException | NoPlayerException | InvalidChessException e) {
            JOptionPane.showMessageDialog(null, e.toString(), "ERROR", JOptionPane.ERROR_MESSAGE);
            initializeGame();
        }
        repaint();
        countDown();
    }

    public List<String> saveGame() {
        StringBuilder rowColumns = new StringBuilder();
        List<String> returnValue = new ArrayList<>();
        ChessComponent[][] nowChessComponent = getChessComponents();
        for (int i = 0; i < nowChessComponent.length; i++) {
            for (int j = 0; j < nowChessComponent[i].length; j++) {
                rowColumns.append(nowChessComponent[i][j].getChessType(nowChessComponent[i][j].getChessColor()));
            }
            returnValue.add(i, rowColumns.toString());
            rowColumns.setLength(0);
        }
        if (currentColor == ChessColor.WHITE) {
            returnValue.add(8, "w");
        } else {
            returnValue.add(8, "b");
        }
        return returnValue;
    }

    public void saveChessCache() {
        try {
            FileWriter saveWriter = new FileWriter("ChessCache/movement" + stepsCounter + ".txt", false);
            for (int i = 0; i < saveGame().size(); i++) {
                saveWriter.write(saveGame().get(i) + "\r\n");
            }
            stepsCounter++;
            saveWriter.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.toString(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void releaseCache() {
        File file = new File("ChessCache");
        File[] files = file.listFiles();
        assert files != null;
        for (File f : files) {
            f.delete();
        }
    }

    public void countDown() {
        if (switchPlayer.isRunning()) {
            switchPlayer.stop();
            timeLeft = maximumTime;
        }
        ActionListener timeListener = e -> {
            if (timeLeft == 0) {
                swapColor();
                this.timeLeft = maximumTime;
            }
            countDown.setText("Time left:" + timeLeft);
            timeLeft--;
        };
        switchPlayer = new Timer(1000, timeListener);
        countDown.setText("Time left:" + timeLeft);
        switchPlayer.start();
    }

    public List<ChessComponent> convertPointToComponent(List<ChessboardPoint> chessboardPoints) {
        List<ChessComponent> chessComponents = new ArrayList<>();
        for (ChessboardPoint Points : chessboardPoints) {
            ChessComponent nowComponent = this.chessComponents[Points.getX()][Points.getY()];
            chessComponents.add(nowComponent);
        }
        return chessComponents;
    }

    public void aimSetterTrue(List<ChessComponent> chessComponents) {
        for (ChessComponent chessComponent : chessComponents) {
            chessComponent.setCanBeMovedTo(true);
            chessComponent.repaint();
        }
    }

    public void aimSetterFalse(List<ChessComponent> chessComponents) {
        for (ChessComponent chessComponent : chessComponents) {
            chessComponent.setCanBeMovedTo(false);
            chessComponent.repaint();
        }
    }

    public void checkMate(ChessComponent chessComponent,List<ChessboardPoint> chessboardPoints, ChessColor chessColor) {
        if (chessColor == ChessColor.BLACK) {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (chessComponents[i][j].getChessType(ChessColor.WHITE) == 'k') {
                        if (chessboardPoints.contains(new ChessboardPoint(i, j))) {
                            JOptionPane.showMessageDialog(null, "BLACK CHECKMATE!", "CHECK", JOptionPane.WARNING_MESSAGE);
                        }
                    }
                }
            }
        } else {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (chessComponents[i][j].getChessType(ChessColor.BLACK) == 'K') {
                        if (chessboardPoints.contains(new ChessboardPoint(i, j))) {
                            JOptionPane.showMessageDialog(null, "WHITE CHECKMATE!", "CHECK", JOptionPane.WARNING_MESSAGE);
                        }
                    }
                }
            }
        }
        chessComponent.repaint();
    }


    public void setStatusLabel(JLabel statusLabel) {
        this.statusLabel = statusLabel;
    }

    public void setCountDown(JLabel countDown) {
        this.countDown = countDown;
    }

    public int getStepsCounter() {
        return stepsCounter;
    }

    public void setStepsCounter(int stepsCounter) {
        this.stepsCounter = stepsCounter;
    }
}
