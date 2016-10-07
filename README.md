# Notify - Notify your team
Whenever something good happens in the office, ring the bell in your phone and everyone in the office with the app will get a push notification with the bell sound and your message.

App in Foreground(left) and app not open at all (right)

![foreground](https://cloud.githubusercontent.com/assets/3669105/19163340/dd4cd108-8bfb-11e6-96b2-fe5f487ca377.gif)
![background](https://cloud.githubusercontent.com/assets/3669105/19163347/e304376c-8bfb-11e6-8a44-2286126b71f7.gif)

##Android application written in Kotlin for learning purposes, using

To get started, register a firebase account, create an application there and replace the google-services.json in the root folder. In NetworkModule replace the accessKey found under Settings -> Cloud Messaging in the firebase console for your project.
   
    fun provideAccessKey(): String {
        return "key=yourFirebaseServerkey";
    }

[Kotlin] (https://github.com/JetBrains/kotlin)

[Anko] (https://github.com/Kotlin/anko) - DSL for creating UI through code instead of XML

[Conductor] (https://github.com/bluelinelabs/Conductor) - Build View based Android applications

[Retrofit](https://square.github.io/retrofit/) - REST


[Dagger2](https://google.github.io/dagger/) - Dependency injection

[RxJava](https://github.com/ReactiveX/RxJava)



[Icons By Kameleon pics](http://www.kameleon.pics)

[Background image from freepik](http://www.freepik.com/)
