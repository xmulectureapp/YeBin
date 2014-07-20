package com.lecture.lectureapp;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SpinnerAdapter extends BaseAdapter {

	private List<String> mList;
	private Context mContext;
	
	public SpinnerAdapter(Context cContext, List<String> cList){
		this.mList = cList;
		this.mContext = cContext;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater mLayoutInflater = LayoutInflater.from(mContext);  //��ȡ
		convertView = mLayoutInflater.inflate(R.layout.spinner_item, null);
		/*LayoutInflater.inflate()��Layout�ļ�ת��ΪView��ר�Ź�Layoutʹ�õ�Inflater��
		��ȻLayoutҲ��View�����࣬����android������뽫xml�е�Layoutת��ΪView����.java������
		������ֻ��ͨ��Inflater��������ͨ��findViewById()��*/
		
		if(convertView != null){
			TextView txtName = (TextView)convertView.findViewById(R.id.txtName);
			txtName.setText(mList.get(position));
		}
		
		return convertView;
	}

}
