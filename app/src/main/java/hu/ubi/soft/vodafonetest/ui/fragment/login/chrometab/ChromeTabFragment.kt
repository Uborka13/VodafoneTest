package hu.ubi.soft.vodafonetest.ui.fragment.login.chrometab

import android.net.Uri
import android.os.Bundle
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs

class ChromeTabFragment : Fragment() {

    private val args by navArgs<ChromeTabFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.intent.setPackage("com.android.chrome");
        customTabsIntent.launchUrl(requireContext(), Uri.parse(args.url))
        findNavController().navigateUp()
    }

}