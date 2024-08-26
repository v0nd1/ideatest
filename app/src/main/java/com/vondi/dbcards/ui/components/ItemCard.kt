package com.vondi.dbcards.ui.components

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vondi.dbcards.R
import com.vondi.dbcards.data.models.Item
import com.vondi.dbcards.ui.theme.Purple
import com.vondi.dbcards.ui.theme.Red
import java.text.SimpleDateFormat

import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ItemCard(
    item: Item,
    onDelete: (Item) -> Unit,
    onChange: (Item) -> Unit
){
    Card(
        onClick = { /*TODO*/ },
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = item.name,
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy((-10).dp)
                ) {
                    IconButton(onClick = { onChange(item) }) {
                        Icon(
                            imageVector = Icons.Default.Create,
                            contentDescription = stringResource(R.string.icon_button_change_description),
                            tint = Purple
                        )
                    }
                    IconButton(onClick = { onDelete(item) }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = stringResource(R.string.icon_button_delete_description),
                            tint = Red
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            FlowRow(
                verticalArrangement = Arrangement.Center,
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                item.tags.forEach{
                    ProductTag(it)
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row {
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
                            append(stringResource(R.string.in_the_warehouse))
                        }
                        append(if (item.amount == 0) stringResource(R.string.out_of_stock) else "${item.amount}")
                    },
                    fontSize = 14.sp,
                    lineHeight = 20.sp
                )
                Spacer(modifier = Modifier.width(100.dp))
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
                            append(stringResource(R.string.date_add))
                        }
                        append(formatDate(item.time, LocalContext.current))
                    },
                    fontSize = 14.sp,
                    lineHeight = 20.sp
                )
            }

        }
    }
}


@Composable
private fun ProductTag(
    title: String
) {
    Surface(
        modifier = Modifier.padding(vertical = 2.dp),
        border = BorderStroke(0.5.dp, Color.Black),
        shape = RoundedCornerShape(20),
        color = Color.White
    ) {
        Text(
            text = title,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black,
            textAlign = TextAlign.Center,
            fontSize = 14.sp,
            modifier = Modifier.padding(horizontal = 15.dp, vertical = 5.dp)
        )

    }
}

private fun formatDate(timestamp: Int, context: Context): String {

    // Преобразуем в миллисекунды
    val millis = timestamp.toLong() * 1000

    // Используем Calendar для получения даты
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = millis

    // Определяем формат даты
    val dateFormat = SimpleDateFormat(context.getString(R.string.date_pattern), Locale.getDefault())

    // Форматируем дату
    return dateFormat.format(calendar.time)
}