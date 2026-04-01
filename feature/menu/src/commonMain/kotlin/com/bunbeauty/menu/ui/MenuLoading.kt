package com.bunbeauty.menu.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.bunbeauty.designsystem.theme.FoodDeliveryTheme
import com.bunbeauty.designsystem.ui.element.shimmer.Shimmer
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun MenuLoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Column {
            Shimmer(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(height = 118.dp)
                        .clip(
                            shape = RoundedCornerShape(bottomEnd = 32.dp, bottomStart = 32.dp),
                        ),
            )

            LazyRow(
                modifier = Modifier.padding(top = 40.dp),
                horizontalArrangement = spacedBy(8.dp),
                contentPadding = PaddingValues(horizontal = 16.dp),
            ) {
                items(6) {
                    Shimmer(
                        modifier =
                            Modifier
                                .width(width = 80.dp)
                                .height(height = 30.dp)
                                .clip(shape = RoundedCornerShape(16.dp)),
                    )
                }
            }

            Shimmer(
                modifier =
                    Modifier
                        .padding(top = 32.dp, bottom = 8.dp)
                        .padding(horizontal = 16.dp)
                        .width(123.dp)
                        .height(height = 30.dp)
                        .clip(shape = RoundedCornerShape(16.dp)),
            )

            repeat(3) { times ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(space = 8.dp),
                    modifier =
                        Modifier
                            .padding(horizontal = 16.dp),
                ) {
                    Shimmer(
                        modifier =
                            Modifier
                                .padding(top = 8.dp)
                                .weight(1f)
                                .height(height = 180.dp)
                                .clip(shape = RoundedCornerShape(24.dp)),
                    )
                    Shimmer(
                        modifier =
                            Modifier
                                .padding(top = 8.dp)
                                .weight(1f)
                                .height(height = 180.dp)
                                .clip(shape = RoundedCornerShape(24.dp)),
                    )
                }
            }
        }

        Row(
            Modifier
                .align(Alignment.BottomEnd),
        ) {
            Shimmer(
                modifier =
                    Modifier
                        .padding(bottom = 16.dp, end = 16.dp)
                        .navigationBarsPadding()
                        .width(width = 106.dp)
                        .height(height = 54.dp)
                        .clip(shape = RoundedCornerShape(16.dp)),
            )
        }
    }
}

@Preview
@Composable
fun MenuLoadingScreenPreview() {
    FoodDeliveryTheme {
        MenuLoadingScreen()
    }
}
