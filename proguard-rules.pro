-keep class org.devore.edit.MainKt {
    public void main(java.lang.String[]);
}
-keep class io.ktor.** { *; }
-keep class kotlin.** { *; }
-keep class kotlinx.coroutines.**  { *; }
-keep class org.jetbrains.skia.** { *; }
-keep class org.jetbrains.skiko.** { *; }
-assumenosideeffects class kotlin.jvm.internal.Intrinsics {
    void checkParameterIsNotNull(java.lang.Object, java.lang.String);
    void checkExpressionValueIsNotNull(java.lang.Object, java.lang.String);
    void checkNotNullExpressionValue(java.lang.Object, java.lang.String);
    void checkReturnedValueIsNotNull(java.lang.Object, java.lang.String, java.lang.String);
    void checkReturnedValueIsNotNull(java.lang.Object, java.lang.String);
    void checkFieldIsNotNull(java.lang.Object, java.lang.String, java.lang.String);
    void checkFieldIsNotNull(java.lang.Object, java.lang.String);
    void checkNotNull(java.lang.Object, java.lang.String);
    void checkNotNullParameter(java.lang.Object, java.lang.String);
}
-assumenosideeffects public class androidx.compose.runtime.ComposerKt {
    void sourceInformation(androidx.compose.runtime.Composer, java.lang.String);
    void sourceInformationMarkerStart(androidx.compose.runtime.Composer, int, java.lang.String);
    void sourceInformationMarkerEnd(androidx.compose.runtime.Composer);
    boolean isTraceInProgress();
    void traceEventStart(int, java.lang.String);
    void traceEventEnd();
}
-ignorewarnings
-dontoptimize
-keepattributes
-verbose
