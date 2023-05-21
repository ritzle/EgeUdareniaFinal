package com.example.egeudareniafinal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.egeudareniafinal.databinding.FinishFragmentBinding;

import java.util.ArrayList;
import java.util.List;


public class FinishFragment extends Fragment {

    private List<FinishItem> mFinishItems;
    private RecyclerView mRecyclerView;

    private FinishAdapter mAdapter;
    private FinishFragmentBinding binding;
    private WordDatabaseHelper databaseHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment using binding
        binding = FinishFragmentBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();


        // создание списка элементов
        mFinishItems = GameFragment.FinishItems;

        databaseHelper = new WordDatabaseHelper(getContext());

        binding.scorePlayer.setText("Вы ответили верно на " + GameFragment.correctAnswer + " из 10");

        if (GameFragment.correctAnswer < 5)
        {
            binding.gradePlayer.setText("Ваша отметка: 2");
        }
        else if(GameFragment.correctAnswer < 7)
        {
            binding.gradePlayer.setText("Ваша отметка: 3");
        }
        else if(GameFragment.correctAnswer < 9)
        {
            binding.gradePlayer.setText("Ваша отметка: 4");
        }
        else
        {
            binding.gradePlayer.setText("Ваша отметка: 5");
        }

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