package ndk.snippet.chat;

import java.util.ArrayList;
import java.util.List;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DiscussArrayAdapter extends ArrayAdapter<OneComment> {

	private TextView countryName;
	private List<OneComment> countries = new ArrayList<OneComment>();
	private LinearLayout wrapper;
	private TextView time;
	private TextView user;

	@Override
	public void add(OneComment object) {
		countries.add(object);
		super.add(object);
	}

	public DiscussArrayAdapter(Context context, int textViewResourceId) {
		super(context, textViewResourceId);
	}

	public int getCount() {
		return this.countries.size();
	}

	public OneComment getItem(int index) {
		return this.countries.get(index);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		if (row == null) {
			LayoutInflater inflater = (LayoutInflater) this.getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.listitem_chat, parent, false);
		}

		wrapper = (LinearLayout) row.findViewById(R.id.wrapper);
		countryName = (TextView) row.findViewById(R.id.comment);
		user = (TextView) row.findViewById(R.id.user);
		user.setTextColor(Color.BLACK);
		OneComment coment = getItem(position);
		
		if(coment.category)
		{
			
			countryName.setText(coment.comment);
			countryName.setBackgroundResource(coment.left ? R.drawable.bubble_yellow : R.drawable.bubble_green);
			countryName.setGravity(coment.left ? Gravity.LEFT : Gravity.RIGHT);		
			user.setText(coment.user);
			wrapper.setGravity(coment.left ? Gravity.LEFT : Gravity.RIGHT);


			
		}
		else
		{
			int miss=71-coment.time.length();
			int equ=miss/2;
			String da = "";
			for(int i=1;i<=equ;i++)
			{
				da=da+" ";
			}
			countryName.setText(da+coment.time+da);

			countryName.setBackgroundColor(Color.CYAN);
			countryName.setGravity(Gravity.CENTER);
			user.setText("");
			wrapper.setGravity(Gravity.CENTER);
		}
		return row;
	}

	public Bitmap decodeToBitmap(byte[] decodedByte) {
		return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
	}

}