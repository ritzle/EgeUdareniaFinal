package com.example.egeudareniafinal;

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

import com.example.egeudareniafinal.Stats.Note;
import com.example.egeudareniafinal.Stats.StatsFragment;
import com.example.egeudareniafinal.databinding.SimpleStatsFragmentBinding;

import java.util.ArrayList;

public class SimpleStatsFragment extends Fragment {

    private SimpleStatsFragmentBinding binding;
    private int count;
    /*private SimpleItemStatsAdapter simpleItemStatsAdapter = new SimpleItemStatsAdapter(new ArrayList<>());*/
    private StatsDatabase statsDatabase;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private int num = 0;


    public SimpleStatsFragment(String count)
    {
        this.count = Integer.parseInt(count);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = SimpleStatsFragmentBinding.inflate(inflater,container,false);

        statsDatabase = new StatsDatabase(getActivity());

        sharedPreferences = getActivity().getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.text.setText("Попытка: " + count + "\nНеправильные слова");

        num = sharedPreferences.getInt("AnswersCount", 0);

        binding.wrongWords.setText(statsDatabase.getWrongWordsString(count - 1));

        for (int i = 0; i < 50; i++) {
            Log.e("word1", statsDatabase.getWrongWordsString(i));
        }




        binding.backBtn.setOnClickListener(v->{

            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.rootContainer, new StatsFragment());
            fragmentTransaction.commit();

        });






    }
}
