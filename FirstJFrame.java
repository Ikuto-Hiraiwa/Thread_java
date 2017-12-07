import javax.swing.*;
public class FirstJFrame {
	public static void main(String[ ] args) {
		JFrame frame = new JFrame("myFirstApp");
		//ウィンドウを閉じたときの動作を設定する
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300,100); //フレームサイズを設定する
		JLabel label = new JLabel("私の初めてのアプリ"); //ラベルを作成
		//コンテントペインにラベルを追加する
		frame.add(label); //frame.getContentPane( ).add(label)と同様
		frame.setVisible(true); //ウィンドウを表示
	}
}
