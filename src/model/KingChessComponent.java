package model;

import controller.ClickController;
import view.ChessboardPoint;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class KingChessComponent extends ChessComponent {
    private static Image KING_WHITE;
    private static Image KING_BLACK;
    private Image kingImage;

    public void loadResource() throws IOException {
        if (KING_WHITE == null) {
            KING_WHITE = ImageIO.read(new File("./images/king-white.png"));
        }

        if (KING_BLACK == null) {
            KING_BLACK = ImageIO.read(new File("./images/king-black.png"));
        }
    }

    private void initiateBishopImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                kingImage = KING_WHITE;
            } else if (color == ChessColor.BLACK) {
                kingImage = KING_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public KingChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size) {
        super(chessboardPoint, location, color, listener, size);
        initiateBishopImage(color);
    }

    public boolean canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination) {
        ChessboardPoint source = getChessboardPoint();
        List<ChessboardPoint> returnValue = new ArrayList<>();
        ChessboardPoint nowMovingTo;
        for (int i = 0; i < 8; i++) {
            nowMovingTo = source.offset(generalMoveDirection[i][0], generalMoveDirection[i][1]);
            if (nowMovingTo == null) {
                continue;
            }
            if (chessComponents[nowMovingTo.getX()][nowMovingTo.getY()].getChessColor() != chessColor) {
                returnValue.add(nowMovingTo);
            }
        }
        if (returnValue.contains(destination)) {
            return true;
        }
        return false;
    }

    public List<ChessboardPoint> canMoveTo(ChessComponent[][] chessComponents) {
        ChessboardPoint source = getChessboardPoint();
        List<ChessboardPoint> returnValue = new ArrayList<>();
        ChessboardPoint nowMovingTo;
        for (int i = 0; i < 8; i++) {
            nowMovingTo = source.offset(generalMoveDirection[i][0], generalMoveDirection[i][1]);
            if (nowMovingTo == null) {
                continue;
            }
            if (chessComponents[nowMovingTo.getX()][nowMovingTo.getY()].getChessColor() != chessColor) {
                returnValue.add(nowMovingTo);
            }
        }
        return returnValue;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(kingImage, 0, 0, getWidth(), getHeight(), this);
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

    public char getChessType(ChessColor chessColor) {
        if (chessColor == ChessColor.BLACK) {
            return 'K';
        } else {
            return 'k';
        }
    }
}
