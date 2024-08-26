package com.vondi.dbcards.ui.screens.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.sharp.AddCircleOutline
import androidx.compose.material.icons.sharp.RemoveCircleOutline
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vondi.dbcards.R
import com.vondi.dbcards.domain.event.ItemEvent
import com.vondi.dbcards.domain.model.ItemState

@Composable
fun ChangeItemDialog(
    state: ItemState,
    onEvent: (ItemEvent) -> Unit,
    modifier: Modifier = Modifier
){
    var count by remember { mutableIntStateOf(state.item?.amount ?: 0) }

    AlertDialog(
        modifier = modifier,
        onDismissRequest = {
            onEvent(ItemEvent.HideDialogChange)
        },
        confirmButton = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Row {
                    TextButton(onClick = { onEvent(ItemEvent.HideDialogChange) }) {
                        Text(text = stringResource(R.string.cancel))
                    }
                    Spacer(modifier = Modifier.width(15.dp))
                    TextButton(
                        onClick = {
                            state.item?.let {
                                onEvent(ItemEvent.UpdateAmount(it.copy(amount = count)))
                            }

                        }
                    ) {
                        Text(text = stringResource(R.string.accept))
                    }

                }

            }
        },
        text = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Icon(
                    imageVector = Icons.Rounded.Settings,
                    contentDescription = stringResource(R.string.icon_settings)
                )
                Text(
                    text = stringResource(R.string.amount_of_product),
                    fontSize = 25.sp,
                    color = Color.Black
                )
                Counter(
                    count = count,
                    onIncrement = { count += 1 },
                    onDecrement = { count -= 1 }
                )
            }
        }
    )

}

@Composable
private fun Counter(
    count: Int,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = { if (count > 0) onDecrement() }
        ) {
            Icon(
                imageVector = Icons.Sharp.RemoveCircleOutline,
                contentDescription = stringResource(R.string.icon_button_minus_description),
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(40.dp)
            )
        }
        Spacer(modifier = Modifier.width(20.dp))
        Text(
            text = "$count",
            fontSize = 20.sp,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.width(20.dp))
        IconButton(onClick = onIncrement) {
            Icon(
                imageVector = Icons.Sharp.AddCircleOutline,
                contentDescription = stringResource(R.string.icon_button_plus_description),
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(40.dp)
            )
        }
    }
}