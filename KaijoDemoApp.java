import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
//<applet code = "KaijoDemoApp.class" width = "200" height = "200">
//</applet>
public class KaijoDemoApp extends JApplet {
	static JTextArea area;
	public void init( ) {
		JPanel pane = new JPanel( ); //パネルを作成
		pane.setOpaque(true); //不透明にする
		// Swing パネルをアプレットのコンテントペインとして設定
		setContentPane(pane);
		//コンテントペインレイアウトの設定
		setLayout (new BorderLayout( ));
		NorthPanel panel = new NorthPanel( ); // NorthPanel を作成
		add(panel,"North"); //アプレットの北部に配置
		area = new JTextArea( ); //テキストエリアの作成
		area.setEditable(false); //テキストエリアは編集不可
		add(area,"Center");//テキストエリアをアプレットの中央部に配置
	}
	//アプリケーションとして実行できるようにする
	public static void main(String[ ] args) {
		JFrame frame = new JFrame( ); //フレームを作成
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(200,200);
		//アプレットを作成
		KaijoDemoApp applet = new KaijoDemoApp( );
		//フレームのコンテントペインにアプレットを追加
		frame.add(applet);
		applet.init( ); //アプレットを初期化
		applet.start( ); //アプレットを開始
		frame.setVisible(true); //ウィンドウを表示
	}
}
//アプレットのテキストフィールドおよびボタンを扱うクラス
class NorthPanel extends JPanel implements ActionListener {
	JTextField field;
	NorthPanel( ) {
		setLayout(new BorderLayout( ));
		field = new JTextField( ); //テキストフィールドの作成
		JButton button = new JButton("階乗"); //ボタンの作成
		//リスナとして NorthPanel クラス自身を設定する
		field.addActionListener(this);
		button.addActionListener(this);
		add(field, "Center"); //テキストフィールドをパネルに追加する
		add(button ,"East"); //ボタンをパネルに追加する
	}
	public void actionPerformed(ActionEvent event){
		int val = 0; long s = 1L; String input = "";
		input = field.getText( );
		//例外が発生しない時に階乗を求め、表示する
		if( inputVerifier(input) == true){
			val= Integer.parseInt( input );
			for(int i=2; i<= val; i++) { s = s*i; }
			//アプレットのテキストエリアのアップデート
			KaijoDemoApp.area.append(input + "の階乗は"+s+"です\n");
		}else KaijoDemoApp.area.append("入力は不適切である¥n");
		field.setText(""); //テキストフィールド空にする
	}
	boolean inputVerifier(String s) { //入力をチェック
		try{
			if ( Integer.parseInt(s) < 0 || Integer.parseInt(s) > 20 )
				return false;
			else return true;
		}
		catch(NumberFormatException e){ return false; }
	}
}