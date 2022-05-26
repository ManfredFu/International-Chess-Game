package view;

import controller.GameController;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * 这个类表示游戏过程中的整个游戏界面，是一切的载体
 */
public class ChessGameFrame extends JFrame {
    //    public final Dimension FRAME_SIZE ;
    private final int WIDTH;
    private final int HEIGTH;
    public final int CHESSBOARD_SIZE;
    private GameController gameController;
    private JLabel statusLabel;
    private String errorMsg;

    public ChessGameFrame(int width, int height) {
        setTitle("Chess Game Ultra Max Pro Ultimate"); //设置标题
        this.WIDTH = width;
        this.HEIGTH = height;
        this.CHESSBOARD_SIZE = HEIGTH * 4 / 5;

        setSize(WIDTH, HEIGTH);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);

        addMovingChessLabel();
        addChessboard();
        addResetButton();
        addLoadBrowseButton();
        addSaveButton();
    }


    /**
     * 在游戏面板中添加棋盘
     */
    private void addChessboard() {
        Chessboard chessboard = new Chessboard(CHESSBOARD_SIZE, CHESSBOARD_SIZE);
        chessboard.setStatusLabel(statusLabel);
        gameController = new GameController(chessboard);
        chessboard.setLocation(HEIGTH / 10, HEIGTH / 10);
        chessboard.initializeGame();
        add(chessboard);
    }

    /**
     * 在游戏面板中添加标签
     */
    private void addMovingChessLabel() {
        statusLabel = new JLabel();
        statusLabel.setLocation(HEIGTH, HEIGTH / 10);
        statusLabel.setSize(200, 60);
        statusLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(statusLabel);
    }

    /**
     * 在游戏面板中增加一个按钮，如果按下的话就会显示Hello, world!
     */

    private void addResetButton() {
        JButton button = new JButton("Reset");
        button.addActionListener((e) -> gameController.initialGame());
        button.setLocation(HEIGTH, HEIGTH / 10 + 120);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }


    private void addLoadBrowseButton() {
        JButton button = new JButton("LoadBrowse");
        button.setLocation(HEIGTH, HEIGTH / 10 + 240);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.addActionListener(e -> {
            JFileChooser loadChoose = new JFileChooser();
            loadChoose.setFileSelectionMode(JFileChooser.OPEN_DIALOG);
            loadChoose.showOpenDialog(null);
            File f = loadChoose.getSelectedFile();
            gameController.loadGameFromFile(f);
        });
    }

    private void addSaveButton() {
        JButton button = new JButton("Save");
        button.setLocation(HEIGTH, HEIGTH / 10 + 340);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
        button.addActionListener(listener -> {
            JFileChooser saveChoose = new JFileChooser();
            saveChoose.setFileSelectionMode(JFileChooser.OPEN_DIALOG);
            saveChoose.showSaveDialog(null);
            String path = saveChoose.getSelectedFile().getPath();
            gameController.saveGame(path);
        });
    }
}
