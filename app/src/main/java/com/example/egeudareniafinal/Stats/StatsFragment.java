package com.example.egeudareniafinal.Stats;

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

import com.example.egeudareniafinal.R;
import com.example.egeudareniafinal.StartFragment;

import com.example.egeudareniafinal.Stats.Simple.SimpleStatsFragment;
import com.example.egeudareniafinal.StatsDatabase;
import com.example.egeudareniafinal.databinding.StatsFragmentBinding;

import java.util.ArrayList;
import java.util.List;


public class StatsFragment extends Fragment {

    private @NonNull StatsFragmentBinding binding;
    private String SAVED_TEXT = "text";
    private SharedPreferences sPref;

    private StatsDatabase statsDatabase;


    private static int count = 0;


    private ItemStatsAdapter itemStatsAdapter = new ItemStatsAdapter(new ArrayList<>(), this::onClickNote);








    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = StatsFragmentBinding.inflate(inflater,container,false);
        binding.recyclerStatics.setAdapter(itemStatsAdapter);

        statsDatabase = new StatsDatabase(getContext());

        sPref = getActivity().getPreferences(Context.MODE_PRIVATE);


        String text = sPref.getString(SAVED_TEXT, "");

        if (text.length() != 0){
            List<Integer> list = new ArrayList<>();




            for (String number : text.split(" ")) {
                list.add(Integer.parseInt(number));}

            for (int i = 0; i < list.size(); i++) {
                addEl(i+1, list.get(i), i + 1);

            }
        }






        return binding.getRoot();

    }




    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        binding.backBtn.setOnClickListener(v->{

            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.rootContainer, new StartFragment());
            fragmentTransaction.commit();

        });

        binding.DelElement.setOnClickListener(v -> {
            sPref = getActivity().getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor ed = sPref.edit();
            ed.putString(SAVED_TEXT, "");
            statsDatabase.deleteAllData();
            ed.commit();

            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("AnswersCount", 0);
            editor.commit();


            itemStatsAdapter.dellitem();
            itemStatsAdapter.notifyDataSetChanged();
        });


    }

    private void addEl(int count, int pr, int addept) {
        Note note = new Note("Попытка: " + count, " " + pr + "/10","" + addept);
        itemStatsAdapter.additem(note);

    }


    // как Создание слушателя на каждый объект в ресайкл статистике
    private void onClickNote(Note note)
    {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.rootContainer, new SimpleStatsFragment(note.getAddept()));
        fragmentTransaction.commit();
    }

}
