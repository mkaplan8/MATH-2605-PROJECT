/**
 * Created by Maxwell on 11/18/2015.
 * Abstract class that will check squareness of a matrix and if the matrix is invertible
 */
public abstract class Iteration {
    /**
     * check if matrix is square
     * @param a
     */
    public void checkSquare(Matrix a) {
        int m = a.getRowDimension();
        int n = a.getColumnDimension();
        if (m != n) {
            throw new IllegalArgumentException("Matrix is not square");
        }
    }

    /**
     * Check to see if matrix is invertible
     * @param a
     */
    public void checkInvert(Matrix a) {
        double det = a.determinant();
        if (det == 0) {
            throw new IllegalArgumentException("Matrix is not invertible");
        }
    }
}
