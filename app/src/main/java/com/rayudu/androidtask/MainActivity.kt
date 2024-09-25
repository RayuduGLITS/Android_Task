package com.rayudu.androidtask

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rayudu.androidtask.ui.theme.AndroidTaskTheme
import kotlin.math.sqrt

class MainActivity : ComponentActivity() {

    private var diffPatterns = listOf(
        Pair("Odd Numbers", 1),
        Pair("Even Numbers", 2),
        Pair("Prime Numbers", 3),
        Pair("Fibonacci sequence numbers", 4)
    )
    private var selectedPattern by mutableStateOf("")
    private var selectedItem by mutableIntStateOf(0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContent {
            AndroidTaskTheme {

                Column {
                    val fibonacciSequence = generateFibonacci(100)

                    Text(
                        modifier = Modifier
                            .wrapContentSize().align(Alignment.CenterHorizontally)
                            .padding(top = 20.dp),
                        text = "Assignment",
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )

                    CustomRadioGroup(
                        modifier = Modifier
                            .padding(start = 10.dp),
                        options = diffPatterns.map { it.first },
                        selectedOption = selectedPattern,
                        onSelectionChange = { itt ->
                            selectedPattern = itt

                             selectedItem = diffPatterns.find { it.first == itt }?.second ?: 0

                        }
                    )

                    LazyVerticalGrid(columns = GridCells.Fixed(4)) {
                        items(100){ pos ->
                            val index = pos+1
                            GridItem(
                                index = index,
                                cardBg = when (selectedItem) {
                                    1 -> isOdd(index)
                                    2 -> isEven(index)
                                    3 -> isPrime(index)
                                    4 -> fibonacciSequence.any { it.toInt() == index }
                                    else -> false
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun GridItem(
    modifier: Modifier = Modifier,
    index : Int,
    cardBg : Boolean = false
){

    val containerColor = if (cardBg) Color( 0xFFF2F8B7) else Color(0xFF7373FC)
    val contentColor = if (cardBg) Color.Black else Color.White

    Card(
        modifier = modifier
            .size(width = 48.dp, height = 48.dp)
            .clickable {

            },
        border = BorderStroke(1.dp,Color.DarkGray),
        shape = RoundedCornerShape(size = 4.dp),
        colors = CardColors(containerColor,contentColor,Color.Gray,Color.White)
    ){
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = ""+index)
        }
    }
}


@Composable
fun CustomRadioGroup(
    options: List<String>,
    selectedOption: String,
    onSelectionChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        options.forEach { option ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onSelectionChange(option)
                    }
                    .padding(vertical = 16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(20.dp)
                        .border(1.dp, Color.Black)
                        .padding(4.dp)
                ) {
                    if (option == selectedOption) {
                        Box(
                            modifier = Modifier
                                .size(12.dp)
                                .background(Color.Black)
                        )
                    }
                }
                Text(
                    text = option,
                    modifier = Modifier
                        .padding(start = 8.dp)
                )
            }
        }
    }
}


fun generateFibonacci(n: Int): List<Long> {
    val fibonacciList = mutableListOf<Long>(0, 1)
    for (i in 2 until n) {
        val nextValue = fibonacciList[i - 1] + fibonacciList[i - 2]
        fibonacciList.add(nextValue)
    }
    return fibonacciList
}

fun isEven(n: Int): Boolean {
    return n % 2 == 0
}

fun isOdd(n: Int): Boolean {
    return n % 2 != 0
}

fun isPrime(n: Int): Boolean {
    if (n <= 1) return false
    if (n == 2) return true
    if (n % 2 == 0) return false
    val sqrtN = sqrt(n.toDouble()).toInt()
    for (i in 3..sqrtN step 2) {
        if (n % i == 0) return false
    }
    return true
}



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    AndroidTaskTheme {
        GridItem(index = 1)
    }
}