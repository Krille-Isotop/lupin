package se.isotop.lupin.events

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class EventsViewModel: ViewModel() {

    private val _all = MutableLiveData<CalendarEvent>()
    val all: LiveData<CalendarEvent> = _all

}