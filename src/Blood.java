import java.awt.*;

public class Blood {
	private int x=0, y=0;
	private static final int BLOOD_WIDTH = 8;
	private static final int BLOOD_HEIGHT= 8;
	private static final Color COLOR_BLOOD = new Color(237, 252, 2);
	private Point[] positions = new Point[STEP];
	private static final int STEP= 1000;
	private int iStep = 0;
	private boolean bLive = true;
	private static final int SLEEP_TIME = 1000;
	private int iSleepTime = 0;
	Blood(){
		int px=0, py=0;
		for (int i = 0; i <STEP; i++) {
			px = i;
			/*
			 * y=Asin(wt+α)   A幅值 W=角频率 α=初相位角 t=自变量
			 */
			//py = (int)(300 + Math.sin(Math.toRadians(i)));
			py = ((Double)( 300+200*Math.sin( Math.toRadians(i) )) ).intValue();
			positions[i] = new Point(px, py);
		}
	}
	
	public void draw(Graphics g){
		if(!bLive){
			iSleepTime++;
			if(iSleepTime==SLEEP_TIME){
				bLive = true;
				iStep = 0;
			}
			return;
		}
		Color c = g.getColor();
		g.setColor(COLOR_BLOOD);
		if(iStep>=STEP || iStep<0){
			iStep = 0;
		}	
		this.x = positions[iStep].x;
		this.y = positions[iStep].y;
		g.fillRect(this.x, this.y, BLOOD_WIDTH, BLOOD_HEIGHT);
		iStep++;
		g.setColor(c);
	}
	
	public boolean isLive(){
		return bLive;
	}
	
	public void setLive(boolean b){
		bLive = b;
	}
	
	public Rectangle getRec(){
		return new Rectangle(x, y, BLOOD_WIDTH, BLOOD_HEIGHT);
	}
}
