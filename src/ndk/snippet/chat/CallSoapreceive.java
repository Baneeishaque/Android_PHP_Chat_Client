package ndk.snippet.chat;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

public class CallSoapreceive 
{

	
	private DefaultHttpClient httpclient;
	private HttpPost httppost;
	private BasicResponseHandler responseHandler;
	private String response;

	public CallSoapreceive()
	{
		
	}
	
	public String Call(String id,String user)
	{
		

		try
				 
		{
				 
			httpclient = new DefaultHttpClient();
			httppost = new HttpPost("http://192.168.1.2/appengine_new/android/chat_recv_ajax.php?id="+id+"&user="+user+""); // make
		
			
			// Execute HTTP Post Request
			// edited by James from coderzheaven.. from here....
			responseHandler = new BasicResponseHandler();
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

