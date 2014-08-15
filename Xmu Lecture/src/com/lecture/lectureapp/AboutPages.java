package com.lecture.lectureapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class AboutPages extends Activity {
	
	public String whichPage; // aboutApp   aboutAuthor   aboutXmuLecture
	
	
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		
		whichPage = (String) getIntent().getStringExtra("whichPage");
		if( whichPage==null || whichPage.equals("") )
			Log.i("About Page", "关于页面出现错误，请联系咸鱼！");
		else {
			
			//开始初始化页面
			if( whichPage.equals("aboutApp") ){
				
				setContentView(R.layout.about_app);
				
				
				
			}
			else if( whichPage.equals("aboutAuthor") ){
				
				setContentView(R.layout.about_author);
				
				
			}
			else if( whichPage.equals("aboutXmuLecture") ){
				
				setContentView(R.layout.about_xmu_lecture);
				
			}
			else if(whichPage.equals("aboutSubmit")){
				
				setContentView(R.layout.about_submit);
				
			}
			else if(whichPage.equals("aboutSetting")){
				
				setContentView(R.layout.about_setting);
				
			}
			else
				Log.i("About Page", "传入的whichPage出错！");
			
			
			
			
		}  //end whichPage if
		
		
		
		
	}

}
