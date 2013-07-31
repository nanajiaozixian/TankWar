import java.awt.*;
import java.util.List;

public class Missile {
	public static final int MISSILE_SPEED_X = 10;
	public static final int MISSILE_SPEED_Y = 10;
	public static final int MISSILE_WIDTH = 10;
	public static final int MISSILE_HEIGHT = 10;
	private boolean bLive = true; //×Óµ¯ÊÇ·ñ´æ»î
	int x, y;
	Tank.Direction dir;
	TankClient tc = null;
	private boolean bGood = false;
	public static final Color COLOR_GOOD = new Color(253, 139, 27);
	public static final Color COLOR_BAD = Color.black;
	

	Missile(int x_in, int y_in, boolean bGood_in, Tank.Direction dir, TankClient tc_in){
		x = x_in - MISSILE_WIDTH/2;
		y = y_in - MISSILE_HEIGHT/2;
		this.dir = dir;
		tc = tc_in;
		bGood = bGood_in;
	}
	
	
	public void draw(Graphics g) {
		if(!bLive){
			return;
		}
		isOutorScreen();
		
		Color c = g.getColor();
		Color cMissile = null;
		if(bGood){
			cMissile = COLOR_GOOD;
		}else{
			cMissile = COLOR_BAD;
		}
		g.setColor(cMissile);
		g.fillOval(x, y, MISSILE_WIDTH, MISSILE_HEIGHT);	
		g.setColor(c);
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
		return new Rectangle(x, y, MISSILE_WIDTH
				, MISSILE_HEIGHT);
	}
	
	public boolean hitTank(Tank t){
		boolean bHitted = false;
		if(this.getRec().intersects(t.getRec()) && this.bGood!= t.isGood()){
			Explode e = new Explode(x,y);
			tc.explodes.add(e);
			if(t.isGood()){
				int i = t.getLives();
				i--;
				t.setLives(i);
				if(i<=0){
					t.setLive(false);
				}
			}else{
				t.setLive(false);
			}
			bHitted = true;
			this.bLive = false;
		}
		return bHitted;
	}
	
	public boolean hitTanks(List<Tank> ts){
	
		Tank t = null;
		for(int i=0; i<ts.size(); i++){
			t = ts.get(i);
			if(t.isLive() && t!=null){
				if(hitTank(t)){
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean hitWall(Wall w){
		if(this.getRec().intersects(w.getRec())){
			/*this.x = iOldX;
			this.y = iOldY;*/
			tc.missiles.remove(this);
			this.bLive = false;
			return true;
		}
		return false;
	}
}
