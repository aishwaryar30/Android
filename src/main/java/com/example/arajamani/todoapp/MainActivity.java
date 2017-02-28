package com.example.arajamani.todoapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static android.R.attr.name;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> todoItems;
    ArrayAdapter<String> aToDoAdapter;
    ListView lvItems;
    EditText etEditText;
    private final int REQUEST_CODE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        populateArrayItems();
        lvItems = (ListView) findViewById(R.id.lvItems);
        lvItems.setAdapter(aToDoAdapter);
        etEditText = (EditText) findViewById(R.id.eteditText);
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(MainActivity.this,EditItemActivity.class);
                i.putExtra("item",lvItems.getItemAtPosition(position).toString());
                i.putExtra("list_position", position);
                startActivityForResult(i,REQUEST_CODE);
            }
        });
    }

    public void populateArrayItems(){
        readItems();
        aToDoAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,todoItems);
    }

    private void readItems(){
        File fileDir = getFilesDir();
        File file = new File(fileDir,"todo.txt");
        try{
            todoItems=new ArrayList<String>(FileUtils.readLines(file));
        }
        catch (IOException e){
            todoItems = new ArrayList<String>();
        }
    }

    private void writeItems(){
        File fileDir = getFilesDir();
        File file = new File(fileDir,"todo.txt");
        try{
            FileUtils.writeLines(file,todoItems);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }


    public void onAddItem(View view) {
        aToDoAdapter.add(etEditText.getText().toString());
        etEditText.setText("");
        writeItems();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // REQUEST_CODE is defined above
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            // Extract name value from result extras
            String editValue = data.getExtras().getString("editedValue");
            int pos = data.getExtras().getInt("list_position");
            todoItems.set(pos,editValue);
            aToDoAdapter.notifyDataSetChanged();
            writeItems();
        }

    }
}
