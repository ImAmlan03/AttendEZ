package com.amlan.attendez;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amlan.attendez.Adapter.ClassListAdapter;
import com.amlan.attendez.realm.Class_Names;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import io.realm.Realm;
import io.realm.RealmResults;

public class ClassActivity extends AppCompatActivity {

    BottomAppBar bottomAppBar;
    FloatingActionButton fab_main;
    RecyclerView recyclerView;
    TextView sample;
    ImageView ivLogout;
    ClassListAdapter mAdapter;

    Realm realm;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class);
        Realm.init(this);

        getWindow().setEnterTransition(null);
        ivLogout = findViewById(R.id.ivLogout);
        bottomAppBar = findViewById(R.id.bottomAppBar);
        fab_main = findViewById(R.id.fab_main);

        ivLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(ClassActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
        fab_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ClassActivity.this, Insert_class_Activity.class);
                startActivity(intent);
            }
        });

        realm = Realm.getDefaultInstance();

        RealmResults<Class_Names> results;

        results = realm.where(Class_Names.class)
                .findAll();

        sample = findViewById(R.id.classes_sample);
        recyclerView = findViewById(R.id.recyclerView_main);

        recyclerView.setHasFixedSize(true);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(staggeredGridLayoutManager);

        mAdapter = new ClassListAdapter(results, ClassActivity.this);
        recyclerView.setAdapter(mAdapter);

    }

    @Override
    protected void onResume() {
        realm.refresh();
        realm.setAutoRefresh(true);
        super.onResume();
    }
}