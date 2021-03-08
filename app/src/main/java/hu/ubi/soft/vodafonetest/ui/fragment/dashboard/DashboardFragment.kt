package hu.ubi.soft.vodafonetest.ui.fragment.dashboard

import android.annotation.SuppressLint
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import hu.ubi.soft.basearchitect.fragment.BaseFragment
import hu.ubi.soft.basearchitect.utils.UIModel
import hu.ubi.soft.basearchitect.utils.viewBinding
import hu.ubi.soft.vodafonetest.R
import hu.ubi.soft.vodafonetest.databinding.FragmentDashboardBinding
import hu.ubi.soft.vodafonetest.extensions.hide
import hu.ubi.soft.vodafonetest.extensions.onClick
import hu.ubi.soft.vodafonetest.extensions.show
import hu.ubi.soft.vodafonetest.model.NetOffersResponse
import hu.ubi.soft.vodafonetest.ui.fragment.dashboard.listadapter.RefillDataItem
import hu.ubi.soft.vodafonetest.ui.fragment.dashboard.listadapter.RefillOfferListAdapter
import java.io.Serializable

@AndroidEntryPoint
class DashboardFragment : BaseFragment<DashboardUIModel, DashboardActions>() {
    override val viewModel by viewModels<DashboardViewModel>()
    override val binding by viewBinding(FragmentDashboardBinding::inflate)

    private val args by navArgs<DashboardFragmentArgs>()

    private val listAdapter = RefillOfferListAdapter(RefillOfferListAdapter.RefillDiffUtil)

    @SuppressLint("ClickableViewAccessibility")
    override fun initUI() {
        binding.rvRefillOffers.adapter = listAdapter
        binding.rvRefillOffers.layoutManager = LinearLayoutManager(context)
        listAdapter.listener = object : RefillOfferListAdapter.OnItemSelectedListener {
            override fun onSelected(item: RefillOffers) {
                findNavController().navigate(
                    DashboardFragmentDirections.actionDashboardFragmentToDataPackageDetailsFragment(
                        item
                    )
                )
            }
        }
        if (arguments != null && args.pickAnother) {
            binding.rvRefillOffers.show()
            binding.rvRefillOffers.requestFocus()
            requireArguments().clear()
        }
        binding.fabRefill.onClick {
            binding.rvRefillOffers.show()
        }
        binding.sbUsage.setOnTouchListener { _, _ ->
            true
        }
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.option_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.sOptions.adapter = adapter
        }
        binding.sOptions.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (id == 1L) {
                    viewModel.addAction(Logout)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                /* no - op */
            }

        }
        if (uiModel.refillOffers.isEmpty()) viewModel.addAction(InitFragment)
        if (uiModel.userName.isBlank()) viewModel.addAction(GetUsername)
        binding.tvWelcomeMessage.text =
            getString(R.string.lbl_welcome_message_text, uiModel.userName)
    }

    override fun onLoading(action: DashboardActions) {
        binding.contentGroup.hide()
        binding.progressBar.show()
    }

    override fun onSuccess(action: DashboardActions) {
        when (action) {
            GetNetOffers,
            InitFragment -> {
                handleNetOffer()
            }
            Logout -> {
                findNavController().navigate(DashboardFragmentDirections.actionDashboardFragmentToLoginFragment())
            }
            else -> { /* no-op */
            }
        }
    }

    private fun handleNetOffer() {
        binding.contentGroup.show()
        binding.progressBar.hide()
        binding.tvActualUsage.text = "${uiModel.sharedDataUsage / 1024} GB"
        binding.tvTotalUsage.text = "${uiModel.sharedDataTotal / 1024} GB"
        val percent =
            ((uiModel.sharedDataUsage.toDouble() / uiModel.sharedDataTotal.toDouble()) * 100)
        binding.sbUsage.progress = percent.toInt()
        val result = uiModel.refillOffers.groupBy { it.hasEnd }
        val firstList = result[false]?.filterNotNull()
        val secondList = result[true]
        val finalList = mutableListOf<RefillDataItem>()
        if (firstList?.isNotEmpty() == true) {
            finalList.add(RefillDataItem.Header(getString(R.string.lbl_refill_offers_one_time)))
            finalList.addAll(firstList.map { RefillDataItem.RefillItem(it) })
        }
        if (secondList?.isNotEmpty() == true) {
            finalList.add(RefillDataItem.Header(getString(R.string.lbl_refill_offers_renewable)))
            finalList.addAll(secondList.map { RefillDataItem.RefillItem(it) })
        }
        listAdapter.submitList(finalList)
    }

    override fun modelRestore(uiModel: DashboardUIModel) {
        handleNetOffer()
        binding.tvWelcomeMessage.text =
            getString(R.string.lbl_welcome_message_text, uiModel.userName)
    }


}

data class DashboardUIModel(
    var userName: String = "",
    val sharedDataUsage: Int = 0,
    val sharedDataTotal: Int = 0,
    val refillOffers: List<RefillOffers> = emptyList()
) : UIModel {
    companion object {
        fun fromNetOfferResponse(response: NetOffersResponse): DashboardUIModel {
            val offersList = mutableListOf<RefillOffers>()
            response.postpaid?.refills?.forEach {
                offersList.add(
                    RefillOffers(
                        id = it?.id ?: "",
                        name = it?.name ?: "",
                        description = it?.description ?: "",
                        price = it?.price ?: 0,
                        hasEnd = it?.psmCodes?.deactivation?.isNotBlank() == true
                    )
                )
            }
            return DashboardUIModel(
                sharedDataUsage = response.postpaid?.cumulativeActualSharedDataUsage ?: 0,
                sharedDataTotal = response.postpaid?.cumulativeTotalSharedData ?: 0,
                refillOffers = offersList
            )
        }
    }
}

data class RefillOffers(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val price: Int = 0,
    val hasEnd: Boolean = false
) : Serializable