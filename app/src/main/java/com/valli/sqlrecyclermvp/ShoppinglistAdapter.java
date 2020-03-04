package com.valli.sqlrecyclermvp;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ShoppinglistAdapter extends RecyclerView.Adapter<ShoppinglistAdapter.ShoppinglistViewHolder> {

    private Context mContext;
    private Cursor mCursor;

    public ShoppinglistAdapter(Context context, Cursor cursor){
        mContext = context;
        mCursor = cursor;
    }
    public class ShoppinglistViewHolder extends RecyclerView.ViewHolder{

        public TextView nameTextItem;
        public TextView valueTextItem;

        public ShoppinglistViewHolder(@NonNull View itemView) {
            super(itemView);

            nameTextItem = itemView.findViewById(R.id.name_item);
            valueTextItem = itemView.findViewById(R.id.value_item);
        }
    }

    @NonNull
    @Override
    public ShoppinglistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.shopping_list_item, parent, false);

        return new ShoppinglistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShoppinglistViewHolder holder, int position) {
        if (!mCursor.moveToPosition(position)){
            return;
        }

        String name = mCursor.getString(mCursor.getColumnIndex(Shoppinglist_entity.Shoppinglist_entries.COLUMN_NAME));
        int value = mCursor.getInt(mCursor.getColumnIndex(Shoppinglist_entity.Shoppinglist_entries.COLUMN_VALUE));
        long id = mCursor.getLong(mCursor.getColumnIndex(Shoppinglist_entity.Shoppinglist_entries._ID));

        holder.nameTextItem.setText(name);
        holder.valueTextItem.setText(String.valueOf(value));
        holder.itemView.setTag(id);
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public void swapCursor(Cursor newCursor){
        if (mCursor!= null){
            mCursor.close();
        }
        mCursor = newCursor;
        if (newCursor!= null){
            notifyDataSetChanged();
        }
    }
}
