package com.example.boundservice43;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
// might need to import com.example.boundservice43.PNJBoundService

public class MainActivity extends AppCompatActivity {

    PNJBoundService pnjBoundService;
    boolean isBound = false;        // flag to track bound-ness of service at mo

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // set up intent to bind showTime to the text to show time using thread in a bound service
        Intent i = new Intent(this, PNJBoundService.class);
        bindService(i, pnjConnection, Context.BIND_AUTO_CREATE);  // bind instead of just starting Intent
        // needing Intent and ServiceConnection instance

        FloatingActionButton fab = findViewById(R.id.fab);
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
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private ServiceConnection pnjConnection = new ServiceConnection()
    {
        // auto-completed a signature for onServiceConnected
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            PNJBoundService.PNJLocalBinder binder = (PNJBoundService.PNJLocalBinder) service;
            pnjBoundService = binder.getService();       // assign local var via reference to Bound Service class
            isBound = true;     // set flag
            // here the client and service are bound
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBound = false;        // clear flag
        }
    };

    public void showTime(View view)
    {
        String currentTime = pnjBoundService.getCurrentTime();
        // get the formatted time calculated inside the thread of the bound service
        EditText pnjText = findViewById(R.id.pnj_text);
        pnjText.setText(currentTime);
        // fill out the
    }

}
