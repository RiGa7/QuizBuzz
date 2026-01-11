# QuizzBuzz

QuizzBuzz is an Android quiz application that fetches real-time multiple-choice questions from a public Trivia API and lets users test their knowledge across different categories and difficulty levels.

## Features
- Category-based quizzes (GK, Science, Technology, Sports, Geography, etc.)
- Difficulty selection (Easy / Medium / Hard)
- Real-time questions using Trivia API
- Multiple-choice questions with shuffled options
- Score calculation and result summary
- Clean and user-friendly UI
- No timer – focus on accuracy

## Tech Stack
- **Language:** Java  
- **IDE:** Android Studio  
- **Networking:** Retrofit  
- **API:** Open Trivia Database (OpenTDB)  
- **UI:** XML, Material Components  

## Project Structure
- `CategoryActivity` – Select quiz category
- `QuizActivity` – Displays questions and handles quiz flow
- `ResultActivity` – Shows final score and feedback
- `ApiClient` – Retrofit instance setup
- `TriviaApi` – API interface
- `Question / Result models` – API response handling

## App Flow
1. User selects category and difficulty
2. App fetches questions from Trivia API
3. Questions are displayed one by one
4. User selects answers and submits
5. Final score is calculated and shown

## 📸 Screenshots
<img width="272" height="600" alt="image" src="https://github.com/user-attachments/assets/5d812578-2121-42f8-9a87-df3e3f1fdbfc" />
<img width="268" height="600" alt="image" src="https://github.com/user-attachments/assets/e9593ba1-58ff-4277-9b63-12b2b9823be2" />
<img width="273" height="608" alt="image" src="https://github.com/user-attachments/assets/2d8e065e-ece4-4314-ac1f-ec5ef58b312c" />
<img width="273" height="599" alt="image" src="https://github.com/user-attachments/assets/1e575e42-ef24-4964-9d06-c53d30f4c624" />

## Future Improvements
- Timer-based quiz mode
- Score history & leaderboard
- User authentication
- Offline mode
- Difficulty-based scoring

## Developed By
**Rishika Gautam**  

