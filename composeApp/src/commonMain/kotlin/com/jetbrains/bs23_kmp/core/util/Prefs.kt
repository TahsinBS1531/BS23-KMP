package com.jetbrains.bs23_kmp.core.util



//class Prefs (context: Context) {
//    private val SHARED_PREF_NAME = "ARISTOPHARMA_SHARED_PREFERENCE"
//    val preferences: SharedPreferences =
//        context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
//    val gson: Gson = GsonBuilder().create()
//
//    init {
//
//    }
//
////    var dashboardTabStatus: DashboardTabStatus
////        get() = get<DashboardTabStatus>(::dashboardTabStatus.name) ?: DashboardTabStatus()
////        set(value) = put(::dashboardTabStatus.name, value)
//
//    var deviceId: DeviceId
//        get() = get<DeviceId>(::deviceId.name) ?: DeviceId("", "")
//        set(value) = put(::deviceId.name, value)
//
//    var loginModel: LoginModel
//        get() = get<LoginModel>(::loginModel.name) ?: LoginModel()
//        set(value) = put(::loginModel.name, value)
//
//    var syncModel: SyncModel
//        get() = get<SyncModel>(::syncModel.name) ?: SyncModel(false)
//        set(value) = put(::syncModel.name, value)
//
//    var appSettings: AppSettings
//        get() = get<AppSettings>(::appSettings.name) ?: AppSettings()
//        set(value) = put(::appSettings.name, value)
//
//    fun <T> put(key: String, `object`: T) {
//        val jsonString = gson.toJson(`object`)
//        preferences.edit().putString(key, jsonString).apply()
//    }
//
//    inline fun <reified T> get(key: String): T? {
//        val value = preferences.getString(key, null)
//        return if(value != null) gson.fromJson(value, T::class.java) else null
//    }
//
//    fun clear(){
//        preferences.edit().clear().apply()
//    }
//
//    fun clearLogin(){
//        if(prefs.loginModel.isRememberMe){
//            prefs.loginModel = LoginModel(
//                userName = prefs.loginModel.userName,
//                password = prefs.loginModel.password,
//                token = "",
//                refreshToken = "",
//                isRememberMe = true
//            )
//        }
//        else{
//            prefs.loginModel = LoginModel()
//        }
//
//        syncModel = SyncModel(
//            false, null
//        )
//    }
//
//}