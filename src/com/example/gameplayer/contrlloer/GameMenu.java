package com.example.gameplayer.contrlloer;


import com.example.gameplayer.view.FlyGameSurfaceView;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

public class GameMenu {
	private Bitmap bmMenu;
	private Bitmap bmButton;
	private Bitmap bmButtonPress;
	private int btnX;
	private int btnY;
	private boolean isPress;

	public GameMenu(Bitmap bmMenu, Bitmap bmButton, Bitmap bmButtonPress) {
		super();
		this.bmMenu = bmMenu;
		this.bmButton = bmButton;
		this.bmButtonPress = bmButtonPress;
		if (FlyGameSurfaceView.screenW == 0) {//给个默认值
			FlyGameSurfaceView.screenW = 720;
			FlyGameSurfaceView.screenH = 1134;
		}
		btnX = FlyGameSurfaceView.screenW / 2 - bmButton.getWidth() / 2;
		btnY = FlyGameSurfaceView.screenH - bmButton.getWidth();
		isPress = false;
	}

	public void draw(Canvas canvas, Paint paint) {
		// 绘制菜单背景
		canvas.drawBitmap(bmMenu, 0, 0, paint);
		// 是否按下开始按钮
		if (isPress) {
			canvas.drawBitmap(bmButtonPress, btnX, btnY, paint);
		} else {
			canvas.drawBitmap(bmButton, btnX, btnY, paint);
		}
	}

	/**
	 * 自定义菜单触屏事件
	 * */
	public void onTouchEvent(MotionEvent event) {
		// 获取当前位置
		int x = (int) event.getX();
		int y = (int) event.getY();
		// 按下或移动
		if (event.getAction() == MotionEvent.ACTION_DOWN
				|| event.getAction() == MotionEvent.ACTION_MOVE) {
			// 判断用户是否点击按钮
			if (x > btnX && x < btnX + bmButton.getWidth()) {
				if (y > btnY && y < btnY + bmButton.getHeight()) {
					isPress = true;
				} else {
					isPress = false;
				}
			} else {
				isPress = false;
			}
		} else if (event.getAction() == MotionEvent.ACTION_UP) {// 抬起
			// 判断是否点击按钮，防止点移到别处
			if (x > btnX && x < btnX + bmButton.getWidth()) {
				if (y > btnY && y < btnY + bmButton.getHeight()) {
					isPress = false;
					FlyGameSurfaceView.GAME_STATE = FlyGameSurfaceView.GAMEING;
				}
			}
		}
	}
}
