package com.pkj.wow.contactsmanager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.pkj.wow.contactsmanager.ui.AddEditContactFragment;

public class AddContactActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Fragment fragment = AddEditContactFragment.newInstance(getIntent().getStringExtra(MainActivity.ACTION_TYPE),getIntent().getStringExtra(MainActivity.CONTACT_ID));
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.layout_add_contact, fragment).commit();
    }

}
