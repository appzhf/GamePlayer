package com.example.gameplayer.contrlloer;

import com.example.gameplayer.view.FlyGameSurfaceView;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class GameBg {
	// 为了循环播放，定义了两张位图
	private Bitmap bmBackGround1;
	private Bitmap bmBackGround2;
	// 游戏背景的坐标
	private int bg1x, bg1y, bg2x, bg2y;
	// 背景滚动的速度
	private int speed = 3;

	public GameBg(Bitmap bmBackGround) {
		this.bmBackGround1 = bmBackGround;
		this.bmBackGround2 = bmBackGround;
		// 第一张背景填满整个屏幕
		bg1y = -Math
				.abs(bmBackGround1.getHeight() - FlyGameSurfaceView.screenH);
		// 第二张背景紧挨着第一张背景上放
		// +111的原因两张图片连接而修正的位置
		bg2y = bg1y - bmBackGround1.getHeight() + 111;
	}

	// 游戏背景绘图
	public void draw(Canvas canvas, Paint paint) {
		// 绘制两张图片
		canvas.drawBitmap(bmBackGround1, bg1x, bg1y, paint);
		canvas.drawBitmap(bmBackGround2, bg2x, bg2y, paint);
	}

	// 逻辑函数
	public void logic() {
		bg1y += speed;
		bg2y += speed;
		// 当地一张图片超出屏幕，立即第二张图片接上
		if (bg1y > FlyGameSurfaceView.screenH) {
			bg1y = bg2y - bmBackGround1.getHeight() + 111;
		}
		if (bg2y > FlyGameSurfaceView.screenH) {
			bg2y = bg1y - bmBackGround1.getHeight() + 111;
		}
	}
}
