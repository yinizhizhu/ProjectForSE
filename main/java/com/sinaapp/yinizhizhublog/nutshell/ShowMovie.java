package com.sinaapp.yinizhizhublog.nutshell;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class ShowMovie extends ActionBarActivity {

    private TextView titleView;
    private TextView showDate;
    private TextView showScore;
    private TextView showDetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_movie);

        titleView = (TextView)findViewById(R.id.titleView);
        showDate = (TextView)findViewById(R.id.showDate);
        showScore = (TextView)findViewById(R.id.showScore);
        showDetails = (TextView)findViewById(R.id.showDetails);

        Intent i = getIntent();
        Bundle data = i.getExtras();
        titleView.setText(data.getString("name"));
        showDate.setText(data.getString("date"));
        showScore.setText(data.getString("score"));
        showDetails.setText(data.getString("content"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_show_movie, menu);
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
}
