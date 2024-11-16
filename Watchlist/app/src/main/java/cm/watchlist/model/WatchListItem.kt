package cm.watchlist.model

data class WatchListItem(
    val id: Int,
    val name: String,
    val isWatched: Boolean
){
    fun toggle() = this.copy(isWatched = !isWatched)
}