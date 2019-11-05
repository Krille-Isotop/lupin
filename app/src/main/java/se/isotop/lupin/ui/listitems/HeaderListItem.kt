package se.isotop.lupin.ui.listitems

import se.isotop.lupin.ListAdapter

data class HeaderListItem(
    val title: String
) : ListAdapter.ListItem {
    override val type: Int = ListAdapter.ListItem.TYPE_HEADER
    override val id: String = title
}