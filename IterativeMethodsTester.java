/**
 * Created by Maxwell on 11/22/2015.
 * Tester class to run both jacobi and gauss-seidel iterative methods
 */
import java.util.Random;
public class IterativeMethodsTester {
    public static void main(String[] args) {
        Random rand = new Random();
        double[] xExact = new double[] {((double)9/(double)190), ((double)28/(double)475), ((double)33/(double)475)};
        double[] tempVectJacobi;
        double[] tempVectGS;
        double[] avgVectJacobi = new double[] {0, 0, 0};
        double[] avgVectGS = new double[] {0, 0, 0};
        double exactNorm = 0;
        double avgIterationNumJacobi = 0;
        double avgIterationNumGS = 0;
        String output;
        double tol = 0.00005;
        int m = 100;
        int counter = 1;
        Jacobi j = new Jacobi();
        GaussSeidel g = new GaussSeidel();

        double[] x = new double[] {0, 0, 0};
        while (counter < 101) {
            for (int i = 0; i < 3; i++) {
                double randomNum = -1 + (1 - (-1)) * rand.nextDouble();
                x[i] = randomNum;
            }
            System.out.println("Vector " + counter);
            tempVectJacobi = j.jacobi_iter(x, tol, m);
            tempVectGS = g.gs_iter(x, tol, m);
            double xNorm = Matrix.norm(x);
            exactNorm = Matrix.norm(xExact);
            double differenceOrigal = Math.abs(xNorm - exactNorm);
            System.out.println("The initial error for the vector: " + differenceOrigal + "\n");
            avgIterationNumJacobi = j.numOfIterations();
            avgIterationNumGS = g.numOfIterations();
            //add the vector into the average vector
            for (int i = 0; i < tempVectJacobi.length; i++) {
                avgVectJacobi[i] += tempVectJacobi[i];
                avgVectGS[i] += tempVectGS[i];
            }
            counter++;
        }
        output = "";
        //find the avg vector
        for (int i = 0; i < avgVectJacobi.length; i++) {
            avgVectJacobi[i] = avgVectJacobi[i] / (double)100;
        }
        for (double elem : avgVectJacobi) {
            output += elem + ", ";
        }
        avgIterationNumJacobi = avgIterationNumJacobi / 100;
        System.out.println("Average number of Jacobi Iterations performed: " + avgIterationNumJacobi);
        System.out.println("Average vector result after 100 Jacobi Iterations: " + output +"\n");

        for (int i = 0; i < avgVectGS.length; i++) {
            avgVectGS[i] = avgVectGS[i] / (double)100;
        }
        output = "";
        for (double elem : avgVectGS) {
            output += elem + ", ";
        }
        avgIterationNumGS = avgIterationNumGS / 100;
        System.out.println("Average number of Gauss-Seidel Iterations performed: " + avgIterationNumGS);
        System.out.println("Average vector result after 100 Gauss=Seidel Iterations: " + output +"\n");

        output = "";
        for (double elem : xExact) {
            output += elem + ", ";
        }
        System.out.println("Exact vector solution: " + output);

        double avgNormJacobi = Matrix.norm(avgVectJacobi);
        double avgNormGS = Matrix.norm(avgVectGS);
        exactNorm = Matrix.norm(xExact);

        double differenceJacobi = avgNormJacobi - exactNorm;
        differenceJacobi = Math.abs(differenceJacobi);

        double differenceGS = avgNormGS - exactNorm;
        differenceGS = Math.abs(differenceGS);
        System.out.println("The error approximation between average Jacobi vector and exact vector: " + differenceJacobi);
        System.out.println("The error approximation between average Gauss-Seidel vector and exact vector: " + differenceGS + "\n");

        double ratio = avgIterationNumJacobi / avgIterationNumGS;
        System.out.println("Ratio of average jacobi iterations to average number gauss-seidel iterations: " + ratio + " : 1");


    }
}
