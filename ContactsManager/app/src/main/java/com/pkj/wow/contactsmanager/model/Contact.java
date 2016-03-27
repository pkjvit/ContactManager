package com.pkj.wow.contactsmanager.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by Pankaj on 23-03-2016.
 */
public class Contact extends RealmObject{

    @PrimaryKey
    private String fullName;
    @Required
    private String phoneNo;
    private String emailId;

    public Contact(){

    }

    public Contact(String fullName, String phoneNo, String emailId){
        this.phoneNo = phoneNo;
        this.fullName = fullName;
        this.emailId = emailId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }
}
