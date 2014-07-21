package com.lecture.lectureapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lecture.DBCenter.DBCenter;
import com.lecture.lectureapp.R;
import com.lecture.lectureapp.RemindMyadapter.ViewHolder;
import com.lecture.localdata.Event;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class RemindMyadapter extends BaseAdapter  
{
	  private LayoutInflater mInflater;  
	  private Context mContext;
	  private Event event;
	  private List<Event> mData; 
	  private DBCenter dbCenter;
	

	
	public final class ViewHolder
	{  
		  public TextView lectureName;  
		  public ImageView line1;  
		  public TextView lectureAddr;  
		  public TextView lectureTime; 
		  public TextView lectureSpeaker; 
		  public LinearLayout linearlayoutid;
		  public TextView lectureId;
		  public ImageView line2;  
		  public ImageView shareIcon; 
		  public TextView shareText; 
		  public ImageView line3; 
		  public ImageView commentIcon; 
		  public TextView commentText; 
		  public ImageView line4;
		  public ImageView likeIcon; 
		  public TextView likeText;
		  public ImageView line5;
		  public ImageView remindIcon; 
		  public TextView remindText;
		  public LinearLayout linearlayoutShare;
		  public LinearLayout linearlayoutComment;
		  public LinearLayout linearlayoutLike;
		  public LinearLayout linearlayoutRemind;
		  
	}
	//���ڸ���ListViewʱ
	public void setMData(List<Event> list){
		
		mData = list;	
	}
	//��������DBCenter
	public void setDBCenter(DBCenter dbCenter){
		
		this.dbCenter = dbCenter;	
	}
	
	
	
	public RemindMyadapter(Context context)
	{  

		this.mContext=context;
		this.mInflater = LayoutInflater.from(context);  
		
	}  
	    
	public RemindMyadapter(Context context, List<Event> list)
	{  

		this.mContext=context;
		this.mInflater = LayoutInflater.from(context);  
		mData = list;
	}  

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mData.size();
		}
		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}
		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}
		@Override
		public View getView(final int position, View convertView, ViewGroup parent) 
		{
			ViewHolder holder = null;
			//if (convertView == null)
			//{
				holder=new ViewHolder(); 
				convertView = mInflater.inflate(R.layout.item, null);
				holder.lectureName = (TextView)convertView.findViewById(R.id.lecture_name); 
				holder.line1 = (ImageView)convertView.findViewById(R.id.line_1);
				holder.lectureTime = (TextView)convertView.findViewById(R.id.lecture_time);
				holder.lectureAddr = (TextView)convertView.findViewById(R.id.lecture_addr); 
				holder.linearlayoutid = (LinearLayout)convertView.findViewById(R.id.linearlayout_id);
				holder.lectureSpeaker = (TextView)convertView.findViewById(R.id.lecture_speaker);
				holder.lectureId = (TextView)convertView.findViewById(R.id.lecture_id); 
				holder.line2 = (ImageView)convertView.findViewById(R.id.line_2);
				holder.shareIcon = (ImageView)convertView.findViewById(R.id.share_icon);
				holder.shareText = (TextView)convertView.findViewById(R.id.share_text);
				holder.line3 = (ImageView)convertView.findViewById(R.id.line_3);
				holder.commentIcon = (ImageView)convertView.findViewById(R.id.comment_icon);
				holder.commentText = (TextView)convertView.findViewById(R.id.comment_text);
				holder.line4 = (ImageView)convertView.findViewById(R.id.line_4);
				holder.likeIcon = (ImageView)convertView.findViewById(R.id.like_icon);
				holder.likeText = (TextView)convertView.findViewById(R.id.like_text);
				holder.line5 = (ImageView)convertView.findViewById(R.id.line_5);
				holder.remindIcon = (ImageView)convertView.findViewById(R.id.remind_icon);
				holder.remindText = (TextView)convertView.findViewById(R.id.remind_text);
				holder.linearlayoutShare = (LinearLayout)convertView.findViewById(R.id.linearlayout_share);
				holder.linearlayoutComment = (LinearLayout)convertView.findViewById(R.id.linearlayout_comment);
				holder.linearlayoutLike = (LinearLayout)convertView.findViewById(R.id.linearlayout_like);
				holder.linearlayoutRemind = (LinearLayout)convertView.findViewById(R.id.linearlayout_remind);
				convertView.setTag(holder); 
			//}
			//else
			//{
				holder = (ViewHolder)convertView.getTag(); 
			//}
			  holder.lectureName.setText(mData.get(position).getTitle());  
			  holder.lectureTime.setText("ʱ��: " + mData.get(position).getTime()); 
			  holder.lectureAddr.setText("�ص�: " + mData.get(position).getAddress());  
			  holder.lectureSpeaker.setText("����: " + mData.get(position).getSpeaker()); 
			  holder.lectureId.setText(mData.get(position).getUid());
			  
			  final ImageView likeIcon_change = holder.likeIcon;
			  final TextView likeText_change = holder.likeText;
			  final ImageView remindIcon_change = holder.remindIcon;
			  final TextView remindText_change = holder.remindText;
			  event = mData.get(position);
			  
			  //����Ĵ�������ʵ�ֵ��޺���ʧ��BUG,Yao,  ���꣬�����㻹�ǲ���ѽ�����Ƿ����ɣ�^ ^
			  if(mData.get(position).isLike()){
				  holder.likeIcon.setImageDrawable(convertView.getResources().getDrawable(R.drawable.like_red));
				  
			  }
			  else {
				  holder.likeIcon.setImageDrawable(convertView.getResources().getDrawable(R.drawable.like));
				  
			  }
			  //����BUG�Խ����You still have a long way to go, no a lot BUGs to go! ahahaha!
			 
			  //����Ĵ���ʹ���� �ղذ�ť
			  if(mData.get(position).isReminded()){
				  holder.remindIcon.setImageDrawable(convertView.getResources().getDrawable(R.drawable.remind_red));
				  
			  }
			  else {
				  holder.remindIcon.setImageDrawable(convertView.getResources().getDrawable(R.drawable.remind));
				  
			  }
			  
		
			 holder.linearlayoutShare.setOnClickListener(new View.OnClickListener() {  
				    public void onClick(View v) {  
				     //showInfo1();
				    	event = mData.get(position);
				    	Intent sendIntent = new Intent();
						sendIntent.setAction(Intent.ACTION_SEND);
						//sendIntent.setType("text/plain");
						sendIntent.setType("image/jpg");
						sendIntent.putExtra(Intent.EXTRA_TITLE, "����");
						sendIntent.putExtra(Intent.EXTRA_TEXT,
								"Hi���������һ����Ȥ�Ľ�����" + "\n"
								+ "���⣺" + event.getTitle()+ "\n" 
								+ "ʱ�䣺" + event.getTime()+"\n" 
								+ "�ص㣺" + event.getAddress()+ "\n" 
								+ "������" + event.getSpeaker() + "\n"
								+ "�������ô�������" + "\n"
								+ event.getLink());
						//sendIntent.putExtra(Intent.EXTRA_TEXT, event.getAddress());
						//sendIntent.putExtra(Intent.EXTRA_TEXT, event.getSpeaker());
						sendIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  
				
						mContext.startActivity(sendIntent);
				    	
				    }  
				   });  
			 holder.linearlayoutComment.setOnClickListener(new View.OnClickListener() {  
				    public void onClick(View v) {  
				  //   showInfo2();
				    	event = mData.get(position);
						//�Ѹ�������Ӧ��event����Bundle������KunCheng
						Bundle detail_bundle = new Bundle();
						detail_bundle.putSerializable("LectureComment", event);
						Intent intent = new Intent(mContext, Comment.class);
						intent.putExtras(detail_bundle);
						mContext.startActivity(intent);
				    	
				    	
				    }  
				   });
			 holder.linearlayoutLike.setOnClickListener(new View.OnClickListener() {  
				    public void onClick(View v) {  
				    // showInfo3();      
				    	 event = mData.get(position);
						 
				    	event.setLike(!event.isLike());
				    	if (event.isLike())
				    	{
				    		likeIcon_change.setImageDrawable(v.getResources().getDrawable(R.drawable.like_red));
				    		likeText_change.setTextColor(v.getResources().getColor(R.color.main_menu_pressed));
				    		//ϲ���Ļ����������ݱ�LikeTable����
				    		DBCenter.setLike(dbCenter.getReadableDatabase(), event.getUid(), true);
				    	}
						else
						{
							likeIcon_change.setImageDrawable(v.getResources().getDrawable(R.drawable.like));
							likeText_change.setTextColor(v.getResources().getColor(R.color.main_menu_normal));
							//ϲ���Ļ����������ݱ�LikeTable����
				    		DBCenter.setLike(dbCenter.getReadableDatabase(), event.getUid(), false);
						}
				    }  
				   });
			 holder.linearlayoutRemind.setOnClickListener(new View.OnClickListener() {  
				    public void onClick(View v) {  
				     //showInfo4();    
				    	event = mData.get(position);
				    	event.setReminded(!event.isReminded());
				    	if (event.isReminded())
				    	{
				    		remindIcon_change.setImageDrawable(v.getResources().getDrawable(R.drawable.remind_red));
				    		remindText_change.setTextColor(v.getResources().getColor(R.color.main_menu_pressed));
				    		//�ղصĻ����������ݱ�CollectionTable����
				    		DBCenter.setRemind(dbCenter.getReadableDatabase(), event.getUid(), true);
				    	}
				    	else
				    	{
				    		remindIcon_change.setImageDrawable(v.getResources().getDrawable(R.drawable.remind));
				    		remindText_change.setTextColor(v.getResources().getColor(R.color.main_menu_normal));
				    		//�ղصĻ����������ݱ�CollectionTable����
				    		DBCenter.setRemind(dbCenter.getReadableDatabase(), event.getUid(), false);
				    	}	
				    }  
				   });
			return convertView;
		}
}
