import java.awt.event.*;
import javax.swing.*;
public class SwingThreadDemo extends JFrame implements ActionListener{
	JSlider slider ;
	int value=0;
	public SwingThreadDemo( ) {
		//範囲が 0 〜 100、初期値が 50 の水平スライダを作成
		slider = new JSlider( );
		slider.setMajorTickSpacing(10); //大目盛りの間隔を設定
		slider.setMinorTickSpacing(1); //小目盛りの間隔を設定
		slider.setPaintTicks(true); //目盛りをスライダで描画
		slider.setPaintLabels(true); // ラベルをスライダに描画
		add(slider, "Center");
		JButton button = new JButton("test");
		button.addActionListener(this); add(button, "South");
		//ウィンドウサイズをサブコンポーネントの推奨サイズ
		//およびレイアウトに合わせる
		pack( );
	}
	public void actionPerformed(ActionEvent e){
		HeavyProcess( ); //時間のかかる処理
		slider.setValue((int)(100*Math.random( ))); // スライダの更新
	}
	public static void main(String[ ] args) {
		SwingThreadDemo frame = new SwingThreadDemo( );
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true); //ウィンドウを表示
	}
	//長時間実行する処理をシミュレートする関数
	public void HeavyProcess( ){
		try{ Thread.sleep(10000); }
		catch(InterruptedException e){return;}
	}
}

