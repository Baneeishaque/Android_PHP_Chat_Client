package ndk.snippet.chat;



public class Callersend  extends Thread 
{

	
	public CallSoapsend cs;
	String sender,msg,r;
	
	
	
	public void run()
	{
		try
		{
			cs=new CallSoapsend();	
			String resp=cs.Call(sender,msg,r);
			Chat.rslt=resp;
		}
		catch(Exception ex)
		{
			Chat.rslt=ex.toString();	
		}

	
    }
}
