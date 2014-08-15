package com.lecture.lectureapp;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;

import com.lecture.DBCenter.DBCenter;
import com.lecture.DBCenter.XMLToList;
import com.lecture.layoutUtil.PopShareView;
import com.lecture.layoutUtil.RefreshableView;
import com.lecture.lectureapp.wxapi.WXEntryActivity;
import com.lecture.localdata.Comment;
import com.lecture.localdata.DetailInfo;
import com.lecture.localdata.Event;
import com.lecture.localdata.ReminderInfo;
import com.lecture.util.GetCommentUtil;
import com.lecture.util.GetCommentUtil.GetCommentCallback;
import com.lecture.util.GetDetailUtil;
import com.lecture.util.LikeInterface;
import com.lecture.util.GetDetailUtil.GetDetailCallback;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.CalendarContract.Events;
import android.provider.CalendarContract.Reminders;
import android.text.Html;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class DetailView extends Activity {

	private TextView tvname;
	private TextView tvtime;
	private TextView tvaddr;
	private TextView tvspeaker;
	private TextView tvlabel;
	private TextView tvaboutspeaker;
	private TextView tvmore;
	private ImageView iv1;

	private Event detail_lectureEvent;

	private LinearLayout linearBtnShare;
	private LinearLayout linearBtnComment;
	private LinearLayout linearBtnLike;
	private LinearLayout linearBtnRemind;

	private ImageView imageViewLike;
	private ImageView imageViewRemind;

	private TextView textViewLike;
	private TextView textViewRemind;

	// 下面开始添加评论 edited by 咸鱼 2014年8月5日 晚 20:20
	private List<Comment> commentsList;
	private ListView commentListView;
	public TextView commentUserNameTextView;
	public TextView commentDateTextView;
	public TextView commentContentTextView;
	public LinearLayout commentsBox;
	
	
	//下面是咸鱼的增加，用于增加返回 主页的header top 左边的按键  2014年8月日  
	public LinearLayout detailHeaderLeftLinearLayout;
	
	//下面是咸鱼的代码，用于解决点赞的bug  2014 - 08 - 10 13:22
	ConnectivityManager mConnectivityManager; 
	NetworkInfo mNetworkInfo; 
	
	
	//下面是咸鱼的增加，用于实现分享  2014-08-14 09:12
	  private PopShareView popShareMenu;
	  
	
	
	// ——————————————————————下面是用于handler的消息标记
	private static final int MESSAGE_DOWNLOAD_DETAIL_START = 1;// Detail刷新开始
	private static final int MESSAGE_DOWNLOAD_DETAIL_END = 2;// Detail刷新结束
	private static final int MESSAGE_DOWNLOAD_DETAIL_FAILED = 3;// Detail刷新失败

	private static final int MESSAGE_DOWNLOAD_COMMENTS_START = 4;// 评论获取开始
	private static final int MESSAGE_DOWNLOAD_COMMENTS_END = 5;// 评论获取结束
	private static final int MESSAGE_DOWNLOAD_COMMENTS_FAILED = 6;// 评论获取失败

	private ProgressDialog mProgressDialog;

	private Handler detailHandler = new Handler() {

		@Override
		public void handleMessage(Message message) {
			// final String msg = (String) message.obj;
			Message msgRepost = Message.obtain();

			if (message.what == MESSAGE_DOWNLOAD_DETAIL_START) {

			}// end start
			else if (message.what == MESSAGE_DOWNLOAD_DETAIL_END) {

				DetailInfo detailInfo = (DetailInfo) message.obj;

				setDetail(detailInfo);

			}// end end
			else if (message.what == MESSAGE_DOWNLOAD_DETAIL_FAILED) {

			}// end failed
			else if (message.what == MESSAGE_DOWNLOAD_COMMENTS_START) {

			}// end start
			else if (message.what == MESSAGE_DOWNLOAD_COMMENTS_END) {

				commentsList = (List<Comment>) message.obj;

				setComments(commentsList);

			}// end end
			else if (message.what == MESSAGE_DOWNLOAD_COMMENTS_FAILED) {

			}// end failed

		}

	}; // end handler

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail_view);

		
		tvname = (TextView) findViewById(R.id.detail_lecture_name);
		tvtime = (TextView) findViewById(R.id.detail_comment_content);
		tvaddr = (TextView) findViewById(R.id.detail_lecture_addr);
		tvspeaker = (TextView) findViewById(R.id.detail_lecture_speaker);
		tvlabel = (TextView) findViewById(R.id.detail_lecture_label);
		tvaboutspeaker = (TextView) findViewById(R.id.about_speaker);
		tvmore = (TextView) findViewById(R.id.more_information);

		detail_lectureEvent = (Event) getIntent().getSerializableExtra(
				"LectureDetail");
		

		tvname.setText("" + detail_lectureEvent.getTitle());
		tvtime.setText("时间：" + detail_lectureEvent.getTime());
		tvaddr.setText("地点：" + detail_lectureEvent.getAddress());
		tvspeaker.setText("主讲：" + detail_lectureEvent.getSpeaker());
		tvlabel.setText("标签：" + "标签还在开发中，敬请期待！");
		// tvaboutspeaker.setText("常耀信，博士，教授，博士生导师，现任教于南开大学及美国关岛大学，研究方向为英美文学。1965年毕业于南开大学英文系，同年赴英国伦敦、剑桥进修。1984年在美国坦普尔大学英文系获博士学位。自1986年起为美国《文化》(Paideuma)杂志特邀编辑。1988年入选英国国际传记中心编纂的《远东及太平洋名人录》，多次入选《美国名师录》。著有《美国文学简史》、《英文学简史》、《美国文学史》、《希腊罗马神话》、《漫话英美文学》及《研究与写作》等；主编有《美国文学选读及《英国文学通史》等。在国内外刊物上发表过多篇论文。");
		// tvmore.setText("人文学科的研究方法系列讲座第17期");
		tvaboutspeaker.setText("主讲资料：" + "主讲信息加载中...");
		tvmore.setText("讲座背景及更多消息：" + "讲座详情加载中...");

		// 下面开始初始化四个按钮
		linearBtnShare = (LinearLayout) findViewById(R.id.detail_linearlayout_share);
		linearBtnComment = (LinearLayout) findViewById(R.id.detail_linearlayout_comment);
		linearBtnLike = (LinearLayout) findViewById(R.id.detail_linearlayout_like);
		linearBtnRemind = (LinearLayout) findViewById(R.id.detail_linearlayout_remind);

		imageViewLike = (ImageView) findViewById(R.id.detail_like_icon);
		imageViewRemind = (ImageView) findViewById(R.id.detail_remind_icon);

		textViewLike = (TextView) findViewById(R.id.detail_like_text);
		textViewRemind = (TextView) findViewById(R.id.detail_remind_text);
		
		//下面是咸鱼的代码，用于解决点赞的bug  2014 - 08 - 10 13:22
		//下面获取网络状态操作的 初始化操作
		mConnectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE); 
		mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
		
		
		

		// 下面开始添加评论 edited by 咸鱼 2014年8月5日 晚 20:20

		if (detail_lectureEvent.isLike()) {
			imageViewLike.setImageDrawable(getResources().getDrawable(
					R.drawable.like_red));
			textViewLike.setTextColor(getResources().getColor(
					R.color.main_menu_pressed));

		} else {
			imageViewLike.setImageDrawable(getResources().getDrawable(
					R.drawable.like));
			textViewLike.setTextColor(getResources().getColor(
					R.color.item_content));

		}

		if (detail_lectureEvent.getLikeCount() > 0) {
			textViewLike.setText(adaptPlace(String.format("%d",
					detail_lectureEvent.getLikeCount())));
		}

		// 下面的代码使用于 收藏按钮
		if (detail_lectureEvent.isReminded()) {
			imageViewRemind.setImageDrawable(getResources().getDrawable(
					R.drawable.remind_red));
			textViewRemind.setTextColor(getResources().getColor(
					R.color.main_menu_pressed));

		} else {
			imageViewRemind.setImageDrawable(getResources().getDrawable(
					R.drawable.remind));

		}

		linearBtnShare.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				

				
		    	 //下面是咸鱼的增加，用于实现分享  2014-08-11 20:34
		    	
		    //实例化
		    popShareMenu = new PopShareView( DetailView.this, shareItemsOnClick);
   			//显示窗口
   			popShareMenu.showAtLocation( DetailView.this.findViewById(R.id.detailview_linearLayout) , Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
   			

			}
		});

		linearBtnComment.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				if( hasEmail(DetailView.this) ){
					
					startComent("");//直接回复 参数为""表示不回复任何人
				
				}
			}
		});

		linearBtnLike.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// showInfo3();
				Event event = detail_lectureEvent;
				
				////////
				if ( !event.isLike() )
		    	{
		    		
		    		
		    		//下面是咸鱼的代码，用于解决点赞的bug  2014 - 08 - 09 22:13
		    		mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
		    		if( mNetworkInfo != null ){
		    			
		    			event.setLike(!event.isLike());
		    			
		    			imageViewLike.setImageDrawable(getResources().getDrawable(
								R.drawable.like_red));
						textViewLike.setTextColor(getResources().getColor(
								R.color.main_menu_pressed));
						// 喜欢的话，进行数据表LikeTable更新
						DBCenter.setLike(
								DBCenter.getStaticDBCenter(DetailView.this)
										.getReadableDatabase(), event.getUid(),
								true);
						event.updateLikeCount(1);// 1 表示＋1
						LikeInterface.LikeGo(event.getUid(), "1");
						
						DBCenter.likeDBSync(
								DBCenter.getStaticDBCenter(DetailView.this)
										.getReadableDatabase(), event.getUid(), "1");
						// 下面一句解决马上变Like数字
						//下面代码由 咸鱼 添加，用于解决按赞数为0 的时候，文字设成 按赞
			    		if(event.getLikeCount() == 0 )
			    			textViewLike.setText( "点赞" );
			    		else
			    			textViewLike.setText(adaptPlace(String.format("%d", event.getLikeCount())));
			    		
			    		
		    		}
		    		else {
		    			
		    			Toast.makeText(DetailView.this, "请连接网络后按赞！", Toast.LENGTH_SHORT).show();
					}
		    		
		    		
		    	}
				else
				{
					
					
					
		    		//下面是咸鱼的代码，用于解决点赞的bug  2014 - 08 - 09 22:13
		    		mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
		    		if( mNetworkInfo != null ){
		    			
		    			event.setLike(!event.isLike());
		    			

						imageViewLike.setImageDrawable(getResources().getDrawable(
								R.drawable.like));
						textViewLike.setTextColor(getResources().getColor(
								R.color.main_menu_normal));
						// 喜欢的话，进行数据表LikeTable更新
						DBCenter.setLike(
								DBCenter.getStaticDBCenter(DetailView.this)
										.getReadableDatabase(), event.getUid(),
								false);
						event.updateLikeCount(-1);// -1 表示＋ (-1)
						
						LikeInterface.LikeGo(event.getUid(), "0");
						DBCenter.likeDBSync(
								DBCenter.getStaticDBCenter(DetailView.this)
										.getReadableDatabase(), event.getUid(), "0");
						// 下面一句解决马上变Like数字
						textViewLike.setText(adaptPlace(String.format("%d",
								event.getLikeCount())));
		    			
		    		}
		    		else {

		    			Toast.makeText(DetailView.this, "请连接网络后按赞！", Toast.LENGTH_SHORT).show();
		    			
					}
		    		
		    		
		    		
				}
				
				
				
				// x下面代码来自咸鱼的增加，用于点赞后返回时刷新相应listView 2014 08 10   13:59
	    		Intent intent = new Intent(); 
				setResult(2,   intent.putExtra("whichCenter", getIntent().getStringExtra("whichCenter"))  );

				
			}
			
			
		});

		linearBtnRemind.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// showInfo4();
				Event event = detail_lectureEvent;
				event.setReminded(!event.isReminded());
				if (event.isReminded()) {
					imageViewRemind.setImageDrawable(v.getResources()
							.getDrawable(R.drawable.remind_red));
					textViewRemind.setTextColor(v.getResources().getColor(
							R.color.main_menu_pressed));
					// 收藏的话，进行数据表CollectionTable更新
					DBCenter.setRemind(
							DBCenter.getStaticDBCenter(DetailView.this)
									.getReadableDatabase(), event.getUid(), event.getReminderID(), event.getEventID(),
							true);
					// 添加到日历
					insertIntoCalender();
				} else {
					imageViewRemind.setImageDrawable(v.getResources()
							.getDrawable(R.drawable.remind));
					textViewRemind.setTextColor(v.getResources().getColor(
							R.color.main_menu_normal));
					// 收藏的话，进行数据表CollectionTable更新
					DBCenter.setRemind(
							DBCenter.getStaticDBCenter(DetailView.this)
									.getReadableDatabase(), event.getUid(), event.getReminderID(), event.getEventID(),
							false);
					// 从日历删除
					deleteFromCalender();
				}
				
				// x下面代码来自咸鱼的增加，用于点赞后返回时刷新相应listView 2014 08 10   13:59
				Intent intent = new Intent(); 
				setResult(2,   intent.putExtra("whichCenter", getIntent().getStringExtra("whichCenter"))  );
				
			}
		});

		//下面是咸鱼的增加，用于增加返回 主页的header top 左边的按键  2014年8月日  
		detailHeaderLeftLinearLayout = (LinearLayout)findViewById(R.id.detail_header_left_linearLayout);
		detailHeaderLeftLinearLayout.setOnClickListener( new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				finish();
				overridePendingTransition(R.anim.show_in_left, R.anim.hide_in_right	);
				
			}
		} );
		
		// 获取讲座更多信息
		downloadDetail();

		// 获取讲座评论信息
		downloadComments();
		
		

	}

	protected void startComent(String commentReplay ) {
		// 开始评论该则讲座
		Event event = detail_lectureEvent;
		// 把该则讲座对应的event传入Bundle，来自KunCheng
		Bundle detail_bundle = new Bundle();
		detail_bundle.putSerializable("LectureComment", event);
		Intent intent = new Intent(DetailView.this,CommentView.class);
		intent.putExtras(detail_bundle);
		intent.putExtra("commentReply", commentReplay);

		// LectureDetail.this.startActivity(intent);
		DetailView.this.startActivityForResult(intent, 1);
		
		
	}

	// 下面用于给按赞添加一个空格如果只有个位数的话
	public String adaptPlace(String s) {

		if(s.length() == 1)
			return "  " + s + "    ";
		else if(s.length() == 2)
			return "  " + s + "   ";
		else
			return s;

	}

	public void setDetail(DetailInfo detailInfo) {
		tvaboutspeaker.setText("主讲资料：\n★" + detailInfo.getLec_aboutSpeaker());
		tvmore.setText("讲座背景及更多消息：\n★" + detailInfo.getLec_about());

	}

	public void setComments(List<Comment> commentsList) {
		
		
		
		DetailItemLinearLayout detailItemLinearLayout = null;
		commentsBox = (LinearLayout) findViewById(R.id.comments_box);
		
		commentsBox.removeAllViews();
		
		int i = 0;// 用于在 getUserName前面添加 #1  #2
		for (Comment c : commentsList) {
			
			i ++;
			
			detailItemLinearLayout = new DetailItemLinearLayout(this);
			
			//下面代码用于实现点击评论直接回复点击楼层
			detailItemLinearLayout.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					if( hasEmail(DetailView.this) )
						startComent( "回复 " 
							+ (  (TextView)v.findViewById(R.id.detail_comment_username) )
												  .getText().toString().trim() + " :"   );
					
					
					
				}
			});

			commentUserNameTextView = (TextView) detailItemLinearLayout
					.findViewById(R.id.detail_comment_username);
			commentDateTextView = (TextView) detailItemLinearLayout
					.findViewById(R.id.detail_comment_date);
			commentContentTextView = (TextView) detailItemLinearLayout
					.findViewById(R.id.detail_comment_content);
			
			commentUserNameTextView.setText( "#" + String.format("%d", i) + "   " +  c.getUserName().trim() );
			
			
			commentUserNameTextView.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//下划线 
		    //commentUserNameTextView.getPaint().setFakeBoldText(true); //italic   
			
			commentDateTextView.setText( c.getCommentDateCustomize() );//使用定制的时间格式
			commentContentTextView.setText( "     -- " + c.getUserComment().trim() );

			commentsBox.addView(detailItemLinearLayout);

		}
		
		//下面进行foot item的添加
		if(commentsList.size() == 0){
			
			//提醒第一个沙发
			commentsBox.addView( new FootItemLinearLayout(DetailView.this) );
			
		}
		else{
			
			FootItemLinearLayout footItemLinearLayout = new FootItemLinearLayout(DetailView.this);
			TextView commentFootItemTextView = (TextView)footItemLinearLayout.findViewById(R.id.comment_foot_item_textView);
			commentFootItemTextView.setText("在 #" + String.format("%d", commentsList.size() + 1 ) + " 楼分享你的观点吧！" );
			
			commentsBox.addView( footItemLinearLayout );
			
		}
		

	}

	public void downloadDetail() {

		GetDetailUtil getDetailUtil = GetDetailUtil
				.getInstance(new GetDetailCallback() {

					@Override
					public void onStart() {
						Message msg = new Message();
						msg.what = MESSAGE_DOWNLOAD_DETAIL_START;
						msg.obj = "正在下载详情...";
						detailHandler.sendMessage(msg);
					}

					@Override
					public void onEnd(DetailInfo detailInfo) {

						Log.i("获取详细信息",
								"获取结束！准备发送MESSAGE_DOWNLOAD_DETAIL_END 消息！");

						Message msg = new Message();
						msg.what = MESSAGE_DOWNLOAD_DETAIL_END;

						msg.obj = detailInfo;

						detailHandler.sendMessage(msg);

					}

					@Override
					public void onNoInternet() {
						Message msg = new Message();
						msg.what = MESSAGE_DOWNLOAD_DETAIL_FAILED;
						msg.obj = "无法连接到网络...";
						detailHandler.sendMessage(msg);
					}
				});
		getDetailUtil.getDetail(this, detail_lectureEvent.getUid());

	}

	public void downloadComments() {

		GetCommentUtil getCommentUtil = GetCommentUtil
				.getInstance(new GetCommentCallback() {

					@Override
					public void onStart() {
						Message msg = new Message();
						msg.what = MESSAGE_DOWNLOAD_COMMENTS_START;
						msg.obj = "正在下载评论...";
						detailHandler.sendMessage(msg);
					}

					@Override
					public void onEnd(List<Comment> commentsList) {

						Log.i("获取评论",
								"获取结束！准备发送MESSAGE_DOWNLOAD_COMMENTS_END 消息！");

						Message msg = new Message();
						msg.what = MESSAGE_DOWNLOAD_COMMENTS_END;

						msg.obj = commentsList;

						detailHandler.sendMessage(msg);

					}

					@Override
					public void onNoInternet() {
						Message msg = new Message();
						msg.what = MESSAGE_DOWNLOAD_COMMENTS_FAILED;
						msg.obj = "无法连接到网络...";
						detailHandler.sendMessage(msg);
					}
				});
		getCommentUtil.getComments(this, detail_lectureEvent.getUid());

	}

	public void insertIntoCalender() {
		long calId = 1;
		Event event = detail_lectureEvent;
		GregorianCalendar startDate = event.getTimeCalendar();
		GregorianCalendar endDate = event.getTimeCalendar();

		startDate.set(Calendar.HOUR_OF_DAY, 8);
		startDate.set(Calendar.MINUTE, 0);

		ContentResolver cr1 = this.getContentResolver(); // 添加新event，步骤是固定的
		ContentValues values = new ContentValues();
		values.put(Events.DTSTART, startDate.getTime().getTime()); // 添加提醒时间，即讲座的日期
		values.put(Events.DTEND, endDate.getTime().getTime());
		values.put(Events.TITLE, event.getTitle());
		values.put(Events.DESCRIPTION, event.getSpeaker());
		values.put(Events.EVENT_LOCATION, event.getAddress());
		values.put(Events.CALENDAR_ID, calId);
		values.put(Events.EVENT_TIMEZONE, "GMT+8");
		Uri uri = cr1.insert(Events.CONTENT_URI, values);
		Long eventId = Long.parseLong(uri.getLastPathSegment()); // 获取刚才添加的event的Id

		ContentResolver cr2 = this.getContentResolver(); // 为刚才新添加的event添加reminder
		ContentValues values1 = new ContentValues();
		values1.put(Reminders.MINUTES, 10); // 设置提前几分钟提醒
		values1.put(Reminders.EVENT_ID, eventId);
		values1.put(Reminders.METHOD, Reminders.METHOD_ALERT); // 设置该事件为提醒
		Uri newReminder = cr2.insert(Reminders.CONTENT_URI, values1); // 调用这个方法返回值是一个Uri
		long reminderId = Long.parseLong(newReminder.getLastPathSegment());

		// 记录数据
		event.setReminderInfo(new ReminderInfo(eventId, reminderId));

		// setAlarmDeal(startMillis); //
		// 设置reminder开始的时候，启动另一个activity
		// // 设置全局定时器
		// Intent intent = new Intent(this,
		// AlarmActivity.class);
		// PendingIntent pi =
		// PendingIntent.getActivity(this, 0, intent, 0);
		// AlarmManager aManager = (AlarmManager)
		// getSystemService(Service.ALARM_SERVICE);
		// aManager.set(AlarmManager.RTC_WAKEUP, time, pi);
		// //
		// 当系统调用System.currentTimeMillis()方法返回值与time相同时启动pi对应的组件

		Toast.makeText(this, "添加到 收藏页面&日历 成功", Toast.LENGTH_SHORT).show();
	}

	public void deleteFromCalender() {
		Event event = detail_lectureEvent;
		Uri deleteReminderUri = null;
		Uri deleteEventUri = null;
		deleteReminderUri = ContentUris.withAppendedId(Reminders.CONTENT_URI,
				event.getReminderInfo().getReminderId());
		deleteEventUri = ContentUris.withAppendedId(Events.CONTENT_URI, event
				.getReminderInfo().getEventId());
		int rowR = this.getContentResolver().delete(deleteReminderUri, null,
				null);
		int rowE = this.getContentResolver().delete(deleteEventUri, null, null);
		if (rowE > 0 && rowR > 0) {
			Toast.makeText(this, "从 收藏页面&日历 删除成功", Toast.LENGTH_SHORT).show();
		} else
			Toast.makeText(this, "从 收藏页面&日历 删除失败", Toast.LENGTH_SHORT).show();
	}

	//下面是每一则评论的item模板
	public class DetailItemLinearLayout extends LinearLayout {

		public DetailItemLinearLayout(Context context) {
			super(context);
			// TODO Auto-generated constructor stub

			((Activity) getContext()).getLayoutInflater().inflate(
					R.layout.comment_item, this);
		}

		public DetailItemLinearLayout(Context context, AttributeSet attrs) {
			super(context, attrs);
			// TODO Auto-generated constructor stub
		}

		public DetailItemLinearLayout(Context context, AttributeSet attrs,
				int defStyle) {
			super(context, attrs, defStyle);
			// TODO Auto-generated constructor stub
		}

	}
	
	//下面是评论的foot item 主要用于解决没有任何评论时的显示，以及在有评论时最后一则显示
	public class FootItemLinearLayout extends LinearLayout {

		public FootItemLinearLayout(Context context) {
			super(context);
			// TODO Auto-generated constructor stub

			((Activity) getContext()).getLayoutInflater().inflate(
					R.layout.comment_foot_item, this);
		}
		

		public FootItemLinearLayout(Context context, AttributeSet attrs) {
			super(context, attrs);
			// TODO Auto-generated constructor stub
		}

		public FootItemLinearLayout(Context context, AttributeSet attrs,
				int defStyle) {
			super(context, attrs, defStyle);
			// TODO Auto-generated constructor stub
		}

	}
	
	
	//下面的增加来自咸鱼，用于在评论返回的时候更新评论列表！  2014年8月6日  15:36
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		
		super.onActivityResult(requestCode, resultCode, data);
		
		if( data != null && resultCode==1 ){
			//数据存在，进行获取
			Comment comment = (Comment)data.getSerializableExtra("returnComment");
			
			commentsList.add(comment);

			//下面进行刷新评论列表
			setComments( commentsList );
			
			
			
		}
		
		
	}
	
	//下面是咸鱼的修改，用于添加没有填写邮箱禁止评论的功能  2014年8月6日 23:16
			public Boolean hasEmail(Context context){
				
				SharedPreferences sharedPre = context.getSharedPreferences("config",
						context.MODE_PRIVATE);
				
				if( sharedPre.getString("email", "").equals("") ){
					
					Toast.makeText(context, "Hi,请先到设置中心\"我\"中设置邮箱后便可评论!",Toast.LENGTH_LONG).show();
					return false;
					
				}
				return true;
				
			}
			@Override
			public boolean onKeyDown( int keyCode, KeyEvent event){
				
				if( keyCode == KeyEvent.KEYCODE_BACK){
					finish();
					overridePendingTransition(R.anim.show_in_left, R.anim.hide_in_right	);
					
				}
				
				
				return false;
				
			}
			
			
			//为弹出窗口popShareMenu实现监听类    2014  08   14   09:15
		    private OnClickListener  shareItemsOnClick = new OnClickListener(){

				public void onClick(View v) {
					Intent intent;
					
					switch (v.getId()) {
					case R.id.wechat_share:
						Toast.makeText(DetailView.this, "微信好友", Toast.LENGTH_LONG).show();
						
						
						intent = new  Intent(DetailView.this, WXEntryActivity.class);	
						intent.putExtra("shareEvent", detail_lectureEvent);
						startActivity(intent);
						
						if(popShareMenu != null)
							popShareMenu.dismiss();
						
						
						
						
						break;
					case R.id.wechat_circle_share:
						Toast.makeText(DetailView.this, "微信朋友圈", Toast.LENGTH_LONG).show();
						
						
						intent = new  Intent(DetailView.this, WXEntryActivity.class);	
						intent.putExtra("shareEvent", detail_lectureEvent);
						intent.putExtra("isSharedToSession", false);
						startActivity(intent);
						
						if(popShareMenu != null)
							popShareMenu.dismiss();
						
						
						break;
					case R.id.weibo_share:
						Toast.makeText(DetailView.this, "新浪微博", Toast.LENGTH_LONG).show();
						
						Intent sendIntent = new Intent();
						
						ComponentName comp = new ComponentName("com.sina.weibo", "com.sina.weibo.EditActivity");  
		                sendIntent.setComponent(comp);
		                
		                if( detail_lectureEvent != null){
		                	try { 
		                	sendIntent.setAction(Intent.ACTION_SEND);
		                	//sendIntent.setType("text/plain");
		                	sendIntent.setType("text");
		                	sendIntent.putExtra(Intent.EXTRA_TITLE, "分享");
		                	
		                	sendIntent.putExtra(Intent.EXTRA_TEXT,
		                			"#" + detail_lectureEvent.getAddress().substring(0, 4) + "讲座#"
										+ "【" + detail_lectureEvent.getTitle()+ "】" + " " 
										+ "时间: " + detail_lectureEvent.getCustomTime() +" | " 
										+ "地点: " + "#" + detail_lectureEvent.getAddress().substring(0, 4) + "#" + detail_lectureEvent.getAddress().substring(4) + " | " 
										+ "主讲: " + detail_lectureEvent.getSpeaker() + " | "
										+ "详情点击: "
										+ detail_lectureEvent.getLink()  + " From #厦大讲座App Especially#" );
		                	
		                	sendIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  
				
		                	startActivity(sendIntent);
		                	
		                	}
		                	catch (Exception e) {  
		                        Toast.makeText(DetailView.this, "您的微博未打开 或者 版本过低，未能够分享！", Toast.LENGTH_LONG).show();  
		                    } 
						
		                }
		                
		                if(popShareMenu != null)
							popShareMenu.dismiss();
						
						
						
						break;
					default:
						
						break;
					}
				}
		    };
	

}// end class

