import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;


@SuppressWarnings("serial")
public class GameFrame extends JFrame {
	
	private JLabel back;
	private GameFrame g;
	private JButton cancel, restart;//返回按钮
	private int LEVEL;//关数
	private int GameSize;//布局大小即行列数
	private JProgressBar jpb;//时间进度条
	private Timer timer; //计时器

	public GameFrame(int GameSize,int t) {
		super(t==-1?"计时模式":"第"+t+"关");
		//设置java图标
		setIconImage(Toolkit.getDefaultToolkit ().getImage(getClass().getResource("/images/icon.png")));
		LEVEL = t;
		this.GameSize = GameSize+2;
		setSize(700, 600);//设置窗体大小
		ImageIcon background = new ImageIcon(getClass().getResource("/images/background.png"));
	  	 //设置背景标签
		back = new JLabel(background);
        //设置背景图片位置大小
		back.setBounds(0, 0, getWidth(), getHeight());
        //面板透明
		JPanel j = (JPanel)getContentPane();
		j.setOpaque(false);
        //设置背景
		getLayeredPane().add(back, new Integer(Integer.MIN_VALUE));
		setVisible(true);
		
		showMenu();
		if(t == -1)
			showTime();
		//添加游戏面板
		GamePanel jpanel = new GamePanel();
		add(jpanel);
		
		g = this;
		
	}
	
