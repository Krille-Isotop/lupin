package se.isotop.lupin.events

import com.google.firebase.Timestamp
import com.google.firebase.firestore.GeoPoint

class CalendarEvent {
    var id: String = ""
    var startTime: Timestamp = Timestamp.now()
    var endTime: Timestamp = Timestamp.now()
    var title: String = ""
    var ingress: String = ""
    var body: String = ""
    var image: String? = null
    var location: GeoPoint = EMPTY_LOCATION

    // We probably need another location variable: location name, like "Kitchen"
    override fun toString(): String {
        return "CalendarEvent(id='$id', startTime=$startTime, endTime=$endTime, title='$title', description='$ingress', image='$image', location=$location)"
    }

    companion object {
        val EMPTY_LOCATION = GeoPoint(0.0, 0.0)
    }

}
