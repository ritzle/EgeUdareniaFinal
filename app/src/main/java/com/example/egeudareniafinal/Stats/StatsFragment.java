package com.example.egeudareniafinal.Stats;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.egeudareniafinal.R;
import com.example.egeudareniafinal.StartFragment;

import com.example.egeudareniafinal.databinding.StatsFragmentBinding;

import java.util.ArrayList;
import java.util.List;


public class StatsFragment extends Fragment {

    private @NonNull StatsFragmentBinding binding;
    private String SAVED_TEXT = "text";
    private SharedPreferences sPref;


    private static int count = 0;


    private ItemStatsAdapter itemStatsAdapter = new ItemStatsAdapter(new ArrayList<>(), this::onClickNote);



    private int counter = 1;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = StatsFragmentBinding.inflate(inflater,container,false);
        binding.recyclerStatics.setAdapter(itemStatsAdapter);


        sPref = getActivity().getPreferences(Context.MODE_PRIVATE);


        String text = sPref.getString(SAVED_TEXT, "");

        if (text.length() != 0){
            List<Integer> list = new ArrayList<>();




            for (String number : text.split(" ")) {
                list.add(Integer.parseInt(number));}

            for (Integer el : list) {
                addEl(count++, el);}
        }






        return binding.getRoot();

    }




    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        if (sPref.getString(SAVED_TEXT,"") == null){
            Log.e("E", "не получмл данные");
        }else {
            String text = sPref.getString(SAVED_TEXT, "");


            List<Integer> list = new ArrayList<>();

            if (list.size() != 0) {
                for (String number : text.split(" ")) {
                    list.add(Integer.parseInt(number));
                }


                for (Integer el : list) {
                    addEl(count++, el);
                }
            }
        }





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
            ed.commit();


            itemStatsAdapter.dellitem();
            itemStatsAdapter.notifyDataSetChanged();

        });


    }

    private void addEl(int count, int pr) {
        Note note = new Note("Title-" + count, " " + pr +"/" + 10);
        itemStatsAdapter.additem(note);

    }

    private void onClickNote(Note note) {
        Log.e("E", "еще нету");
    }

}
