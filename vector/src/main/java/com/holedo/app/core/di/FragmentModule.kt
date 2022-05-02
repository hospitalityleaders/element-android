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
 *
 */

package com.holedo.app.core.di

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.multibindings.IntoMap
import com.holedo.app.features.analytics.ui.consent.AnalyticsOptInFragment
import com.holedo.app.features.attachments.preview.AttachmentsPreviewFragment
import com.holedo.app.features.contactsbook.ContactsBookFragment
import com.holedo.app.features.crypto.keysbackup.settings.KeysBackupSettingsFragment
import com.holedo.app.features.crypto.quads.SharedSecuredStorageKeyFragment
import com.holedo.app.features.crypto.quads.SharedSecuredStoragePassphraseFragment
import com.holedo.app.features.crypto.quads.SharedSecuredStorageResetAllFragment
import com.holedo.app.features.crypto.recover.BootstrapConclusionFragment
import com.holedo.app.features.crypto.recover.BootstrapConfirmPassphraseFragment
import com.holedo.app.features.crypto.recover.BootstrapEnterPassphraseFragment
import com.holedo.app.features.crypto.recover.BootstrapMigrateBackupFragment
import com.holedo.app.features.crypto.recover.BootstrapReAuthFragment
import com.holedo.app.features.crypto.recover.BootstrapSaveRecoveryKeyFragment
import com.holedo.app.features.crypto.recover.BootstrapSetupRecoveryKeyFragment
import com.holedo.app.features.crypto.recover.BootstrapWaitingFragment
import com.holedo.app.features.crypto.verification.QuadSLoadingFragment
import com.holedo.app.features.crypto.verification.cancel.VerificationCancelFragment
import com.holedo.app.features.crypto.verification.cancel.VerificationNotMeFragment
import com.holedo.app.features.crypto.verification.choose.VerificationChooseMethodFragment
import com.holedo.app.features.crypto.verification.conclusion.VerificationConclusionFragment
import com.holedo.app.features.crypto.verification.emoji.VerificationEmojiCodeFragment
import com.holedo.app.features.crypto.verification.qrconfirmation.VerificationQRWaitingFragment
import com.holedo.app.features.crypto.verification.qrconfirmation.VerificationQrScannedByOtherFragment
import com.holedo.app.features.crypto.verification.request.VerificationRequestFragment
import com.holedo.app.features.devtools.RoomDevToolEditFragment
import com.holedo.app.features.devtools.RoomDevToolFragment
import com.holedo.app.features.devtools.RoomDevToolSendFormFragment
import com.holedo.app.features.devtools.RoomDevToolStateEventListFragment
import com.holedo.app.features.discovery.DiscoverySettingsFragment
import com.holedo.app.features.discovery.change.SetIdentityServerFragment
import com.holedo.app.features.home.HomeDetailFragment
import com.holedo.app.features.home.HomeDrawerFragment
import com.holedo.app.features.home.LoadingFragment
import com.holedo.app.features.home.room.breadcrumbs.BreadcrumbsFragment
import com.holedo.app.features.home.room.detail.TimelineFragment
import com.holedo.app.features.home.room.detail.search.SearchFragment
import com.holedo.app.features.home.room.list.RoomListFragment
import com.holedo.app.features.home.room.threads.list.views.ThreadListFragment
import com.holedo.app.features.location.LocationPreviewFragment
import com.holedo.app.features.location.LocationSharingFragment
import com.holedo.app.features.login.LoginCaptchaFragment
import com.holedo.app.features.login.LoginFragment
import com.holedo.app.features.login.LoginGenericTextInputFormFragment
import com.holedo.app.features.login.LoginResetPasswordFragment
import com.holedo.app.features.login.LoginResetPasswordMailConfirmationFragment
import com.holedo.app.features.login.LoginResetPasswordSuccessFragment
import com.holedo.app.features.login.LoginServerSelectionFragment
import com.holedo.app.features.login.LoginServerUrlFormFragment
import com.holedo.app.features.login.LoginSignUpSignInSelectionFragment
import com.holedo.app.features.login.LoginSplashFragment
import com.holedo.app.features.login.LoginWaitForEmailFragment
import com.holedo.app.features.login.LoginWebFragment
import com.holedo.app.features.login.terms.LoginTermsFragment
import com.holedo.app.features.login2.LoginCaptchaFragment2
import com.holedo.app.features.login2.LoginFragmentSigninPassword2
import com.holedo.app.features.login2.LoginFragmentSigninUsername2
import com.holedo.app.features.login2.LoginFragmentSignupPassword2
import com.holedo.app.features.login2.LoginFragmentSignupUsername2
import com.holedo.app.features.login2.LoginFragmentToAny2
import com.holedo.app.features.login2.LoginGenericTextInputFormFragment2
import com.holedo.app.features.login2.LoginResetPasswordFragment2
import com.holedo.app.features.login2.LoginResetPasswordMailConfirmationFragment2
import com.holedo.app.features.login2.LoginResetPasswordSuccessFragment2
import com.holedo.app.features.login2.LoginServerSelectionFragment2
import com.holedo.app.features.login2.LoginServerUrlFormFragment2
import com.holedo.app.features.login2.LoginSplashSignUpSignInSelectionFragment2
import com.holedo.app.features.login2.LoginSsoOnlyFragment2
import com.holedo.app.features.login2.LoginWaitForEmailFragment2
import com.holedo.app.features.login2.LoginWebFragment2
import com.holedo.app.features.login2.created.AccountCreatedFragment
import com.holedo.app.features.login2.terms.LoginTermsFragment2
import com.holedo.app.features.matrixto.MatrixToRoomSpaceFragment
import com.holedo.app.features.matrixto.MatrixToUserFragment
import com.holedo.app.features.onboarding.ftueauth.FtueAuthAccountCreatedFragment
import com.holedo.app.features.onboarding.ftueauth.FtueAuthCaptchaFragment
import com.holedo.app.features.onboarding.ftueauth.FtueAuthChooseDisplayNameFragment
import com.holedo.app.features.onboarding.ftueauth.FtueAuthChooseProfilePictureFragment
import com.holedo.app.features.onboarding.ftueauth.FtueAuthGenericTextInputFormFragment
import com.holedo.app.features.onboarding.ftueauth.FtueAuthLegacyStyleCaptchaFragment
import com.holedo.app.features.onboarding.ftueauth.FtueAuthLoginFragment
import com.holedo.app.features.onboarding.ftueauth.FtueAuthPersonalizationCompleteFragment
import com.holedo.app.features.onboarding.ftueauth.FtueAuthResetPasswordFragment
import com.holedo.app.features.onboarding.ftueauth.FtueAuthResetPasswordMailConfirmationFragment
import com.holedo.app.features.onboarding.ftueauth.FtueAuthResetPasswordSuccessFragment
import com.holedo.app.features.onboarding.ftueauth.FtueAuthServerSelectionFragment
import com.holedo.app.features.onboarding.ftueauth.FtueAuthSignUpSignInSelectionFragment
import com.holedo.app.features.onboarding.ftueauth.FtueAuthSplashCarouselFragment
import com.holedo.app.features.onboarding.ftueauth.FtueAuthSplashFragment
import com.holedo.app.features.onboarding.ftueauth.FtueAuthUseCaseFragment
import com.holedo.app.features.onboarding.ftueauth.FtueAuthWaitForEmailFragment
import com.holedo.app.features.onboarding.ftueauth.FtueAuthWebFragment
import com.holedo.app.features.onboarding.ftueauth.terms.FtueAuthLegacyStyleTermsFragment
import com.holedo.app.features.onboarding.ftueauth.terms.FtueAuthTermsFragment
import com.holedo.app.features.pin.PinFragment
import com.holedo.app.features.poll.create.CreatePollFragment
import com.holedo.app.features.qrcode.QrCodeScannerFragment
import com.holedo.app.features.reactions.EmojiChooserFragment
import com.holedo.app.features.reactions.EmojiSearchResultFragment
import com.holedo.app.features.roomdirectory.PublicRoomsFragment
import com.holedo.app.features.roomdirectory.createroom.CreateRoomFragment
import com.holedo.app.features.roomdirectory.picker.RoomDirectoryPickerFragment
import com.holedo.app.features.roomdirectory.roompreview.RoomPreviewNoPreviewFragment
import com.holedo.app.features.roommemberprofile.RoomMemberProfileFragment
import com.holedo.app.features.roommemberprofile.devices.DeviceListFragment
import com.holedo.app.features.roommemberprofile.devices.DeviceTrustInfoActionFragment
import com.holedo.app.features.roomprofile.RoomProfileFragment
import com.holedo.app.features.roomprofile.alias.RoomAliasFragment
import com.holedo.app.features.roomprofile.banned.RoomBannedMemberListFragment
import com.holedo.app.features.roomprofile.members.RoomMemberListFragment
import com.holedo.app.features.roomprofile.notifications.RoomNotificationSettingsFragment
import com.holedo.app.features.roomprofile.permissions.RoomPermissionsFragment
import com.holedo.app.features.roomprofile.settings.RoomSettingsFragment
import com.holedo.app.features.roomprofile.settings.joinrule.RoomJoinRuleFragment
import com.holedo.app.features.roomprofile.settings.joinrule.advanced.RoomJoinRuleChooseRestrictedFragment
import com.holedo.app.features.roomprofile.uploads.RoomUploadsFragment
import com.holedo.app.features.roomprofile.uploads.files.RoomUploadsFilesFragment
import com.holedo.app.features.roomprofile.uploads.media.RoomUploadsMediaFragment
import com.holedo.app.features.settings.VectorSettingsGeneralFragment
import com.holedo.app.features.settings.VectorSettingsHelpAboutFragment
import com.holedo.app.features.settings.VectorSettingsLabsFragment
import com.holedo.app.features.settings.VectorSettingsPinFragment
import com.holedo.app.features.settings.VectorSettingsPreferencesFragment
import com.holedo.app.features.settings.VectorSettingsSecurityPrivacyFragment
import com.holedo.app.features.settings.account.deactivation.DeactivateAccountFragment
import com.holedo.app.features.settings.crosssigning.CrossSigningSettingsFragment
import com.holedo.app.features.settings.devices.VectorSettingsDevicesFragment
import com.holedo.app.features.settings.devtools.AccountDataFragment
import com.holedo.app.features.settings.devtools.GossipingEventsPaperTrailFragment
import com.holedo.app.features.settings.devtools.IncomingKeyRequestListFragment
import com.holedo.app.features.settings.devtools.KeyRequestsFragment
import com.holedo.app.features.settings.devtools.OutgoingKeyRequestListFragment
import com.holedo.app.features.settings.homeserver.HomeserverSettingsFragment
import com.holedo.app.features.settings.ignored.VectorSettingsIgnoredUsersFragment
import com.holedo.app.features.settings.legals.LegalsFragment
import com.holedo.app.features.settings.locale.LocalePickerFragment
import com.holedo.app.features.settings.notifications.VectorSettingsAdvancedNotificationPreferenceFragment
import com.holedo.app.features.settings.notifications.VectorSettingsNotificationPreferenceFragment
import com.holedo.app.features.settings.notifications.VectorSettingsNotificationsTroubleshootFragment
import com.holedo.app.features.settings.push.PushGatewaysFragment
import com.holedo.app.features.settings.push.PushRulesFragment
import com.holedo.app.features.settings.threepids.ThreePidsSettingsFragment
import com.holedo.app.features.share.IncomingShareFragment
import com.holedo.app.features.signout.soft.SoftLogoutFragment
import com.holedo.app.features.spaces.SpaceListFragment
import com.holedo.app.features.spaces.create.ChoosePrivateSpaceTypeFragment
import com.holedo.app.features.spaces.create.ChooseSpaceTypeFragment
import com.holedo.app.features.spaces.create.CreateSpaceAdd3pidInvitesFragment
import com.holedo.app.features.spaces.create.CreateSpaceDefaultRoomsFragment
import com.holedo.app.features.spaces.create.CreateSpaceDetailsFragment
import com.holedo.app.features.spaces.explore.SpaceDirectoryFragment
import com.holedo.app.features.spaces.leave.SpaceLeaveAdvancedFragment
import com.holedo.app.features.spaces.manage.SpaceAddRoomFragment
import com.holedo.app.features.spaces.manage.SpaceManageRoomsFragment
import com.holedo.app.features.spaces.manage.SpaceSettingsFragment
import com.holedo.app.features.spaces.people.SpacePeopleFragment
import com.holedo.app.features.spaces.preview.SpacePreviewFragment
import com.holedo.app.features.terms.ReviewTermsFragment
import com.holedo.app.features.usercode.ShowUserCodeFragment
import com.holedo.app.features.userdirectory.UserListFragment
import com.holedo.app.features.widgets.WidgetFragment

