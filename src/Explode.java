import java.awt.*;


public class Explode {
	private int x,y;
	private boolean bLive = true;
	int diameter[] = {4, 8, 16, 32, 36, 40,50,80,50,40,32, 16, 8, 4};
	Color color = new Color(255, 0, 0);
	private int step = 0;
	public static int EXPLODE_WIDTH = 10;
	public static int EXPLODE_HEIGHT= 10;
	Explode(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public void draw(Graphics g){
		if(step==diameter.length || !bLive){
			bLive = false;
			step=0;
			return;
		}
		Color c = g.getColor();
		g.setColor(color);
		g.fillOval(x, y, diameter[step], diameter[step]);
		step++;
		g.setColor(c);
	}
	
	public boolean isLive(){
		return bLive;
	}
}
