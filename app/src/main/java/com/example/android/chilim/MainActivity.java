package com.example.android.chilim;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.ui.FirebaseListAdapter;

import java.sql.Ref;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Firebase rootRef;
    FirebaseListAdapter<com.example.android.chilim.ChatMessage> mListAdapter;

    EditText mEditText;
    ListView listView;

    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEditText = (EditText) findViewById(R.id.message);
        listView = (ListView) findViewById(R.id.list);

        username = getUsername();
        if (username == null) {
            username = "Anonymous";
        }

        Firebase.setAndroidContext(this);
        rootRef = new Firebase("https://vivid-inferno-572.firebaseio.com");

        mListAdapter = new FirebaseListAdapter<com.example.android.chilim.ChatMessage>(this, com.example.android.chilim.ChatMessage.class,
                android.R.layout.two_line_list_item, rootRef) {
            @Override
            protected void populateView(View v, com.example.android.chilim.ChatMessage model, int position) {
                ((TextView)v.findViewById(android.R.id.text1)).setText(model.getName());
                ((TextView)v.findViewById(android.R.id.text2)).setText(model.getText());
            }
        };
        listView.setAdapter(mListAdapter);
    }

    public void onClick(View v) {
        String text = mEditText.getText().toString();
        com.example.android.chilim.ChatMessage message = new com.example.android.chilim.ChatMessage(username, text);
        rootRef.push().setValue(message);
        //Toast.makeText(MainActivity.this, username, Toast.LENGTH_SHORT).show();
        mEditText.setText("");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mListAdapter.cleanup();
    }

    public String getUsername() {
        AccountManager manager = AccountManager.get(this);
        Account[] accounts = manager.getAccountsByType("com.google");
        List<String> possibleEmails = new LinkedList<String>();

        for (Account account : accounts) {
            // TODO: Check possibleEmail against an email regex or treat
            // account.name as an email address only for certain account.type
            // values.
            possibleEmails.add(account.name);
        }

        if (!possibleEmails.isEmpty() && possibleEmails.get(0) != null) {
            String email = possibleEmails.get(0);
            String[] parts = email.split("@");
            if (parts.length > 0 && parts[0] != null)
                return parts[0];
            else
                return null;
        } else
            return null;
    }

}
