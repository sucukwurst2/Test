package at.spengergasse.connect;

import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.image.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;


/**
 * https://carlfx.wordpress.com/tag/collision-detection/   
 */
public class Runner extends Application {
	
	
    private double W = 600, H = 400;
    private ArrayList<Wall> walls = new ArrayList();

    boolean running, goNorth, goSouth, goEast, goWest;
    private static final String IMG_1 =
            "file:///C:/Users/Niki/git/Test/img/player1.png", IMG_2 = "file:///C:/Users/Niki/git/Test/img/player2.png";

    private Image playerImage1,playerImage2,image;
    private Node  player1;
    private Node  player2;
    private static Group root;
    
    
    
	private Node rect;
	private static final String imgpath = "file:///C:/Users/Niki/git/Test/img/wall.png";

    @Override
    public void start(Stage stage) throws Exception {
    	//player1 = new Circle(50.0f, Color.RED);
		//movePlayer1To(100, 100);
        playerImage1 = new Image(IMG_1);
        player1 = new ImageView(playerImage1);
        
        playerImage2 = new Image(IMG_2);
        player2 = new ImageView(playerImage2);
        
        image = new Image(imgpath);
		rect = new ImageView(image);
        
        root = new Group(player1, player2,rect);
        
        //Wall wall1 = new Wall(350,350,this);
        //root = wall1.returnGroup();



		
		stage.setResizable(false);
		Scene scene1 = new Scene(root, W, H, Color.FORESTGREEN);


       
		rect.relocate(W/8, H/8);
        player1.relocate(W/2,H/2 );
        player2.relocate(W/4, H/4);

        

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

                if (goNorth && checkPlayerUp(player2)!=true && checkPlayerUp(rect)!=true) dy -= 1;
                if (goSouth && checkPlayerDown(player2)!=true && checkPlayerDown(rect)!=true) dy += 1;
                if (goEast && checkPlayerRight(player2)!=true && checkPlayerRight(rect)!=true)  dx += 1;
                if (goWest && checkPlayerLeft(player2)!=true && checkPlayerLeft(rect)!=true)  dx -= 1;
                if (running) { dx *= 3; dy *= 3; }
                

                movePlayerBy(dx, dy,player1);
               // System.out.println(checkPlayerCollision(player1, player2));
                //System.out.println(player1.getBoundsInParent());
                System.out.println("Left:" + checkPlayerLeft(player2) + "    Right:" + checkPlayerRight(player2) + "    Up:" + checkPlayerUp(player2) + "    Down:" + checkPlayerDown(player2));
                
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

    
    
    private void movePlayerBy(int dx, int dy,Node player) {
        if (dx == 0 && dy == 0) return;

        final double cx = player.getBoundsInLocal().getWidth()  / 2;
        final double cy = player.getBoundsInLocal().getHeight() / 2;

        double x = cx + player.getLayoutX() + dx;
        double y = cy + player.getLayoutY() + dy;

        movePlayerTo(x, y,player);
    }

    private void movePlayerTo(double x, double y,Node player) {
        final double cx = player.getBoundsInLocal().getWidth()  / 2;
        final double cy = player.getBoundsInLocal().getHeight() / 2;
        
        if (x - cx >= 0 &&
            x + cx <= W &&
            y - cy >= 0 &&
            y + cy <= H ) {
            player.relocate(x - cx, y - cy);
        }
    }
    
    public boolean checkPlayerCollision(Node player1, Node player2) {
    	if(player1.getBoundsInParent().intersects(player2.getBoundsInParent()))return true;
    	else return false;

    	
    }
    
    public boolean checkPlayerUp(Node n) {
    	if((player1.getBoundsInParent().getMinY() == n.getBoundsInParent().getMaxY()+1) &&
    	    player1.getBoundsInParent().getMaxX() >= n.getBoundsInParent().getMinX() &&
    	    player1.getBoundsInParent().getMinX() <= n.getBoundsInParent().getMaxX())return true;
    	else return false;
    	
    			
    }
    public boolean checkPlayerDown(Node n) {
    	if((player1.getBoundsInParent().getMaxY() == n.getBoundsInParent().getMinY()-1 &&
    	    player1.getBoundsInParent().getMaxX() >= n.getBoundsInParent().getMinX() &&
    	    player1.getBoundsInParent().getMinX() <= n.getBoundsInParent().getMaxX()))return true;
    	else return false;
    	
    }
    public boolean checkPlayerLeft(Node n) {
    	if(player1.getBoundsInParent().getMinX() == n.getBoundsInParent().getMaxX()+1 &&
    	   player1.getBoundsInParent().getMaxY() >= n.getBoundsInParent().getMinY() &&
    	   player1.getBoundsInParent().getMaxY() <= n.getBoundsInParent().getMaxX())return true;
    	else return false;
    }
    public boolean checkPlayerRight(Node n) {
    	if(player1.getBoundsInParent().getMaxX() == n.getBoundsInParent().getMinX()-1 &&
    	   player1.getBoundsInParent().getMaxY() >= n.getBoundsInParent().getMinY() &&
    	   player1.getBoundsInParent().getMaxY() <= n.getBoundsInParent().getMaxX())return true;
    	else return false;
    }
    
    
    
    
    
    public static void main(String[] args) { launch(args); }

    
    
    
    
    
    
	public ArrayList<Wall> getWalls() {
		return walls;
	}

	public void addWalls(Wall wallss) {
		walls.add(wallss);
	}
	

	public  double getW() {
		return W;
	}

	public  void setW(double w) {
		W = w;
	}

	public  double getH() {
		return H;
	}

	public  void setH(double h) {
		H = h;
	}
    
	public Group getRoot() {
		return root;
	}
    
}


