package hu.ubi.soft.basearchitect.utils

import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.viewbinding.ViewBinding
import hu.ubi.soft.basearchitect.fragment.BaseFragment
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

inline fun <reified VB : ViewBinding> BaseFragment<*, *>.viewBinding(noinline inflater: (layoutInflater: LayoutInflater) -> VB) =
    FragmentViewBindingDelegate(this, inflater)

class FragmentViewBindingDelegate<VB : ViewBinding>(
    private val fragment: BaseFragment<*, *>,
    private val inflater: (layoutInflater: LayoutInflater) -> VB
) : ReadOnlyProperty<Fragment, VB> {

    private var binding: VB? = null

    init {
        fragment.onViewDestroyed = { binding = null }
    }

    @Suppress("UNCHECKED_CAST")
    override fun getValue(thisRef: Fragment, property: KProperty<*>): VB {
        binding?.let { return it }

        val lifecycle = fragment.viewLifecycleOwner.lifecycle
        if (!lifecycle.currentState.isAtLeast(Lifecycle.State.INITIALIZED)) {
            error("Cannot access view bindings. View lifecycle is ${lifecycle.currentState}!")
        }
        val invoke = inflater.invoke(fragment.layoutInflater)
        return invoke.also { this.binding = it }
    }

}