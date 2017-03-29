package com.example.gameplayer;

import com.example.gameplayer.view.FlyGameSurfaceView;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(new FlyGameSurfaceView(this));
	}
}
