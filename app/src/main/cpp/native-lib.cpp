#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_com_anmol_clashking_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

extern "C" JNIEXPORT jint JNICALL
Java_com_anmol_clashking_Authentication_Validator(JNIEnv* env,jobject) {
   int a=19;
    return a;
}

extern "C" JNIEXPORT jint JNICALL
Java_com_anmol_clashking_Authentication_Validator2(JNIEnv* env,jobject ) {
    int a=070;
    return a;
}
extern "C" JNIEXPORT jstring JNICALL
Java_com_anmol_clashking_MainActivity_databaseref(JNIEnv* env,jobject) {
    std::string hello = "update";
    return env->NewStringUTF(hello.c_str());
}
extern "C" JNIEXPORT jboolean JNICALL
Java_com_anmol_clashking_Authentication_dialogshow(JNIEnv* env,jobject) {
    bool dialogshow= false;
    return dialogshow;
}
extern "C" JNIEXPORT jboolean JNICALL
Java_com_anmol_clashking_login_dialogshow(JNIEnv* env,jobject) {
    bool dialogshow= false;
    return dialogshow;
}
extern "C" JNIEXPORT jstring JNICALL
Java_com_anmol_clashking_login_dialogmsg(JNIEnv* env,jobject) {
   std::string msg="Logging in...";
    return env->NewStringUTF(msg.c_str());
}
extern "C" JNIEXPORT jstring JNICALL
Java_com_anmol_clashking_login_loginmsg(JNIEnv* env,jobject) {
    std::string msg="Successfully Logged in";
    return env->NewStringUTF(msg.c_str());
}
extern "C" JNIEXPORT jstring JNICALL
Java_com_anmol_clashking_login_Resetmsg(JNIEnv* env,jobject) {
    std::string msg="Password reset link sended to given Email";
    return env->NewStringUTF(msg.c_str());
}
extern "C" JNIEXPORT jstring JNICALL
Java_com_anmol_clashking_login_Resetdialogmsg(JNIEnv* env,jobject) {
    std::string msg="Sending Reset link...";
    return env->NewStringUTF(msg.c_str());
}
extern "C" JNIEXPORT jstring JNICALL
Java_com_anmol_clashking_Authentication_parm1(JNIEnv* env,jobject) {
    std::string hello = "versioncode";
    return env->NewStringUTF(hello.c_str());
}
extern "C" JNIEXPORT jstring JNICALL
Java_com_anmol_clashking_Authentication_parm2(JNIEnv* env,jobject) {
    std::string hello = "version";
    return env->NewStringUTF(hello.c_str());
}
extern "C" JNIEXPORT jstring JNICALL
Java_com_anmol_clashking_Authentication_parm3(JNIEnv* env,jobject) {
    std::string hello = "url";
    return env->NewStringUTF(hello.c_str());
}
extern "C" JNIEXPORT jstring JNICALL
Java_com_anmol_clashking_Authentication_parm4(JNIEnv* env,jobject) {
    std::string hello = "about";
    return env->NewStringUTF(hello.c_str());
}
extern "C" JNIEXPORT jstring JNICALL
Java_com_anmol_clashking_Authentication_aboutupdate(JNIEnv* env,jobject) {
    std::string hello = "About Update";
    return env->NewStringUTF(hello.c_str());
}
extern "C" JNIEXPORT jstring JNICALL
Java_com_anmol_clashking_Authentication_whennotupdate(JNIEnv* env,jobject) {
    std::string hello = "notupdate";
    return env->NewStringUTF(hello.c_str());
}
extern "C" JNIEXPORT jstring JNICALL
Java_com_anmol_clashking_login_Email(JNIEnv* env,jobject) {
    std::string hello = "Email is Empty";
    return env->NewStringUTF(hello.c_str());
}
extern "C" JNIEXPORT jstring JNICALL
Java_com_anmol_clashking_login_pass(JNIEnv* env,jobject) {
    std::string hello = "Password is Empty";
    return env->NewStringUTF(hello.c_str());
}
extern "C" JNIEXPORT jstring JNICALL
Java_com_anmol_clashking_login_done(JNIEnv* env,jobject) {
    std::string hello = "Email is Empty";
    return env->NewStringUTF(hello.c_str());
}
extern "C" JNIEXPORT jint JNICALL
Java_com_anmol_clashking_validation_ValidateEmail(JNIEnv* env,jobject) {
    int a=19;
    return a;
}
extern "C" JNIEXPORT jint JNICALL
Java_com_anmol_clashking_validation_Validatepass(JNIEnv* env,jobject) {
    int a=20;
    return a;
}
extern "C" JNIEXPORT jint JNICALL
Java_com_anmol_clashking_validation_Validatedone(JNIEnv* env,jobject) {
    int a=30;
    return a;
}
extern "C" JNIEXPORT jint JNICALL
Java_com_anmol_clashking_validation_Validatephone(JNIEnv* env,jobject) {
    int a=40;
    return a;
}
extern "C" JNIEXPORT jstring JNICALL
Java_com_anmol_clashking_signup_Email(JNIEnv* env,jobject) {
    std::string hello = "Email is Empty";
    return env->NewStringUTF(hello.c_str());
}
extern "C" JNIEXPORT jstring JNICALL
Java_com_anmol_clashking_signup_pass(JNIEnv* env,jobject) {
    std::string hello = "Password is Empty";
    return env->NewStringUTF(hello.c_str());
}
extern "C" JNIEXPORT jstring JNICALL
Java_com_anmol_clashking_signup_phone(JNIEnv* env,jobject) {
    std::string hello = "Phone number  is Empty";
    return env->NewStringUTF(hello.c_str());
}
extern "C" JNIEXPORT jstring JNICALL
Java_com_anmol_clashking_signup_dialogmsg(JNIEnv* env,jobject) {
    std::string hello = "Account Creating...";
    return env->NewStringUTF(hello.c_str());
}
extern "C" JNIEXPORT jstring JNICALL
Java_com_anmol_clashking_signup_signupresponse(JNIEnv* env,jobject) {
    std::string hello = "Account Successfully Created";
    return env->NewStringUTF(hello.c_str());
}
extern "C" JNIEXPORT jstring JNICALL
Java_com_anmol_clashking_signup_DBref(JNIEnv* env,jobject) {
    std::string hello = "user";
    return env->NewStringUTF(hello.c_str());
}
extern "C" JNIEXPORT jstring JNICALL
Java_com_anmol_clashking_signup_child(JNIEnv* env,jobject) {
    std::string hello = "phone";
    return env->NewStringUTF(hello.c_str());
}
extern "C" JNIEXPORT jstring JNICALL
Java_com_anmol_clashking_signup_child2(JNIEnv* env,jobject) {
    std::string hello = "email";
    return env->NewStringUTF(hello.c_str());
}
extern "C" JNIEXPORT jstring JNICALL
Java_com_anmol_clashking_signup_child3(JNIEnv* env,jobject) {
    std::string hello = "password";
    return env->NewStringUTF(hello.c_str());
}
extern "C" JNIEXPORT jstring JNICALL
Java_com_anmol_clashking_Home_dbmatchref(JNIEnv* env,jobject) {
    std::string hello = "match";
    return env->NewStringUTF(hello.c_str());
}
extern "C" JNIEXPORT jstring JNICALL
Java_com_anmol_clashking_Home_dialogmsg(JNIEnv* env,jobject) {
    std::string hello = "<font color='#FF000000'>Are you want to exit?</font>";
    return env->NewStringUTF(hello.c_str());
}
extern "C" JNIEXPORT jstring JNICALL
Java_com_anmol_clashking_user_image(JNIEnv* env,jobject) {
    std::string hello = "proimg";
    return env->NewStringUTF(hello.c_str());
}
extern "C" JNIEXPORT jstring JNICALL
Java_com_anmol_clashking_user_profile(JNIEnv* env,jobject) {
    std::string hello = "user";
    return env->NewStringUTF(hello.c_str());
}
extern "C" JNIEXPORT jstring JNICALL
Java_com_anmol_clashking_developer_getinsta(JNIEnv* env,jobject) {
    std::string hello = "https://instagram.com/____anmol_singh__rajput?utm_medium=copy_link";
    return env->NewStringUTF(hello.c_str());
}
extern "C" JNIEXPORT jstring JNICALL
Java_com_anmol_clashking_developer_getyoutube(JNIEnv* env,jobject) {
    std::string hello = "https://youtube.com/c/GamersCrowd";
    return env->NewStringUTF(hello.c_str());
}
extern "C" JNIEXPORT jstring JNICALL
Java_com_anmol_clashking_Editprofile_getname(JNIEnv* env,jobject) {
    std::string hello = "user";
    return env->NewStringUTF(hello.c_str());
}
extern "C" JNIEXPORT jstring JNICALL
Java_com_anmol_clashking_user_getname(JNIEnv* env,jobject) {
    std::string hello = "user";
    return env->NewStringUTF(hello.c_str());
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_anmol_clashking_addmoney_moneypath(JNIEnv *env, jobject thiz) {
    std::string amount="user/";
    return env->NewStringUTF(amount.c_str());
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_anmol_clashking_payment_getkeyid(JNIEnv *env, jobject thiz) {
    std::string amount="rzp_live_gkfGVpnPzHK30p";
    return env->NewStringUTF(amount.c_str());
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_anmol_clashking_payment_getkey(JNIEnv *env, jobject thiz) {
    std::string amount="amount";
    return env->NewStringUTF(amount.c_str());
}
extern "C"
JNIEXPORT jint JNICALL
Java_com_anmol_clashking_payment_getint(JNIEnv *env, jobject thiz) {
    return 100;
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_anmol_clashking_moneyadd_getkey(JNIEnv *env, jobject thiz) {
    std::string amount="amount";
    return env->NewStringUTF(amount.c_str());
}