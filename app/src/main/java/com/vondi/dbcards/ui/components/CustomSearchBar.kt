package com.vondi.dbcards.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import com.vondi.dbcards.R
import com.vondi.dbcards.domain.event.ItemEvent
import com.vondi.dbcards.ui.viewmodels.ItemViewModel

@Composable
fun CustomSearchBar(
    viewModel: ItemViewModel
) {
    var searchText by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = searchText,
        modifier = Modifier
            .fillMaxWidth(),
        onValueChange = { query ->
            searchText = query
            active = query.isNotEmpty()
            viewModel.onEvent(ItemEvent.UpdateSearchQuery(query))
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = stringResource(R.string.search_icon_description)
            )
        },
        trailingIcon = {
            if (active) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = stringResource(R.string.clear_search_button_description),
                    modifier = Modifier.clickable {
                        searchText = ""
                        active = false
                        viewModel.onEvent(ItemEvent.UpdateSearchQuery(""))
                    }
                )
            }
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            focusedLabelColor = Color.Black
        ),
        label = {
            Text(text = stringResource(R.string.search_items))
        },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search
        ),
        maxLines = 1,
        keyboardActions = KeyboardActions(
            onSearch = {

            }
        )
    )
}