# MobiPC Connect — Native app starter

This package contains starter source projects for Windows and Android. It is **not a finished remote-access product** and does not yet stream a remote screen or send remote input.

## What's included
- Windows .NET 8 WinForms app with visible Start/Stop sharing controls and a local screen preview.
- Android Kotlin app with a visible MediaProjection permission prompt and foreground sharing notification.
- A build guide and the next implementation milestones.

## Build requirements
- Windows: Windows 10/11 + .NET 8 SDK. Run `build-windows.bat` on Windows.
- Android: Android Studio + Android SDK 35. Open the `android-companion` folder and build an APK.

This environment does not have the .NET SDK, Android SDK, or Gradle installed, so compiled `.exe` and `.apk` files cannot be produced here. The source projects are provided instead.

## Safety and permissions
Pairing a device code does not grant screen access. Each device must explicitly approve screen sharing through its operating-system permission UI. Sharing must remain visible and stoppable. Android remote touch control would require a separate Accessibility service that the device owner enables manually; it is intentionally not silently enabled here.

## Not implemented yet
- Supabase authenticated signaling for WebRTC
- WebRTC video transport / TURN
- Cross-device live screen viewing
- Remote mouse, keyboard, or touch control
- Persistent trusted-device authorization

Do not treat this starter as a working AnyDesk replacement yet.
