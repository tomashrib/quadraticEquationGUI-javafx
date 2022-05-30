import java.text.DecimalFormat;


import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
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

    public void start(Stage stage) {

        lbAppTitle = new Label("Quadratic Equation");
        lbEquationDisplay = new Label("ax^2 + bx - c");

        lbParameterA = new Label("a:");
        tfParameterA = new TextField();

        lbParameterB = new Label("b:");
        tfParameterB = new TextField();

        lbParameterC = new Label("c:");
        tfParameterC = new TextField();

        lbDiscriminantD = new Label("D:");
        lbRootDiscriminantDResult = new Label();

        lbRootX1 = new Label("x1:");
        lbRootX1Result = new Label();

        lbRootX2 = new Label("x2:");
        lbRootX2Result = new Label();

        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();

        LineChart lineChart = new LineChart(xAxis, yAxis);
        // lineChart.setHorizontalZeroLineVisible(true);
        // lineChart.setVerticalZeroLineVisible(true);
        // lineChart.setCreateSymbols(false);
        lineChart.setLegendVisible(false);
        XYChart.Series series1 = new XYChart.Series();
        lineChart.getData().addAll(series1);


        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                DecimalFormat df = new DecimalFormat();
                df.setMaximumFractionDigits(4);
                try {
                    Double parameterA = Double.parseDouble(tfParameterA.getText());
                    Double parameterB = Double.parseDouble(tfParameterB.getText());
                    Double parameterC = Double.parseDouble(tfParameterC.getText());
    
                    series1.getData().clear();
                    double arrayVertex[] = new double[2];
                    arrayVertex = Calculation.vertexXY(parameterA, parameterB, parameterC);
                    series1.getData().add(new XYChart.Data(arrayVertex[0], arrayVertex[1]));

                    for(double i = -100.0; i < 100.0; i = i + 0.1){
                        series1.getData().add(new XYChart.Data(arrayVertex[0] + i, i *i));
                    }
    
                    Double x1 = calculate.calculationPlus(parameterA, parameterB, parameterC);
                    Double x2 = calculate.calculationMinus(parameterA, parameterB, parameterC);
                    Double d = calculate.calculationD(parameterA, parameterB, parameterC);
    
                    if (d < 0.0) {
                        lbRootX1Result.setText("not real (ps. get real)");
                        lbRootX2Result.setText("not real (ps. get real)");
                    } else {
                        lbRootX1Result.setText(String.valueOf(df.format(x1)));
                        lbRootX2Result.setText(String.valueOf(df.format(x2)));
    
                    }
                    lbRootDiscriminantDResult.setText(String.valueOf(df.format(d)));
    
                    // +b +c
                    if (parameterB > 0 && parameterC > 0) {
                        lbEquationDisplay.setText(String.valueOf(df.format(parameterA)) + "x^2 + "
                                + String.valueOf(df.format(parameterB)) + "x + " + String.valueOf(df.format(parameterC)));
                    }
                    // +b -c
                    else if (parameterB > 0 && parameterC < 0) {
                        lbEquationDisplay.setText(String.valueOf(df.format(parameterA)) + "x^2 + "
                                + String.valueOf(df.format(parameterB)) + "x " + String.valueOf(df.format(parameterC)));
                    }
                    // -b +c
                    else if (parameterB < 0 && parameterC > 0) {
                        lbEquationDisplay.setText(String.valueOf(df.format(parameterA)) + "x^2 "
                                + String.valueOf(df.format(parameterB)) + "x + " + String.valueOf(df.format(parameterC)));
                    }
                    // -b -c
                    else if (parameterB < 0 && parameterC < 0) {
                        lbEquationDisplay.setText(String.valueOf(df.format(parameterA)) + "x^2 "
                                + String.valueOf(df.format(parameterB)) + "x " + String.valueOf(df.format(parameterC)));
                    }
                    // 0 else
                    else {
                        lbEquationDisplay.setText(String.valueOf(df.format(parameterA)) + "x^2 + "
                                + String.valueOf(df.format(parameterB)) + "x + " + String.valueOf(df.format(parameterC)));
                    }
                    
    
                } catch (Exception exception) {
                    Alert alert = new Alert(AlertType.WARNING);
                    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                    DialogPane dialogPane = alert.getDialogPane();
                    // dialogPane.getStylesheets().add("css/style.css");
                    // dialogPane.getStyleClass().add("warning");
                    // stage.getIcons().add(new Image("./media/warningIkona.png"));
                    alert.setHeaderText("Zadajte číslo!");
                    // javac -encoding utf8 ....java
                    alert.setTitle("Chyba");
                    alert.setContentText("Zadaný vstup musí byť číslo.");
                    alert.show();
                }
    
            }
        };

        btCalculate = new Button("Calculate");
        btCalculate.setOnAction(event);

        GridPane gPane = new GridPane();
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

        Scene scene = new Scene(bPane, 1280, 864);
        scene.getStylesheets().add("style/style.css");
        stage.setTitle("Quadratic Equation");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();
    }

    

    public static void main(String arg[]) {
        Application.launch();
    }

}