@InstallIn(ActivityComponent::class)
@Module
interface FragmentModule {
    /**
     * Fragments with @IntoMap will be injected by this factory
     */
    @Binds
    fun bindFragmentFactory(factory: VectorFragmentFactory): FragmentFactory

    @Binds
    @IntoMap
    @FragmentKey(RoomListFragment::class)
    fun bindRoomListFragment(fragment: RoomListFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(LocalePickerFragment::class)
    fun bindLocalePickerFragment(fragment: LocalePickerFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(SpaceListFragment::class)
    fun bindSpaceListFragment(fragment: SpaceListFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(TimelineFragment::class)
    fun bindTimelineFragment(fragment: TimelineFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(RoomDirectoryPickerFragment::class)
    fun bindRoomDirectoryPickerFragment(fragment: RoomDirectoryPickerFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(CreateRoomFragment::class)
    fun bindCreateRoomFragment(fragment: CreateRoomFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(RoomPreviewNoPreviewFragment::class)
    fun bindRoomPreviewNoPreviewFragment(fragment: RoomPreviewNoPreviewFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(KeysBackupSettingsFragment::class)
    fun bindKeysBackupSettingsFragment(fragment: KeysBackupSettingsFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(LoadingFragment::class)
    fun bindLoadingFragment(fragment: LoadingFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(HomeDrawerFragment::class)
    fun bindHomeDrawerFragment(fragment: HomeDrawerFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(HomeDetailFragment::class)
    fun bindHomeDetailFragment(fragment: HomeDetailFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(EmojiSearchResultFragment::class)
    fun bindEmojiSearchResultFragment(fragment: EmojiSearchResultFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(LoginFragment::class)
    fun bindLoginFragment(fragment: LoginFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(LoginCaptchaFragment::class)
    fun bindLoginCaptchaFragment(fragment: LoginCaptchaFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(LoginTermsFragment::class)
    fun bindLoginTermsFragment(fragment: LoginTermsFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(LoginServerUrlFormFragment::class)
    fun bindLoginServerUrlFormFragment(fragment: LoginServerUrlFormFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(LoginResetPasswordMailConfirmationFragment::class)
    fun bindLoginResetPasswordMailConfirmationFragment(fragment: LoginResetPasswordMailConfirmationFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(LoginResetPasswordFragment::class)
    fun bindLoginResetPasswordFragment(fragment: LoginResetPasswordFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(LoginResetPasswordSuccessFragment::class)
    fun bindLoginResetPasswordSuccessFragment(fragment: LoginResetPasswordSuccessFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(LoginServerSelectionFragment::class)
    fun bindLoginServerSelectionFragment(fragment: LoginServerSelectionFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(LoginSignUpSignInSelectionFragment::class)
    fun bindLoginSignUpSignInSelectionFragment(fragment: LoginSignUpSignInSelectionFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(LoginSplashFragment::class)
    fun bindLoginSplashFragment(fragment: LoginSplashFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(LoginWebFragment::class)
    fun bindLoginWebFragment(fragment: LoginWebFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(LoginGenericTextInputFormFragment::class)
    fun bindLoginGenericTextInputFormFragment(fragment: LoginGenericTextInputFormFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(LoginWaitForEmailFragment::class)
    fun bindLoginWaitForEmailFragment(fragment: LoginWaitForEmailFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(LoginFragmentSigninUsername2::class)
    fun bindLoginFragmentSigninUsername2(fragment: LoginFragmentSigninUsername2): Fragment

    @Binds
    @IntoMap
    @FragmentKey(AccountCreatedFragment::class)
    fun bindAccountCreatedFragment(fragment: AccountCreatedFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(LoginFragmentSignupUsername2::class)
    fun bindLoginFragmentSignupUsername2(fragment: LoginFragmentSignupUsername2): Fragment

    @Binds
    @IntoMap
    @FragmentKey(LoginFragmentSigninPassword2::class)
    fun bindLoginFragmentSigninPassword2(fragment: LoginFragmentSigninPassword2): Fragment

    @Binds
    @IntoMap
    @FragmentKey(LoginFragmentSignupPassword2::class)
    fun bindLoginFragmentSignupPassword2(fragment: LoginFragmentSignupPassword2): Fragment

    @Binds
    @IntoMap
    @FragmentKey(LoginCaptchaFragment2::class)
    fun bindLoginCaptchaFragment2(fragment: LoginCaptchaFragment2): Fragment

    @Binds
    @IntoMap
    @FragmentKey(LoginFragmentToAny2::class)
    fun bindLoginFragmentToAny2(fragment: LoginFragmentToAny2): Fragment

    @Binds
    @IntoMap
    @FragmentKey(LoginTermsFragment2::class)
    fun bindLoginTermsFragment2(fragment: LoginTermsFragment2): Fragment

    @Binds
    @IntoMap
    @FragmentKey(LoginServerUrlFormFragment2::class)
    fun bindLoginServerUrlFormFragment2(fragment: LoginServerUrlFormFragment2): Fragment

    @Binds
    @IntoMap
    @FragmentKey(LoginResetPasswordMailConfirmationFragment2::class)
    fun bindLoginResetPasswordMailConfirmationFragment2(fragment: LoginResetPasswordMailConfirmationFragment2): Fragment

    @Binds
    @IntoMap
    @FragmentKey(LoginResetPasswordFragment2::class)
    fun bindLoginResetPasswordFragment2(fragment: LoginResetPasswordFragment2): Fragment

    @Binds
    @IntoMap
    @FragmentKey(LoginResetPasswordSuccessFragment2::class)
    fun bindLoginResetPasswordSuccessFragment2(fragment: LoginResetPasswordSuccessFragment2): Fragment

    @Binds
    @IntoMap
    @FragmentKey(LoginServerSelectionFragment2::class)
    fun bindLoginServerSelectionFragment2(fragment: LoginServerSelectionFragment2): Fragment

    @Binds
    @IntoMap
    @FragmentKey(LoginSsoOnlyFragment2::class)
    fun bindLoginSsoOnlyFragment2(fragment: LoginSsoOnlyFragment2): Fragment

    @Binds
    @IntoMap
    @FragmentKey(LoginSplashSignUpSignInSelectionFragment2::class)
    fun bindLoginSplashSignUpSignInSelectionFragment2(fragment: LoginSplashSignUpSignInSelectionFragment2): Fragment

    @Binds
    @IntoMap
    @FragmentKey(LoginWebFragment2::class)
    fun bindLoginWebFragment2(fragment: LoginWebFragment2): Fragment

    @Binds
    @IntoMap
    @FragmentKey(LoginGenericTextInputFormFragment2::class)
    fun bindLoginGenericTextInputFormFragment2(fragment: LoginGenericTextInputFormFragment2): Fragment

    @Binds
    @IntoMap
    @FragmentKey(LoginWaitForEmailFragment2::class)
    fun bindLoginWaitForEmailFragment2(fragment: LoginWaitForEmailFragment2): Fragment

    @Binds
    @IntoMap
    @FragmentKey(FtueAuthLegacyStyleCaptchaFragment::class)
    fun bindFtueAuthLegacyStyleCaptchaFragment(fragment: FtueAuthLegacyStyleCaptchaFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(FtueAuthCaptchaFragment::class)
    fun bindFtueAuthCaptchaFragment(fragment: FtueAuthCaptchaFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(FtueAuthGenericTextInputFormFragment::class)
    fun bindFtueAuthGenericTextInputFormFragment(fragment: FtueAuthGenericTextInputFormFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(FtueAuthLoginFragment::class)
    fun bindFtueAuthLoginFragment(fragment: FtueAuthLoginFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(FtueAuthResetPasswordFragment::class)
    fun bindFtueAuthResetPasswordFragment(fragment: FtueAuthResetPasswordFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(FtueAuthResetPasswordMailConfirmationFragment::class)
    fun bindFtueAuthResetPasswordMailConfirmationFragment(fragment: FtueAuthResetPasswordMailConfirmationFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(FtueAuthResetPasswordSuccessFragment::class)
    fun bindFtueAuthResetPasswordSuccessFragment(fragment: FtueAuthResetPasswordSuccessFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(FtueAuthServerSelectionFragment::class)
    fun bindFtueAuthServerSelectionFragment(fragment: FtueAuthServerSelectionFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(FtueAuthSignUpSignInSelectionFragment::class)
    fun bindFtueAuthSignUpSignInSelectionFragment(fragment: FtueAuthSignUpSignInSelectionFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(FtueAuthSplashFragment::class)
    fun bindFtueAuthSplashFragment(fragment: FtueAuthSplashFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(FtueAuthSplashCarouselFragment::class)
    fun bindFtueAuthSplashCarouselFragment(fragment: FtueAuthSplashCarouselFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(FtueAuthUseCaseFragment::class)
    fun bindFtueAuthUseCaseFragment(fragment: FtueAuthUseCaseFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(FtueAuthWaitForEmailFragment::class)
    fun bindFtueAuthWaitForEmailFragment(fragment: FtueAuthWaitForEmailFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(FtueAuthWebFragment::class)
    fun bindFtueAuthWebFragment(fragment: FtueAuthWebFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(FtueAuthLegacyStyleTermsFragment::class)
    fun bindFtueAuthLegacyStyleTermsFragment(fragment: FtueAuthLegacyStyleTermsFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(FtueAuthTermsFragment::class)
    fun bindFtueAuthTermsFragment(fragment: FtueAuthTermsFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(FtueAuthAccountCreatedFragment::class)
    fun bindFtueAuthAccountCreatedFragment(fragment: FtueAuthAccountCreatedFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(FtueAuthChooseDisplayNameFragment::class)
    fun bindFtueAuthChooseDisplayNameFragment(fragment: FtueAuthChooseDisplayNameFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(FtueAuthChooseProfilePictureFragment::class)
    fun bindFtueAuthChooseProfilePictureFragment(fragment: FtueAuthChooseProfilePictureFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(FtueAuthPersonalizationCompleteFragment::class)
    fun bindFtueAuthPersonalizationCompleteFragment(fragment: FtueAuthPersonalizationCompleteFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(UserListFragment::class)
    fun bindUserListFragment(fragment: UserListFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(PushGatewaysFragment::class)
    fun bindPushGatewaysFragment(fragment: PushGatewaysFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(VectorSettingsNotificationsTroubleshootFragment::class)
    fun bindVectorSettingsNotificationsTroubleshootFragment(fragment: VectorSettingsNotificationsTroubleshootFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(VectorSettingsAdvancedNotificationPreferenceFragment::class)
    fun bindVectorSettingsAdvancedNotificationPreferenceFragment(fragment: VectorSettingsAdvancedNotificationPreferenceFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(VectorSettingsNotificationPreferenceFragment::class)
    fun bindVectorSettingsNotificationPreferenceFragment(fragment: VectorSettingsNotificationPreferenceFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(VectorSettingsLabsFragment::class)
    fun bindVectorSettingsLabsFragment(fragment: VectorSettingsLabsFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(HomeserverSettingsFragment::class)
    fun bindHomeserverSettingsFragment(fragment: HomeserverSettingsFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(VectorSettingsPinFragment::class)
    fun bindVectorSettingsPinFragment(fragment: VectorSettingsPinFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(VectorSettingsGeneralFragment::class)
    fun bindVectorSettingsGeneralFragment(fragment: VectorSettingsGeneralFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(PushRulesFragment::class)
    fun bindPushRulesFragment(fragment: PushRulesFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(VectorSettingsPreferencesFragment::class)
    fun bindVectorSettingsPreferencesFragment(fragment: VectorSettingsPreferencesFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(VectorSettingsSecurityPrivacyFragment::class)
    fun bindVectorSettingsSecurityPrivacyFragment(fragment: VectorSettingsSecurityPrivacyFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(VectorSettingsHelpAboutFragment::class)
    fun bindVectorSettingsHelpAboutFragment(fragment: VectorSettingsHelpAboutFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(VectorSettingsIgnoredUsersFragment::class)
    fun bindVectorSettingsIgnoredUsersFragment(fragment: VectorSettingsIgnoredUsersFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(VectorSettingsDevicesFragment::class)
    fun bindVectorSettingsDevicesFragment(fragment: VectorSettingsDevicesFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(ThreePidsSettingsFragment::class)
    fun bindThreePidsSettingsFragment(fragment: ThreePidsSettingsFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(PublicRoomsFragment::class)
    fun bindPublicRoomsFragment(fragment: PublicRoomsFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(RoomProfileFragment::class)
    fun bindRoomProfileFragment(fragment: RoomProfileFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(RoomMemberListFragment::class)
    fun bindRoomMemberListFragment(fragment: RoomMemberListFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(RoomUploadsFragment::class)
    fun bindRoomUploadsFragment(fragment: RoomUploadsFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(RoomUploadsMediaFragment::class)
    fun bindRoomUploadsMediaFragment(fragment: RoomUploadsMediaFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(RoomUploadsFilesFragment::class)
    fun bindRoomUploadsFilesFragment(fragment: RoomUploadsFilesFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(RoomSettingsFragment::class)
    fun bindRoomSettingsFragment(fragment: RoomSettingsFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(RoomAliasFragment::class)
    fun bindRoomAliasFragment(fragment: RoomAliasFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(RoomPermissionsFragment::class)
    fun bindRoomPermissionsFragment(fragment: RoomPermissionsFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(RoomMemberProfileFragment::class)
    fun bindRoomMemberProfileFragment(fragment: RoomMemberProfileFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(BreadcrumbsFragment::class)
    fun bindBreadcrumbsFragment(fragment: BreadcrumbsFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(AnalyticsOptInFragment::class)
    fun bindAnalyticsOptInFragment(fragment: AnalyticsOptInFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(EmojiChooserFragment::class)
    fun bindEmojiChooserFragment(fragment: EmojiChooserFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(SoftLogoutFragment::class)
    fun bindSoftLogoutFragment(fragment: SoftLogoutFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(VerificationRequestFragment::class)
    fun bindVerificationRequestFragment(fragment: VerificationRequestFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(VerificationChooseMethodFragment::class)
    fun bindVerificationChooseMethodFragment(fragment: VerificationChooseMethodFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(VerificationEmojiCodeFragment::class)
    fun bindVerificationEmojiCodeFragment(fragment: VerificationEmojiCodeFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(VerificationQrScannedByOtherFragment::class)
    fun bindVerificationQrScannedByOtherFragment(fragment: VerificationQrScannedByOtherFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(VerificationQRWaitingFragment::class)
    fun bindVerificationQRWaitingFragment(fragment: VerificationQRWaitingFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(VerificationConclusionFragment::class)
    fun bindVerificationConclusionFragment(fragment: VerificationConclusionFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(VerificationCancelFragment::class)
    fun bindVerificationCancelFragment(fragment: VerificationCancelFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(QuadSLoadingFragment::class)
    fun bindQuadSLoadingFragment(fragment: QuadSLoadingFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(VerificationNotMeFragment::class)
    fun bindVerificationNotMeFragment(fragment: VerificationNotMeFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(QrCodeScannerFragment::class)
    fun bindQrCodeScannerFragment(fragment: QrCodeScannerFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(DeviceListFragment::class)
    fun bindDeviceListFragment(fragment: DeviceListFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(DeviceTrustInfoActionFragment::class)
    fun bindDeviceTrustInfoActionFragment(fragment: DeviceTrustInfoActionFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(CrossSigningSettingsFragment::class)
    fun bindCrossSigningSettingsFragment(fragment: CrossSigningSettingsFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(AttachmentsPreviewFragment::class)
    fun bindAttachmentsPreviewFragment(fragment: AttachmentsPreviewFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(IncomingShareFragment::class)
    fun bindIncomingShareFragment(fragment: IncomingShareFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(AccountDataFragment::class)
    fun bindAccountDataFragment(fragment: AccountDataFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(OutgoingKeyRequestListFragment::class)
    fun bindOutgoingKeyRequestListFragment(fragment: OutgoingKeyRequestListFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(IncomingKeyRequestListFragment::class)
    fun bindIncomingKeyRequestListFragment(fragment: IncomingKeyRequestListFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(KeyRequestsFragment::class)
    fun bindKeyRequestsFragment(fragment: KeyRequestsFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(GossipingEventsPaperTrailFragment::class)
    fun bindGossipingEventsPaperTrailFragment(fragment: GossipingEventsPaperTrailFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(BootstrapEnterPassphraseFragment::class)
    fun bindBootstrapEnterPassphraseFragment(fragment: BootstrapEnterPassphraseFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(BootstrapConfirmPassphraseFragment::class)
    fun bindBootstrapConfirmPassphraseFragment(fragment: BootstrapConfirmPassphraseFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(BootstrapWaitingFragment::class)
    fun bindBootstrapWaitingFragment(fragment: BootstrapWaitingFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(BootstrapSetupRecoveryKeyFragment::class)
    fun bindBootstrapSetupRecoveryKeyFragment(fragment: BootstrapSetupRecoveryKeyFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(BootstrapSaveRecoveryKeyFragment::class)
    fun bindBootstrapSaveRecoveryKeyFragment(fragment: BootstrapSaveRecoveryKeyFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(BootstrapConclusionFragment::class)
    fun bindBootstrapConclusionFragment(fragment: BootstrapConclusionFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(BootstrapReAuthFragment::class)
    fun bindBootstrapReAuthFragment(fragment: BootstrapReAuthFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(BootstrapMigrateBackupFragment::class)
    fun bindBootstrapMigrateBackupFragment(fragment: BootstrapMigrateBackupFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(DeactivateAccountFragment::class)
    fun bindDeactivateAccountFragment(fragment: DeactivateAccountFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(SharedSecuredStoragePassphraseFragment::class)
    fun bindSharedSecuredStoragePassphraseFragment(fragment: SharedSecuredStoragePassphraseFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(SharedSecuredStorageKeyFragment::class)
    fun bindSharedSecuredStorageKeyFragment(fragment: SharedSecuredStorageKeyFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(SharedSecuredStorageResetAllFragment::class)
    fun bindSharedSecuredStorageResetAllFragment(fragment: SharedSecuredStorageResetAllFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(SetIdentityServerFragment::class)
    fun bindSetIdentityServerFragment(fragment: SetIdentityServerFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(DiscoverySettingsFragment::class)
    fun bindDiscoverySettingsFragment(fragment: DiscoverySettingsFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(LegalsFragment::class)
    fun bindLegalsFragment(fragment: LegalsFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(ReviewTermsFragment::class)
    fun bindReviewTermsFragment(fragment: ReviewTermsFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(WidgetFragment::class)
    fun bindWidgetFragment(fragment: WidgetFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(ContactsBookFragment::class)
    fun bindPhoneBookFragment(fragment: ContactsBookFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(PinFragment::class)
    fun bindPinFragment(fragment: PinFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(RoomBannedMemberListFragment::class)
    fun bindRoomBannedMemberListFragment(fragment: RoomBannedMemberListFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(RoomNotificationSettingsFragment::class)
    fun bindRoomNotificationSettingsFragment(fragment: RoomNotificationSettingsFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(SearchFragment::class)
    fun bindSearchFragment(fragment: SearchFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(ShowUserCodeFragment::class)
    fun bindShowUserCodeFragment(fragment: ShowUserCodeFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(RoomDevToolFragment::class)
    fun bindRoomDevToolFragment(fragment: RoomDevToolFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(RoomDevToolStateEventListFragment::class)
    fun bindRoomDevToolStateEventListFragment(fragment: RoomDevToolStateEventListFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(RoomDevToolEditFragment::class)
    fun bindRoomDevToolEditFragment(fragment: RoomDevToolEditFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(RoomDevToolSendFormFragment::class)
    fun bindRoomDevToolSendFormFragment(fragment: RoomDevToolSendFormFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(SpacePreviewFragment::class)
    fun bindSpacePreviewFragment(fragment: SpacePreviewFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(ChooseSpaceTypeFragment::class)
    fun bindChooseSpaceTypeFragment(fragment: ChooseSpaceTypeFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(CreateSpaceDetailsFragment::class)
    fun bindCreateSpaceDetailsFragment(fragment: CreateSpaceDetailsFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(CreateSpaceDefaultRoomsFragment::class)
    fun bindCreateSpaceDefaultRoomsFragment(fragment: CreateSpaceDefaultRoomsFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(MatrixToUserFragment::class)
    fun bindMatrixToUserFragment(fragment: MatrixToUserFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(MatrixToRoomSpaceFragment::class)
    fun bindMatrixToRoomSpaceFragment(fragment: MatrixToRoomSpaceFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(SpaceDirectoryFragment::class)
    fun bindSpaceDirectoryFragment(fragment: SpaceDirectoryFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(ChoosePrivateSpaceTypeFragment::class)
    fun bindChoosePrivateSpaceTypeFragment(fragment: ChoosePrivateSpaceTypeFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(CreateSpaceAdd3pidInvitesFragment::class)
    fun bindCreateSpaceAdd3pidInvitesFragment(fragment: CreateSpaceAdd3pidInvitesFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(SpaceAddRoomFragment::class)
    fun bindSpaceAddRoomFragment(fragment: SpaceAddRoomFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(SpacePeopleFragment::class)
    fun bindSpacePeopleFragment(fragment: SpacePeopleFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(SpaceSettingsFragment::class)
    fun bindSpaceSettingsFragment(fragment: SpaceSettingsFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(SpaceManageRoomsFragment::class)
    fun bindSpaceManageRoomsFragment(fragment: SpaceManageRoomsFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(RoomJoinRuleFragment::class)
    fun bindRoomJoinRuleFragment(fragment: RoomJoinRuleFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(RoomJoinRuleChooseRestrictedFragment::class)
    fun bindRoomJoinRuleChooseRestrictedFragment(fragment: RoomJoinRuleChooseRestrictedFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(SpaceLeaveAdvancedFragment::class)
    fun bindSpaceLeaveAdvancedFragment(fragment: SpaceLeaveAdvancedFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(ThreadListFragment::class)
    fun bindThreadListFragment(fragment: ThreadListFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(CreatePollFragment::class)
    fun bindCreatePollFragment(fragment: CreatePollFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(LocationSharingFragment::class)
    fun bindLocationSharingFragment(fragment: LocationSharingFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(LocationPreviewFragment::class)
    fun bindLocationPreviewFragment(fragment: LocationPreviewFragment): Fragment
}
