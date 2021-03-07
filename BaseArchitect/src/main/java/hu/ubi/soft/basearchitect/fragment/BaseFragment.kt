package hu.ubi.soft.basearchitect.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import hu.ubi.soft.basearchitect.utils.UIActions
import hu.ubi.soft.basearchitect.utils.UIModel
import hu.ubi.soft.basearchitect.utils.UIStates
import hu.ubi.soft.basearchitect.viewmodel.BaseViewModel

abstract class BaseFragment<UM : UIModel, A: UIActions> : Fragment() {

    var onViewDestroyed: (() -> Unit)? = null

    abstract val viewModel: BaseViewModel<UM, A>

    abstract val binding: ViewBinding

    val uiModel: UM
        get() = viewModel.uiModel

    @CallSuper
    @Suppress("UNCHECKED_CAST")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.states.observe(this) {
            when (it) {
                is UIStates.Init -> {
                    onInit()
                    stateDone()
                }
                is UIStates.Loading -> {
                    onLoading(it.action as A)
                }
                is UIStates.Success -> {
                    onSuccess(it.action as A)
                    stateDone()
                }
                is UIStates.Error -> {
                    onError(it.error, it.action as A)
                    stateDone()
                }
                is UIStates.StateDone -> { /* no-op */}
            }
        }
        initObservers()
    }

    @CallSuper
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        modelRestore(viewModel.uiModel)
    }

    @CallSuper
    override fun onDestroyView() {
        saveModel()?.let {
            viewModel.uiModel = it
        }
        onViewDestroyed?.invoke()
        super.onDestroyView()
    }

    private fun stateDone() {
        viewModel.setStateDone()
    }

    /**
     * Best place to init observers, to prevent double onChange call
     */
    open fun initObservers() {
        // no - op
    }

    /**
     * OnClickListeners should be placed here
     */
    open fun initUI() {
        // no - op
    }

    /**
     * It is called when the fragment initialized first time, won't be called on the next
     * onCreateView
     */
    open fun onInit() {

    }

    /**
     * It is called when an async (network/database) call finished and returned with 200 or data
     */
    open fun onSuccess(action: A) { }

    /**
     * It is called when an async call started
     */
    open fun onLoading(action: A) { }

    /**
     * It is called when an async call
     */
    open fun onError(error: String?, action: A) { }

    open fun modelRestore(uiModel: UM) { }

    open fun saveModel(): UM? { return null }

}