package com.example.gameplayer.view;

import java.util.Random;
import java.util.Vector;

import com.example.gameplayer.R;
import com.example.gameplayer.contrlloer.Boom;
import com.example.gameplayer.contrlloer.Bullet;
import com.example.gameplayer.contrlloer.Enemy;
import com.example.gameplayer.contrlloer.GameBg;
import com.example.gameplayer.contrlloer.GameMenu;
import com.example.gameplayer.contrlloer.GamePlayer;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

public class FlyGameSurfaceView extends SurfaceView implements Callback {
	private static final String TAG = FlyGameSurfaceView.class.getSimpleName();
	private SurfaceHolder sfh;
	private Canvas canvas;
	private Paint paint;
	private Thread th;
	private boolean flag;
	/**
	 * 定义常量
	 * */
	private static final int GAME_MENU = 0;// 游戏菜单
	public static final int GAMEING = 1;// 游戏中
	private static final int GAME_WIN = 2;// 游戏胜利
	private static final int GAME_LOST = 3;// 游戏失败
	private static final int GAME_PAUSE = -1;// 游戏菜单
	public static int screenW;
	public static int screenH;
	
	// 当前游戏状态
	public static int GAME_STATE = GAME_MENU;

	// 声明一个Resources实例
	private Resources res = this.getResources();

	private Bitmap bmBackGround;// 游戏背景图片
	private Bitmap bmBoom;// 爆炸效果
	private Bitmap bmBoosBoom;// boos爆炸
	private Bitmap bmButton;// 开始按钮
	private Bitmap bmButtonPress;// 开始按钮被点击
	private Bitmap bmEnemy1;// 怪物
	private Bitmap bmEnemy2;// 怪物
	private Bitmap bmEnemy3;// 怪物
	private Bitmap bmGameWin;// 胜利
	private Bitmap bmGameLost;// 失败
	private Bitmap bmPlay;// 游戏主角
	private Bitmap bmMenu;// 菜单背景
	private Bitmap bmBullet;// 子弹
	private Bitmap bmEnemyBullet;// 敌人子弹
	private Bitmap bmBossBullet;// boss子弹
	// 声明容器
	private Vector<Enemy> vcEnemy;
	// 每次生成敌机的时间（毫秒）
	private int createEnemyTime = 50;
	private int count;// 计算器
	// 声明二维数组，每一组都是怪物
	private int enemyArray[][] = { { 1, 2 }, { 1, 1 }, { 1, 3, 1, 2 },
			{ 1, 2 }, { 2, 3 }, { 3, 1, 3 }, { 2, 2 }, { 1, 2 }, { 2, 2 },
			{ 1, 3, 1, 1 }, { 2, 1 }, { 1, 3 }, { 2, 1 }, { -1 } };
	// 取出当前数组的下标
	private int enemyArrayIndex;
	// 是否出现boos标识
	private boolean isBoos;
	// 随机
	private Random random;
	// 声明菜单对象
	private GameMenu menu;
	// 滚动背景图片
	private GameBg backGround;
	// 主角
	private GamePlayer player;
	/**
	 * Bullet 绘制子弹
	 * */
	private Vector<Bullet> vcBullet = new Vector<>();
	// 添加子弹计数
	private int countEnemyBullet, countPlayerBullet;
	private Vector<Bullet> vcBulletPlayer = new Vector<>();
	/**
	 * Boom 爆炸效果
	 * */
	private Vector<Boom> vcBoom = new Vector<>();

