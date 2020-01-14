package br.com.erico.desafioappia.views;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.erico.desafioappia.R;
import br.com.erico.desafioappia.controls.Connection;
import br.com.erico.desafioappia.models.Measurement;

public class Dashboard extends AppCompatActivity {

    private TextView textViewAuth;
    private BarChart barChart;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    private List<Measurement> measurementList = new ArrayList<>();

    private FirebaseAuth auth;
    private FirebaseUser user;

    //private int[] itensGr = {153, 190, 134, 65, 112};

    //private String[] descrip = {"01/12", "02/12", "03/12", "04/12", "05/12"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        startComponents();
        startFirebase();
        eventDataBase();
        starGraphic();
    }

    @Override
    protected void onStart() {
        super.onStart();
        auth = Connection.getFirebaseAuth();
        user = Connection.getFirebaseUser();
        verifyAuth();
    }

    private void eventDataBase(){
        databaseReference.child("Measurement").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot objSnapshot:dataSnapshot.getChildren()){
                    Measurement measurement = objSnapshot.getValue(Measurement.class);
                    measurementList.add(measurement);
                }

                starGraphic();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

    private void startFirebase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        //firebaseDatabase.setPersistenceEnabled(true);
        databaseReference = firebaseDatabase.getReference();
    }

    private void startComponents() {
        textViewAuth = (TextView)findViewById(R.id.textViewAuth);
        barChart = (BarChart) findViewById(R.id.graphicID);
    }

    private void starGraphic()  {

        List<BarEntry> barEntries = new ArrayList<>();
        List<String> labels = new ArrayList<>();

        for(int i = 0; i < measurementList.size(); i++){
            barEntries.add(new BarEntry(i, measurementList.get(i).getGlucose()));
            labels.add(measurementList.get(i).getDay());
        }

        BarDataSet barDataSet = new BarDataSet(barEntries, "Níveis de Glicose");
        barDataSet.setColors(ColorTemplate.rgb("#95A67A"));
        barDataSet.setDrawValues(true);

        BarData barData = new BarData(barDataSet);
        barData.setValueTextColor(Color.BLACK);
        barData.setBarWidth(0.9f);

        barChart.getDescription().setEnabled(false);
        barChart.setDrawGridBackground(false);
        barChart.invalidate();

        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        xAxis.setDrawGridLines(false);

        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setLabelCount(5, false);
        leftAxis.setSpaceTop(15f);

        YAxis rightAxis = barChart.getAxisRight();
        rightAxis.setLabelCount(5, false);
        rightAxis.setSpaceTop(15f);

        barChart.setData(barData);
        barChart.setFitBars(true);

        barChart.animateY(500);

    }

    private void verifyAuth() {
        if(user == null){
            finish();
        }else{
            textViewAuth.setText("ID: " + user.getUid() + "\nE-mail: " + user.getEmail());
        }
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
            Connection.logOut();
            Intent intent = new Intent(this, Auth.class);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
