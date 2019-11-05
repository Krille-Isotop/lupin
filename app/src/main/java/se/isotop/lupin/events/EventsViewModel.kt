package se.isotop.lupin.events

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.google.firebase.Timestamp
import se.isotop.lupin.ListAdapter
import se.isotop.lupin.R
import se.isotop.lupin.ui.listitems.HeaderListItem
import java.text.DateFormat
import java.util.Calendar
import java.util.Locale

class EventsViewModel(app: Application) : AndroidViewModel(app) {

    val all: LiveData<List<ListAdapter.ListItem>>

    private val repository = EventRepository()

    init {

        all = Transformations.map(repository.getUpcomingEvents()) { events ->
            val groups = events.groupBy {

                val calendar = Calendar.getInstance()
                calendar.time = it.startTime.toDate()

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
                        getHoursAndMinutesFrom(it.startTime),
                        "12-13",
                        "Plats?!",
                        it.image
                    )
                    items.add(item)
                }
            }

            items
        }
    }

    private fun getHoursAndMinutesFrom(timestamp: Timestamp): String {
        val calendar = Calendar.getInstance().apply {
            time = timestamp.toDate()
        }

        val hours = calendar.get(Calendar.HOUR_OF_DAY).toString().padStart(2, '0')
        val minutes = calendar.get(Calendar.MINUTE).toString().padStart(2, '0')

        return "$hours.$minutes"
    }

    private fun Calendar.niceDateFormat(): String? {

        val today = Calendar.getInstance()

        today.set(Calendar.HOUR_OF_DAY, 0)
        today.set(Calendar.MINUTE, 0)
        today.set(Calendar.SECOND, 0)
        today.set(Calendar.MILLISECOND, 0)

        val tomorrow = Calendar.getInstance()
        tomorrow.time = today.time
        tomorrow.add(Calendar.HOUR_OF_DAY, 24)

        val nextWeek= Calendar.getInstance()
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
                // i övermorgon ++
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