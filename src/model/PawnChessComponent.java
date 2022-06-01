package model;

import controller.ClickController;
import view.ChessboardPoint;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PawnChessComponent extends ChessComponent {
    private static Image PAWN_WHITE;
    private static Image PAWN_BLACK;
    private Image pawnImage;

    public void loadResource() throws IOException {
        if (PAWN_WHITE == null) {
            PAWN_WHITE = ImageIO.read(new File("./images/pawn-white.png"));
        }

        if (PAWN_BLACK == null) {
            PAWN_BLACK = ImageIO.read(new File("./images/pawn-black.png"));
        }
    }

    private void initiatePawnImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                pawnImage = PAWN_WHITE;
            } else if (color == ChessColor.BLACK) {
                pawnImage = PAWN_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public PawnChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size) {
        super(chessboardPoint, location, color, listener, size);
        initiatePawnImage(color);
    }

    public boolean canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination) {
        ChessboardPoint source = getChessboardPoint();
        int xAxis = source.getX();
        int yAxis = source.getY();
        List<ChessboardPoint> returnValue = new ArrayList<>();
        if (chessColor == ChessColor.WHITE) {
            if (source.getX() == 6) {
                if (chessComponents[source.getX() - 1][source.getY()].getChessColor() == ChessColor.NONE) {
                    returnValue.add(new ChessboardPoint(xAxis - 1, yAxis));
                    if (chessComponents[source.getX() - 2][source.getY()].getChessColor() == ChessColor.NONE) {
                        returnValue.add(new ChessboardPoint(xAxis - 2, yAxis));
                    }
                }
            }
            if (source.getX() != 6) {
                if (source.offset(-1, 0) != null) {
                    if (chessComponents[source.getX() - 1][source.getY()].getChessColor() == ChessColor.NONE) {
                        returnValue.add(new ChessboardPoint(xAxis - 1, yAxis));
                    }
                }
            }
            if (source.offset(-1, -1) != null) {
                if (chessComponents[source.getX() - 1][source.getY() - 1].getChessColor() != chessColor && chessComponents[source.getX() - 1][source.getY() - 1].getChessColor() != ChessColor.NONE) {
                    returnValue.add(new ChessboardPoint(xAxis - 1, yAxis - 1));
                }
            }
            if (source.offset(-1, +1) != null) {
                if (chessComponents[source.getX() - 1][source.getY() + 1].getChessColor() != chessColor && chessComponents[source.getX() - 1][source.getY() + 1].getChessColor() != ChessColor.NONE) {
                    returnValue.add(new ChessboardPoint(xAxis - 1, yAxis + 1));
                }
            }
        }
        if (chessColor == ChessColor.BLACK) {
            if (source.getX() == 1) {
                if (chessComponents[source.getX() + 1][source.getY()].getChessColor() == ChessColor.NONE) {
                    returnValue.add(new ChessboardPoint(xAxis + 1, yAxis));
                    if (chessComponents[source.getX() + 2][source.getY()].getChessColor() == ChessColor.NONE) {
                        returnValue.add(new ChessboardPoint(xAxis + 2, yAxis));
                    }
                }
            }
            if (source.getX() != 1) {
                if (source.offset(+1, 0) != null) {
                    if (chessComponents[source.getX() + 1][source.getY()].getChessColor() == ChessColor.NONE) {
                        returnValue.add(new ChessboardPoint(xAxis + 1, yAxis));
                    }
                }
            }
            if (source.offset(+1, +1) != null) {
                if (chessComponents[source.getX() + 1][source.getY() + 1].getChessColor() != chessColor && chessComponents[source.getX() + 1][source.getY() + 1].getChessColor() != ChessColor.NONE) {
                    returnValue.add(new ChessboardPoint(xAxis + 1, yAxis + 1));
                }
            }
            if (source.offset(+1, -1) != null) {
                if (chessComponents[source.getX() + 1][source.getY() - 1].getChessColor() != chessColor && chessComponents[source.getX() + 1][source.getY() - 1].getChessColor() != ChessColor.NONE) {
                    returnValue.add(new ChessboardPoint(xAxis + 1, yAxis - 1));
                }
            }
        }
        if (returnValue.contains(destination)) {
            return true;
        }
        return false;
    }
    public List<ChessboardPoint> canMoveTo(ChessComponent[][] chessComponents) {
        ChessboardPoint source = getChessboardPoint();
        int xAxis = source.getX();
        int yAxis = source.getY();
        List<ChessboardPoint> returnValue = new ArrayList<>();
        if (chessColor == ChessColor.WHITE) {
            if (source.getX() == 6) {
                if (chessComponents[source.getX() - 1][source.getY()].getChessColor() == ChessColor.NONE) {
                    returnValue.add(new ChessboardPoint(xAxis - 1, yAxis));
                    if (chessComponents[source.getX() - 2][source.getY()].getChessColor() == ChessColor.NONE) {
                        returnValue.add(new ChessboardPoint(xAxis - 2, yAxis));
                    }
                }
            }
            if (source.getX() != 6) {
                if (source.offset(-1, 0) != null) {
                    if (chessComponents[source.getX() - 1][source.getY()].getChessColor() == ChessColor.NONE) {
                        returnValue.add(new ChessboardPoint(xAxis - 1, yAxis));
                    }
                }
            }
            if (source.offset(-1, -1) != null) {
                if (chessComponents[source.getX() - 1][source.getY() - 1].getChessColor() != chessColor && chessComponents[source.getX() - 1][source.getY() - 1].getChessColor() != ChessColor.NONE) {
                    returnValue.add(new ChessboardPoint(xAxis - 1, yAxis - 1));
                }
            }
            if (source.offset(-1, +1) != null) {
                if (chessComponents[source.getX() - 1][source.getY() + 1].getChessColor() != chessColor && chessComponents[source.getX() - 1][source.getY() + 1].getChessColor() != ChessColor.NONE) {
                    returnValue.add(new ChessboardPoint(xAxis - 1, yAxis + 1));
                }
            }
        }
        if (chessColor == ChessColor.BLACK) {
            if (source.getX() == 1) {
                if (chessComponents[source.getX() + 1][source.getY()].getChessColor() == ChessColor.NONE) {
                    returnValue.add(new ChessboardPoint(xAxis + 1, yAxis));
                    if (chessComponents[source.getX() + 2][source.getY()].getChessColor() == ChessColor.NONE) {
                        returnValue.add(new ChessboardPoint(xAxis + 2, yAxis));
                    }
                }
            }
            if (source.getX() != 1) {
                if (source.offset(+1, 0) != null) {
                    if (chessComponents[source.getX() + 1][source.getY()].getChessColor() == ChessColor.NONE) {
                        returnValue.add(new ChessboardPoint(xAxis + 1, yAxis));
                    }
                }
            }
            if (source.offset(+1, +1) != null) {
                if (chessComponents[source.getX() + 1][source.getY() + 1].getChessColor() != chessColor && chessComponents[source.getX() + 1][source.getY() + 1].getChessColor() != ChessColor.NONE) {
                    returnValue.add(new ChessboardPoint(xAxis + 1, yAxis + 1));
                }
            }
            if (source.offset(+1, -1) != null) {
                if (chessComponents[source.getX() + 1][source.getY() - 1].getChessColor() != chessColor && chessComponents[source.getX() + 1][source.getY() - 1].getChessColor() != ChessColor.NONE) {
                    returnValue.add(new ChessboardPoint(xAxis + 1, yAxis - 1));
                }
            }
        }
        return returnValue;
    }


    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(pawnImage, 0, 0, getWidth(), getHeight(), this);
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
            return 'P';
        }
        else {
            return 'p';
        }
    }
}

