package se.isotop.lupin_moments.events

import com.google.firebase.Timestamp
import java.util.*

class CalendarEvent {
    var id: String = ""
    var startDate: Timestamp = Timestamp.now()
    var endDate: Timestamp = Timestamp.now()
    var title: String = ""
    var description: String = ""
    var imageURL: String = ""

    val startTimeAsString: String
        get() = getHoursAndMinutesFrom(startDate)

    val endTimeAsString: String
        get() = getHoursAndMinutesFrom(endDate)

    private fun getHoursAndMinutesFrom(timestamp: Timestamp): String {
        val calendar = Calendar.getInstance().apply {
            time = timestamp.toDate()
        }

        val hours = calendar.get(Calendar.HOUR_OF_DAY).toString().padStart(2, '0')
        val minutes = calendar.get(Calendar.MINUTE).toString().padStart(2, '0')

        return "$hours.$minutes"
    }

    override fun toString(): String {
        return "CalendarEvent(id='$id', startTime=$startTimeAsString, endTime=$endTimeAsString, title='$title', description='$description')"
    }
}
