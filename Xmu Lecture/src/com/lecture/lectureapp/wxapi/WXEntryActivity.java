package com.lecture.lectureapp.wxapi;

import java.io.ByteArrayOutputStream;
import java.net.URL;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.widget.Toast;

import com.lecture.layoutUtil.PopMenuView;
import com.lecture.lectureapp.MainView;
import com.lecture.lectureapp.R;
import com.lecture.localdata.Event;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXTextObject;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.mm.sdk.modelmsg.WXImageObject;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler  {
	
	
	//private final String WX_PACKAGE_NAME = "com.tencent.mm";
	//public static final String APP_ID = "wx932efd1a7f9f947c";  //original appid
	public static final String APP_ID = "wx423796ceda220d74"; // current appid 厦大讲座
	Event eventToShare;
	String shareDirection;
	Boolean isReopen = false;//如果是分享完回到这个应用就设置为true，然后结束Activity
	private final int THUMB_SIZE = 40;
	private Event event;
	private Boolean isSharedToSession = true;//默认分享到会话
	
	
	
	// IWXAPI 是第三方app和微信通信的openapi接口
	private IWXAPI api;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.wechat_share);
		
		event = (Event) getIntent().getSerializableExtra("shareEvent");
		isSharedToSession = (Boolean) getIntent().getBooleanExtra("isSharedToSession", true);
		
		
		
		// 通过WXAPIFactory工厂，获取IWXAPI的实例
		api = WXAPIFactory.createWXAPI(WXEntryActivity.this, APP_ID, true);
		api.registerApp(APP_ID);
		api.handleIntent(getIntent(), WXEntryActivity.this);
		
	
		
		
		if(isReopen){
			Log.i("顺序", " 重新打开!");
			finish();
		}
		else{
			
			if(event == null){
				finish();
				// error
				Log.i("微信分享错误", "event为NULL！");
			}
			
			if(isSharedToSession)
				shareToSession();
			else 
				shareToTimeline();
		}
		
		
		

		
		
	}

	public WXEntryActivity() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onReq(BaseReq req) {
		// TODO Auto-generated method stub
		
		
	}

	@Override
	public void onResp(BaseResp resp) {
		// TODO Auto-generated method stub
		
		
		
		String result = "";
		switch (resp.errCode) {
		case BaseResp.ErrCode.ERR_OK:
			
			isReopen = true;//回调的时候设置为true， 然后在OnCreate中finish
			
			result = "分享成功";
			break;
		case BaseResp.ErrCode.ERR_USER_CANCEL:
			result = "分享取消";
			isReopen = true;//回调的时候设置为true， 然后在OnCreate中finish
			break;
		case BaseResp.ErrCode.ERR_AUTH_DENIED:
			result = "分享失败";
			isReopen = true;//回调的时候设置为true， 然后在OnCreate中finish
			break;
		default:
			result = "分享出现异常";
			isReopen = true;//回调的时候设置为true， 然后在OnCreate中finish
			break;
		}
		Toast.makeText(this, result, Toast.LENGTH_LONG).show();
		
	}
	
	@Override
	protected void onNewIntent(Intent intent) {
		
		super.onNewIntent(intent);
		setIntent(intent);
		api.handleIntent(intent, this);
		
	}
	
	/**
	 * 分享到微信会话，即朋友
	 * 
	 * @author xianyu 2014年8月13日  19:25
	 * any questions? ask me or check the documentation of WeChat 
	 * 
	 *            
	 */
	public void shareToSession() {

		
		WXWebpageObject webPage = new WXWebpageObject();
		webPage.webpageUrl = event.getLink();
		
		WXMediaMessage msg = new WXMediaMessage();
		
		WXImageObject imgObj;
		try {
			
			Bitmap thumb = BitmapFactory.decodeResource(getResources(), R.drawable.wechat_sahre_img_black);  
		    msg.setThumbImage(thumb);  
			
		} catch (Exception e) {
			e.printStackTrace();
		}
				
		// 初始化一个WXMediaMessage对象
		msg.mediaObject = webPage;
		
		
		msg.description = "(●°u°●)​ 」 | 点击获取该讲座的详细信息 | 一则讲座,改变一生 | via 厦大讲座";
		msg.title = "主题:" + event.getTitle() + "\n时间:" + event.getCustomTime() + "\n主讲:" + event.getSpeaker() + "\n地点:" + event.getAddress();
		

		// 构造一个Req
		SendMessageToWX.Req req = new SendMessageToWX.Req();
		req.transaction = buildTransaction("url"); // transaction字段用于唯一标识一个请求
		req.message = msg;
		req.scene = SendMessageToWX.Req.WXSceneSession;  //分享到WeChat会话，Session

		// 调用api接口发送数据到微信
		api.sendReq(req);
		
		finish();
	}
	
	/**
	 * 更新为微信朋友圈状态
	 *
	 *  @author xianyu 2014年8月13日  19:25
	 * any questions? ask me or check the documentation of WeChat
	 *
	 */
	public void shareToTimeline() {

		
		WXWebpageObject webPage = new WXWebpageObject();
		webPage.webpageUrl = event.getLink();
		
		WXMediaMessage msg = new WXMediaMessage();
		
		WXImageObject imgObj;
		try {
			
			Bitmap thumb = BitmapFactory.decodeResource(getResources(), R.drawable.wechat_sahre_img_black);  
		    msg.setThumbImage(thumb);  
			
		} catch (Exception e) {
			e.printStackTrace();
		}
				
		// 初始化一个WXMediaMessage对象
		msg.mediaObject = webPage;
		
		
		msg.description = "(●°u°●)​ 」 | 点击获取该讲座的详细信息 | 一则讲座,改变一生 | via 厦大讲座";
		msg.title = "主题:" + event.getTitle() + "\n时间:" + event.getCustomTime() + "\n主讲:" + event.getSpeaker() + "\n地点:" + event.getAddress();
		

		// 构造一个Req
		SendMessageToWX.Req req = new SendMessageToWX.Req();
		req.transaction = buildTransaction("url"); // transaction字段用于唯一标识一个请求
		req.message = msg;
		req.scene = SendMessageToWX.Req.WXSceneTimeline;  //分享到WeChat会话，Session

		// 调用api接口发送数据到微信
		api.sendReq(req);
		
		finish();
	}
	/**
	 * transaction字段用于唯一标识一个请求
	 *
	 *  @author xianyu 2014年8月13日  19:25
	 * any questions? ask me or check the documentation of WeChat
	 *
	 */
	private String buildTransaction(final String type) {

		return (type == null) ? String.valueOf(System.currentTimeMillis())
				: type + System.currentTimeMillis();

	}
	
	
	
	
	

}// end activity
