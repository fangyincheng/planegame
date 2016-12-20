package plane;

import java.net.URISyntaxException;

/**
 * 
 * @author 方银�?
 * 本类描述碰撞
 *
 */

public class Collide {
	
	private PlaySound p;
	
	/**
	 * 玩家飞机子弹与敌机碰撞方�?
	 * @param b
	 * @param e
	 */
	void bullet_enemy(Bullet b, Enemy e) {
		if(b.getX_Y().getX() >= e.getX_Y().getX()-GamePanel.BULLET_WIDTH
				&& b.getX_Y().getX() <= e.getX_Y().getX()+GamePanel.ENEMY_SIZE
				&& b.getX_Y().getY() >= e.getX_Y().getY()-GamePanel.BULLET_HEIGHT
				&& b.getX_Y().getY() <= e.getX_Y().getY()+GamePanel.ENEMY_SIZE) {
			b.stayed = false;
			e.stayed = false;
			if(PlaySound.b[2]) {
				//敌机爆炸声音
				p = new PlaySound();
				p.open("sounds/Break.wav");
				p.play();
				p.start();
			}
			
			GamePanel.sum += 100;//增加分数，每架飞�?100
		}
	}
	/**
	 * 玩家飞机与敌机碰撞方�?
	 * @param m
	 * @param e
	 */
	void plane_enemy(MyPlane m, Enemy e) {
		if(m.getX_Y().getX() >= e.getX_Y().getX()-GamePanel.PLANE_SIZE
				&& m.getX_Y().getX() <= e.getX_Y().getX()+GamePanel.ENEMY_SIZE
				&& m.getX_Y().getY() >= e.getX_Y().getY()-GamePanel.PLANE_SIZE
				&& m.getX_Y().getY() <= e.getX_Y().getY()+GamePanel.ENEMY_SIZE) {
			e.stayed = false;
			if(GamePanel.live <= 50) {
				m.stayed = false;
				if(PlaySound.b[3]) {
					//玩家飞机爆炸声音
					p = new PlaySound();
//					try {
//						p.open(getClass().getResource("/sounds/HeroBrustSound.wav").toURI().getPath());
						p.open("sounds/HeroBrustSound.wav");
						p.play();
						p.start();
//					} catch (URISyntaxException e1) {
//						// TODO Auto-generated catch block
//						e1.printStackTrace();
//					}
					
				}
				GamePanel.live = 0;
			} else
				GamePanel.live -= 50;
		}
	}
	/**
	 * 玩家飞机和敌机子弹碰�?
	 * @param b
	 * @param e
	 */
	void bullet_plane(Bullet b, MyPlane m) {
		if(b.getX_Y().getX() >= m.getX_Y().getX()
				&& b.getX_Y().getX() <= m.getX_Y().getX()+GamePanel.PLANE_SIZE-GamePanel.BULLET_WIDTH
				&& b.getX_Y().getY() >= m.getX_Y().getY()
				&& b.getX_Y().getY() <= m.getX_Y().getY()+GamePanel.PLANE_SIZE) {
			b.stayed = false;
			if(GamePanel.live <= 5) {
				m.stayed = false;
				if(PlaySound.b[3]) {
					//玩家飞机爆炸声音
					p = new PlaySound();
//					try {
//						p.open(getClass().getResource("/sounds/HeroBrustSound.wav").toURI().getPath());
						p.open("sounds/HeroBrustSound.wav");
						p.play();
						p.start();
//					} catch (URISyntaxException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
					
			    }
				GamePanel.live = 0;//飞机生命减少
			
			} else
				GamePanel.live -= 5;//飞机生命减少
		}
	}
	/**
	 * boss与玩家飞机碰�?
	 * @param b
	 * @param m
	 */
	void boss_plane(BossPlane b, MyPlane m) {
		if(b.getX_Y().getX() >= m.getX_Y().getX()-GamePanel.BOSS_WIDTH
				&& b.getX_Y().getX() <= m.getX_Y().getX()+GamePanel.PLANE_SIZE
				&& b.getX_Y().getY() >= m.getX_Y().getY()-GamePanel.BOSS_HEIGHT
				&& b.getX_Y().getY() <= m.getX_Y().getY()+GamePanel.PLANE_SIZE) {
			m.stayed = false;
			if(PlaySound.b[3]) {
				//玩家飞机爆炸声音
				p = new PlaySound();
//				try {
//					p.open(getClass().getResource("/sounds/HeroBrustSound.wav").toURI().getPath());
					p.open("sounds/HeroBrustSound.wav");
					p.play();
					p.start();
//				} catch (URISyntaxException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
				
		    }
			GamePanel.live = 0;
		}
	}
	/**
	 * 玩家飞机子弹与boss飞机碰撞
	 * @param b
	 * @param m
	 */
	void bullet_boss(Bullet b, BossPlane m) {
		if(b.getX_Y().getX() >= m.getX_Y().getX()
				&& b.getX_Y().getX() <= m.getX_Y().getX()+GamePanel.BOSS_WIDTH-GamePanel.BULLET_WIDTH
				&& b.getX_Y().getY() >= m.getX_Y().getY()
				&& b.getX_Y().getY() <= m.getX_Y().getY()+GamePanel.BOSS_HEIGHT 
				&& m.getX_Y().getY() >= 0) {
			b.stayed = false;
			if(GamePanel.live1 <= 1) {
				m.stayed = false;
				if(PlaySound.b[3]) {
					//玩家飞机爆炸声音
					p = new PlaySound();
//					try {
//						p.open(getClass().getResource("/sounds/HeroBrustSound.wav").toURI().getPath());
						p.open("sounds/HeroBrustSound.wav");
						p.play();
						p.start();
//					} catch (URISyntaxException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
					
			    }
				GamePanel.live1 = 0;//boss飞机生命减少
				GamePanel.sum += 500;//分数�?500
			
			} else
				GamePanel.live1 -= 1;//boss飞机生命减少
		}
	}
	
}
