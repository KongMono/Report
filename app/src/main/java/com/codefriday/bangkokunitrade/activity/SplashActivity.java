package com.codefriday.bangkokunitrade.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.codefriday.bangkokunitrade.R;
import com.codefriday.bangkokunitrade.dataset.UserDataset;
import com.codefriday.bangkokunitrade.util.PreferencesApp;
import com.codefriday.bangkokunitrade.util.Util;

public class SplashActivity extends Activity {

	private Handler mHandler = new Handler();
	private ImageView splash;
	UserDataset dataset = UserDataset.getInstance();

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.splashscreen);
		
		
		splash = (ImageView) findViewById(R.id.splashimage);
		splash.setVisibility(View.INVISIBLE);
		mHandler.post(runFadeIn);
	}

	private void goToNextActivity() {
		
		PreferencesApp app = new PreferencesApp(this);
		
		app.getData();
		
		if (Util.stringIsNotNull(dataset.getUser_id())  && Util.stringIsNotNull(dataset.getUser_type())) {
			Intent intent = new Intent();
			
			switch (Integer.parseInt(dataset.getUser_type())) {
			case UserDataset.Type_System:
                intent = new Intent(getApplicationContext(),ReportActivity.class);
                startActivity(intent);
				break;
				
			case UserDataset.Type_Administrator:
                intent = new Intent(getApplicationContext(),ReportActivity.class);
                startActivity(intent);
				break;
				
			default:
				Toast.makeText(getApplicationContext(), "No Content", Toast.LENGTH_LONG).show();
				break;
			}
		}else{
			Intent intent = new Intent(SplashActivity.this, MainActivity.class);
			startActivity(intent);
		}
		
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
		finish();
		
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {

		boolean r = false;
		if (keyCode == 4) {
			System.exit(0);
			r = true;
		}
		return r;
	}

	Runnable runFadeIn = new Runnable() {
		@Override
		public void run() {
			splash.setImageResource(R.drawable.logo);
			Animation anim = AnimationUtils.loadAnimation(SplashActivity.this, android.R.anim.fade_in);
			anim.setDuration(1500);
			anim.setFillAfter(true);
			splash.startAnimation(anim);
			mHandler.post(runApp);
		}
	};

	Runnable runApp = new Runnable() {
		@Override
		public void run() {
			goToNextActivity();
		}
	};
}