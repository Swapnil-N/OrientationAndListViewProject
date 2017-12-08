package com.example.swapn.orientationandlistviewproject;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

class Car implements Serializable{

    private String name;
    private int speed;
    private int hp;
    private String content;

    public Car (String name, int speed, int hp, String content){
        this.name = name;
        this.speed = speed;
        this.hp = hp;
        this.content = content;
    }

    public String getName(){
        return name;
    }
    public int getSpeed(){
        return speed;
    }
    public int getHp(){
        return hp;
    }
    public  String getContent() {
        return content;
    }
}


public class OrientationAndListViewProjectActivity extends AppCompatActivity {

    ListView listView;
    TextView textView1;
    TextView textView2;
    TextView CtextView;
    ArrayList<Car> carArray;
    int currentPos = -1;

    public static final String ARRAY_KEY = "array key";
    public static final String TS_KEY = "top speed";
    public static final String HP_KEY = "horsepower";
    public static final String C_KEY = "current";

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(ARRAY_KEY,carArray);
        outState.putString(TS_KEY, textView1.getText().toString());
        outState.putString(HP_KEY, textView2.getText().toString());
        outState.putInt(C_KEY, currentPos);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orientation_and_list_view_project);

        listView = (ListView) findViewById(R.id.id_listView);
        textView1 = (TextView) findViewById(R.id.textView1);
        textView2 = (TextView) findViewById(R.id.textView2);
        CtextView = (TextView) findViewById(R.id.content_textView);

        carArray = new ArrayList<>();
        carArray.add(new Car("KOENIGSEGG AGERA R",273,1140,"This is the fastest car on my list. It is 3 mph faster than the next fastest car in my list. Even though it is from KOENIGSEGG, not a well known company, they were able to make one of the fastest car ever."));
        carArray.add(new Car("HENNESSEY VENOM GT",270,1244,"This car is right behind the Agera R in top speed even though it has 104 more HP. The GT stands for Grand Turismo and means it has a better engine. Only 13 such cars has been made by Hennessey."));
        carArray.add(new Car("BUGATTI VEYRON SUPER SPORT",268,1200,"This car usd to hold the title for the fastest car until recently. It was one of the first cars to ever break the 250 MPH limit. The super sport is the fastest model for the Bugatti Veyron."));
        carArray.add(new Car("9FF GT9-R",260,1120,"This car was based on the Porche 911 and built to test the limits of street legal cars. Only 20 versions with the most powerful engine were ever made. It is manufactured by 9ff, not a comman brand but a fast one."));
        carArray.add(new Car("SSC ULTIMATE AERO",256,1287,"The SSC Ultimate Aero once held the record for the fast caar ever until the Veyron stole the number one spot. It has the most horsepower on this list but is unable to reach speeds above 260 MPH."));
        carArray.add(new Car("MCLAREN F1", 241,670,"The McLaren F1 is a looks amazing. Though it only has 670 HP it is still one of the fastest cars in the world. McLaren is an amazing race car manufacurer but is also capable of making fast street legal cars."));

        if (savedInstanceState != null){
            carArray = (ArrayList) savedInstanceState.getSerializable(ARRAY_KEY);
            textView1.setText(savedInstanceState.getString(TS_KEY));
            textView2.setText(savedInstanceState.getString(HP_KEY));
            currentPos = savedInstanceState.getInt(C_KEY);
            if (currentPos != -1 && CtextView != null) {
                CtextView.setText(carArray.get(currentPos).getContent());
            }
        }

        CustomAdapter adapter = new CustomAdapter(this, R.layout.list_layout,carArray);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                currentPos = i;
                textView1.setText("Top Speed: " + carArray.get(i).getSpeed()+ " mph");
                textView2.setText("Horse power: " + carArray.get(i).getHp());
                if (CtextView!=null){
                    CtextView.setText(carArray.get(i).getContent());
                }
            }
        });
    }

    public class CustomAdapter extends ArrayAdapter<Car>{

        Context context;
        List<Car> list;

        public CustomAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Car> objects) {
            super(context, resource, objects);

            this.context = context;
            list = objects;
        }

        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

            View adapterView = inflater.inflate(R.layout.list_layout,null);

            TextView textView = adapterView.findViewById(R.id.id_textView);
            ImageView imageView = adapterView.findViewById(R.id.imageView);
            Button button = adapterView.findViewById(R.id.button);

            textView.setText(list.get(position).getName());

            if (list.get(position).getName().equals("KOENIGSEGG AGERA R"))
                imageView.setImageResource(R.drawable.koenigsegg);
            else if (list.get(position).getName().equals("HENNESSEY VENOM GT"))
                imageView.setImageResource(R.drawable.henesseyvenom);
            else if (list.get(position).getName().equals("BUGATTI VEYRON SUPER SPORT"))
                imageView.setImageResource(R.drawable.bugatti);
            else if (list.get(position).getName().equals("9FF GT9-R"))
                imageView.setImageResource(R.drawable.car4);
            else if (list.get(position).getName().equals("SSC ULTIMATE AERO"))
                imageView.setImageResource(R.drawable.sscultimateaero);
            else if (list.get(position).getName().equals("MCLAREN F1"))
                imageView.setImageResource(R.drawable.mclaren);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    carArray.remove(position);
                    notifyDataSetChanged();
                    textView1.setText("Top Speed:");
                    textView2.setText("Horse power:");
                    if (CtextView!= null)
                        CtextView.setText("Select a car for more info");
                    currentPos = -1;
                }
            });

            return adapterView;

        }
    }
}
