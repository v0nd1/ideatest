package com.vondi.dbcards.ui.screens.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vondi.dbcards.R
import com.vondi.dbcards.domain.event.ItemEvent
import com.vondi.dbcards.domain.model.ItemState

@Composable
fun DeleteItemDialog(
    state: ItemState,
    onEvent: (ItemEvent) -> Unit,
    modifier: Modifier = Modifier
){
    AlertDialog(
        modifier = modifier,
        onDismissRequest = {
            onEvent(ItemEvent.HideDialogDelete)
        },
        confirmButton = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Row {
                    TextButton(onClick = { onEvent(ItemEvent.HideDialogDelete) }) {
                        Text(text = stringResource(R.string.no))
                    }

                    Spacer(modifier = Modifier.width(15.dp))

                    TextButton(onClick = { onEvent(ItemEvent.DeleteItem(state.item)) }) {
                        Text(text = stringResource(R.string.yes))
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
                    imageVector = Icons.Rounded.Warning,
                    contentDescription = stringResource(R.string.icon_warning)
                )
                Text(
                    text = stringResource(R.string.delete_product),
                    fontSize = 20.sp
                )
                Text(
                    text = stringResource(R.string.delete_warning),
                    fontSize = 14.sp,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start
                )
            }
        }
    )

}