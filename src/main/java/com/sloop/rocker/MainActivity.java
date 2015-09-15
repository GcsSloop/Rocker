package com.sloop.rocker;


import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.sloop.widget.Rocker;

public class MainActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		final TextView status = (TextView) findViewById(R.id.status);

		Rocker rocker = (Rocker) findViewById(R.id.rudder);

		Bitmap rocker_bg = BitmapFactory.decodeResource(getResources(), R.drawable.rocker_bg1);
		Bitmap rocker_ctrl = BitmapFactory.decodeResource(getResources(), R.drawable.rocker_ctrl);
		rocker.setRockerBg(rocker_bg);
		rocker.setRockerCtrl(rocker_ctrl);

		rocker.setRudderListener(new Rocker.RudderListener() {

			@Override
			public void onSteeringWheelChanged(int action, int angle) {
				if (action == Rocker.ACTION_RUDDER) {
					//TODO:事件实现
				//	Log.e("夹角", "angle"+angle);
					status.setText("角度:"+angle);
				}
			}
		});
	}

}