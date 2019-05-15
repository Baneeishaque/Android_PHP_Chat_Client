package ndk.snippet.chat;

public class OneComment {
	public boolean left,category;
	public String comment,user,time;

	public OneComment(boolean category,boolean left,String time, String user, String comment) {
		super();
		this.left = left;
		this.comment = comment;
		this.category=category;
		this.user=user;
		this.time=time;

	}

}