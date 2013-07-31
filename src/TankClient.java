import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;


public class TankClient extends Frame{

	private static final long serialVersionUID = 1L;
	public static final int GAME_WIDTH = 800;
	public static final int GAME_HEIGHT = 600;
	public static final Color COLOR_BACKGROUN = new Color(151,249,138);	
	int x = 50;
	int y = 50;
	Image offScreenImage = null;
	Tank myTank = null;
	List<Tank> enemyTanks = new ArrayList<Tank>();
	List<Missile> missiles = new ArrayList<Missile>();
	List<Explode> explodes = new ArrayList<Explode>();
	public static final int ENEMY_NUMBER_INIT = 20;
	public static final int ENEMY_NUMBER = 50;
	public Wall w1 = null, w2 = null;
	public Blood blood = null;
	public int enemyNumber = 0;
	
	public static void main(String[] args) {
		
		TankClient tc = new TankClient();
		tc.launchFrame();

	}
	
	public void launchFrame(){
		setSize(GAME_WIDTH, GAME_HEIGHT);
		setLocation(50,50);
		setResizable(false);
		setTitle("TankWar");
		setBackground(COLOR_BACKGROUN);
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				setVisible(false);
				System.exit(0);			
			}
		});
		playNewGame();
		new Thread(new PaintThread()).start();
	}
	
	public void paint(Graphics g){
		Color c = g.getColor();
		
		if(enemyTanks.size()==0 && enemyNumber == ENEMY_NUMBER){
			g.setColor(Color.red);
			g.drawString("Winner!!!!!! ", 350, 350);
			g.drawString("Press F10 for Play New Game! ", 100, 50);
		}else if(!myTank.isLive()){
			g.setColor(Color.red);
			g.drawString("Loser!!!!!! ", 350, 350);
			g.drawString("Press F10 for Play New Game! ", 100, 50);
		}
		g.drawString("EnemyTanks Number: "+ enemyTanks.size(), 650, 50);
		
		//myTank.hitOtherTanks(enemyTanks);
		myTank.draw(g);
		Missile m=null;
		for (int i = 0; i < missiles.size(); i++) {
			m = missiles.get(i);
			if(!m.isLive()){
				missiles.remove(m);
			} else if (m != null) {
				m.draw(g);	
				m.hitTanks(enemyTanks);
				m.hitWall(w1);
				m.hitWall(w2);
				if(myTank.isLive()){
					m.hitTank(myTank);
				}
							
			}
		}
		Tank enemy =null;
		for(int i=0; i<enemyTanks.size(); i++){
			enemy = enemyTanks.get(i);
			if(!enemy.isLive()){
				enemyTanks.remove(enemy);
			}else if(enemy!=null){
				//enemy.hitOtherTanks(enemyTanks);
				enemy.draw(g);	
			}
		}
		
		
		//draw explode
		Explode e=null;
		for(int i=0; i<explodes.size(); i++){
			e = explodes.get(i);
			if(!e.isLive()){
				explodes.remove(e);
			}else if(e != null){
				e.draw(g);
			}
		}
		
		//draw wall
		w1.draw(g);
		w2.draw(g);
		blood.draw(g);
		
		//添加新的敌军
		if(enemyTanks.size()<5 && enemyNumber<ENEMY_NUMBER){
			for(int i=0; i<5; i++){
				enemyTanks.add(new Tank(150*i, 30, this, false));
			}
			enemyNumber = enemyNumber+5;
		}
		g.setColor(c);
	}

	//减少闪烁
	public void update(Graphics g){
		if(offScreenImage == null){
			offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);
		}
		Graphics gOffScreen = offScreenImage.getGraphics();
		Color c = gOffScreen.getColor();
		gOffScreen.setColor(COLOR_BACKGROUN);
		gOffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
		gOffScreen.setColor(c);
		paint(gOffScreen);
		g.drawImage(offScreenImage, 0, 0, null);
	}
	
	private class PaintThread implements Runnable {

		@Override
		public void run() {
			while(true){
				repaint();
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		}
		
	}


	private class KeyAction extends KeyAdapter {
		public void keyPressed(KeyEvent e){
			myTank.keyPressed(e);
		}
		
		public void keyReleased(KeyEvent e){
			myTank.keyReleased(e);
		}
	}
	
	public void playNewGame(){
		myTank = null;
		myTank = new Tank(50,50,this,true);
		enemyTanks.clear();
		// 添加多辆敌人坦克
		for(int i=0; i<ENEMY_NUMBER_INIT; i++){
			Tank t = new Tank(50*i,50*(i+2),this,false);
			enemyTanks.add(t);
		}
		enemyNumber =  ENEMY_NUMBER_INIT;
		this.addKeyListener(new KeyAction());
		w1 = new Wall(200, 300, 20, 200);
		w2 = new Wall(400, 200, 200, 20);
		blood = new Blood();
		setVisible(true);
		
	}
}
