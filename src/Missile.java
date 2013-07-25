import java.awt.*;

public class Missile {
	public static final int MISSILE_SPEED_X = 10;
	public static final int MISSILE_SPEED_Y = 10;
	int x, y;
	Tank.Direction dir;
	Missile(int x_in, int y_in, Tank.Direction dir){
		x = x_in;
		y = y_in;
		this.dir = dir;
	}
	
	
	public void draw(Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.black);
		g.fillOval(x, y, 10, 10);
		move();
	}
	
	public void move(){
		switch(dir){
		case L:
			x-=MISSILE_SPEED_X;
			break;
		case LU:
			x-=MISSILE_SPEED_X;
			y-=MISSILE_SPEED_Y;
			break;
		case LD:
			x-=MISSILE_SPEED_X;
			y+=MISSILE_SPEED_Y;
			break;
		case R:
			x+=MISSILE_SPEED_X;
			break;
		case RU:
			x+=MISSILE_SPEED_X;
			y-=MISSILE_SPEED_Y;
			break;
		case RD:
			x+=MISSILE_SPEED_X;
			y+=MISSILE_SPEED_Y;
			break;
		case U:
			y-=MISSILE_SPEED_Y;
			break;
		case D:
			y+=MISSILE_SPEED_Y;
			break;
		default:
			break;
		}
	
	}
}
