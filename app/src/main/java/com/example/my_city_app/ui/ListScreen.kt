package com.example.my_city_app.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.my_city_app.R
import com.example.my_city_app.data.CategoriesData
import com.example.my_city_app.model.ListItem
import com.example.my_city_app.model.Recommendation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(
    itemsList: List<ListItem>, onCardClick: (Int) -> Unit, modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(dimensionResource(id = R.dimen.padding_small))
    ) {
        items(items = itemsList) {
            Card(
                onClick = { onCardClick(it.id) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = dimensionResource(id = R.dimen.padding_small))
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = it.imgRes),
                        contentDescription = stringResource(id = it.stringRes),
                        modifier = Modifier.size(120.dp),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium)))
                    Text(
                        text = stringResource(id = it.stringRes),
                        style = MaterialTheme.typography.headlineMedium
                    )
                }
            }
        }
    }
}

@Composable
fun ListScreenWithDetails(
    itemsList: List<ListItem>,
    onCardClick: (Int) -> Unit,
    recommendation: Recommendation,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        ListScreen(itemsList = itemsList, onCardClick = onCardClick, modifier = Modifier.weight(1f))
        DetailsScreen(recommendation = recommendation, modifier = Modifier.weight(1f))
    }
}

@Preview
@Composable
fun ListScreenPreview() {
    val list = CategoriesData.loadCategories().map {
        ListItem(it.id, it.title, it.imgResource)
    }
    ListScreen(itemsList = list, onCardClick = {})
}

@Preview(widthDp = 1000, showBackground = true)
@Composable
fun ListScreenWithDetailsPreview() {
    val categories = CategoriesData.loadCategories()
    val list = categories.map {
        ListItem(it.id, it.title, it.imgResource)
    }
    ListScreenWithDetails(itemsList = list, onCardClick = {}, recommendation = categories[0].recommendations[0]!!)
}