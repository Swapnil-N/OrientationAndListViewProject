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

    String name;
    int speed;
    int hp;
    String content;

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
    Car car1;
    Car car2;
    Car car3;

    public static final String ARRAY_KEY = "array key";
    public static final String TS_KEY = "top speed";
    public static final String HP_KEY = "horsepower";

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(ARRAY_KEY,carArray);
        outState.putString(TS_KEY, textView1.getText().toString());
        outState.putString(HP_KEY, textView2.getText().toString());
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
        carArray.add(new Car("KOENIGSEGG AGERA R",273,1140,"qwer"));
        carArray.add(new Car("HENNESSEY VENOM GT",270,1244,"asdf"));
        carArray.add(new Car("BUGATTI VEYRON SUPER SPORT",268,1200,"zxcv"));
        carArray.add(new Car("9FF GT9-R",257,1120,"uiop"));
        carArray.add(new Car("SSC ULTIMATE AERO",256,1287,"vnbm"));
        carArray.add(new Car("MCLAREN F1", 241,670,"jkl"));

        if (savedInstanceState != null){
            carArray = (ArrayList) savedInstanceState.getSerializable(ARRAY_KEY);
            textView1.setText(savedInstanceState.getString(TS_KEY));
            textView2.setText(savedInstanceState.getString(HP_KEY));
        }

        CustomAdapter adapter = new CustomAdapter(this, R.layout.list_layout,carArray);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
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

            if (position == 0)
                imageView.setImageResource(R.drawable.koenigsegg);
            else if (position == 1)
                imageView.setImageResource(R.drawable.henesseyvenom);
            else if (position == 2)
                imageView.setImageResource(R.drawable.bugatti);
            else if (position == 3)
                imageView.setImageResource(R.drawable.car4);
            else if (position == 4)
                imageView.setImageResource(R.drawable.sscultimateaero);
            else if (position == 5)
                imageView.setImageResource(R.drawable.mclaren);
            
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    carArray.remove(position);
                    notifyDataSetChanged();
                    textView1.setText("Top Speed:");
                    textView2.setText("Horse power:");
                }
            });

            return adapterView;

        }
    }
}
