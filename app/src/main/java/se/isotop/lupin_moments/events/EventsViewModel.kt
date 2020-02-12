package se.isotop.lupin_moments.events

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import se.isotop.lupin_moments.ListAdapter
import se.isotop.lupin_moments.LiveDataEvent
import se.isotop.lupin_moments.R
import se.isotop.lupin_moments.ui.listitems.HeaderListItem
import java.text.DateFormat
import java.util.*

class EventsViewModel(app: Application) : AndroidViewModel(app) {

    val all: LiveData<List<ListAdapter.ListItem>>
    private val _click: MutableLiveData<LiveDataEvent<String>> = MutableLiveData()
    val click: LiveData<LiveDataEvent<String>> = _click

    private val repository = EventRepository()

    init {
        all = Transformations.map(repository.getAllEvents()) { events ->
            val groups = events.groupBy {

                val calendar = Calendar.getInstance()
                calendar.time = it.startDate.toDate()

                calendar.set(Calendar.HOUR_OF_DAY, 0)
                calendar.set(Calendar.MINUTE, 0)
                calendar.set(Calendar.SECOND, 0)
                calendar.set(Calendar.MILLISECOND, 0)

                calendar
            }

            val items = mutableListOf<ListAdapter.ListItem>()
            groups.forEach { group ->
                val headerText = ""
                items.add(HeaderListItem(headerText))
                group.value.forEach {
                    val item = CalendarListItem(
                        it.id,
                        it.title,
                        it.description,
                        it.startTimeAsString
                    ) {
                        _click.value = LiveDataEvent(it.id)
                    }
                    items.add(item)
                }
            }

            items
        }
    }
}