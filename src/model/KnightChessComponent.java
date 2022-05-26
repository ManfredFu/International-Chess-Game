package model;

import controller.ClickController;
import view.ChessboardPoint;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class KnightChessComponent extends ChessComponent {
    private static Image KNIGHT_WHITE;
    private static Image KNIGHT_BLACK;
    private Image knightImage;

    public void loadResource() throws IOException {
        if (KNIGHT_WHITE == null) {
            KNIGHT_WHITE = ImageIO.read(new File("./images/knight-white.png"));
        }

        if (KNIGHT_BLACK == null) {
            KNIGHT_BLACK = ImageIO.read(new File("./images/knight-black.png"));
        }
    }

    private void initiateBishopImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                knightImage = KNIGHT_WHITE;
            } else if (color == ChessColor.BLACK) {
                knightImage = KNIGHT_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public KnightChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size) {
        super(chessboardPoint, location, color, listener, size);
        initiateBishopImage(color);
    }

    public boolean canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination) {
        ChessboardPoint source = getChessboardPoint();
        int xAxis = source.getX();
        int yAxis = source.getY();
        List<ChessboardPoint> returnValue = new ArrayList<>();
        returnValue.add(new ChessboardPoint(xAxis + 1, yAxis + 2));
        returnValue.add(new ChessboardPoint(xAxis + 2, yAxis + 1));
        returnValue.add(new ChessboardPoint(xAxis + 2, yAxis - 1));
        returnValue.add(new ChessboardPoint(xAxis + 1, yAxis - 2));
        returnValue.add(new ChessboardPoint(xAxis - 1, yAxis - 2));
        returnValue.add(new ChessboardPoint(xAxis - 2, yAxis - 1));
        returnValue.add(new ChessboardPoint(xAxis - 2, yAxis + 1));
        returnValue.add(new ChessboardPoint(xAxis - 1, yAxis + 2));
        for (int i = 0; i < returnValue.size(); i++) {
            if (returnValue.get(i).offset() == null) {
                returnValue.remove(i);
                i--;
                continue;
            }
            if (chessComponents[returnValue.get(i).getX()][returnValue.get(i).getY()].getChessColor() == chessColor) {
                returnValue.remove(i);
                i--;
            }
        }
        if (returnValue.contains(destination)) {
            return true;
        }
        return false;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(knightImage, 0, 0, getWidth(), getHeight(), this);
        g.setColor(Color.BLACK);
        if (isSelected()) {
            g.setColor(Color.RED);
            g.drawOval(0, 0, getWidth(), getHeight());
        }
    }
    public char getChessType(ChessColor chessColor){
        if(chessColor == ChessColor.BLACK){
            return 'N';
        }
        else {
            return 'n';
        }
    }
}
