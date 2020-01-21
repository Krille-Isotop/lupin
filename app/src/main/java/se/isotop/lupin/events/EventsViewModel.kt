package se.isotop.lupin.events

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.google.firebase.Timestamp
import com.google.firebase.firestore.GeoPoint
import se.isotop.lupin.ListAdapter
import se.isotop.lupin.LiveDataEvent
import se.isotop.lupin.R
import se.isotop.lupin.ui.listitems.HeaderListItem
import java.text.DateFormat
import java.util.*
import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.round
import kotlin.math.sin

class EventsViewModel(app: Application) : AndroidViewModel(app) {

    val all: LiveData<List<ListAdapter.ListItem>>
    private val _click: MutableLiveData<LiveDataEvent<String>> = MutableLiveData()
    val click: LiveData<LiveDataEvent<String>> = _click
    
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
                        "${getHoursAndMinutesFrom(it.startTime)} - ${getHoursAndMinutesFrom(it.endTime)}",
                        getHoursAndMinutesFrom(it.startTime),
                        distanceFromIsotop(it.location),
                        it.image
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
                // i övermorgon ++
                getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, LOCALE_SWEDISH)
            }
            else -> {
                app.getString(R.string.tomorrow)
            }
        }
    }

    private fun distanceFromIsotop(geoPoint: GeoPoint): String {
        return if (geoPoint == CalendarEvent.EMPTY_LOCATION) {
            "Plats?!"
        } else {
            "${round(
                distance(
                    geoPoint.latitude,
                    geoPoint.longitude,
                    ISOTOP_LAT,
                    ISOTOP_LON
                )
            )}  km från Isotop"
        }
    }

    // Source:
    // https://stackoverflow.com/questions/6981916/how-to-calculate-distance-between-two-locations-using-their-longitude-and-latitu

    private fun distance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val theta = lon1 - lon2
        var dist =
            sin(deg2rad(lat1)) * sin(deg2rad(lat2)) + cos(deg2rad(lat1)) * cos(deg2rad(lat2)) * cos(
                deg2rad(theta)
            )
        dist = acos(dist)
        dist = rad2deg(dist)
        dist *= 60 * 1.1515
        return (dist)
    }

    private fun deg2rad(deg: Double): Double = (deg * Math.PI / 180.0)


    private fun rad2deg(rad: Double): Double = (rad * 180.0 / Math.PI)


    companion object {
        private const val TAG = "EventsViewModel"
        private val LOCALE_SWEDISH = Locale("sv", "SE")
        private const val ISOTOP_LAT = 59.3368266
        private const val ISOTOP_LON = 18.0692704
    }
}