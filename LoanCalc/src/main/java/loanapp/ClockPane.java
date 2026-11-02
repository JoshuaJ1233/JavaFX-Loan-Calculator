
package loanapp;

/*
 * Name: Joshua Barrett
 * Course: CISC 191 - Java Programming
 * Assignment: JavaFX Loan Calculator
 * Description: Reusable clock component displaying
 * the current time with a Timeline.
 */

import javafx.animation.*;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ClockPane extends HBox {

    private final Label timeLabel = new Label();

    private final DateTimeFormatter formatter =
        DateTimeFormatter.ofPattern("hh:mm:ss a");

    public ClockPane() {

        setAlignment(Pos.CENTER);
        setSpacing(10);

        Label clockTitle = new Label("Current Time:");

        clockTitle.setFont(Font.font("Arial", 14));

        timeLabel.setFont(Font.font("Arial", 16));
        timeLabel.setTextFill(Color.DARKBLUE);

        getChildren().addAll(clockTitle, timeLabel);

        // Display current time immediately
        updateTime();

        // Update clock once every second
        Timeline clock = new Timeline(
            new KeyFrame(
                Duration.seconds(1),
                event -> updateTime()
            )
        );

        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    private void updateTime() {
        timeLabel.setText(
            LocalTime.now().format(formatter)
        );
    }
}
