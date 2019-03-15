package com.example.suqi.widgetdemo;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author suqi
 * @time 2018/12/19
 * @description
 */
public class CoordinatorActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator);
        recyclerView = findViewById(R.id.recyclerview);
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add(String.valueOf(i));
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MyAdapter(this, list));
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.ItemView> {

        private List<String> list;
        private Context context;

        public MyAdapter(Context context, List<String> list) {
            this.context = context;
            this.list = list;
        }

        @NonNull
        @Override
        public ItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view =  LayoutInflater.from(context).inflate(R.layout.item, parent, false);
            return new ItemView(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ItemView holder, int position) {
            holder.textView.setText(list.get(position));
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ItemView extends RecyclerView.ViewHolder {
            TextView textView;
            public ItemView(View itemView) {
                super(itemView);
                textView = itemView.findViewById(R.id.text2);
            }
        }
    }
}
