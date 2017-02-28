package com.example.arajamani.todoapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import static android.R.attr.name;

public class EditItemActivity extends AppCompatActivity {

    EditText etEditItem;
    int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        String item = getIntent().getStringExtra("item");
        position = getIntent().getIntExtra("list_position",0);
        etEditItem = (EditText) findViewById(R.id.etEditItem);
        etEditItem.setText(item);
        etEditItem.setSelection(etEditItem.getText().length());
    }

    public void onEditItem(View view) {
        Intent data = new Intent();
        data.putExtra("editedValue",etEditItem.getText().toString());
        data.putExtra("list_position",position);
        setResult(RESULT_OK,data);
        finish();
    }

}