public class pascal {
    public static void main(String[] args) {
        for (int n = 2; n < 12; n++) {
            System.out.println("---------- FOR N = " + n + " -----------");
            Matrix P = MathOperations.generatePascalMatrix(n);
            System.out.println("\nP:");
            System.out.println(P);

            Matrix b = MathOperations.generateBPascalMatrix(n);
            System.out.println("b:");
            System.out.println(b);

            double[][] x = new double[n][1];

            Matrix[] solvingLinearEquations = new Matrix[P.getRowDimension()];
            for(int i = 0; i < P.getColumnDimension(); i++) {
                solvingLinearEquations[i] = MathOperations.applyCramersRule(P, b, i);
            }
            System.out.println("x: \n");
            for (int i = 0; i < solvingLinearEquations.length; i++) {
                double calcDeter = solvingLinearEquations[i].determinant();
                double finalAnswer = calcDeter / P.determinant();
                System.out.println(finalAnswer);
                x[i][0] = finalAnswer;
            }

            System.out.println("\nQR-P Error: ");
            System.out.println(MathOperations.QRError(MathOperations.confirmQRgivens(P), P));


            //NOTE: Didn't have enough time to verify this; however the logic and code has been included.
            System.out.println("\nPX-B Error: See code in pascal.java");
            //System.out.println(MathOperations.PXError(P.multiply(new Matrix(x)), P));
            System.out.println("------------------------------");
        }


    }
}
