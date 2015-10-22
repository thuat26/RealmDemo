package com.hodoroid.realmdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import io.realm.Realm;

/**
 * Created by billey_b on 07/11/14.
 */
public class EditActivity extends Activity {

    private EditText mName;
    private EditText mCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        mName = (EditText) findViewById(R.id.mName);
        mCity = (EditText) findViewById(R.id.mCity);
        Realm realm = Realm.getInstance(this);
        Person toEdit = realm.where(Person.class)
                .equalTo("id", getIntent().getStringExtra("person")).findFirst();
        mName.setText(toEdit.getName());
        mCity.setText(toEdit.getCity());
    }

    public void update(View view) {
        Realm realm = Realm.getInstance(getApplicationContext());
        Person toEdit = realm.where(Person.class)
                .equalTo("id", getIntent().getStringExtra("person")).findFirst();
        realm.beginTransaction();
        toEdit.setCity(mCity.getText().toString());
        toEdit.setName(mName.getText().toString());
        realm.commitTransaction();
        Toast.makeText(getApplicationContext(), "updated", Toast.LENGTH_SHORT).show();
        finish();
    }

    public void delete(View view) {
        Realm realm = Realm.getInstance(getApplicationContext());
        Person toEdit = realm.where(Person.class)
                .equalTo("id", getIntent().getStringExtra("person")).findFirst();
        realm.beginTransaction();
        toEdit.removeFromRealm();
        realm.commitTransaction();
        finish();
    }

    @Override
    public void finish() {
        super.finish();
        finishActivity(MainActivity.REQUEST_CODE);
    }
}
