package com.example.egeudareniafinal;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.egeudareniafinal.databinding.GameFragmentBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;



public class GameFragment extends Fragment {

    private GameFragmentBinding binding;
    public static ArrayList<WordsSingleArray> wordsList = Words.newArray();;
    private WordsSingleArray wordPair;
    private boolean threadFlag = false; //Когда поток запущен - true, иначе - false
    public static int question = 1;
    public static int correctAnswer;
    public static List<FinishItem> FinishItems = new ArrayList<>();
    private WordDatabaseHelper databaseHelper;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = GameFragmentBinding.inflate(inflater, container, false);

        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (wordsList.size() <3)
            wordsList = Words.newArray();

        binding.question.setText("Вопрос " + question + " из 10");

        databaseHelper = new WordDatabaseHelper(getContext());

        Random rand = new Random();
         //Получает ArrayList со всеми парами слов


        int randomIndex = rand.nextInt(wordsList.size());
        wordPair = wordsList.get(randomIndex); //Берет рандомную пару слов по рандомному индексу ArrayList`а



        //Рандомно устанавливает слова в кнопках
        Random wordRand = new Random();

        if (wordRand.nextInt(2) == 0) {
            binding.firstWord.setText(wordPair.correctWord);
            binding.secondWord.setText(wordPair.wrongWord);
        } else {
            binding.firstWord.setText(wordPair.wrongWord);
            binding.secondWord.setText(wordPair.correctWord);
        }
        //--------------------------------------


        binding.firstWord.setOnClickListener(v -> {

            if (binding.firstWord.getText().equals(wordPair.getCorrectWord())) //Выбран правильный вариант ответа
            {

                if(!threadFlag)
                {
                    binding.firstWord.setBackgroundColor(getResources().getColor(R.color.GoodGreen));
                    correctAnswer++;
                    FinishItems.add(new FinishItem(R.drawable.correct, wordPair.getCorrectWord(), wordPair.getCorrectWord() + ", а не " + wordPair.getWrongWord(), true));


                    if(!databaseHelper.hasCorrectWord(wordPair.getCorrectWord()))
                    {
                        databaseHelper.addWord(wordPair.getCorrectWord(), wordPair.getWrongWord(), 1, 0);
                        databaseHelper.close();
                    }
                    else
                    {
                        databaseHelper.updateCorrectDigitByCorrectWord(wordPair.getCorrectWord(), databaseHelper.getCorrectDigitByCorrectWord(wordPair.getCorrectWord()) + 1);
                        databaseHelper.close();
                    }

                }


            }
            else //Выбран не правильный вариант ответа
            {
                if(!threadFlag)
                {
                    binding.secondWord.setBackgroundColor(getResources().getColor(R.color.GoodGreen));
                    binding.firstWord.setBackgroundColor(getResources().getColor(R.color.GoodRed));
                    FinishItems.add(new FinishItem(R.drawable.wrong, wordPair.getCorrectWord(), wordPair.getCorrectWord() + ", а не " + wordPair.getWrongWord(), false));

                    if(!databaseHelper.hasCorrectWord(wordPair.getCorrectWord()))
                    {
                        databaseHelper.addWord(wordPair.getCorrectWord(), wordPair.getWrongWord(), 0, 1);
                        databaseHelper.close();
                    }
                    else
                    {
                        databaseHelper.updateWrongDigitByWrongWord(wordPair.getWrongWord(), databaseHelper.getWrongDigitByWrongWord(wordPair.getWrongWord()) + 1);
                        databaseHelper.close();
                    }

                }
            }

            if(!threadFlag)
            {
                threadFlag = true;

                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run()
                    {

                        if(question == 10)
                        {
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.rootContainer, new FinishFragment());
                            fragmentTransaction.commit();
                        }

                        else
                        {
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.rootContainer, new GameFragment());
                            fragmentTransaction.commit();
                            threadFlag = false;
                            question++;
                            wordsList.remove(randomIndex);
                        }

                    }
                }, 800);
            }


        });

        binding.secondWord.setOnClickListener(v -> {

            if (binding.secondWord.getText().equals(wordPair.correctWord)) //Выбран правильный вариант ответа
            {


                if(!threadFlag)
                {
                    binding.secondWord.setBackgroundColor(getResources().getColor(R.color.GoodGreen));
                    correctAnswer++;
                    FinishItems.add(new FinishItem(R.drawable.correct, wordPair.getCorrectWord(), wordPair.getCorrectWord() + ", а не " + wordPair.getWrongWord(), true));


                    if(!databaseHelper.hasCorrectWord(wordPair.getCorrectWord()))
                    {
                        databaseHelper.addWord(wordPair.getCorrectWord(), wordPair.getWrongWord(), 1, 0);
                        databaseHelper.close();
                    }
                    else
                    {
                        databaseHelper.updateCorrectDigitByCorrectWord(wordPair.getCorrectWord(), databaseHelper.getCorrectDigitByCorrectWord(wordPair.getCorrectWord()) + 1);
                        databaseHelper.close();
                    }

                }

            }
            else //Выбран не правильный вариант ответа
            {

                if(!threadFlag)
                {
                    binding.firstWord.setBackgroundColor(getResources().getColor(R.color.GoodGreen));
                    binding.secondWord.setBackgroundColor(getResources().getColor(R.color.GoodRed));
                    FinishItems.add(new FinishItem(R.drawable.wrong, wordPair.getCorrectWord(), wordPair.getCorrectWord() + ", а не " + wordPair.getWrongWord(), false));

                    if(!databaseHelper.hasCorrectWord(wordPair.getCorrectWord()))
                    {
                        databaseHelper.addWord(wordPair.getCorrectWord(), wordPair.getWrongWord(), 0, 1);
                        databaseHelper.close();
                    }
                    else
                    {
                        databaseHelper.updateWrongDigitByWrongWord(wordPair.getWrongWord(), databaseHelper.getWrongDigitByWrongWord(wordPair.getWrongWord()) + 1);
                        databaseHelper.close();
                    }

                }

            }


            if(!threadFlag)
            {
                threadFlag = true;
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run()
                    {

                        if(question == 10)
                        {
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.rootContainer, new FinishFragment());
                            fragmentTransaction.commit();
                        }
                        else
                        {
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.rootContainer, new GameFragment());
                            fragmentTransaction.commit();
                            threadFlag = false;
                            question++;
                            wordsList.remove(randomIndex);
                        }


                    }
                }, 800);
            }
        });

    }
}