package com.valli.sqlrecyclermvp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private SQLiteDatabase mDatabase;
    private ShoppinglistAdapter mAdapter;
    private EditText mEditText;
    private TextView mTextViewValue;
    private int mValue = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Shoppinglist_database dbHelper = new Shoppinglist_database(this);
        mDatabase = dbHelper.getWritableDatabase();

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ShoppinglistAdapter(this, getAllItems());
        recyclerView.setAdapter(mAdapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                removeItem((long) viewHolder.itemView.getTag());
            }
        }).attachToRecyclerView(recyclerView);

        mEditText = findViewById(R.id.edittext_name);
        mTextViewValue = findViewById(R.id.textview_value);

        Button buttonIncrease = findViewById(R.id.button_increase);
        Button buttonDecrease = findViewById(R.id.button_decrease);
        Button buttonAdd = findViewById(R.id.button_add);

        buttonIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increase();
            }
        });

        buttonDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decrease();
            }
        });

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem();
            }
        });
    }

    private void increase(){
        mValue++;
        mTextViewValue.setText(String.valueOf(mValue));
    }

    private void decrease(){
        if (mValue > 0){
            mValue--;
            mTextViewValue.setText(String.valueOf(mValue));
        }
    }

    private void addItem(){
        if (mEditText.getText().toString().trim().length() == 0 || mValue == 0){
            return;
        }

        String name = mEditText.getText().toString();
        ContentValues cv = new ContentValues();
        cv.put(Shoppinglist_entity.Shoppinglist_entries.COLUMN_NAME, name);
        cv.put(Shoppinglist_entity.Shoppinglist_entries.COLUMN_VALUE, mValue);

        mDatabase.insert(Shoppinglist_entity.Shoppinglist_entries.TABLE_NAME, null, cv);
        mAdapter.swapCursor(getAllItems());
        mEditText.getText().clear();
    }

    private void removeItem(long id){
        mDatabase.delete(Shoppinglist_entity.Shoppinglist_entries.TABLE_NAME,
                Shoppinglist_entity.Shoppinglist_entries._ID + "=" + id,
                null);
        mAdapter.swapCursor(getAllItems());
    }

    private Cursor getAllItems(){
        return mDatabase.query(Shoppinglist_entity.Shoppinglist_entries.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                Shoppinglist_entity.Shoppinglist_entries.COLUMN_TIMESTAMP + " DESC"
        );
    }
}
