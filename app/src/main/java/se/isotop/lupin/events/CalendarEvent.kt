package se.isotop.lupin.events

import com.google.firebase.Timestamp
import com.google.firebase.firestore.GeoPoint
import java.util.*
import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.round
import kotlin.math.sin

class CalendarEvent {
    var id: String = ""
    var startTime: Timestamp = Timestamp.now()
    var endTime: Timestamp = Timestamp.now()
    var title: String = ""
    var ingress: String = ""
    var body: String = ""
    var image: String? = null
    var location: GeoPoint = EMPTY_LOCATION

    val startTimeAsString: String
        get() = getHoursAndMinutesFrom(startTime)

    val endTimeAsString: String
        get() = getHoursAndMinutesFrom(endTime)

    val distanceFromIsotop: String
        get() = distanceFromIsotop()

    private fun getHoursAndMinutesFrom(timestamp: Timestamp): String {
        val calendar = Calendar.getInstance().apply {
            time = timestamp.toDate()
        }

        val hours = calendar.get(Calendar.HOUR_OF_DAY).toString().padStart(2, '0')
        val minutes = calendar.get(Calendar.MINUTE).toString().padStart(2, '0')

        return "$hours.$minutes"
    }

    fun distanceFromIsotop(): String {
        return if (location == CalendarEvent.EMPTY_LOCATION) {
            "Plats?!"
        } else {
            "${round(
                distance(
                    location.latitude,
                    location.longitude,
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


    // We probably need another location variable: location name, like "Kitchen"
    override fun toString(): String {
        return "CalendarEvent(id='$id', startTime=$startTime, endTime=$endTime, title='$title', description='$ingress', image='$image', location=$location)"
    }

    companion object {
        val EMPTY_LOCATION = GeoPoint(0.0, 0.0)
        private const val ISOTOP_LAT = 59.3368266
        private const val ISOTOP_LON = 18.0692704
    }

}
