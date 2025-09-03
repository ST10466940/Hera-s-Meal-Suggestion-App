package com.hera.mealsuggest

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp

private const val TAG = "MealSuggest"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(Modifier.fillMaxSize()) {
                    MealSuggestScreen()
                }
            }
        }
    }
}

@Composable
fun MealSuggestScreen() {
    var input by remember { mutableStateOf("") }
    var suggestion by remember { mutableStateOf("Type a time of day and tap Suggest.") }
    val ctx = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Hera’s Meal Picker",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(Modifier.height(8.dp))
        Text(
            "Tell me the time of day (e.g., “Morning”, “Mid-morning snack”, “Afternoon”, “Dinner”…)"
        )
        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = input,
            onValueChange = { input = it },
            label = { Text("Time of day") },
            singleLine = true,
            supportingText = { Text("Examples: Morning, Mid-morning snack, Afternoon, Afternoon snack, Dinner, After dinner snack") },
            keyboardActions = KeyboardActions(
                onDone = {
                    suggestion = handleSuggest(input) { msg ->
                        Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show()
                    }
                }
            ),
            imeAction = ImeAction.Done,
            modifier = Modifier
                .padding(top = 4.dp)
        )

        Spacer(Modifier.height(10.dp))

        // Helpful quick-fill buttons to make the UI engaging
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf(
                "Morning", "Mid-morning snack", "Afternoon",
                "Afternoon snack", "Dinner", "After dinner snack"
            ).forEach { label ->
                Button(onClick = { input = label }) { Text(label) }
            }
        }

        Spacer(Modifier.height(16.dp))
        Button(onClick = {
            suggestion = handleSuggest(input) { msg ->
                Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show()
            }
        }) {
            Text("Suggest")
        }

        Spacer(Modifier.height(24.dp))
        ElevatedCard {
            Column(Modifier.padding(16.dp)) {
                Text("Suggestion", style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(8.dp))
                Text(suggestion, style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}

/**
 * Core logic using if/else statements (as required by the assignment).
 * We normalize the user input and categorize with friendly synonyms.
 */
private fun handleSuggest(raw: String, onError: (String) -> Unit): String {
    val t = raw.trim().lowercase()
    Log.d(TAG, "User input: \"$raw\" -> normalized: \"$t\"")

    if (t.isBlank()) {
        onError("Try something like “Morning” or “Dinner”.")
        return "Please enter a time of day."
    }

    // After Dinner Snack
    if ((t.contains("after") && t.contains("dinner")) || t == "after dinner snack" || t.contains("late night snack")) {
        val options = listOf("Ice cream 🍨", "Fruit salad 🍓", "Yoghurt with honey 🍯")
        val pick = options.random()
        Log.d(TAG, "Matched: after-dinner-snack -> $pick")
        return "Dessert idea: $pick"
    }

    // Dinner
    if (t.contains("dinner") || t.contains("evening") || t.contains("supper") || t.contains("night")) {
        val options = listOf("Pasta 🍝", "Stir-fry 🥦", "Grilled chicken with veggies 🍗")
        val pick = options.random()
        Log.d(TAG, "Matched: dinner -> $pick")
        return "Main course: $pick"
    }

    // Afternoon Snack
    if ((t.contains("afternoon") && t.contains("snack")) || t.contains("tea time")) {
        val options = listOf("Cookies and milk 🍪🥛", "Trail mix 🥜", "Cheese & crackers 🧀")
        val pick = options.random()
        Log.d(TAG, "Matched: afternoon-snack -> $pick")
        return "Quick bite: $pick"
    }

    // Afternoon (Lunch)
    if (t.contains("afternoon") || t.contains("lunch") || t.contains("midday") || t.contains("noon")) {
        val options = listOf("Sandwich 🥪", "Salad 🥗", "Burrito 🌯")
        val pick = options.random()
        Log.d(TAG, "Matched: afternoon/lunch -> $pick")
        return "Lunch: $pick"
    }

    // Mid-morning snack
    if (t.contains("mid") && t.contains("morning") || t.contains("brunch")) {
        val options = listOf("Fruit & yoghurt 🍓🥣", "Granola bar 🌾", "Boiled eggs 🥚")
        val pick = options.random()
        Log.d(TAG, "Matched: mid-morning-snack -> $pick")
        return "Light snack: $pick"
    }

    // Morning (Breakfast)
    if (t.contains("morning") || t.contains("breakfast") || t == "am") {
        val options = listOf("Pancakes 🥞", "Smoothie 🥤", "Oats with banana 🍌")
        val pick = options.random()
        Log.d(TAG, "Matched: morning/breakfast -> $pick")
        return "Breakfast: $pick"
    }

    // Generic "snack" if user only typed "snack"
    if (t == "snack") {
        val options = listOf("Apple slices 🍎", "Popcorn 🍿", "Nut mix 🥜")
        val pick = options.random()
        Log.d(TAG, "Matched: generic-snack -> $pick")
        return "Snack: $pick"
    }

    // Fallback: constructive error
    onError("Hmm, I don’t recognize “$raw”. Try: Morning, Mid-morning snack, Afternoon, Afternoon snack, Dinner, or After dinner snack.")
    Log.d(TAG, "No category matched; showing guidance.")
    return "Not sure yet — try a clearer time like “Morning” or “Dinner”."
}
