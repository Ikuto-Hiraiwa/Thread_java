import java.awt.*;
import javax.swing.*;
public class JFrameDemo {
	public static void main(String[ ] args) {
		JFrame frame = new JFrame("FrameDemo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(200,100);
		MyJPanel panel = new MyJPanel( ); //パネルを作成
		//コンテントペインにパネルを追加する
		frame.add(panel); //frame.getContentPane( ).add(panel)と同様
		frame.setVisible(true); //ウィンドウを表示
	}
}

class MyJPanel extends JPanel{
	public void paintComponent(Graphics g) {
		//スーパークラスの paintComponent を呼び出して
		//コンポーネントの土台(今回はパネルの背景)を描画
		super.paintComponent(g);
		//文字列を表示
		g.drawString("Hello World", 50, 30);
	}
}
