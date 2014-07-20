package com.lecture.lectureapp;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.lecture.localdata.SubmitLecture;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class SubmitCenter extends Activity {

	EditText string_title, string_speaker, string_address,
			string_speaker_information, string_more_information,
			string_information_source;

	TextView string_time;
	Spinner mSpinner;
	Button btnSubmit;

	int one = 0;

	private int year, monthOfYear, dayOfMonth, hourOfDay, minute;

	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.submitcenter);

		mSpinner = (Spinner) findViewById(R.id.string_campus);
		// ��������Դ
		String[] mItems = { "˼��У��", "�谲У��", "����У��", "��ͼ���" };
		// ����Adapter���Ұ�����Դ
		ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_dropdown_item_1line, mItems);
		// ����������ʽ
		// mAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		// �� Adapter���ؼ�
		mSpinner.setAdapter(mAdapter);

		/*
		 * mSpinner = (Spinner) findViewById(R.id.string_campus); List<String>
		 * mList = new ArrayList<String>(); mList.add("˼��У��");
		 * mList.add("�谲У��"); mList.add("����У��"); mList.add("��ͼ���");
		 * 
		 * SpinnerAdapter mAdapter = new SpinnerAdapter(this, mList);
		 * mSpinner.setAdapter(mAdapter);
		 */

		// ѡȡʱ���
		Calendar calendar = Calendar.getInstance();
		year = calendar.get(Calendar.YEAR);
		monthOfYear = calendar.get(Calendar.MONTH);
		dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
		hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
		minute = calendar.get(Calendar.MINUTE);

		string_time = (TextView) findViewById(R.id.string_time);
		string_time.setOnClickListener(new OnClickListener() {

			String time;

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				string_time.setText("");
				time = new String("");

				// one++;
				// Toast.makeText(getApplicationContext(), one + "",
				// Toast.LENGTH_SHORT).show();
				TimePickerDialog timePickerDialog = new TimePickerDialog(
						SubmitCenter.this,
						new TimePickerDialog.OnTimeSetListener() {
							@Override
							public void onTimeSet(TimePicker view,
									int hourOfDay, int minute) {

								if (one % 2 == 0) {
									time += " " + hourOfDay + ":" + minute;

									string_time.setText(time);

									// Toast.makeText(getApplicationContext(),
									// time, Toast.LENGTH_SHORT).show();
								}

								one++;
							}

						}, hourOfDay, minute, true);

				timePickerDialog.show();

				DatePickerDialog datePickerDialog = new DatePickerDialog(
						SubmitCenter.this,
						new DatePickerDialog.OnDateSetListener() {
							@Override
							public void onDateSet(DatePicker view, int year,
									int monthOfYear, int dayOfMonth) {
								if (one % 2 == 0) {
									time += " " + year + "-"
											+ (monthOfYear + 1) + "-"
											+ dayOfMonth;

									string_time.setText(time);
									// Toast.makeText(getApplicationContext(),
									// time, Toast.LENGTH_SHORT).show();
								}

								one++;
							}
						}, year, monthOfYear, dayOfMonth);

				datePickerDialog.show();

			}

		});

		btnSubmit = (Button) findViewById(R.id.btn_submit);
		btnSubmit.setOnClickListener(new OnClickListener() {

			boolean isOK;

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				isOK = true;
				SubmitLecture sl = new SubmitLecture();
				if (!TextUtils.isEmpty(string_title.getText())) {
					sl.setTitle(string_title.getText().toString());
				} else {
					Toast.makeText(getApplicationContext(), "���ⲻ��Ϊ��",
							Toast.LENGTH_SHORT).show();
					isOK = false;
				}
				
				if (!TextUtils.isEmpty(string_speaker.getText())) {
					sl.setTitle(string_speaker.getText().toString());
				} else {
					Toast.makeText(getApplicationContext(), "�����˲���Ϊ��",
							Toast.LENGTH_SHORT).show();
					isOK = false;
				}
				
				if (!TextUtils.isEmpty(string_time.getText())) {
					sl.setTime(string_time.getText().toString());
				} else {
					Toast.makeText(getApplicationContext(), "ʱ�䲻��Ϊ��",
							Toast.LENGTH_SHORT).show();
					isOK = false;
				}
				
				sl.setCampus(mSpinner.getSelectedItem().toString());
				
				if (!TextUtils.isEmpty(string_address.getText())) {
					sl.setAddress(string_address.getText().toString());
				} else {
					Toast.makeText(getApplicationContext(), "��ϸ�ص㲻��Ϊ��",
							Toast.LENGTH_SHORT).show();
					isOK = false;
				}
				
				if (!TextUtils.isEmpty(string_speaker_information.getText())) {
					sl.setSpeaker_information(string_speaker_information.getText().toString());
				} else {
					Toast.makeText(getApplicationContext(), "��������Ϣ����Ϊ��",
							Toast.LENGTH_SHORT).show();
					isOK = false;
				}
				
				if (!TextUtils.isEmpty(string_more_information.getText())) {
					sl.setMore_information(string_more_information.getText().toString());
				} else {
					Toast.makeText(getApplicationContext(), "������Ϣ����Ϊ��",
							Toast.LENGTH_SHORT).show();
					isOK = false;
				}
				
				if (!TextUtils.isEmpty(string_information_source.getText())) {
					sl.setInformation_source(string_information_source.getText().toString());
				} else {
					Toast.makeText(getApplicationContext(), "��Ϣ��Դ����Ϊ��",
							Toast.LENGTH_SHORT).show();
					isOK = false;
				}

				if (isOK) {
					Toast.makeText(getApplicationContext(), "�ύ���",
							Toast.LENGTH_SHORT).show();
				}

			}

		});

	}
}
