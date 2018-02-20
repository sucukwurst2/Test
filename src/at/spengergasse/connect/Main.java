package at.spengergasse.connect;

import java.awt.event.KeyEvent;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * @web http://java-buddy.blogspot.com/
 */
public class Main  extends Application {
    
    Circle circle_Red,  circle_Blue;
    double orgSceneX, orgSceneY;
    double orgTranslateX, orgTranslateY;
    
    @Override
    public void start(Stage primaryStage) {
    	
        //Create Circles
        circle_Red = new Circle(50.0f, Color.RED);
        circle_Red.setCursor(Cursor.HAND);
      
        
        circle_Blue = new Circle(50.0f, Color.BLUE);
        circle_Blue.setCursor(Cursor.CROSSHAIR);
        circle_Blue.setTranslateX(250);
        circle_Blue.setTranslateY(250);
       
                
        Group root = new Group();
        root.getChildren().addAll(circle_Red, circle_Blue);
        
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root, 500,500));
        
        primaryStage.setTitle("fresh thing");
        primaryStage.show();
        
    }
}



