package com.mahesh_prajapati.jetpeckcompose

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class BasicComposableActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
             coloumnDraw()
        }
    }
}

@Composable
fun coloumnDraw(){
    Column(
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ImageDraw()
            Greeting(name = "Mahesh")
            buttonDraw()
            textFieldDraw()
        }

        Row(horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.horizontalScroll(rememberScrollState()).padding(5.dp)) {
            ImageDraw()
            Greeting(name = "Mahesh")
            buttonDraw()
            textFieldDraw()
        }
    }
}

@Composable
fun textFieldDraw(){
    var text by remember { mutableStateOf("Hello World") }

    TextField(
        value = text,
        onValueChange = { newText ->
            // 2. Update the state when the user types
            text = newText
            Log.d("textFieldDraw", newText)
        },
        // 3. Wrap the Text composable in a lambda for the label
        label = { Text("Enter Text") }
    )
}

@Composable
fun buttonDraw(){
    Button(onClick = {}) {
        Text("Login", fontSize = 20.sp, modifier = Modifier.padding(10.dp) )
        Image(painterResource(R.drawable.ic_launcher_foreground),
            contentDescription = "My Image",
            colorFilter = ColorFilter.tint(Color.Red),
            contentScale = ContentScale.Crop)
    }
}


@Composable
fun Greeting(name: String) {
    Text(
        text = "Hello $name!",
        fontSize = 15.sp,
        color = Color.Red,
        fontStyle = FontStyle.Italic,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center
    )
}

@Composable
fun ImageDraw(){
    Image(painter = painterResource(R.drawable.youtube),
        contentDescription = "My Image",
        contentScale = ContentScale.Crop)
}

@Preview(showBackground = true, name = "coloumnDraw Preview", widthDp = 400, heightDp = 800)
@Composable
fun coloumnDrawPreview() {
    coloumnDraw()
}

/*@Preview(showBackground = true, name = "textFieldDraw Preview", showSystemUi = true)
@Composable
fun textFieldDrawPreview() {
    textFieldDraw()
}*/

/*@Preview(showBackground = true, name = "buttonDraw Preview", showSystemUi = true)
@Composable
fun buttonDrawPreview() {
    buttonDraw()
}*/

/*@Preview(showBackground = true, name = "Greeting Preview", showSystemUi = true, widthDp = 100, heightDp = 50)
@Composable
fun GreetingPreview() {
    Greeting("Android")
}*/


/*@Preview(showBackground = true, name = "ImageDraw Preview", widthDp = 200, heightDp = 200)
@Composable
fun ImageDrawPreview() {
    ImageDraw()
}*/
