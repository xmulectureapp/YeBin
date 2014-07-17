package com.lecture.lectureapp;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class LectureDetail extends Activity {

	private TextView tvname;
	private TextView tvtime;
	private TextView tvaddr;
	private TextView tvspeaker;
	private TextView tvlabel;
	private TextView tvaboutspeaker;
	private TextView tvmore;
	private ImageView iv1;
	
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lecture_detail);
		
		tvname = (TextView)findViewById(R.id.detail_lecture_name);
		tvtime = (TextView)findViewById(R.id.detail_lecture_time);
		tvaddr = (TextView)findViewById(R.id.detail_lecture_addr);
		tvspeaker = (TextView)findViewById(R.id.detail_lecture_speaker);
		tvlabel = (TextView)findViewById(R.id.detail_lecture_label);
		tvaboutspeaker = (TextView)findViewById(R.id.about_speaker);
		tvmore = (TextView)findViewById(R.id.more_information);
		
		tvname.setText("��ͼͻ�Ƶ��µ���--��ǰ����С˵�ļ�������");
		tvtime.setText("2014��07��16�գ���������15��00��");
		tvaddr.setText("��˼��У��������ѧԺ��ө¥��¥�������");
		tvspeaker.setText("��ҫ��");
		tvlabel.setText("����С˵\\���µ��� ");
		tvaboutspeaker.setText("��ҫ�ţ���ʿ�����ڣ���ʿ����ʦ�����ν����Ͽ���ѧ�������ص���ѧ���о�����ΪӢ����ѧ��1965���ҵ���Ͽ���ѧӢ��ϵ��ͬ�기Ӣ���׶ء����Ž��ޡ�1984��������̹�ն���ѧӢ��ϵ��ʿѧλ����1986����Ϊ�������Ļ���(Paideuma)��־�����༭��1988����ѡӢ�����ʴ������ı���ġ�Զ����̫ƽ������¼���������ѡ��������ʦ¼�������С�������ѧ��ʷ������Ӣ��ѧ��ʷ������������ѧʷ������ϣ�������񻰡���������Ӣ����ѧ�������о���д�����ȣ������С�������ѧѡ������Ӣ����ѧͨʷ���ȡ��ڹ����⿯���Ϸ������ƪ���ġ�");
		tvmore.setText("����ѧ�Ƶ��о�����ϵ�н�����17��");
	}
}
