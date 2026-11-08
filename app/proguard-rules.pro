# Android default rules
-dontwarn android.**
-dontwarn android.app.**
-dontwarn android.content.**
-dontwarn android.os.**
-dontwarn android.util.**
-dontwarn android.widget.**

# Room
-keep class androidx.room.** { *; }
-keep class * extends androidx.room.Database { *; }
-keep class * extends androidx.room.Entity { *; }
-keep class * extends androidx.room.Dao { *; }

# Kotlin
-keep class kotlin.Metadata { *; }
-keep class kotlin.** { *; }
-keep class kx.** { *; }

# Compose
-keep class androidx.compose.** { *; }

# Keep all activities
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver

# Keep all ViewModels
-keep class * extends androidx.lifecycle.ViewModel { *; }

# Keep R classes
-keep class **.R$* { *; }

# Keep data classes
-keepclassmembers class ** {
    @androidx.room.Entity <methods>;
}