package se.isotop.lupin.page

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_page.*
import kotlinx.android.synthetic.main.fragment_recyler_with_toolbar.collapsingToolbarLayout
import se.isotop.lupin.R
import se.isotop.lupin.events.EventsViewModel

class PageFragment : Fragment(R.layout.fragment_page) {

    val args: PageFragmentArgs by navArgs()

    private lateinit var eventsViewModel: EventsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        eventsViewModel = ViewModelProviders.of(this).get(EventsViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = args.id
        eventsViewModel.getEvent(id).observe(viewLifecycleOwner, Observer { event ->
            collapsingToolbarLayout.title = event.title
            preamble.text = event.ingress
            Glide.with(this).load(event.image).into(image)
            body.text = event.body
        })
    }

}