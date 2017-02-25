package com.codepath.simpletodo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private final int REQUEST_CODE = 20;

    ArrayList<String> items;
    ArrayAdapter<String> itemsAdaprter;
    ListView lvItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvItems = (ListView) findViewById(R.id.lvItems);
        readItems();
        itemsAdaprter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdaprter);
        setupListViewListener();
        setupEditItemClickListener();
    }

    public void onAddItem(View v) {
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        itemsAdaprter.add(itemText);
        etNewItem.setText("");
        writeItems();
    }

    private void setupEditItemClickListener() {
        lvItems.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapter, View item, int pos, long id) {
                        Intent launchIntent = new Intent(getApplicationContext(), EditItemActivity.class);
                        launchIntent.putExtra(EditItemActivity.EXTRA_ITEM_POS, pos);
                        launchIntent.putExtra(EditItemActivity.EXTRA_ITEM_TEXT, items.get(pos));
                        startActivityForResult(launchIntent, REQUEST_CODE);
                    }
                }

        );
    }

    private void setupListViewListener() {
        lvItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapter, View item, int pos, long id) {
                        items.remove(pos);
                        itemsAdaprter.notifyDataSetChanged();
                        writeItems();
                        return true;
                    }
                }

        );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            String editText = data.getExtras().getString(EditItemActivity.EXTRA_ITEM_TEXT);
            int editTextPos = data.getExtras().getInt(EditItemActivity.EXTRA_ITEM_POS, 0);
            items.set(editTextPos, editText);
            itemsAdaprter.notifyDataSetChanged();
            writeItems();
        }
    }

    private void readItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            items = new ArrayList<String>(FileUtils.readLines(todoFile));
        } catch (IOException e) {
            items = new ArrayList<String>();
        }
    }

    private void writeItems() {
        File filesDir = getFilesDir();
        File todoFile = new File (filesDir, "todo.txt");
        try {
            FileUtils.writeLines(todoFile, items);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
