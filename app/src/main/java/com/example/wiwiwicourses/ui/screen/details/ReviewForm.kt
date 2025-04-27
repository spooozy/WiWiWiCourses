package com.example.wiwiwicourses.ui.screen.details

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.wiwiwicourses.ui.theme.Black
import com.example.wiwiwicourses.ui.theme.DarkBlue
import com.example.wiwiwicourses.ui.theme.TransparentDarkBlue
import java.nio.file.WatchEvent

@Composable
fun ReviewForm(
    onReviewChange: (String) -> Unit,
    onRatingChange: (Float) -> Unit,
    onCommentButtonClick: () -> Unit
){
    var reviewText = remember { mutableStateOf("") }
    var rating = remember { mutableStateOf(0f) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ){

        Text(
            text = "Leave your comment:",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            textAlign = TextAlign.Center
        )

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 60.dp, max = 200.dp),
            value = reviewText.value,
            onValueChange = {
                reviewText.value = it
                onReviewChange(it)
            },
            visualTransformation = VisualTransformation.None,
            singleLine = false,
            shape = RoundedCornerShape(20.dp),
            maxLines = 5,
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
                focusedTextColor = Black,
                unfocusedTextColor = Black,
                unfocusedPlaceholderColor = Black,
                focusedPlaceholderColor = Black,
                focusedLeadingIconColor = Black,
                unfocusedLeadingIconColor = Black,
                focusedContainerColor = TransparentDarkBlue,
                unfocusedContainerColor = TransparentDarkBlue
            ),
            placeholder = {
                Text(text = "...")
            }
        )

        Row(
            modifier = Modifier.padding(vertical = 8.dp).height(40.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            (1..5).forEach { index ->
                Icon(
                    imageVector = if (index <= rating.value) Icons.Filled.Star else Icons.Outlined.Star,
                    contentDescription = "Star $index",
                    modifier = Modifier
                        .size(30.dp)
                        .clickable {
                            rating.value = index.toFloat()
                            onRatingChange(rating.value)
                        },
                    tint = if (index <= rating.value) DarkBlue else TransparentDarkBlue
                )
            }
            Button (
                onClick = onCommentButtonClick,
                colors = ButtonDefaults.buttonColors(containerColor = DarkBlue)
            ){
                Text(
                    text = "Leave comment"
                )
            }
        }

    }
}