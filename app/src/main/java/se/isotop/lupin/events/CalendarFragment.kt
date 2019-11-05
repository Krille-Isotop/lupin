package se.isotop.lupin.events

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_recyler_with_toolbar.*
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

        return inflater.inflate(R.layout.fragment_recyler_with_toolbar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        collapsingToolbarLayout.title = getString(R.string.calendar_header)

        eventsViewModel.all.observe(viewLifecycleOwner, Observer {  events ->
            adapter.setData(events)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()

        recyclerView.adapter = null
    }
}
