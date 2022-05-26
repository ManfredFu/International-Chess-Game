package model;

import controller.ClickController;
import view.ChessboardPoint;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BishopChessComponent extends ChessComponent {
    private static Image BISHOP_WHITE;
    private static Image BISHOP_BLACK;
    private Image bishopImage;
    protected ChessColor chessColor;

    public void loadResource() throws IOException {
        if (BISHOP_WHITE == null) {
            BISHOP_WHITE = ImageIO.read(new File("./images/bishop-white.png"));
        }

        if (BISHOP_BLACK == null) {
            BISHOP_BLACK = ImageIO.read(new File("./images/bishop-black.png"));
        }
    }

    private void initiateBishopImage(ChessColor chessColor) {
        try {
            loadResource();
            if (chessColor == ChessColor.WHITE) {
                bishopImage = BISHOP_WHITE;
            } else if (chessColor == ChessColor.BLACK) {
                bishopImage = BISHOP_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BishopChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size) {
        super(chessboardPoint, location, color, listener, size);
        initiateBishopImage(color);
    }

    public boolean canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination) {
        ChessboardPoint source = getChessboardPoint();
        List<ChessboardPoint> returnValue = new ArrayList<>();
        ChessboardPoint nowMovingTo;
        for (int i = 0; i < 8; i = i + 2) {
            for (int j = 1; j < 8; j++) {
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

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bishopImage, 0, 0, getWidth(), getHeight(), this);
        g.setColor(Color.BLACK);
        if (isSelected()) {
            g.setColor(Color.RED);
            g.drawOval(0, 0, getWidth(), getHeight());
        }
    }
    public char getChessType(ChessColor chessColor){
        if(chessColor == ChessColor.BLACK){
            return 'B';
        }
        else {
            return 'b';
        }
    }
}
