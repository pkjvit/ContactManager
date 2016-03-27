package com.pkj.wow.contactsmanager;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;


/**
 * Created by Pankaj on 24-03-2016.
 */
public class ContactsApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // Configure default configuration for Realm
        RealmConfiguration realmConfig = new RealmConfiguration.Builder(this).build();
        Realm.setDefaultConfiguration(realmConfig);
    }
}
