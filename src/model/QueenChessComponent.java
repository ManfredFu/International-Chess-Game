package model;

import controller.ClickController;
import view.ChessboardPoint;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class QueenChessComponent extends ChessComponent {
    private static Image QUEEN_WHITE;
    private static Image QUEEN_BLACK;
    private Image queenImage;

    public void loadResource() throws IOException {
        if (QUEEN_WHITE == null) {
            QUEEN_WHITE = ImageIO.read(new File("./images/queen-white.png"));
        }

        if (QUEEN_BLACK == null) {
            QUEEN_BLACK = ImageIO.read(new File("./images/queen-black.png"));
        }
    }

    private void initiateQueenImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                queenImage = QUEEN_WHITE;
            } else if (color == ChessColor.BLACK) {
                queenImage = QUEEN_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public QueenChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size) {
        super(chessboardPoint, location, color, listener, size);
        initiateQueenImage(color);
    }

    public boolean canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination) {
        ChessboardPoint source = getChessboardPoint();
        List<ChessboardPoint> returnValue = new ArrayList<>();
        ChessboardPoint nowMovingTo;
        for (int i = 0; i < 8; i++) {
            for (int j = 1; j < 9; j++) {
                nowMovingTo = source.offset(generalMoveDirection[i][0] * j, generalMoveDirection[i][1] * j);
                if (nowMovingTo == null) {
                    break;
                }
                if (chessComponents[source.getX() + generalMoveDirection[i][0] * j][source.getY() + generalMoveDirection[i][1] * j].chessColor == ChessColor.NONE) {
                    returnValue.add(nowMovingTo);
                } else if (chessComponents[source.getX() + generalMoveDirection[i][0] * j][source.getY() + generalMoveDirection[i][1] * j].chessColor != ChessColor.NONE) {
                    if (chessComponents[source.getX() + generalMoveDirection[i][0] * j][source.getY() + generalMoveDirection[i][1] * j].chessColor != this.chessColor) {
                        returnValue.add(nowMovingTo);
                    }
                    break;
                }
            }
        }
        if (returnValue.contains(destination)) {
            return true;
        }
        return false;
    }
    public List<ChessboardPoint> canMoveTo(ChessComponent[][] chessComponents) {
        System.out.println(this.chessColor);
        ChessboardPoint source = getChessboardPoint();
        List<ChessboardPoint> returnValue = new ArrayList<>();
        ChessboardPoint nowMovingTo;
        for (int i = 0; i < 8; i++) {
            for (int j = 1; j < 9; j++) {
                nowMovingTo = source.offset(generalMoveDirection[i][0] * j, generalMoveDirection[i][1] * j);
                if (nowMovingTo == null) {
                    break;
                }
                if (chessComponents[source.getX() + generalMoveDirection[i][0] * j][source.getY() + generalMoveDirection[i][1] * j].chessColor == ChessColor.NONE) {
                    returnValue.add(nowMovingTo);
                } else if (chessComponents[source.getX() + generalMoveDirection[i][0] * j][source.getY() + generalMoveDirection[i][1] * j].chessColor != ChessColor.NONE) {
                    if (chessComponents[source.getX() + generalMoveDirection[i][0] * j][source.getY() + generalMoveDirection[i][1] * j].chessColor != this.chessColor) {
                        returnValue.add(nowMovingTo);
                    }
                    break;
                }
            }
        }
        return returnValue;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(queenImage, 0, 0, getWidth(), getHeight(), this);
        g.setColor(Color.BLACK);
        if (isSelected()) {
            g.setColor(Color.RED);
            g.drawOval(0, 0, getWidth(), getHeight());
        }
        if(isCanBeMovedTo()){
            g.setColor(Color.CYAN);
            g.drawOval(0,0,getWidth(),getHeight());
        }
    }
    public char getChessType(ChessColor chessColor){
        if(chessColor == ChessColor.BLACK){
            return 'Q';
        }
        else {
            return 'q';
        }
    }
}
