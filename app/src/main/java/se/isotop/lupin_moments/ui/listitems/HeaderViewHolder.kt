package se.isotop.lupin_moments.ui.listitems

import android.view.View
import kotlinx.android.synthetic.main.list_item_header.*
import se.isotop.lupin_moments.ListAdapter

class HeaderViewHolder(itemView: View) : ListAdapter.ListViewHolder(itemView) {

    override fun bind(listItem: ListAdapter.ListItem) {
        if (listItem is HeaderListItem) {
            titleView.text = listItem.title
        }
    }
}