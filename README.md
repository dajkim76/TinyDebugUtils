Tiny Android Debug Utils
========================


Configure
---------
project build.gradle
<pre><code>
allprojects {
    repositories {
        maven { url "https://jitpack.io" }
    }
}
</code></pre>

app build.gradle
<pre><code>
implementation "com.github.dajkim76:tinydebugutils:0.7"
</code></pre>

Simple usage
------------
Initialize
<pre><code>
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            TinyDebugUtils.init(this)
        }
    }
}
</code></pre>

Release build proguard(configure proguard-android-optimize.txt)
<pre><code>
android {
  buildTypes {
    release {
      minifyEnabled true
      proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
    }
  }
} 
</code></pre>

Proguard settings(proguard-rules.pro)
<pre><code>
-assumenosideeffects class com.mdiwebma.tinydebugutils.DebugUtils {
	public static void *** (...);
}

-assumenosideeffects class com.mdiwebma.tinydebugutils.DebugLog {
	public static void *** (...);
}

-assumenosideeffects class com.mdiwebma.tinydebugutils.FileLog {
	public static void *** (...);
}

-assumenosideeffects class com.mdiwebma.tinydebugutils.ServerLog {
	public static void *** (...);
}

-assumenosideeffects class com.mdiwebma.tinydebugutils.AssertUtils {
	public static void *** (...);
}
</code></pre>

Release build usage (firebase sample)
-------------------
<pre><code>
if (BuildConfig.DEBUG) {
    TinyDebugUtils.init(this)
} else {
    ServerLog.setSamplingPercent(10f) // 10% of users
    ServerLog.setHandler(object : ServerLogHandler {
        override fun send(tag: String, message: String, throwable: Throwable?) {
            if (throwable != null) {
                Crashlytics.logException(throwable)
            } else {
                Crashlytics.log("[$tag]  $message")
            }
        }
    })
}
</code></pre>
Do not strip DebugUtils, ServerLog
<pre><code>
#-assumenosideeffects class com.mdiwebma.tinydebugutils.DebugUtils {
#	public static void *** (...);
#}

#-assumenosideeffects class com.mdiwebma.tinydebugutils.ServerLog {
#	public static void *** (...);
#}
</code></pre>

TinyDebugUtils Config
---------------------
<pre><code>
val config = TinyDebugUtils.Config()
    .buildGitBranchName(BuildConfig.GIT_BRANCH_NAME)
    .buildTimeStamp(BuildConfig.BUILD_TIMESTAMP)
    .debugUtilsEnable(true)
    .debugLogEnable(true)
    .debugLogLevel(DebugLog.LEVEL_VEBOSE)
    .debugLogWriteToFile(true)
    .assertUtilsEnable(true)
    .crashHandlerEnable(true)
    .fileLogEnable(true)             

TinyDebugUtils.init(this, config)
</code></pre>

Get build branch name & build timestamp
<pre><code>
## Project build.gradle
buildscript {
    ext.getGitBranchName = {
        try {
            println "Task Getting Branch Name.."

            def stdout = new ByteArrayOutputStream()
            exec {
                commandLine 'git', 'name-rev', '--name-only', 'HEAD'
                standardOutput = stdout
            }

            println "Git Current Branch = " + stdout.toString()

            return stdout.toString().trim()
        } catch (Exception e) {
            println "Exception = " + e.getMessage()
            return null
        }
    }

## App build.gradle
android {
    compileSdkVersion 28
    defaultConfig {
        ...
        buildConfigField "String", "GIT_BRANCH_NAME", "\"" + getGitBranchName() + "\""
        buildConfigField "long", "BUILD_TIMESTAMP", System.currentTimeMillis() + "L"
    }
</code></pre>

Utils description 
-----------------
* DebugUtils: Show alert if possible, toast, notify, write to file log(In case exception, checkState, checkNotNull, checkMainThread)
* DebugLog: Append file source line, Log level, write to file log also
* FileLog: 
   * location: /SD-Card/log-{packageName last word} if have external storage permission or /SD-card/Android/data/{packageName}/cache/log-{packageName last word}
   * line format: Hour:Minute:Second.Millisecond threadId(or main)/logLevel/Tag: message
<pre><code>
-----------------------------------------
App: com.mdiwebma.tinydebugutilsdemo
Version: 1.0(1)
Branch: master
BuildTime: 2019. 2. 20. 오후 4:06:38
OS Version: 28, OS Locale: ko_KR
Device: Google/Pixel 2/PQ1A.181205.002
-----------------------------------------
[2019.02.20]
16:06:53.797 main/F/Exception!: Version: 1.0(1) Branch: master BuildTime: 2019. 2. 20. 오후 4:06:38
java.lang.NumberFormatException: For input string: "abc"
 at java.lang.Integer.parseInt(Integer.java:615)
 at
</code></pre>
* ServerLog:  In release mode, send log to server instead of DebugLog, FileLog
* AssertUtils: throw Error if failed
