/*
 * Copyright (c) 2021 New Vector Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package im.vector.app.features.onboarding.ftueauth

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import im.vector.app.R
import im.vector.app.core.extensions.incrementByOneAndWrap
import im.vector.app.core.extensions.setCurrentItem
import im.vector.app.core.resources.BuildMeta
import im.vector.app.databinding.FragmentFtueSplashCarouselBinding
import im.vector.app.features.VectorFeatures
import im.vector.app.features.onboarding.OnboardingAction
import im.vector.app.features.onboarding.OnboardingFlow
import im.vector.app.features.settings.VectorPreferences
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val CAROUSEL_ROTATION_DELAY_MS = 5000L
private const val CAROUSEL_TRANSITION_TIME_MS = 500L

@AndroidEntryPoint
class FtueAuthSplashCarouselFragment :
        AbstractFtueAuthFragment<FragmentFtueSplashCarouselBinding>() {

    @Inject lateinit var vectorPreferences: VectorPreferences
    @Inject lateinit var vectorFeatures: VectorFeatures
    @Inject lateinit var carouselController: SplashCarouselController
    @Inject lateinit var carouselStateFactory: SplashCarouselStateFactory
    @Inject lateinit var buildMeta: BuildMeta

    private var tabLayoutMediator: TabLayoutMediator? = null

    override fun getBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentFtueSplashCarouselBinding {
        return FragmentFtueSplashCarouselBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        val termsUrl = "https://holedo.im/tos"
        val privacyPolicyUrl = "https://holedo.im/privacy"
        val textView = view.findViewById<TextView>(R.id.termtextline)
        textView.movementMethod = LinkMovementMethod.getInstance()


        val text = "By clicking Create Account, you agree to our Terms. Learn how we collect, use and share your data in our Privacy Policy."
        val terms = "Terms"
        val privacy = "Privacy Policy"
        val termsIndex = text.indexOf(terms)
        val privacyIndex = text.indexOf(privacy)
        val spannableString = SpannableString(text)
        val clickableSpanTerms = object : ClickableSpan() {
            override fun onClick(widget: View) {
                val webView = requireView().findViewById<WebView>(R.id.webView)
                webView.visibility = View.VISIBLE
                webView.loadUrl(termsUrl)
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.color = Color.parseColor("#32a3fd")
                ds.isUnderlineText = true
            }
        }


        val clickableSpanPrivacy = object : ClickableSpan() {
            override fun onClick(widget: View) {

                val webView = requireView().findViewById<WebView>(R.id.webView)
                        webView.visibility = View.VISIBLE
                             webView.loadUrl(privacyPolicyUrl)

                //                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(privacyPolicyUrl))
//                requireActivity().startActivity(browserIntent)
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.color = Color.parseColor("#32a3fd")
                ds.isUnderlineText = true
            }
        }
        if (termsIndex != -1) {
            spannableString.setSpan(clickableSpanTerms, termsIndex, termsIndex + terms.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        if (privacyIndex != -1) {
            spannableString.setSpan(clickableSpanPrivacy, privacyIndex, privacyIndex + privacy.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        textView.text = spannableString
        textView.movementMethod = LinkMovementMethod.getInstance()


/*
        textView.setOnClickListener { textview ->
            when ((textview as TextView).text.toString()) {
                "Terms" -> {
                    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(termsUrl))
                    requireActivity().startActivity(browserIntent)

//                    val webView = requireView().findViewById<WebView>(R.id.webView)
//                    webView.visibility = View.VISIBLE
//                    webView.loadUrl(termsUrl)
                }
                "Privacy Policy" -> {
                    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(privacyPolicyUrl))
                    requireActivity().startActivity(browserIntent)
//                    val webView = requireView().findViewById<WebView>(R.id.webView)
//                    webView.visibility = View.VISIBLE
//                    webView.loadUrl(privacyPolicyUrl)
                }
            }
        }

 */
    }

    override fun onDestroyView() {
        tabLayoutMediator?.detach()
        tabLayoutMediator = null
        views.splashCarousel.adapter = null
        super.onDestroyView()
    }

    private fun setupViews() {
        val carouselAdapter = carouselController.adapter
        views.splashCarousel.adapter = carouselAdapter
        tabLayoutMediator = TabLayoutMediator(views.carouselIndicator, views.splashCarousel) { _, _ -> }
                .also { it.attach() }

        carouselController.setData(carouselStateFactory.create())

        val isAlreadyHaveAccountEnabled = vectorFeatures.isOnboardingAlreadyHaveAccountSplashEnabled()
        views.loginSplashSubmit.apply {
            setText(if (isAlreadyHaveAccountEnabled) R.string.login_splash_create_account else R.string.login_splash_submit)

        }
        views.loginSplashAlreadyHaveAccount.apply {
            isVisible = isAlreadyHaveAccountEnabled
            debouncedClicks { alreadyHaveAnAccount() }
        }

        // checking check box mandatory
        val checkbox: CheckBox = requireView().findViewById(R.id.terms_and_conditions_checkbox2)
        val createAccountButton: Button = requireView().findViewById(R.id.loginSplashSubmit)
        createAccountButton.setOnClickListener {
            if (!checkbox.isChecked) {
                Toast.makeText(requireContext(), "Please mark the checkbox before proceeding", Toast.LENGTH_SHORT).show()
            } else {
                // code to proceed with create account
                 splashSubmit(isAlreadyHaveAccountEnabled)

            }
        }

//        views.ll2.setOnClickListener {
//            openHoledoTerms()
//
//        }

//        views.termsClick1.setOnClickListener {
//            openHoledoTerms()
//        }

        if (buildMeta.isDebug || vectorPreferences.developerMode()) {
            views.loginSplashVersion.isVisible = false
            @SuppressLint("SetTextI18n")
            views.loginSplashVersion.text = "Version : ${buildMeta.versionName}\n" +
                    "Branch: ${buildMeta.gitBranchName} ${buildMeta.gitRevision}"
            views.loginSplashVersion.debouncedClicks { navigator.openDebug(requireContext()) }
        }
        views.splashCarousel.registerAutomaticUntilInteractionTransitions()
    }

    private fun ViewPager2.registerAutomaticUntilInteractionTransitions() {
        var scheduledTransition: Job? = null
        val pageChangingCallback = object : ViewPager2.OnPageChangeCallback() {
            private var hasUserManuallyInteractedWithCarousel: Boolean = false

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                hasUserManuallyInteractedWithCarousel = !isFakeDragging
            }

            override fun onPageSelected(position: Int) {
                scheduledTransition?.cancel()
                // only schedule automatic transitions whilst the user has not interacted with the carousel
                if (!hasUserManuallyInteractedWithCarousel) {
                    scheduledTransition = scheduleCarouselTransition()
                }
            }
        }
        viewLifecycleOwner.lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onCreate(owner: LifecycleOwner) {
                registerOnPageChangeCallback(pageChangingCallback)
            }

            override fun onDestroy(owner: LifecycleOwner) {
                unregisterOnPageChangeCallback(pageChangingCallback)
            }
        })
    }

    private fun ViewPager2.scheduleCarouselTransition(): Job {
        val itemCount = adapter?.itemCount ?: throw IllegalStateException("An adapter must be set")
        return viewLifecycleOwner.lifecycleScope.launch {
            delay(CAROUSEL_ROTATION_DELAY_MS)
            setCurrentItem(currentItem.incrementByOneAndWrap(max = itemCount - 1), duration = CAROUSEL_TRANSITION_TIME_MS)
        }
    }

    private fun splashSubmit(isAlreadyHaveAccountEnabled: Boolean) {
        val getStartedFlow = if (isAlreadyHaveAccountEnabled) OnboardingFlow.SignUp else OnboardingFlow.SignInSignUp
        viewModel.handle(OnboardingAction.SplashAction.OnGetStarted(onboardingFlow = getStartedFlow))
    }

    private fun alreadyHaveAnAccount() {
        viewModel.handle(OnboardingAction.SplashAction.OnIAlreadyHaveAnAccount(onboardingFlow = OnboardingFlow.SignIn))
    }

    override fun resetViewModel() {
        // Nothing to do
    }

//    private fun openHoledoTerms() {
//
//        //vectorwebview will only work for logged in client so use webview
//
////        val urlholedo = "https://holedo.im/tos"
////        val intent  = VectorWebViewActivity.getIntent(requireContext(), urlholedo)
////        startActivity(intent)
//
//        val webView = requireView().findViewById<WebView>(R.id.webView)
//        webView.visibility = View.VISIBLE
//        webView.loadUrl("https://holedo.im/tos")
//
//    }

}
