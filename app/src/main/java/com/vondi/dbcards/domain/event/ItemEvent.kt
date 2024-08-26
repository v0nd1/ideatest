package com.vondi.dbcards.domain.event

import com.vondi.dbcards.data.models.Item


sealed interface ItemEvent {

    data class ShowDialogDelete(val item: Item) : ItemEvent
    data object HideDialogDelete : ItemEvent

    data class ShowDialogChange(val item: Item) : ItemEvent
    data object HideDialogChange : ItemEvent

    data class UpdateAmount(val item: Item) : ItemEvent
    data class DeleteItem(val item: Item?) : ItemEvent

    data class UpdateSearchQuery(val query: String) : ItemEvent

}