import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class Dialog extends JDialog{
	
	private JLabel sounds, about, pass_level;
	private JButton to_main, to_next, to_new;
	private JCheckBox jcb_1, jcb_2;
	private JTextArea jta;
	private int LEVEL;//关数

	public Dialog(JFrame f,int i, int LEVEL, String s) {
		super(f, true);
		this.LEVEL = LEVEL;
		setLayout(null);//设置布局管理器为空
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		setResizable(false);//设置对话框不可拉
		if(i == 0)
			showSetting(f);//显示设置对话框
		else if(i == 1)
			showOver(f);
		else if(i == 2)
			showTimerOver(f,s);
		
		setVisible(true);//设置对话框显示
	}
	
	private void showTimerOver(final JFrame f, String s) {
		setTitle("计时模式");
		setBounds(f.getBounds().x+125, f.getBounds().y+150, 400, 200);//设置对话框位置大小
		//恭喜过关标签
		pass_level = new JLabel(s);
		pass_level.setFont(new Font("acefont-family", Font.BOLD, 40));
		pass_level.setBounds(110, 30, 200, 40);
		add(pass_level);
		
		//返回主菜单按钮
		to_main = new JButton("返回主菜单");
		to_main.setBounds(50, 120, 100, 30);
		add(to_main);
		
		to_main.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Point p = f.getLocation();
				f.dispose();
				StartMain.main(null);
				StartMain.e1.setLocation(p);
			}
		});
		
		//下一关按钮
		to_new = new JButton("重新开始");
		to_new.setBounds(250, 120, 100, 30);
		add(to_new);
		
		to_new.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Point p = f.getLocation();
				f.dispose();
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
	}

	private void showOver(final JFrame f) {
		setTitle("过关");
		setBounds(f.getBounds().x+125, f.getBounds().y+150, 400, 200);//设置对话框位置大小
		
		//恭喜过关标签
		pass_level = new JLabel("恭喜过关");
		pass_level.setFont(new Font("acefont-family", Font.BOLD, 40));
		pass_level.setBounds(110, 30, 200, 40);
		add(pass_level);
		
		//返回主菜单按钮
		to_main = new JButton("返回主菜单");
		to_main.setBounds(50, 120, 100, 30);
		add(to_main);
		
		to_main.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Point p = f.getLocation();
				f.dispose();
				StartMain.main(null);
				StartMain.e1.setLocation(p);
			}
		});
		
		//下一关按钮
		to_next = new JButton("下一关");
		to_next.setBounds(250, 120, 100, 30);
		if(LEVEL == 20)
			to_next.setEnabled(false);
		add(to_next);
		
		to_next.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Point p = f.getLocation();
				f.dispose();
				GameFrame gameFrame;
				gameFrame = new GameFrame(LEVEL>2?8:(int) Math.pow(2, LEVEL+1),LEVEL+1);
				//监听关闭窗体按钮
				gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				gameFrame.setLayout(null);//清除布局管理器
				
				//设置不可拉伸
				gameFrame.setResizable(false);
				gameFrame.setLocation(p);
			}
		});
	}

	private void showSetting(JFrame f) {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setTitle("设置");
		setBounds(f.getBounds().x+75, f.getBounds().y+75, 500, 350);//设置对话框位置大小
		
		//音效标签
		sounds = new JLabel("音效:");
		sounds.setFont(new Font("acefont-family", Font.BOLD, 15));
		sounds.setBounds(10, 10, 50, 20);
		add(sounds);
		
		//关于作者标签
		about = new JLabel("关于作者:");
		about.setFont(new Font("acefont-family", Font.BOLD, 15));
		about.setBounds(10, 75, 100, 20);
		add(about);
		
		//音效选项多选按钮
		jcb_1 = new JCheckBox("背景声音");
		jcb_1.setBounds(20, 40, 80, 20);
		if(PlaySound.b[0])
			jcb_1.setSelected(true);
		add(jcb_1);
		jcb_1.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				PlaySound.b[0] = !PlaySound.b[0];
				if(!PlaySound.b[0])
				{
					StartMain.p.stop();
				}
				else
					StartMain.p.start();			
				}
		});
		
		jcb_2 = new JCheckBox("消除音");
		jcb_2.setBounds(120, 40, 80, 20);
		if(PlaySound.b[1])
			jcb_2.setSelected(true);
		add(jcb_2);
		jcb_2.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				PlaySound.b[1] = !PlaySound.b[1];
			}
		});
		
		//关于作者内容
		jta = new JTextArea();
		jta.setBounds(20, 110, 300, 150);
		jta.setEditable(false);
		jta.setText("  武纪怡\n  15级软件工程3班");
		add(jta);
	}

}
