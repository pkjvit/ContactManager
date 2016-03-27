package com.pkj.wow.contactsmanager.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pkj.wow.contactsmanager.AddContactActivity;
import com.pkj.wow.contactsmanager.MainActivity;
import com.pkj.wow.contactsmanager.R;
import com.pkj.wow.contactsmanager.model.Contact;

import io.realm.RealmResults;

/**
 * Created by Pankaj on 24-03-2016.
 */
public class ContactAdaper extends RecyclerView.Adapter<ContactAdaper.ViewHolder> {

    private static final String TAG = "ContactAdaper";
    private RealmResults<Contact> mResults;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        // each data item is just a string in this case
        public TextView mNameTV;
        public TextView mPhoneTV;
        private Contact mContact;
        public ViewHolder(View v) {
            super(v);
            v.setOnClickListener(this);
            mNameTV = (TextView)v.findViewById(R.id.tvName);
            mPhoneTV = (TextView)v.findViewById(R.id.tvPhone);
        }

        public void setItem(Contact contact){
            mContact = contact;
        }

        @Override
        public void onClick(View v) {
            Log.d(TAG, "onClick: "+getPosition()+ " : item "+mContact.getFullName());
            Intent intent = new Intent(v.getContext(), AddContactActivity.class);
            intent.putExtra(MainActivity.ACTION_TYPE,MainActivity.EDIT_ACTION);
            intent.putExtra(MainActivity.CONTACT_ID,mContact.getFullName());
            v.getContext().startActivity(intent);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ContactAdaper(RealmResults<Contact> results) {
        update(results);
    }

    public void update(RealmResults<Contact> results) {
        mResults = results;
        notifyDataSetChanged();
    }


    @Override
    public ContactAdaper.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: ");
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_contact, parent, false);

        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ContactAdaper.ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: "+position);
        Contact contact = mResults.get(position);
        holder.setItem(contact);
        holder.mNameTV.setText(contact.getFullName());
        holder.mPhoneTV.setText(contact.getPhoneNo());
    }

    @Override
    public int getItemCount() {
        return mResults.size();
    }


}
