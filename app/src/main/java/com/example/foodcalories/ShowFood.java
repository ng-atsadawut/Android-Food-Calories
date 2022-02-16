package com.example.foodcalories;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class ShowFood extends AppCompatActivity {

    public TextView txtFinalPrice, txtFinalCal, txt_titlemenu;

    public ImageView Img;

    public Button butShow1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_food);


        initWidget();

//        txtFinalCal.setText("---------");

        try {
            txtFinalCal.setText(getIntent().getStringExtra("Calories"));
            txtFinalPrice.setText(getIntent().getStringExtra("Price"));
            txt_titlemenu.setText(getIntent().getStringExtra("menu_name"));
            Picasso.with(ShowFood.this).load(getIntent().getStringExtra("Path_img")).into(Img);

            butShow1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                    startActivity(new Intent(ShowFood.this, MainActivity.class));
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void initWidget(){
        txtFinalPrice = (TextView) findViewById(R.id.txtFinalPrice);
        txtFinalCal = (TextView) findViewById(R.id.txtFinalCal);
        txt_titlemenu = (TextView) findViewById(R.id.txt_titlemenu);
        Img = (ImageView) findViewById(R.id.imgFood);
        butShow1 = (Button) findViewById(R.id.butShow1);
    }
}
