package com.pkj.wow.contactsmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.pkj.wow.contactsmanager.ui.ContactListFragment;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity {

    public static final String ACTION_TYPE = "action_type";
    public static final String CONTACT_ID = "contact_id";
    public static final String ADD_ACTION = "add";
    public static final String EDIT_ACTION = "edit";

    private Realm mRealm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRealm = Realm.getDefaultInstance();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddContactActivity.class);
                intent.putExtra(ACTION_TYPE,ADD_ACTION);
                intent.putExtra(CONTACT_ID,"");
                MainActivity.this.startActivity(intent);
            }
        });

        Fragment contactFragment = ContactListFragment.newInstance("");
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.layout_contact_list, contactFragment).commit();

    }
}
