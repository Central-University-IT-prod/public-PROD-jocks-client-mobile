package ru.jocks.swipecsad.presentation.ui.custom

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.jocks.swipecsad.R

@Composable
fun Coupon(
    title : String,
    text : String,
    code : String
) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        content = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top=24.dp).padding(horizontal=24.dp),
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    style = MaterialTheme.typography.headlineMedium,
                    text = title,
                )
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = text,
                    style = MaterialTheme.typography.headlineSmall
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            DashDivider(color = Color.Black)

            Spacer(modifier = Modifier.height(8.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top=16.dp)
                    .padding(horizontal=26.dp),
                content = {
                    Text(
                        text = stringResource(id = R.string.coupon_title),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Right,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = code,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Right,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.height(13.dp))
                }
            )
        }
    )
}