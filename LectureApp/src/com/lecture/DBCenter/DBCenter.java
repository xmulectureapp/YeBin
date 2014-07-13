package com.lecture.DBCenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lecture.lectureapp.Myadapter;
import com.lecture.localdata.Event;

import android.R.integer;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBCenter extends SQLiteOpenHelper {
	
	
	// --------------------LECTURE_TABLE, Total:13 ROWS---------------------------//
		private static String LECTURE_ID = "Lid";// ����ID==>primary key
		private static String LECTURE_UID = "Luid";// �����ϻ�ȡ����Ϊ������Ψһ��ʶ
		//private static String TITLE = "Ltitle";// ��������
		private static String LECTURE_NAME = "Lwhat";// ������ϸ����
		private static String LECTURE_DATE = "Lwhen";// ����ʱ��
		private static String PLACE = "Lwhere";// ����У��
		private static String SPEAKER = "Lwho";// ������
		//private static String SPEAKER_MESSAGE = "whomsg";// ��������ϸ��Ϣ
		//private static String DATE_MESSAGE = "whatmsg";// ������ϸ����
		//private static String PLACE_MESSAGE = "wheremsg";// ������ϸ�ص�
		//private static String SUBJECT = "sub";// ��������
		//private static String TAG = "tag";// ������ǩ
		private static String LINK = "link";// ��������
		
		
		private static final String LECTURE_TABLE = "LectureTable";// ������Ϣ����
		
		
		
		// -------------------���ڴ洢�����ݿ��ж�ȡ����ϸ��Ϣ����---------//
		
		//--------------------------
		private String msg_luid;
		private String msg_what;

		private String msg_when;

		private String msg_where;

		private String msg_who;
		
		
		// ------------------------------�ַ���--������������Ϣ��-------------------//
		private static final String LTABLE_CREATE = "create table " + LECTURE_TABLE
				+ "(" + LECTURE_ID + " integer primary key autoincrement,"
				+ LECTURE_UID + " varchar(64) UNIQUE," + LECTURE_NAME + " varchar(64)," 
				+ LECTURE_DATE + " varchar(64)," + PLACE + " varchar(64)," + SPEAKER + " varchar(64),"
				+ LINK + " varchar(64)"
				+ ")";
		// ------------------------------�ַ���--������������Ϣ�����-------------------//

		// ------------------------------���뵽���ݿ�------------------------------����
		
		public void insertInto(SQLiteDatabase db, String tableName, Event event){
			
			Log.i("insert Lecture", "��ʼ���԰ѽ������뵽���ݿ�");
			
			db.execSQL(
					"insert OR IGNORE into " + tableName + " values(null , ? , ? , ? , ? , ?, ?)",
					new String[] { event.getUid(), event.getTitle(),
							event.getTime(), event.getAddress(), event.getSpeaker(), event.getLink() });
			Log.i("insert Lecture", "�������ݿ������");
			
			//return true;
		}
		
		

		// ------------------------------���뵽���ݿ� end------------------------------����

		//-----------------------------��ҳѡ�����-----------------------------------------------//
		public Cursor select(SQLiteDatabase db, String time, String place, String subject){
			
			Log.i("SELECT", "��ʼ�������ݷ���");
			String[] selectString = new String[]{time, place, subject};
			//Cursor selectResult = db.rawQuery("select * from LectureTable where sub like ?",new String[] { "%"+SUBJECT+"%"});
			
			Cursor selectResult = db.rawQuery("select * from LectureTable where 1 ",new String[]{});
			Log.i("SELECT", "Select���ҽ���");
			
			return selectResult;
		}
		
		// --------�����ݿ��ѯLECTURETABLE���CURSORת��ΪList ��ȡ 1 2 3 4 5 ��-------//
		public List<Map<String, Object>> L_converCursorToList(Cursor cursor) {
			ArrayList<Map<String, Object>> result = new ArrayList<Map<String, Object>>();

			while (cursor.moveToNext()) {

				Map<String, Object> map = new HashMap<String, Object>();
	           // map.put("luid", cursor.getString(1));
				map.put("lecture_name", cursor.getString(2));
				map.put("lecture_time", cursor.getString(3));
				map.put("lecture_addr", cursor.getString(4));
				map.put("lecture_speaker", cursor.getString(5));
				//map.put("tag", cursor.getString(11));
				result.add(map);
			}
			
			return result;
		
		}

		// --------�����ݿ��ѯLECTURETABLE���CURSORת��ΪList ��ȡ4 5 10 7 12�н���-------//
		
		
	public DBCenter(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	public DBCenter(Context context, String name, CursorFactory factory,
			int version, DatabaseErrorHandler errorHandler) {
		super(context, name, factory, version, errorHandler);
		// TODO Auto-generated constructor stub
	}
	public DBCenter(Context context, String name, int version) {
		super(context, name, null, version);
	}

	



	@Override
	public void onCreate(SQLiteDatabase db) {
		// ��һ��ʹ�����ݿ�ʱ�Զ�����
		db.execSQL(LTABLE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Print in console Window ? 
		System.out.println("--------onUpdate Called--------" + oldVersion
				+ "--->" + newVersion);
	}

}
