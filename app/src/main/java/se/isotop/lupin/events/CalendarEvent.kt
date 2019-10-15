package se.isotop.lupin.events

data class CalendarEvent(
    val startTime: Long,
    val endTime: Long,
    val title: String,
    val description: String,
    val image: String
)