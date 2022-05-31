import java.awt.Color;
import java.text.DecimalFormat;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;

//javac -encoding utf8 App.java && java App
//javac --module-path $PATH_TO_FX --add-modules javafx.controls App.java && java --module-path $PATH_TO_FX --add-modules javafx.controls App

public class App extends Application {
    private TextField tfParameterA, tfParameterB, tfParameterC;
    private Label lbRootX1, lbRootX2, lbDiscriminantD, lbAppTitle, lbEquationDisplay;
    private Label lbParameterA, lbParameterB, lbParameterC;
    private Label lbRootX1Result, lbRootX2Result, lbRootDiscriminantDResult;
    private Button btCalculate;
    private static Calculation calculate;

    NumberAxis xAxis = new NumberAxis();
    NumberAxis yAxis = new NumberAxis();

    public void start(Stage stage) {

        lbAppTitle = new Label("Quadratic Equation");
        lbAppTitle.getStyleClass().add("lbAppTitle");
        lbEquationDisplay = new Label("ax^2 + bx - c");
        lbEquationDisplay.getStyleClass().add("lbEquationDisplay");

        lbParameterA = new Label("a:");
        lbParameterA.getStyleClass().add("lbParameterA");
        tfParameterA = new TextField();
        tfParameterA.getStyleClass().add("tfParameterA");

        lbParameterB = new Label("b:");
        lbParameterB.getStyleClass().add("lbParameterB");
        tfParameterB = new TextField();
        tfParameterB.getStyleClass().add("tfParameterB");

        lbParameterC = new Label("c:");
        lbParameterC.getStyleClass().add("lbParameterC");
        tfParameterC = new TextField();
        tfParameterC.getStyleClass().add("tfParameterC");

        lbDiscriminantD = new Label("D:");
        lbDiscriminantD.getStyleClass().add("lbDiscriminantD");
        lbRootDiscriminantDResult = new Label();
        lbRootDiscriminantDResult.getStyleClass().add("lbRootDiscriminantDResult");

        lbRootX1 = new Label("x1:");
        lbRootX1.getStyleClass().add("lbRootX1");
        lbRootX1Result = new Label();
        lbRootX1Result.getStyleClass().add("lbRootX1Result");

        lbRootX2 = new Label("x2:");
        lbRootX2.getStyleClass().add("lbRootX2");
        lbRootX2Result = new Label();
        lbRootX2Result.getStyleClass().add("lbRootX2Result");

        xAxis.setAutoRanging(false);
        yAxis.setAutoRanging(false);

        xAxis.setTickLabelFill(Paint.valueOf("white"));
        yAxis.setTickLabelFill(Paint.valueOf("white"));

        xAxis.setLowerBound(-25);
        xAxis.setUpperBound(25);
        yAxis.setLowerBound(-25);
        yAxis.setUpperBound(25);

        LineChart lineChart = new LineChart(xAxis, yAxis);
        lineChart.setCreateSymbols(false);
        lineChart.setLegendVisible(false);
        XYChart.Series series1 = new XYChart.Series();
        lineChart.getData().addAll(series1);
        lineChart.setAnimated(true);

        Scroll scroll = new Scroll();
        lineChart.addEventFilter(ScrollEvent.ANY, scroll.onScrollEventHandler);

        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                DecimalFormat df = new DecimalFormat();
                df.setMaximumFractionDigits(4);
                try {
                    Double parameterA = Double.parseDouble(tfParameterA.getText());
                    if (parameterA == 0) {
                        lbAppTitle.setText("Linear equation");
                    }
                    Double parameterB = Double.parseDouble(tfParameterB.getText());
                    Double parameterC = Double.parseDouble(tfParameterC.getText());

                    series1.getData().clear();
                    

                    double discriminant = calculate.calculationD(parameterA, parameterB, parameterC);

                    if (discriminant < 0) {
                        Alert alert = new Alert(AlertType.WARNING);
                        alert.setTitle("Discriminant < 0");
                        alert.setHeaderText("Discriminant < 0");
                        alert.setContentText("Function graph is in irrational realm.");
                        alert.show();
                    } else {
                        if (parameterA == 0) {
                            for (double i = -1000; i <= 1000; i++) {
                                if (parameterB == 0) {
                                    series1.getData().add(new XYChart.Data(i, parameterC));
                                } else {
                                    double a = ((-parameterC) / (parameterB));
                                    series1.getData().add(new XYChart.Data(i + a, i * parameterB));
                                }
                            }
                        } else {
                            for (double i = -100.0; i <= 100.0; i = i + 0.1D) {
                                double x = calculate.axisOfSymetry(parameterA, parameterB);
                                double y = (parameterC - ((parameterB * parameterB) / (4 * parameterA)));

                                double nasobok = (parameterA * Math.pow(i, 2));

                                series1.getData().add(new XYChart.Data(i + x, nasobok + y));
                            }
                        }
                    }

                    Double x1 = calculate.calculationPlus(parameterA, parameterB, parameterC);
                    Double x2 = calculate.calculationMinus(parameterA, parameterB, parameterC);

                    if (discriminant < 0.0) {
                        lbRootX1Result.setText("not real (ps. get real)");
                        lbRootX2Result.setText("not real (ps. get real)");
                    } else {
                        lbRootX1Result.setText(String.valueOf(df.format(x1)));
                        lbRootX2Result.setText(String.valueOf(df.format(x2)));

                    }
                    lbRootDiscriminantDResult.setText(String.valueOf(df.format(discriminant)));

                    // +b +c
                    if (parameterB > 0 && parameterC > 0) {
                        lbEquationDisplay.setText(String.valueOf(df.format(parameterA)) + "x^2 + "
                                + String.valueOf(df.format(parameterB)) + "x + "
                                + String.valueOf(df.format(parameterC)));
                    }
                    // +b -c
                    else if (parameterB > 0 && parameterC < 0) {
                        lbEquationDisplay.setText(String.valueOf(df.format(parameterA)) + "x^2 + "
                                + String.valueOf(df.format(parameterB)) + "x " + String.valueOf(df.format(parameterC)));
                    }
                    // -b +c
                    else if (parameterB < 0 && parameterC > 0) {
                        lbEquationDisplay.setText(String.valueOf(df.format(parameterA)) + "x^2 "
                                + String.valueOf(df.format(parameterB)) + "x + "
                                + String.valueOf(df.format(parameterC)));
                    }
                    // -b -c
                    else if (parameterB < 0 && parameterC < 0) {
                        lbEquationDisplay.setText(String.valueOf(df.format(parameterA)) + "x^2 "
                                + String.valueOf(df.format(parameterB)) + "x " + String.valueOf(df.format(parameterC)));
                    }
                    // 0 else
                    else {
                        lbEquationDisplay.setText(String.valueOf(df.format(parameterA)) + "x^2 + "
                                + String.valueOf(df.format(parameterB)) + "x + "
                                + String.valueOf(df.format(parameterC)));
                    }

                } catch (Exception exception) {
                    Alert alert = new Alert(AlertType.WARNING);
                    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                    DialogPane dialogPane = alert.getDialogPane();
                    dialogPane.getStylesheets().add("style/style.css");
                    dialogPane.getStyleClass().add("warning");
                    stage.getIcons().add(new Image("./media/warningIkona.png"));
                    alert.setHeaderText("Zadajte číslo!");
                    alert.setTitle("Chyba");
                    alert.setContentText("Zadaný vstup musí byť číslo.");
                    alert.show();
                }

            }
        };

        btCalculate = new Button("Calculate");
        btCalculate.getStyleClass().add("btCalculate");
        btCalculate.setOnAction(event);

        GridPane gPane = new GridPane();
        gPane.getStyleClass().add("gpane");
        gPane.setVgap(20);
        gPane.setHgap(20);
        gPane.setAlignment(Pos.CENTER);

        gPane.setMargin(lbParameterA, new Insets(0, 0, 0, 20));
        gPane.setMargin(lbParameterB, new Insets(0, 0, 0, 20));
        gPane.setMargin(lbParameterC, new Insets(0, 0, 0, 20));
        gPane.setMargin(lbDiscriminantD, new Insets(0, 0, 0, 20));
        gPane.setMargin(lbRootX1, new Insets(0, 0, 0, 20));
        gPane.setMargin(lbRootX2, new Insets(0, 0, 0, 20));
        gPane.setMargin(btCalculate, new Insets(0, 0, 0, 20));

        gPane.add(lbParameterA, 0, 0, 1, 1);
        gPane.add(tfParameterA, 1, 0, 1, 1);

        gPane.add(lbParameterB, 0, 1, 1, 1);
        gPane.add(tfParameterB, 1, 1, 1, 1);

        gPane.add(lbParameterC, 0, 2, 1, 1);
        gPane.add(tfParameterC, 1, 2, 1, 1);

        gPane.add(lbDiscriminantD, 0, 3, 1, 1);
        gPane.add(lbRootDiscriminantDResult, 1, 3, 1, 1);

        gPane.add(lbRootX1, 0, 4, 1, 1);
        gPane.add(lbRootX1Result, 1, 4, 1, 1);

        gPane.add(lbRootX2, 0, 5, 1, 1);
        gPane.add(lbRootX2Result, 1, 5, 1, 1);

        gPane.add(btCalculate, 0, 6, 2, 1);
        btCalculate.setMaxWidth(Double.MAX_VALUE);

        VBox vbox = new VBox(lbAppTitle, lbEquationDisplay);
        vbox.getStyleClass().add("vbox");
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(5);

        BorderPane bPane = new BorderPane();
        bPane.setTop(vbox);
        bPane.setAlignment(vbox, Pos.CENTER);
        bPane.setLeft(gPane);
        bPane.setCenter(lineChart);
        lineChart.getStyleClass().add("linechart");

        bPane.setMargin(gPane, new Insets(20));
        bPane.setMargin(lineChart, new Insets(20));

        Scene scene = new Scene(bPane, 1280, 864);
        scene.getStylesheets().add("style/style.css");
        stage.setTitle("Quadratic Equation");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();

    }

    class Scroll {
        public EventHandler<ScrollEvent> onScrollEventHandler = new EventHandler<ScrollEvent>() {
            public void handle(ScrollEvent event) {
                double zoom = 1.1;
                double delta_y = event.getDeltaY();

                if (delta_y > 0) {
                    zoom = 1.9 - zoom;
                }

                xAxis.setLowerBound(xAxis.getLowerBound() * zoom);
                xAxis.setUpperBound(xAxis.getUpperBound() * zoom);
                yAxis.setLowerBound(yAxis.getLowerBound() * zoom);
                yAxis.setUpperBound(yAxis.getUpperBound() * zoom);
            }
        };
    }

    public static void main(String arg[]) {
        Application.launch();
    }

}