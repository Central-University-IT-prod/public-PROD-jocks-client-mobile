package ru.jocks.swipecsad.presentation.ui.screens.main.place

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import ru.jocks.swipecsad.R

@Composable
fun PlaceInfo(
    name: String,
    rating: Double,
    ratingCount : Int,
    description: String,
    imageUrl : String,
) {
    Row(Modifier.padding(24.dp), verticalAlignment = Alignment.CenterVertically) {
        Card(shape = RoundedCornerShape(16.dp), modifier = Modifier.size(96.dp)) {
            AsyncImage(
                model = imageUrl,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillHeight,
            )
        }
        Spacer(Modifier.weight(1f))
        Column(verticalArrangement = Arrangement.spacedBy(6.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = name, style = MaterialTheme.typography.titleMedium.copy(textAlign = TextAlign.Center))
            Rating(rating=rating, ratingCount=ratingCount)
            Text(text = description, style = MaterialTheme.typography.bodySmall.copy(textAlign = TextAlign.Center))
        }
    }
}

