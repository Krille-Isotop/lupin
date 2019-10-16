package se.isotop.lupin.events

import android.view.View
import kotlinx.android.synthetic.main.list_item_calendar_event.*
import se.isotop.lupin.ListAdapter

class CalendarEventViewHolder(itemView: View) : ListAdapter.ListViewHolder(itemView) {
    override fun bind(listItem: ListAdapter.ListItem) {
        if (listItem is CalendarListItem) {
            timeView.text = listItem.startTime
            eventTitle.text = listItem.title
            eventSubtitle.text = listItem.subTitle
            locationView.text = listItem.location
        }
    }
}