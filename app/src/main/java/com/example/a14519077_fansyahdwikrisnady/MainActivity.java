package com.example.a14519077_fansyahdwikrisnady;

import android.content.DialogInterface;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    DbHelper dbHelper;
    ArrayAdapter<String> mAdapter;
    ListView lstTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new DbHelper(this);
        lstTask = (ListView)findViewById(R.id.lstTask);
        loadTaskList();
    }
    private void loadTaskList() {
        ArrayList<String> taskList = dbHelper.getTaskList();
        if(mAdapter==null){
            mAdapter = new ArrayAdapter<String>(this,R.layout.row,R.id.task_title,taskList);
            lstTask.setAdapter(mAdapter);
        } else{ mAdapter.clear();
            mAdapter.addAll(taskList);
            mAdapter.notifyDataSetChanged();
        }
    }
    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
//Change menu icon color
        Drawable icon = menu.getItem(0).getIcon();
        icon.mutate();
        icon.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_IN);
        return super.onCreateOptionsMenu(menu);
    }
    @Override public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_add_task: final EditText taskEditText = new EditText(this);
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle("Tambah Tugas")
                        .setMessage("Apa yang kamu ingin lakukan hari ini ?")
                        .setView(taskEditText)
                        .setPositiveButton("Tambah", new DialogInterface.OnClickListener() {
                                    @Override public void onClick(DialogInterface dialog, int which) {
                                        String task = String.valueOf(taskEditText.getText());
                                        dbHelper.insertNewTask(task);
                                        loadTaskList();
                                    }
                                }
                        )
                        .setNegativeButton("Batal",null)
                        .create();
                dialog.show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void deleteTask(View view){ View parent = (View)view.getParent();

        final TextView taskTextView = (TextView)parent.findViewById(R.id.task_title);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Hapus Tugas")
                .setMessage("Apa kamu ingin menghapus tugas ini ?")
                .setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                            @Override public void onClick(DialogInterface dialog, int which) {
                                Log.e("String", (String) taskTextView.getText());
                                String task = String.valueOf(taskTextView.getText());
                                dbHelper.deleteTask(task);
                                loadTaskList();
                            }
                        }
                )
                .setNegativeButton("Batal",null)
                .create();
        dialog.show();




    }
}