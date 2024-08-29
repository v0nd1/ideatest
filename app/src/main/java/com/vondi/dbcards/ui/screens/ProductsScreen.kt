package com.vondi.dbcards.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.vondi.dbcards.R
import com.vondi.dbcards.domain.event.ItemEvent
import com.vondi.dbcards.domain.model.ItemState
import com.vondi.dbcards.ui.components.CustomSearchBar
import com.vondi.dbcards.ui.components.ItemCard
import com.vondi.dbcards.ui.screens.dialogs.ChangeItemDialog
import com.vondi.dbcards.ui.screens.dialogs.DeleteItemDialog
import com.vondi.dbcards.ui.theme.BackWhite
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
                        text = stringResource(R.string.list_items)
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