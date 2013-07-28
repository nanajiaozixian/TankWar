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
	Tank enemyTank = null;
	List<Missile> missiles = new ArrayList<Missile>();
	Explode ex = null;
	
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
		myTank = new Tank(50,50,this,true);
		enemyTank = new Tank(80,80,this,false);
		ex = new Explode(100, 100);
		this.addKeyListener(new KeyAction());
		
		setVisible(true);
		
		new Thread(new PaintThread()).start();
	}
	
	public void paint(Graphics g){
		g.drawString("Missiles number: "+ missiles.size(), 10, 50);
		myTank.draw(g);
		if(enemyTank!=null){
			enemyTank.draw(g);	
		}
		Missile m = null;
		for (int i = 0; i < missiles.size(); i++) {
			m = missiles.get(i);
			if(!m.isLive()){
				missiles.remove(m);
			} else if (m != null) {
				m.draw(g);
				if (enemyTank != null) {
					if (m.hitTank(enemyTank)) {
						enemyTank = null;
						missiles.remove(m);
					}
				}
			}
		}
		ex.draw(g);
		
	}

	//¼õÉÙÉÁË¸
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
					Thread.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
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
}
