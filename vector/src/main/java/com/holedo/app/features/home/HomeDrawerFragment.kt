/*
 * Copyright 2019 New Vector Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.holedo.app.features.home

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.isVisible
import com.holedo.app.BuildConfig
import com.holedo.app.R
import com.holedo.app.core.extensions.observeK
import com.holedo.app.core.extensions.replaceChildFragment
import com.holedo.app.core.platform.VectorBaseFragment
import com.holedo.app.core.utils.startSharePlainTextIntent
import com.holedo.app.databinding.FragmentHomeDrawerBinding
import com.holedo.app.features.analytics.plan.MobileScreen
import com.holedo.app.features.settings.VectorPreferences
import com.holedo.app.features.settings.VectorSettingsActivity
import com.holedo.app.features.spaces.SpaceListFragment
import com.holedo.app.features.usercode.UserCodeActivity
import com.holedo.app.features.webview.VectorWebViewActivity
import com.holedo.app.features.workers.signout.SignOutUiWorker
import org.matrix.android.sdk.api.session.Session
import org.matrix.android.sdk.api.util.toMatrixItem
import javax.inject.Inject

class HomeDrawerFragment @Inject constructor(
        private val session: Session,
        private val vectorPreferences: VectorPreferences,
        private val avatarRenderer: AvatarRenderer
) : VectorBaseFragment<FragmentHomeDrawerBinding>() {

    private lateinit var sharedActionViewModel: HomeSharedActionViewModel

    override fun getBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentHomeDrawerBinding {
        return FragmentHomeDrawerBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedActionViewModel = activityViewModelProvider.get(HomeSharedActionViewModel::class.java)

        if (savedInstanceState == null) {
            replaceChildFragment(R.id.homeDrawerGroupListContainer, SpaceListFragment::class.java)
        }
        session.userService().getUserLive(session.myUserId).observeK(viewLifecycleOwner) { optionalUser ->
            val user = optionalUser?.getOrNull()
            if (user != null) {
                avatarRenderer.render(user.toMatrixItem(), views.homeDrawerHeaderAvatarView)
                views.homeDrawerUsernameView.text = user.displayName
                views.homeDrawerUserIdView.text = user.userId
            }
        }
        // Profile
        views.homeDrawerHeader.debouncedClicks {
            sharedActionViewModel.post(HomeActivitySharedAction.CloseDrawer)
            navigator.openSettings(requireActivity(), directAccess = VectorSettingsActivity.EXTRA_DIRECT_ACCESS_GENERAL)
        }
        // Settings
        views.homeDrawerHeaderSettingsView.debouncedClicks {
            sharedActionViewModel.post(HomeActivitySharedAction.CloseDrawer)
            navigator.openSettings(requireActivity())
        }
        // Sign out
        views.homeDrawerHeaderSignoutView.debouncedClicks {
            sharedActionViewModel.post(HomeActivitySharedAction.CloseDrawer)
            SignOutUiWorker(requireActivity()).perform()
        }

        views.homeDrawerQRCodeButton.debouncedClicks {
            UserCodeActivity.newIntent(requireContext(), sharedActionViewModel.session.myUserId).let {
                val options =
                        ActivityOptionsCompat.makeSceneTransitionAnimation(
                                requireActivity(),
                                views.homeDrawerHeaderAvatarView,
                                ViewCompat.getTransitionName(views.homeDrawerHeaderAvatarView) ?: ""
                        )
                startActivity(it, options.toBundle())
            }
        }




        views.community1.setOnClickListener {

            val calci12 = sharedActionViewModel.session.myUserId
            val calci13 = calci12.replace(":holedo.com", "")
            val calci14 = calci13.replace("@", "")


//            myWebView.loadUrl("https://community.holedo.im/?u=$calci14")

            val  urll13 = "https://community.holedo.im/?u=$calci14"
//            Toast.makeText(requireContext(), urll13, Toast.LENGTH_LONG).show()
            val intentaa  = VectorWebViewActivity.getIntent(requireContext(), urll13)
            startActivity(intentaa)



            // 1st way to create chrome custom tabs...imp -----/start
/*
            val url = "https://community.holedo.im/?u=$calci14"
            val builder = CustomTabsIntent.Builder()
            val customTabsIntent = builder.build()
            customTabsIntent.launchUrl(requireContext(),Uri.parse(url))

//            CustomTabsIntent.setToolbarColor(ContextCompat.getColor(this, R.color.colorPrimary));
//            builder.setToolbarColor(ContextCompat.getColor(requireContext(),R.color.palette_element_green))
            CustomTabColorSchemeParams.Builder().setToolbarColor(ContextCompat.getColor(requireContext(),R.color.palette_aqua))
            builder.setInstantAppsEnabled(true)
            builder.setShowTitle(true)
            builder.setStartAnimations(requireContext(), android.R.anim.slide_in_left, android.R.anim.slide_out_right)
              builder.setExitAnimations(requireContext(), android.R.anim.fade_in, android.R.anim.fade_out)
//               builder.setColorScheme()
//            1st way to create chrome custom tabs...imp -----/end

            val colorInt: Int = Color.parseColor("#FF0000") //red

            val defaultColors = CustomTabColorSchemeParams.Builder()
                    .setToolbarColor(colorInt)
                    .build()
            builder.setDefaultColorSchemeParams(defaultColors) */

        }


