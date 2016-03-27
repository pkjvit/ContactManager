package com.pkj.wow.contactsmanager.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.pkj.wow.contactsmanager.R;
import com.pkj.wow.contactsmanager.model.Contact;

import io.realm.Realm;
import io.realm.RealmAsyncTask;

/**
 * --------- Add or Edit Contact Detail ------------------
 * A simple {@link Fragment} subclass.
 * Use the {@link AddEditContactFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddEditContactFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ACTION_TYPE = "action_type";
    private static final String CONTACT_ID = "contact_id";

    private String mActionType="";
    private String mContactId="";

    private EditText mFullNameET;
    private EditText mMobileNumberET;
    private EditText mEmailET;
    private Button   mSaveBtn;

    private RealmAsyncTask mTransaction;


    public AddEditContactFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param actionType Type of action you want to do with contact ( add or edit ).
     * @param contactId If action type == edit then contactId of contact to edit.
     * @return A new instance of fragment AddEditContactFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddEditContactFragment newInstance(String actionType, String contactId) {
        AddEditContactFragment fragment = new AddEditContactFragment();
        Bundle args = new Bundle();
        args.putString(ACTION_TYPE, actionType);
        args.putString(CONTACT_ID, contactId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mActionType = getArguments().getString(ACTION_TYPE);
            mContactId =  getArguments().getString(CONTACT_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_add_edit_contact, container, false);
        initView(rootView);
        if(!mContactId.isEmpty()){
            bindViewData();
        }
        return rootView;
    }

    private void initView(View rootView){

        mFullNameET     = (EditText) rootView.findViewById(R.id.etName);
        mMobileNumberET = (EditText) rootView.findViewById(R.id.etPhone);
        mEmailET        = (EditText) rootView.findViewById(R.id.etEmail);
        mSaveBtn        = (Button) rootView.findViewById(R.id.btnSave);

        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add or update contact into database
                final Contact contact = new Contact(mFullNameET.getText().toString(),
                        mMobileNumberET.getText().toString(),
                        mEmailET.getText().toString());

                Realm realm = Realm.getDefaultInstance();
                mTransaction = realm.executeTransactionAsync(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        realm.copyToRealmOrUpdate(contact);
                    }
                });

                getActivity().finish();

            }
        });


    }

    private void bindViewData(){
        Realm realm = Realm.getDefaultInstance();
        Contact contact = realm.where(Contact.class).equalTo("fullName",mContactId).findFirst();

        mFullNameET.setText(contact.getFullName());
        mMobileNumberET.setText(contact.getPhoneNo());
        mEmailET.setText(contact.getEmailId());
    }


    @Override
    public void onStop() {
        super.onStop();
        if (mTransaction != null && !mTransaction.isCancelled()) {
            mTransaction.cancel();
        }
    }
}
