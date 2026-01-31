package com.mahesh_prajapati.hiltmvvmnetworking.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mahesh_prajapati.hiltmvvmnetworking.R
import com.mahesh_prajapati.hiltmvvmnetworking.viewModel.CategoryViewModel

@Composable
fun CategoryScreen(onClick: (catagory: String) -> Unit = {}) {
    val categoryViewModel: CategoryViewModel = hiltViewModel()
    val category = categoryViewModel.categories.collectAsState()
    LazyVerticalGrid(columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.SpaceAround) {
        items(category.value.distinct()) {
            CategoryItems(category = it, onClick)
        }
    }
}

@Composable
fun CategoryItems(category: String, onClick: (catagory: String) -> Unit = {}) {
    Box(
        modifier = Modifier.padding(4.dp)
            .clickable {
                onClick(category)
            }
            .size(160.dp)
            .clip(RoundedCornerShape(8.dp))
            .paint(painter = painterResource(id = R.drawable.item_bg),
                contentScale = ContentScale.Crop)
            .border(1.dp, Color.Gray), contentAlignment = Alignment.BottomCenter
    ) {
        Text(text = category.capitalize(java.util.Locale.getDefault()), fontSize = 18.sp, color = Color.Black,
            modifier = Modifier.padding(0.dp,8.dp),
            style = MaterialTheme.typography.bodyMedium)
    }
}