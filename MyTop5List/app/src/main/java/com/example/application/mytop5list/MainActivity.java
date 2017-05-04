package com.example.application.mytop5list;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    GoogleTranslate translator;
    Button tlButton;
    TextView translatabletext;
    Button prevButton;
    Button nextButton;
    ImageView petView;
    TextView descriptionView;
    TextView topFiveIndexNumberView;
    private int index;
    ArrayList<String> topFiveList;
    int indic = 0;
    ArrayList<String> topFiveDescriptionList;
    Integer[] imageIds = { R.drawable.africangray ,R.drawable.germanshepherd, R.drawable.hamster, R.drawable.rabbit, R.drawable.vancat};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(savedInstanceState!=null)
        {
            index=savedInstanceState.getInt("index");

        }
        else
        {
            index=0;
        }
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        topFiveList =new ArrayList<String>();
        topFiveList.add("africangray");
        topFiveList.add("germanshepherd");
        topFiveList.add("hamster");
        topFiveList.add("rabbit");
        topFiveList.add("vancat");

        topFiveDescriptionList =new ArrayList<String>();
        topFiveDescriptionList.add("Description:\nThe African grey parrot is a medium-sized, predominantly grey, black-billed parrot which weighs 400 g, with a length of 33 cm and an average wingspan of 46–52 cm. The adults weigh between 418 and 526 grams");
        topFiveDescriptionList.add("Description:\nGerman Shepherds are medium to large-sized dogs. The breed standard height at the withers is 60–65 cm for males and 55–60 cm for females. The weight standard is 30–40 kilograms for males and 22–32 kilograms for females");
        topFiveDescriptionList.add("Description:\nHamsters are rodents belonging to the subfamily Cricetinae. The subfamily contains about 25 species. They have become established as popular small house pets, and partly because they are easy to breed in captivity");
        topFiveDescriptionList.add("Description:\nRabbits are small mammals in the family Leporidae of the order Lagomorpha, found in several parts of the world. The male is called a buck and the female is a doe; a young rabbit is a kitten or kit.");
        topFiveDescriptionList.add("Description:\nThe Van cat is a distinctive landrace of domestic cat, found in the Lake Van region of eastern Turkey. It has blue or amber eyes or is odd-eyed. The variety has been referred to as the swimming cat, and observed to swim in Lake Van");

        prevButton=(Button) findViewById(R.id.btnPrev);
        nextButton=(Button) findViewById(R.id.btnNext);
        petView= (ImageView) findViewById(R.id.imgvwTop5);
        Button tlButton = (Button) findViewById(R.id.tlButton);
        petView.setImageResource(imageIds[index]);
        descriptionView =(TextView)findViewById(R.id.txtvwDescription);
        descriptionView.setText(topFiveDescriptionList.get(index));
        topFiveIndexNumberView = (TextView)findViewById(R.id.txtvwTop5);
        topFiveIndexNumberView.setText("Top 5 : \nNumber " + (5-index));

        if(index>0&& index<5)
        {
            nextButton.setVisibility(View.VISIBLE);
            prevButton.setVisibility(View.VISIBLE);
        }
        if(index==4)
        {
            nextButton.setVisibility(View.INVISIBLE);
        }
        if(index==0)
        {
            prevButton.setVisibility(View.INVISIBLE);
        }

    }

    // invoked when the activity may be temporarily destroyed, save the instance state here
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("index", index);
    }

    public void btnNextClick(View view) {
        if(index<4)
        {
            index++;
            Resources res = getResources();
            int resID = res.getIdentifier(topFiveList.get(index) , "drawable", getPackageName());
            petView.setImageResource(resID);
            descriptionView.setText(topFiveDescriptionList.get(index));
            prevButton.setVisibility(View.VISIBLE);
        }
        if(index==4)
        {
            nextButton.setVisibility(View.INVISIBLE);
        }
        topFiveIndexNumberView.setText("Top 5 : \nNumber " + (5-index));
        //Toast.makeText(this, "index"+index,Toast.LENGTH_SHORT ).show();
    }

    public void btnPreviousClick(View view) {
        if(index>0)
        {
            index--;
            Resources res = getResources();
            int resID = res.getIdentifier(topFiveList.get(index) , "drawable", getPackageName());
            petView.setImageResource(resID);
            descriptionView.setText(topFiveDescriptionList.get(index));
            nextButton.setVisibility(View.VISIBLE);
        }
        if(index==0)
        {
            prevButton.setVisibility(View.INVISIBLE);
        }
        topFiveIndexNumberView.setText("Top 5 : \nNumber " + (5-index));
        //Toast.makeText(this, "index"+ index,Toast.LENGTH_SHORT ).show();
    }

    public void translate(View view) {
        if (indic == 0) {
            this.translatedSp();
        }
        if (indic == 1) {
            this.translatedEn();
        }
    }

    private class translate extends AsyncTask<Void, Void, Void> {
        private ProgressDialog progress = null;

        protected void onError(Exception ex) {

        }
        @Override
        protected Void doInBackground(Void... params) {

            try {
                translator = new GoogleTranslate("AIzaSyBVfqNRsAhgDobQYtvVGXa7QbD9ahkBaDg");

                Thread.sleep(1000);


            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;

        }
        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected void onPreExecute() {
            //start the progress dialog
            progress = ProgressDialog.show(MainActivity.this, null, "Translating...");
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress.setIndeterminate(true);
            super.onPreExecute();
        }
        @Override
        protected void onPostExecute(Void result) {
            progress.dismiss();

            super.onPostExecute(result);
            if (indic == 0) {
                indic++;
                translatedSp();
            }
            else if (indic == 1) {
                indic--;
                translatedEn();
            }

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

    }


    public void translatedSp(){

        String translateToSpanish = descriptionView.getText().toString();//get the value of text
        System.out.println(translateToSpanish);
        String text = translator.translate(translateToSpanish, "en", "es");
        //translatabletext = (TextView) findViewById(R.id.);
        descriptionView.setText(text);
        translateToSpanish = topFiveIndexNumberView.getText().toString();
        text = translator.translate(translateToSpanish, "es","en");
        topFiveIndexNumberView.setText(text);

    }

    public void translatedEn(){

        String translateToEnglish = descriptionView.getText().toString();//get the value of text
        String text = translator.translate(translateToEnglish, "es", "en");
        //translatabletext = (TextView) findViewById(R.id.);
        descriptionView.setText(text);
        translateToEnglish = topFiveIndexNumberView.getText().toString();
        text = translator.translate(translateToEnglish, "es","en");
        topFiveIndexNumberView.setText(text);;
    }
}
