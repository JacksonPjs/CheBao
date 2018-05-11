package com.chebao.ui.activity;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.chebao.MainActivity;
import com.chebao.R;

public class StartActivity extends BaseActivity{
	
	Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			Intent intent = new Intent(StartActivity.this, MainActivity.class);
			startActivity(intent);
			finish();
		};
	};
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.startactivty);
		
		mHandler.sendEmptyMessageDelayed(0, 2000);
	}

}
