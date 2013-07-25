import java.awt.*;
import java.awt.event.*;


public class Tank {
	private int x, y;
	private Color color;
	private boolean bL=false, bR=false, bU=false, bD=false;
	enum Direction {L, LU, LD, R, RU, RD, U, D, STOP};
	private Direction dir = Direction.STOP;
	public static final int TANK_SPEED_X = 5;
	public static final int TANK_SPEED_Y = 5;
	private TankClient tc = null;
	Tank(int x_in, int y_in, TankClient tc_in){
		x = x_in;
		y = y_in;
		tc = tc_in;
		color = TankClient.COLOR_MYTANK;
	}
	
	public void draw(Graphics g){
		Color c = g.getColor();
		g.setColor(color);
		g.fillOval(x, y, 30, 30);
		g.setColor(c);
	}
	
	public void move(){
		switch(dir){
		case L:
			x-=TANK_SPEED_X;
			break;
		case LU:
			x-=TANK_SPEED_X;
			y-=TANK_SPEED_Y;
			break;
		case LD:
			x-=TANK_SPEED_X;
			y+=TANK_SPEED_Y;
			break;
		case R:
			x+=TANK_SPEED_X;
			break;
		case RU:
			x+=TANK_SPEED_X;
			y-=TANK_SPEED_Y;
			break;
		case RD:
			x+=TANK_SPEED_X;
			y+=TANK_SPEED_Y;
			break;
		case U:
			y-=TANK_SPEED_Y;
			break;
		case D:
			y+=TANK_SPEED_Y;
			break;
		default:
			break;
		}
	}
	
	public void keyPressed(KeyEvent e) {
		int keycode = e.getKeyCode();
		switch(keycode){
		case KeyEvent.VK_KP_DOWN:
		case KeyEvent.VK_DOWN :
			bD =true;
			break;
		case KeyEvent.VK_KP_UP:
		case KeyEvent.VK_UP :
			bU=true;
			break;
		case KeyEvent.VK_KP_LEFT:
		case KeyEvent.VK_LEFT :
			bL=true;
			break;
		case KeyEvent.VK_KP_RIGHT:
		case KeyEvent.VK_RIGHT :
			bR=true;
			break;
		default:
			break;
		}
		
		locateDirection();
	}
	
	public void keyReleased(KeyEvent e){
		int keycode = e.getKeyCode();
		switch(keycode){
		case KeyEvent.VK_CONTROL:
			tc.m = fire();
			break;
		case KeyEvent.VK_KP_DOWN:
		case KeyEvent.VK_DOWN :
			bD =false;
			break;
		case KeyEvent.VK_KP_UP:
		case KeyEvent.VK_UP :
			bU=false;
			break;
		case KeyEvent.VK_KP_LEFT:
		case KeyEvent.VK_LEFT :
			bL=false;
			break;
		case KeyEvent.VK_KP_RIGHT:
		case KeyEvent.VK_RIGHT :
			bR=false;
			break;
		default:
			break;
		}
	}
	
	public void locateDirection(){
		if(bL && !bR && !bU && !bD){
			dir = Direction.L;
		}else if(bR && !bL && !bU && !bD){
			dir = Direction.R;
		}
		else if(bU && !bL && !bR && !bD){
			dir = Direction.U;
		}
		else if(bD && !bL && !bR && !bU){
			dir = Direction.D;
		}
		else if(bR && bU && !bL && !bD){
			dir = Direction.RU;
		}
		else if(bR && bD && !bU && !bL){
			dir = Direction.RD;
		}
		else if(bL && bU && !bR && !bD){
			dir = Direction.LU;
		}
		else if(bL && bD && !bU && !bR){
			dir = Direction.LD;
		}
		else{
			dir = Direction.STOP;
		}
		
		move();
	}

	public Missile fire(){
		Missile m = new Missile(x, y, dir);
		return m;
	}
}
