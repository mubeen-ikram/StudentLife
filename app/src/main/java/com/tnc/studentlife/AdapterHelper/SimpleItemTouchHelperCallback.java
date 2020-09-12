package com.tnc.studentlife.AdapterHelper;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.ItemTouchHelper.Callback;
import androidx.recyclerview.widget.RecyclerView;

import com.tnc.studentlife.Interfaces.ItemTouchHelperAdapter;

import static android.content.ContentValues.TAG;

public class SimpleItemTouchHelperCallback  extends Callback {

    int dragFrom = -1;
    int dragTo = -1;

    private final ItemTouchHelperAdapter mAdapter;

    public SimpleItemTouchHelperCallback(
            ItemTouchHelperAdapter adapter) {
        mAdapter = adapter;
    }


    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {

        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
        return makeMovementFlags(dragFlags, swipeFlags);
    }
    private void reallyMoved(int from, int to) {
        Log.d(TAG, "reallyMoved: from index: "+from+ "  to Index: "+to);
        mAdapter.itemDrag(from,to);
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);

        if(dragFrom != -1 && dragTo != -1 && dragFrom != dragTo) {
            reallyMoved(dragFrom, dragTo);
        }

        dragFrom = dragTo = -1;
    }


    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        int fromPosition = viewHolder.getAdapterPosition();
        int toPosition = target.getAdapterPosition();


        if(dragFrom == -1) {
            dragFrom =  fromPosition;
        }
        dragTo = toPosition;

//        mAdapter.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        mAdapter.onItemSwiped(viewHolder.getAdapterPosition(),direction);
    }
}
