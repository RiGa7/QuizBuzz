package com.example.quizzbuzz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class CategoryActivity extends AppCompatActivity implements View.OnClickListener {
    private  int[] categoriesId;
    private final String[] categoriesName = {"General Knowledge", "Science", "Technology", "Sports", "Geography", "Random"};
    private ImageView homeIcon;
    private  int score;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_category);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        homeIcon = findViewById(R.id.home);

        LinearLayout[] categories = new LinearLayout[6];
        categoriesId = new int[]{R.id.gk, R.id.science, R.id.tech, R.id.sports, R.id.geography, R.id.random};

        for(int i=0; i<6; i++){
            categories[i] = findViewById(categoriesId[i]);
            categories[i].setOnClickListener(this);
        }

        Intent i = new Intent(CategoryActivity.this, MainActivity.class);
        homeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(i);
            }
        });
    }
    
    private void showDifficultyDialog(String category) {

        String[] difficulties = {"Easy", "Medium", "Hard", "Random"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pick Difficulty");
        builder.setItems(difficulties, ((dialog, which) -> {

            Toast.makeText(
                    this,
                    category + " - " + difficulties[which],
                    Toast.LENGTH_SHORT
            ).show();

            Intent quizIntent = new Intent(CategoryActivity.this, QuizActivity.class);
            quizIntent.putExtra("category", category);
            if (which==3){
//                 a number between 0 (inclusive) and 3 (exclusive)
                quizIntent.putExtra("difficulty",difficulties[(int) (new Random().nextInt(3))]);
            }else{
                quizIntent.putExtra("difficulty",difficulties[which]);
            }

            startActivity(quizIntent);

        }));
        builder.show();
    }

    @Override
    public void onClick(View v) {
        for (int i = 0; i < categoriesId.length; i++) {
            if (v.getId() == categoriesId[i]) {
                if(categoriesName[i].equals("Random")){
                    showDifficultyDialog(categoriesName[new Random().nextInt(5)]);
                }else{
                    showDifficultyDialog(categoriesName[i]);
                }
                break;
            }
        }
    }
}