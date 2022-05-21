/*
 * Copyright (c) 2022 New Vector Ltd
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

package com.holedo.app.features.qrcode

import com.airbnb.mvrx.MavericksViewModelFactory
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import com.holedo.app.core.di.MavericksAssistedViewModelFactory
import com.holedo.app.core.di.hiltMavericksViewModelFactory
import com.holedo.app.core.platform.VectorDummyViewState
import com.holedo.app.core.platform.VectorViewModel
import org.matrix.android.sdk.api.session.Session

class QrCodeScannerViewModel @AssistedInject constructor(
        @Assisted initialState: VectorDummyViewState,
        val session: Session
) : VectorViewModel<VectorDummyViewState, QrCodeScannerAction, QrCodeScannerEvents>(initialState) {

    @AssistedFactory
    interface Factory : MavericksAssistedViewModelFactory<QrCodeScannerViewModel, VectorDummyViewState> {
        override fun create(initialState: VectorDummyViewState): QrCodeScannerViewModel
    }

    companion object : MavericksViewModelFactory<QrCodeScannerViewModel, VectorDummyViewState> by hiltMavericksViewModelFactory()

    override fun handle(action: QrCodeScannerAction) {
        _viewEvents.post(
                when (action) {
                    is QrCodeScannerAction.CodeDecoded -> QrCodeScannerEvents.CodeParsed(action.result, action.isQrCode)
                    is QrCodeScannerAction.SwitchMode  -> QrCodeScannerEvents.SwitchMode
                    is QrCodeScannerAction.ScanFailed  -> QrCodeScannerEvents.ParseFailed
                }
        )
    }
}
