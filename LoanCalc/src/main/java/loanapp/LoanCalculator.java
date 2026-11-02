
package loanapp;

/*
 * Name: Joshua Barrett
 * Course: CISC 191 - Java Programming
 * Assignment: JavaFX Loan Calculator with Animated Bouncing Ball
 * Description: Calculates loan payments using a JavaFX interface
 * and displays an animated ball and a clock.
 */

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.util.Locale;

public class LoanCalculator extends Application {

    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("JavaFX Loan Calculator");

        // Input fields for loan information
        TextField loanAmount = new TextField();
        TextField interestRate = new TextField();
        TextField loanTerm = new TextField();

        loanAmount.setPromptText("Example: 20000");
        interestRate.setPromptText("Example: 6.5");
        loanTerm.setPromptText("Example: 5");

        // Buttons and result labels
        Button calculateButton = new Button("Calculate");
        Button clearButton = new Button("Clear");

        Label resultLabel = new Label("Monthly Payment: --");
        Label totalLabel = new Label("Total Paid: --");
        Label interestLabel = new Label("Total Interest: --");
        Label liveLabel = new Label();

        resultLabel.setFont(Font.font("Arial", 18));
        resultLabel.setTextFill(Color.DARKBLUE);

        // Observable binding updates when input changes
        liveLabel.textProperty().bind(
            Bindings.concat(
                "Amount: $", loanAmount.textProperty(),
                " | Rate: ", interestRate.textProperty(),
                "% | Years: ", loanTerm.textProperty()
            )
        );

        liveLabel.setWrapText(true);

        // Organize input fields using GridPane
        GridPane grid = new GridPane();
        grid.setHgap(15);
        grid.setVgap(15);
        grid.setPadding(new Insets(20));

        grid.add(new Label("Loan Amount ($):"), 0, 0);
        grid.add(loanAmount, 1, 0);

        grid.add(new Label("Annual Interest Rate (%):"), 0, 1);
        grid.add(interestRate, 1, 1);

        grid.add(new Label("Loan Term (Years):"), 0, 2);
        grid.add(loanTerm, 1, 2);

        // Calculate loan payment when button is clicked
        calculateButton.setOnAction(event -> {

            try {
                double principal = Double.parseDouble(
                    loanAmount.getText().trim().replace(",", "")
                );

                double annualRate = Double.parseDouble(
                    interestRate.getText().trim()
                );

                int years = Integer.parseInt(
                    loanTerm.getText().trim()
                );

                if (!Double.isFinite(principal)
                        || !Double.isFinite(annualRate)
                        || principal <= 0
                        || annualRate < 0
                        || years <= 0
                        || years > 100) {

                    throw new IllegalArgumentException(
                        "Enter valid positive loan details."
                    );
                }

                // Convert annual rate to monthly rate
                double monthlyRate = annualRate / 100 / 12;

                // Convert years into months
                int months = years * 12;

                double monthlyPayment;

                // Handle loans with no interest
                if (monthlyRate == 0) {
                    monthlyPayment = principal / months;
                } else {
                    monthlyPayment =
                        (principal * monthlyRate) /
                        (1 - Math.pow(1 + monthlyRate, -months));
                }

                double totalPaid = monthlyPayment * months;
                double totalInterest = totalPaid - principal;

                resultLabel.setText(String.format(
                    Locale.US,
                    "Monthly Payment: $%.2f",
                    monthlyPayment
                ));

                totalLabel.setText(String.format(
                    Locale.US,
                    "Total Paid: $%.2f",
                    totalPaid
                ));

                interestLabel.setText(String.format(
                    Locale.US,
                    "Total Interest: $%.2f",
                    totalInterest
                ));

                resultLabel.setTextFill(Color.DARKGREEN);

            } catch (NumberFormatException exception) {

                resultLabel.setText(
                    "Error: Please enter numbers only."
                );
                resultLabel.setTextFill(Color.RED);

            } catch (IllegalArgumentException exception) {

                resultLabel.setText(exception.getMessage());
                resultLabel.setTextFill(Color.RED);
            }
        });

        // Clear inputs and previous calculations
        clearButton.setOnAction(event -> {
            loanAmount.clear();
            interestRate.clear();
            loanTerm.clear();

            resultLabel.setText("Monthly Payment: --");
            totalLabel.setText("Total Paid: --");
            interestLabel.setText("Total Interest: --");
            resultLabel.setTextFill(Color.DARKBLUE);
        });

        HBox buttons = new HBox(
            10, calculateButton, clearButton
        );
        buttons.setAlignment(Pos.CENTER);

        VBox calculator = new VBox(
            15,
            new Label("Loan Calculator"),
            grid,
            liveLabel,
            buttons,
            resultLabel,
            totalLabel,
            interestLabel
        );

        calculator.setAlignment(Pos.CENTER);
        calculator.setPadding(new Insets(20));
        calculator.setPrefWidth(430);

        // Create the bouncing ball animation
        BouncingBallPane ballPane = new BouncingBallPane();

        VBox animationPanel = new VBox(
            15,
            new Label("Bouncing Ball Animation"),
            ballPane,
            new Label("Click the ball to change its color!")
        );

        animationPanel.setAlignment(Pos.CENTER);
        animationPanel.setPadding(new Insets(20));

        // Reusable clock component
        ClockPane clock = new ClockPane();

        Label heading = new Label(
            "JavaFX Loan Calculator Project"
        );
        heading.setFont(Font.font("Arial", 24));
        heading.setTextFill(Color.DARKBLUE);

        HBox top = new HBox(25, heading, clock);
        top.setAlignment(Pos.CENTER);
        top.setPadding(new Insets(20));

        // Main application layout
        HBox center = new HBox(20, calculator, animationPanel);
        center.setAlignment(Pos.CENTER);

        BorderPane root = new BorderPane();
        root.setTop(top);
        root.setCenter(center);

        root.setStyle(
            "-fx-background-color: #edf3fa;"
        );

        Scene scene = new Scene(root, 950, 550);

        // Display the application
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
