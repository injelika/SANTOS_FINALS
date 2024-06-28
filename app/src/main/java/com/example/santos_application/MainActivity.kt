package com.example.santos_application

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Main Activity of my Application
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp()
        }
    }
}

// Data class to represent a question, its options, and the correct answer.
data class Question(val question: String, val options: List<String>, val answer: String)

// Main composable function that represents the entire quiz application.
@Composable
fun MyApp() {
    // State to keep track of the current question index, score, and quiz completion status.
    var currentQuestionIndex by remember { mutableStateOf(0) }
    var score by remember { mutableStateOf(0) }
    var quizFinished by remember { mutableStateOf(false) }

    // List of questions, choices, and correct answers.
    val questions = listOf(
        Question(
            "Is Chihiro a Boy or a Girl?",
            listOf("Boy", "Girl"),
            "Girl"
        ),
        Question(
            "Where was Spirited Away made?",
            listOf("Britain", "China", "Japan", "America"),
            "Japan"
        ),
        Question(
            "What animal does Haku turn into?",
            listOf("Cow", "Dragon", "Goat", "Cat"),
            "Dragon"
        ),
        Question(
            "What does the witch take from Chihiro?",
            listOf("Her parents", "Her spirit", "Her purse", "Her name"),
            "Her name"
        ),
        Question(
            "What are Chihiro's parents turned into?",
            listOf("Pigs", "Dogs", "Birds", "Ghosts"),
            "Pigs"
        ),
    )

    // State to manage which screen to display
    var currentScreen by remember { mutableStateOf(Screen.Start) }

    // Layout of the application using Box and Column functions
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFC0CB)) // Background color
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "⋆ ˚｡⋆୨♡୧⋆ ˚｡⋆   ♡   ⋆ ˚｡⋆୨♡୧⋆ ˚｡⋆   ♡   ⋆ ˚｡⋆୨♡୧⋆ ˚｡⋆   ♡  ⋆ ˚｡⋆୨♡୧⋆ ˚｡⋆   ♡   ⋆ ˚｡⋆୨♡୧⋆ ˚｡⋆",
                fontSize = 9.5.sp,
                color = Color(0xFFFF69B4),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 0.dp)
            )
            Text(
                text = "Welcome to my Spirited Away Quiz! ♡ ",
                color = Color.White,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 4.dp)
            )
            Text(
                text = "⋆ ˚｡⋆୨♡୧⋆ ˚｡⋆ ♡ ⋆ ˚｡⋆୨♡୧⋆ ˚｡⋆  By: Santos, Angelica S.⋆  ˚｡⋆୨♡୧⋆ ˚｡⋆ ♡  ⋆ ˚｡⋆୨♡୧⋆ ˚｡⋆ ",
                color = Color(0xFFFF69B4),
                fontSize = 9.5.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(vertical = 0.dp)
                    .fillMaxWidth()
            )
            Image(
                painter = painterResource(id = R.drawable.pic_1),
                contentDescription = "Spirited Away Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(vertical = 8.dp)
                    .clip(RoundedCornerShape(12.dp)) // Clip with rounded corners
                    .shadow(10.dp, shape = RoundedCornerShape(12.dp)) // Add shadow
                    .clickable {
                        // Click listener for the image if needed
                    }
            )
            // Display different screens based on currentScreen
            Crossfade(
                targetState = currentScreen,
                animationSpec = tween(durationMillis = 500)
            ) { screen ->
                when (screen) {
                    Screen.Start -> StartScreen(
                        onStartQuiz = {
                            currentScreen = Screen.Quiz
                        }
                    )
                    Screen.Quiz -> QuizScreen(
                        question = questions[currentQuestionIndex],
                        onOptionSelected = { selectedOption ->
                            if (selectedOption == questions[currentQuestionIndex].answer) {
                                score++
                            }
                            if (currentQuestionIndex < questions.size - 1) {
                                currentQuestionIndex++
                            } else {
                                currentScreen = Screen.Result
                            }
                        }
                    )
                    Screen.Result -> ResultScreen(
                        score = score,
                        onRestartQuiz = {
                            currentQuestionIndex = 0
                            score = 0
                            currentScreen = Screen.Start
                        }
                    )
                }
            }
        }
    }
}

// Composable function to create option buttons for each question.
@Composable
fun OptionButton(option: String, onOptionSelected: (String) -> Unit) {
    Button(
        onClick = { onOptionSelected(option) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clip(RoundedCornerShape(12.dp)), // Clip with rounded corners
        colors = ButtonDefaults.buttonColors(Color(0xFFFF69B4)),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(text = option, color = Color.White, fontSize = 18.sp)
    }
}

// Start screen composable
@Composable
fun StartScreen(onStartQuiz: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f)) // Spacer to push the button to the vertical center
        Button(
            onClick = {
                onStartQuiz()
            },
            colors = ButtonDefaults.buttonColors(Color(0xFFFF69B4)),
            modifier = Modifier
                .padding(vertical = 8.dp)
                .clip(RoundedCornerShape(12.dp)) // Clip with rounded corners
                .clickable(
                    onClick = {
                        onStartQuiz()
                    }
                )
        ) {
            Text(text = "Start Quiz", color = Color.White, fontSize = 20.sp)
        }
        Spacer(modifier = Modifier.weight(10f)) // Spacer to push the button to the vertical center
    }
}

// Quiz screen composable
@Composable
fun QuizScreen(question: Question, onOptionSelected: (String) -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = question.question,
            color = Color.White,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(vertical = 16.dp)
                .clip(RoundedCornerShape(12.dp)) // Clip with rounded corners
                .background(Color(0xFFFF69B4)) // Background color
                .padding(16.dp)
        )
        question.options.forEach { option ->
            OptionButton(
                option = option,
                onOptionSelected = { selectedOption ->
                    onOptionSelected(selectedOption)
                }
            )
        }
    }
}

// Result screen composable
@Composable
fun ResultScreen(score: Int, onRestartQuiz: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Quiz Finished!",
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        Text(
            text = "Your final score is $score",
            color = Color.White,
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(vertical = 16.dp)
        )
        Button(
            onClick = onRestartQuiz,
            colors = ButtonDefaults.buttonColors(Color(0xFFFF69B4)),
            modifier = Modifier
                .padding(vertical = 8.dp)
                .clip(RoundedCornerShape(12.dp)) // Clip with rounded corners
        ) {
            Text(text = "Restart Quiz", color = Color.White, fontSize = 20.sp)
        }
    }
}

// Enum class to represent different screens
enum class Screen {
    Start, Quiz, Result
}

@Preview(showBackground = true) // To display the output
@Composable
fun MyAppPreview() {
    MyApp()
}
