import java.awt.*;
import java.awt.event.*;


public class TankClient extends Frame{

	int x = 50;
	int y = 50;
	Image offScreenImage = null;
	
	public static void main(String[] args) {
		
		TankClient tc = new TankClient();
		tc.launchFrame();

	}
	
	public void launchFrame(){
		setSize(800,600);
		setLocation(50,50);
		setResizable(false);
		setTitle("TankWar");
		setBackground(Color.green);
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				setVisible(false);
				System.exit(0);			
			}
		});
		
		setVisible(true);
		
		new Thread(new PaintThread()).start();
	}
	
	public void paint(Graphics g){		
		Color c = g.getColor();
		g.setColor(Color.red);
		g.fillOval(x, y, 30, 30);
		g.setColor(c);
		y+=0.1;
	}

	//ºı…Ÿ…¡À∏
	public void update(Graphics g){
		if(offScreenImage == null){
			offScreenImage = this.createImage(800, 600);
		}
		Graphics gOffScreen = offScreenImage.getGraphics();
		Color c = gOffScreen.getColor();
		gOffScreen.setColor(Color.GREEN);
		gOffScreen.fillRect(0, 0, 800, 600);
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
}
