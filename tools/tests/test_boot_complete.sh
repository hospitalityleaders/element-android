# Test boot complete broadcast

adb shell am broadcast -a android.intent.action.ACTION_BOOT_COMPLETED -c android.intent.category.HOME -n com.holedo.alpha/com.holedo.receiver.VectorBootReceiver
