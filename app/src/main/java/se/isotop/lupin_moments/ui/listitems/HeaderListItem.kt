package se.isotop.lupin_moments.ui.listitems

import se.isotop.lupin_moments.ListAdapter

data class HeaderListItem(
    val title: String
) : ListAdapter.ListItem {
    override val type: Int = ListAdapter.ListItem.TYPE_HEADER
    override val id: String = title
}