package view;

import controller.GameController;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Locale;

/**
 * 这个类表示游戏过程中的整个游戏界面，是一切的载体
 */
public class ChessGameFrame extends JFrame {
    //    public final Dimension FRAME_SIZE ;
    private final int F_WIDTH;
    private final int F_HEIGHT;
    public final int CHESSBOARD_SIZE;
    private Chessboard chessboard;
    private GameController gameController;
    private JLabel statusLabel,countDown;
    private JLayeredPane layeredPane;
    private BackgroundPanel backgroundImagePanel;
    
    public ChessGameFrame(int width, int height) {
        setTitle("Chess Game Ultra Max Pro Ultimate"); //设置标题
        this.F_WIDTH = width;
        this.F_HEIGHT = height;
        this.CHESSBOARD_SIZE = F_HEIGHT * 3 / 5;

        setSize(F_WIDTH, F_HEIGHT);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);
        
        layeredPane=new JLayeredPane();
        addBackgroundImage();
        addMovingChessLabel();
        addCountDown();
        addChangeBackgroundButton();
        addUndoButton();
        addReplayButton();
        addChessboard();
        addResetButton();
        addLoadBrowseButton();
        addSaveButton();
        setLayeredPane(layeredPane);
    }

    /**
     * 添加背景
     */
    private void addBackgroundImage(){
        try{
            backgroundImagePanel=new BackgroundPanel(ImageIO.read(new File("images\\default_background_image_cropped.jpg")));
        }
        catch(Exception e){
            e.printStackTrace();
        }
        backgroundImagePanel.setBounds(0,0,F_WIDTH,F_HEIGHT);
        layeredPane.add(backgroundImagePanel,JLayeredPane.DEFAULT_LAYER);
        backgroundImagePanel.repaint();
    }
    private class BackgroundPanel extends JPanel{
        BufferedImage backgroundImage;
        public BackgroundPanel(BufferedImage backgroundImage){
            this.backgroundImage=backgroundImage;
            this.setOpaque(true);
        }
        public void setBackgroundImage(BufferedImage backgroundImage){
            this.backgroundImage=backgroundImage;
        }
        @Override
        public void paint(Graphics g){
            g.drawImage(backgroundImage,0,-30,F_WIDTH, F_HEIGHT,null);
        }
    }
    
    /**
     * 在游戏面板中添加棋盘
     */
    private void addChessboard() {
        Chessboard chessboard = new Chessboard(CHESSBOARD_SIZE, CHESSBOARD_SIZE);
        chessboard.setStatusLabel(statusLabel);
        chessboard.setCountDown(this.countDown);
        gameController = new GameController(chessboard);
        chessboard.setLocation(F_HEIGHT / 10, F_HEIGHT / 4+20);
        chessboard.initializeGame();
        layeredPane.add(chessboard,JLayeredPane.MODAL_LAYER);
        this.chessboard = chessboard;
    }

    /**
     * 在游戏面板中添加标签
     */
    private void addMovingChessLabel() {
        statusLabel = new JLabel();
        statusLabel.setLocation(F_HEIGHT, F_HEIGHT / 10);
        statusLabel.setSize(200, 60);
        statusLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        layeredPane.add(statusLabel,JLayeredPane.MODAL_LAYER);
    }

    /**
     * 在游戏面板中增加一个按钮，如果按下的话就会显示Hello, world!
     */

    private void addResetButton() {
        JButton button = new JButton("RESET");
        button.addActionListener((e) -> gameController.initialGame());
        button.setLocation(F_HEIGHT, F_HEIGHT / 10 + 120);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        layeredPane.add(button,JLayeredPane.MODAL_LAYER);
    }


    private void addLoadBrowseButton() {
        JButton button = new JButton("LOAD");
        button.setLocation(F_HEIGHT, F_HEIGHT / 10 + 240);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        layeredPane.add(button,JLayeredPane.MODAL_LAYER);

        button.addActionListener(e -> {

            JFileChooser loadChoose = new JFileChooser();
            loadChoose.setFileSelectionMode(JFileChooser.OPEN_DIALOG);
            loadChoose.showOpenDialog(null);
            File f = loadChoose.getSelectedFile();
            gameController.loadGameFromFile(f);
            chessboard.releaseCache();
            chessboard.setStepsCounter(0);
            chessboard.saveChessCache();
        });
    }

    private void addChangeBackgroundButton() {
        JButton button = new JButton("BACKGROUND");
        button.setLocation(F_HEIGHT/10,F_HEIGHT/10);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        layeredPane.add(button,JLayeredPane.MODAL_LAYER);

        button.addActionListener(e -> {
            JFileChooser backgroundChoose = new JFileChooser();
            backgroundChoose.setFileSelectionMode(JFileChooser.FILES_ONLY);
            backgroundChoose.setFileFilter(new FileFilter() {
                @Override
                public String getDescription() {
                    return "图片文件(*.jpg, *.png)";
                }
                @Override
                public boolean accept(File f) {
                    if(f.isDirectory()){
                        return true;
                    }
                    if(f.getName().toLowerCase().endsWith(".jpg")){
                        return true;
                    }
                    else return f.getName().toLowerCase().endsWith(".png");
                }
            });
            backgroundChoose.showOpenDialog(null);
            File f = backgroundChoose.getSelectedFile();
            try{
                backgroundImagePanel.setBackgroundImage(ImageIO.read(f));
            }
            catch (Exception exception){
                System.out.println("No file chose");
            }
            backgroundImagePanel.repaint();
        });
    }
    
    private void addSaveButton() {
        JButton button = new JButton("SAVE");
        button.setLocation(F_HEIGHT, F_HEIGHT / 10 + 340);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        layeredPane.add(button,JLayeredPane.MODAL_LAYER);
        button.addActionListener(listener -> {
            JFileChooser saveChoose = new JFileChooser();
            saveChoose.setFileSelectionMode(JFileChooser.OPEN_DIALOG);
            saveChoose.showSaveDialog(null);
            String path = saveChoose.getSelectedFile().getPath();
            gameController.saveGame(path);
        });
    }

    private void addUndoButton() {
        JButton button = new JButton("UNDO");
        button.setLocation(F_HEIGHT, F_HEIGHT / 10 + 440);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        layeredPane.add(button,JLayeredPane.MODAL_LAYER);
        button.addActionListener(listener -> {
            System.out.println(chessboard.getStepsCounter());
            gameController.undo(chessboard.getStepsCounter());
        });
    }

    private void addReplayButton() {
        JButton button = new JButton("REPLAY");
        button.setLocation(F_HEIGHT, F_HEIGHT / 10 + 540);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        layeredPane.add(button,JLayeredPane.MODAL_LAYER);
        button.addActionListener(listener -> {
            gameController.replay();
        });
    }
    private void addCountDown(){
        countDown = new JLabel();
        countDown.setLocation(F_HEIGHT, F_HEIGHT / 10-50);
        countDown.setSize(300, 60);
        countDown.setFont(new Font("Rockwell", Font.BOLD, 20));
        layeredPane.add(countDown,JLayeredPane.MODAL_LAYER);
    }
}