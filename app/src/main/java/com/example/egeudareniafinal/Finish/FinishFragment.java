package com.example.egeudareniafinal.Finish;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.egeudareniafinal.GameFragment;
import com.example.egeudareniafinal.R;
import com.example.egeudareniafinal.StartFragment;
import com.example.egeudareniafinal.StatsDatabase;
import com.example.egeudareniafinal.WordDatabaseHelper;
import com.example.egeudareniafinal.databinding.FinishFragmentBinding;

import java.util.List;


public class FinishFragment extends Fragment {

    private List<FinishItem> mFinishItems;
    private RecyclerView mRecyclerView;

    private SharedPreferences sPref;


    private FinishAdapter mAdapter;
    private FinishFragmentBinding binding;
    private WordDatabaseHelper databaseHelper;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private int num;
    private StatsDatabase statsDatabase;


    private String SAVED_TEXT = "text";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment using binding
        binding = FinishFragmentBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();

        sharedPreferences = getActivity().getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
        num = sharedPreferences.getInt("AnswersCount", 0);
        editor = sharedPreferences.edit();

        statsDatabase = new StatsDatabase(getContext());

        // создание списка элементов
        mFinishItems = GameFragment.FinishItems;

        databaseHelper = new WordDatabaseHelper(getContext());

        num = num - 1;
        binding.scorePlayer.setText(GameFragment.correctAnswer + " из 10");


        if (GameFragment.correctAnswer < 5) {
            binding.gradePlayer.setText("Ваша отметка: 2");
        } else if (GameFragment.correctAnswer < 7) {
            binding.gradePlayer.setText("Ваша отметка: 3");
        } else if (GameFragment.correctAnswer < 9) {
            binding.gradePlayer.setText("Ваша отметка: 4");
        } else {
            binding.gradePlayer.setText("Ваша отметка: 5");
        }


        //статистика


        sPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();

        String text = sPref.getString(SAVED_TEXT, "");

        if (text.length() == 0) {

            ed.putString(SAVED_TEXT, String.valueOf(GameFragment.correctAnswer));
            ed.commit();
        }else {
            ed.putString(SAVED_TEXT, text + " " + String.valueOf(GameFragment.correctAnswer));
            ed.commit();

        }


        Log.e("E", "save");
        //------------------

        GameFragment.question = 1;
        GameFragment.correctAnswer = 0;


        // настройка RecyclerView
        mRecyclerView = binding.recycler;
        mAdapter = new FinishAdapter(mFinishItems);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.backBtn.setOnClickListener(v -> {
            mFinishItems.clear();


            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.rootContainer, new StartFragment());
            fragmentTransaction.commit();

        });


    }
}