/**
 * Created by Maxwell on 11/22/2015.
 * Perform Gauss-Seidel Iterations on a Matrix
 *
 */
public class GaussSeidel extends Iteration {

    public final double[][] aContents;
    public final double[] bVector;
    public double numOfIterations;

    public final Matrix a;

    public GaussSeidel() {
        this.aContents = new double[][] {{1, 0.5, 0.3333333}, {0.5, 1, 0.25}, {0.33333, 0.25, 1}};
        this.bVector = new double[] {0.1, 0.1, 0.1};
        this.a = new Matrix(aContents);
        this.numOfIterations = 0;
    }

    /**
     * Perform the gs iteration on a matrix
     * @param x the initial vector
     * @param tolerance the tolerance to compare to
     * @param M the number of iterations to peform
     * @return the answer vector
     */
    public double[] gs_iter(double[] x, double tolerance, int M) {
        Matrix s = getInverse(a);
        double[][] tContent = {{0, -0.5, -0.33333333}, {0, 0, -0.25}, {0, 0, 0}};
        Matrix t = new Matrix(tContent);

        int counter = 0; //keep track of the number of iterations
        double tol = 10000; //initial
        double[] tempXVector;
        Matrix firstFirst;
        double[] firstPart; //s^-1(-T)(Xn)
        double[] secondPart; //s^-1(b)
        String output = "";

        double tempXVectorNorm;
        double xVectorNorm;
        while (tol > tolerance && counter < M) {
            //perform the gs iterations
            firstFirst = s.multiply(t);
            firstPart = Matrix.matrixVectorMultiply(firstFirst, x);
            secondPart = Matrix.matrixVectorMultiply(s, bVector);
            tempXVector = Matrix.plus(firstPart, secondPart);

            //find norm of vectors and then subtract the
            tempXVectorNorm = Matrix.norm(tempXVector);
            xVectorNorm = Matrix.norm(x);
            tol = tempXVectorNorm - xVectorNorm;
            tol = Math.abs(tol);
            counter++;
            numOfIterations++;
            x = tempXVector;
        }

        output = "";
        if (counter == M) {
            System.out.println("Gauss-Seidel Output for x(N): null");
            System.out.println("Too many iterations without reaching a accurate answer.");
            return null;
        }
        for (double elem : x) {
            output += elem + ", ";
        }
        System.out.println("Gauss-Seidel for x(N): " + output);
        System.out.println("Number of Iterations using Gauss-Seidel Algorithm: " + counter);
        return x;
    }
    public double numOfIterations() {
        return numOfIterations;
    }

    public static Matrix getInverse(Matrix s) {
        double a = s.get(0,0);
        double b = s.get(1, 0);
        double c = s.get(1, 1);
        double d = s.get(2, 0);
        double e = s.get(2, 1);
        double f = s.get(1, 1);

        s.set(0, 0, (double)1 / a); //set a
        s.set(1, 1, (double)1 / c); //set c
        s.set(2, 2, (double)1 / f); //set f
        s.set(1, 0, -b / (a * c)); //set b
        s.set(2, 0, ((-c * d) + (b * e)) / (a * c * f)); //set d
        s.set(2, 1, -e/(c * f)); //set e

        return s;
    }
}