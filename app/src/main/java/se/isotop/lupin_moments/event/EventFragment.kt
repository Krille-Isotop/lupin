package se.isotop.lupin_moments.event

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_page.*
import se.isotop.lupin_moments.R
import se.isotop.lupin_moments.events.EventsViewModel

class EventFragment : Fragment(R.layout.fragment_page) {
    private lateinit var eventsViewModel: EventsViewModel
    private val args: EventFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        eventsViewModel = ViewModelProviders.of(this).get(EventsViewModel::class.java)

        eventsViewModel.getEvent(args.id).observe(this, Observer {
            preamble.text = it.title
            Glide.with(this).load(it.imageURL).into(image)
            body.text = it.description
        })
    }
}