package ndk.snippet.chat;

import java.util.ArrayList;
import java.util.Vector;

import org.apache.http.NameValuePair;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class Chat extends Activity {

	private ndk.snippet.chat.DiscussArrayAdapter adapter;
	private ListView lv;
	private EditText editText_message;
	private ProgressDialog dialog;
	private DefaultHttpClient httpclient;
	private HttpPost httppost;
	String response = "";
	private ArrayList<NameValuePair> nameValuePairs;
	private String pdate;
	private String puser;
	private BasicResponseHandler responseHandler;
	private String rid;
	private View v;

	// used for register alarm manager
	PendingIntent pendingIntent;
	// used to store running alarmmanager instance
	AlarmManager alarmManager;
	// Callback function for Alarmmanager event
	BroadcastReceiver mReceiver;

	private Vector<String> ls;
	private Vector<String> lsd;
	private int flag;

	public static String rslt;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chat);
		RegisterAlarmBroadcast();

		lv = (ListView) findViewById(R.id.listView1);
		adapter = new DiscussArrayAdapter(getApplicationContext(), R.layout.listitem_chat);
		lv.setAdapter(adapter);

		editText_message = (EditText) findViewById(R.id.message);
		editText_message.setBackgroundColor(Color.LTGRAY);
		rid = "51";

		getmessages_t(rid);

		alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 5000, pendingIntent);

		ls = new Vector<String>();
		lsd = new Vector<String>();

		editText_message.clearFocus();

	}

	public void refresh(View v) {

		startActivity(new Intent(Chat.this, Chat.class));
		this.finish();
	}

	private void RegisterAlarmBroadcast() {
		// Toast.makeText(getBaseContext(), "Going to register
		// Intent.RegisterAlramBroadcast", Toast.LENGTH_LONG).show();

		// This is the call back function(BroadcastReceiver) which will be call
		// when your
		// alarm time will reached.
		mReceiver = new BroadcastReceiver() {

			@Override
			public void onReceive(Context context, Intent intent) {

				// Toast.makeText(context, "Congrats!. Your Alarm time has been
				// reached", Toast.LENGTH_LONG).show();

				alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 5000, pendingIntent);

//				adapter = new DiscussArrayAdapter(getApplicationContext(), R.layout.listitem_discuss);
//
//				
//					lv.setAdapter(adapter);
				getmessages_t(rid);
//					if (!editText1.isFocused()) {
//						lv.requestFocus();
//
//					}
				// refresh(v);

			}
		};

		// register the alarm broadcast here
		registerReceiver(mReceiver, new IntentFilter("ndk.snippet.chat"));
		pendingIntent = PendingIntent.getBroadcast(this, 0, new Intent("ndk.snippet.chat"), 0);
		alarmManager = (AlarmManager) (this.getSystemService(Context.ALARM_SERVICE));
	}

	public void send(View v) {
		// dialog = ProgressDialog.show(HelloBubblesActivity.this, "",
		// "Refreshing...", true);
		// new Thread(new Runnable() {
		// public void run() {
		// setmessages();
		// }
		// }).start();
		if (!editText_message.getText().toString().isEmpty()) {
			setmessages_t();
		}

	}

	public void setmessages() {
		// TODO Auto-generated method stub

		try {

			httpclient = new DefaultHttpClient();
			httppost = new HttpPost("http://192.168.42.59/appengine/android/chat_send_ajax.php"); // make
																									// sure
																									// the
																									// url
																									// is
																									// correct.

			// add your data
			nameValuePairs = new ArrayList<NameValuePair>(3);
			// Always use the same variable name for posting i.e the android
			// side variable name and php side variable name should be similar,
			nameValuePairs.add(new BasicNameValuePair("sender", "35")); // $Edittext_value
																		// =
																		// $_POST['Edittext_value'];
			nameValuePairs.add(new BasicNameValuePair("msg", editText_message.getEditableText().toString()));

			nameValuePairs.add(new BasicNameValuePair("r", "34"));
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			// Execute HTTP Post Request
			// edited by James from coderzheaven.. from here....
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			response = httpclient.execute(httppost, responseHandler);
			// System.out.println("Response : " + response);

			runOnUiThread(new Runnable() {
				public void run() {
					// Toast.makeText(HelloBubblesActivity.this,"Response from
					// PHP : " +response,Toast.LENGTH_LONG).show();
					dialog.dismiss();
					startActivity(new Intent(Chat.this, Chat.class));

				}
			});

		} catch (final Exception e) {
			runOnUiThread(new Runnable() {
				public void run() {
					Toast.makeText(Chat.this, "Try Again, Reason : " + e, Toast.LENGTH_SHORT).show();
					dialog.dismiss();
				}
			});

		}

	}

	public void getmessages_t(String id) {
		try {
			rslt = "START";
			Callerreceive c = new Callerreceive();
			c.id = id;
			c.user = "53";

			c.join();
			c.start();
			while (rslt == "START") {
				try {
					Thread.sleep(10);

				} catch (Exception ex) {
					Toast.makeText(getApplicationContext(), "Communication Error" + ex.getLocalizedMessage().toString(),
							Toast.LENGTH_LONG).show();

				}
			}

			// Toast.makeText(getApplicationContext(),
			// rslt, Toast.LENGTH_LONG)
			// .show();
			//

			process_response(rslt);

			// Toast.makeText(getBaseContext(), "Alarm set",
			// Toast.LENGTH_LONG).show();
			if (flag == 1) {
				lv.smoothScrollToPosition(adapter.getCount());
				if (!editText_message.isFocused()) {
					lv.requestFocus();
				}

			}

		} catch (Exception ex) {

			// Toast.makeText(getApplicationContext(), "Error : " + ex.getMessage(),
			// Toast.LENGTH_LONG).show();

		}
	}

	public void setmessages_t() {
		try {
			rslt = "START";
			Callersend c = new Callersend();
			c.sender = "53";
			c.msg = editText_message.getText().toString();
			c.r = "51";

			c.join();
			c.start();
			while (rslt == "START") {
				try {
					Thread.sleep(10);

				} catch (Exception ex) {
					Toast.makeText(getApplicationContext(), "Communication Error" + ex.getLocalizedMessage().toString(),
							Toast.LENGTH_LONG).show();

				}
			}

			// Toast.makeText(getApplicationContext(),
			// rslt, Toast.LENGTH_LONG)
			// .show();

			// adapter = new DiscussArrayAdapter(getApplicationContext(),
			// R.layout.listitem_discuss);
			//
			// lv.setAdapter(adapter);
			// getmessages_t(rid);
			editText_message.setText("");
			//
			// lv.requestFocus();

//			refresh(v);
			getmessages_t(rid);
			editText_message.clearFocus();

		} catch (Exception ex) {
			Toast.makeText(getApplicationContext(), "Communication Error3", Toast.LENGTH_LONG).show();

			Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_LONG).show();

		}
	}

	private void getmessages(String id) {
		// TODO Auto-generated method stub
		try {

			httpclient = new DefaultHttpClient();
			httppost = new HttpPost("http://192.168.42.59/appengine/android/chat_recv_ajax.php?id=" + id + "&user=35"); // make

			// Execute HTTP Post Request
			// edited by James from coderzheaven.. from here....
			responseHandler = new BasicResponseHandler();
			response = httpclient.execute(httppost, responseHandler);
			// System.out.println("Response : " + response);

			runOnUiThread(new Runnable() {
				public void run() {
					Toast.makeText(Chat.this, "Response from PHP : " + response, Toast.LENGTH_LONG).show();
					dialog.dismiss();
				}
			});

		} catch (final Exception e) {
			runOnUiThread(new Runnable() {
				public void run() {
					Toast.makeText(Chat.this, "Try Again, Reason : " + e, Toast.LENGTH_LONG).show();
					dialog.dismiss();
				}
			});

		}

		process_response(response);

	}

	private void process_response(String response) {
		// TODO Auto-generated method stub

		flag = 0;
		if (!response.isEmpty()) {
			String[] p = response.split("\\`");
			int use = 35;

			for (int i = 0; i < p.length; i++) {

				String[] q = p[i].split("\\~");
				if (!ls.contains(q[5])) {
					if ((!lsd.contains(q[0]))) {

//						Toast.makeText(getApplicationContext(), String.valueOf(("                                                       "+q[0]).length()), Toast.LENGTH_LONG).show();
						adapter.add(new OneComment(false, true, q[0], q[2] + " " + q[1], q[4]));
						lsd.add(q[0]);
					}

					if (use == Integer.parseInt(q[3])) {

						if (i != 0) {
							if (q[3].equals(puser)) {
								adapter.add(new OneComment(true, false, q[0], q[1], q[4]));
								flag = 1;
							} else {
								adapter.add(new OneComment(true, false, q[0], q[2] + " " + q[1], q[4]));
								puser = q[3];
								flag = 1;
							}
						} else {
							adapter.add(new OneComment(true, false, q[0], q[2] + " " + q[1], q[4]));
							puser = q[3];
							flag = 1;
						}

					} else {

						if (i != 0) {
							if (q[3].equals(puser)) {
								adapter.add(new OneComment(true, true, q[0], q[1], q[4]));
								flag = 1;
							} else {
								adapter.add(new OneComment(true, true, q[0], q[2] + " " + q[1], q[4]));
								puser = q[3];
								flag = 1;
							}
						} else {
							adapter.add(new OneComment(true, true, q[0], q[2] + " " + q[1], q[4]));
							puser = q[3];
							flag = 1;
						}

					}
					// Toast.makeText(HelloBubblesActivity.this,q[5], Toast.LENGTH_SHORT).show();
					ls.add(q[5]);
				}

			}
		}

	}

}