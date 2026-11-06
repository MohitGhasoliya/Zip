# Next implementation milestones

1. Add authenticated device identity and short-lived pairing/session tokens. Never treat the device code as a password.
2. Add WebRTC offer/answer/ICE signaling and a TURN server; test both network directions.
3. Capture and encode Windows frames; on Android use MediaProjection + VirtualDisplay + ImageReader/encoder. Send frames only to an authenticated approved peer.
4. Add per-session screen-share approval, visible active indicator, and immediate stop/revoke.
5. Add remote input separately: Windows input control behind a local consent prompt; Android Accessibility Service only after the owner manually enables it in Android Settings. Show a persistent indicator and provide disconnect/revoke.
6. Add expiry, rate limiting, audit trail, and device removal.

A saved pairing can remember device identity, but it must not silently bypass OS-level screen capture permission or the other device owner's session consent.
