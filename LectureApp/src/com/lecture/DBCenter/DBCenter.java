package com.lecture.DBCenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lecture.lectureapp.HotMyadapter;
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
	
	public static final String LECTURE_TABLE = "LectureTable";// ������Ϣ����
	public static final String COLLECTION_TABLE = "CollectionTable"; //�ղ����ݱ�
	public static final String LIKE_TABLE = "LikeTable"; //Like���ݱ�
	
	
		//--------------------LECTURE_TABLE---------------------------
	
		private static String LECTURE_ID = "Lid";// ����ID==>primary key ���ڱ������ݿ�ʶ��
		private static String LECTURE_UID = "Luid";// �����ϻ�ȡ����Ϊ������Ψһ��ʶ
		private static String LECTURE_TITLE = "Lwhat";// ������ϸ����
		private static String LECTURE_DATE = "Lwhen";// ����ʱ��
		private static String LECTURE_ADDRESS = "Lwhere";// ����У��
		private static String LECTURE_SPEAKER = "Lwho";// ������
		private static String LECTURE_LINK = "link";// ��������
		private static String LECTURE_LIKE = "Llike";// �Ƿ�ϲ������LIKE_TABLE�л�ȡ��Ĭ�ϲ�ϲ��
		private static String LECTURE_REMIND = "Lisremind";// �Ƿ��ղأ���COLLECTION_TABLE�л�ȡ��Ĭ�ϲ��ղ�
		//--------------------LECTURE_TABLE---------------------------
		
		//--------------------CollectionTable---------------------------
		// �ղ� ʵ�ֵĻ����� ÿ�����ص��е�uid����� CollectionTable �г��־���ʾ�� �ղ��б���
		
		private static String COLLECTION_ID = "Cid";
		private static String COLLECTION_UID = "Cuid";
		private static String ISREMIND = "isRemind";
		//--------------------CollectionTable---------------------------
		
		//----LikeTable
		private static String LIKE_ID = "Likeid";//�����ж��Ƿ�Like�ý�����0��ʾ��ϲ����1��ʾϲ��
		private static String LIKE_UID = "Likeuid";//�����ж��Ƿ�Like�ý�����0��ʾ��ϲ����1��ʾϲ��
		private static String ISLIKE = "isLike";//�����ж��Ƿ�Like�ý�����0��ʾ��ϲ����1��ʾϲ��
		
		//----LikeTable
		

		
		//-------------------------------------------------��Щ��ʱ�ò�����������������		
				//��������ϸ��Ϣ���ֶ�
				//private static String SPEAKER_INFO = "whoinfo";// ��������ϸ��Ϣ
				//private static String TITLE_MESSAGE = "whatinfo";// ������ϸ����
				//private static String SUBJECT = "sub";// ��������
				//private static String TAG = "tag";// ������ǩ
				//-------------------------------------------------��Щ��ʱ�ò�����������������
		

		
		//Collection table create
		private static final String COLLECTION_TABLE_CREATE = "create table " + COLLECTION_TABLE
				+ "(" + COLLECTION_ID + " integer primary key autoincrement,"
				+ COLLECTION_UID + " varchar(64) UNIQUE," + ISREMIND + " integer" + ")";
		
		//Like table create
		private static final String LIKE_TABLE_CREATE = "create table " + LIKE_TABLE
				+ "(" + LIKE_ID + " integer primary key autoincrement,"
				+ LIKE_UID + " varchar(64) UNIQUE," + ISLIKE + " integer" + ")";
		
		
		
		
		//------------------------------���� LectureTable-------------------
		
		private static final String LTABLE_CREATE = "create table " + LECTURE_TABLE
				+ "(" + LECTURE_ID + " integer primary key autoincrement,"
				+ LECTURE_UID + " varchar(64) UNIQUE," + LECTURE_TITLE + " varchar(64)," 
				+ LECTURE_DATE + " varchar(64)," + LECTURE_ADDRESS + " varchar(64)," + LECTURE_SPEAKER + " varchar(64),"
				+ LECTURE_LINK + " varchar(64),"+ LECTURE_LIKE + " integer," + LECTURE_REMIND + " integer"
				+ ")";
		//------------------------------���� LectureTable-------------------
		
		//like func like select
		public Cursor likeSelect(SQLiteDatabase db){
			
			Log.i("Like SELECT", "��ʼ�������ݷ���");
			//Cursor selectResult = db.rawQuery("select * from LectureTable where sub like ?",new String[] { "%"+SUBJECT+"%"});
			Cursor selectResult;
			//if(time == null)
				selectResult = db.rawQuery("SELECT * FROM " + LECTURE_TABLE + " where " + LECTURE_LIKE + "=1",new String[]{});
			//else
				//selectResult = db.rawQuery("select * from " + LECTURE_TABLE + " where 1 LIMIT 0,4",new String[]{});
			Log.i("Like SELECT", "Select���ҽ���");
			
			return selectResult;
		}
		//collection func collection select
		public Cursor collectionSelect(SQLiteDatabase db){
			
			Log.i("Collection SELECT", "��ʼ�������ݷ���");
			//Cursor selectResult = db.rawQuery("select * from LectureTable where sub like ?",new String[] { "%"+SUBJECT+"%"});
			Cursor selectResult;
			//if(time == null)
			selectResult = db.rawQuery("select * from " + LECTURE_TABLE + " where " + LECTURE_REMIND + "=1",new String[]{});
			
			//else
				//selectResult = db.rawQuery("select * from " + LECTURE_TABLE + " where 1 LIMIT 0,4",new String[]{});
			Log.i("Collection SELECT", "Select���ҽ���");
			
			return selectResult;
		}
		
		
		//like table func refresh
		public static void refreshLike(SQLiteDatabase db){
			Log.i("ϲ���б�", "��ʼ����");
			//update LectureTable set Llike=1 where exists (select * from LikeTable where Likeuid = Luid)
		 //TODO ����SQL������ȷ��
			
			//�����һ��sql������� 1�� �ڶ���sql��������0
			db.execSQL("UPDATE " + LECTURE_TABLE + " SET " + LECTURE_LIKE + "=1 WHERE " + LECTURE_UID + " IN (SELECT " + LIKE_UID + " FROM " + LIKE_TABLE
					+ " WHERE 1" + ")");
			db.execSQL("UPDATE " + LECTURE_TABLE + " SET " + LECTURE_LIKE + "=0 WHERE " + LECTURE_UID + " NOT IN (SELECT " + LIKE_UID + " FROM " + LIKE_TABLE
					+ " WHERE 1" + ")");
			
						
			Log.i("ϲ���б�", "��������");
			
		}
		//Collection table func refresh
		public static void refreshCollection(SQLiteDatabase db){
			Log.i("�ղ��б�", "��ʼ����");
			//update LectureTable set Llike=1 where exists (select * from LikeTable where Likeuid = Luid)
		 //TODO ����SQL������ȷ��
			//�����һ��sql������� 1�� �ڶ���sql��������0
			db.execSQL("UPDATE " + LECTURE_TABLE + " SET " + LECTURE_REMIND + "=1 WHERE " + LECTURE_UID + " IN (SELECT " + COLLECTION_UID + " FROM "
					+ COLLECTION_TABLE + " WHERE 1" + ")");
			//
			db.execSQL("UPDATE " + LECTURE_TABLE + " SET " + LECTURE_REMIND + "=0 WHERE " + LECTURE_UID + " NOT IN (SELECT " + COLLECTION_UID + " FROM "
					+ COLLECTION_TABLE + " WHERE 1" + ")");
			
			Log.i("�ղ��б�", "��������");
			
		}
		
		// like table func set like
		public static void setLike(SQLiteDatabase db, String likeUid, Boolean isLike){
			Log.i("Like�б�", "��ʼsetLike");
			
		 //TODO ����SQL������ȷ��
			if(isLike)
				db.execSQL("INSERT OR IGNORE INTO " + LIKE_TABLE + " VALUES(null , ? , ?)",new String[] { likeUid, "1" });
			else
				db.execSQL("DELETE FROM " + LIKE_TABLE + " WHERE " + LIKE_UID + "=?",new String[] { likeUid });
			Log.i("Like�б�", "����setLike");
			refreshLike(db);  //����LectureTable
		}
		//coloection table finc set collect
		public static void setRemind(SQLiteDatabase db, String collectionUid, Boolean isReminded){
			Log.i("Collection�б�", "��ʼsetRemind");
			
		 //TODO ����SQL������ȷ��
			if(isReminded)
				db.execSQL("INSERT OR IGNORE INTO " + COLLECTION_TABLE + " VALUES(null , ? , ?)",new String[] { collectionUid, "1" });
			else
				db.execSQL("DELETE FROM " + COLLECTION_TABLE + " WHERE " + COLLECTION_UID + "=?",new String[] { collectionUid });
			Log.i("Collection�б�", "����setRemind");
			
			refreshCollection(db); //����LectureTable
		}
		
		
		
		
		//------------------------------���뵽���ݿ�LectureTable------------------------------
		
		public void insertInto(SQLiteDatabase db, String tableName, Event event){
			
			event.setUid( event.trimUid( event.getUid() ) );
			
			Log.i("insert into LectureTable", "��ʼ���԰ѽ������뵽LectureTable");
			int isLike;
			int isReminded;
			
			if(event.isLike())
				isLike = 1;
			else
				isLike = 0;
			
			if(event.isReminded())
				isReminded = 1;
			else
				isReminded = 0;
			
			db.execSQL("insert OR IGNORE into " + tableName + " values(null , ? , ? , ? , ? , ?, ?, ?, ?)",
					new String[] { event.getUid(), event.getTitle(),event.getTime(), event.getAddress(),
					event.getSpeaker(), event.getLink(), String.format("%d", isLike), String.format("%d", isReminded) });
			Log.i("insert into LectureTable", "�������ݿ������");
			
		}
		//------------------------------���뵽���ݿ�LectureTable------------------------------
		


		//-----------------------------LectureTable��ѡ����䣬���ؽ����Cursor-----------------
		public Cursor select(SQLiteDatabase db, String time, String place, String subject){
			
			Log.i("SELECT", "��ʼ�������ݷ���");
			String[] selectString = new String[]{time, place, subject};
			//Cursor selectResult = db.rawQuery("select * from LectureTable where sub like ?",new String[] { "%"+SUBJECT+"%"});
			Cursor selectResult;
			//if(time == null)
				selectResult = db.rawQuery("select * from " + LECTURE_TABLE + " where 1",new String[]{});
			//else
				//selectResult = db.rawQuery("select * from " + LECTURE_TABLE + " where 1 LIMIT 0,4",new String[]{});
			Log.i("SELECT", "Select���ҽ���");
			
			return selectResult;
		}
		//-----------------------------LectureTable��ѡ����䣬���ؽ����Cursor-----------------
		
		//--------�����ݿ��ѯLECTURETABLE���CURSORת��ΪList<Map>-----------------------------------
		public static List<Map<String, Object>> L_converCursorToList(Cursor cursor) {
			ArrayList<Map<String, Object>> result = new ArrayList<Map<String, Object>>();

			while (cursor.moveToNext()) {

				Map<String, Object> map = new HashMap<String, Object>();
	           // map.put("luid", cursor.getString(1));
				map.put("lecture_name", cursor.getString(2));
				map.put("lecture_time","ʱ��: " + cursor.getString(3));
				map.put("lecture_addr", "�ص�: " +  cursor.getString(4));
				map.put("lecture_speaker", "����: " + cursor.getString(5));
				//map.put("tag", cursor.getString(11));
				result.add(map);
			}
			
			return result;
		
		}
		//--------�����ݿ��ѯLECTURETABLE���CURSORת��ΪList<Map>-----------------------------------
		
		//--------�����ݿ��ѯLECTURETABLE���CURSORת��ΪList<Event>-----------------------------------
		public static List<Event> L_convertCursorToListEvent(Cursor cursor){
			
			ArrayList<Event> result = new ArrayList<Event>();
			
			while(cursor.moveToNext()){
				Event event = new Event();
				event.setUid(cursor.getString(1));
				event.setTitle(cursor.getString(2));
				event.setTime(cursor.getString(3));
				event.setAddress(cursor.getString(4));
				event.setSpeaker(cursor.getString(5));
				event.setLink(cursor.getString(6));
				if(cursor.getString(7).equals("1") )  //�����Ƿ�ϲ��
					event.setLike(true);
				else
					event.setLike(false);
				
				if(cursor.getString(8).equals("1"))  //�����Ƿ��ղ�
					event.setReminded(true);
				else
					event.setReminded(false);
				
				result.add(event);  //���뵽 result
			}
			
			return result;
		}
		//--------�����ݿ��ѯLECTURETABLE���CURSORת��ΪList<Event>-----------------------------------
	
		//------��������������ݿ�LectureTable��������-----------------------------------------
		public static void clearAllData(SQLiteDatabase dbToClear, String tableName){
			
			Log.i("ɾ�� LectureTable", "��ʼ���� ɾ��");
			
			dbToClear.execSQL(
					"DELETE FROM " + LECTURE_TABLE + " WHERE Lid IS NOT NULL ", new String[]{});
			Log.i("ɾ�� LectureTable", "ɾ�� LectureTable������");
			
		}
		//------��������������ݿ�LectureTable��������-----------------------------------------
		
		
		//constructors
		
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
			db.execSQL(COLLECTION_TABLE_CREATE);
			db.execSQL(LIKE_TABLE_CREATE);
		}
	
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// Print in console Window ? 
			System.out.println("--------onUpdate Called--------" + oldVersion
					+ "--->" + newVersion);
		}

}
