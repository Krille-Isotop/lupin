package se.isotop.lupin.events

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.Timestamp
import kotlinx.android.synthetic.main.fragment_calendar.*
import se.isotop.lupin.ListAdapter
import se.isotop.lupin.R

class CalendarFragment : Fragment() {

    private val adapter = ListAdapter()
    private lateinit var eventsViewModel: EventsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        eventsViewModel = ViewModelProviders.of(this).get(EventsViewModel::class.java)

        return inflater.inflate(R.layout.fragment_calendar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        eventsViewModel.all.observe(viewLifecycleOwner, Observer {  events ->
            val data = events.map {
                CalendarListItem(
                    it.id,
                    it.title,
                    it.description,
                    getHoursAndMinutesFrom(it.startTime),
                    "12-13",
                    "Plats?!"
                )
            }

            adapter.setData(data)
        })
    }

    private fun getHoursAndMinutesFrom(timestamp: Timestamp): String {
        val date = timestamp.toDate()
        return "${date.hours}.${date.minutes}"
    }

    override fun onDestroyView() {
        super.onDestroyView()

        recyclerView.adapter = null
    }
}
