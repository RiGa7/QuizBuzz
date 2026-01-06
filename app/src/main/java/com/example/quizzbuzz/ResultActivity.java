package com.example.quizzbuzz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ResultActivity extends AppCompatActivity {
    TextView showScore, message;
    Button restart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_result);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


         showScore= findViewById(R.id.score);
         message = findViewById(R.id.msg);
         restart = findViewById(R.id.playAgainBtn);

         String score = getIntent().getStringExtra("score");

         showScore.setText(score);

        if (score == null) score = "0";

        showScore.setText(score);

        int finalScore = Integer.parseInt(score);

        if (finalScore == 10) {
            message.setText("🎉 Perfect! You're a genius!");
        } else if (finalScore >= 8) {
            message.setText("👍 Excellent work!");
        } else if (finalScore >= 6) {
            message.setText("😊 Good job!");
        } else if (finalScore >= 4) {
            message.setText("📚 Keep learning!");
        } else if (finalScore >= 2) {
            message.setText("💪 Don't give up! Try again!");
        } else {
            message.setText("😅 Tough round! But you’ve got this next time! 🚀");
        }


        restart.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 startActivity(new Intent(ResultActivity.this, CategoryActivity.class));
             }
         });
    }
}