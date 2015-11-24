/**
 * Created by Maxwell on 11/19/2015.
 * Perform Jacobi Iterations of a Matrix
 *
 */
public class Jacobi extends Iteration{
    public final double[][] aContents;
    public final double[] bVector;
    public double numOfIterations;

    public final Matrix a;

    public Jacobi() {
        this.aContents = new double[][] {{1, 0.5, 0.3333333}, {0.5, 1, 0.25}, {0.33333, 0.25, 1}};
        this.bVector = new double[] {0.1, 0.1, 0.1};
        this.a = new Matrix(aContents);
        this.numOfIterations = 0;
    }

    /**
     * Perform Jacobi iterations on a matrix
     * @param x vector containing the initial guess
     * @param tolerance tolerance to which answers will be compared
     * @param M number of iterations being performed
     */
    public double[] jacobi_iter(double[] x, double tolerance, int M) {
        double[][] sContent = {{1.0, 0, 0}, {0, 1.0, 0}, {0, 0, 1.0}};
        Matrix s = new Matrix(sContent);

        double[][] tContent = {{0, -0.5, -0.33333333}, {-0.5, 0, -0.25}, {-0.3333333333, -0.25, 0}};
        Matrix t = new Matrix(tContent);

        //keep track of the number of iterations

        int counter = 0;
        double tol = 10000; //initial
        double[] tempXVector;
        Matrix firstFirst;
        double[] firstPart; //s^-1(-T)(Xn)
        double[] secondPart; //s^-1(b)
        String output = "";

        double tempXVectorNorm;
        double xVectorNorm;

        for (double elem : x) {
            output += elem + ", ";
        }
        System.out.println("x(0): " + output);
        while (tol > tolerance && counter < M) {
            //perform the jacobi iterations
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
            System.out.println("Jacobi Output for x(N): null");
            System.out.println("Too many iterations without reaching a accurate answer.");
            return null;
        }
        for (double elem : x) {
            output += elem + ", ";
        }
        System.out.println("Jacobi Output for x(N): " + output);
        System.out.println("Number of Iterations using Jacobi Algorithm: " + counter + "\n");
        return x;
    }
    public double numOfIterations() {
        return numOfIterations;
    }
}
