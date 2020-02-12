package se.isotop.lupin_moments.events

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.View
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import kotlinx.android.synthetic.main.list_item_calendar_event.*
import se.isotop.lupin_moments.ListAdapter

class CalendarEventViewHolder(itemView: View) : ListAdapter.ListViewHolder(itemView) {
    override fun bind(listItem: ListAdapter.ListItem) {
        if (listItem is CalendarListItem) {
            startTimeView.text = listItem.startTime
            eventTitle.text = listItem.title
            eventTime.text = listItem.subTitle

            cardView.setOnClickListener {
                listItem.action()
            }
        }
    }
}