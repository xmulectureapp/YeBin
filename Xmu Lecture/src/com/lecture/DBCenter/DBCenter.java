package com.lecture.DBCenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lecture.SettingAndSubmit.SettingsCenter;
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
	
	
	public static final String LECTURE_TABLE = "LectureTable";// 讲座信息表名
	public static final String COLLECTION_TABLE = "CollectionTable"; //收藏数据表
	public static final String LIKE_TABLE = "LikeTable"; //Like数据表
	
	
	
	
		//--------------------LECTURE_TABLE---------------------------
	
		private static String LECTURE_ID = "Lid";// 讲座ID==>primary key 用于本地数据库识别
		private static String LECTURE_UID = "Luid";// 从网上获取的作为讲座的唯一标识
		private static String LECTURE_TITLE = "Lwhat";// 讲座详细标题
		private static String LECTURE_DATE = "Lwhen";// 讲座时间
		private static String LECTURE_ADDRESS = "Lwhere";// 讲座校区
		private static String LECTURE_SPEAKER = "Lwho";// 主讲人
		private static String LECTURE_LINK = "link";// 讲座链接
		private static String LECTURE_LIKE = "Llike";// 是否喜欢，从LIKE_TABLE中获取，默认不喜欢
		private static String LECTURE_REMIND = "Lisremind";// 是否收藏，从COLLECTION_TABLE中获取，默认不收藏
		private static String LECTURE_LIKECOUNT = "Likecount";// like 点赞数量
		private static String LECTURE_REMINDERID = "LreminderID";  //设置日历提醒
		private static String LECTURE_EVENTID    = "LeventID";  //设置日历提醒
		//--------------------LECTURE_TABLE---------------------------
		
		//--------------------CollectionTable---------------------------
		// 收藏 实现的机制是 每次下载的中的uid如果在 CollectionTable 中出现就显示到 收藏列表中
		
		private static String COLLECTION_ID = "Cid";
		private static String COLLECTION_UID = "Cuid";
		private static String ISREMIND = "isRemind";
		//下面是咸鱼的添加，用于解决关闭App再打开时还能够删除日历  2014年 8月14日  10；18
		private static String REMINDERID = "reminderID";
		private static String EVENTID    = "eventID";
		//--------------------CollectionTable---------------------------
		
		//----LikeTable
		private static String LIKE_ID = "Likeid";//用于判断是否Like该讲座，0表示不喜欢，1表示喜欢
		private static String LIKE_UID = "Likeuid";//用于判断是否Like该讲座，0表示不喜欢，1表示喜欢
		private static String ISLIKE = "isLike";//用于判断是否Like该讲座，0表示不喜欢，1表示喜欢
		
		//----LikeTable
		

	

		
		//Collection table create
		private static final String COLLECTION_TABLE_CREATE = "create table " + COLLECTION_TABLE
				+ "(" + COLLECTION_ID + " integer primary key autoincrement,"
				+ COLLECTION_UID + " varchar(64) UNIQUE," + ISREMIND + " integer,"
				+ REMINDERID + " varchar(64)," + EVENTID + " varchar(64)"
				+ ")";
		
		//Like table create
		private static final String LIKE_TABLE_CREATE = "create table " + LIKE_TABLE
				+ "(" + LIKE_ID + " integer primary key autoincrement,"
				+ LIKE_UID + " varchar(64) UNIQUE," + ISLIKE + " integer" 
				
				+ ")";
		
		
		
		
		//------------------------------创建 LectureTable-------------------
		
		private static final String LTABLE_CREATE = "create table " + LECTURE_TABLE
				+ "(" + LECTURE_ID + " integer primary key autoincrement,"
				+ LECTURE_UID + " varchar(64) UNIQUE," + LECTURE_TITLE + " varchar(64)," 
				+ LECTURE_DATE + " varchar(64)," + LECTURE_ADDRESS + " varchar(64)," + LECTURE_SPEAKER + " varchar(64),"
				+ LECTURE_LINK + " varchar(64),"+ LECTURE_LIKE + " integer," + LECTURE_REMIND + " integer,"
				+ LECTURE_LIKECOUNT +" integer,"
				+ LECTURE_REMINDERID + " varchar(64)," + LECTURE_EVENTID + " varchar(64)"
				+ ")";
		//------------------------------创建 LectureTable-------------------
		
		
		
		public static DBCenter getStaticDBCenter(Context context){
			
			return new DBCenter(context, "LectureDB", 1);
			
		}
		
		
		public static void likeDBSync(SQLiteDatabase db, String id, String isLiked){
			// 本地上可能会产生Like为负值的情况，造成的原因可能是用户的疯狂点赞！服务器上面已经解决这个问题！
			if(isLiked.equals("1"))
				db.execSQL(
						"UPDATE " + DBCenter.LECTURE_TABLE + " SET " + LECTURE_LIKECOUNT + "=" + LECTURE_LIKECOUNT + "+1" + " WHERE Luid=?", new String[]{ id });
			else 
				db.execSQL(
						"UPDATE " + DBCenter.LECTURE_TABLE + " SET " + LECTURE_LIKECOUNT + "=" + LECTURE_LIKECOUNT + "-1" + " WHERE Luid=?", new String[]{ id });
				
		}
		//like func like select
		public Cursor likeSelect(SQLiteDatabase db){
			
			Log.i("Like SELECT", "开始查找数据分类");
			//Cursor selectResult = db.rawQuery("select * from LectureTable where sub like ?",new String[] { "%"+SUBJECT+"%"});
			Cursor selectResult;
			//if(time == null)
				selectResult = db.rawQuery("SELECT * FROM " + LECTURE_TABLE + " where " + LECTURE_LIKE + "=1",new String[]{});
			//else
				//selectResult = db.rawQuery("select * from " + LECTURE_TABLE + " where 1 LIMIT 0,4",new String[]{});
			Log.i("Like SELECT", "Select查找结束");
			
			return selectResult;
		}
		//collection func collection select
		public Cursor collectionSelect(SQLiteDatabase db){
			
			Log.i("Collection SELECT", "开始查找数据分类");
			//Cursor selectResult = db.rawQuery("select * from LectureTable where sub like ?",new String[] { "%"+SUBJECT+"%"});
			Cursor selectResult;
			//if(time == null)
			selectResult = db.rawQuery("select * from " + LECTURE_TABLE + " where " + LECTURE_REMIND + "=1",new String[]{});
			
			//else
				//selectResult = db.rawQuery("select * from " + LECTURE_TABLE + " where 1 LIMIT 0,4",new String[]{});
			Log.i("Collection SELECT", "Select查找结束");
			
			return selectResult;
		}
		//like func like select
				public Cursor hotSelect(SQLiteDatabase db){
					
					Log.i("Hot SELECT", "开始查找数据分类");
					//Cursor selectResult = db.rawQuery("select * from LectureTable where sub like ?",new String[] { "%"+SUBJECT+"%"});
					Cursor selectResult;
					//if(time == null)
						selectResult = db.rawQuery("SELECT * FROM " + LECTURE_TABLE + " where 1 ORDER BY " + LECTURE_LIKECOUNT + " DESC",new String[]{});
					//else
						//selectResult = db.rawQuery("select * from " + LECTURE_TABLE + " where 1 LIMIT 0,4",new String[]{});
					Log.i("Like SELECT", "Select查找结束");
					
					return selectResult;
				}
		
		
		
		//like table func refresh
		public static void refreshLike(SQLiteDatabase db){
			Log.i("喜欢列表", "开始更新");
			//update LectureTable set Llike=1 where exists (select * from LikeTable where Likeuid = Luid)
		 //TODO 检验SQL语句的正确性
			
			//下面第一个sql语句设置 1， 第二个sql句子设置0
			db.execSQL("UPDATE " + LECTURE_TABLE + " SET " + LECTURE_LIKE + "=1 WHERE " + LECTURE_UID + " IN (SELECT " + LIKE_UID + " FROM " + LIKE_TABLE
					+ " WHERE 1" + ")");
			db.execSQL("UPDATE " + LECTURE_TABLE + " SET " + LECTURE_LIKE + "=0 WHERE " + LECTURE_UID + " NOT IN (SELECT " + LIKE_UID + " FROM " + LIKE_TABLE
					+ " WHERE 1" + ")");
			
						
			Log.i("喜欢列表", "结束更新");
			
		}
		//Collection table func refresh
		public static void refreshCollection(SQLiteDatabase db){
			Log.i("收藏列表", "开始更新");
			//update LectureTable set Llike=1 where exists (select * from LikeTable where Likeuid = Luid)
		 //TODO 检验SQL语句的正确性
			//下面第一个sql语句设置 1， 第二个sql句子设置0
			db.execSQL("UPDATE " + LECTURE_TABLE + " SET " + LECTURE_REMIND + "=1 WHERE " + LECTURE_UID + " IN (SELECT " + COLLECTION_UID + " FROM "
					+ COLLECTION_TABLE + " WHERE 1" + ")");
			//
			db.execSQL("UPDATE " + LECTURE_TABLE + " SET " + LECTURE_REMIND + "=0 WHERE " + LECTURE_UID + " NOT IN (SELECT " + COLLECTION_UID + " FROM "
					+ COLLECTION_TABLE + " WHERE 1" + ")");
			
			
			///////////
			Cursor cursor;
			cursor = db.rawQuery("select * from " + COLLECTION_TABLE + " where 1",new String[]{});
			
			while (cursor.moveToNext()) {
				
				//下面开始设置日历提醒的reminderID 以及 eventID
				db.execSQL("UPDATE " + LECTURE_TABLE + " SET " + LECTURE_REMINDERID + "=" +  setStringNotEmpty( cursor.getString(3) ) + "," + LECTURE_EVENTID + "=" + setStringNotEmpty( cursor.getString(4) ) + " WHERE " + LECTURE_UID + "=" + cursor.getString(1) );
				//db.execSQL("UPDATE " + LECTURE_TABLE + " SET " + LECTURE_EVENTID + "=" + cursor.getString(4) + " WHERE " + LECTURE_UID + "=" + cursor.getString(1) );

				
			}
			
			Log.i("收藏列表", "结束更新");
			
		}
		
		// like table func set like
		public static void setLike(SQLiteDatabase db, String likeUid, Boolean isLike){
			Log.i("Like列表", "开始setLike");
			
		 //TODO 检验SQL语句的正确性
			if(isLike)
				db.execSQL("INSERT OR IGNORE INTO " + LIKE_TABLE + " VALUES(null , ? , ?)",new String[] { likeUid, "1" });
			else
				db.execSQL("DELETE FROM " + LIKE_TABLE + " WHERE " + LIKE_UID + "=?",new String[] { likeUid });
			Log.i("Like列表", "结束setLike");
			refreshLike(db);  //更新LectureTable
		}
		//coloection table finc set collect
		public static void setRemind(SQLiteDatabase db, String collectionUid, String reminderID, String eventID, Boolean isReminded){
			Log.i("Collection列表", "开始setRemind");
			
		 //TODO 检验SQL语句的正确性
			if(isReminded)
				db.execSQL("INSERT OR IGNORE INTO " + COLLECTION_TABLE + " VALUES(null , ? , ? , ? , ?)",new String[] { collectionUid, "1", reminderID, eventID });
			else
				db.execSQL("DELETE FROM " + COLLECTION_TABLE + " WHERE " + COLLECTION_UID + "=?",new String[] { collectionUid });
			Log.i("Collection列表", "结束setRemind");
			
			refreshCollection(db); //更新LectureTable
		}
		
		
		
		
		//------------------------------插入到数据库LectureTable------------------------------
		
		public void insertInto(SQLiteDatabase db, String tableName, Event event){
			
			event.setUid( event.trimUid( event.getUid() ) );
			
			Log.i("insert into LectureTable", "开始尝试把讲座插入到LectureTable");
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
			
			db.execSQL("insert OR IGNORE into " + tableName + " values(null , ? , ? , ? , ? , ?, ?, ?, ?, ?, ?, ?)",
					new String[] { event.getUid(), event.getTitle(),event.getTime(), event.getAddress(),
					event.getSpeaker(), event.getLink(), String.format("%d", isLike), String.format("%d", isReminded), String.format("%d", event.getLikeCount()), "", "" });
			Log.i("insert into LectureTable", "插入数据库结束！");
			
		}
		//------------------------------插入到数据库LectureTable------------------------------
		


		//-----------------------------LectureTable的选择语句，返回结果的Cursor-----------------
		public Cursor select(SQLiteDatabase db){
			
			
			Cursor selectResult;
			selectResult = db.rawQuery("select * from " + LECTURE_TABLE + " where 1",new String[]{});

			Log.i("SELECT", "Select查找结束");
			
			return selectResult;
		}
		//-----------------------------LectureTable的选择语句，返回结果的Cursor-----------------
		
		//--------将数据库查询LECTURETABLE结果CURSOR转化为List<Map>-----------------------------------
		public static List<Map<String, Object>> L_converCursorToList(Cursor cursor) {
			ArrayList<Map<String, Object>> result = new ArrayList<Map<String, Object>>();

			while (cursor.moveToNext()) {

				Map<String, Object> map = new HashMap<String, Object>();
	           // map.put("luid", cursor.getString(1));
				map.put("lecture_name", cursor.getString(2));
				map.put("lecture_time","时间: " + cursor.getString(3));
				map.put("lecture_addr", "地点: " +  cursor.getString(4));
				map.put("lecture_speaker", "主讲: " + cursor.getString(5));
				//map.put("tag", cursor.getString(11));
				result.add(map);
			}
			
			return result;
		
		}
		//--------将数据库查询LECTURETABLE结果CURSOR转化为List<Map>-----------------------------------
		
		//--------将数据库查询LECTURETABLE结果CURSOR转化为List<Event>-----------------------------------
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
				if(cursor.getString(7).equals("1") )  //设置是否喜欢
					event.setLike(true);
				else
					event.setLike(false);
				
				if(cursor.getString(8).equals("1"))  //设置是否收藏
					event.setReminded(true);
				else
					event.setReminded(false);
				//可能出错
				event.setLikeCount(cursor.getString(9));
				event.setReminderID( cursor.getString(10) );
				event.setEventID( cursor.getString(11) );
				
				result.add(event);  //加入到 result
			}
			
			return result;
		}
		//--------将数据库查询LECTURETABLE结果CURSOR转化为List<Event>-----------------------------------
		public static List<Event> L_convertCursorToListEventSubscribe(Cursor cursor,Context context){
			
			ArrayList<Event> result = new ArrayList<Event>();
			SettingsCenter settingsCenter = new SettingsCenter(context);
			
			while(cursor.moveToNext()){
				Event event = new Event();
				
				event.setTime(cursor.getString(3));
				event.setAddress(cursor.getString(4));
				
				if(settingsCenter.isNeededLecture(event)){
					
					event.setUid(cursor.getString(1));
					event.setTitle(cursor.getString(2));
					event.setSpeaker(cursor.getString(5));
					event.setLink(cursor.getString(6));
					if(cursor.getString(7).equals("1") )  //设置是否喜欢
						event.setLike(true);
					else
						event.setLike(false);
					
					if(cursor.getString(8).equals("1"))  //设置是否收藏
						event.setReminded(true);
					else
						event.setReminded(false);
					//可能出错
					event.setLikeCount(cursor.getString(9));
					event.setReminderID( cursor.getString(10) );
					event.setEventID( cursor.getString(11) );
					
					result.add(event);  //加入到 result
					
				} // end if
						
			}
			
			return result;
		}
		//------下面用于清除数据库LectureTable所有数据-----------------------------------------
		public static void clearAllData(SQLiteDatabase dbToClear, String tableName){
			
			Log.i("删除 LectureTable", "开始尝试 删除");
			
			dbToClear.execSQL(
					"DELETE FROM " + LECTURE_TABLE + " WHERE Lid IS NOT NULL ", new String[]{});
			Log.i("删除 LectureTable", "删除 LectureTable结束！");
			
		}
		//------下面用于清除数据库LectureTable所有数据-----------------------------------------
		
		
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
			// 第一次使用数据库时自动建表
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

		//用于解决update没有值的执行错误  by 咸鱼  2014年8月14 中午
		public static String setStringNotEmpty(String string){
			
			if(string == null || string.equals(""))
				string = "0";
			
			return string;
			
		}
}
