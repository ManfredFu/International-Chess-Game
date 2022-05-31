package AI;
import view.*;
import model.*;
import controller.*;

import java.util.ArrayList;
import java.util.List;


public class easyAI {
    private Chessboard chessboard;
    private ChessColor color = ChessColor.WHITE;
    public easyAI(Chessboard chessboard,ChessColor chessColor){
        this.chessboard = chessboard;
        this.color = chessColor;
    }
    public ChessComponent getRandomChess(Chessboard chessboard,ChessColor chessColor){
        ChessComponent[][] nowBoard = chessboard.getChessComponents();
        List<ChessComponent> myChessPosition = new ArrayList<>();
        for(int i =0;i<8;i++){
            for(int j = 0;j<8;j++){
                if(nowBoard[i][j].getChessColor() == chessColor){
                    myChessPosition.add(nowBoard[i][j]);
                }
            }
        }
        return null;
    }

    public void setChessboard(Chessboard chessboard) {
        this.chessboard = chessboard;
    }
}
