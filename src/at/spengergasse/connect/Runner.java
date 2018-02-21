package at.spengergasse.connect;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.image.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.shape.Circle;

/**
 * Hold down an arrow key to have your player1 move around the screen.
 * Hold down the shift key to have the player1 run.
 */
public class Runner extends Application {

    private static double W = 600, H = 400;


    boolean running, goNorth, goSouth, goEast, goWest;
    private static final String IMG_1 =
            "file:///C:/Users/Niki/git/Test/img/player1.png", IMG_2 = "file:///C:/Users/Niki/git/Test/img/player2.png";

    private Image playerImage1,playerImage2;
    private Node  player1;
    private Node  player2;

    @Override
    public void start(Stage stage) throws Exception {
    	//player1 = new Circle(50.0f, Color.RED);
		//movePlayer1To(100, 100);
        playerImage1 = new Image(IMG_1);
        player1 = new ImageView(playerImage1);
        
        playerImage2 = new Image(IMG_2);
        player2 = new ImageView(playerImage2);
        

		Group root = new Group(player1, player2);
		

		stage.setResizable(false);
		Scene scene1 = new Scene(root, W, H, Color.FORESTGREEN);


       

        movePlayer1To(W / 2, H / 2);

        

        scene1.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case UP:    goNorth = true; break;
                    case DOWN:  goSouth = true; break;
                    case LEFT:  goWest  = true; break;
                    case RIGHT: goEast  = true; break;
                    case SHIFT: running = true; break;
                    case ESCAPE: menue(stage,scene1); break;
                }
            }
        });

        scene1.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case UP:    goNorth = false; break;
                    case DOWN:  goSouth = false; break;
                    case LEFT:  goWest  = false; break;
                    case RIGHT: goEast  = false; break;
                    case SHIFT: running = false; break;
                }
            }
        });

        stage.setScene(scene1);
        stage.show();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                int dx = 0, dy = 0;

                if (goNorth) dy -= 5;
                if (goSouth) dy += 5;
                if (goEast)  dx += 5;
                if (goWest)  dx -= 5;
                if (running) { dx *= 3; dy *= 3; }

                movePlayer1By(dx, dy);
            }
        };
        timer.start();
    }
    
    public void menue(Stage stage,Scene scene) {
    	
    	Button fullHd = new Button("FullHd");
        Button hd = new Button("hd");
        Button exit = new Button("exit");
        Button backtogame = new Button("backtogame");
        Button fullscreen = new Button("fullscreen");
        HBox hbox = new HBox(fullHd, hd, exit,backtogame,fullscreen);
        
        fullHd.setOnAction(action -> {
            setW(1920);
            setH(1080);
            stage.setWidth(W);
            stage.setHeight(H);
        });
        hd.setOnAction(action -> {
            setW(1280);
            setH(720);
            stage.setWidth(W);
            stage.setHeight(H);
        });
        exit.setOnAction(action -> {
            System.exit(1);
        });
        backtogame.setOnAction(action -> {
            stage.setScene(scene);
        });
        fullscreen.setOnAction(action -> {
            stage.setFullScreen(true);
        });
        
    	Scene menue = new Scene(hbox, W, H, Color.GRAY);
    	stage.setScene(menue);
    	
    }

    private void movePlayer1By(int dx, int dy) {
        if (dx == 0 && dy == 0) return;

        final double cx = player1.getBoundsInLocal().getWidth()  / 2;
        final double cy = player1.getBoundsInLocal().getHeight() / 2;

        double x = cx + player1.getLayoutX() + dx;
        double y = cy + player1.getLayoutY() + dy;

        movePlayer1To(x, y);
    }

    private void movePlayer1To(double x, double y) {
        final double cx = player1.getBoundsInLocal().getWidth()  / 2;
        final double cy = player1.getBoundsInLocal().getHeight() / 2;

        if (x - cx >= 0 &&
            x + cx <= W &&
            y - cy >= 0 &&
            y + cy <= H) {
            player1.relocate(x - cx, y - cy);
        }
    }

    public static void main(String[] args) { launch(args); }

    
	public static double getW() {
		return W;
	}

	public static void setW(double w) {
		W = w;
	}

	public static double getH() {
		return H;
	}

	public static void setH(double h) {
		H = h;
	}
    
    
}


