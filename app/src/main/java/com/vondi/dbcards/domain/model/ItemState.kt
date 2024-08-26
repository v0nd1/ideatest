package com.vondi.dbcards.domain.model

import com.vondi.dbcards.data.models.Item

data class ItemState(
    val items: List<Item> = emptyList(),
    val item: Item? = null,
    val isChangeItem: Boolean = false,
    val isDeleteItem: Boolean = false,
)