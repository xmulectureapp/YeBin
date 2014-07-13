package com.lecture.lectureapp;





import com.lecture.lectureapp.R;
import com.lecture.util.GetEventsHttpUtil;
import com.lecture.util.GetEventsHttpUtil.GetEventsCallback;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public class RefreshCenter  extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.hotlecturecenter);
		//getActionBar().setDisplayHomeAsUpEnabled(true);
		//setFragment();
		this.refresh();
		this.finish();
	}
	


	private static final int MESSAGE_REFRESH_START = 1;
	private static final int MESSAGE_REFRESH_END = 2;
	private static final int MESSAGE_REFRESH_FAILED = 3;

	private ProgressDialog mProgressDialog;
	
	private Handler refreshHandler = new Handler() {
		@Override
		public void handleMessage(Message message) {
			final String msg = (String) message.obj;
			if (message.what == MESSAGE_REFRESH_START) {
				mProgressDialog = ProgressDialog.show(RefreshCenter.this,
						"请稍后", msg, true, false);
			} else if (message.what == MESSAGE_REFRESH_END) {
				if (mProgressDialog != null) {
					mProgressDialog.dismiss();
					mProgressDialog = null;
				}
			} else if (message.what == MESSAGE_REFRESH_FAILED) {
				if (mProgressDialog != null)
					mProgressDialog.dismiss();
				mProgressDialog = null;
				Toast.makeText(RefreshCenter.this, msg, Toast.LENGTH_SHORT)
						.show();
			}

		}

	};
	
	
	public RefreshCenter() {
		// TODO Auto-generated constructor stub
	}

	
	public void refresh() {
		GetEventsHttpUtil getEventsUtil = GetEventsHttpUtil
				.getInstance(new GetEventsCallback() {

					@Override
					public void onStart() {
						Message msg = new Message();
						msg.what = MESSAGE_REFRESH_START;
						msg.obj = "正在更新...";
						refreshHandler.sendMessage(msg);
					}

					@Override
					public void onEnd() {
						
						Message msg = new Message();
						msg.what = MESSAGE_REFRESH_END;
						refreshHandler.sendMessage(msg);
					}

					@Override
					public void onNoInternet() {
						Message msg = new Message();
						msg.what = MESSAGE_REFRESH_FAILED;
						msg.obj = "无法连接到网络...";
						refreshHandler.sendMessage(msg);
					}
				});
		getEventsUtil.getInfo(this);
		//this.finish();
		//return true;
	}
	@Override
	protected void onDestroy(){
		
		Log.i("Refresh Center on Destroy", "关闭 RefreshCenter!");
		mProgressDialog.dismiss();
		super.onDestroy();
	}
}