	public FlyGameSurfaceView(Context context) {
		super(context);
		// 初始化SurfaceHolder
		sfh = getHolder();
		// SurfaceHolder添加监听
		sfh.addCallback(this);
		paint = new Paint();
		paint.setColor(Color.WHITE);
		
		initGame();
		// 设置焦点
		setFocusable(true);
		//设置触屏
		setFocusableInTouchMode(true);
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {

	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		screenW = this.getWidth();
		screenH = this.getHeight();
		flag = true;
		th = new Thread() {
			@Override
			public void run() {
				while (flag) {
					long start = System.currentTimeMillis();
					myDraw();
					logic();
					long end = System.currentTimeMillis();
					if (end - start < 50) {
						try {
							Thread.sleep(50 - (end - start));
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
				super.run();
			}
		};
		th.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		flag = false;
	}

	/**
	 * 按下事件
	 * */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		/*
		// 处理back事件
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// 默认返回菜单
			if (GAME_STATE == GAMEING || GAME_STATE == GAME_WIN
					|| GAME_STATE == GAME_LOST) {
				GAME_STATE = GAME_MENU;
				isBoos = false;
				// 重置游戏
				initGame();
				// 重置怪物
				enemyArrayIndex = 0;
			} else if (GAME_STATE == GAME_MENU) {
				System.exit(0);
			}
			return true;
		}
		*/
		switch (GAME_STATE) {
		case GAME_MENU:// 菜单

			break;
		case GAMEING:// 游戏中
			// 按下
			player.onKeyDown(keyCode, event);
			break;
		case GAME_WIN:// 游戏胜利

			break;
		case GAME_LOST:// 游戏失败

			break;
		case GAME_PAUSE:// 游戏菜单

			break;
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * 抬起事件
	 * */
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		switch (GAME_STATE) {
		case GAME_MENU:// 菜单

			break;
		case GAMEING:// 游戏中
			// 抬起
			player.onKeyUp(keyCode, event);
			break;
		case GAME_WIN:// 游戏胜利

			break;
		case GAME_LOST:// 游戏失败

			break;
		case GAME_PAUSE:// 游戏菜单

			break;
		}
		return super.onKeyUp(keyCode, event);
	}

	/**
	 * 滑动事件
	 * */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (GAME_STATE) {
		case GAME_MENU:// 菜单
			menu.onTouchEvent(event);
			break;
		case GAMEING:// 游戏中
			if(event.getAction()==MotionEvent.ACTION_MOVE){
				player.onTouchEvent(event);
			}
			break;
		case GAME_WIN:// 游戏胜利

			break;
		case GAME_LOST:// 游戏失败

			break;
		case GAME_PAUSE:// 游戏菜单

			break;
		}
		return true;
	}

	private void logic() {
		switch (GAME_STATE) {
		case GAME_MENU:// 菜单

			break;
		case GAMEING:// 游戏中
			// 背景逻辑
			backGround.logic();
			// 主角逻辑
			player.logic();
			if (isBoos == false) {
				// 添加子弹
				countEnemyBullet++;
				if (countEnemyBullet % 30 == 0) {
					for (int i = 0; i < vcEnemy.size(); i++) {
						Enemy en = vcEnemy.elementAt(i);
						// 不同类型的敌机不同子弹的轨迹
						int bulletType = 0;
						switch (en.type) {
						case Enemy.TYPE_BOOM1:
							bulletType = Bullet.BULLET_BOOM1;
							break;
						case Enemy.TYPE_BOOM2:
							bulletType = Bullet.BULLET_BOOM2;
							break;
						}
						vcBullet.add(new Bullet(bmEnemyBullet, en.x + 10,
								en.y + 20, bulletType));
					}
				}
				// 处理敌机的子弹
				for (int i = 0; i < vcBullet.size(); i++) {
					Bullet b = vcBullet.elementAt(i);
					if (b.isDead) {
						vcBullet.removeElement(b);
					} else {
						b.logic();
					}
				}
			} else {
				// boss
			}
			// 添加主角子弹
			countPlayerBullet++;
			if (countPlayerBullet % 15 == 0) {
				vcBulletPlayer.add(new Bullet(bmBullet, player.x + 15,
						player.y - 20, Bullet.BULLET_PLAYER));
			}
			// 处理主角逻辑子弹
			for (int i = 0; i < vcBulletPlayer.size(); i++) {
				Bullet b = vcBulletPlayer.elementAt(i);
				if (b.isDead) {
					vcBulletPlayer.removeElement(b);
				} else {
					b.logic();
				}
			}
			for (int i = 0; i < vcBullet.size(); i++) {
				if (player.isCollsionWith(vcBullet.elementAt(i))) {
					// 游戏失败
					GAME_STATE = GAME_LOST;
				}
			}
			// 主角子弹与敌机相撞
			for (int i = 0; i < vcBulletPlayer.size(); i++) {
				// 取出主角子弹容器的每个元素
				Bullet blPlayer = vcBulletPlayer.elementAt(i);
				for (int j = 0; j < vcEnemy.size(); j++) {
					if (vcEnemy.elementAt(j).isCollsionWith(blPlayer)) {
						// 爆炸效果
						vcBoom.add(new Boom(bmBoom, vcEnemy.elementAt(j).x,
								vcEnemy.elementAt(j).y, 7));
					}
				}
			}
			for (int i = 0; i < vcBoom.size(); i++) {
				Boom boom = vcBoom.elementAt(i);
				if (boom.playEnd) {
					// 删除
					vcBoom.removeElementAt(i);
					
				} else {
					vcBoom.elementAt(i).logic();
				}
			}
			break;
		case GAME_WIN:// 游戏胜利

			break;
		case GAME_LOST:// 游戏失败

			break;
		case GAME_PAUSE:// 游戏菜单

			break;
		}
	}

	private void initGame() {
		// 当游戏处于菜单状态时，才能进入游戏
		if (GAME_STATE == GAME_MENU) {
			// 加载资源
			bmBackGround = BitmapFactory.decodeResource(getResources(),
					R.drawable.background);
			bmBoom = BitmapFactory.decodeResource(getResources(),
					R.drawable.boom);
			bmBoosBoom = BitmapFactory.decodeResource(getResources(),
					R.drawable.boosboom);
			bmButton = BitmapFactory.decodeResource(getResources(),
					R.drawable.start);
			bmButtonPress = BitmapFactory.decodeResource(getResources(),
					R.drawable.click);
			bmEnemy1 = BitmapFactory.decodeResource(getResources(),
					R.drawable.enemybullet);
			bmEnemy2 = BitmapFactory.decodeResource(getResources(),
					R.drawable.blz1);
			bmEnemy3 = BitmapFactory.decodeResource(getResources(),
					R.drawable.blz_en44);
			bmGameWin = BitmapFactory.decodeResource(getResources(),
					R.drawable.win);
			bmGameLost = BitmapFactory.decodeResource(getResources(),
					R.drawable.over);
			bmPlay = BitmapFactory.decodeResource(getResources(),
					R.drawable.blz_en41);
			bmMenu = BitmapFactory.decodeResource(getResources(),
					R.drawable.utyu);
			bmBullet = BitmapFactory.decodeResource(getResources(),
					R.drawable.enemybullet);
			bmEnemyBullet = BitmapFactory.decodeResource(getResources(),
					R.drawable.enemy);
			bmBossBullet = BitmapFactory.decodeResource(getResources(),
					R.drawable.laser);
			// 实例化
			menu = new GameMenu(bmMenu, bmButton, bmButtonPress);
			// 背景
			backGround = new GameBg(bmBackGround);
			// 主角
			player = new GamePlayer(bmPlay);
			// 实例化容器
			vcEnemy = new Vector<>();
			// 随机数
			random = new Random();
		}
	}

	private void myDraw() {
		try {
			canvas = sfh.lockCanvas();
			if (null != canvas) {
				switch (GAME_STATE) {
				case GAME_MENU:// 菜单
					menu.draw(canvas, paint);
					break;
				case GAMEING:// 游戏中
					// 背景
					backGround.draw(canvas, paint);
					// 主角
					player.draw(canvas, paint);
					if (isBoos == false) {
						// 绘制敌机
						for (int i = 0; i < vcEnemy.size(); i++) {
							vcEnemy.elementAt(i).draw(canvas, paint);
							Enemy en = vcEnemy.elementAt(i);
							if (en.isDead) {
								vcEnemy.removeElementAt(i);
							} else {
								en.logic();
							}
						}
						// 生成敌机
						count++;
						if (count % createEnemyTime == 0) {
							for (int i = 0; i < enemyArray[enemyArrayIndex].length; i++) {
								// 敌机左
								if (enemyArray[enemyArrayIndex][i] == 2) {
									int y = random.nextInt(20);
									vcEnemy.add(new Enemy(bmEnemy2, 2, -50, y));
								} else if (enemyArray[enemyArrayIndex][i] == 3) {// 敌机右
									int y = random.nextInt(20);
									vcEnemy.add(new Enemy(bmEnemy3, 2, -50, y));
								}
							}
							// 判断是否是最后一组数组
							if (enemyArrayIndex == enemyArray.length - 1) {
								isBoos = true;
							} else {
								enemyArrayIndex++;
							}
						}
						// 绘制敌机子弹
						for (int i = 0; i < vcBullet.size(); i++) {
							vcBullet.elementAt(i).draw(canvas, paint);
						}
					} else {
						// 绘制Boos
					}
					// 绘制主角子弹
					for (int i = 0; i < vcBulletPlayer.size(); i++) {
						vcBulletPlayer.elementAt(i).draw(canvas, paint);
					}
					// 爆炸效果绘制
					for (int i = 0; i < vcBoom.size(); i++) {
						vcBoom.elementAt(i).draw(canvas, paint);
					}
					break;
				case GAME_WIN:// 游戏胜利
					canvas.drawBitmap(bmGameWin, 0, 0, paint);
					break;
				case GAME_LOST:// 游戏失败
					canvas.drawBitmap(bmGameLost, 0, 0, paint);
					break;
				case GAME_PAUSE:// 游戏菜单

					break;
				}
			}
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
			e.printStackTrace();
		} finally {
			if (null != canvas)
				sfh.unlockCanvasAndPost(canvas);
		}
	}
}
