package se.isotop.lupin.ui.listitems

import android.view.View
import kotlinx.android.synthetic.main.list_item_header.*
import se.isotop.lupin.ListAdapter

class HeaderViewHolder(itemView: View) : ListAdapter.ListViewHolder(itemView) {

    override fun bind(listItem: ListAdapter.ListItem) {
        if (listItem is HeaderListItem) {
            titleView.text = listItem.title
        }
    }
}