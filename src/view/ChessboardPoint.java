package view;

import java.util.Objects;

/**
 * 这个类表示棋盘上的位置，如(0, 0), (0, 7)等等
 * 其中，左上角是(0, 0)，左下角是(7, 0)，右上角是(0, 7)，右下角是(7, 7)
 */
public class ChessboardPoint {
    private int x, y;

    public ChessboardPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChessboardPoint)) return false;
        ChessboardPoint that = (ChessboardPoint) o;
        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ") " + "on the chessboard is clicked!";
    }

    public ChessboardPoint offset(int dx, int dy) {
        if ((this.x + dx) > 7 || (this.y + dy) > 7 || (this.x + dx) < 0 || (this.y + dy) < 0) {
            return null;
        } else {
            return new ChessboardPoint(this.x + dx, this.y + dy);
        }
    }

    public ChessboardPoint offset() {
        if ((this.x > 7 || this.y > 7 || this.x < 0 || this.y < 0)) {
            return null;
        } else {
            return new ChessboardPoint(this.x, this.y);
        }
    }
}
