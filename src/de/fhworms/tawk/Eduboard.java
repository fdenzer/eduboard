package de.fhworms.tawk;

import android.accounts.NetworkErrorException;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public final class Eduboard extends ListActivity {

	private static final int LOGIN = 0;
	private static final int WRITE = 1;
	private static final String TOKEN = "sessionToken";

	// Android
	private Dialog dialog;
	private SharedPreferences dataStore;
	private EditText msg, subj;
	private ListView board;

	// Custom Classes
	private SessionID token;
	//private ListItem[] items;
	private AsyncGetMessages refresh;
	private AsyncFilterByUser filter;

	// Java
	public final static Integer ITEM_COUNT = 20;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		token = null;
		dataStore = getSharedPreferences(TawkRequest.KEY, MODE_PRIVATE);
		// helper variable s(tring)Token
		String sToken = dataStore.getString(TOKEN, "");
		if (sToken.equals("")) {
			login(this.getCurrentFocus());
		} else {
			token = new SessionID(sToken);
			refresh(this.getCurrentFocus());
		}
		
		board = getListView();
		board.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				filter(board.getItemAtPosition(position));
			}
		});
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		if (token == null)
			showDialog(LOGIN);
		super.onRestoreInstanceState(savedInstanceState);
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		dialog = null;
		switch (id) {
		case LOGIN:
			dialog = loginScreen();
			break;
		case WRITE:
			dialog = writeScreen();
			break;
		default:
			break;
		}
		return dialog;
	}

	private void filter(Object o) {
		ListItem tmp = (ListItem) o;
		if (refresh.getItems() == null) {
			refresh = new AsyncGetMessages(this, board, token);
			refresh.execute();
		} else {
			filter = new AsyncFilterByUser(this, tmp.getAuthor(),
					refresh.getItems(), token);
			filter.execute();
		}
	}

	// TODO: xml: onClick --> public and icon
	// public void search(View v) {
	// // TODO: add search in the ITEM_COUNT (...n, but thats another todo)
	// // messages
	// }

	// xml: onClick --> public
	public void login(View v) {
		showDialog(LOGIN);
	}

	private Dialog loginScreen() {
		dialog = new Dialog(this);
		dialog.setContentView(R.layout.login_dialog);
		dialog.setTitle("User information");

		final EditText user = (EditText) dialog.findViewById(R.id.username);
		final EditText pwd = (EditText) dialog.findViewById(R.id.password);

		Button login = (Button) dialog.findViewById(R.id.login);

		login.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				try {
					token = TawkRequest.authenticate(user.getText().toString(),
							pwd.getText().toString());
					if (token == null) {
						throw new NetworkErrorException();
					} else {
						Editor ed = dataStore.edit();
						ed.putString(TOKEN, token.getSessionID());
						ed.commit();
					}
				} catch (Exception e) {
					Toast.makeText(getApplicationContext(),
							"Check username/password and network",
							Toast.LENGTH_SHORT).show();
					e.printStackTrace();
				}
				dialog.dismiss();
			}
		});
		return dialog;
	}

	// xml: onClick --> public
	public void write(View v) {
		showDialog(WRITE);
	}

	public Dialog writeScreen() {
		dialog = new Dialog(this);
		dialog.setContentView(R.layout.write_dialog);
		dialog.setCanceledOnTouchOutside(true);
		dialog.setTitle("Subject:");

		subj = (EditText) dialog.findViewById(R.id.subject);
		msg = (EditText) dialog.findViewById(R.id.message);
		Button send = (Button) dialog.findViewById(R.id.send);

		subj.setText("");
		msg.setText("");

		send.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				try {
					if (token == null) {
						throw new NetworkErrorException();
					} else {
						Message message = new Message();
						String subject = "", messageText = "";

						subject = subj.getText().toString();
						messageText = msg.getText().toString();

						message.setSubject(subject);
						message.setMessage(messageText);

						if (subject.equals("") && messageText.equals("")) {
							// empty message --> do not send
						} else {
							TawkRequest.sendMessage(token, message);
							subj.setText("");
							msg.setText("");
						}
					}
				} catch (NetworkErrorException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				dialog.dismiss();
				// TODO: edittext keeps old edittext :(
				// dialog = null;
			}
		});
		return dialog;
	}

	// xml: onClick --> public
	public void refresh(View v) {
		refresh = new AsyncGetMessages(this, getListView(), token);
		refresh.execute();
	}
}