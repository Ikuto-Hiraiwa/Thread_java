import javax.swing.*;
import javax.swing.text.*; 
import java.util.concurrent.*; 
import java.awt.*;
import java.util.*;
public class HeavyProcess{
	public static void main(String args[ ]) {
		JFrame frame = new JFrame("Heavy Process");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add( new HeavyProcessPane( ) );
		frame.setSize(400, 400); 
		frame.setResizable(false); 
		frame.setVisible(true);
	}
}
class HeavyProcessPane extends JPanel {
	JTextArea area;
	JTextField textField;
	JButton startbutton;
	HeavyProcessPane( ){ //コンストラクタ
		setLayout( new BorderLayout( ) );
		area = new JTextArea( ); 
		area.setEditable(false);
		JScrollPane jscrol = new JScrollPane(area);
		jscrol.setVerticalScrollBarPolicy( JScrollPane.VERTICAL_SCROLLBAR_ALWAYS );
		add(jscrol,"Center");
		JPanel cpanel = new JPanel( );
		add(cpanel,"North");
		cpanel.setLayout(new BorderLayout( ));
		cpanel.add( textField = new JTextField( ),"Center" ); 
		cpanel.add(startbutton=new JButton("start"),"East" );
		startbutton.addActionListener(( e) -> {
			startbutton.setEnabled(false); 
			area.setText("");
			try{
				Integer N= Integer.parseInt( textField.getText( ) );
				CompletableFuture<Integer> cfuture = CompletableFuture.supplyAsync(() -> HeavyTask(N));
				cfuture.thenAccept( result -> {
					SwingUtilities.invokeLater ( ( ) -> {
						area.setText(Integer.toString(result));
						startbutton.setEnabled(true);
					});
				});
			}catch(NumberFormatException ex) {
				area.setText("入力は int ではない¥n");
				startbutton.setEnabled(true);
			}
		});
	}
	private static Integer HeavyTask(Integer n) {
		try { 

			//ここにn番目の素数求めるプログラム入れろ
			
			TimeUnit.SECONDS.sleep(0);
		}catch (InterruptedException e) {
			e.printStackTrace();
		}
		return n;
	}
}

