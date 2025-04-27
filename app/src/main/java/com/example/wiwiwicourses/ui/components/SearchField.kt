package com.example.wiwiwicourses.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Icon
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.VisualTransformation
import com.example.wiwiwicourses.R
import com.example.wiwiwicourses.ui.theme.TransparentDarkBlue

/*@Composable
fun SearchInpurField(
    value:String,
    onValueChange: (String) -> Unit,
    onSearch:() -> Unit,
    modifier: Modifier
){
    Row(
        modifier = modifier.fillMaxWidth().padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        InputField(
            leadingIconRes = R.drawable.ic_search,
            placeholderText = "Search",
            visualTransformation = VisualTransformation.None,
            modifier = Modifier.padding(horizontal = 16.dp),
            value = value,
            height = 60,
            roundedCornerShape = 20,
            containerColor = TransparentDarkBlue,
            onValueChange = onValueChange
        )

    }
}*/

@Composable
fun SearchField(
    modifier: Modifier = Modifier,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    @DrawableRes trailingActionIconRes: Int? = null,
    placeholderText: String,
    value: String,
    height: Int,
    roundedCornerShape: Int,
    containerColor: Color,
    onValueChange: (String) -> Unit,
    onTrailingIconClick: () -> Unit = {}
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,

    ) {
        InputField(
            modifier = Modifier.weight(1f),
            visualTransformation = visualTransformation,
            leadingIconRes = R.drawable.baseline_dehaze_24,
            placeholderText = placeholderText,
            value = value,
            height = height,
            roundedCornerShape = roundedCornerShape,
            containerColor = containerColor,
            onValueChange = onValueChange
        )

        trailingActionIconRes?.let { iconRes ->
            Spacer(modifier = Modifier.width(8.dp).background(TransparentDarkBlue))
            IconButton(
                onClick = onTrailingIconClick,
                modifier = Modifier.size(height.dp).background(
                    color = TransparentDarkBlue,
                    shape = RoundedCornerShape(
                        topStart = roundedCornerShape.dp,
                        bottomStart = roundedCornerShape.dp,
                        topEnd = roundedCornerShape.dp,
                        bottomEnd = roundedCornerShape.dp
                    )
                )
            ) {
                Icon(
                    painter = painterResource(iconRes),
                    contentDescription = "Action icon",
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}