package com.lecture.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.util.Log;


/*
 * @author xianyu
 *用于手机手机安装、打开、刷新次数，便于讲座网统计 
 * 
 */
public class StatusInterface {
	
	

	public StatusInterface() {
		// TODO Auto-generated constructor stub
	}
	
	public static void StatusGo(final String status){
		
		new Thread(new Runnable() {

			@Override
			public void run() {
				HttpURLConnection connection = null;
				FileOutputStream out = null;
				InputStream in = null;
				try {
					
					URL url = new URL( "http://lecture.xmu.edu.cn/appinterface/status_interface.php?status="+status );
					connection = (HttpURLConnection) url.openConnection();
					if(connection.getResponseCode() == HttpURLConnection.HTTP_OK)
						Log.i("Status","连接成功！");
					else {
						Log.i("Status","连接失败！");
					}
					
				}
				catch (MalformedURLException e) {
				} 
				catch (IOException e) {
					
					e.printStackTrace();
				} 
				finally {
					try {
						connection.disconnect();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
	
	
	
}
