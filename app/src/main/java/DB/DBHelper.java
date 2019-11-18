package DB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.example.hayounglee.xml_test.EqInfo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



public  class DBHelper extends SQLiteOpenHelper {
    private Context context;


    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //4개의 열을 만듭니다.
        StringBuffer sb = new StringBuffer();
        sb.append(" CREATE TABLE EARTHQUAKE_TABLE (");
        sb.append(" MT TEXT, ");
        sb.append(" TMEQK TEXT, ");
        sb.append(" LOC TEXT); ");


        db.execSQL(sb.toString());

      //  Toast.makeText(context, "EARTHQUAKE_TABLE이 만들어짐 ", Toast.LENGTH_SHORT).show();

    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Toast.makeText(context, "버전이 올라갔습니다", Toast.LENGTH_SHORT).show();
    }

    public void testDB() {
        SQLiteDatabase db = getReadableDatabase();
    }


    public void putData(EqInfo eqInfo) {
        //데이터를 테이블에 넣는 과정
        SQLiteDatabase db = getWritableDatabase();

        StringBuffer sb = new StringBuffer();
        sb.append("INSERT INTO EARTHQUAKE_TABLE (");
        sb.append(" MT, TMEQK, LOC )");
        sb.append(" VALUES ( ?, ?, ? ) ");
        SimpleDateFormat date_1 = new SimpleDateFormat("yyyyMMddHHmmss");
        SimpleDateFormat date_2 = new SimpleDateFormat(("yyyy년 MM월 dd일 hh시 mm분"));
        Date date = null;
        try {
            date = date_1.parse(eqInfo.getTmEqk());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        db.execSQL(sb.toString(), new Object[]{
              Double.toString(eqInfo.getMt()), date_2.format(date), eqInfo.getLoc() //eqInfo.getAddress()
        });

       // Toast.makeText(context, "iNSERT 완료", Toast.LENGTH_SHORT).show();

    }

    //입력된 모든 person데이터를 가져옴
    public List getAllPersonData() {
        StringBuffer sb = new StringBuffer();



        //읽기 전용 DB 객체
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM EARTHQUAKE_TABLE", null );

        List people = new ArrayList();
        EqInfo eqInfo;


        //moveToNext() 다음에 데이터가 있으면 데이터를 갖고온다

        while (cursor.moveToNext()) {


            eqInfo = new EqInfo();
            eqInfo.setMt(cursor.getDouble(0));  // String 으로 넣었는데 double로 갖고와도 된나?
            eqInfo.setTmEqk(cursor.getString(1));
            eqInfo.setLoc(cursor.getString(2));
            //person.setAddress(cursor.getString(3));


            people.add(eqInfo);


        }

        return people;


    }
}
