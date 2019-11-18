package com.example.hayounglee.xml_test;

import android.media.Image;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class Eqaction extends AppCompatActivity {
LinearLayout loclayout;
LinearLayout situlayout;
Button[] bt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eqaction);
        loclayout = (LinearLayout) findViewById(R.id.locLayout);
        situlayout = (LinearLayout) findViewById(R.id.situLayout);
        bt = new Button[2];
        bt[0] = (Button) findViewById(R.id.locActionBtn);
        bt[1] = (Button) findViewById(R.id.situActionBtn);
    }
    public void menuClick(View v) {
        switch (v.getId()){
            case R.id.locActionBtn:
                loclayout.setVisibility(View.VISIBLE);
                situlayout.setVisibility(View.INVISIBLE);
                bt[0].setBackgroundResource(R.drawable.select_border);
                bt[1].setBackgroundResource(R.drawable.non_select_border);
                break;
            case R.id.situActionBtn:
                loclayout.setVisibility(View.INVISIBLE);
                situlayout.setVisibility(View.VISIBLE);
                bt[1].setBackgroundResource(R.drawable.select_border);
                bt[0].setBackgroundResource(R.drawable.non_select_border);
                break;
        }
    }
    public void situBtn(View v) {
        switch (v.getId()) {
            case R.id.situbtn1: {
                AlertDialog.Builder builder = new AlertDialog.Builder(Eqaction.this);
                View view = LayoutInflater.from(Eqaction.this)
                        .inflate(R.layout.actioneq, null, false);
                builder.setView(view);
                final Button ButtonSubmit = (Button) view.findViewById(R.id.okbtn);
                final AlertDialog dialog = builder.create();
                final ImageView imageview = (ImageView) view.findViewById(R.id.image1);
                imageview.setImageResource(R.drawable.situact1);
                ButtonSubmit.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
                break;
            }
            case R.id.situbtn2: {
                AlertDialog.Builder builder = new AlertDialog.Builder(Eqaction.this);
                View view = LayoutInflater.from(Eqaction.this)
                        .inflate(R.layout.actioneq, null, false);
                builder.setView(view);
                final Button ButtonSubmit = (Button) view.findViewById(R.id.okbtn);
                final AlertDialog dialog = builder.create();
                final ImageView imageview = (ImageView) view.findViewById(R.id.image1);
                imageview.setImageResource(R.drawable.situact2);
                ButtonSubmit.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
                break;
            }
            case R.id.situbtn3: {
                AlertDialog.Builder builder = new AlertDialog.Builder(Eqaction.this);
                View view = LayoutInflater.from(Eqaction.this)
                        .inflate(R.layout.actioneq, null, false);
                builder.setView(view);
                final Button ButtonSubmit = (Button) view.findViewById(R.id.okbtn);
                final AlertDialog dialog = builder.create();
                final ImageView imageview = (ImageView) view.findViewById(R.id.image1);
                imageview.setImageResource(R.drawable.situact3);
                ButtonSubmit.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
                break;
            }
            case R.id.situbtn4: {
                AlertDialog.Builder builder = new AlertDialog.Builder(Eqaction.this);
                View view = LayoutInflater.from(Eqaction.this)
                        .inflate(R.layout.actioneq, null, false);
                builder.setView(view);
                final Button ButtonSubmit = (Button) view.findViewById(R.id.okbtn);
                final AlertDialog dialog = builder.create();
                final ImageView imageview = (ImageView) view.findViewById(R.id.image1);
                imageview.setImageResource(R.drawable.situact4);
                ButtonSubmit.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
                break;
            }
            case R.id.situbtn5: {
                AlertDialog.Builder builder = new AlertDialog.Builder(Eqaction.this);
                View view = LayoutInflater.from(Eqaction.this)
                        .inflate(R.layout.actioneq, null, false);
                builder.setView(view);
                final Button ButtonSubmit = (Button) view.findViewById(R.id.okbtn);
                final AlertDialog dialog = builder.create();
                final ImageView imageview = (ImageView) view.findViewById(R.id.image1);
                imageview.setImageResource(R.drawable.situact5);
                ButtonSubmit.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
                break;
            }
            case R.id.situbtn6: {
                AlertDialog.Builder builder = new AlertDialog.Builder(Eqaction.this);
                View view = LayoutInflater.from(Eqaction.this)
                        .inflate(R.layout.actioneq, null, false);
                builder.setView(view);
                final Button ButtonSubmit = (Button) view.findViewById(R.id.okbtn);
                final AlertDialog dialog = builder.create();
                final ImageView imageview = (ImageView) view.findViewById(R.id.image1);
                imageview.setImageResource(R.drawable.situact6);
                ButtonSubmit.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
                break;
            }

        }
    }
    public void locBtn(View v){
        switch (v.getId()){
            case R.id.btn1: {
                AlertDialog.Builder builder = new AlertDialog.Builder(Eqaction.this);
                View view = LayoutInflater.from(Eqaction.this)
                        .inflate(R.layout.actioneq, null, false);
                builder.setView(view);
                final Button ButtonSubmit = (Button) view.findViewById(R.id.okbtn);
                final AlertDialog dialog = builder.create();
                final ImageView imageview = (ImageView) view.findViewById(R.id.image1);
                imageview.setImageResource(R.drawable.locact1);
                ButtonSubmit.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
                break;
            }
            case R.id.btn2: {
                AlertDialog.Builder builder = new AlertDialog.Builder(Eqaction.this);
                View view = LayoutInflater.from(Eqaction.this)
                        .inflate(R.layout.actioneq, null, false);
                builder.setView(view);
                final Button ButtonSubmit = (Button) view.findViewById(R.id.okbtn);
                final AlertDialog dialog = builder.create();
                final ImageView imageview = (ImageView) view.findViewById(R.id.image1);
                imageview.setImageResource(R.drawable.locact2);
                ButtonSubmit.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
                break;
            }
            case R.id.btn3: {
                AlertDialog.Builder builder = new AlertDialog.Builder(Eqaction.this);
                View view = LayoutInflater.from(Eqaction.this)
                        .inflate(R.layout.actioneq, null, false);
                builder.setView(view);
                final Button ButtonSubmit = (Button) view.findViewById(R.id.okbtn);
                final AlertDialog dialog = builder.create();
                final ImageView imageview = (ImageView) view.findViewById(R.id.image1);
                imageview.setImageResource(R.drawable.locact3);
                ButtonSubmit.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
                break;
            }
            case R.id.btn4: {
                AlertDialog.Builder builder = new AlertDialog.Builder(Eqaction.this);
                View view = LayoutInflater.from(Eqaction.this)
                        .inflate(R.layout.actioneq, null, false);
                builder.setView(view);
                final Button ButtonSubmit = (Button) view.findViewById(R.id.okbtn);
                final AlertDialog dialog = builder.create();
                final ImageView imageview = (ImageView) view.findViewById(R.id.image1);
                imageview.setImageResource(R.drawable.locact4);
                ButtonSubmit.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
                break;
            }
            case R.id.btn5: {
                AlertDialog.Builder builder = new AlertDialog.Builder(Eqaction.this);
                View view = LayoutInflater.from(Eqaction.this)
                        .inflate(R.layout.actioneq, null, false);
                builder.setView(view);
                final Button ButtonSubmit = (Button) view.findViewById(R.id.okbtn);
                final AlertDialog dialog = builder.create();
                final ImageView imageview = (ImageView) view.findViewById(R.id.image1);
                imageview.setImageResource(R.drawable.locact5);
                ButtonSubmit.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
                break;
            }
            case R.id.btn6: {
                AlertDialog.Builder builder = new AlertDialog.Builder(Eqaction.this);
                View view = LayoutInflater.from(Eqaction.this)
                        .inflate(R.layout.actioneq, null, false);
                builder.setView(view);
                final Button ButtonSubmit = (Button) view.findViewById(R.id.okbtn);
                final AlertDialog dialog = builder.create();
                final ImageView imageview = (ImageView) view.findViewById(R.id.image1);
                imageview.setImageResource(R.drawable.locact6);
                ButtonSubmit.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
                break;
            }
            case R.id.btn7: {
                AlertDialog.Builder builder = new AlertDialog.Builder(Eqaction.this);
                View view = LayoutInflater.from(Eqaction.this)
                        .inflate(R.layout.actioneq, null, false);
                builder.setView(view);
                final Button ButtonSubmit = (Button) view.findViewById(R.id.okbtn);
                final AlertDialog dialog = builder.create();
                final ImageView imageview = (ImageView) view.findViewById(R.id.image1);
                imageview.setImageResource(R.drawable.locact7);
                ButtonSubmit.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
                break;
            }
            case R.id.btn8: {
                AlertDialog.Builder builder = new AlertDialog.Builder(Eqaction.this);
                View view = LayoutInflater.from(Eqaction.this)
                        .inflate(R.layout.actioneq, null, false);
                builder.setView(view);
                final Button ButtonSubmit = (Button) view.findViewById(R.id.okbtn);
                final AlertDialog dialog = builder.create();
                final ImageView imageview = (ImageView) view.findViewById(R.id.image1);
                imageview.setImageResource(R.drawable.locact8);
                ButtonSubmit.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
                break;
            }
            case R.id.btn9: {
                AlertDialog.Builder builder = new AlertDialog.Builder(Eqaction.this);
                View view = LayoutInflater.from(Eqaction.this)
                        .inflate(R.layout.actioneq, null, false);
                builder.setView(view);
                final Button ButtonSubmit = (Button) view.findViewById(R.id.okbtn);
                final AlertDialog dialog = builder.create();
                final ImageView imageview = (ImageView) view.findViewById(R.id.image1);
                imageview.setImageResource(R.drawable.locact9);
                ButtonSubmit.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
                break;
            }

        }
    }
}
