package stoil.loki.interim;

import android.content.Context;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CustomLayoutManager extends LinearLayoutManager {

    private static final int TYPE_FIRST_ITEM = 0;
    private static final int TYPE_SECOND_ITEM = 1;
    private static final int TYPE_OTHER_ITEM = 2;

    private int mFirstItemHeight;
    private int mSecondItemHeight;

    private int[] mItemHeightArray = new int[getItemCount()];

    public CustomLayoutManager(Context context) {
        super(context);
        mFirstItemHeight = context.getResources().getDimensionPixelSize(R.dimen.first_item_height);
        mSecondItemHeight = context.getResources().getDimensionPixelSize(R.dimen.second_item_height);
        int itemCount = getItemCount();
        mItemHeightArray = new int[itemCount];
        for (int i = 0; i < itemCount; i++) {
            mItemHeightArray[i] = context.getResources().getDimensionPixelSize(R.dimen.first_item_height);
        }
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(
                RecyclerView.LayoutParams.MATCH_PARENT,
                RecyclerView.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state, int widthSpec, int heightSpec) {
        super.onMeasure(recycler, state, widthSpec, heightSpec);

        int width = View.MeasureSpec.getSize(widthSpec);
        int height = View.MeasureSpec.getSize(heightSpec);

        int top = 0;
        for (int i = 0; i < getItemCount(); i++) {
            View child = recycler.getViewForPosition(i);
            int type = getItemViewType(i);

            int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(
                    type == TYPE_FIRST_ITEM ? mFirstItemHeight : type == TYPE_SECOND_ITEM ? mSecondItemHeight : height / 3,
                    View.MeasureSpec.EXACTLY);
            int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);

            measureChildWithMargins(child, widthMeasureSpec, heightMeasureSpec);

            mItemHeightArray[i] = getDecoratedMeasuredHeight(child) + (type == TYPE_FIRST_ITEM ? height / 6 : type == TYPE_SECOND_ITEM ? height / 4 : height / 8);
        }
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        int top = 0;
        for (int i = 0; i < getItemCount(); i++) {
            View child = recycler.getViewForPosition(i);

            addView(child);
            layoutDecoratedWithMargins(child, 0, top, getDecoratedMeasuredWidth(child), top + mItemHeightArray[i]);

            top += mItemHeightArray[i];
        }
    }



    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_FIRST_ITEM;
        } else if (position == 1) {
            return TYPE_SECOND_ITEM;
        } else {
            return TYPE_OTHER_ITEM;
        }
    }
}
