package com.example.personalityinventory.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.personalityinventory.R;
import com.example.personalityinventory.model.Test;

import java.util.List;

public class TestListAdapter extends RecyclerView.Adapter<TestListAdapter.MyViewHolder>{

    private List<Test> testList;

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView textViewTestname,textViewDuration,textViewDescription,textViewDate;

        public MyViewHolder(View view){
            super(view);
            textViewTestname=(TextView)view.findViewById(R.id.textViewTestname);
            textViewDuration=(TextView)view.findViewById(R.id.textViewDuration);
            textViewDescription=(TextView)view.findViewById(R.id.textViewDescription);
            textViewDate=(TextView)view.findViewById(R.id.textViewDate);
        }
    }

    public TestListAdapter(List<Test> testList){
        this.testList=testList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.test_list_row,parent,false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder,int position){
        Test test=testList.get(position);
        holder.textViewTestname.setText(String.valueOf(test.getTestName()));
        holder.textViewDuration.setText(String.valueOf(test.getDuration()));
        holder.textViewDescription.setText(String.valueOf(test.getDescription()));
        holder.textViewDate.setText(String.valueOf(test.getDateAdded()));
    }

    @Override
    public int getItemCount(){
        return testList.size();
    }

    @Override
    public void  onAttachedToRecyclerView(RecyclerView recyclerView){
        super.onAttachedToRecyclerView(recyclerView);
    }
}
