import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.geom.*;
//<applet code="SwingDragRectangle2D.class" width="600" height= "400" >
//</applet>
public class SwingDragRectangle2D extends JApplet {
	@Override
	public void init( ){
		//コンテントペインとしてパネルを設置
		setContentPane(new SwingPanel( ));
	}
}

//DragRectangle2D.java の内容を記述する
class SwingPanel extends JPanel {
	Rectangle2D.Double rect;
	final int w=100, h=100;
	int pressedX, pressedY;
	boolean mouseOn=true;
	public SwingPanel( ){ //パネルのコンストラクタ
		//四角形のオブジェクトを作成
		rect = new Rectangle2D.Double(0, 0, w, h);
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent m) {
				Point p=m.getPoint();
				if(rect.contains(p.x,p.y)){
					pressedX = (int)rect.x - p.x;
					pressedY = (int)rect.y - p.y;
					mouseOn = true;
				}else { mouseOn = false;}
			}
		});
		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent m) {
				if(mouseOn){updateLocation(m);}
			}
		});
	}
	public void updateLocation(MouseEvent m){
		Point p=m.getPoint();
		rect.x = pressedX + p.x; rect.y = pressedY + p.y;
		repaint( );
	}
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		g2.setPaint(Color.red);
		g2.fill(rect);
	}
}




