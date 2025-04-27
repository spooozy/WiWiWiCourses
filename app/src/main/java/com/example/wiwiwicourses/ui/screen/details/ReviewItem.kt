package com.example.wiwiwicourses.ui.screen.details

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.wiwiwicourses.Data.Review
import com.example.wiwiwicourses.ui.theme.TransparentDarkBlue
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ReviewItem(review: Review) {
    Column(modifier = Modifier.padding(16.dp).fillMaxWidth()) {
        Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween) {
            Box(
                modifier = Modifier
                    .widthIn(min = 70.dp, max = 200.dp)
                    .background(TransparentDarkBlue)
            ) {
                Text(
                    text = review.userName,
                    modifier = Modifier.align(Alignment.Center).clip(RoundedCornerShape(5.dp))
                )
            }
            Text(
                text = review.timestamp.toFormattedDate(),
                color = Color.Gray
            )
        }
        Text(
            text = review.text,
            modifier = Modifier.padding(top = 8.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Divider(
            color = TransparentDarkBlue,
            thickness = 1.dp,
            modifier = Modifier.padding(horizontal = 3.dp)
        )
    }
}

fun Long.toFormattedDate(): String {
    val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    return dateFormat.format(Date(this))
}