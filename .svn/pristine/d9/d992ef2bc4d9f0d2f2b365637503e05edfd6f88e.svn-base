package com.atom.android.booklist.activity;

import com.atom.android.booklist.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

public class SplashActivity extends Activity{

	private Intent intent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_splash);
		//initUtil();
		startLoginActivity();
	}
	
	private void startLoginActivity() {
		// TODO Auto-generated method stub
		new Handler().postDelayed(new Runnable() {			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				intent = new Intent(SplashActivity.this,LoginActivity.class);
				startActivity(intent);
				SplashActivity.this.finish();//结束当前activity
			}
		},1000);//执行时间
	}
	
}
	
