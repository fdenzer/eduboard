package de.fhworms.tawk;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ListItemAdapter extends BaseAdapter {

	private ListItem[] listItems = null;

	private LayoutInflater mInflater = null;

	public ListItemAdapter(Context context, ListItem[] items) {
		listItems = items;
		mInflater = LayoutInflater.from(context);
	}

	public int getCount() {
		return listItems.length;
	}

	public ListItem getItem(int position) {
		return listItems[position];
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {

			convertView = mInflater.inflate(R.layout.list_item, null);
			holder = new ViewHolder();
			holder.author = (TextView) convertView.findViewById(R.id.author);
			holder.subject = (TextView) convertView.findViewById(R.id.subject);
			holder.message = (TextView) convertView.findViewById(R.id.message);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.message.setText(listItems[position].getMessage());
		holder.author.setText(listItems[position].getAuthor());
		holder.subject.setText(listItems[position].getSubject());

		return convertView;
	}

	/**
	 * corresponds to res/layout/list_item.xml
	 * 
	 * @author frank
	 * 
	 */
	static class ViewHolder {

		// from User
		private TextView author;

		// from Message
		private TextView subject;
		private TextView message;
	}
}