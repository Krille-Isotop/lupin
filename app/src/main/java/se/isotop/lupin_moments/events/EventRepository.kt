package se.isotop.lupin_moments.events

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Calendar

class EventRepository {

    private val db = FirebaseFirestore.getInstance()

    fun getAllEvents(): LiveData<List<CalendarEvent>> {
        val liveData = MutableLiveData<List<CalendarEvent>>()

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

                liveData.value = list
            }

        return liveData
    }

    fun getEvent(id: String) : LiveData<CalendarEvent> {
        val liveData = MutableLiveData<CalendarEvent>()

        db.collection(COLLECTION_CALENDAR_EVENTS)
            .document(id)
            .addSnapshotListener { documentSnapshot, firebaseFirestoreException ->
                if (firebaseFirestoreException != null) {
                    Log.w(TAG, firebaseFirestoreException)
                    return@addSnapshotListener
                }

                val foo = documentSnapshot?.toObject(CalendarEvent::class.java)

                foo?.let { it.id = id }

                liveData.value = foo

            }

        return liveData
    }

    companion object {
        private val TAG = EventRepository::class.java.simpleName

        private const val COLLECTION_CALENDAR_EVENTS = "events"
        private const val FIELD_START_TIME = "startTime"
    }

}