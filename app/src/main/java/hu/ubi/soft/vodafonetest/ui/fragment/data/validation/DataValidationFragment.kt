package hu.ubi.soft.vodafonetest.ui.fragment.data.validation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import hu.ubi.soft.vodafonetest.R
import hu.ubi.soft.vodafonetest.databinding.FragmentDataPackageValidationBinding
import hu.ubi.soft.vodafonetest.extensions.onClick
import kotlin.random.Random

class DataValidationFragment : Fragment() {

    private var _binding: FragmentDataPackageValidationBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<DataValidationFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDataPackageValidationBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvConfirmationMessage.text =
            getString(R.string.lbl_confirmation_message, args.offer.name)
        binding.mbConfirm.onClick {
            if (Random.nextInt(0, 10) % 3 == 0) {
                findNavController().navigate(
                    DataValidationFragmentDirections.actionDataValidationFragmentToConfirmationSuccessfulFragment(
                        args.offer
                    )
                )
            } else {
                findNavController().navigate(
                    DataValidationFragmentDirections.actionDataValidationFragmentToConfirmationUnsuccessfulFragment(
                        args.offer
                    )
                )
            }

        }
        binding.mbNoThanks.onClick {
            findNavController().navigateUp()
        }
    }
}