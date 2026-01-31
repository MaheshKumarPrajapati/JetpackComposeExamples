package com.mahesh_prajapati.jetpeckcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


class ScrollableViews : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LazyColumn {
                items(personList.size) { index ->
                    val person = personList[index]
                    listViewItem(person.cellColor, person.name, person.occupation)
                }
            }

        }
    }

    @Composable
    fun listViewItem(color: Color, title: String, description: String) {
      Card(elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
          modifier = Modifier.padding(10.dp).background(Color.White)) {
          Row(
              verticalAlignment = Alignment.CenterVertically,
              modifier = Modifier.padding(8.dp)
          ) {
              Image(
                  painter = painterResource(R.drawable.ic_launcher_foreground),
                  contentDescription = "My Image",
                  colorFilter = ColorFilter.tint(color),
                  contentScale = ContentScale.Crop,
                  modifier = Modifier.size(80.dp).padding(8.dp).weight(2f)
              )

              Column(modifier = Modifier.weight(8f)){
                  Text(
                      text = title,
                      fontSize = 20.sp,
                      color = Color.Black,
                      style = MaterialTheme.typography.titleLarge,
                      textAlign = TextAlign.Center
                  )
                  Text(
                      text = description,
                      fontSize = 15.sp,
                      color = Color.Gray,
                      style = MaterialTheme.typography.titleSmall,
                      fontWeight = FontWeight.Thin,
                      textAlign = TextAlign.Center
                  )
              }
          }
      }

    }

    @Preview(showBackground = true, name = "listViewItem Preview", showSystemUi = true)
    @Composable
    fun listViewItemPreview() {
        Column(Modifier.verticalScroll(rememberScrollState())) {
            personList.map{
                listViewItem(it.cellColor, it.name, it.occupation)
            }
        }
    }

}

data class Person(val name: String, val occupation: String, val cellColor: Color)

val personList = listOf(
    // Sample data
    Person("Alice", "Engineer", Color.Red),
    Person("Bob", "Designer", Color.Blue),
    Person("Charlie", "Doctor", Color.Green),
    Person("Diana", "Artist", Color.Yellow),
    Person("Ethan", "Teacher", Color.Cyan),
    Person("Fiona", "Chef", Color.Magenta),
    Person("Jorge", "Engineer", Color.Red),
    Person("John", "Designer", Color.Blue),
    Person("Tom", "Doctor", Color.Green),
    Person("Kitty", "Artist", Color.Yellow),
    Person("Yulia", "Teacher", Color.Cyan),
    Person("Nolan", "Chef", Color.Magenta),

)