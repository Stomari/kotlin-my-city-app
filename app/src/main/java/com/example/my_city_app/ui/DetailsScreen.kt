package com.example.my_city_app.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.my_city_app.R
import com.example.my_city_app.data.CategoriesData
import com.example.my_city_app.model.Recommendation

@Composable
fun DetailsScreen(recommendation: Recommendation?, modifier: Modifier = Modifier) {
    if (recommendation != null) {
        Column(modifier = modifier.verticalScroll(rememberScrollState())) {
            Image(
                painter = painterResource(id = recommendation.imgResource),
                contentDescription = stringResource(
                    id = recommendation.title
                ),
                modifier = Modifier.heightIn(max = 300.dp).fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
            Text(
                text = stringResource(id = recommendation.title), modifier = Modifier.padding(
                    dimensionResource(id = R.dimen.padding_medium)
                ), style = MaterialTheme.typography.headlineMedium
            )
            Text(
                text = stringResource(id = recommendation.description), modifier = Modifier.padding(
                    horizontal = dimensionResource(
                        id = R.dimen.padding_medium
                    )
                ), style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailsScreenPreview() {
    DetailsScreen(recommendation = CategoriesData.loadCategories()[0].recommendations[0])
}