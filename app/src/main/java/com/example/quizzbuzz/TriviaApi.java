package com.example.quizzbuzz;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TriviaApi { // this is a Retrofit interface; Purpose: Defines the HTTP request to Open Trivia DB.

    @GET("api.php")
    Call<Result> getQuestions( //Call<Result> → Retrofit returns a Call object.
//                    Result is your model class that maps the JSON response from the API.
//                    You don’t get the result immediately.
//                    Instead, you enqueue the call, and Retrofit gives you the response asynchronously.
//https://opentdb.com/api.php?amount=10&category=21&difficulty=medium&type=multiple
            @Query("amount") int amount,
            @Query("category") int category,
            @Query("difficulty") String difficulty,
            @Query("type") String type
//          @Query tells Retrofit to append these as URL query parameters.

    );
}
