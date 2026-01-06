package com.example.quizzbuzz;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

//    Retrofit → object that performs API calls
    private static Retrofit retrofit;
//    Why static?
//    You do NOT want to recreate Retrofit every time
//    Networking clients are expensive
//    Static ensures reuse
//    Think of it as: “One shared internet engine for the whole app.”
    public static Retrofit getRetrofit() {


        if (retrofit == null) { //This is called lazy initialization.
//            Meaning: Retrofit is created only when needed
//            First call → create it
//            Later calls → reuse it

//            Without this: Every call would create a new Retrofit object, Waste of memory & Slower app

            retrofit = new Retrofit.Builder() //Builder pattern = step-by-step configuration.
                    .baseUrl("https://opentdb.com/") //Must end with/ & all API calls are relative to this
                    .addConverterFactory(GsonConverterFactory.create()) //Takes raw JSON response & Converts it into Java objects (Result, Question)
                    .build(); //Finalizes configuration, creates the Retrofit object & stores it in the static variable
        }


        return retrofit; //returns: Newly created Retrofit (first time) OR existing Retrofit (every other time)
    }
}
