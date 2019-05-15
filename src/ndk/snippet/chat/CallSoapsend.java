package ndk.snippet.chat;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

public class CallSoapsend 
{

	
	private DefaultHttpClient httpclient;
	private HttpPost httppost;
	private BasicResponseHandler responseHandler;
	private String response;
	private ArrayList<NameValuePair> nameValuePairs;

	public CallSoapsend()
	{
		
	}
	
	public String Call(String sender,String msg,String r)
	{
		

		try
				 
		{
				 
			httpclient = new DefaultHttpClient();
			httppost = new HttpPost("http://192.168.1.2/appengine_new/android/chat_send_ajax.php"); // make
																									// sure
																									// the
																									// url
																									// is
																									// correct.

			// add your data
			nameValuePairs = new ArrayList<NameValuePair>(3);
			// Always use the same variable name for posting i.e the android
			// side variable name and php side variable name should be similar,
			nameValuePairs.add(new BasicNameValuePair("sender", sender)); // $Edittext_value
																		// =
																		// $_POST['Edittext_value'];
			nameValuePairs.add(new BasicNameValuePair("msg", msg));

			nameValuePairs.add(new BasicNameValuePair("r", r));
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			// Execute HTTP Post Request
			// edited by James from coderzheaven.. from here....
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			response = httpclient.execute(httppost, responseHandler);
			// System.out.println("Response : " + response);

			
			
		
				 
		}
				 
		catch (Exception exception)
				 
		{
				 
			response=exception.toString()+" transport error";
				 
		}
		
		
		return response.toString();
	}

	

}

