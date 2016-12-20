import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


/**
 * 这是闯关界面
 * @author 武纪怡
 *
 */
 
@SuppressWarnings("serial")
public class LevelPanel extends JPanel {
	
	private JPanel level0, level;
	JFrame f;
	
	public LevelPanel(JFrame f) {
		setLayout(null);
		setOpaque(false);
		
		showReturn();//返回按钮
		
		showLevel();//关数按钮
		this.f = f;
	}
	
	private void showReturn() {
		level0 = new JPanel();
		level0.setLayout(new GridLayout(1,10));
		level0.setBounds(25,30,100,40);
		level0.setOpaque(false);
		add(level0);
		//返回按钮
		JButton jb = new JButton("返回主菜单");
		jb.setBackground(Color.cyan);
		level0.add(jb);
		
		jb.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Point p = f.getLocation();
				f.dispose();
				StartMain.main(null);
				StartMain.e1.setLocation(p);
			}
		});
	}
	
	private void showLevel() {
		//关数按钮
		level = new JPanel();
		level.setLayout(new GridLayout(4, 5,20,20));
		level.setBounds(70, 130, 500, 200);
		JButton bt[] = new JButton[20];
		for(int i = 0;i<20;i++) {
			final int t = i;
			bt[i] = new JButton("第"+(i+1)+"关");
			bt[i].setBackground(new Color(239-i*6,223-i*6,39+i*9));
			level.add(bt[i]);
			bt[i].addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					Point p = f.getLocation();
					f.dispose();
					GameFrame gameFrame;
					gameFrame = new GameFrame(t>2?8:(int) Math.pow(2, t+1),t+1);
					//监听关闭窗体按钮
					gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					gameFrame.setLayout(null);//清除布局管理器
					
					//设置不可拉伸
					gameFrame.setResizable(false);
					gameFrame.setLocation(p);
				}
			});
		}
		level.setOpaque(false);
		add(level);
	}

}
