package com.example.personalityinventory.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.personalityinventory.R;
import com.example.personalityinventory.model.Result;
import com.example.personalityinventory.sql.DatabaseHelper;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.List;

public class ResultGraph extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;

    private DatabaseHelper databaseHelper;
    private final AppCompatActivity activity = ResultGraph.this;

    String id;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_graph);

        mDrawerList = (ListView) findViewById(R.id.navList);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();

        addDrawerItems();
        setupDrawer();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        TextView textView=(TextView)findViewById(R.id.textView);

        id = getIntent().getStringExtra("TESTID");
        userID = getIntent().getStringExtra("USERID");

        databaseHelper = new DatabaseHelper(activity);
        List<Result> resultList = new ArrayList<Result>();
        resultList = databaseHelper.getResults(Integer.parseInt(id), Integer.parseInt(userID));

        DataPoint[] dataPoints = new DataPoint[resultList.size()];
        String message = "";
        String[] subjects = new String[resultList.size()];
        String[] labels = new String[resultList.size()];

        for (int i = 0; i < resultList.size(); i++) {
            dataPoints[i] = new DataPoint(i, resultList.get(i).getPercent() * 20);
            labels[i]=Integer.toString(resultList.get(i).getSubjectID());
            subjects[i] = databaseHelper.getSubjectName(resultList.get(i).getSubjectID());
            message += resultList.get(i).getSubjectID() + " " + subjects[i] + "\n";
        }

       // Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();


        GraphView graph = (GraphView) findViewById(R.id.graph);
        BarGraphSeries<DataPoint> series = new BarGraphSeries<>(dataPoints);


        graph.addSeries(series);

        series.setDrawValuesOnTop(true);
        series.setValuesOnTopColor(Color.BLACK);

        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
        staticLabelsFormatter.setHorizontalLabels(labels);
        graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);

        textView.setText(message);
    }

    private void addDrawerItems() {
        String[] osArray = {"Home", "Tests"};
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, osArray);
        mDrawerList.setAdapter(mAdapter);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /*Toast.makeText(Home.this, "Time for an upgrade!", Toast.LENGTH_SHORT).show();*/
                if (position == 0) {
                    Intent intent = new Intent(ResultGraph.this, Home.class);
                    intent.putExtra("USERID", userID);
                    ResultGraph.this.startActivity(intent);
                    ResultGraph.this.finish();
                }
                if (position == 1) {
                    Intent intent = new Intent(ResultGraph.this, TestList.class);
                    intent.putExtra("USERID", userID);
                    ResultGraph.this.startActivity(intent);
                    ResultGraph.this.finish();
                }
            }
        });
    }

    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Menu");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.addDrawerListener(mDrawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
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

        // Activate the navigation drawer toggle
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
