package com.pkj.wow.contactsmanager.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pkj.wow.contactsmanager.R;
import com.pkj.wow.contactsmanager.adapter.ContactAdaper;
import com.pkj.wow.contactsmanager.model.Contact;

import org.json.JSONArray;
import org.json.JSONException;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

/**
 * A simple {@link Fragment} subclass.
 *
 * Use the {@link ContactListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContactListFragment extends Fragment {

    private static final String TAG = "ContactListFragment";

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ACTION_TYPE = "action_type";

    private String mActionType;
    private RecyclerView mContactRecyclerView;
    private ContactAdaper mContactAdapter;
    private RecyclerView.LayoutManager mContactLayoutManager;

    private Realm mRealm;
    private RealmResults<Contact> mResults;
    private RealmChangeListener mRealmChangeListener = new RealmChangeListener() {
        @Override
        public void onChange() {
            Log.d(TAG, "onChange: ");
            mContactAdapter.update(mResults);
        }
    };

    public ContactListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ContactListFragment.
     */
    public static ContactListFragment newInstance(String actionType) {
        ContactListFragment fragment = new ContactListFragment();
        Bundle args = new Bundle();
        args.putString(ACTION_TYPE, actionType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mActionType = getArguments().getString(ACTION_TYPE);
        }

        mRealm = Realm.getDefaultInstance();

        // Insert some dummy json data into database
        String jsonArray = "[{\"fullName\":\"Marietta Spencer\",\"emailId\":\"mariettaspencer@zorromop.com\",\"phoneNo\":\"+1 (996) 418-3356\"},{\"fullName\":\"Hamilton Goodman\",\"emailId\":\"hamiltongoodman@zorromop.com\",\"phoneNo\":\"+1 (816) 486-2183\"},{\"fullName\":\"Diane Ferrell\",\"emailId\":\"dianeferrell@zorromop.com\",\"phoneNo\":\"+1 (881) 480-2833\"},{\"fullName\":\"John Fischer\",\"emailId\":\"johnfischer@zorromop.com\",\"phoneNo\":\"+1 (890) 427-2093\"}]";
        JSONArray contactJson = null;
        try {
            contactJson = new JSONArray(jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mRealm.beginTransaction();
        try {
            mRealm.createOrUpdateAllFromJson(Contact.class, contactJson);
            mRealm.commitTransaction();
        } catch (Exception e) {
            mRealm.cancelTransaction();
        }
        mRealm.close();


        mResults = mRealm.where(Contact.class).findAllAsync();
    }

    @Override
    public void onStart() {
        super.onStart();
        mRealm.addChangeListener(mRealmChangeListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        mRealm.removeChangeListener(mRealmChangeListener);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_contact_list, container, false);
        initView(rootView);
        return rootView;
    }

    private void initView(View rootView){
        mContactRecyclerView = (RecyclerView) rootView.findViewById(R.id.contact_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mContactRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mContactLayoutManager = new LinearLayoutManager(getActivity());
        mContactRecyclerView.setLayoutManager(mContactLayoutManager);

        // specify an adapter (see also next example)
        mContactAdapter = new ContactAdaper(mResults);
        mContactRecyclerView.setAdapter(mContactAdapter);
    }


}
