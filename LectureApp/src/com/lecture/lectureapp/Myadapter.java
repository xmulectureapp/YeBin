package com.lecture.lectureapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lecture.DBCenter.DBCenter;
import com.lecture.lectureapp.R;
import com.lecture.lectureapp.Myadapter.ViewHolder;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
public class Myadapter extends BaseAdapter 
{
	  private LayoutInflater mInflater;  
	  private Context mContext;
	  
	 // private DBCenter dbCenter = new DBCenter(null, MainView.DB_NAME, 1);
	  
	  private Cursor cursor;
	  
	private List<Map<String, Object>> mData; 
	final String[] LTitle = { "An introduction to nonparametric regression", 
			"̨�����������ˮ��Ⱦ�����μ����ֿ�",
			"�й�֤���Թ��иĸ����ߵĴ����뷢չ",
			"Ϸ������ѧ",
			"����ʫ����"};  
    String[] LTime = { "2014��07��08�գ����ڶ���14��30��", 
    		"2014��07��08�գ����ڶ���14��30��", 
    			"2014��07��08�գ����ڶ���16��30��",
    			"2014��07��08�գ����ڶ���19��00��",
    			"2014��07��08�գ����ڶ���19��00��" };  
    String[] LAddr = { "��˼��У��������¥N301",
    		"���谲У������������̬ѧԺA201",
    		"������У�������Ĵ�¥B#301",
    		"��˼��У�����Ϲ�һ214", 
    		"��˼��У��������ѧԺ��¥������" }; 
    String[] LSpeaker = { "Daniel Henderson ������", 
    		"�ֲƸ�����",
    		"����Դ", 
    		"���� ����", 
    		"������" };

	private List<Map<String, Object>> getData(String time, String place, String subject) 
	{  
		/*
		Log.i("SELECT", "��ʼ��ѯ��Ϣ��������");
		Cursor selectCursor = dbCenter.select(dbCenter.getReadableDatabase(), time, place, subject);
		Log.i("SELECT", "���ݿ��ѯ��������");
		//startManagingCursor(selectCursor);
		List<Map<String, Object>> result = dbCenter
				.L_converCursorToList(selectCursor);
		
		return result;
		*/
		Log.i("SELECT", "Cursor�α��ȡ���ݿ�ʼ��");

		List<Map<String, Object>> result = DBCenter
				.L_converCursorToList(cursor);

		return result;
		/*
	    List<Map<String,Object>> listItem = new ArrayList<Map<String,Object>>();
	    for(int i=0;i<5;i++)
	    {
	    	HashMap<String,Object> map = new HashMap<String,Object>();
	    	map.put("lecture_name",LTitle[i]);
	    	map.put("lecture_time","ʱ��: "+LTime[i]);
	    	map.put("lecture_addr","�ص�: "+LAddr[i]);
	    	map.put("lecture_speaker","����: "+LSpeaker[i]);
	    	listItem.add(map);
		}  
	    
		return listItem;  
	  */
	}  
	public void showInfo1(){
		
	
	  new AlertDialog.Builder(mContext)  
	  .setTitle("�ҵ�listview")  
	  .setMessage("����")  
	  .setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {  
	   @Override  
	   public void onClick(DialogInterface dialog, int which) {  
	   }  
	  })  
	  .show();  
	    
	}
	public void showInfo2(){  
		  new AlertDialog.Builder(mContext)  
		  .setTitle("�ҵ�listview")  
		  .setMessage("����")  
		  .setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {  
		   @Override  
		   public void onClick(DialogInterface dialog, int which) {  
		   }  
		  })  
		  .show();  
		    
		}
	public void showInfo3(){  
		  new AlertDialog.Builder(mContext)  
		  .setTitle("�ҵ�listview")  
		  .setMessage("����")  
		  .setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {  
		   @Override  
		   public void onClick(DialogInterface dialog, int which) {  
		   }  
		  })  
		  .show();  
		    
		}
	public void showInfo4(){  
		  new AlertDialog.Builder(mContext)  
		  .setTitle("�ҵ�listview")  
		  .setMessage("�ղ�")  
		  .setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {  
		   @Override  
		   public void onClick(DialogInterface dialog, int which) {  
		   }  
		  })  
		  .show();  
		    
		}
	public final class ViewHolder
	{  
		  public TextView lectureName;  
		  public ImageView line1;  
		  public TextView lectureAddr;  
		  public TextView lectureTime; 
		  public TextView lectureSpeaker; 
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
	public Myadapter(Context context)
	{  

		this.mContext=context;
		this.mInflater = LayoutInflater.from(context);  
		mData=getData(null, null, null);
		this.cursor = null;
	}    
	public Myadapter(Context context, List<Map<String, Object>> list)
	{  

		this.mContext=context;
		this.mInflater = LayoutInflater.from(context);  
		mData = list;
		this.cursor = null;
	}  
	public Myadapter(Context context, Cursor cursor)
	{  

		this.mContext=context;
		this.mInflater = LayoutInflater.from(context);  
		mData=getData(null, null, null);
		this.cursor = cursor;
		
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
		public View getView(int position, View convertView, ViewGroup parent) 
		{
			ViewHolder holder = null;
			if (convertView == null)
			{
				holder=new ViewHolder(); 
				convertView = mInflater.inflate(R.layout.item, null);
				holder.lectureName = (TextView)convertView.findViewById(R.id.lecture_name); 
				holder.line1 = (ImageView)convertView.findViewById(R.id.line_1);
				holder.lectureTime = (TextView)convertView.findViewById(R.id.lecture_time);
				holder.lectureAddr = (TextView)convertView.findViewById(R.id.lecture_addr); 
				holder.lectureSpeaker = (TextView)convertView.findViewById(R.id.lecture_speaker);
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
			}
			else
			{
				holder = (ViewHolder)convertView.getTag(); 
			}
			  holder.lectureName.setText((String)mData.get(position).get("lecture_name"));  
			   holder.lectureTime.setText((String)mData.get(position).get("lecture_time")); 
			 holder.lectureAddr.setText((String)mData.get(position).get("lecture_addr"));  
			  holder.lectureSpeaker.setText((String)mData.get(position).get("lecture_speaker")); 
			 holder.linearlayoutShare.setOnClickListener(new View.OnClickListener() {  
				    public void onClick(View v) {  
				     showInfo1();       
				    }  
				   });  
			 holder.linearlayoutComment.setOnClickListener(new View.OnClickListener() {  
				    public void onClick(View v) {  
				     showInfo2();       
				    }  
				   });
			 holder.linearlayoutLike.setOnClickListener(new View.OnClickListener() {  
				    public void onClick(View v) {  
				     showInfo3();       
				    }  
				   });
			 holder.linearlayoutRemind.setOnClickListener(new View.OnClickListener() {  
				    public void onClick(View v) {  
				     showInfo4();       
				    }  
				   });
			return convertView;
		}
	}


