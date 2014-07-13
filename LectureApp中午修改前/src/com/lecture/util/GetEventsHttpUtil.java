package com.lecture.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.content.Context;
import android.util.Log;



public class GetEventsHttpUtil {


	private static final String fileName = "eventsInfo.xml";

	private static GetEventsHttpUtil singleEventsUtil;

	public static GetEventsHttpUtil getInstance(GetEventsCallback callback) {
		if (singleEventsUtil == null)
			singleEventsUtil = new GetEventsHttpUtil(callback);
		else
			singleEventsUtil.setCallback(callback);
		return singleEventsUtil;
	}

	private GetEventsCallback mCallback;

	private GetEventsHttpUtil(GetEventsCallback callback) {
		mCallback = callback;
	}

	public void setCallback(GetEventsCallback callback) {
		mCallback = callback;
	}

	public static boolean isContainInfo(Context context) {
		File outFile = new File(context.getCacheDir().getAbsolutePath()
				+ "/info", fileName);
		return outFile.exists();
	}

	public void getInfo(final Context context) {
		mCallback.onStart();
		new Thread(new Runnable() {

			@Override
			public void run() {
				HttpURLConnection connection = null;
				FileOutputStream out = null;
				InputStream in = null;
				try {
					URL url = new URL("http://lecture.xmu.edu.cn/events");
					connection = (HttpURLConnection) url.openConnection();
					in = new BufferedInputStream(connection.getInputStream());
					File outFile = new File(context.getCacheDir()
							.getAbsolutePath() + "/info", fileName);
					if (!outFile.exists()) {
						if (!outFile.getParentFile().exists())
							outFile.getParentFile().mkdirs();
						outFile.createNewFile();
					}
					out = new FileOutputStream(outFile);
					byte[] buffer = new byte[256];
					int length = 0;
					while ((length = in.read(buffer)) != -1) {
						out.write(buffer, 0, length);
						// System.out.write(buffer, 0, length);
					}
					Log.i("DownLoad","���سɹ���");
					mCallback.onEnd();
				}
				catch (MalformedURLException e) {
				} 
				catch (IOException e) {
					mCallback.onNoInternet();
					e.printStackTrace();
				} 
				finally {
					try {
						if (out != null)
							out.close();
						if (in != null)
							in.close();
						connection.disconnect();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

	public static File getEventsPath(Context context) {
		return new File(context.getCacheDir().getAbsolutePath() + "/info",
				fileName);
	}

	public interface GetEventsCallback {
		/**
		 * ֻ��д�����߳�handler
		 */
		public void onStart();

		/**
		 * ֻ��д�����߳�handler
		 */
		public void onEnd();

		/**
		 * ֻ��д�����߳�handler
		 */
		public void onNoInternet();
	}

}
