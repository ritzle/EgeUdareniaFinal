package com.example.egeudareniafinal;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
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

import com.example.egeudareniafinal.Finish.FinishFragment;
import com.example.egeudareniafinal.Finish.FinishItem;
import com.example.egeudareniafinal.databinding.GameFragmentBinding;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;



public class GameFragment extends Fragment {

    private GameFragmentBinding binding;
    public static ArrayList<WordsSingleArray> wordsList;
    private WordsSingleArray wordPair;
    private boolean threadFlag = false; //Когда поток запущен - true, иначе - false
    public static int question = 1;
    public static int correctAnswer;
    public static List<FinishItem> FinishItems = new ArrayList<>();
    private WordDatabaseHelper databaseHelper;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private int num = 0;

    private StatsDatabase statsDatabase;

    private DatabaseHelper mDBHelper;
    private static SQLiteDatabase mDb;






    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = GameFragmentBinding.inflate(inflater, container, false);

        super.onCreate(savedInstanceState);
        mDBHelper = new DatabaseHelper(getActivity());
        statsDatabase = new StatsDatabase(getActivity());

        try {
            mDBHelper.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }

        try {
            mDb = mDBHelper.getWritableDatabase();
        } catch (SQLException mSQLException) {
            throw mSQLException;
        }

        wordsList = getWordList();


        return binding.getRoot();



    }

    public ArrayList<WordsSingleArray>  getWordList(){

        ArrayList<WordsSingleArray> words = new ArrayList<>();

        Cursor cursor = mDb.rawQuery("SELECT correctWord, wrongWord FROM WordEge", null);
        int field1Index = cursor.getColumnIndexOrThrow("correctWord");
        int field2Index = cursor.getColumnIndexOrThrow("wrongWord");
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String field1Value = cursor.getString(field1Index);
            String field2Value = cursor.getString(field2Index);
            // использование значений полей
            words.add(new WordsSingleArray(field1Value, field2Value));
            cursor.moveToNext();
        }
        cursor.close();

        return words;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        if (wordsList.size() <3)
        {
            wordsList.clear();
            wordsList = getWordList();
        }

        sharedPreferences = getActivity().getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
        num = sharedPreferences.getInt("AnswersCount", 0);
        editor = sharedPreferences.edit();


        binding.question.setText("Вопрос " + question + " из 10");

        databaseHelper = new WordDatabaseHelper(getContext());

        Random rand = new Random();
         //Получает ArrayList со всеми парами слов


        int randomIndex = rand.nextInt(wordsList.size());
        wordPair = wordsList.get(randomIndex); //Берет рандомную пару слов по рандомному индексу ArrayList`а

        String p = "";
        for (int i = 0; i < wordsList.size(); i++) {
            String s =  wordsList.get(i).correctWord + " " + wordsList.get(i).wrongWord;
            p += s + "|";
        }
        Log.e("E",p + wordsList.size());


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
                    FinishItems.add(new FinishItem(R.drawable.wrong, wordPair.getWrongWord(), wordPair.getCorrectWord() + ", а не " + wordPair.getWrongWord(), false));

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

                    String wrongWordsString = statsDatabase.getWrongWordsString(num);
                    if (!wrongWordsString.isEmpty())
                    {
                        statsDatabase.addWordToWrongWords(num, wordPair.getWrongWord());
                    }
                    else
                    {
                        statsDatabase.addWrongWords(num, wordPair.getWrongWord());
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

                            num++;
                            editor.putInt("AnswersCount", num);
                            editor.apply();
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
                    FinishItems.add(new FinishItem(R.drawable.wrong, wordPair.getWrongWord(), wordPair.getCorrectWord() + ", а не " + wordPair.getWrongWord(), false));

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

                    String wrongWordsString = statsDatabase.getWrongWordsString(num);
                    if (!wrongWordsString.isEmpty())
                    {
                        statsDatabase.addWordToWrongWords(num, wordPair.getWrongWord());
                    }
                    else
                    {
                        statsDatabase.addWrongWords(num, wordPair.getWrongWord());
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

                            num++;
                            editor.putInt("AnswersCount", num);
                            editor.apply();
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