import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import java.awt.Color;
import java.util.*;

public class pascalplot extends Application {

    @Override
    public void start(Stage stage) {

        stage.setTitle("Errors Obtained as a Function of n");
        final NumberAxis xAxis = new NumberAxis(-20, 20, 10);
        final NumberAxis yAxis = new NumberAxis(-5, 5, 1);
        final ScatterChart<Number,Number> sc = new
                ScatterChart<Number,Number>(xAxis,yAxis);
        xAxis.setLabel("Determinant");
        yAxis.setLabel("Trace");
        sc.setTitle("Errors Obtained as a Function of n");



        final double TOLERANCE = 0.00005;
        final int ITERATIONS = 100;
        double[][] approx2by2 = {{1},{1}};
        Map<Double, Integer> detToMatrixIterations = new HashMap<>();
        Map<Double, Double> detToTrace = new HashMap<>();
        ArrayList<Double> traceList = new ArrayList<>(1000);
        ArrayList<Double> detList = new ArrayList<>(1000);
        ArrayList<Integer> iterationsList = new ArrayList<>(1000);
        ArrayList<Integer> invIterationsList = new ArrayList<>(1000);
        Matrix startingApproxMatrix = new Matrix(approx2by2);

        for (int i = 0; i < 1000; i++) {
            Matrix current = create2by2Matrix();
            Matrix invMatrix = current.inverse2by2();
            power_method powMethod = new power_method(current, startingApproxMatrix, TOLERANCE, ITERATIONS);
            iterationsList.add(powMethod.iterationsCount);
            double det = 1 / ((current.get(0, 0) * current.get(1, 1)) - (current.get(0, 1) * current.get(1, 0)));
            detToMatrixIterations.put(det, powMethod.iterationsCount);
            powMethod = new power_method(invMatrix, startingApproxMatrix, TOLERANCE, ITERATIONS);
            invIterationsList.add(powMethod.iterationsCount);

            detList.add(det);
            traceList.add(current.trace());
            detToTrace.put(det, current.trace());

        }
        Collections.sort(detList);
        Iterator iter = detList.iterator();

        XYChart.Series series1 = new XYChart.Series();
        series1.setName("iterations < 10");
        XYChart.Series series2 = new XYChart.Series();
        series2.setName("10 < iterations < 20");
        XYChart.Series series3 = new XYChart.Series();
        series3.setName("20 < iterations < 30");
        XYChart.Series series4 = new XYChart.Series();
        series4.setName("30 < iterations < 40");
        XYChart.Series series5 = new XYChart.Series();
        series5.setName("40 < iterations < 50");
        XYChart.Series series6 = new XYChart.Series();
        series6.setName("50 < iterations < 60");
        XYChart.Series series7 = new XYChart.Series();
        series7.setName("60 < iterations < 70");
        XYChart.Series series8 = new XYChart.Series();
        series8.setName("70 < iterations < 80");
        XYChart.Series series9 = new XYChart.Series();
        series9.setName("80 < iterations < 90");
        XYChart.Series series10 = new XYChart.Series();
        series10.setName("90 < iterations < 99");
        XYChart.Series series11 = new XYChart.Series();
        series11.setName("100 iterations");

        double current = detList.get(0);
        int c = 0;
        for (int i = 0; i < detList.size(); i++) {
            current = detList.get(i);
            if (detToMatrixIterations.get(current) < 10) {
                series1.getData().add(new XYChart.Data(current, detToTrace.get(current)));
            } else if (detToMatrixIterations.get(current) > 10 && detToMatrixIterations.get(current) < 20 ) {
                series2.getData().add(new XYChart.Data(current, detToTrace.get(current)));
            } else if (detToMatrixIterations.get(current) > 20 && detToMatrixIterations.get(current) < 30 ) {
                series3.getData().add(new XYChart.Data(current, detToTrace.get(current)));
            } else if (detToMatrixIterations.get(current) > 30 && detToMatrixIterations.get(current) < 40 ) {
                series4.getData().add(new XYChart.Data(current, detToTrace.get(current)));
            } else if (detToMatrixIterations.get(current) > 40 && detToMatrixIterations.get(current) < 50 ) {
                series5.getData().add(new XYChart.Data(current, detToTrace.get(current)));
            } else if (detToMatrixIterations.get(current) > 50 && detToMatrixIterations.get(current) < 60 ) {
                series6.getData().add(new XYChart.Data(current, detToTrace.get(current)));
            } else if (detToMatrixIterations.get(current) > 60 && detToMatrixIterations.get(current) < 70 ) {
                series7.getData().add(new XYChart.Data(current, detToTrace.get(current)));
            } else if (detToMatrixIterations.get(current) > 70 && detToMatrixIterations.get(current) < 80 ) {
                series8.getData().add(new XYChart.Data(current, detToTrace.get(current)));
            } else if (detToMatrixIterations.get(current) > 80  && detToMatrixIterations.get(current) < 90 ) {
                series9.getData().add(new XYChart.Data(current, detToTrace.get(current)));
            } else if (detToMatrixIterations.get(current) > 90  && detToMatrixIterations.get(current) < 99 ) {
                series10.getData().add(new XYChart.Data(current, detToTrace.get(current)));
            } else if (detToMatrixIterations.get(current) == 100) {
                series11.getData().add(new XYChart.Data(current, detToTrace.get(current)));
            }
        }
        /*while (current < 0) {
            series1.getData().add(new XYChart.Data(current, detToTrace.get(current)));
            current = detList.get(++c);
        }
        while (current < 3) {
            series2.getData().add(new XYChart.Data(current, detToTrace.get(current)));
            current = detList.get(++c);
        }*/




        // XYChart.Series series2 = new XYChart.Series();
        //series1.setName("Determinant vs. Trace");


        sc.getData().addAll(series1, series2, series3, series4, series5, series6, series7, series8, series9, series10, series11);
        Scene scene  = new Scene(sc, 500, 400);
        scene.getStylesheets().add("GraphEffects.css");
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

    public static Matrix create2by2Matrix() {
        Random numGenerator = new Random();
        double[][] matrix = {{0, 0}, {0, 0}};
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                matrix[i][j] = (numGenerator.nextFloat() * 4) - 2;
            }
        }
        Matrix ret = new Matrix(matrix);
        return ret;
    }
}
