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
                val headerText = group.key.niceDateFormat() ?: ""
                items.add(HeaderListItem(headerText))
                group.value.forEach {
                    val item = CalendarListItem(
                        it.id,
                        it.title,
                        it.description,
                        it.startTimeAsString,
                        it.imageURL
                    ) {
                        _click.value = LiveDataEvent(it.id)
                    }
                    items.add(item)
                }
            }

            items
        }
    }

    fun getEvent(id: String): LiveData<CalendarEvent> = repository.getEvent(id)

    private fun Calendar.niceDateFormat(): String? {

        val today = Calendar.getInstance()

        today.set(Calendar.HOUR_OF_DAY, 0)
        today.set(Calendar.MINUTE, 0)
        today.set(Calendar.SECOND, 0)
        today.set(Calendar.MILLISECOND, 0)

        val tomorrow = Calendar.getInstance()
        tomorrow.time = today.time
        tomorrow.add(Calendar.HOUR_OF_DAY, 24)

        val nextWeek = Calendar.getInstance()
        nextWeek.time = today.time
        nextWeek.add(Calendar.DAY_OF_YEAR, 7)

        val app = getApplication<Application>()
        return when {
            before(today) -> {
                app.getString(R.string.previous)
            }
            before(tomorrow) -> {
                app.getString(R.string.today)
            }
            after(nextWeek) -> {
                DateFormat.getDateInstance(DateFormat.LONG, LOCALE_SWEDISH).format(this.time)
            }
            after(tomorrow) -> {
                getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, LOCALE_SWEDISH)
            }
            else -> {
                app.getString(R.string.tomorrow)
            }
        }
    }

    companion object {
        private const val TAG = "EventsViewModel"
        private val LOCALE_SWEDISH = Locale("sv", "SE")
    }
}