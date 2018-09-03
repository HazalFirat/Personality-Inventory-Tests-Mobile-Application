package com.example.personalityinventory.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.personalityinventory.R;
import com.example.personalityinventory.model.Question;

import java.util.ArrayList;
import java.util.List;

public class QuestionsAdapter extends BaseAdapter {
    Context context;
    List<Question> questionsList = new ArrayList<>();
    LayoutInflater inflater;
    public static ArrayList<String> selectedAnswers;

    public QuestionsAdapter(Context context, List<Question> questionsList) {
        this.context = context;
        this.questionsList = questionsList;

        selectedAnswers = new ArrayList<>();
        for (int i = 0; i < questionsList.size(); i++) {
            selectedAnswers.add("3");
        }
        inflater = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return questionsList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.questions_list_row, null);

        TextView textViewQuestion = (TextView) view.findViewById(R.id.textViewQuestion);
        SeekBar seekBar = (SeekBar) view.findViewById(R.id.seekBar);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                selectedAnswers.set(i, Integer.toString(progress+1));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        textViewQuestion.setText(questionsList.get(i).getQuestionContent());
        return view;
    }
}
