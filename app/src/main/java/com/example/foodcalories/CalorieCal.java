package com.example.foodcalories;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

public class CalorieCal extends AppCompatActivity {

    ListView listView;

    EditText edtUnit;

    public static ArrayList<Model> modelArrayList;

    Button btnBack, btnOk;

    String mat_unit, mat_calories, mat_price, mat_name, Path_img;

    ImageView imgCal1;

    int myArrListSize = 0;

    public TextView titleMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.calorie_cal);

        initWidget();

        modelArrayList = getModel();

        titleMenu.setText(getIntent().getStringExtra("menu_name"));

        myArrListSize = getIntent().getIntExtra("myArrListSize", 0);

        Picasso.with(CalorieCal.this).load(getIntent().getStringExtra("Path_img")).into(imgCal1);
        Path_img = getIntent().getStringExtra("Path_img");

        ArrayList<String> arrayListMatName = new ArrayList<>();
        ArrayList<String> arrayListMatUnit = new ArrayList<>();
        final ArrayList<String> arrayListMatCalories = new ArrayList<>();
        final ArrayList<String> arrayListMatPrice = new ArrayList<>();

        for (int i = 0; i < myArrListSize; i++) {


            mat_name = getIntent().getStringExtra("mat_name"+i);
            mat_unit = getIntent().getStringExtra("mat_unit"+i);
            mat_calories = getIntent().getStringExtra("mat_calories"+i);
            mat_price = getIntent().getStringExtra("mat_price"+i);

            arrayListMatName.add(mat_name);
            if(!(mat_unit.toString().equals("null"))) {
                arrayListMatUnit.add(mat_unit);
            }else{
                arrayListMatUnit.add("0");
            }
            arrayListMatCalories.add(mat_calories);
            arrayListMatPrice.add(mat_price);

        }

        CustomListView adapter = new CustomListView(getApplicationContext(), arrayListMatName, arrayListMatUnit, arrayListMatCalories, arrayListMatPrice);

        listView.setAdapter(adapter);


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        if(getIntent().getStringExtra("menu_name").equals("ไม่มีเมนูนี้ในระบบ")){
            btnOk.setEnabled(false);
        }

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Price = null;
                String Calories = null;
                int sumPrice= 0;
                int sumCalories = 0;
                for (int i = 0; i < listView.getCount(); i++) {
                    View v = listView.getChildAt(i);
                    Calories = arrayListMatCalories.get(i);
                    int resultCalories = Integer.parseInt(Calories);

                    Price = arrayListMatPrice.get(i);
                    int resultPrice = Integer.parseInt(Price);

                    EditText myView = (EditText) v.findViewById(R.id.edtUnit);
                    sumCalories = sumCalories + ((Integer.parseInt( myView.getText().toString() )) * resultCalories);
                    sumPrice = sumPrice + ((Integer.parseInt( myView.getText().toString() )) * resultPrice);
                }

                sumPrice = sumPrice/100;
                sumCalories = sumCalories/100;

                Price = String.valueOf(sumPrice);
                Calories = String.valueOf(sumCalories);

                Intent intent = new Intent(CalorieCal.this, ShowFood.class);
                intent.putExtra("Calories", Calories);
                intent.putExtra("Price", Price);
                intent.putExtra("menu_name",getIntent().getStringExtra("menu_name"));
                intent.putExtra("Path_img", Path_img);
                startActivity(intent);

//                Toast.makeText(getBaseContext(), "sumPrice : " + sumPrice + " sumCalories : " + sumCalories, Toast.LENGTH_LONG).show();

            }
        });

    }

    public void initWidget(){
        btnOk = (Button) findViewById(R.id.btn_ok);
        btnBack = (Button) findViewById(R.id.btn_back);
        titleMenu = (TextView) findViewById(R.id.txt_titlemenu);
        listView = (ListView) findViewById(R.id.calCal);
        edtUnit = (EditText) findViewById(R.id.edtUnit);
        imgCal1 = (ImageView) findViewById(R.id.imgCal1);
    }

    private ArrayList<Model> getModel(){
        ArrayList<Model> list = new ArrayList<>();
        for(int i = 0; i < 5; i++) {

            Model model = new Model();
            model.setNumber(1);
            list.add(model);
        }
        return list;

    }
}
