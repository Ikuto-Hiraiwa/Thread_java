import java.math.BigInteger;
import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*; 
import java.io.File;
import java.io.FileWriter;
import java.io.*;

public class BigPower2 {

	private static void createGUI( ) { //GUI を構築する関数
		JFrame frame = new JFrame("BigPower"); 
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		frame.add( new MyPane( ) );
		frame.setSize(400, 500); 
		frame.setResizable(false); 
		frame.setVisible(true);
	}
	public static void main(String args[ ]) {
		javax.swing.SwingUtilities.invokeLater(new Runnable( ) {
			public void run( ) {
				//イベントディスパッチスレッドに GUI の構築を要求
				createGUI( );
			}
		});
	}
}

class MyPane extends JPanel implements Runnable{
	//JTextArea は大量のデータを扱えないので、JEditorPane を利用
	JEditorPane area;
	JTextField textField1, textField2;
	Thread thread=null;
	JProgressBar progBar;
	int n,base,exponent;
	JButton startbutton, stopbutton , savebutton;
	MyPane( ){ //コンストラクタ
		setLayout( new BorderLayout( ) );
		area = new JEditorPane( ); 
		area.setEditable(false);
		JScrollPane jscrol = new JScrollPane(area); 
		jscrol.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS );
		add(jscrol,"Center");
		JPanel npanel = new JPanel( );
		npanel.setLayout(new BorderLayout( )); 
		add(npanel,"North"); 
		//進捗バーの作成と配置
		progBar = new JProgressBar(0,100); 
		npanel.add(progBar,"North");
		JPanel cpanel = new JPanel( ); 
		npanel.add(cpanel,"Center");
		cpanel.setLayout(new GridLayout(3, 2));
		// Base と書かれたラベルの作成と配置 
		cpanel.add(new JLabel(" Base"));
		// Exponent と書かれたラベルの作成と配置 
		cpanel.add(new JLabel(" Exponent")); //テキストフィールドの作成と配置 
		cpanel.add( textField1 = new JTextField( ) ); 
		cpanel.add( textField2 = new JTextField( ) );
		//ストップボタンの作成と配置
		cpanel.add( stopbutton = new JButton("stop") );
		stopbutton.addActionListener ( e ->{
			progBar.setStringPainted(false);
			//進捗バーを最小値に設定
			progBar.setValue(progBar.getMinimum( ));
			startbutton.setEnabled(true);
			setCursor(null);
			area.setText("");
			stopThread( );
		});
		//スタートボタンの作成と配置
		cpanel.add(startbutton=new JButton("start"));
		startbutton.addActionListener ( e -> {
			startbutton.setEnabled(false);
			area.setText("");
			setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			try{
				base= Integer.parseInt( textField1.getText( ) );
				exponent= Integer.parseInt( textField2.getText( ) );
				if ( base < 0 || exponent<0) throw new ArithmeticException("正の数を入力してください¥n");
				startThread( );
			}catch(NumberFormatException ex) {
				area.setText("入力は int ではない\n");
				startbutton.setEnabled(true);
				setCursor(null); //turn off the wait cursor
			}catch( ArithmeticException ex ){
				//テキストエリアのアップデート
				area.setText( ex.toString( ) );
				startbutton.setEnabled(true);
				setCursor(null);
			}
		});
		JPanel bpanel = new JPanel( ); 
		npanel.add(bpanel,"South");
		bpanel.setLayout(new GridLayout(2, 1));
		JLabel label = new JLabel("Not save");
		JPanel labelpanel = new JPanel();
		labelpanel.add(label);
		bpanel.add(savebutton = new JButton("save"));
		bpanel.add(labelpanel);
		savebutton.addActionListener ( e ->{
			JFileChooser filechooser = new JFileChooser(System.getProperty("user.dir"));
			int selected = filechooser.showSaveDialog(this);
			try{
			    File file = filechooser.getSelectedFile();
				while (true) {
					if (selected != JFileChooser.APPROVE_OPTION) {
						break;
					}
					if (file.exists()) {
						switch (JOptionPane.showConfirmDialog(this, 
								file.getName() + " は既に存在します。\n上書きしますか？",
								"上書き保存の確認", JOptionPane.YES_NO_CANCEL_OPTION)) {
							case JOptionPane.YES_OPTION:
								break;
							case JOptionPane.NO_OPTION:
								continue;
							case JOptionPane.CANCEL_OPTION:
								return;
						}
						break;
					} else {
						break;
					}					
				}
				if (selected == JFileChooser.APPROVE_OPTION) {
					FileWriter fw = new FileWriter(filechooser.getSelectedFile());
					fw.write(area.getText());
					label.setText(file.getName());
			   	}else if (selected == JFileChooser.CANCEL_OPTION){
			      label.setText("キャンセルされました");
			    }else if (selected == JFileChooser.ERROR_OPTION){
			      label.setText("エラー又は取消しがありました");
			    }
			}catch(Exception ex) {
				ex.printStackTrace();
			}
		});
	}

	void startThread ( ) {
		if(thread==null){
			thread = new Thread(this);
			thread.start( );
		}
	}
	void stopThread( ) {
		if(thread!=null){
			Thread t = thread;
			thread = null;
			t.interrupt( );
		}
	}
	public void run( ) {
		BigInteger big = BigInteger.valueOf((long)base);
		BigInteger bigResult = BigInteger.ONE;
		progBar.setStringPainted(true);
		n=exponent;
		while(n!=0){
			//繰り返し 2 乗法による計算
			if( (n & 1) ==1 ){
				bigResult=bigResult.multiply(big);
				n=n-1;
			}else{
				big = big.multiply(big);
				n = n/2;
				SwingUtilities.invokeLater ( ( ) -> {
					//進捗バーの更新
					progBar.setValue(100-100*n/exponent);
				});
			}
			try{ Thread.sleep(50); }
			catch( InterruptedException e ){ return; }
			if ( thread==null ) break;
		}
		try{
			SwingUtilities.invokeAndWait (( ) -> {
				stopbutton.setEnabled(false);
				//進捗バーを最大値に設定
				progBar.setValue(progBar.getMaximum( ));
			});
		}catch (Exception e) {
			System.err.println("GUI didn't successfully complete");
		}
		if(thread!=null){ //結果の表示
			area.setText("PRINTING"); 
			area.setText(bigResult.toString( ));
			SwingUtilities.invokeLater (( ) -> {
				setCursor(null); //turn off the wait cursor
				startbutton.setEnabled(true); 
				stopbutton.setEnabled(true); 
				Toolkit.getDefaultToolkit( ).beep( ); 
				progBar.setStringPainted(false); 
				progBar.setValue(progBar.getMinimum( ));
			});
			thread=null;
		}
	}
}




