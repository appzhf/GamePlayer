package com.example.gameplayer.contrlloer;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * 爆炸
 * */
public class Boom {
	// 爆炸图片
	private Bitmap bmBoom;
	// 位置
	private int x, y;
	// 爆炸当前帧的下标
	private int cureentFrameIndex;
	// 爆炸效果的总帧数
	private int totleFrame;
	// 每帧的宽高
	private int FrameW, FrameH;
	// 是否播放完毕
	public boolean playEnd;

	public Boom(Bitmap bmBoom, int x, int y, int totleFrame) {
		super();
		this.bmBoom = bmBoom;
		this.x = x;
		this.y = y;
		this.totleFrame = totleFrame;
		FrameW = bmBoom.getWidth() / totleFrame;
		FrameH = bmBoom.getHeight();
	}

	// 绘制爆炸效果
	public void draw(Canvas canvas, Paint paint) {
		canvas.save();
		canvas.clipRect(x, y, x + FrameW, y + FrameH);
		canvas.drawBitmap(bmBoom, x - cureentFrameIndex * FrameW, y, paint);
		canvas.restore();
	}

	// 爆炸效果逻辑
	public void logic() {
		if (cureentFrameIndex < totleFrame) {
			cureentFrameIndex++;
		} else {
			playEnd = true;
		}
	}
}
