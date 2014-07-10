package com.lecture.util;

import java.util.GregorianCalendar;
import java.util.TimeZone;

import com.lecture.localdata.Event;
import com.lecture.localdata.ReminderInfo;

import android.app.Activity;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Calendars;
import android.provider.CalendarContract.Events;
import android.provider.CalendarContract.Reminders;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader.OnLoadCompleteListener;


public class CalendarControlUtil {


	private static int loaderId = 0xfffff1;

	/**
	 * ��ȡ�����б�,�ڼ������з��ؽ��
	 * 
	 * @param context
	 * @param listener
	 */
	public static void getCalendars(Context context,
			OnLoadCompleteListener<Cursor> listener) {
		String[] projection = new String[] { Calendars._ID, Calendars.NAME };
		Uri calendarUri = Calendars.CONTENT_URI;
		CursorLoader loader = new CursorLoader(context, calendarUri,
				projection, null, null, null);
		loader.registerListener(loaderId, listener);
		loader.startLoading();
	}

	/**
	 * �������в�������
	 * 
	 * @param activity
	 * @param calendarId
	 *            ����id
	 * @param data
	 * @return id �¼�id
	 */
	public static ReminderInfo insertReminder(Activity activity,
			String calendarId, Event data) {
		// ����¼�
		ContentValues event = new ContentValues();
		GregorianCalendar calDate = data.getTimeCalendar();
		event.put(Events.TITLE, data.getName());
		event.put(Events.DESCRIPTION, data.getOwner());
		event.put(Events.CALENDAR_ID, calendarId);
		event.put(Events.EVENT_LOCATION, data.getAddress());
		event.put(Events.DTSTART, calDate.getTime().getTime());
		event.put(Events.DTEND, calDate.getTime().getTime());
		event.put(Events.HAS_ALARM, 1);
		event.put(Events.EVENT_TIMEZONE, TimeZone.getDefault().getID());
		Uri newEvent = activity.getContentResolver().insert(Events.CONTENT_URI,
				event);
		long eventId = Long.parseLong(newEvent.getLastPathSegment());
		// �������
		ContentValues values = new ContentValues();
		values.put(Reminders.EVENT_ID, eventId);
		values.put(Reminders.MINUTES, 10);
		values.put(Reminders.METHOD, Reminders.METHOD_ALERT);
		Uri newReminder = activity.getContentResolver().insert(
				Reminders.CONTENT_URI, values);
		long reminderId = Long.parseLong(newReminder.getLastPathSegment());
		// ��¼����
		data.setReminderInfo(new ReminderInfo(eventId, reminderId));
		return new ReminderInfo(eventId, reminderId);
	}

	/**
	 * ��������ɾ�����Ѻ��¼�
	 * 
	 * @param activity
	 * @param info
	 *            �����¼�����Ϣ
	 * @return �����Ƿ�ɹ�(�����򷵻�TRUE)
	 */
	public static boolean deleteReminder(Activity activity, ReminderInfo info) {
		Uri deleteReminderUri = null;
		Uri deleteEventUri = null;
		deleteReminderUri = ContentUris.withAppendedId(Reminders.CONTENT_URI,
				info.getReminderId());
		deleteEventUri = ContentUris.withAppendedId(Events.CONTENT_URI,
				info.getEventId());
		int rowR = activity.getContentResolver().delete(deleteReminderUri,
				null, null);
		int rowE = activity.getContentResolver().delete(deleteEventUri, null,
				null);
		if (rowE > 0 && rowR > 0)
			return true;
		return false;
	}

	public static void setAlert(Activity activity, Event data) {
		Intent calIntent = new Intent(Intent.ACTION_INSERT);
		calIntent.setType("vnd.android.cursor.item/event");
		calIntent.putExtra(Events.TITLE, data.getName());
		calIntent.putExtra(Events.EVENT_LOCATION, data.getAddress());
		calIntent.putExtra(Events.DESCRIPTION, data.getOwner());
		GregorianCalendar calDate = data.getTimeCalendar();
		calIntent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true);
		calIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
				calDate.getTimeInMillis());
		calIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,
				calDate.getTimeInMillis());
		activity.startActivity(calIntent);

	}
}
