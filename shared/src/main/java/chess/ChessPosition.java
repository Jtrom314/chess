package chess;

/**
 * Represents a single square position on a chess board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPosition {

    private int row;
    private int col;
    public ChessPosition(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     * @return which row this position is in
     * 1 codes for the bottom row
     */
    public int getRow() {
        return this.row;
    }

    /**
     * @return which column this position is in
     * 1 codes for the left row
     */
    public int getColumn() {
        return this.col;
    }
    /**
     * 8    r   n   b   q   k   b   n   r
     * 7    p   p   p   p   p   p   p   p
     * 6    {black}
     * 5
     * 4
     * 3    {white}
     * 2    P   P   P   P   P   P   P   P
     * 1    R   N   B   Q   K   B   N   R
     *      1   2   3   4   5   6   7   8
     */
}
