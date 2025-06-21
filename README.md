# 🍕 Food Delivery App

The app allows customers to order food online from a restaurant and have it delivered to their doorstep. It features an easy-to-use interface that allows users to browse menu, select their desired dishes, and place orders for delivery or pickup.

## Release

Get it on [Google Play](https://play.google.com/store/apps/details?id=com.bunbeuaty.papakarlo) <br/>
Download on the [App Store](https://apps.apple.com/ru/app/%D0%BF%D0%B0%D0%BF%D0%B0-%D0%BA%D0%B0%D1%80%D0%BB%D0%BE/id6443966083)

## Stack

- [Kotlin](https://kotlinlang.org/): Programming language (Android + Shared)
- [Swift](https://developer.apple.com/swift/): Programming language (iOS)
- [Kotlin Multiplatform](https://kotlinlang.org/lp/mobile/): Code sharing between Android and iOS platforms
- [Koin](https://insert-koin.io/): Dependency injection
- Kotlinx
  - [Coroutines](https://github.com/Kotlin/kotlinx.coroutines): Multithreading
  - [Serialization](https://github.com/Kotlin/kotlinx.serialization): JSON serialization/deserailization
  - [Datetime](https://github.com/Kotlin/kotlinx-datetime): Handling of date and time
- [Ktor](https://ktor.io/): HTTP/WebSocket client
- [SQLDelight](https://cashapp.github.io/sqldelight/2.0.0-alpha05/): Generating typesafe Kotlin APIs from SQL statements
- [Firebase](https://firebase.google.com/): Authorization
- [Coil](https://coil-kt.github.io/coil/): Image loading (Android)
- [Jetpack compose](https://developer.android.com/jetpack/compose): UI (Android)
- [SwiftUI](https://developer.apple.com/xcode/swiftui/): UI (iOS)

![Frame 10](https://github.com/user-attachments/assets/e38eda17-7996-45b9-937b-576ae92cd7f9)

## Tech info

### Git flow
1. Create new branch feature/"name" from **develop** <br/>
2. Create MR to develop
3. Check pipelines and send to code review <br/>
4. Merge into **develop** <br/>

### Release

1. Increase version by rules: <br/>
    fix n.m.**update** <br/>
    new release n.**update**.0 <br/>
    when minor version 9 next update major version **update**.0.0 <br/>
2. Create MR to **master** and write title release/**version**, check pipelines <br/>
3. Merge into **master**
4. Check actions (CI\CD Publish workflow).
5. **Android**  CD will send to Google Play by himself <br/>
6. **Ios** TODO 

