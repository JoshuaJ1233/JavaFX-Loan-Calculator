
package loanapp;

/*
 * Name: Joshua Barrett
 * Course: CISC 191 - Java Programming
 * Assignment: JavaFX Loan Calculator
 * Description: Creates a bouncing ball animation using
 * PathTransition, Timeline, and mouse events.
 */

import javafx.animation.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.util.Duration;

public class BouncingBallPane extends Pane {

    private Circle ball;
    private int colorIndex = 0;

    private final Color[] colors = {
        Color.RED,
        Color.BLUE,
        Color.GREEN,
        Color.ORANGE,
        Color.PURPLE
    };

    public BouncingBallPane() {

        setPrefSize(440, 260);
        setMinSize(440, 260);

        setStyle(
            "-fx-background-color: white;" +
            "-fx-border-color: lightgray;"
        );

        // Create the animated ball
        ball = new Circle(17);
        ball.setFill(Color.RED);

        // Decorative floor
        Rectangle floor = new Rectangle(
            20, 229, 400, 6
        );
        floor.setFill(Color.LIGHTGRAY);

        // Decorative line and polygon
        Line line = new Line(20, 240, 420, 240);
        line.setStroke(Color.DARKGRAY);

        Polygon triangle = new Polygon(
            20.0, 15.0,
            35.0, 40.0,
            5.0, 40.0
        );
        triangle.setFill(Color.LIGHTBLUE);

        // Create a curved bouncing path
        Path path = new Path();

        path.getElements().addAll(
            new MoveTo(30, 205),
            new QuadCurveTo(110, 5, 220, 205),
            new QuadCurveTo(330, 40, 410, 205)
        );

        // Animate the ball along the path
        PathTransition bounce = new PathTransition();

        bounce.setDuration(Duration.seconds(3));
        bounce.setNode(ball);
        bounce.setPath(path);

        bounce.setCycleCount(Animation.INDEFINITE);
        bounce.setAutoReverse(true);

        // Timeline adds continuous squash animation
        Timeline squash = new Timeline(
            new KeyFrame(
                Duration.ZERO,
                new KeyValue(ball.scaleYProperty(), 1),
                new KeyValue(ball.scaleXProperty(), 1)
            ),
            new KeyFrame(
                Duration.millis(500),
                new KeyValue(ball.scaleYProperty(), 0.85),
                new KeyValue(ball.scaleXProperty(), 1.15)
            ),
            new KeyFrame(
                Duration.seconds(1),
                new KeyValue(ball.scaleYProperty(), 1),
                new KeyValue(ball.scaleXProperty(), 1)
            )
        );

        squash.setCycleCount(Animation.INDEFINITE);

        // Mouse event changes ball color
        ball.setOnMouseClicked(event -> {

            colorIndex = (colorIndex + 1) % colors.length;

            ball.setFill(colors[colorIndex]);
        });

        getChildren().addAll(
            floor, line, triangle, ball
        );

        // Start both animations
        bounce.play();
        squash.play();
    }
}
