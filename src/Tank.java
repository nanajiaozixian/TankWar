import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;


public class Tank {
	private int x, y;
	private Color color;
	private Point pTankCen = null;
	private boolean bL=false, bR=false, bU=false, bD=false;
	enum Direction {L, LU, LD, R, RU, RD, U, D, STOP};
	private Direction dir = Direction.STOP;
	private Direction fireDir = Direction.D;
	public static final int TANK_SPEED_X = 5;
	public static final int TANK_SPEED_Y = 5;
	public static final int TANK_WIDTH = 30;
	public static final int TANK_HEIGHT = 30;
	private TankClient tc = null;
	private boolean bGood = false;
	public static final Color COLOR_MYTANK = new Color(209,87,240);
	public static final Color COLOR_ENEMYTANK = new Color(60,132,230);
	public static final Color COLOR_LIVE = new Color(243,102,118);
	private boolean bLive = true;
	private Random rd = new Random();//随机数
	private Direction[] dirs = Direction.values();
	public static final int RANDOME_TIME = 2;
	private int t = 0;
	private int iOldX, iOldY;
	public static final int MYTANK_LIVES = 5;
	private int iLives = MYTANK_LIVES;
	int iNum=0;
	
	Tank(int x_in, int y_in, TankClient tc_in, boolean b){
		x = x_in;
		y = y_in;
		tc = tc_in;
		bGood = b;
		if(!bGood){
			dir = Direction.D;
		}
	}
	
	
	public void draw(Graphics g){
		if(!bLive){
			/*if(bGood){
				g.drawString("Press F12 for Relive!", 300, 50);
			}*/
			return;
		}
		if(bGood){
			color = COLOR_MYTANK;
		}else{
			color = COLOR_ENEMYTANK;
					
		}
		
		Color c = g.getColor();
		g.setColor(color);
		g.fillOval(x, y, TANK_WIDTH, TANK_HEIGHT);
		drawFireLine(g);
		g.setColor(c);
		move();
		hitWall(tc.w1);
		hitWall(tc.w2);
		//hitOtherTanks(tc.enemyTanks);
		if(bGood){
			drawBlood(g);
			eatBlood(tc.blood);
		}
	}
	
