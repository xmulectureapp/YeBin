package com.lecture.SettingAndSubmit;


import java.util.Date;

import com.lecture.localdata.Event;

import android.R.integer;
import android.text.format.Time;
import android.util.Log;

public class SQLMaker {

	public static Time time = new Time();
	public static String weekdaySettings;//ֻ��   1��2��3��4��5��6��7
	public static String placeSettings;  //ֻ��   ˼���ġ��衢��
	
	
	
	public SQLMaker() {
		time.setToNow();
		Log.i("Date WeekDay", String.format("%d:%d", time.weekDay, Time.FRIDAY));
		
	}

	public static void initSettings(String weekdaySettings, String placeSettings){
		SQLMaker.weekdaySettings = weekdaySettings;
		SQLMaker.placeSettings = placeSettings;
		
	}
	//ʱ��ת��
	public static Time stringToTime(String timeString){
		
		String[] strings = null;
		strings = timeString.split("-| |:");
		for (String string : strings) {
			Log.i("Split", string);
		}
		
		//  �� �� ʱ �� �� ��
		time.set( 0, Integer.parseInt(strings[4]), Integer.parseInt(strings[3]),
				Integer.parseInt(strings[2]), Integer.parseInt(strings[1]), Integer.parseInt(strings[0]) );
		
		
		return new Time(time);
		
	}
	
	public static Boolean isNeededLecture(Event e){
		if( weekdaySettings.contains(String.format("%d", stringToTime(e.getTime()).monthDay)) &&
				placeSettings.contains(e.getAddress().substring(0, 1)) )	
			return true;//���weekSettings�д��ڸ����־ͷ���true
		return false;
		
		
		
	}
	
	

}
