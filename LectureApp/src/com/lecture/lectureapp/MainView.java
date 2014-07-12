package com.lecture.lectureapp;

import java.util.ArrayList;
import java.util.HashMap;




import java.util.List;

import com.lecture.DBCenter.ListToDB;
import com.lecture.DBCenter.XMLToList;
import com.lecture.lectureapp.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MainView extends Activity 
{
	public static MainView instance = null;
	
	//���ݿ�
	public static final String DB_NAME = "LectureDB";
	private ListToDB listToDB = new ListToDB(this, DB_NAME, 1);
	 
	private ViewPager mTabPager;	
	private ImageView mTabImg;// ����ͼƬ
	private ImageView mTab1,mTab2,mTab3,mTab4;
	private int zero = 0;// ����ͼƬƫ����
	private int currIndex = 0;// ��ǰҳ�����
	private int one;//����ˮƽ����λ��
	private int two;
	private int three;
	private LinearLayout mClose;
    private LinearLayout mCloseBtn;
    private View layout;	
	private boolean menu_display = false;
	private PopupWindow menuWindow;
	private LayoutInflater inflater;
	//private Button mRightBtn;
	
	private ImageView mTab5;
	private int four;
	
	//��ȡmain menu�µ����ֱ���
	private TextView mText1;
	private TextView mText2;
	private TextView mText3;
	private TextView mText4;
	private TextView mText5;
	private Myadapter myadapter;
	
	//mainview top button
	private TextView topTextView;
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);	
		setContentView(R.layout.mainview);
		
		 //����activityʱ���Զ����������
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN); 
        instance = this;
        /*
        mRightBtn = (Button) findViewById(R.id.right_btn);
        mRightBtn.setOnClickListener(new Button.OnClickListener()
		{	@Override
			public void onClick(View v)
			{	showPopupWindow (MainWeixin.this,mRightBtn);
			}
		  });*/
        
        mTabPager = (ViewPager)findViewById(R.id.tabpager);
        mTabPager.setOnPageChangeListener(new MyOnPageChangeListener());
        
        mTab1 = (ImageView) findViewById(R.id.img_subscribecenter);
        mTab2 = (ImageView) findViewById(R.id.img_hotlecturecenter);
        mTab3 = (ImageView) findViewById(R.id.img_noticecenter);
        mTab4 = (ImageView) findViewById(R.id.img_submitcenter);
        
        mTab5 = (ImageView) findViewById(R.id.img_mycenter);
        
        mTabImg = (ImageView) findViewById(R.id.img_tab_now);
        mTab1.setOnClickListener(new MyOnClickListener(0));
        mTab2.setOnClickListener(new MyOnClickListener(1));
        
        mTab3.setOnClickListener(new MyOnClickListener(2));
        mTab4.setOnClickListener(new MyOnClickListener(3));
        
        mTab5.setOnClickListener(new MyOnClickListener(4));
        
        //��ȡ�˵����־��
        mText1 = (TextView)findViewById(R.id.mText1);
        mText2 = (TextView)findViewById(R.id.mText2);
        mText3 = (TextView)findViewById(R.id.mText3);
        mText4 = (TextView)findViewById(R.id.mText4);
        mText5 = (TextView)findViewById(R.id.mText5);
        
        //�����ȡmianview�����ɸѡ������ť�ľ��
        topTextView = (TextView)findViewById(R.id.filterTextView);
        OnClickListener listener = 
                
                new OnClickListener() {
        			
        			@Override
        			public void onClick(View arg0) {
        				//Intent intent = new Intent(MainView.this,RefreshCenter.class);
        				//startActivity(intent);
        				//Intent intent = new Intent(MainView.this,
        					//	RefreshCenter.class);
        				//startActivity(intent);
        				
        				//XML TO List, then to db
        				XMLToList xmlToList = new XMLToList();
        				xmlToList.insertListToDB(MainView.this, listToDB, "LectureTable");
        				
        				
        				
        			}
        		};
        topTextView.setOnClickListener(listener);
        
       
        
        
        Display currDisplay = getWindowManager().getDefaultDisplay();//��ȡ��Ļ��ǰ�ֱ���
        int displayWidth = currDisplay.getWidth();
        int displayHeight = currDisplay.getHeight();
        //one = displayWidth/4; //����ˮƽ����ƽ�ƴ�С
        one = displayWidth/5;
        two = one*2;
        three = one*3;
        four = one*4;
        //Log.i("info", "��ȡ����Ļ�ֱ���Ϊ" + one + two + three + "X" + displayHeight);
        
        //InitImageView();//ʹ�ö���
      //��Ҫ��ҳ��ʾ��Viewװ��������
        LayoutInflater mLi = LayoutInflater.from(this);
        View view1 = mLi.inflate(R.layout.subscribecenter, null);
        View view2 = mLi.inflate(R.layout.hotlecturecenter, null);
        View view3 = mLi.inflate(R.layout.noticecenter, null);
        View view4 = mLi.inflate(R.layout.submitcenter, null);
        
        View view5 = mLi.inflate(R.layout.mycenter, null);
        ListView list = (ListView) view2.findViewById(R.id.hot_ListView);//��hot_ListViewת������
       //�����϶�item��Ĭ�ϵ����ʾ��ɫ���иı�, ��Ĭ�ϵ��Ч��ȡ��
        list.setSelector(getResources().getDrawable(R.drawable.item_none_selector));
        
        
      //ÿ��ҳ���view����
        final ArrayList<View> views = new ArrayList<View>();
        views.add(view1);
        views.add(view2);
        views.add(view3);
        views.add(view4);
        
        views.add(view5);
        
      //���ViewPager������������
        PagerAdapter mPagerAdapter = new PagerAdapter() {
			
			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				return arg0 == arg1;
			}
			
			@Override
			public int getCount() {
				return views.size();
			}

			@Override
			public void destroyItem(View container, int position, Object object) {
				((ViewPager)container).removeView(views.get(position));
			}
			
			//@Override
			//public CharSequence getPageTitle(int position) {
				//return titles.get(position);
			//}
			
			@Override
			public Object instantiateItem(View container, int position) {
				((ViewPager)container).addView(views.get(position));
				return views.get(position);
			}
			
	
		};
		
		mTabPager.setAdapter(mPagerAdapter);
		
		//����Yao�ĸ��� 2014��7��7��
		myadapter = new Myadapter(this);
		list.setAdapter(myadapter);
		// ListView ��ĳ�ѡ�к���߼�  
		 list.setOnItemClickListener(new OnItemClickListener() {  
		        
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
						long arg3) {
					// TODO Auto-generated method stub
					Toast.makeText(MainView.this,"��ѡ���˽�����" 
					+ myadapter.LTitle[arg2],Toast.LENGTH_LONG ).show(); 
					 
				}  
		    });
		
		/*final String[] LTitle = { "An introduction to nonparametric regression", 
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
		ArrayList<HashMap<String,Object>> listItem = new ArrayList<HashMap<String,Object>>();
	    for(int i=0;i<5;i++)
	    {
	    	HashMap<String,Object> map = new HashMap<String,Object>();
	    	map.put("lecture_name",LTitle[i]);
	    	map.put("lecture_time","ʱ��: "+LTime[i]);
	    	map.put("lecture_addr","�ص�: "+LAddr[i]);
	    	map.put("lecture_speaker","����: "+LSpeaker[i]);
	    	listItem.add(map);
		}
	    //����������
	    SimpleAdapter listItemAdapter = new SimpleAdapter(this,listItem,R.layout.item,
	    		new String[]{"lecture_name","lecture_time","lecture_addr","lecture_speaker"},
	    		new int[]{R.id.lecture_name,R.id.lecture_time,R.id.lecture_addr,R.id.lecture_speaker
	    		});
	   // list.setAdapter(listItemAdapter);
	    list.setOnItemClickListener(new OnItemClickListener() {  
	        
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				 Toast.makeText(MainView.this,"��ѡ���˽�����" 
				+ LTitle[arg2],Toast.LENGTH_LONG ).show();  
			}  
	    });*/  
	}
	
	/**
	 * ͷ��������
	 */
	public class MyOnClickListener implements View.OnClickListener {
		private int index = 0;

		public MyOnClickListener(int i) {
			index = i;
		}
		@Override
		public void onClick(View v) {
			mTabPager.setCurrentItem(index);
		}
	};
    
	 /* ҳ���л�����(ԭ����:D.Winter)
	 */
	public class MyOnPageChangeListener implements OnPageChangeListener {
		@Override
		public void onPageSelected(int arg0) {
			Animation animation = null;
			switch (arg0) {
			case 0:
				mTab1.setImageDrawable(getResources().getDrawable(R.drawable.subscribecenter_pressed));
				mText1.setTextColor(getResources().getColor(R.color.main_menu_pressed));
				if (currIndex == 1) {
					animation = new TranslateAnimation(one, 0, 0, 0);
					mTab2.setImageDrawable(getResources().getDrawable(R.drawable.hotlecturecenter_normal));
					mText2.setTextColor(getResources().getColor(R.color.main_menu_normal));
				} else if (currIndex == 2) {
					animation = new TranslateAnimation(two, 0, 0, 0);
					mTab3.setImageDrawable(getResources().getDrawable(R.drawable.noticecenter_normal));
					mText3.setTextColor(getResources().getColor(R.color.main_menu_normal));
				}
				else if (currIndex == 3) {
					animation = new TranslateAnimation(three, 0, 0, 0);
					mTab4.setImageDrawable(getResources().getDrawable(R.drawable.submitcenter_normal));
					mText4.setTextColor(getResources().getColor(R.color.main_menu_normal));
				}else if (currIndex == 4) {
					animation = new TranslateAnimation(four, 0, 0, 0);
					mTab5.setImageDrawable(getResources().getDrawable(R.drawable.mycenter_normal));
					mText5.setTextColor(getResources().getColor(R.color.main_menu_normal));
				}
				break;
			case 1:
				mTab2.setImageDrawable(getResources().getDrawable(R.drawable.hotlecturecenter_pressed));
				mText2.setTextColor(getResources().getColor(R.color.main_menu_pressed));
				if (currIndex == 0) {
					animation = new TranslateAnimation(zero, one, 0, 0);
					mTab1.setImageDrawable(getResources().getDrawable(R.drawable.subscribecenter_normal));
					mText1.setTextColor(getResources().getColor(R.color.main_menu_normal));
				} else if (currIndex == 2) {
					animation = new TranslateAnimation(two, one, 0, 0);
					mTab3.setImageDrawable(getResources().getDrawable(R.drawable.noticecenter_normal));
					mText3.setTextColor(getResources().getColor(R.color.main_menu_normal));
				}
				else if (currIndex == 3) {
					animation = new TranslateAnimation(three, one, 0, 0);
					mTab4.setImageDrawable(getResources().getDrawable(R.drawable.submitcenter_normal));
					mText4.setTextColor(getResources().getColor(R.color.main_menu_normal));
				}else if (currIndex == 4) {
					animation = new TranslateAnimation(four, one, 0, 0);
					mTab5.setImageDrawable(getResources().getDrawable(R.drawable.mycenter_normal));
					mText5.setTextColor(getResources().getColor(R.color.main_menu_normal));
				}
				break;
			case 2:
				mTab3.setImageDrawable(getResources().getDrawable(R.drawable.noticecenter_pressed));
				mText3.setTextColor(getResources().getColor(R.color.main_menu_pressed));
				if (currIndex == 0) {
					animation = new TranslateAnimation(zero, two, 0, 0);
					mTab1.setImageDrawable(getResources().getDrawable(R.drawable.subscribecenter_normal));
					mText1.setTextColor(getResources().getColor(R.color.main_menu_normal));
				} else if (currIndex == 1) {
					animation = new TranslateAnimation(one, two, 0, 0);
					mTab2.setImageDrawable(getResources().getDrawable(R.drawable.hotlecturecenter_normal));
					mText2.setTextColor(getResources().getColor(R.color.main_menu_normal));
				}
				else if (currIndex == 3) {
					animation = new TranslateAnimation(three, two, 0, 0);
					mTab4.setImageDrawable(getResources().getDrawable(R.drawable.submitcenter_normal));
					mText4.setTextColor(getResources().getColor(R.color.main_menu_normal));
				}else if (currIndex == 4) {
					animation = new TranslateAnimation(four, two, 0, 0);
					mTab5.setImageDrawable(getResources().getDrawable(R.drawable.mycenter_normal));
					mText5.setTextColor(getResources().getColor(R.color.main_menu_normal));
				}
				break;
			case 3:
				mTab4.setImageDrawable(getResources().getDrawable(R.drawable.submitcenter_pressed));
				mText4.setTextColor(getResources().getColor(R.color.main_menu_pressed));
				if (currIndex == 0) {
					animation = new TranslateAnimation(zero, three, 0, 0);
					mTab1.setImageDrawable(getResources().getDrawable(R.drawable.subscribecenter_normal));
					mText1.setTextColor(getResources().getColor(R.color.main_menu_normal));
				} else if (currIndex == 1) {
					animation = new TranslateAnimation(one, three, 0, 0);
					mTab2.setImageDrawable(getResources().getDrawable(R.drawable.hotlecturecenter_normal));
					mText2.setTextColor(getResources().getColor(R.color.main_menu_normal));
				}
				else if (currIndex == 2) {
					animation = new TranslateAnimation(two, three, 0, 0);
					mTab3.setImageDrawable(getResources().getDrawable(R.drawable.noticecenter_normal));
					mText3.setTextColor(getResources().getColor(R.color.main_menu_normal));
				}
				else if (currIndex == 4) {
					animation = new TranslateAnimation(four, three, 0, 0);
					mTab5.setImageDrawable(getResources().getDrawable(R.drawable.mycenter_normal));
					mText5.setTextColor(getResources().getColor(R.color.main_menu_normal));
				}
				break;
			case 4:
				mTab5.setImageDrawable(getResources().getDrawable(R.drawable.mycenter_pressed));
				mText5.setTextColor(getResources().getColor(R.color.main_menu_pressed));
				if (currIndex == 0) {
					animation = new TranslateAnimation(zero, four, 0, 0);
					mTab1.setImageDrawable(getResources().getDrawable(R.drawable.subscribecenter_normal));
					mText1.setTextColor(getResources().getColor(R.color.main_menu_normal));
				} else if (currIndex == 1) {
					animation = new TranslateAnimation(one, four, 0, 0);
					mTab2.setImageDrawable(getResources().getDrawable(R.drawable.hotlecturecenter_normal));
					mText2.setTextColor(getResources().getColor(R.color.main_menu_normal));
				}
				else if (currIndex == 2) {
					animation = new TranslateAnimation(two, four, 0, 0);
					mTab3.setImageDrawable(getResources().getDrawable(R.drawable.noticecenter_normal));
					mText3.setTextColor(getResources().getColor(R.color.main_menu_normal));
				}else if (currIndex == 3) {
					animation = new TranslateAnimation(three, four, 0, 0);
					mTab4.setImageDrawable(getResources().getDrawable(R.drawable.submitcenter_normal));
					mText4.setTextColor(getResources().getColor(R.color.main_menu_normal));
				}
				break;
			}
			currIndex = arg0;
			animation.setFillAfter(true);// True:ͼƬͣ�ڶ�������λ��
			animation.setDuration(150);
			mTabImg.startAnimation(animation);
		}
		
		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}
	}
	
