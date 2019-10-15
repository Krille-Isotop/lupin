package se.isotop.lupin.events

import se.isotop.lupin.ListAdapter
import se.isotop.lupin.ListAdapter.ListItem.Companion.TYPE_CALENDAR_EVENT

data class CalendarListItem(
    override val id: String,
    val title: String
) : ListAdapter.ListItem {
    override val type: Int = TYPE_CALENDAR_EVENT
}