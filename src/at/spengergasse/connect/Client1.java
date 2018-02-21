package at.spengergasse.connect;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Client1 extends Application implements KeyListener {
	private int translateX = 0,translateY = 0,width,height;
	public static void main(String[] args) {
        Application.launch(args);
    }
    
    public void start(Stage primaryStage) {

    	primaryStage.setTitle("Settings");


        Button fullHd = new Button("FullHd");
        Button hd = new Button("hd");

        fullHd.setOnAction(action -> {
            setWidth(1920);
            setHeight(1080);
        });
        hd.setOnAction(action -> {
            setWidth(1280);
            setHeight(720);
        });

        HBox hbox = new HBox(fullHd, hd);

        Scene scene = new Scene(hbox, 500, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            System.out.println("Right key pressed");
            changeTranslateX(10);
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            System.out.println("Left key pressed");
            changeTranslateX(-10);
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            System.out.println("Up key pressed");
            changeTranslateY(10);
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            System.out.println("Down key pressed");
            changeTranslateY(-10);
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    
    
    
    
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	
	
	public int getTranslateX() {
		return translateX;
	}

	public void setTranslateX(int translateX) {
		this.translateX = translateX;
	}

	public int getTranslateY() {
		return translateY;
	}

	public void setTranslateY(int translateY) {
		this.translateY = translateY;
	}
	
	
	public void changeTranslateX(int value) {
		translateX += value;
	}
	public void changeTranslateY(int value) {
		translateY += value;
	}

}