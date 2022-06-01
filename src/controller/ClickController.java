package controller;


import model.ChessColor;
import model.ChessComponent;
import view.Chessboard;

import javax.swing.*;

public class ClickController extends JComponent {
    private final Chessboard chessboard;
    private ChessComponent first;

    public ClickController(Chessboard chessboard) {
        this.chessboard = chessboard;
    }

    public void onClick(ChessComponent chessComponent) {
        if (first == null) {
            if (handleFirst(chessComponent)) {
                chessComponent.setSelected(true);
                first = chessComponent;
                chessboard.aimSetterTrue(chessboard.convertPointToComponent(first.canMoveTo(chessboard.getChessComponents())));
                first.repaint();
            }
        } else {
            if (first == chessComponent) { // 再次点击取消选取
                chessComponent.setSelected(false);
                chessboard.aimSetterFalse(chessboard.convertPointToComponent(first.canMoveTo(chessboard.getChessComponents())));
                ChessComponent recordFirst = first;
                first = null;
                recordFirst.repaint();
            } else if (handleSecond(chessComponent)) {
                //repaint in swap chess method.
                chessboard.aimSetterFalse(chessboard.convertPointToComponent(first.canMoveTo(chessboard.getChessComponents())));
                chessboard.swapChessComponents(first, chessComponent);
                if (chessComponent.getChessType(ChessColor.WHITE) != 'k' || chessComponent.getChessType(ChessColor.BLACK) != 'K') {
                    chessboard.swapColor();
                }
                first.setSelected(false);
                chessboard.checkMate(first,first.canMoveTo(chessboard.getChessComponents()), first.getChessColor());
                first = null;
            }
        }
    }

    /**
     * @param chessComponent 目标选取的棋子
     * @return 目标选取的棋子是否与棋盘记录的当前行棋方颜色相同
     */

    private boolean handleFirst(ChessComponent chessComponent) {
        return chessComponent.getChessColor() == chessboard.getCurrentColor();
    }

    /**
     * @param chessComponent first棋子目标移动到的棋子second
     * @return first棋子是否能够移动到second棋子位置
     */

    private boolean handleSecond(ChessComponent chessComponent) {
        return chessComponent.getChessColor() != chessboard.getCurrentColor() &&
                first.canMoveTo(chessboard.getChessComponents(), chessComponent.getChessboardPoint());
    }
}
