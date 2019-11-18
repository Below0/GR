package com.example.hayounglee.xml_test;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class phonenumSetting extends AppCompatActivity {
    public EditText[] save;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phonenum_setting);
        save = new EditText[2];
        save[0] = (EditText)findViewById(R.id.phone_1);
        save[1] = (EditText)findViewById(R.id.phone_2);
        pref = PreferenceManager.getDefaultSharedPreferences(this);
       editor = pref.edit();
       save[0].setText(pref.getString("num1",""));
       save[1].setText(pref.getString("num2",""));
    }
    public void save(View v){
        switch (v.getId()) {

            case R.id.add_1:
                Toast.makeText(getApplicationContext(),"번호[1]이 저장 되었습니다.",Toast.LENGTH_SHORT).show();
                editor.remove("num1");
                editor.putString("num1",""+save[0].getText().toString());
                break;

            case R.id.add_2:
                Toast.makeText(getApplicationContext(),"번호[2]이 저장 되었습니다.",Toast.LENGTH_SHORT).show();
                editor.remove("num2");
                editor.putString("num2",""+save[1].getText().toString());
                break;

        }
        editor.commit();
    }
    public String[] getPhoneNumber(){
        String num[] = new String[2];
        num[0] = save[0].getText().toString();
        num[1] = save[1].getText().toString();
        return num;
    }
}
