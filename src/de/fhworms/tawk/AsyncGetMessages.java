//Copyright (c) 2011, Markus Brandt, Frank Denzer
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
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.Toast;

/**
 * {@link AsyncTask} to handle the search for a given user search pattern.
 * 
 * @authors Markus Brandt, Frank Denzer
 * 
 */
class AsyncGetMessages extends AsyncTask<Void, Void, Void> {

	// Android
	private Activity activity;
	private ListView lv;
	private ProgressDialog dialog = null;

	// Java
	private String[] msgIDs;

	// Custom classes
	private ListItem[] items;
	private SessionID token;
	private Message msg;

	public ListItem[] getItems() {
		return this.items;
	}

	
	/**
	 * 
	 * @param a
	 *            the activity that calls the task
	 * @param listView
	 *            the ListView you wish to fill ...
	 * @param sid
	 *            a _valid_ SessionID
	 */
	public AsyncGetMessages(Activity a, ListView listView, SessionID sid) {
		this.activity = a;
		this.lv = listView;
		this.token = sid;
	}

	@Override
	protected Void doInBackground(Void... params) {
		try {
			/*
			 * get UserID's matching the search string
			 */
			items = new ListItem[Eduboard.ITEM_COUNT];

			msgIDs = TawkRequest.getMessageIDs(token);

			for (int i = 0; i < Eduboard.ITEM_COUNT; i++) {
				try {
					msg = TawkRequest.getMessage(token,
							new MessageID(msgIDs[i]));
					items[i] = new ListItem(msg.getMessage(), TawkRequest
							.getUser(token, msg.getUserID()).getShortName(),
							msg.getSubject());
				} catch (Exception e) {
					e.printStackTrace();
					break;
				}
			}
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
			lv.setAdapter(new ListItemAdapter(activity.getApplicationContext(),
					items));

		} else {
			/*
			 * if there were no messages display a toast to inform the user
			 */
			Toast.makeText(activity, "No messages fetched", Toast.LENGTH_SHORT)
					.show();
		}
	}

	@Override
	protected void onPreExecute() {
		/*
		 * display loading dialog
		 */
		dialog = ProgressDialog.show(activity, "", "connecting", true);
	}

}