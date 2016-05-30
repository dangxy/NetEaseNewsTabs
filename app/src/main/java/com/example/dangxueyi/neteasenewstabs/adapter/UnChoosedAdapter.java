package com.example.dangxueyi.neteasenewstabs.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dangxueyi.neteasenewstabs.R;

import java.util.ArrayList;

/**
 * Created by dangxueyi on 16/5/30.
 */
public class UnChoosedAdapter extends RecyclerView.Adapter<UnChoosedAdapter.ChoosedViewHolder> {


    private Context context;

    private ArrayList<String> arrayList;

    private LayoutInflater layoutInflater;
    private  OnClickMoveItemListener mOnClickMoveItemListener;



    public  interface OnClickMoveItemListener{

        void  moveItemListener(final View view , final int position);
    }

    public  void setonClickMoveItemListener(OnClickMoveItemListener OnClickMoveItemListener){
        this.mOnClickMoveItemListener=OnClickMoveItemListener;
    }


    public UnChoosedAdapter(Context context, ArrayList<String> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public ChoosedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        return new ChoosedViewHolder(layoutInflater.inflate(R.layout.item_layout, null, false));
    }

    @Override
    public void onBindViewHolder(final ChoosedViewHolder holder, final int position) {

        holder.textView.setText(arrayList.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getLayoutPosition();
                mOnClickMoveItemListener.moveItemListener(v,position);
            }
        });

    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class ChoosedViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;

        public ChoosedViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv_tabs_content);
        }


    }
}
