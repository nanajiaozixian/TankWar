import java.awt.*;

public class Missile {
	public static final int MISSILE_SPEED_X = 1;
	public static final int MISSILE_SPEED_Y = 1;
	public static final int MISSILE_WIDTH = 10;
	public static final int MISSILE_HEIGHT = 10;
	private boolean bLive = true; //×Óµ¯ÊÇ·ñ´æ»î
	int x, y;
	Tank.Direction dir;

	Missile(int x_in, int y_in, Tank.Direction dir){
		x = x_in - MISSILE_WIDTH/2;
		y = y_in - MISSILE_HEIGHT/2;
		this.dir = dir;
		
	}
	
	
	public void draw(Graphics g) {
		isOutorScreen();
		Color c = g.getColor();
		g.setColor(Color.black);
		g.fillOval(x, y, MISSILE_WIDTH, MISSILE_HEIGHT);
		move();
		g.setColor(c);
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

	public boolean isOutorScreen (){
		boolean bOut = false;
		if(x<0 || y<0 || x>TankClient.GAME_WIDTH || y>TankClient.GAME_HEIGHT){
			bOut = true;
			bLive = false;
		}
		return bOut;
	}
	
	public boolean isLive() {
		return bLive;
	}
	
	public Rectangle getRec(){
		return new Rectangle(x, y, MISSILE_WIDTH, MISSILE_HEIGHT);
	}
	
	public boolean hitTank(Tank t){
		boolean bHitted = false;
		if(this.getRec().intersects(t.getRec())){
			bHitted = true;
		}
		return bHitted;
	}
}
