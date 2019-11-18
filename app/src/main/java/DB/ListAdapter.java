package DB;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hayounglee.xml_test.EqInfo;

import java.util.List;



public class ListAdapter extends BaseAdapter {
    List people;
    Context context;

    public ListAdapter(List people, Context context) {
        this.people = people;
        this.context = context;
        Log.d("ListAdapter", "111111111111111111111111111111111111111");

    }

    @Override
    public int getCount() {
        return this.people.size();
    }

    @Override
    public Object getItem(int position) {
        return this.people.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Holder holder = null;

        if (convertView == null) {
            //convertView 가 없으면 초기화합니다

            convertView = new LinearLayout(context);
            ((LinearLayout) convertView).setOrientation(LinearLayout.HORIZONTAL);


            TextView listMt = new TextView(context);
            listMt.setPadding(10, 5, 20, 5);
            listMt.setTextColor(Color.rgb(0, 0, 0));

            TextView listTmEqk = new TextView(context);
            listTmEqk.setPadding(10, 5, 20, 5);
            listTmEqk.setTextColor(Color.rgb(0, 0, 0));


            TextView listLoc = new TextView(context);
            listLoc.setPadding(10, 5, 20, 5);
            listLoc.setTextColor(Color.rgb(0, 0, 0));


            TextView listAddress = new TextView(context);
            listAddress.setPadding(10, 5, 20, 5);
            listAddress.setTextColor(Color.rgb(0, 0, 0));

            ((LinearLayout) convertView).addView(listMt);
            ((LinearLayout) convertView).addView(listTmEqk);
            ((LinearLayout) convertView).addView(listLoc);
            //  ((LinearLayout) convertView).addView(listAddress);


            holder = new Holder();
            holder.listMt = listMt;
            holder.listTmeqk = listTmEqk;
            holder.listLoc = listLoc;


            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        //한명의 데이터를 받아와서 입력
        EqInfo eqinfo = (EqInfo)getItem(position);
        holder.listMt.setText(Double.toString(eqinfo.getMt()));
        holder.listTmeqk.setText(eqinfo.getTmEqk());
        holder.listLoc.setText(eqinfo.getLoc());




        return convertView;


    }
}

class Holder {


    public TextView listMt;
    public TextView listTmeqk;
    public TextView listLoc;
    //public TextView listAddress;

}
