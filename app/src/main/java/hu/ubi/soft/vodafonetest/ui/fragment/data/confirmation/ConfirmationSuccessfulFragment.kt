package hu.ubi.soft.vodafonetest.ui.fragment.data.confirmation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import hu.ubi.soft.vodafonetest.R
import hu.ubi.soft.vodafonetest.databinding.FragmentDataConfirmationSuccessfulBinding
import hu.ubi.soft.vodafonetest.extensions.onClick

class ConfirmationSuccessfulFragment : Fragment() {

    private var _binding: FragmentDataConfirmationSuccessfulBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<ConfirmationSuccessfulFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDataConfirmationSuccessfulBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvSuccessfulTitle.text =
            getString(R.string.lbl_successful_message_title, args.offer.name)
        binding.tvSuccessfulMessage.text =
            getString(R.string.lbl_successful_message_body, args.offer.name)
        binding.mbGoToDash.onClick {
            findNavController().navigateUp()
        }
    }

}