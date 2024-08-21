package ru.jocks.swipecsad.presentation.ui.screens.main.place

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.jocks.swipecsad.R

@Composable
fun Rating(
    rating: Double,
    ratingCount: Int
) {
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
        Text(
            text = "%.1f".format(rating),
            style = MaterialTheme.typography.bodySmall.copy(fontSize = 16.sp)
        )
        RatingStars(rating = rating)
        Text(
            text = stringResource(id = R.string.brackets, ratingCount),
            style = MaterialTheme.typography.bodySmall.copy(fontSize = 16.sp)
        )
    }
}

@Composable
fun RatingStars(
    modifier : Modifier = Modifier,
    rating : Double, size : Dp = 16.dp,
    onRatingClicked : ((Double) -> Unit)? = null) {
    Row(horizontalArrangement = Arrangement.spacedBy(4.dp), modifier=modifier    ) {
        for (i in 1..5) {
            Image(
                painter = painterResource(id = if (i <= rating) R.drawable.star else R.drawable.star_outlined),
                contentDescription = null,
                Modifier.size(size).let {
                    if (onRatingClicked!=null) {
                        it.clickable {
                            onRatingClicked(i.toDouble())
                        }
                    } else {
                        it
                    }
                }
            )
        }
    }
}