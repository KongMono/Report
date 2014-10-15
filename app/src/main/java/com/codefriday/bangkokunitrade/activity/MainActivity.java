package com.codefriday.bangkokunitrade.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.codefriday.bangkokunitrade.R;
import com.codefriday.bangkokunitrade.dataset.UserDataset;
import com.codefriday.bangkokunitrade.util.Api;
import com.codefriday.bangkokunitrade.util.PreferencesApp;
import com.codefriday.bangkokunitrade.util.Util;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends Activity implements OnTouchListener {
	private ProgressDialog dialog;
	private EditText edittxtUserName,edittxtPassword;
	private Button btnlogin;
	AQuery aQuery;
	UserDataset dataset;
	int userType;
	Api api;
	PreferencesApp app;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);
		
		init();
		dialog = ProgressDialog.show(this, "", "Loading. Please wait...", true);
		dialog.setCancelable(false);
		dialog.cancel();
		
		
		btnlogin.setOnTouchListener(this);
		
	}

	private void init() {
		edittxtUserName = (EditText) findViewById(R.id.edittxtUserName);
		edittxtPassword = (EditText) findViewById( R.id.edittxtPassword);
		btnlogin = (Button)findViewById(R.id.btnlogin);
		
		edittxtUserName.setHint("Username");
		edittxtPassword.setHint("Password");
		
		aQuery = new AQuery(this);
		api = new Api(this);
		app = new PreferencesApp(this);
		dataset =  UserDataset.getInstance();
	}


	@Override
	public boolean onTouch(View v, MotionEvent event) {
		dialog.show();
		
		if (event.getAction() == MotionEvent.ACTION_UP) {
			String md5 = Util.StringToMD5(edittxtPassword.getText().toString());
			String url = api.getApiauth();
	
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("username",edittxtUserName.getText().toString());
			params.put("password",md5);

			aQuery.ajax(url, params, JSONObject.class, new AjaxCallback<JSONObject>() {
				Intent intent = new Intent();
			    @Override
			    public void callback(String url, JSONObject json, AjaxStatus status) {
			    	
			    	if (json != null) {
			    		JSONObject responseObject = json;
						 try {
							 app.inputData(responseObject.getString("id"),
									 responseObject.getString("user_type"),
									 edittxtUserName.getText().toString(),
									 edittxtPassword.getText().toString());
							 
							 app.getData();
							 
						} catch (JSONException e) {
							e.printStackTrace();
						}
						 
					 	switch (Integer.parseInt(dataset.getUser_type())) {
							case UserDataset.Type_System:
                                intent = new Intent(getApplicationContext(),ReportActivity.class);
                                startActivity(intent);
                                finish();
								break;
								
							case UserDataset.Type_Administrator:
                                intent = new Intent(getApplicationContext(),ReportActivity.class);
                                startActivity(intent);
                                finish();
								break;

							default:
								Toast.makeText(getApplicationContext(), "No Content", Toast.LENGTH_LONG).show();
								break;
							}
					 	overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
						 
					}else{
						Toast.makeText(getApplicationContext(), "wrong password", Toast.LENGTH_LONG).show();
					}
			    	dialog.cancel();
			    }
		    });
			
			return true;
		}
		return false;
	}
	
	

}
