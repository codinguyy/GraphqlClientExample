package app.codinguyy.graphqlclientexample

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import app.codinguyy.graphqlclientexample.databinding.FragmentFirstBinding
import com.apollographql.apollo3.ApolloClient
import com.codinguyy.graphqlclientexample.GetPeopleQuery
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        val apolloClient = ApolloClient.Builder()
            .httpExposeErrorBody(true)
            .serverUrl("http://10.0.2.2:8080".plus("/graphql"))
            .build()
        CoroutineScope(Dispatchers.IO).launch {
            val data = apolloClient.query(GetPeopleQuery()).execute()
            Log.i("data", data.data.toString())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
