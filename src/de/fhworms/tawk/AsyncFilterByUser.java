//Copyright (c) 2011, Markus Brandt
//All rights reserved.
//
//Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
//
//Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
//Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
//Neither the name of the FH Worms nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.
//THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

package de.fhworms.tawk;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.Toast;

/**
 * {@link AsyncTask} to handle the search for a given user search pattern.
 * 
 * @author Markus Brandt
 * 
 */
public class AsyncFilterByUser extends AsyncTask<Void, Void, Void> {

	private Activity activity;
	private String searchString;
	private ListItem[] items;
	// private SessionID token;
	private ListView lv;

	private Dialog dialog = null;

	/**
	 * 
	 * @param activity
	 *            calling activity
	 * @param searchString
	 *            is the exact user name, typically selected by click
	 * @param items
	 *            an array of ListItems, each representing a single message
	 * @param token
	 *            SessionID
	 */
	public AsyncFilterByUser(Activity activity, String searchString,
			ListItem[] items, SessionID token) {
		this.activity = activity;
		this.searchString = searchString;
		this.items = items;
		// this.token = token;
	}

	@Override
	protected Void doInBackground(Void... params) {
		try {
			/*
			 * get users matching the search string
			 */
			int hits = 0;
			for (int i = 0; i < items.length; i++) {
				if (items[i].getAuthor().equals(searchString)) {
					hits++;
				}
			}
			ListItem[] matches = new ListItem[hits];
			hits = 0;
			for (int i = 0; i < items.length; i++) {
				if (items[i].getAuthor().equals(searchString)) {
					matches[hits++] = items[i];
				}
			}
			items = matches;

		} catch (Exception e) {
			e.printStackTrace();
			/*
			 * cancel result to tell the login activity that the session expired
			 */
			activity.setResult(Activity.RESULT_CANCELED);
			activity.finish();
		}
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		/*
		 * dismiss loading dialog
		 */
		dialog.dismiss();
		dialog = null;

		if (items != null && items.length > 0) {
			/*
			 * display fetched messages
			 */
			lv.setAdapter(new ListItemAdapter(activity.getBaseContext(), items));

		} else {
			/*
			 * if there were no messages display a toast to inform the user
			 */
			Toast.makeText(activity, "No messages found", Toast.LENGTH_SHORT)
					.show();
		}
	}

	@Override
	protected void onPreExecute() {
		/*
		 * display loading dialog
		 */
		dialog = ProgressDialog.show(activity, "search for", "user", true);
	}

}