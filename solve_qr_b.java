import java.util.InputMismatchException;
public class solve_qr_b {
    public static void main(String[] args) {

        Matrix b = null;
        Matrix A = null;

        MatrixScanner input = new MatrixScanner();
        System.out.println("Please enter a matrix A: \n");
        try {
            Matrix matrix = input.readMatrix();
            System.out.println();
            A = matrix;
        } catch (InputMismatchException e) {
            System.out.println(e.getMessage());
        } catch (IndexOutOfBoundsException me) {
            System.out.println(me.getMessage());
        }

        //Now let's solve the equation.
        System.out.println("Please enter a nx1 matrix (b) for us to solve\n "
                + " the factorization (where Ax = b)"
                + " \n");
        try {
            Matrix matrix = input.readMatrix();
            System.out.println();
            b = matrix;
            Matrix[] solvingLinearEquations = new Matrix[matrix.getRowDimension()];
            for(int i = 0; i < A.getColumnDimension(); i++) {
                solvingLinearEquations[i] = MathOperations.applyCramersRule(A, b, i);
            }

            System.out.println("x: \n");
            for (int i = 0; i < solvingLinearEquations.length; i++) {
                double calcDeter = solvingLinearEquations[i].determinant();
                double finalAnswer = calcDeter / A.determinant();
                System.out.println(finalAnswer);
            }
        } catch (InputMismatchException e) {
            System.out.println(e.getMessage());
        } catch (IndexOutOfBoundsException me) {
            System.out.println(me.getMessage());
        }

    }
}


