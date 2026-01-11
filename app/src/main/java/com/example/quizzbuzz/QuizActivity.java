package com.example.quizzbuzz;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
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
    private int categoryId;

    private ProgressBar progressBar;
    private TextView currentQuestionNo;
    private TextView question;
    private RadioGroup optionsGrp;
    private RadioButton[] options;
    private Button nextBtn;
    List<Question> questions;
    private int count;
    int score = 0;


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

        LinearLayout mainLayout = findViewById(R.id.main);
        mainLayout.setVisibility(View.GONE);
        TextView loadingText = findViewById(R.id.loadingText);
        loadingText.setVisibility(View.VISIBLE);

        String category = getIntent().getStringExtra("category");
        String difficulty = getIntent().getStringExtra("difficulty");

        progressBar = findViewById(R.id.progressBar);

        question = findViewById(R.id.question);
        currentQuestionNo = findViewById(R.id.currentQuestion);

        nextBtn = findViewById(R.id.nextBtn);

        nextBtn.setEnabled(false);
        nextBtn.setText("Next");

        optionsGrp = findViewById(R.id.optionsGrp);
        options = new RadioButton[]{
                findViewById(R.id.option1), findViewById(R.id.option2),
                findViewById(R.id.option3), findViewById(R.id.option4)
        };
        optionsGrp.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId != -1) {
                nextBtn.setEnabled(true);
            }
        });

        if (category != null) {
            switch (category) {
                case "General Knowledge":
                    categoryId = 9;
                    break;
                case "Science":
                    categoryId = 17;
                    break;
                case "Technology":
                    categoryId = 18;
                    break;
                case "Geography":
                    categoryId = 22;
                    break;
                case "Sports":
                    categoryId = 21;
                    break;
                default:
                    categoryId = 26;
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
                    loadingText.setVisibility(View.GONE);
                    mainLayout.setVisibility(View.VISIBLE);

                    count = 0;
                    displayQuestion();
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Log.e("onFailure: ", t.getMessage());
            }


        });

        nextBtn.setOnClickListener(v -> {

            int selectedId = optionsGrp.getCheckedRadioButtonId();
            if (selectedId == -1) {
                showToast("Please select an answer!");
                return;
            }

            RadioButton selectedOption = findViewById(selectedId);

            answer(selectedOption);
            count++;
            nextBtn.postDelayed(()->{
                if (count < questions.size()) {
                    displayQuestion();
                    if (count == questions.size() - 1) {
                        nextBtn.setText("Submit");
                    }
                } else {
                    Intent i = new Intent(QuizActivity.this, ResultActivity.class);
                    i.putExtra("score", String.valueOf(score));
                    startActivity(i);
                    finish();
                }
            },2000);
        });
    }
    private void displayQuestion() {
        resetOptions();
        optionsGrp.clearCheck();

        Question q = questions.get(count);

        question.setText(Html.fromHtml(q.getQuestion(),Html.FROM_HTML_MODE_LEGACY));
        currentQuestionNo.setText(String.valueOf(count + 1));
        progressBar.setMax(questions.size());
        progressBar.setProgress(count + 1);

        List<String> answers = q.getAllAnswers();
        Collections.shuffle(answers);

        for (int i = 0; i < 4; i++) {
            options[i].setText(Html.fromHtml(answers.get(i)));
        }
    }
    private void resetOptions() {
        for (RadioButton option : options) {
            option.setBackgroundResource(R.drawable.option_bg);
            option.setEnabled(true);
        }
    }
    private void answer(RadioButton selectedOption) {
        String userAnswer = Html.fromHtml(selectedOption.getText().toString(), Html.FROM_HTML_MODE_LEGACY).toString().trim();

        Question currentQuestion = questions.get(count);
        String correctAnswer = Html.fromHtml(currentQuestion.getCorrect_answer(), Html.FROM_HTML_MODE_LEGACY).toString().trim();
        ;

        for (RadioButton option : options) {
            option.setEnabled(false);
        }

        if (userAnswer.equals(correctAnswer)) {
            selectedOption.setBackgroundResource(R.drawable.correct_option_bg);
            score++;
            return;
        }

        selectedOption.setBackgroundResource(R.drawable.wrong_option_bg);

        for (RadioButton option : options) {
            if (option.getText().toString().equals(correctAnswer)) {
                option.setBackgroundResource(R.drawable.correct_option_bg);
                break;
            }
        }
    }
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}