package com.vondi.dbcards.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vondi.dbcards.data.dao.ItemDao
import com.vondi.dbcards.domain.event.ItemEvent
import com.vondi.dbcards.domain.model.ItemState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ItemViewModel(
    private val dao: ItemDao
) : ViewModel() {

    private val _state = MutableStateFlow(ItemState())

    private val _searchQuery = MutableStateFlow("")
    private val searchQueryFlow = _searchQuery
        .debounce(300)
        .distinctUntilChanged()

    private val _items =
        dao.getItems().stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    // Отфильтрованный список по поисковому запросу
    private val filteredItems = combine(searchQueryFlow, _items) { query, items ->
        if (query.isBlank()) {
            items
        } else {
            items.filter { it.name.lowercase().contains(query.lowercase(), ignoreCase = true) }
        }
    }.flowOn(Dispatchers.Default)

    val state = combine(_state, filteredItems) { state, items ->
        state.copy(
            items = items
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(1000), ItemState())

    fun onEvent(
        event: ItemEvent
    ) {
        when (event) {
            is ItemEvent.DeleteItem -> {
                viewModelScope.launch {
                    event.item?.let { dao.deleteItem(it) }
                }
                onEvent(ItemEvent.HideDialogDelete)
            }

            ItemEvent.HideDialogDelete -> {
                _state.update {
                    it.copy(
                        isDeleteItem = false
                    )
                }
            }
            is ItemEvent.ShowDialogDelete -> {
                _state.update {
                    it.copy(
                        isDeleteItem = true,
                        item = event.item
                    )
                }
            }

            is ItemEvent.ShowDialogChange -> {
                _state.update {
                    it.copy(
                        isChangeItem = true,
                        item = event.item
                    )
                }
            }

            ItemEvent.HideDialogChange -> {
                _state.update {
                    it.copy(
                        isChangeItem = false
                    )
                }
            }

            is ItemEvent.UpdateAmount -> {
                viewModelScope.launch {
                    dao.upsertItem(event.item)
                }
                onEvent(ItemEvent.HideDialogChange)
            }

            is ItemEvent.UpdateSearchQuery -> {
                _searchQuery.value = event.query
            }




        }

    }
}