# Building Ink Notes

## First Time Setup

### 1. Install Prerequisites

Make sure you have the following installed:
- **Java JDK 17 or later**
- **Android Studio** (latest stable version)
- **Android SDK 34** (compileSdk)
- **Android NDK** (optional, for native code)

### 2. Clone the Repository

```bash
git clone https://github.com/tormentoraj/InkNotes.git
cd InkNotes
```

### 3. Run Gradle Wrapper

The first time you run the gradle wrapper, it will download the required Gradle version:

```bash
./gradlew --version
```

This will download Gradle 8.3 and set up the wrapper.

### 4. Build the Project

```bash
# Build debug APK
./gradlew assembleDebug

# Or build release APK (requires signing config)
./gradlew assembleRelease
```

### 5. Run on Device

```bash
# Install debug APK on connected device
./gradlew installDebug
```

## Common Commands

### Build
```bash
# Build all variants
./gradlew build

# Build debug variant
./gradlew assembleDebug

# Build release variant
./gradlew assembleRelease
```

### Clean
```bash
# Clean build
./gradlew clean

# Clean and rebuild
./gradlew clean assembleDebug
```

### Test
```bash
# Run unit tests
./gradlew testDebugUnitTest

# Run instrumented tests
./gradlew connectedDebugAndroidTest

# Run all tests
./gradlew test
```

### Lint
```bash
# Run lint
./gradlew lintDebug

# Fix lint issues (where possible)
./gradlew lintDebug --fix
```

## Troubleshooting

### Gradle Wrapper Issues

If you get errors about the gradle wrapper:

1. Make sure you have internet access
2. Run `./gradlew --version` to trigger the download
3. Wait for the download to complete
4. Try building again

### Java Version Issues

If you get errors about Java version:

1. Make sure you have JDK 17 or later installed
2. Set JAVA_HOME environment variable:
   ```bash
   export JAVA_HOME=/path/to/jdk17
   ```
3. Verify with `java -version`

### Android SDK Issues

If you get errors about Android SDK:

1. Make sure you have Android Studio installed
2. Install SDK 34 from Android Studio
3. Set ANDROID_HOME environment variable:
   ```bash
   export ANDROID_HOME=/path/to/android/sdk
   ```

### Memory Issues

If you get out of memory errors:

1. Increase Gradle heap size in `gradle.properties`:
   ```properties
   org.gradle.jvmargs=-Xmx4g -XX:MaxMetaspaceSize=1g
   ```

2. Or set it via command line:
   ```bash
   export GRADLE_OPTS="-Xmx4g -XX:MaxMetaspaceSize=1g"
   ./gradlew assembleDebug
   ```

## Android Studio Setup

1. Open Android Studio
2. File → Open → Select the InkNotes project directory
3. Android Studio will automatically:
   - Download required dependencies
   - Index the project
   - Set up the build

4. Once indexing is complete, you can:
   - Run the app on an emulator or device
   - Edit the code with full IDE support
   - Use the debugger

## Emulator Setup

To create an emulator for testing:

1. Open Android Studio
2. Tools → Device Manager
3. Click "Create Virtual Device"
4. Select a device (e.g., Pixel 6)
5. Select a system image (e.g., Android 14)
6. Click "Finish"
7. Click the play button to start the emulator

## Physical Device Setup

To run on a physical Android device:

1. Enable Developer Options:
   - Go to Settings → About Phone
   - Tap "Build Number" 7 times
   - Enter your PIN if prompted

2. Enable USB Debugging:
   - Go to Settings → Developer Options
   - Enable "USB Debugging"

3. Connect your device to your computer via USB
4. On your device, allow USB debugging when prompted
5. Run the app from Android Studio or command line

## Build Variants

The project has two build variants:

- **debug**: For development and testing
  - No minification/proguard
  - Debuggable
  - Signed with debug keystore

- **release**: For production
  - Minification/proguard enabled
  - Not debuggable
  - Requires signing config

To build a release APK, you need to set up signing:

1. Create a keystore file:
   ```bash
   keytool -genkey -v -keystore inknotes-release.keystore -alias inknotes -keyalg RSA -keysize 2048 -validity 10000
   ```

2. Add signing config to `app/build.gradle.kts`:
   ```kotlin
   android {
       signingConfigs {
           release {
               storeFile = file("inknotes-release.keystore")
               storePassword = "yourpassword"
               keyAlias = "inknotes"
               keyPassword = "yourpassword"
           }
       }
       buildTypes {
           release {
               signingConfig = signingConfigs.getByName("release")
           }
       }
   }
   ```

## Continuous Integration

For CI/CD pipelines, use the following commands:

```bash
# Clean build
./gradlew clean

# Build debug APK
./gradlew assembleDebug

# Run tests
./gradlew testDebugUnitTest

# Run lint
./gradlew lintDebug

# Build release APK (with signing)
./gradlew assembleRelease
```

## Performance Testing

To run performance tests:

```bash
# Run benchmark tests
./gradlew benchmarkDebugAndroidTest

# Generate performance report
./gradlew generatePerformanceReport
```

## Code Quality

### Static Analysis
```bash
# Run lint
./gradlew lintDebug

# Run detekt (if configured)
./gradlew detekt

# Run ktlint (if configured)
./gradlew ktlintCheck
```

### Code Coverage
```bash
# Generate coverage report
./gradlew jacocoTestReport

# Open coverage report
open app/build/reports/jacoco/jacocoTestReport/html/index.html
```

## Dependencies

To view the dependency tree:

```bash
./gradlew dependencies

# For a specific configuration
./gradlew :app:dependencies
```

To update dependencies:

1. Check for updates:
   ```bash
   ./gradlew dependencyUpdates
   ```

2. Update the version in `build.gradle.kts` files
3. Run `./gradlew clean build` to verify

## Environment Variables

The following environment variables can be set:

- `JAVA_HOME`: Path to Java JDK
- `ANDROID_HOME`: Path to Android SDK
- `GRADLE_OPTS`: JVM options for Gradle
- `GRADLE_USER_HOME`: Gradle user home directory

Example:
```bash
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk
export ANDROID_HOME=/home/user/Android/Sdk
export GRADLE_OPTS="-Xmx4g -XX:MaxMetaspaceSize=1g"
```