//	@Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//    	if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {  //��ȡ back��
//    		
//        	if(menu_display){         //��� Menu�Ѿ��� ���ȹر�Menu
//        		menuWindow.dismiss();
//        		menu_display = false;
//        		}
//        	else {
//        		Intent intent = new Intent();
//            	intent.setClass(MainWeixin.this,Exit.class);
//            	startActivity(intent);
//        	}
//    	}
//    	
//    	else if(keyCode == KeyEvent.KEYCODE_MENU){   //��ȡ Menu��			
//			if(!menu_display){
//				//��ȡLayoutInflaterʵ��
//				inflater = (LayoutInflater)this.getSystemService(LAYOUT_INFLATER_SERVICE);
//				//�����main��������inflate�м����Ŷ����ǰ����ֱ��this.setContentView()�İɣ��Ǻ�
//				//�÷������ص���һ��View�Ķ����ǲ����еĸ�
//				layout = inflater.inflate(R.layout.main_menu, null);
//				
//				//��������Ҫ�����ˣ����������ҵ�layout���뵽PopupWindow���أ������ܼ�
//				menuWindow = new PopupWindow(layout,LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT); //������������width��height
//				//menuWindow.showAsDropDown(layout); //���õ���Ч��
//				//menuWindow.showAsDropDown(null, 0, layout.getHeight());
//				menuWindow.showAtLocation(this.findViewById(R.id.mainweixin), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0); //����layout��PopupWindow����ʾ��λ��
//				//��λ�ȡ����main�еĿؼ��أ�Ҳ�ܼ�
//				mClose = (LinearLayout)layout.findViewById(R.id.menu_close);
//				mCloseBtn = (LinearLayout)layout.findViewById(R.id.menu_close_btn);
//				
//				
//				//�����ÿһ��Layout���е����¼���ע��ɡ�����
//				//���絥��ĳ��MenuItem��ʱ�����ı���ɫ�ı�
//				//����׼����һЩ����ͼƬ������ɫ
//				mCloseBtn.setOnClickListener (new View.OnClickListener() {					
//					@Override
//					public void onClick(View arg0) {						
//						//Toast.makeText(Main.this, "�˳�", Toast.LENGTH_LONG).show();
//						Intent intent = new Intent();
//			        	intent.setClass(MainWeixin.this,Exit.class);
//			        	startActivity(intent);
//			        	menuWindow.dismiss(); //��Ӧ����¼�֮��ر�Menu
//					}
//				});				
//				menu_display = true;				
//			}else{
//				//�����ǰ�Ѿ�Ϊ��ʾ״̬������������
//				menuWindow.dismiss();
//				menu_display = false;
//				}
//			
//			return false;
//		}
//    	return false;
//    }
//	//���ñ������Ҳఴť������
//	public void btnmainright(View v) {  
//		Intent intent = new Intent (MainWeixin.this,MainTopRightDialog.class);			
//		startActivity(intent);	
//		//Toast.makeText(getApplicationContext(), "����˹��ܰ�ť", Toast.LENGTH_LONG).show();
//      }  	
//	public void startchat(View v) {      //С��  �Ի�����
//		Intent intent = new Intent (MainWeixin.this,ChatActivity.class);			
//		startActivity(intent);	
//		//Toast.makeText(getApplicationContext(), "��¼�ɹ�", Toast.LENGTH_LONG).show();
//      }  
//	public void exit_settings(View v) {                           //�˳�  α���Ի��򡱣���ʵ��һ��activity
//		Intent intent = new Intent (MainWeixin.this,ExitFromSettings.class);			
//		startActivity(intent);	
//	 }
//	public void btn_shake(View v) {                                   //�ֻ�ҡһҡ
//		Intent intent = new Intent (MainWeixin.this,ShakeActivity.class);			
//		startActivity(intent);	
//	}
	
	

}
