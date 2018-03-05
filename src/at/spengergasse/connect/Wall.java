package at.spengergasse.connect;



import javafx.scene.image.*;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
/**
 * 
 * 
 * @author Niki
 * erstellt ein Rectangle auf das ein bild gelegt wird, 2 variablen um die x und y koordinaten zu speichern
 */
public class Wall {
	private Rectangle rect;
	private static final String imgpath = "file:///C:/Users/Niki/git/Test/img/wall.png";
	private StackPane wall;
	private ImageView img;
	private double x,y;
	
	public Wall(double x, double y){
	    rect = new Rectangle(30,30);
        wall = new StackPane();
        img = new ImageView(imgpath);
        wall.getChildren().addAll(rect, img);
        setX(x);
        setY(y);
        
	}
	public StackPane getWall() {
		return wall;
	}
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	
	
}
	

