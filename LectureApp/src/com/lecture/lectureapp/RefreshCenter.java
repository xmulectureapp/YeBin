package com.lecture.lectureapp;





import com.lecture.DBCenter.DBCenter;
import com.lecture.DBCenter.XMLToList;
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
	
	/*
	 * @author Xianyu
	 * 
	 * 2014��7��15�� 14:29  �����Ȱ��������ò��ã�ֱ�Ӱ�refresh()�ŵ�MainView������в���
	 * @notice   ���ǣ���ʱ�Ȳ�Ҫɾ�������
	 */
	
	//���ݿ�
		public static final String DB_NAME = "LectureDB";
		private DBCenter dbCenter = new DBCenter(this, DB_NAME, 1);
		
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.hotlecturecenter);
		//getActionBar().setDisplayHomeAsUpEnabled(true);
		//setFragment();
		this.refresh();
		//this.finish();
		
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
						"���Ժ�", msg, true, false);
			} else if (message.what == MESSAGE_REFRESH_END) {
				if (mProgressDialog != null) {
					mProgressDialog.dismiss();
					mProgressDialog = null;
					finish();
					
				
					
					
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
						msg.obj = "���ڸ���...";
						refreshHandler.sendMessage(msg);
					}

					@Override
					public void onEnd() {
						//XML TO List, then to db
						XMLToList xmlToList = new XMLToList();
						xmlToList.insertListToDB(RefreshCenter.this, dbCenter, "LectureTable");
						
						Log.i("��RefreshCenter���еĲ���", "XMLToList�Ѿ������ݴ������ݿ⣡");
						Message msg = new Message();
						msg.what = MESSAGE_REFRESH_END;
						refreshHandler.sendMessage(msg);
						
						//����
						
						
						
						
						
					}

					@Override
					public void onNoInternet() {
						Message msg = new Message();
						msg.what = MESSAGE_REFRESH_FAILED;
						msg.obj = "�޷����ӵ�����...";
						refreshHandler.sendMessage(msg);
					}
				});
		getEventsUtil.getInfo(this);
		//this.finish();
		//return true;
	}
	@Override
	protected void onDestroy(){
		
		Log.i("Refresh Center on Destroy", "�ر� RefreshCenter!");
		if(mProgressDialog != null)
			mProgressDialog.dismiss();
		super.onDestroy();
	}
}
