package se.isotop.lupin.page

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_page.*
import se.isotop.lupin.R
import se.isotop.lupin.events.EventsViewModel

class PageFragment : Fragment(R.layout.fragment_page) {

    private val args: PageFragmentArgs by navArgs()
    private lateinit var eventsViewModel: EventsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        eventsViewModel = ViewModelProviders.of(this).get(EventsViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = args.id
        eventsViewModel.getEvent(id).observe(viewLifecycleOwner, Observer { event ->

            toolbarTitle.text = event.title

            val cardColor = ContextCompat.getColor(requireContext(), R.color.pink)
            calendarCard.setBackgroundColor(cardColor)

            val cardTextColor = ContextCompat.getColor(requireContext(), R.color.white)
            val startTimeText = calendarCard.findViewById<TextView>(R.id.startTimeView)
            startTimeText.text = event.startTimeAsString
            startTimeText.setTextColor(cardTextColor)

            val titleText = calendarCard.findViewById<TextView>(R.id.eventTitle)
            titleText.text = event.title
            titleText.setTextColor(cardTextColor)

            val timeText = calendarCard.findViewById<TextView>(R.id.eventTime)
            timeText.text = "${event.startTimeAsString} - ${event.endTimeAsString}"
            timeText.setTextColor(cardTextColor)

            val locationText = calendarCard.findViewById<TextView>(R.id.eventLocation)
            locationText.text = event.distanceFromIsotop
            locationText.setTextColor(cardTextColor)

            val arrow = calendarCard.findViewById<ImageView>(R.id.arrowView)
            arrow.isGone = true

            preamble.text = event.ingress
            Glide.with(this).load(event.image).into(image)
            body.text = event.body
        })
    }

}