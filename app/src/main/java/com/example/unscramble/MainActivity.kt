package com.example.unscramble

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextGranularity.Companion.Word
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.room.util.query
import com.example.unscramble.ui.GameScreen
import com.example.unscramble.ui.theme.UnscrambleTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        val db = AppDatabase.getInstance(applicationContext)
        val listOfWords = mutableStateListOf<String>("Adi","nugrohgo")
        val words = mutableStateListOf<Words>()
        super.onCreate(savedInstanceState)
        setContent {
            UnscrambleTheme {
                LaunchedEffect(Unit) {
                    val response = db.WordsDao().getAllWords()
                    words.clear()
                    if(response.isEmpty()){
                        val kata = listOfWords
                        words.addAll(kata.mapIndexed { index, s -> Words(s, index) })
                        db.WordsDao().insert(*words.toTypedArray())
                    }else{
                        words.addAll(response)
                    }
                }
                Scaffold(modifier = Modifier.fillMaxSize()){
                innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding)) {
                        Layout(db,words)
                        LazyColumn(modifier = Modifier.padding(innerPadding)) {
                            items(words){
                                kata(it)
                            }
                    }

                    }
                }
            }
        }
    }
}

@Composable
fun kata(words: Words,modifier: Modifier = Modifier){
    Column {
        Text(
            text = words.kata.toString(),
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun Layout(db: AppDatabase,words: MutableList<Words>,
           modifier: Modifier = Modifier){
    var text by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    Column(modifier = modifier.padding(16.dp)) {
        Text(
            text = "Masukan Kata anda",
          
        )

        OutlinedTextField(
            value = text,
            onValueChange = {
                text = it
            },
        )

        Button(
            onClick = {
                scope.launch {
                    val newWord = Words(kata = text)
                    db.WordsDao().insert(newWord)
                    words.add(newWord)
                    text = ""
                }
            },
            ){
                Text(text = "Submit")
            }

    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    UnscrambleTheme {
        kata(Words())
    }
}
