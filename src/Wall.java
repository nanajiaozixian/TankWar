import java.awt.*;

public class Wall {
	private int x, y;
	private int iWidth, iHeight;
	private static final Color COLOR_WALL = Color.gray;
	
	Wall(int x_in, int y_in, int width, int height){
		x = x_in;
		y = y_in;
		iWidth = width;
		iHeight = height;
	}
	
	public void draw(Graphics g){
		Color c = g.getColor();
		g.setColor(COLOR_WALL);
		g.fillRect(x, y, iWidth, iHeight);
		g.setColor(c);
	}
	
	public Rectangle getRec(){
		return new Rectangle(x, y, iWidth, iHeight);
	}
}
