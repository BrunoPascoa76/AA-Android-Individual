package cm.watchlist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cm.watchlist.model.WatchlistViewModel
import cm.watchlist.ui.theme.WatchlistTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import cm.watchlist.model.WatchListItem

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WatchlistTheme {
                val context= LocalContext.current
                val viewModel = viewModel<WatchlistViewModel>()

                LaunchedEffect(Unit) {
                    viewModel.loadFromJson(context)
                }


                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding->
                    WatchListScreen(Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun WatchListScreen(modifier: Modifier = Modifier) {
    val viewModel: WatchlistViewModel = viewModel() // Scoped to the current activity
    val watchListState by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Watchlist") }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = modifier.padding(innerPadding)
        ) {
            items(watchListState.items) { item ->
                WatchListItemView(item) {
                    viewModel.toggleCheckbox(item)
                }
            }
        }
    }
}


@Composable
fun WatchListItemView(item: WatchListItem,onCheckedChange: (Boolean) -> Unit){
    Row(
        modifier=Modifier.fillMaxWidth().clickable {onCheckedChange(!item.isWatched)},
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Checkbox(
            checked = item.isWatched,
            onCheckedChange = onCheckedChange // Update state on checkbox change
        )
        Text(
            text = item.name,
            modifier = Modifier.padding(start = 8.dp) // Space between checkbox and text
        )
    }
}
