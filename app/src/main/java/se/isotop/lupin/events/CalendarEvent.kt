package se.isotop.lupin.events

import com.google.firebase.Timestamp
import com.google.firebase.firestore.GeoPoint

class CalendarEvent() {
    var id: String = ""
    var startTime: Timestamp = Timestamp.now()
    var endTime: Timestamp = Timestamp.now()
    var title: String = ""
    var description: String = ""
    var image: String = ""
    var location: GeoPoint = GeoPoint(0.0, 0.0)

    override fun toString(): String {
        return "CalendarEvent(id='$id', startTime=$startTime, endTime=$endTime, title='$title', description='$description', image='$image', location=$location)"
    }

}
