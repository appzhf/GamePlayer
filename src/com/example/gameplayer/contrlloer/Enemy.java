package com.example.gameplayer.contrlloer;


import com.example.gameplayer.view.FlyGameSurfaceView;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Enemy {
	// 敌机的种类标识
	public int type;
	// 敌机(从左往右)
	public static final int TYPE_BOOM1 = 2;
	// 敌机(从右往左)
	public static final int TYPE_BOOM2 = 3;
	// 图片资源
	private Bitmap bmEnemy;
	// 坐标
	public int x, y;
	// 敌机的宽高
	public int frameW, frameH;
	// 敌机当前下标
	private int frameIndex;
	// 敌机移动速度
	private int speed;
	// 判断敌机是否超出屏幕
	public boolean isDead;

	public Enemy(Bitmap bmEnemy, int enemType, int x, int y) {
		this.bmEnemy = bmEnemy;
		frameW = bmEnemy.getWidth();
		frameH = bmEnemy.getHeight();
		this.type = enemType;
		this.x = x;
		this.y = y;
		// 敌机类型不同，速度也不同
		switch (type) {
		case TYPE_BOOM1:
			speed = 3;
			break;
		case TYPE_BOOM2:
			speed = 5;
			break;

		}
	}

	// 绘制
	public void draw(Canvas canvas, Paint paint) {
		canvas.save();
		canvas.clipRect(x, y, x + frameW, y + frameH);
		canvas.drawBitmap(bmEnemy, x - frameIndex * frameW, y, paint);
		canvas.restore();
	}

	// 逻辑
	public void logic() {
		// 循环播放
		frameIndex++;
		if (frameIndex >= 1) {
			frameIndex = 0;
		}
		// 不同类型的敌机
		switch (type) {
		case TYPE_BOOM1:
			if (isDead == false) {
				// 斜右下角运动
				x += speed / 2;
				y += speed;
				if (x > FlyGameSurfaceView.screenW) {
					isDead = true;
				}
			}
			break;
		case TYPE_BOOM2:
			if (isDead == false) {
				// 斜左下角运动
				x -= speed / 2;
				y += speed;
				if (x < -50) {
					isDead = true;
				}
			}
			break;
		}
	}
	//敌机撞主角
	public boolean isCollsionWith(Bullet bullet){
		int x2 = bullet.x;
		int y2 = bullet.y;
		int w2 = bullet.bmBullet.getWidth();
		int h2 = bullet.bmBullet.getHeight();
		if (x > x2 && x >= x2 + w2) {
			return false;
		} else if (x <= x2 && x + frameW <= x2) {
			return false;
		} else if (y >= y2 && y >= y2 + h2) {
			return false;
		} else if (y <= y2 && y + frameH <= y2) {
			return false;
		}
		//死亡
		isDead = true;
		return true;
	}
}
