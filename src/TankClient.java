import java.awt.*;
import java.awt.event.*;


public class TankClient extends Frame{

	public static final int GAME_WIDTH = 800;
	public static final int GAME_HEIGHT = 600;
	public static final Color COLOR_BACKGROUN = new Color(151,249,138);
	public static final Color COLOR_MYTANK = new Color(251,77,151);
	
	int x = 50;
	int y = 50;
	Image offScreenImage = null;
	Tank myTank = null;
	Missile m = null;
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
		myTank = new Tank(50,50,this);
		this.addKeyListener(new KeyAction());
		
		setVisible(true);
		
		new Thread(new PaintThread()).start();
	}
	
	public void paint(Graphics g){		
		myTank.draw(g);
		if(m!=null){
			m.draw(g);
		}
	}

	//ºı…Ÿ…¡À∏
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
