package com.example.personalityinventory.activities;

import android.content.Intent;
import android.content.res.Configuration;
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
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.personalityinventory.R;
import com.example.personalityinventory.adapters.QuestionsAdapter;
import com.example.personalityinventory.model.Question;
import com.example.personalityinventory.model.Result;
import com.example.personalityinventory.model.Test;
import com.example.personalityinventory.sql.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class TestQuestions extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;


    ListView questionsListView;
    List<Question> questionsList = new ArrayList<>();
    Button submitTest;

    private DatabaseHelper databaseHelper;
    private final AppCompatActivity activity = TestQuestions.this;

    String id;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_questions);

        mDrawerList = (ListView) findViewById(R.id.navList);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();

        addDrawerItems();
        setupDrawer();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        id = getIntent().getStringExtra("testID");
        userID = getIntent().getStringExtra("USERID");


        databaseHelper = new DatabaseHelper(activity);
        questionsList.addAll(databaseHelper.getQuestions(Integer.parseInt(id)));
       /* for(int i=0;i<questionList.size();i++){
         questions[i]=questionList.get(i).getQuestionContent();
        }*/

        questionsListView = (ListView) findViewById(R.id.questionsListView);
        submitTest = (Button) findViewById(R.id.submitTest);

        QuestionsAdapter questionsAdapter = new QuestionsAdapter(getApplicationContext(), questionsList);
        questionsListView.setAdapter(questionsAdapter);

        submitTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = "" + id + userID;
                if(databaseHelper.getResults(Integer.parseInt(id),Integer.parseInt(userID)).isEmpty()) {
                    for (int i = 0; i < QuestionsAdapter.selectedAnswers.size(); i++) {
                        message = message + "\n" + (i + 1) + " " + QuestionsAdapter.selectedAnswers.get(i);
                        Result result = new Result(Integer.parseInt(userID), Integer.parseInt(id), questionsList.get(i).getSubjectID(), Float.parseFloat(QuestionsAdapter.selectedAnswers.get(i)), questionsList.get(i).getQuestionID());
                        databaseHelper.addResult(result);
                    }
                   // Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                }
                else {
                    for (int i = 0; i < QuestionsAdapter.selectedAnswers.size(); i++) {
                        message = message + "\n" + (i + 1) + " " + QuestionsAdapter.selectedAnswers.get(i);
                        Result result = new Result(Integer.parseInt(userID), Integer.parseInt(id), questionsList.get(i).getSubjectID(), Float.parseFloat(QuestionsAdapter.selectedAnswers.get(i)), questionsList.get(i).getQuestionID());
                        databaseHelper.updateResult(result);
                    }
                   // Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                }
                Intent intent = new Intent(TestQuestions.this, ResultGraph.class);
                intent.putExtra("USERID", userID);
                intent.putExtra("TESTID", id);
                TestQuestions.this.startActivity(intent);
                TestQuestions.this.finish();
            }
        });
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
                    Intent intent = new Intent(TestQuestions.this, Home.class);
                    intent.putExtra("USERID", userID);
                    TestQuestions.this.startActivity(intent);
                    TestQuestions.this.finish();
                }
                if (position == 1) {
                    Intent intent = new Intent(TestQuestions.this, TestList.class);
                    intent.putExtra("USERID", userID);
                    TestQuestions.this.startActivity(intent);
                    TestQuestions.this.finish();
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
