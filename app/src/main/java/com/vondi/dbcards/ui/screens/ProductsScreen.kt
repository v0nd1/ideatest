package com.vondi.dbcards.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.vondi.dbcards.domain.event.ItemEvent
import com.vondi.dbcards.domain.model.ItemState
import com.vondi.dbcards.ui.components.CustomSearchBar
import com.vondi.dbcards.ui.components.ItemCard
import com.vondi.dbcards.ui.screens.dialogs.ChangeItemDialog
import com.vondi.dbcards.ui.screens.dialogs.DeleteItemDialog
import com.vondi.dbcards.ui.theme.BackWhite
import com.vondi.dbcards.ui.theme.Red
import com.vondi.dbcards.ui.theme.SkyBlue
import com.vondi.dbcards.ui.viewmodels.ItemViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductsScreen(
    viewModel: ItemViewModel,
    state: ItemState,
    onEvent: (ItemEvent) -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Список товаров"
                    )

                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = SkyBlue
                ),

            )
        }
    ) {
        Box(
            modifier = Modifier
                .padding(it)
                .background(BackWhite)
        ){
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())

            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp)
                ) {
                    CustomSearchBar(viewModel = viewModel)
                    state.items.forEach { item ->
                        Spacer(modifier = Modifier.height(20.dp))
                        ItemCard(
                            item = item,
                            onDelete = {
                                onEvent(ItemEvent.ShowDialogDelete(item))

                            },
                            onChange = {
                                onEvent(ItemEvent.ShowDialogChange(item))
                            }
                        )
                    }
                }

            }

        }


        if (state.isDeleteItem) {
            DeleteItemDialog(state = state, onEvent = onEvent)
        }

        if (state.isChangeItem) {
            ChangeItemDialog(state = state, onEvent = onEvent)
        }

    }

}