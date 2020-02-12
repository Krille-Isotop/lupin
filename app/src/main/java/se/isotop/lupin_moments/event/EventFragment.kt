package se.isotop.lupin_moments.event

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import se.isotop.lupin_moments.R
import se.isotop.lupin_moments.events.EventsViewModel

class EventFragment : Fragment(R.layout.fragment_page) {
    private lateinit var eventsViewModel: EventsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        eventsViewModel = ViewModelProviders.of(this).get(EventsViewModel::class.java)
    }
}