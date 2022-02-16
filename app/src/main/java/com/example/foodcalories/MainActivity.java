package com.example.foodcalories;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    public Button next;
    public TextView menu;
    public String url_link = "http://192.168.1.45:8080/foodcalories/connect.php?status=0&action=getfood";

    public String smenu_id = "";
    public String smenu_name = "";
    public String smat_name = "";
    public  String smat_unit = "";
    public String smat_calories = "";
    public String smat_price = "";
    public String Path_img = "";
    public String getMenu = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initWidget();

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(menu.getText().length() > 0) {

                    url_link = url_link + "&food=" + menu.getText().toString().trim();
                    getMenu = menu.getText().toString().trim();

                    AsyncTaskRunner runner = new AsyncTaskRunner();
                    runner.execute();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        menu.setText("");
//        url_link = "";
    }

    public void initWidget(){
        menu = (TextView) findViewById(R.id.edtFood);
        next = (Button)findViewById(R.id.butMain1);
    }

    private class AsyncTaskRunner extends AsyncTask<String, String, String> {

        //            When AsyncTask to use it start firsty
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d("J onPreExecute ","onPreExecute");

        }

        @Override
        protected String doInBackground(String... strings) {
            Log.d("J doInBackground ", "doInBackground");
            try {
                Log.d("J doInBackground", "Try");
                String URL = url_link;
                URL url = new URL(URL);
                URLConnection urlConnection = url.openConnection();
                Log.d("J doInBackground", "URL");
                Log.d("J doInBackground", url_link);

                HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
                httpURLConnection.setAllowUserInteraction(false);
                httpURLConnection.setInstanceFollowRedirects(true);
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.connect();
                Log.d("J doInBackground", "httpURLConnection");


                InputStream inputStream = null;
                if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    Log.d("J doInBackground", "HTTP_OK");
                    inputStream = httpURLConnection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);

                    StringBuilder stringBuilder = new StringBuilder();
                    String line = null;

                    while ((line = reader.readLine()) != null) {
                        stringBuilder.append(line + "\n");
                    }

                    inputStream.close();



                    JSONObject jsonObject = new JSONObject(stringBuilder.toString());

                    JSONArray data = new JSONArray(jsonObject.getString("result"));

                    Log.d("J doInBackground", "HTTP_OK");

                    Log.d("J doInBackground", "jsonObject.getString(\"result\") " + data);

                    Log.d("J doInBackground", "HTTP_OK");
                    ArrayList<HashMap<String, String>> myArrList = new ArrayList<HashMap<String, String>>();
                    HashMap<String, String> map;

                    for(int i = 0; i < data.length(); i++){
                        JSONObject c = data.getJSONObject(i);
                        map = new HashMap<String, String>();

                        if(!(c.getString("mat_unit").isEmpty())) {
                            map.put("menu_id", c.getString("menu_id"));
                            map.put("menu_name", c.getString("menu_name"));
                            map.put("mat_name", c.getString("mat_name"));
                            map.put("mat_unit", c.getString("mat_unit"));
                            map.put("mat_calories", c.getString("mat_calories"));
                            map.put("mat_price", c.getString("mat_price"));
                            map.put("path_img", c.getString("path_img"));
                            myArrList.add(map);
                        }
                    }

                    Intent intent = new Intent(MainActivity.this, CalorieCal.class);

                    for (int i = 0; i < myArrList.size(); i++) {

                        intent.putExtra("mat_name"+i, myArrList.get(i).get("mat_name").toString());
                        intent.putExtra("mat_unit"+i, myArrList.get(i).get("mat_unit").toString());
                        intent.putExtra("mat_calories"+i, myArrList.get(i).get("mat_calories").toString());
                        intent.putExtra("mat_price"+i, myArrList.get(i).get("mat_price").toString());

                         smenu_id = myArrList.get(i).get("menu_id").toString();
                         smenu_name = myArrList.get(i).get("menu_name").toString();
                         smat_name = myArrList.get(i).get("mat_name").toString();
                         smat_unit = myArrList.get(i).get("mat_unit").toString();
                         smat_calories = myArrList.get(i).get("mat_calories").toString();
                         smat_price = myArrList.get(i).get("mat_price").toString();

                        Path_img = myArrList.get(i).get("path_img").toString();


                        Log.d("J doInBackground Array", "smenu_id " + smenu_id);
                        Log.d("J doInBackground Array", "smenu_name " + smenu_name);
                        Log.d("J doInBackground Array", "smat_name " + smat_name);
                        Log.d("J doInBackground Array", "smat_unit " + smat_unit);
                        Log.d("J doInBackground Array", "smat_calories " + smat_calories);
                        Log.d("J doInBackground Array", "smat_price " + smat_price);


                    }

                    intent.putExtra("myArrListSize", myArrList.size());

                    intent.putExtra("Path_img", Path_img);
                    intent.putExtra("menu_name", smenu_name);

                    if(data.length() == 0){
//                        Toast.makeText(MainActivity.this,"ไม่มีเมนูนี้ในระบบ", Toast.LENGTH_SHORT);
                        intent.putExtra("menu_name", "ไม่มีเมนูนี้ในระบบ");
                        intent.putExtra("Path_img", "https://firebasestorage.googleapis.com/v0/b/food-calories-price.appspot.com/o/nofood.webp?alt=media&token=b8e38771-719c-46e0-88a4-09600fe0f6c4");
                        startActivity(intent);
                    }else {
                        startActivity(intent);
                    }

                } else{

                    Toast.makeText(getBaseContext(),"ไม่สามารถติดต่อเซิร์ฟเวอร์ได้ในขณะนี้", Toast.LENGTH_SHORT);
                }



            } catch (MalformedURLException e) {
                Log.d("J doInBackground catch", "MalformedURLException");
                e.printStackTrace();
            } catch (IOException e) {
                Log.d("J doInBackground catch", "IOException");
                e.printStackTrace();
                e.getMessage();
            } catch (JSONException e) {
                Log.d("J doInBackground catch", "JSONException");
                e.printStackTrace();
            } catch (RuntimeException e) {
                Log.d("J doInBackground catch", "RuntimeException");
                e.printStackTrace();
            }finally {

            }

            return null;
        }

        @Override
        protected void onPostExecute(String aVoid) {
            super.onPostExecute(aVoid);

            Log.d("J onPostExecute","Start onPostExecute");
            return;
        }

    }
}
