package com.example.quizzbuzz;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuizActivity extends AppCompatActivity {
    private  int categoryId;

    private ProgressBar progressBar;
    private TextView currentQuestionNo;
    private TextView question;
    private RadioGroup optionsGrp;
    private RadioButton[] options;
    private Button nextBtn;
    List<Question> questions;
    private int count;
    int score =0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quiz);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        String category = getIntent().getStringExtra("category");
        String difficulty = getIntent().getStringExtra("difficulty");

        progressBar = findViewById(R.id.progressBar);

        question = findViewById(R.id.question);
        currentQuestionNo = findViewById(R.id.currentQuestion);

        nextBtn = findViewById(R.id.playAgainBtn);

        nextBtn.setEnabled(false);
        nextBtn.setText("Next");

        optionsGrp = findViewById(R.id.optionsGrp);
        options = new RadioButton[]{
                findViewById(R.id.option1), findViewById(R.id.option2),
                findViewById(R.id.option3), findViewById(R.id.option4)
        };

        optionsGrp.setOnCheckedChangeListener((group, checkedId) -> {
            nextBtn.setEnabled(true);
        });


        if (category != null) {
            switch (category){
                case "General Knowledge":
                    categoryId = 9;
                    break;
                case "Science":
                    categoryId= 17;
                    break;
                case "Technology":
                    categoryId= 18;
                    break;
                case "Geography":
                    categoryId= 22;
                    break;
                case "Sports":
                    categoryId=21;
                    break;
                default:
                    categoryId= 26;
                    break;
            }
        }

        TextView categoryDifficulty = findViewById(R.id.categoryDifficulty);
        categoryDifficulty.setText(String.format("%s | %s ", category, difficulty));

        TriviaApi api = ApiClient.getRetrofit().create(TriviaApi.class);

        assert difficulty != null;
        Call<Result> call = api.getQuestions(
                10,
                categoryId,
                difficulty.toLowerCase(),
                "multiple"
        );

        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {

                if (response.isSuccessful() && response.body() != null) {
                    questions = response.body().getResults();
                    if (questions == null || questions.isEmpty()) {
                        question.setText("No question is available for this selection");
                        nextBtn.setEnabled(false);
                        return;
                    }

                    count =0;
                    displayQuestion();
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                question.setText("QUIZ", TextView.BufferType.valueOf(t.getMessage()));
            }
        });

        nextBtn.setOnClickListener(v -> {

            int selectedId = optionsGrp.getCheckedRadioButtonId();
            if (selectedId == -1) {
                showToast("Please select an answer!");
                return;
            }

            RadioButton selectedOption = findViewById(selectedId);
            String userAnswer = selectedOption.getText().toString();

            Question currentQuestion = questions.get(count);
            String correctAnswer = currentQuestion.getCorrect_answer();

            if (userAnswer.equals(correctAnswer)) {
                score++;
            }

            count++;

            if (count < questions.size()) {
                displayQuestion();
                if (count == questions.size() - 1) {
                    nextBtn.setText("Submit");
                }
            } else {
                Intent i = new Intent(QuizActivity.this, ResultActivity.class);
                i.putExtra("score",String.valueOf(score));
                startActivity(i);
                finish();
            }
    });
    }

    private void displayQuestion() {
        optionsGrp.clearCheck();
        nextBtn.setEnabled(false);

        Question q = questions.get(count);

        question.setText(q.getQuestion());
        currentQuestionNo.setText(String.valueOf(count + 1));
        int currentProgress = progressBar.getProgress();
        progressBar.setProgress(currentProgress+1);

        List<String> answers = q.getAllAnswers();
        Collections.shuffle(answers);

        for (int i = 0; i < 4; i++) {
            options[i].setText(answers.get(i));
        }
    }
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}