	/*
	 * 添加定时设置
	 */
	private void showTime() {
		jpb = new JProgressBar();
		jpb.setOrientation(JProgressBar.HORIZONTAL);
		jpb.setMinimum(0);//设置进度条最小值
		jpb.setMaximum(100);//设置进度条最大值
		jpb.setValue(0);//设置进度条当前值
		jpb.setBackground(new Color(238,226,29));
		jpb.setBounds(175, 25, 350, 12);
		add(jpb);
		
		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				jpb.setValue(jpb.getValue()+1);
				if(jpb.getValue() > 80)
					jpb.setForeground(Color.RED);
				if(jpb.getValue() == 100) {
					timer.cancel();
					new Dialog(g, 2, LEVEL,"闯关失败");
				}
			}
		}, 0, 900);
		
	}
	
	/*
	 * 添加返回按钮
	 */
	private void showMenu() {
		//LEVEL==-1?"返回主菜单":"返回选关"
		cancel = new JButton();
		cancel.setBounds(10, 10, 60, 40);
		cancel.setIcon(new ImageIcon(getClass().getResource("/images/home.png")));
		add(cancel);
		
		restart = new JButton();
		restart.setBounds(10, 60, 60, 40);
		restart.setIcon(new ImageIcon(getClass().getResource("/images/restart.png")));
		add(restart);
		
		cancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Point p = g.getLocation();
				g.dispose();
				StartMain.main(null);
				StartMain.e1.setLocation(p);
				if(LEVEL != -1)
					StartMain.e1.toLevel();
				else
					timer.cancel();
			}
		});
		
		restart.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Point p = g.getLocation();
				g.dispose();
				GameFrame gameFrame;
				gameFrame = new GameFrame(GameSize-2,LEVEL);
				//监听关闭窗体按钮
				gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				gameFrame.setLayout(null);//清除布局管理器
				
				//设置不可拉伸
				gameFrame.setResizable(false);
				gameFrame.setLocation(p);
				if(timer != null)
					timer.cancel();
			}
		});
	}
	
	class GamePanel extends JPanel implements MouseListener {
		
		private int W = 50;                  //动物方块图案的宽度
	    
	    private Icon icon[];         //水果图片数组
	    private Icon icon_line[];     //连线图片数组
	    @SuppressWarnings("rawtypes")
		private ArrayList images_t;        //水果图片过渡地图
	    @SuppressWarnings("rawtypes")
		private ArrayList label_arr;     //label数组
	    private int[] path_line;		//消除路径对应图片数组
	    
	    private int index=-1;			//记录有边框的label
	    @SuppressWarnings("unused")
		private Point p_index;			//记录有边框的label
	    private int k;                  //记录第二个点中方块
	    
	    private int sum;               //记录剩下的方块
	    @SuppressWarnings("rawtypes")
		private ArrayList path;      //记录消除路径
	    
	    private int can;            //是否消除标志位
	    
		public GamePanel() {
			// TODO Auto-generated constructor stub
			setLayout(new GridLayout(GameSize, GameSize));//网格布局
			setBounds((700-GameSize*W)/2,(600-GameSize*W)/2,GameSize*W,GameSize*W);
			setOpaque(false);
			initMap();//初始化地图
			showGame();//显示游戏面板
		}
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		private void showGame() {
			// TODO Auto-generated method stub
			label_arr = new ArrayList();
			
			//显示水果图片
			for(int i=0;i<GameSize*GameSize;i++) {
				if(i%GameSize==0 || i%GameSize==GameSize-1 || i/GameSize==0 || i/GameSize==GameSize-1) {
					JLabel j = new JLabel();
					j.setIcon(null);
					label_arr.add(j);
					add(j);
					continue;
				}
				int nIndex = new Random().nextInt(images_t.size());
				
				JLabel j = new JLabel(icon[(int) images_t.get(nIndex)]);
				label_arr.add(j);
				j.addMouseListener(this);
				add(j);
				
				images_t.remove(nIndex);
			}
		}

		@SuppressWarnings({ "unchecked", "rawtypes" })
		private void initMap() {
			// TODO Auto-generated method stub
			images_t = new ArrayList();
			path = new ArrayList();
			icon = new Icon[10];
			icon_line = new Icon[6];
			path_line = new int[GameSize*GameSize];
			
			for(int i=0;i<icon.length;i++) {
				icon[i] = new ImageIcon(getClass().getResource("/images/"+"fruit_"+(i+1)+".jpg"));
			}
			for(int i=0;i<icon_line.length;i++) {
				icon_line[i] = new ImageIcon(getClass().getResource("/images/"+"line_"+(i+1)+".png"));
			}
			for(int i=0;images_t.size()<(GameSize-2)*(GameSize-2);i++) {
				images_t.add(i%10);
				if(images_t.size()==(GameSize-2)*(GameSize-2)) {
					continue;
				}
				images_t.add(i%10);
			}
			sum = images_t.size();
			
		}
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		private boolean isOK(Point p) {
			
			k = p.x/W+p.y/W*GameSize;
			
			if(index == k)
				return false;
			if(((JLabel)label_arr.get(index)).getIcon()!=((JLabel)label_arr.get(k)).getIcon())
				return false;
			
			//消除判断
			int index_top=index, index_left=index, index_right=index, index_bottum=index;//第一个方块四个方向最长延伸
			int k_top=k, k_left=k, k_right=k, k_bottum=k;//第二个方块四个方向最长延伸
			
			for(int i=index-GameSize;i>0;i-=GameSize) {
				if(((JLabel)label_arr.get(i)).getIcon() != null) {
					index_top = i+GameSize;
					break;
				}
				if(i < GameSize)
					index_top = i;
			}
			for(int i=index-1;i>index/GameSize*GameSize-1;i--) {
				if(((JLabel)label_arr.get(i)).getIcon() != null) {
					index_left = i+1;
					break;
				}
				if(i == index/GameSize*GameSize)
					index_left = index/GameSize*GameSize;
			}
			for(int i=index+1;i<(index/GameSize+1)*GameSize;i++) {
				if(((JLabel)label_arr.get(i)).getIcon() != null) {
					index_right = i-1;
					break;
				}
				if(i == (index/GameSize+1)*GameSize-1)
					index_right = (index/GameSize+1)*GameSize-1;
			}
			for(int i=index+GameSize;i<GameSize*GameSize;i+=GameSize) {
				if(((JLabel)label_arr.get(i)).getIcon() != null) {
					index_bottum = i-GameSize;
					break;
				}
				if(i > GameSize*GameSize-1-GameSize)
					index_bottum = i;
			}
			for(int i=k-GameSize;i>0;i-=GameSize) {
				if(((JLabel)label_arr.get(i)).getIcon() != null) {
					k_top = i+GameSize;
					break;
				}
				if(i < GameSize)
					k_top = i;
			}
			for(int i=k-1;i>k/GameSize*GameSize-1;i--) {
				if(((JLabel)label_arr.get(i)).getIcon() != null) {
					k_left = i+1;
					break;
				}
				if(i == k/GameSize*GameSize)
					k_left = k/GameSize*GameSize;
				
			}
			for(int i=k+1;i<(k/GameSize+1)*GameSize;i++) {
				if(((JLabel)label_arr.get(i)).getIcon() != null) {
					k_right = i-1;
					break;
				}
				if(i == (k/GameSize+1)*GameSize-1)
					k_right = (k/GameSize+1)*GameSize-1;
			}
			for(int i=k+GameSize;i<GameSize*GameSize;i+=GameSize) {
				if(((JLabel)label_arr.get(i)).getIcon() != null) {
					k_bottum = i-GameSize;
					break;
				}
				if(i > GameSize*GameSize-1-GameSize)
					k_bottum = i;
			}
			
			//水平方向
			if(index/GameSize > k/GameSize) {  //第一个在第二个下方
				int i=index, p1=index+1, f=0;
				while(true) {
					if(i<index_left && p1>index_right)
						break;
					ArrayList arr = new ArrayList();
					if(f == 0) {
						if(i>=index_left && i%GameSize>=k_left%GameSize && i%GameSize<=k_right%GameSize) {
							for(int t = i;t <= index;t++) {
								arr.add(t);
								path_line[t] = 0;
							}
							path_line[i] = 3;

							for(int j = i-GameSize;j>0;j-=GameSize) {
								System.out.println("aaal");
								arr.add(j);
								path_line[j] = 1;
								if(j/GameSize == k/GameSize) {
									for(int t = 0;t < arr.size();t++) {
										path.add(arr.get(t));
									}
									if(j<k) {
										for(int t = j+1;t <= k;t++) {
											path.add(t);
											path_line[t] = 0;
										}
										path_line[j] = 4;
									} else {
										for(int t = j-1;t >= k;t--) {
											path.add(t);
											path_line[t] = 0;
										}
										path_line[j] = 5;
									}
									return true;
								}
								if(((JLabel)label_arr.get(j)).getIcon() != null) {
									arr.clear();
									break;
								}
							}
						}
						i--;
						f = 1;
					} else {
						if(p1<=index_right && p1%GameSize>=k_left%GameSize && p1%GameSize<=k_right%GameSize) {
							for(int t = p1;t >= index;t--) {
								arr.add(t);
								path_line[t] = 0;
							}
							path_line[p1] = 2;		  
							for(int j = p1-GameSize;j>0;j-=GameSize) {
								System.out.println("aaar");
								arr.add(j);
								path_line[j] = 1;
								if(j/GameSize == k/GameSize) {
									for(int t = 0;t < arr.size();t++) {
										path.add(arr.get(t));
									}
									if(j<k) {
										for(int t = j+1;t <= k;t++) {
											path.add(t);
											path_line[t] = 0;
										}
										path_line[j] = 4;
									} else {
										for(int t = j-1;t >= k;t--) {
											path.add(t);
											path_line[t] = 0;
										}
										path_line[j] = 5;
									}
									return true;
								}
								if(((JLabel)label_arr.get(j)).getIcon() != null) {
									arr.clear();
									break;
								}
							}
						}
						p1++;
						f = 0;
					}
				}
			} else if(index/GameSize < k/GameSize) {	  //第二个在第一个下方
				int i=index,p1=index+1,f=0;
				while(true) {
					if(i<index_left && p1>index_right)
						break;
					ArrayList arr = new ArrayList();
					if(f == 0) {
						if(i>=index_left && i%GameSize>=k_left%GameSize && i%GameSize<=k_right%GameSize) {
							for(int t = i;t <= index;t++) {
								arr.add(t);
								path_line[t] = 0;
							}
							path_line[i] = 4;
							for(int j = i+GameSize;j>0;j+=GameSize) {
								System.out.println("bbbl");
								arr.add(j);
								path_line[j] = 1;
								if(j/GameSize == k/GameSize) {
									for(int t = 0;t < arr.size();t++) {
										path.add(arr.get(t));
									}
									if(j<k) {
										for(int t = j+1;t <= k;t++) {
											path.add(t);
											path_line[t] = 0;
										}
										path_line[j] = 3;
									} else {
										for(int t = j-1;t >= k;t--) {
											path.add(t);
											path_line[t] = 0;
										}
										path_line[j] = 2;
									}
									return true;
								}
								if(((JLabel)label_arr.get(j)).getIcon() != null) {
									arr.clear();
									break;
								}
							}
						}
						i--;
						f = 1;
					} else {
						if(p1<=index_right && p1%GameSize>=k_left%GameSize && p1%GameSize<=k_right%GameSize) {
							for(int t = p1;t >= index;t--) {
								arr.add(t);
								path_line[t] = 0;
							}
							path_line[p1] = 5;
							for(int j = p1+GameSize;j>0;j+=GameSize) {
								System.out.println("bbbr");
								arr.add(j);
								path_line[j] = 1;
								if(j/GameSize == k/GameSize) {
									for(int t = 0;t < arr.size();t++) {
									path.add(arr.get(t));
									}
									if(j<k) {
										for(int t = j+1;t <= k;t++) {
											path.add(t);
											path_line[t] = 0;
										}
										path_line[j] = 3;
									} else {
										for(int t = j-1;t >= k;t--) {
											path.add(t);
											path_line[t] = 0;
										}
										path_line[j] = 2;
									}
									return true;
								}
								if(((JLabel)label_arr.get(j)).getIcon() != null) {
									arr.clear();
									break;
								}
							}
						}
						p1++;
						f = 0;
					}
				}
			}
			//垂直方向
			if(index%GameSize < k%GameSize) {		   //第一个在第二个左方
				int i=index, p1=index+GameSize, f=0;
				while(true) {
					if(i<index_top && p1>index_bottum)
						break;
					ArrayList arr = new ArrayList();
					if(f == 0) {
						if(i>=index_top && i/GameSize>=k_top/GameSize && i/GameSize<=k_bottum/GameSize) {
							for(int t = i;t <= index;t+=GameSize) {
									arr.add(t);
									path_line[t] = 1;
								}
							path_line[i] = 4;
							for(int j = i+1;j<(i/GameSize+1)*GameSize-1;j++) {
								System.out.println("ccct");
								arr.add(j);
								path_line[j] = 0;
								if(j%GameSize == k%GameSize) {
									for(int t = 0;t < arr.size();t++) {
										path.add(arr.get(t));
									}
									if(j<k) {
										for(int t = j+GameSize;t <= k;t+=GameSize) {
											path.add(t);
											path_line[t] =1;
										}
										path_line[j] = 5;
									} else {
										for(int t = j-GameSize;t >= k;t-=GameSize) {
											path.add(t);
											path_line[t] =1;
										}
										path_line[j] = 2;
									}
									return true;
								}
								if(((JLabel)label_arr.get(j)).getIcon() != null) {
									arr.clear();
									break;
								}
							}
						}
						i-=GameSize;
						f = 1;
					} else {
						if(p1<=index_bottum && p1/GameSize>=k_top/GameSize && p1/GameSize<=k_bottum/GameSize) {
							for(int t = p1;t >= index;t-=GameSize) {
									arr.add(t);
									path_line[t] = 1;
								}
							path_line[p1] = 3;
							for(int j = p1+1;j<(p1/GameSize+1)*GameSize-1;j++) {
								System.out.println("cccb");
								arr.add(j);
								path_line[j] = 0;
								if(j%GameSize == k%GameSize) {
									for(int t = 0;t < arr.size();t++) {
										path.add(arr.get(t));
									}
									if(j<k) {
										for(int t = j+GameSize;t <= k;t+=GameSize) {
											path.add(t);
											path_line[t] =1;
										}
										path_line[j] = 5;
									} else {
										for(int t = j-GameSize;t >= k;t-=GameSize) {
											path.add(t);
											path_line[t] =1;
										}
										path_line[j] = 2;
									}
									return true;
								}
								if(((JLabel)label_arr.get(j)).getIcon() != null) {
									arr.clear();
									break;
								}
							}
						}
						p1+=GameSize;
						f = 0;
					}
				}
			} else if(index%GameSize > k%GameSize) {	   //第一个在第二个右方
				int i=index, p1=index+GameSize, f=0;
				while(true) {
					if(i<index_top && p1>index_bottum)
						break;
					ArrayList arr = new ArrayList();
					if(f == 0) {
						if(i>=index_top && i/GameSize>=k_top/GameSize && i/GameSize<=k_bottum/GameSize) {
							for(int t = i;t <= index;t+=GameSize) {
								arr.add(t);
								path_line[t] = 1;
							}
							path_line[i] = 5;
							for(int j = i-1;j>i/GameSize*GameSize;j--) {
								System.out.println("dddt");
								arr.add(j);
								path_line[j] = 0;
								if(j%GameSize == k%GameSize) {
									for(int t = 0;t < arr.size();t++) {
										path.add(arr.get(t));
									}
									if(j<k) {
										for(int t = j+GameSize;t <= k;t+=GameSize) {
											path.add(t);
											path_line[t] =1;
										}
										path_line[j] = 4;
									} else {
										for(int t = j-GameSize;t >= k;t-=GameSize) {
											path.add(t);
											path_line[t] =1;
										}
										path_line[j] = 3;
									}
									return true;
								}
								if(((JLabel)label_arr.get(j)).getIcon() != null) {
									arr.clear();
									break;
								}
							}
						}
						i-=GameSize;
						f = 1;
					} else {
						if(p1<=index_bottum && p1/GameSize>=k_top/GameSize && p1/GameSize<=k_bottum/GameSize) {
							for(int t = p1;t >= index;t-=GameSize) {
								arr.add(t);
								path_line[t] = 1;
							}
							path_line[p1] = 2;
							for(int j = p1-1;j>p1/GameSize*GameSize;j--) {
								System.out.println("dddb");
								arr.add(j);
								path_line[j] = 0;
								if(j%GameSize == k%GameSize) {
									for(int t = 0;t < arr.size();t++) {
										path.add(arr.get(t));
									}
									if(j<k) {
										for(int t = j+GameSize;t <= k;t+=GameSize) {
											path.add(t);
											path_line[t] =1;
										}
										path_line[j] = 4;
									} else {
										for(int t = j-GameSize;t >= k;t-=GameSize) {
											path.add(t);
											path_line[t] =1;
										}
										path_line[j] = 3;
									}
									return true;
								}
								if(((JLabel)label_arr.get(j)).getIcon() != null) {
									arr.clear();
									break;
								}
							}
						}
						p1+=GameSize;
						f = 0;
					}
				}
			}
			
			return false;
		}
		

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			JLabel j = (JLabel)e.getComponent();//获取点击的label
			Point p = j.getLocation(); //获取位置
			if(j.getIcon()!=null)
				if(index != -1) {
					((JLabel)label_arr.get(index)).setBorder(null);//取消上一个边框
					if(!isOK(p)) {
						j.setBorder(BorderFactory.createLineBorder(Color.RED));  //点击出现边框
						//记录位置
						p_index = p;
						index = p.x/W+p.y/W*GameSize;
					} else {
						//路径
						for(int i = 0;i < path.size();i++) {
							System.out.print(path.get(i)+" a ");
							if((int)path.get(i) == index || (int)path.get(i) == k)
								continue;
							((JLabel)label_arr.get((int)path.get(i))).setIcon(icon_line[path_line[(int) path.get(i)]]);
						}
						can = 1;
					}
				} else {
					j.setBorder(BorderFactory.createLineBorder(Color.RED));  //点击出现边框
					//记录位置
					p_index = p;
					index = p.x/W+p.y/W*GameSize;
				}
		}

		@SuppressWarnings("static-access")
		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			if(can == 1) {
				JLabel j = (JLabel)e.getComponent();//获取点击的label
				//当前暂停1秒
				try {
					Thread.currentThread().sleep(500);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				//清除路径
				for(int i = 0;i < path.size();i++) {
					((JLabel)label_arr.get((int)path.get(i))).setIcon(null);
				}
				path.clear();//路径数组复位
				//消除
				((JLabel)label_arr.get(index)).setIcon(null);
				j.setIcon(null);
				
				//消除音乐
				if(PlaySound.b[1]) {
					PlaySound ps = new PlaySound();
					ps.open("sounds/ClearSound.wav");
					ps.play();
					ps.start();
				}
				can = 0;
				
				sum -= 2;
				
				if(sum == 0) {
					if(LEVEL == -1) {
						timer.cancel();
						new Dialog(g,2,LEVEL,"闯关成功");
						
					} else
						new Dialog(g,1,LEVEL,null);
				}
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
}
