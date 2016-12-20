import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * 这是主界面
 * @author 武纪怡
 *
 */
@SuppressWarnings("serial")
public class FirstFrame extends JFrame {
	
	private JLabel back;//背景
//	private JLabel label01, label02, label03, label04, label05, label06;
	private JButton button01, button02, button03;
	private FirstFrame f;
	
	public FirstFrame(int i) {
		super("水果连连看");
		//设置java图标
		setIconImage(Toolkit.getDefaultToolkit ().getImage(getClass().getResource("/images/icon.png")));
		
		setSize(650, 500);//设置窗体大小
		
		setLayout(null);//清除布局管理器
				
		showBackground();//设置背景
		if(i == 1) {
			LevelPanel level = new LevelPanel(this);
			level.setBounds(0,0,650,500);
			add(level);
			
			//监听关闭窗体按钮
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			//设置可视
			setVisible(true);
			//设置不可拉伸
			setResizable(false);
		} else {
			showButton();//显示界面
			
			adapter();//监听
		}
		
		f = this;
	}
	
	//监听
	private void adapter() {
		// TODO Auto-generated method stub
		
		button01.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				toLevel();
			}
		});
		
		button02.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Point p = StartMain.e1.getLocation();
				StartMain.e1.dispose();
				GameFrame gameFrame;
				gameFrame = new GameFrame(8,-1);
				//监听关闭窗体按钮
				gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				gameFrame.setLayout(null);//清除布局管理器
				
				//设置不可拉伸
				gameFrame.setResizable(false);
				gameFrame.setLocation(p);
			}
		});
		
		button03.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new Dialog(f,0,0,null);
			}
		});
		
	}
	//跳转到闯关页面
	public void toLevel() {
		Point p = this.getLocation();
		this.dispose();
		FirstFrame f = new FirstFrame(1);
		f.setLocation(p);
		
		
	}
	//显示界面	
	private void showButton() {
		// TODO Auto-generated method stub
		
		//设置按钮
		button01 = new JButton("经典模式");
		button01.setFont(new Font("acefont-family", Font.BOLD, 25));
		button01.setBackground(Color.RED);
		button01.setBounds(260, 200, 150, 40);

		button02 = new JButton("计时模式");
		button02.setFont(new Font("acefont-family", Font.BOLD, 25));
		button02.setBackground(Color.GREEN);
		button02.setBounds(260, 250, 150, 40);
		 
		button03 = new JButton("设置");
     	button03.setFont(new Font("acefont-family", Font.BOLD, 25));
     	button03.setBackground(Color.BLUE);
	    button03.setBounds(260, 300, 150, 40);
		
		 add(button01);
		 add(button02);
		 add(button03);
	}
	
	//设置背景
	private void showBackground() {
		// TODO Auto-generated method stub
		//背景图片
	 	ImageIcon background = new ImageIcon(getClass().getResource("/images/background.jpg"));
	  	//设置背景标签
        back = new JLabel(background);
        //设置背景图片位置大小
        back.setBounds(0, 0, getWidth(), getHeight());
        //面板透明
        JPanel j = (JPanel)getContentPane();
        j.setOpaque(false);
        //设置背景
        getLayeredPane().add(back, new Integer(Integer.MIN_VALUE));
	}

}
