package com.bunbeauty.designsystem.ui.element

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow

@Composable
fun OverflowingText(
    modifier: Modifier = Modifier,
    text: String,
    style: TextStyle,
    color: Color,
    maxLines: Int = 1,
) {
    Text(
        modifier = modifier,
        text = text,
        style = style,
        color = color,
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis,
    )
}
