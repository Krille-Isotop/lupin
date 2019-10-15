package se.isotop.lupin.events

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_calendar.*
import se.isotop.lupin.ListAdapter
import se.isotop.lupin.R

class CalendarFragment : Fragment() {

    private val adapter = ListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_calendar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        val list = mutableListOf<ListAdapter.ListItem>()
        for (i in 0L..30L) {
            list.add(CalendarListItem(i, "Titel! $i"))
        }
        adapter.setData(list)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        recyclerView.adapter = null
    }
}
