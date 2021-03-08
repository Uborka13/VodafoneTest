package hu.ubi.soft.vodafonetest.ui.fragment.data.confirmation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import hu.ubi.soft.vodafonetest.R
import hu.ubi.soft.vodafonetest.databinding.FragmentDataConfirmationUnsuccessfulBinding
import hu.ubi.soft.vodafonetest.extensions.onClick

class ConfirmationUnsuccessfulFragment : Fragment() {

    private var _binding: FragmentDataConfirmationUnsuccessfulBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<ConfirmationUnsuccessfulFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDataConfirmationUnsuccessfulBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvUnsuccessfulTitle.text =
            getString(R.string.lbl_unsuccessful_message_title, args.offer.name)
        binding.mbGoToDash.onClick {
            findNavController().navigateUp()
        }
        binding.mbPickAnother.onClick {
            findNavController().navigate(
                ConfirmationUnsuccessfulFragmentDirections.actionConfirmationUnsuccessfulFragmentToDashboardFragment(
                    true
                )
            )
        }
    }
}