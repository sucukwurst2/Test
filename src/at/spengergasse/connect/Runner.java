package at.spengergasse.connect;

import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.image.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * https://carlfx.wordpress.com/tag/collision-detection/   
 */
public class Runner extends Application {
	
	
    private double W = 1000, H = 666;


    boolean running, goNorth, goSouth, goEast, goWest;
    private static final String IMG_1 =
            "file:///C:/Users/Niki/git/Test/src/img/player1.png", IMG_2 = "file:///C:/Users/Niki/git/Test/src/img/player2.png",BACKGROUND = "file:///C:/Users/Niki/git/Test/src/img/boden.png";

    private Image playerImage1,playerImage2,image;
    private Node  player1;
    private Node  player2;
    private static Group root;
    private ArrayList<Wall> walls = new ArrayList();
    

    @Override
    public void start(Stage stage) throws Exception {

        playerImage1 = new Image(IMG_1);
        player1 = new ImageView(playerImage1);
        
        playerImage2 = new Image(IMG_2);
        player2 = new ImageView(playerImage2);

        
        image = new Image(BACKGROUND);
        ImageView imgView = new ImageView(image);
		stage.setResizable(false);
        
        root = new Group();
        root.getChildren().add(imgView);
        root.getChildren().add(player1);
        root.getChildren().add(player2);
        
        walls.add(new Wall(W/8,H/8));
        walls.add(new Wall(500,500));
       

		
		
		

		Scene scene1 = new Scene(root, W, H);
		


		
		
		
		for(int x = 0;x<walls.size();x++) {
			Wall w = walls.get(x);
			StackPane y = walls.get(x).getWall();
			root.getChildren().add(y);
			y.relocate(w.getX(),w.getY());

		}

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

                if (goNorth && checkUp()!=true) dy -= 1;
                if (goSouth && checkDown()!=true) dy += 1;
                if (goEast && checkRight()!=true)  dx += 1;
                if (goWest && checkLeft()!=true)  dx -= 1;
                if (running) { dx *= 3; dy *= 3; }
                

                movePlayerBy(dx, dy,player1);
                //serverStuff(1, 1);
              
                System.out.println("Left:" + checkLeft() + "    Right:" + checkRight() + "    Up:" + checkUp() + "    Down:" + checkDown());
                
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
    
    /*public boolean checkPlayerCollision(Node player1, Node player2) {
    	if(player1.getBoundsInParent().intersects(player2.getBoundsInParent()))return true;
    	else return false;
    	
    }
    
    public void checkSet(double x,double y,Node n) {
    	double xx = player1.getBoundsInParent().getMinX()+25;
    	double yy = player1.getBoundsInParent().getMinY()+25;
    	player1.relocate(x, y);
    	if(player1.getBoundsInParent().intersects(player2.getBoundsInParent())){
    		player1.relocate(xx, yy);
    	}
    }*/
    
    
 
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
    	   player1.getBoundsInParent().getMinY() <= n.getBoundsInParent().getMaxX())return true;
    	else return false;
    }
    public boolean checkPlayerRight(Node n) {
    	if(player1.getBoundsInParent().getMaxX() == n.getBoundsInParent().getMinX()-1 &&
    	   player1.getBoundsInParent().getMaxY() >= n.getBoundsInParent().getMinY() &&
    	   player1.getBoundsInParent().getMinY() <= n.getBoundsInParent().getMaxX())return true;
    	else return false;
    }
    
    
    
    public boolean checkWallUp(Wall w) {
    	if((player1.getBoundsInParent().getMinY() == w.getWall().getBoundsInParent().getMaxY()+1) &&
    	    player1.getBoundsInParent().getMaxX() >= w.getWall().getBoundsInParent().getMinX() &&
    	    player1.getBoundsInParent().getMinX() <= w.getWall().getBoundsInParent().getMaxX())return true;
    	else return false;
    	
    			
    }
    public boolean checkWallDown(Wall w) {
    	if((player1.getBoundsInParent().getMaxY() == w.getWall().getBoundsInParent().getMinY()-1 &&
    	    player1.getBoundsInParent().getMaxX() >= w.getWall().getBoundsInParent().getMinX() &&
    	    player1.getBoundsInParent().getMinX() <= w.getWall().getBoundsInParent().getMaxX()))return true;
    	else return false;
    	
    }
    public boolean checkWallLeft(Wall w) {
    	if(player1.getBoundsInParent().getMinX() == w.getWall().getBoundsInParent().getMaxX()+1 &&
    	   player1.getBoundsInParent().getMaxY() >= w.getWall().getBoundsInParent().getMinY() &&
    	   player1.getBoundsInParent().getMinY() <= w.getWall().getBoundsInParent().getMaxX())return true;
    	else return false;
    }
    public boolean checkWallRight(Wall w) {
    	if(player1.getBoundsInParent().getMaxX() == w.getWall().getBoundsInParent().getMinX()-1 &&
    	   player1.getBoundsInParent().getMaxY() >= w.getWall().getBoundsInParent().getMinY() &&
    	   player1.getBoundsInParent().getMinY() <= w.getWall().getBoundsInParent().getMaxX())return true;
    	else return false;
    }
    
    
    
    public boolean checkRight() {
    	boolean b = false;
    	if(checkPlayerRight(player2))b = true;
    	for(int x = 0;x<walls.size();x++) {
			Wall w = walls.get(x);
			
			if(checkWallRight(w))b = true;
		}
    	return b;
    }
    
    public boolean checkLeft() {
    	boolean b = false;
    	if(checkPlayerLeft(player2))b = true;
    	for(int x = 0;x<walls.size();x++) {
			Wall w = walls.get(x);
			
			if(checkWallLeft(w))b = true;
		}
    	return b;
    }
    public boolean checkUp() {
    	boolean b = false;
    	if(checkPlayerUp(player2))b = true;
    	for(int x = 0;x<walls.size();x++) {
			Wall w = walls.get(x);
			
			if(checkWallUp(w))b = true;
		}
    	return b;
    }
    public boolean checkDown() {
    	boolean b = false;
    	if(checkPlayerDown(player2))b = true;
    	for(int x = 0;x<walls.size();x++) {
			Wall w = walls.get(x);
			
			if(checkWallDown(w))b = true;
		}
    	return b;
    }
    
    
    
    
    
    /*
    public void serverStuff(int x, int y) {
    	try {

    	     Socket socketConnection = new Socket("127.0.0.1", 11111);


    	     //QUERY PASSING
    	     DataOutputStream outToServer = new DataOutputStream(socketConnection.getOutputStream());
    	     
    	     int SQL= 2 + 3;
    	     outToServer.write(SQL);


    	  } catch (Exception e) {System.out.println(e); }
     }*/
    
    
    
    
    public static void main(String[] args) { launch(args); }

    
    
	

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


