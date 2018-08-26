package comfizztheturtle.httpsgithub.liveweatherwallpaper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;


import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import comfizztheturtle.httpsgithub.liveweatherwallpaper.Find_Data;


public class tabbed_activity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    private final static Find_Data find_weather_data= new Find_Data();

//    change broadcast service to timer


    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private boolean update_status=true;

    private MenuItem item_refresh;

    private final static String TAG = "BroadcastService";
//    MyDBHandler db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabbed_activity);
//        db=new MyDBHandler(getApplicationContext());

//        startService(new Intent(this, BroadcastService.class));

//
//        try {
//            get_weather_info();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
//        android.R.id.icon

        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main2, menu);
        return true;
        // Handle item selection

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.refresh:
                item_refresh = item;
                // Create a count down timer which will count 60 seconds and invoke the timer object onTick() method every second.
                final BroadcastService myCountDownTimer = new BroadcastService(86400*1000, 1000);

                // Set count down timer source activity.
                myCountDownTimer.setSourceActivity(this);

                // Start the count down timer.
                myCountDownTimer.start();

//
                try {
                    add_weather_info();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // Disable send verify code button.
                item.setEnabled(false);


//                AlertDialog alertDialog = new AlertDialog.Builder(CountDownTimerActivity.this).create();
//                alertDialog.setMessage("Verification code has been send through sms. " +
//                        "Click this button to resend 60 seconds later.");
//                alertDialog.show();
                return true;
            case R.id.action_settings:
//                showHelp();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    /* This method will be invoked when CountDownTimer finish. */
    public void onCountDownTimerFinishEvent()
    {
        item_refresh.setEnabled(true);
    }

    /* This method will be invoked when CountDownTimer tick event happened.*/
    public void onCountDownTimerTickEvent(long millisUntilFinished)
    {
        // Calculate left seconds.
        long leftSeconds = millisUntilFinished / 1000;

        String sendButtonText = "Left " + leftSeconds + " (s)";

        if(leftSeconds==0)
        {
            sendButtonText = "Send Verification Code";
        }

        // Show left seconds in send button.
        item_refresh.setTitle(sendButtonText);
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
//        private final List<Fragment> mFragmentList = new ArrayList<>();
//        private final List<String> mFragmentTitleList = new ArrayList<>();
        private SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch(position){
                case 0:
                    tab1_sunny tab1 =new tab1_sunny();
                    return tab1;
                case 1:
                    tab2_rain tab2 =new tab2_rain();
                    return tab2;
                case 2:
                    tab3_cloudy tab3 =new tab3_cloudy();
                    return tab3;
                default:
                    return null;

            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            // Show 3 total pages.
            switch (position) {
                case 0:
                    return "sunny";
                case 1:
                    return "rain";
                case 2:
                    return "cloudy";
            }
        return null;
        }
    }

    //        memory leaks are possible
//    private void get_weather_info() throws Exception {
//        new image_class();
//        image_class weather_result = db.find_ID(0);
//        Log.e(TAG, weather_result.toString());
//        String ltf=weather_result.get_link_to_file();
//        int ltf2=Integer.parseInt(ltf);
//
//    }

    private void add_weather_info() throws Exception {
//        new image_class();
////        image_class weather_result = db.find_ID(0);
//        Log.e(TAG, weather_result.toString());
////        String ltf=weather_result.get_link_to_file();
////        int ltf2=Integer.parseInt(ltf);
//        finding_data.get_weather(this,0);

    new finding_data();
    weather_data finding_weather_data = finding_data.add_weather(this,R.raw.api_key );

        Log.e(TAG, find_weather_data.toString());

    }






}
