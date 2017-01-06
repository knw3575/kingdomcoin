#include <jni.h>
#include <string>

extern "C"
jstring
Java_de_schildbach_wallet_app2_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}
