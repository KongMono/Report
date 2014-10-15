package com.codefriday.bangkokunitrade.util;


import android.app.Application;

public class UIApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		
		new Runnable() {
			@Override
			public void run() {
//				Crashlytics.start(getApplicationContext());
			}
		};
		
		
		 Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {

             @Override
             public void uncaughtException(Thread thread, Throwable ex) {
//                 Crashlytics.logException(ex);
                 System.exit(1);
             }
         });
		
	}

}
