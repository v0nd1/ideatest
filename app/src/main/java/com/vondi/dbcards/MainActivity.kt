package com.vondi.dbcards

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import android.graphics.Color
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vondi.dbcards.data.network.ProductsDb
import com.vondi.dbcards.ui.screens.ProductsScreen
import com.vondi.dbcards.ui.theme.DbCardsTheme
import com.vondi.dbcards.ui.viewmodels.ItemViewModel

class MainActivity : ComponentActivity() {
    private val db by lazy {
        ProductsDb.getDatabase(applicationContext)
    }

    private val viewModelItem by viewModels<ItemViewModel>(factoryProducer = {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return ItemViewModel(db.dao) as T
            }
        }
    })
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            navigationBarStyle = SystemBarStyle.light(
                Color.parseColor(getString(R.string.color_skyblue)),
                Color.parseColor(getString(R.string.color_skyblue))
            )
        )

        setContent {
            val state by viewModelItem.state.collectAsState()
            DbCardsTheme {
                ProductsScreen(
                    state = state,
                    onEvent = viewModelItem::onEvent,
                    viewModel = viewModelItem
                )
            }
        }
    }
}
