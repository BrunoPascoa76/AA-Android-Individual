package cm.watchlist.model

data class WatchListState(
    val items: List<WatchListItem> = emptyList(),
    var isLoaded: Boolean=false
)