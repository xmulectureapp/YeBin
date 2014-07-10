package com.lecture.lectureapp;

import java.io.OutputStream;

import com.example.lectureapp.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;

public class Appstart extends Activity {

	/*
	 * @Override public void onCreate(Bundle savedInstanceState) { // TODO
	 * Auto-generated method stub super.onCreate(savedInstanceState);
	 * setContentView(R.layout.appstart);
	 * //requestWindowFeature(Window.FEATURE_NO_TITLE);//ȥ��������
	 * //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, //
	 * WindowManager.LayoutParams.FLAG_FULLSCREEN); //ȫ����ʾ
	 * //Toast.makeText(getApplicationContext(), "���ӣ��úñ��У�",
	 * Toast.LENGTH_LONG).show();
	 * //overridePendingTransition(R.anim.hyperspace_in, R.anim.hyperspace_out);
	 * new Handler().postDelayed(new Runnable(){
	 * 
	 * @Override public void run(){ Intent intent = new Intent
	 * (Appstart.this,Guide.class); startActivity(intent);
	 * overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
	 * Appstart.this.finish(); } }, 1500); }
	 */

	private SharedPreferences preferences;
	private Editor editor;
	private OutputStream os;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.appstart);
		preferences = getSharedPreferences("phone", Context.MODE_PRIVATE);
		// �ж��ǲ����״ε�¼��
		if (preferences.getBoolean("firststart", true)) 
		{
			editor = preferences.edit();
			// ����¼��־λ����Ϊfalse���´ε�¼ʱ������ʾ�״ε�¼����
			editor.putBoolean("firststart", false);
			editor.commit();

			new Handler().postDelayed(new Runnable() 
			{

				@Override
				public void run() 
				{
					Intent intent = new Intent(Appstart.this, Guide.class);
					startActivity(intent);
					overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
					Appstart.this.finish();
				}
			}, 1500);
		} 
		else 
		{
			new Handler().postDelayed(new Runnable() 
			{

				@Override
				public void run() 
				{
					Intent intent = new Intent(Appstart.this, MainView.class);
					startActivity(intent);
					overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
					Appstart.this.finish();
				}
			}, 1500);
		}
	}
}
