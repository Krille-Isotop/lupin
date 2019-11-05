package se.isotop.lupin.events

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore

class EventsViewModel: ViewModel() {

    private val _all = MutableLiveData<List<CalendarEvent>>()
    val all: LiveData<List<CalendarEvent>> = _all

    private val db = FirebaseFirestore.getInstance()

    init {
        db.collection(COLLECTION_CALENDAR_EVENTS)
            .addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                if (firebaseFirestoreException != null) {
                    Log.w(TAG, firebaseFirestoreException)
                    return@addSnapshotListener
                }

                val list = querySnapshot?.map { document ->
                    document.toObject(CalendarEvent::class.java).apply {
                        id = document.id
                    }
                }

                _all.value = list
            }
    }

    companion object {
        private const val TAG = "EventsViewModel"
        private const val COLLECTION_CALENDAR_EVENTS = "calendar-events"
    }

}