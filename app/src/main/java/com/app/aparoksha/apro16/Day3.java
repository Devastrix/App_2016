package com.app.aparoksha.apro16;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.util.ArrayList;
import java.util.HashMap;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by Satyam Poddar on 30-Jan-16.
 */
public class Day3 extends AppCompatActivity{
    private Toolbar toolbar;
    private SwipeMenuListView mList;
    String[] events_1;
    int[] images;
    String[] timing;
    String[] intents;
    TextView eventName,act_name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                        .setDefaultFontPath("fonts/JosefinSans-Regular.ttf")
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );
        setContentView(R.layout.eventsnow);

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        act_name = (TextView)findViewById(R.id.activity_name);
        Typeface tf1 = Typeface.createFromAsset(getAssets(),
                "JosefinSans-Regular.ttf");

        act_name.setTypeface(tf1);
        act_name.setText("Day 4");

        mList = (SwipeMenuListView) findViewById(R.id.listView);
        // step 1. create a MenuCreator
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                openItem.setBackground(new ColorDrawable(Color.rgb(0x52, 0xb3,
                        0xd9)));
                // set item width
                openItem.setWidth(dp2px(90));
                // set item title
                openItem.setTitle("Open");
                // set item title fontsize
                openItem.setTitleSize(18);
                // set item title font color
                openItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(openItem);

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xff,
                        0x00, 0x00)));
                // set item width
                deleteItem.setWidth(dp2px(90));
                // set a icon
                deleteItem.setIcon(R.drawable.ic_settings);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };
        // set creator
        mList.setMenuCreator(creator);
        mList.setOpenInterpolator(new BounceInterpolator());
        mList.setCloseInterpolator(new BounceInterpolator());



        //Add events in this array
        events_1 = new String[] {"Pool Master","Flappy Bird","Novita","Three Musketeers","Riddilonics Final Round","C Fresh Finals",
                "Android Quiz","Backbone"};

        images = new int[] {R.drawable.pool,R.drawable.flappy,R.mipmap.ic_launcher,R.drawable.threemuski,R.drawable.riddlonics,
        R.drawable.cfresh,R.drawable.android,R.drawable.backbone};

        timing = new String[] {"11 AM - 1 PM, Swimming Pool","12 AM - 1 PM, Tennis Court","1.30 PM - 4.30 PM, Admin Audi",
                "1.30 PM - 3.30 PM, CC3","1.30 PM - 3.30 PM, Lab 2311","3.30 PM - 4.30 PM, CC3","3.30 PM - 4.30 PM, CC3 5006","4.30 PM - 6.30 PM"
        ,"CC3 First Floor Labs"};

        intents = new String[]{"POO", "FLA", "MAIN1", "THR", "RID","CFR","AND","BAC" };

        initList(events_1, images, timing, intents);

    }


    public void initList(String[] eventsArray, int[] imagesList, String[] timingList, String[] intentsList) {
        if(eventsArray.length != 0) {

            ArrayList<HashMap<String, String>> eventList = new ArrayList<HashMap<String, String>>();

            for(int i = 0; i < eventsArray.length; i++) {
                HashMap<String, String> candy = new HashMap<String, String>();
                candy.put("event", eventsArray[i]);
                candy.put("image", Integer.toString(imagesList[i]));
                candy.put("time", timingList[i]);
                candy.put("intent", "com.app.aparoksha.apro16."+intentsList[i].trim());
                eventList.add(candy);
            }

            ListAdapter adapter = new SimpleAdapter(
                    Day3.this ,
                    eventList,
                    R.layout.list_item,
                    new String[] { "event", "image", "time", "intent" },
                    new int[] { R.id.event_name, R.id.eventImg, R.id.eventTime, R.id.intent }) {
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    View view = super.getView(position, convertView, parent);
                    TextView item_name = (TextView)view.findViewById(R.id.event_name);

                    return view;
                }
            };

            mList.setAdapter(adapter);
        }

        mList.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        // open

                        String intentToOpen = "com.app.aparoksha.apro16." + intents[position];

                        Intent i = new Intent(intentToOpen);
                        i.putExtra("INTENT", intentToOpen);
                        startActivity(i);

                        break;
                    case 1:
                        Intent it = new Intent("com.app.aparoksha.apro16.SN");
                        it.putExtra("event", events_1[position]);
                        it.putExtra("address","com.app.aparoksha.apro16."+ intents[position]);
                        startActivity(it);
                        break;
                }
                return false;
            }
        });

    }
    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
