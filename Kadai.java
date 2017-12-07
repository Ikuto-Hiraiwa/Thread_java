import java.awt.*;
import javax.swing.*;
import java.awt.geom.*;
import java.awt.event.*;

public class Kadai {
	public static void main(String[ ] args) {
		JFrame frame = new JFrame("Kadai");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600,400);
		MyJPanel panel = new MyJPanel( ); //パネルを作成
		//コンテントペインにパネルを追加する
		frame.add(panel); //frame.getContentPane( ).add(panel)と同様
		frame.setVisible(true); //ウィンドウを表示
	}
}



//DragRectangle2D.java の内容を記述する
class MyJPanel extends JPanel {
	Rectangle2D.Double rect;
	final int w=100, h=100;
	int pressedX, pressedY;
	boolean mouseOn=true;
	public MyJPanel( ){ //パネルのコンストラクタ
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

