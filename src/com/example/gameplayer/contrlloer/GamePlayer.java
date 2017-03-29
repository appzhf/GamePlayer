package com.example.gameplayer.contrlloer;


import com.example.gameplayer.view.FlyGameSurfaceView;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.KeyEvent;
import android.view.MotionEvent;

public class GamePlayer {
	// 坐标及位图
	public int x, y;
	private Bitmap bmPlayer;
	// 移动速度
	private int speed = 5;
	// 标识
	private boolean isUp, isDown, isLeft, isRight;
	// 计时器
	private int noCollisionCount = 0;
	//无敌时间
	private int noCollisionTime=60;
	// 判断是否撞击
	private boolean isCollision;

	public GamePlayer(Bitmap bmPlayer) {
		this.bmPlayer = bmPlayer;
		x = FlyGameSurfaceView.screenW / 2 - bmPlayer.getWidth() / 2;
		y = FlyGameSurfaceView.screenH - bmPlayer.getHeight();
	}

	// 绘制
	public void draw(Canvas canvas, Paint paint) {
		//处于无敌状态
		if(isCollision){
			//每两次游戏循环，绘制一次主角
			if(noCollisionCount % 2 ==0){
				canvas.drawBitmap(bmPlayer, x, y, paint);
			}
		} else {
			canvas.drawBitmap(bmPlayer, x, y, paint);
		}
	}

	/**
	 * 自定义onKeyDown
	 * */
	public void onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
			isUp = true;
		} else if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
			isDown = true;
		} else if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
			isLeft = true;
		} else if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
			isRight = true;
		}
	}

	/**
	 * 自定义onKeyUp
	 * */
	public void onKeyUp(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
			isUp = false;
		} else if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
			isDown = false;
		} else if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
			isLeft = false;
		} else if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
			isRight = false;
		}
	}
	/**
	 * 自定义触屏事件
	 * */
	public boolean onTouchEvent(MotionEvent event) {
		x = (int) event.getX();
		y = (int) event.getY();
		return true;
	}
	/**
	 * 主要的游戏逻辑
	 * */
	public void logic() {
		if (isLeft) {
			x -= speed;
		}
		if (isRight) {
			x += speed;
		}
		if (isUp) {
			y -= speed;
		}
		if (isDown) {
			y += speed;
		}
		// 判断屏幕x边界
		if (x + bmPlayer.getWidth() >= FlyGameSurfaceView.screenW) {
			x = FlyGameSurfaceView.screenW - bmPlayer.getWidth();
		} else if (x <= 0) {
			x = 0;
		}
		// 判断屏幕y边界
		if (y + bmPlayer.getHeight() >= FlyGameSurfaceView.screenH) {
			y = FlyGameSurfaceView.screenH - bmPlayer.getHeight();
		} else if (y <= 0) {
			y = 0;
		}
		//判断状态
		if(isCollision){
			//开始计时
			noCollisionCount++;
			if(noCollisionCount>=noCollisionTime){
				isCollision = false;
				noCollisionCount = 0;
			}
		}
	}

	// 判断是否碰撞
	public boolean isCollsionWith(Enemy enemy) {
		if (isCollision == false) {
			int x2 = enemy.x;
			int y2 = enemy.y;
			int w2 = enemy.frameW;
			int h2 = enemy.frameH;
			if (x > x2 && x >= x2 + w2) {
				return false;
			} else if (x <= x2 && x + bmPlayer.getWidth() <= x2) {
				return false;
			} else if (y >= y2 && y >= y2 + h2) {
				return false;
			} else if (y <= y2 && y + bmPlayer.getHeight() <= y2) {
				return false;
			}
			// 无敌状态
			isCollision = true;
			return true;
		} else {// 处于无敌状态
			return false;
		}
	}
	public boolean isCollsionWith(Bullet bullet){
		//是否处于无敌时间
		if(isCollision==false){
			int x2 = bullet.x;
			int y2 = bullet.y;
			int w2 = bullet.bmBullet.getWidth();
			int h2 = bullet.bmBullet.getHeight();
			if (x > x2 && x >= x2 + w2) {
				return false;
			} else if (x <= x2 && x + bmPlayer.getWidth() <= x2) {
				return false;
			} else if (y >= y2 && y >= y2 + h2) {
				return false;
			} else if (y <= y2 && y + bmPlayer.getHeight() <= y2) {
				return false;
			}
			// 无敌状态
			isCollision = true;
			return true;
		}else{//处于无敌状态
			return false;
		}
	}
}
