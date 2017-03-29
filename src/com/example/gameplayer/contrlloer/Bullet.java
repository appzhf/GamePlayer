package com.example.gameplayer.contrlloer;

import com.example.gameplayer.view.FlyGameSurfaceView;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Bullet {
	// 子弹图片资源
	public Bitmap bmBullet;
	// 子弹坐标
	public int x, y;
	// 子弹速度
	private int speed;
	// 子弹类型及常量
	private int bulletType;
	public static final int BULLET_PLAYER = -1;
	public static final int BULLET_BOOM1 = 1;
	public static final int BULLET_BOOM2 = 2;
	// 是否超出屏幕
	public boolean isDead;
	
	
	public Bullet(Bitmap bmBullet, int x, int y, int bulletType) {
		this.bmBullet = bmBullet;
		this.x = x;
		this.y = y;
		this.bulletType = bulletType;
		// 不同子弹类型的速度不一样
		switch (bulletType) {
		case BULLET_PLAYER:
			speed = 5;
			break;
		case BULLET_BOOM1:
			speed = 3;
			break;
		case BULLET_BOOM2:
			speed = 4;
			break;
		}
	}

	// 子弹绘制
	public void draw(Canvas canvas, Paint paint) {
		canvas.drawBitmap(bmBullet, x, y, paint);
	}

	// 子弹逻辑
	public void logic() {
		// 主角子弹垂直向上
		switch (bulletType) {
		case BULLET_PLAYER:
			y -= speed;
			if (x < -50) {
				isDead = true;
			}
			break;
		case BULLET_BOOM1:
		case BULLET_BOOM2:
			y += speed;
			if (y > FlyGameSurfaceView.screenH) {
				isDead = true;
			}
			break;
		}
	}
}