	public void move(){
		iOldX = x;
		iOldY = y;
		if (!bGood) {
			
			if (t > RANDOME_TIME) {
				int i = rd.nextInt(dirs.length);
				dir = dirs[i];
				if(dir!=Direction.STOP){
					fireDir = dir;
				}
				t = 0;
			}
			t++;
			
			if((rd.nextInt(40))>38){
				this.fire(fireDir);
			}
		} 
		switch (dir) {
		case L:
			x -= TANK_SPEED_X;
			break;
		case LU:
			x -= TANK_SPEED_X;
			y -= TANK_SPEED_Y;
			break;
		case LD:
			x -= TANK_SPEED_X;
			y += TANK_SPEED_Y;
			break;
		case R:
			x += TANK_SPEED_X;
			break;
		case RU:
			x += TANK_SPEED_X;
			y -= TANK_SPEED_Y;
			break;
		case RD:
			x += TANK_SPEED_X;
			y += TANK_SPEED_Y;
			break;
		case U:
			y -= TANK_SPEED_Y;
			break;
		case D:
			y += TANK_SPEED_Y;
			break;
		default:
			break;
		}

		if (x < 0) {
			x = 0;
		}
		if (y < 30) {
			y = 30;
		}
		if (x > TankClient.GAME_WIDTH - TANK_WIDTH) {
			x = TankClient.GAME_WIDTH - TANK_WIDTH;
		}
		if (y > TankClient.GAME_HEIGHT - TANK_HEIGHT) {
			y = TankClient.GAME_HEIGHT - TANK_HEIGHT;
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
		iNum++;
System.out.println("key: "+ iNum);
		locateDirection();
	}
	
	public void keyReleased(KeyEvent e){
		int keycode = e.getKeyCode();
		switch(keycode){
		case KeyEvent.VK_CONTROL:
			fire(fireDir);
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
		case KeyEvent.VK_A :
			superFire();
			break;
		case KeyEvent.VK_F10:
			tc.playNewGame();
			break;
		/*case KeyEvent.VK_F12:
			if(!bLive && bGood){
				bLive = true;
				iLives = MYTANK_LIVES;
				tc.enemyNumber = TankClient.ENEMY_NUMBER_INIT;
			}
			break;
			*/
		default:
			break;
		}
		dir=Direction.STOP;
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
		
		if(dir!=Direction.STOP){
			fireDir = dir;
		}
		
		move();
	}

	public Missile fire(Direction d){
		if(!bLive){
			return null;
		}
		pTankCen = tankCenter();
		Missile m = new Missile(pTankCen.x, pTankCen.y, this.bGood, d, this.tc);
		tc.missiles.add(m);
		return m;
	}
	
	public Point tankCenter() {
		int px=0, py=0;
		px = this.x + TANK_WIDTH/2;
		py = this.y + TANK_HEIGHT/2;
		return new Point(px, py);
	}
	
	public void drawFireLine(Graphics g) {
		//计算直线的两点
		pTankCen = tankCenter();
		int x1 = pTankCen.x;
		int y1 = pTankCen.y;
		int x2=0, y2=0;
		switch(fireDir){
		case L:
			x2 = x1 - TANK_WIDTH/2;
			y2 = y1;
			break;
		case LU:
			x2 = x1 - TANK_WIDTH/2;
			y2 = y1 - TANK_HEIGHT/2;
			break;
		case LD:
			x2 = x1 - TANK_WIDTH/2;
			y2 = y1 + TANK_HEIGHT/2;
			break;
		case R:
			x2 = x1 + TANK_WIDTH/2;
			y2 = y1;
			break;
		case RU:
			x2 = x1 + TANK_WIDTH/2;
			y2 = y1 - TANK_HEIGHT/2;
			break;
		case RD:
			x2 = x1 + TANK_WIDTH/2;
			y2 = y1 + TANK_HEIGHT/2;
			break;
		case U:
			x2 = x1;
			y2 = y1 - TANK_HEIGHT/2;
			break;
		case D:
			x2 = x1;
			y2 = y1 + TANK_HEIGHT/2;
			break;
		default:
			x2 = x1;
			y2 = y1 + TANK_HEIGHT/2;
			break;
		}
		g.setColor(Color.black);
		g.drawLine(x1, y1, x2, y2);
	}
	
	public Rectangle getRec(){
		return new Rectangle(x, y, TANK_WIDTH, TANK_HEIGHT);
	}
	
	public boolean isLive(){
		return bLive;
	}
	public void setLive(boolean b){
		bLive = b;
	}
	
	public boolean isGood(){
		return bGood;
	}
	
	public boolean hitWall(Wall w){
		if(this.getRec().intersects(w.getRec())){
			this.x = iOldX;
			this.y = iOldY;
			return true;
		}
		return false;
	}
	
	public boolean hitOtherTanks(List<Tank> ts){
		
		if(!this.bLive){
			return false;
		}
		Tank t = null;
		for(int i =0; i<ts.size(); i++){
			t = ts.get(i);
			if (t != this && t!=null) {
				if (this.getRec().intersects(t.getRec())) {
					this.dir = t.dir = Direction.STOP;
					return true;
				}
			}
		}
		return false;
	}
	
	public void superFire(){
		Direction[] dirs= Direction.values();
		for(int i=0; i<dirs.length; i++){
			if(dirs[i]!=Direction.STOP){
				fire(dirs[i]);
			}
		}
	}
	
	public void setLives(int iValue){
		iLives = iValue;
	}
	public int getLives(){
		return iLives;
	}
	
	public void drawBlood(Graphics g){
		Color c = g.getColor();
		g.setColor(COLOR_LIVE);
		g.drawRect(x, y-12, 5*MYTANK_LIVES, 10);
		
		g.fillRect(x, y-12, 5*iLives, 10);
		g.setColor(c);
	}
	
	public boolean eatBlood(Blood bd){
		if(bd.isLive()){
			if(this.getRec().intersects(bd.getRec())){
				this.iLives = MYTANK_LIVES;
				bd.setLive(false);
				return true;
			}
		}
		return false;
	}
}
