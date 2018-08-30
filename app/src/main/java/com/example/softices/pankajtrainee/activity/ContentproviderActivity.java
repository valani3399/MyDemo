package com.example.softices.pankajtrainee.activity;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.softices.pankajtrainee.R;
import com.example.softices.pankajtrainee.adapter.ContextAdepter;
import com.example.softices.pankajtrainee.db.DbHelper;
import com.example.softices.pankajtrainee.models.ContextModel;

import java.util.ArrayList;
import java.util.List;

public class ContentproviderActivity extends AppCompatActivity {
    private RecyclerView redemo;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<String> data;
    TextView tvcontextname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contentprovider);
        tvcontextname = (TextView) findViewById(R.id.tv_contextname);
        redemo = (RecyclerView) findViewById(R.id.recycler_contex);
        getAllContacts();

    }

    private void getAllContacts() {
        ArrayList<ContextModel> arrayList=new ArrayList();
        ContextModel contextModel;
        ContentResolver contentResolver=getContentResolver();
        Cursor cursor=contentResolver.query(ContactsContract.Contacts.CONTENT_URI,
                null,null,
                null,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
        if(cursor.getCount()>0){
            while (cursor.moveToNext()){
                int hasPhoneNUmber= Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)));
                if(hasPhoneNUmber>0){
                    String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                    String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    contextModel=new ContextModel();
                    contextModel.setName(name);

                //this cursor use for get mobail number
                    Cursor phoneCursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id},
                            null);
                    if (phoneCursor.moveToNext()) {
                        String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        contextModel.setNumber(phoneNumber);
                    }
                    phoneCursor.close();
                    arrayList.add(contextModel);
                }
            }
        }
        cursor.close();
        ContextAdepter contextAdepter=new ContextAdepter(arrayList,this);
        redemo.setLayoutManager(new LinearLayoutManager(this));
        redemo.setAdapter(contextAdepter);
    }
//    private void getAllContacts() {
//        List<ContextModel> contactVOList = new ArrayList();
//        ContextModel contactVO;
//
//        ContentResolver contentResolver = getContentResolver();
//        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
//        if (cursor.getCount() > 0) {
//            while (cursor.moveToNext()) {
//
//                int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)));
//                if (hasPhoneNumber > 0) {
//                    String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
//                    String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
//
//                    contactVO = new ContextModel();
//                    contactVO.setName(name);
//
//                    Cursor phoneCursor = contentResolver.query(
//                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
//                            null,
//                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
//                            new String[]{id},
//                            null);
//                    if (phoneCursor.moveToNext()) {
//                        String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
//                        contactVO.setContactNumber(phoneNumber);
//                    }
//
//                    phoneCursor.close();
//
//                    Cursor emailCursor = contentResolver.query(
//                            ContactsContract.CommonDataKinds.Email.CONTENT_URI,
//                            null,
//                            ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
//                            new String[]{id}, null);
//                    while (emailCursor.moveToNext()) {
//                        String emailId = emailCursor.getString(emailCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
//                    }
//                    contactVOList.add(contactVO);
//                }
//            }
//
//            AllContactsAdapter contactAdapter = new AllContactsAdapter(contactVOList, getApplicationContext());
//            rvContacts.setLayoutManager(new LinearLayoutManager(this));
//            rvContacts.setAdapter(contactAdapter);
//        }
//    }

}
