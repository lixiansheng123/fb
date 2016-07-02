# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in E:\android\SDK/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
  #混淆时是否记录日志
 -verbose
 #->设置混淆的压缩比率 0 ~ 7
 -optimizationpasses 5
 # Aa aA
 -dontusemixedcaseclassnames
 #->如果应用程序引入的有jar包,并且想混淆jar包里面的class
 -dontskipnonpubliclibraryclasses
 -dontpreverify
 #  ->混淆后生产映射文件 map 类名->转化后类名的映射
 -verbose
 # !code/simplification/arithmetic,!field/*,!class/merging/*       ->混淆采用的算法.
# -optimizations

 -keep public class * extends android.app.Fragment
 -keep public class * extends android.support.v4.app.Fragment
 -keep public class * extends android.app.Activity
 -keep public class * extends android.app.Application
 -keep public class * extends android.app.Service
 -keep public class * extends android.content.BroadcastReceiver
 -keep public class * extends android.content.ContentProvider
 -keep public class * extends android.app.backup.BackupAgentHelper
 -keep public class * extends android.preference.Preference
 -keep public class * extends android.support.v4.**
 -keep public class com.android.vending.licensing.ILicensingService
 -keep public class * extends java.lang.Exception
 # -> 枚举类不能去混淆
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keepclassmembers class * {
   public <init>(org.json.JSONObject);
}
#不混淆注解
#-keepattributes *Annotation*
-keep class * extends java.lang.annotation.Annotation { *; }

-dontwarn android.net.http.**
-keep class android.net.http.** { *;}
## support-v4
-dontwarn android.support.v4.**
-keep class android.support.v4.** { *; }
-keep interface android.support.v4.app.** { *; }
-keep public class * extends android.support.v4.**
-dontwarn com.android.support.**
-keep class com.android.support.** { *; }
-keep class android.support.design.**
#org.apache.http.legacy.jar
-keep class org.apache.http.** { *; }
-dontwarn org.apache.http.**
-dontwarn android.net.**
-keep class com.android.volley.** { *; }
# Gson
-keepattributes Signature
-keepattributes *Annotation*
-keep class sun.misc.Unsafe { *; }
-keep class me.isming.meizitu.model.** { *;}
-keep class com.google.gson.stream.** { *; }
-keep class com.google.gson.**{*;}
#nineoldandroids
-keep class com.nineoldandroids.** { *; }
#bean
-keep class com.yuedong.lib_develop.bean.**{*;}
-keep class com.yuedong.football_mad.model.bean.**{*;}
#R
-keep public class com.yuedong.football_mad.R$*{
    public static final int *;
}
#xutils
#-keep class com.yuedong.lib_develop.db.**{*;}
#-keep class com.yuedong.lib_develop.ioc.**{*;}
#因为有注解 所以activity fragment都不混淆
-keep class com.yuedong.football_mad.ui.**{*;}

#对使用Db模块持久化的实体类不要混淆，或者注解所有表和列名称@Table(name="xxx")，@Id(column="xxx")，



