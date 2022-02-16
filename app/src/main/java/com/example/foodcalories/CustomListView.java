package com.example.foodcalories;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CustomListView extends BaseAdapter {
    ArrayList<String> name;
    ArrayList<String> unit;
    ArrayList<String> calories;
    ArrayList<String> price;

    Context mContext;
    ArrayList<Integer> iconadapter;

    public CustomListView(Context context, ArrayList<String> name, ArrayList<String> unit, ArrayList<String> calories, ArrayList<String> price) {
        this.name = name;
        this.unit = unit;
        this.calories = calories;
        this.price = price;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return name.size();
    }

    @Override
    public Object getItem(int i) {
        return CalorieCal.modelArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final ViewHolder holder;
        LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (view != null) {
            holder = (ViewHolder) view.getTag();
        } else {
            holder = new ViewHolder(); LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_view_cal, null, true);

            holder.btnAdd = (Button) view.findViewById(R.id.btnAdd);
            holder.btnSub = (Button) view.findViewById(R.id.btnSub);

            view.setTag(holder);
        }

        holder.txtMenuname = (TextView) view.findViewById(R.id.txtMenuname);
        holder.txtMenuname.setText(name.get(i));


        holder.edtUnit = (EditText) view.findViewById(R.id.edtUnit);
        holder.edtUnit.setText(unit.get(i));

        holder.btnAdd.setTag(R.integer.btn_plus_view, view);
        holder.btnAdd.setTag(R.integer.btn_plus_pos, i);
        holder.btnAdd.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View tempview = (View) holder.btnAdd.getTag(R.integer.btn_plus_view);
                TextView tv = (TextView) tempview.findViewById(R.id.edtUnit);
                Integer pos = (Integer) holder.btnAdd.getTag(R.integer.btn_plus_pos);

                int number = Integer.parseInt(tv.getText().toString()) + 1;
                tv.setText(String.valueOf(number));

                CalorieCal.modelArrayList.get(pos).setNumber(number);
            }
        });

        holder.btnSub.setTag(R.integer.btn_minus_view, view);
        holder.btnSub.setTag(R.integer.btn_minus_pos, i);
        holder.btnSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                View tempview = (View) holder.btnSub.getTag(R.integer.btn_minus_view);
                TextView tv = (TextView) tempview.findViewById(R.id.edtUnit);
                Integer pos = (Integer) holder.btnSub.getTag(R.integer.btn_minus_pos);

                if(!(tv.getText().toString().equals("0"))) {
                    int number = Integer.parseInt(tv.getText().toString()) - 1;
                    tv.setText(String.valueOf(number));
                    CalorieCal.modelArrayList.get(pos).setNumber(number);
                }


            }
        });


        return view;
    }
}

class ViewHolder {
    TextView txtMenuname;
    Button btnAdd;
    Button btnSub;
    EditText edtUnit;
}