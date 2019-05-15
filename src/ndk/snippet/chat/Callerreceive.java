package ndk.snippet.chat;



public class Callerreceive  extends Thread 
{

	
	public CallSoapreceive cs;
	String id,user;
	
	
	
	public void run()
	{
		try
		{
			cs=new CallSoapreceive();	
			String resp=cs.Call(id,user);
			Chat.rslt=resp;
		}
		catch(Exception ex)
		{
			Chat.rslt=ex.toString();	
		}

	
    }
}
