package se.isotop.lupin_moments.events

import se.isotop.lupin_moments.ListAdapter
import se.isotop.lupin_moments.ListAdapter.ListItem.Companion.TYPE_CALENDAR_EVENT

data class CalendarListItem(
    override val id: String,
    val title: String,
    val subTitle: String,
    val startTime: String,
    val action: () -> Unit
) : ListAdapter.ListItem {
    override val type: Int = TYPE_CALENDAR_EVENT
}