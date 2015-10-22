package com.hodoroid.realmdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmResults;


public class MainActivity extends Activity {

    public static final int REQUEST_CODE = 0x1;
    ArrayList<Person> persons = new ArrayList<>();
    private ListView mListView;
    private MyListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView = (ListView) findViewById(R.id.mListView);
        loadData();
        mAdapter = new MyListAdapter(persons, this);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Person p = persons.get(position);
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                intent.putExtra("person", p.getId());
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
    }

    public void loadData() {
        persons.clear();
        Realm realm = Realm.getInstance(this);
        RealmResults<Person> query = realm.where(Person.class)
                .findAll();
        for (Person p : query) {
            if (p.isValid())
                persons.add(p);
        }
    }

    public void add(View view) {
        Realm realm = Realm.getInstance(getApplicationContext());
        realm.beginTransaction();
        Person p = realm.createObject(Person.class);
        p.setCity("New City");
        p.setName("New Guy");
        p.setId(UUID.randomUUID().toString());
        realm.commitTransaction();
        persons.add(p);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            loadData();
            mAdapter.notifyDataSetChanged();
        }
    }
}
