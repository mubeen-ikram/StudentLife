package com.tnc.studentlife.Interfaces;
public interface ItemTouchHelperAdapter {

    void onItemMove(int fromPosition, int toPosition);

    void itemDrag(int fromIndex,int toIndex);

    void onItemSwiped(int position,int direction);

}
