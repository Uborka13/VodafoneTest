package hu.ubi.soft.vodafonetest.ui.fragment.data.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import hu.ubi.soft.vodafonetest.R
import hu.ubi.soft.vodafonetest.databinding.FragmentDataDetailsBinding
import hu.ubi.soft.vodafonetest.extensions.onClick
import hu.ubi.soft.vodafonetest.extensions.showIf

class DataPackageDetailsFragment : Fragment() {

    private var _binding: FragmentDataDetailsBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<DataPackageDetailsFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDataDetailsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvDataPackageName.text = args.offer.name
        binding.tvDataPackageDetails.text = args.offer.description
        binding.mbCancel.showIf(args.offer.hasEnd)
        if (args.offer.hasEnd) {
            binding.mbRefill.text =
                getString(R.string.lbl_refill_with, args.offer.price)
            binding.mbCancel.text =
                getString(R.string.lbl_cancel_refill)
        } else {
            binding.mbRefill.text =
                getString(R.string.lbl_buy_with, args.offer.price)
        }
        binding.mbRefill.onClick {
            findNavController().navigate(
                DataPackageDetailsFragmentDirections.actionDataPackageDetailsFragmentToDataValidationFragment(
                    args.offer
                )
            )
        }
        binding.mbCancel.onClick {
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}