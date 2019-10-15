package se.isotop.lupin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import se.isotop.lupin.events.CalendarEventViewHolder

class ListAdapter: RecyclerView.Adapter<ListAdapter.ListViewHolder>() {

    private val items = mutableListOf<ListItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            ListItem.TYPE_CALENDAR_EVENT -> {
                val view = inflater.inflate(R.layout.list_item_calendar_event, parent, false)
                CalendarEventViewHolder(view)
            }
            else -> {
                throw IllegalStateException("Can't create view holder for type $viewType")
            }
        }
    }

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int = items[position].type

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    fun setData(data: List<ListItem>) {
        items.clear()
        items.addAll(data)
        notifyDataSetChanged()
    }

    abstract class ListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), LayoutContainer {

        override val containerView: View?
            get() = itemView

        abstract fun bind(listItem: ListItem)
    }

    interface ListItem {
        val id: Long
        val type: Int

        companion object {
            val TYPE_CALENDAR_EVENT = 0
        }
    }
}

