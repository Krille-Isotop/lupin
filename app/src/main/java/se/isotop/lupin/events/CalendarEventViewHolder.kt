package se.isotop.lupin.events

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.View
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import kotlinx.android.synthetic.main.list_item_calendar_event.*
import se.isotop.lupin.ListAdapter

class CalendarEventViewHolder(itemView: View) : ListAdapter.ListViewHolder(itemView) {
    override fun bind(listItem: ListAdapter.ListItem) {
        if (listItem is CalendarListItem) {
            timeView.text = listItem.startTime
            eventTitle.text = listItem.title
            eventSubtitle.text = listItem.subTitle
            locationView.text = listItem.location

            listItem.image?.let {
                Glide.with(itemView.context)
                    .asBitmap()
                    .load(it)
                    .into(object : CustomTarget<Bitmap>(){
                        override fun onLoadCleared(placeholder: Drawable?) {

                        }

                        override fun onResourceReady(
                            resource: Bitmap,
                            transition: Transition<in Bitmap>?
                        ) {
                            val palette = Palette.from(resource).generate()
                            palette.swatches

                            val color = palette.getDominantColor(Color.BLACK)

                            val invertedColor = 0x00FFFFFF.xor(color)

                            timeView.setTextColor(invertedColor)
                            eventTitle.setTextColor(invertedColor)
                            eventSubtitle.setTextColor(invertedColor)
                            locationView.setTextColor(invertedColor)
                        }
                    })

                Glide.with(itemView).load(it).into(cardBackground)
            }
        }
    }
}