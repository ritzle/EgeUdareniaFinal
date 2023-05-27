package com.example.egeudareniafinal.Stats.Simple;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.egeudareniafinal.Finish.FinishAdapter;
import com.example.egeudareniafinal.Finish.FinishItem;
import com.example.egeudareniafinal.R;
import com.example.egeudareniafinal.Stats.Note;
import com.example.egeudareniafinal.Stats.StatsFragment;
import com.example.egeudareniafinal.StatsDatabase;
import com.example.egeudareniafinal.databinding.SimpleStatsFragmentBinding;

import java.util.ArrayList;
import java.util.List;

public class SimpleStatsFragment extends Fragment {

    private SimpleStatsFragmentBinding binding;
    private int count;
    /*private SimpleItemStatsAdapter simpleItemStatsAdapter = new SimpleItemStatsAdapter(new ArrayList<>());*/
    private StatsDatabase statsDatabase;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private ItemStatsSimpleAdapter adapter = new ItemStatsSimpleAdapter(new ArrayList<>());


    private int num = 0;


    public SimpleStatsFragment(String count)
    {
        this.count = Integer.parseInt(count);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = SimpleStatsFragmentBinding.inflate(inflater,container,false);


        binding.recyclerStaticsSimple.setAdapter(adapter);

        statsDatabase = new StatsDatabase(getActivity());

        sharedPreferences = getActivity().getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();


        String text = statsDatabase.getWrongWordsString(count - 1);

        if (text.length() != 0){
            List<String> list = new ArrayList<>();

            for (String str : text.split(", ")) {
                list.add(str);}

            for (int i = 0; i < list.size(); i++) {
                addEl(list.get(i),  "");

            }
        }


        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.text.setText("Попытка: " + count + "\nНеправильные слова");

        num = sharedPreferences.getInt("AnswersCount", 0);

//        binding.wrongWords.setText(statsDatabase.getWrongWordsString(count - 1));
//
//        for (int i = 0; i < 50; i++) {
//            Log.e("word1", statsDatabase.getWrongWordsString(i));
//        }




        binding.backBtn.setOnClickListener(v->{

            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.rootContainer, new StatsFragment());
            fragmentTransaction.commit();

        });

    }
    private void addEl(String title, String descripteon) {
        NoteSimple note = new NoteSimple(title, descripteon);
        adapter.addItem(note);
    }
}
