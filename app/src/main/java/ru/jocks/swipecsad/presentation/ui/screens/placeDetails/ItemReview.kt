package ru.jocks.swipecsad.presentation.ui.screens.placeDetails

import android.widget.Space
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.jocks.swipecsad.presentation.ui.screens.main.place.RatingStars
import ru.jocks.swipecsad.presentation.ui.theme.DividerColor

@Composable
fun ItemReview(name : String, rating : Double) {
    Column {
        Row(Modifier.height(44.dp), verticalAlignment = Alignment.CenterVertically) {
            Text(text = name, modifier = Modifier.padding(start=24.dp), style = MaterialTheme.typography.bodyMedium)
            Spacer(Modifier.weight(1f))
            RatingStars(rating = rating,modifier = Modifier.padding(end=24.dp), size=16.dp)
        }
        Divider(Modifier.fillMaxWidth(), thickness = 1.dp, color = DividerColor)
    }
}