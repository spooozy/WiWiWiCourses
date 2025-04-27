package com.example.wiwiwicourses.ui.screen.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import com.example.wiwiwicourses.Data.Course
import com.example.wiwiwicourses.ui.theme.DarkBlue
import com.example.wiwiwicourses.ui.theme.TransparentDarkBlue

@Composable
fun CourseListItem(course: Course,
                   modifier: Modifier = Modifier,
                   onFavClick: () -> Unit = {},
                   onCourseClick: (Course) -> Unit = {}
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(TransparentDarkBlue, shape = RoundedCornerShape(20.dp))
            .clip(RoundedCornerShape(15.dp))
            .clickable{
                onCourseClick(course)
            }
    ) {
        Column(
            modifier = Modifier
                .width(190.dp)
                .padding(10.dp)
        ) {
            AsyncImage(
                model = course.images.cover,
                contentDescription = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(175.dp)
                    .clip(RoundedCornerShape(20.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(15.dp))
            Text(
                text = course.title,
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                minLines = 2,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(15.dp))
            Row(modifier = Modifier.fillMaxWidth().height(25.dp),
                verticalAlignment = Alignment.CenterVertically)
            {
                Text(
                    text = course.price,
                    color = Color.Black,
                    maxLines = 1,
                    fontSize = 14.sp,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = {
                    onFavClick()
                }) {
                    Icon(
                        if(course.isFavorite) {
                            Icons.Default.Favorite
                        } else {
                            Icons.Default.FavoriteBorder
                               },
                        contentDescription = "",
                        modifier = Modifier.size(18.dp),
                        tint = DarkBlue
                    )
                }
            }
        }
    }
}