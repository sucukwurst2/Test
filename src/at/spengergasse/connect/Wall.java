package at.spengergasse.connect;



import javafx.scene.image.*;
import javafx.scene.*;

public class Wall{
	private double x,y;
	private Node rect;
	private static final String imgpath = "file:///C:/Users/Niki/git/Test/img/wall.png";
	private Image img;
	private Runner r;
	Group g;
	
	public Wall(double x,double y,Runner r) {
		this.r = r;
		img = new Image(imgpath);
		rect = new ImageView(img);
		
		
		
	}
	
	public Group returnGroup() {
		return g;
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

	public Node getRect() {
		return rect;
	}

	public void setRect(Node rect) {
		this.rect = rect;
	}
	
	
}
