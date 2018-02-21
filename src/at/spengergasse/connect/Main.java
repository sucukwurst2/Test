package at.spengergasse.connect;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.scene.input.KeyEvent;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * @web http://java-buddy.blogspot.com/
 */
public class Main extends Application {

	Circle player1, player2;
	double orgSceneX, orgSceneY;
	double orgTranslateX, orgTranslateY;
	String color1, color2;
	private static int width = 500, height = 500;
	boolean running, goNorth, goSouth, goEast, goWest;

	@Override
	public void start(Stage primaryStage) {

		// Create Circles
		player1 = new Circle(50.0f, Color.RED);
		movePlayer1To(100, 100);

		player2 = new Circle(50.0f, Color.BLUE);
		player2.setTranslateX(250);
		player2.setTranslateY(250);

		Group root = new Group(player1, player2);
		

		primaryStage.setResizable(false);
		Scene scene1 = new Scene(root, width, height, Color.FORESTGREEN);

		
		scene1.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				switch (event.getCode()) {
				case UP:
					goNorth = true;
					break;
				case DOWN:
					goSouth = true;
					break;
				case LEFT:
					goWest = true;
					break;
				case RIGHT:
					goEast = true;
					break;
				case SHIFT:
					running = true;
					break;
				}
			}
		});

		scene1.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				switch (event.getCode()) {
				case UP:
					goNorth = false;
					break;
				case DOWN:
					goSouth = false;
					break;
				case LEFT:
					goWest = false;
					break;
				case RIGHT:
					goEast = false;
					break;
				case SHIFT:
					running = false;
					break;
				}
			}
		});
		primaryStage.setTitle("fresh thing");
		primaryStage.setScene(scene1);
		primaryStage.show();

		AnimationTimer timer = new AnimationTimer() {
			@Override
			public void handle(long now) {
				int dx = 0, dy = 0;

				if (goNorth)
					dy -= 1;
				if (goSouth)
					dy += 1;
				if (goEast)
					dx += 1;
				if (goWest)
					dx -= 1;
				if (running) {
					dx *= 3;
					dy *= 3;
				}

				movePlayer1By(dx, dy);
				System.out.println();
			}
		};
		timer.start();
	}

	private void movePlayer1By(int dx, int dy) {
		if (dx == 0 && dy == 0)
			return;

		final double cx = player1.getBoundsInLocal().getWidth() / 2;
		final double cy = player1.getBoundsInLocal().getHeight() / 2;

		double x = cx + player1.getLayoutX() + dx;
		double y = cy + player1.getLayoutY() + dy;

		movePlayer1To(x, y);
	}

	private void movePlayer1To(double x, double y) {
		final double cx = player1.getBoundsInLocal().getWidth() / 2;
		final double cy = player1.getBoundsInLocal().getHeight() / 2;

		if (x - cx >= 0 && x + cx <= width && y - cy >= 0 && y + cy <= height) {
			player1.relocate(x - cx, y - cy);
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
