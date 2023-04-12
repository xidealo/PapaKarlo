package com.bunbeauty.shared.data

import platform.Foundation.NSBundle

internal val targetName = NSBundle.mainBundle.objectForInfoDictionaryKey("CFBundleName")