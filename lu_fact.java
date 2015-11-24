import java.util.InputMismatchException;
public class lu_fact {
    public static void main(String[] args) {
        MatrixScanner input = new MatrixScanner();
        System.out.println("Please enter a matrix to find the LU decomposition: \n ");
        try {
            Matrix matrix = input.readMatrix();
            System.out.println();

            //MathOperations.LU(matrix);

//            System.out.println(results);
//
//            System.out.println("LU: \n");
//            System.out.println(results[2]);
//
//            System.out.println("L: \n");
//            System.out.println(results[0]);
//
//            System.out.println("U: \n");
//            System.out.println(results[1]);

//            System.out.print("Error: ");
//            System.out.println(MathOperations.QRError(LU, matrix));

        } catch (InputMismatchException e) {
            System.out.println(e.getMessage());
        } catch (IndexOutOfBoundsException me) {
            System.out.println(me.getMessage());
        }
    }
}