//            val url = "https://community.holedo.im/?u=$calci14"
//            // initializing object for custom chrome tabs.
//            // initializing object for custom chrome tabs.
//            val customIntent = CustomTabsIntent.Builder()
//
//            // below line is setting toolbar color
//            // for our custom chrome tab.
//
//            // below line is setting toolbar color
//            // for our custom chrome tab.
//            customIntent.setToolbarColor(ContextCompat.getColor(requireContext(), R.color.palette_element_green))
//
//            customIntent.setStartAnimations(requireContext(),android.R.anim.slide_in_left,android.R.anim.slide_out_right)  //animation for this
//            // we are calling below method after
//            // setting our toolbar color.
//
//            // we are calling below method after
//            // setting our toolbar color.
//            openCustomTab(requireActivity(), customIntent.build(), Uri.parse(url))

//            val i = Intent(Intent.ACTION_VIEW, Uri.parse("https://community.holedo.im/?u=$calci14"))
//            startActivity(i)
//        }

        views.about1.setOnClickListener {
            val calci12 = sharedActionViewModel.session.myUserId
            val calci13 = calci12.replace(":holedo.com", "")
            val calci14 = calci13.replace("@", "")

//            myWebView.loadUrl("https://about.holedo.im/?u=$calci14")
//            val i = Intent(Intent.ACTION_VIEW, Uri.parse("https://about.holedo.im/?u=$calci14"))
//            startActivity(i)

//            val  urll15 = "https://about.holedo.im/?u=$calci14"
////            val intent13  = Intent(requireContext(), VectorWebViewActivity::class.java)
////            intent13.putExtra("URL14",urll15)
////            startActivity(intent13)
//
//
//            val intentab  = VectorWebViewActivity.getIntent(requireContext(), urll15)
//            startActivity(intentab)

            // 2nd way to create chrome custom tabs...imp -----/start
            val uri = Uri.parse("https://about.holedo.im/?u=$calci14")
            val intentBuilder = CustomTabsIntent.Builder()
            val params = CustomTabColorSchemeParams.Builder()
                    .setNavigationBarColor(ContextCompat.getColor(requireActivity(), R.color.palette_grape))
                    .setToolbarColor(ContextCompat.getColor(requireActivity(), R.color.palette_prune))
                    .setSecondaryToolbarColor(ContextCompat.getColor(requireActivity(), R.color.palette_melon))
                    .build()
            intentBuilder.setColorSchemeParams(CustomTabsIntent.COLOR_SCHEME_DARK, params)
            intentBuilder.setStartAnimations(requireActivity(), R.anim.animation_slide_in_right, R.anim.animation_slide_out_left)
            intentBuilder.setExitAnimations(requireActivity(), android.R.anim.slide_in_left, android.R.anim.slide_out_right)
            val customTabsIntent = intentBuilder.build()
            customTabsIntent.launchUrl(requireActivity(), uri)

            // 2nd way to create chrome custom tabs...imp -----/ends

        }


        views.Badges1.setOnClickListener {
            val calci12 = sharedActionViewModel.session.myUserId
            val calci13 = calci12.replace(":holedo.com", "")
            val calci14 = calci13.replace("@", "")
//            val i = Intent(Intent.ACTION_VIEW, Uri.parse("https://badges.holedo.im/?u=$calci14"))
//            startActivity(i)

            val  urll17 = "https://badges.holedo.im/?u=$calci14"
//            val intent13  = Intent(requireContext(), VectorWebViewActivity::class.java)
//            intent13.putExtra("URL16",urll17)
//            startActivity(intent13)

            val intentac  = VectorWebViewActivity.getIntent(requireContext(), urll17)
            startActivity(intentac)

        }

        views.help1.setOnClickListener {
//            val calci12 = sharedActionViewModel.session.myUserId
//            val calci13 = calci12.replace(":holedo.com", "")
//            val calci14 = calci13.replace("@", "")
//            val i = Intent(Intent.ACTION_VIEW, Uri.parse("https://jobs.holedo.im/?u=$calci14"))
//            startActivity(i)

//            val  urll19 = "https://jobs.holedo.im/?u=$calci14"
//            val intent13  = Intent(requireContext(), VectorWebViewActivity::class.java)
//            intent13.putExtra("URL18",urll19)
//            startActivity(intent13)

            val  urll19 = "https://www.holedo.im/help"

            val intentad  = VectorWebViewActivity.getIntent(requireContext(), urll19)
            startActivity(intentad)
        }


        views.newsbutton1.setOnClickListener {
            val calci12 = sharedActionViewModel.session.myUserId
            val calci13 = calci12.replace(":holedo.com", "")
            val calci14 = calci13.replace("@", "")
//            val i = Intent(Intent.ACTION_VIEW, Uri.parse("https://news.holedo.im/?u=$calci14"))
//            startActivity(i)

            val  urll21 = "https://news.holedo.im/?u=$calci14"
//            val intent13  = Intent(requireContext(), VectorWebViewActivity::class.java)
//            intent13.putExtra("URL20",urll21)
//            startActivity(intent13)

            val intentae  = VectorWebViewActivity.getIntent(requireContext(), urll21)
            startActivity(intentae)

        }

        views.jobsbutton1.setOnClickListener {
            val calci123 = sharedActionViewModel.session.myUserId
            val calci135 = calci123.replace(":holedo.com", "")
            val calci147 = calci135.replace("@", "")
//            val i = Intent(Intent.ACTION_VIEW, Uri.parse("https://jobs.holedo.im/?u=$calci147"))
//            startActivity(i)

            val  urll23 = "https://jobs.holedo.im/?u=$calci147"
//            val intent13  = Intent(requireContext(), VectorWebViewActivity::class.java)
//            intent13.putExtra("URL22",urll23)
//            startActivity(intent13)

            val intentaf  = VectorWebViewActivity.getIntent(requireContext(), urll23)
            startActivity(intentaf)

        }

        views.profileebutton1.setOnClickListener {

            val usernameorigaa = sharedActionViewModel.session.myUserId
            // usernameorigaa = @appsdev_tanmay:holedo.com
            val usernamefinaa = usernameorigaa.replace(":holedo.com", "")
            // usernamefinaa = @appsdev_tanmay
            val usernamefinba = usernamefinaa.replace("@", "")
            // usernamefinba = appsdev_tanmay

//            val i = Intent(Intent.ACTION_VIEW, Uri.parse("https://profile.holedo.im/$usernamefinba"))
//            startActivity(i)

            val  urll25 = "https://profile.holedo.im/?u=$usernamefinba"

            //urll25 = "https://profile.holedo.im/appsdev_tanmay"


//            val intent13  = Intent(requireContext(), VectorWebViewActivity::class.java)
//            intent13.putExtra("URL24",urll25)
//            startActivity(intent13)

            val intentag  = VectorWebViewActivity.getIntent(requireContext(), urll25)
            startActivity(intentag)

        }



        views.homeDrawerInviteFriendButton.debouncedClicks {
            session.permalinkService().createPermalink(sharedActionViewModel.session.myUserId)?.let { permalink ->
                analyticsTracker.screen(MobileScreen(screenName = MobileScreen.ScreenName.InviteFriends))
                val text = getString(R.string.invite_friends_text, permalink)

                startSharePlainTextIntent(
                        fragment = this,
                        activityResultLauncher = null,
                        chooserTitle = getString(R.string.invite_friends),
                        text = text,
                        extraTitle = getString(R.string.invite_friends_rich_title)
                )
            }
        }

        // Debug menu
        views.homeDrawerHeaderDebugView.isVisible = BuildConfig.DEBUG && vectorPreferences.developerMode()
        views.homeDrawerHeaderDebugView.debouncedClicks {
            sharedActionViewModel.post(HomeActivitySharedAction.CloseDrawer)
            navigator.openDebug(requireActivity())
        }
    }
}
