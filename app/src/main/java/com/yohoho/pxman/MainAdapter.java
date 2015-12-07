package com.yohoho.pxman;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

/**
 * author Zaaach on 2015/12/6.
 */
public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    private static final int ITEM_TYPE_TITLE = 123;
    private static final int ITEM_TYPE_DPI   = 321;
    private static final String[] DPI_NAMES = {"ldpi", "mdpi", "hdpi", "xhdpi", "xxhdpi", "xxxhdpi"};

    private Context mContext;
    private double mdpiValue = 1;
    private OnFocusedListener mOnFocusedListener;

    public MainAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void changeDataByMdpiValue(double value){
        this.mdpiValue = value;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == ITEM_TYPE_TITLE){
            view = LayoutInflater.from(mContext).inflate(R.layout.item_title, parent, false);
        }else if (viewType == ITEM_TYPE_DPI){
            view = LayoutInflater.from(mContext).inflate(R.layout.item_dpi, parent, false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if (position > 0){
            holder.dpiName.setText(DPI_NAMES[position - 1]);
            holder.pxValue.setText(double2String(mdpiValue, position));
            holder.dpValue.setText(double2String(mdpiValue, DpiType.MDPI));

            holder.pxValue.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (mOnFocusedListener != null){
                        mOnFocusedListener.onFocused(holder.pxValue, hasFocus, position, false);
                    }
                }
            });
            holder.dpValue.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (mOnFocusedListener != null){
                        mOnFocusedListener.onFocused(holder.dpValue, hasFocus, position, true);
                    }
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? ITEM_TYPE_TITLE : ITEM_TYPE_DPI;
    }

    @Override
    public int getItemCount() {
        return DPI_NAMES.length + 1;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView dpiName;
        EditText pxValue;
        EditText dpValue;

        public ViewHolder(View itemView) {
            super(itemView);
            dpiName = (TextView) itemView.findViewById(R.id.id_tv_dpi_name);
            pxValue = (EditText) itemView.findViewById(R.id.id_et_px_value);
            dpValue = (EditText) itemView.findViewById(R.id.id_et_dp_value);
        }
    }

    private String double2String(double d, int position){
        String result = String.valueOf(d);
        switch (position){
            case DpiType.LDPI:
                result = String.valueOf(d * 3/4);
                break;
            case DpiType.MDPI:
                break;
            case DpiType.HDPI:
                result = String.valueOf(d * 3/2);
                break;
            case DpiType.XHDPI:
                result = String.valueOf(d * 2);
                break;
            case DpiType.XXHDPI:
                result = String.valueOf(d * 3);
                break;
            case DpiType.XXXHDPI:
                result = String.valueOf(d * 4);
                break;
        }
        return result;
    }

    public void setOnFocusedListener(OnFocusedListener listener){
        this.mOnFocusedListener = listener;
    }

    public interface OnFocusedListener{
        void onFocused(EditText editText, boolean hasFocus, int dpiType, boolean isDp);
    }
}
