package com.codepath.simpletodo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {

    static public final String EXTRA_ITEM_POS = "codepath.item_pos";
    static public final String EXTRA_ITEM_TEXT = "codepath.item_text";
    private String editItemText;
    private int editItemPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        this.editItemText = getIntent().getExtras().getString(EXTRA_ITEM_TEXT);
        this.editItemPos = getIntent().getExtras().getInt(EXTRA_ITEM_POS);
        initEditItem();
    }

    public void initEditItem() {
        EditText etEditItem = (EditText) findViewById(R.id.etEditItem);
        etEditItem.setText(this.editItemText);
        if(etEditItem.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    public void onSaveItem(View v) {
        EditText etEditItem = (EditText) findViewById(R.id.etEditItem);
        editItemText = etEditItem.getText().toString();
        Intent resultData = new Intent();
        resultData.putExtra(EXTRA_ITEM_TEXT, editItemText);
        resultData.putExtra(EXTRA_ITEM_POS, editItemPos);
        setResult(RESULT_OK, resultData);
        etEditItem.setText("");
        finish();
    }
}
