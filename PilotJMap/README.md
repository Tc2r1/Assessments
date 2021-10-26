# PFJ Android Coding Challenge! - Nudennie White


**Instructions**
The purpose of this application is to load a set of locations from a
JSON file and display the locations as a pin on the map.  
The developer must use the following libraries:
-  KotlinX Serialization
-  Retrofit & OKHttp
-  Kotlin Coroutines
-  Live Data (AndroidX)
-  ViewModel (AndroidX)
-  GoogleMaps
-  Leak Canary
-  Dagger Hilt  
   The architecture should be MVVM.  
   The acceptance criteria for this program is as follows:
-  Application shows a map on launch and requests locations
-  Locations loaded are sent to the map as markers
-  On rotation, the API is not called again  
     The developer must not override the system’s default behavior for configuration change
-  The app is written entirely in Kotlin
-  All libraries required above are used
-  Dependency injection is used, where appropriate
-  Unit testing is performed on the API layer
-  Pin markers show up for every entry the API provides
-  Coroutines are used, no RxJava or other async methods should be used


![Project Instructions](/PilotMap/instructions.pdf)

Open the Project in Android Studio.

## Screenshots

![Screen shot 1](/PilotMap/preview_01.png) ![Screen shot 2](/PilotMap/preview_02.png) ![Screen shot 3](/PilotMap/preview_03.png)

## Nudennie's Notes on Project:

Design Principles I was lead by: 
**Separation of Concerns** 
Divided code into classes, each with separate, well-defined responsibilities
Code is more Organized and Easier to debug. Code is much more Modular. 

**Drive UI from a model** 
Kept Model code functionality independent from the UI Controller (View objects)
and app components in the app. They are unaffected by the app's lifecycle.

**Encapsulation** 
The Notion of restricting direct access to an object's fields.

**UI Controller** (Fragments/Activities) Displays data and captures OS and user events (user touches).

**ViewModel** Holds all of the data needed for the UI and prepares it for display (this is where calculations are done), Holds LiveData classes
ViewModel survives configuration changes.

**LiveData** LiveData classes are crucial for communicating information from the viewModel to the UI Controller that it should upadate and redraw the screen.

I could have done more but I feel like I've put more than enough energy into this assessment. lol and my face really hurts. (TODD: remove this sentence)

## Libraries Utilized: 

Android Map Utils
constraint layout
Moshi
Dagger2
Hilt
Kotlinx Coroutines
Jetpack
Kotlinx Serialization
Leak Canary
Android Lifecycle (LiveData ViewModel)
Material
Navigation Arch
OKHttp
Play Services
RxAndroid
RxJava3 Retrofit
Timber
GoogleMaps

## Extras:

I implemented a navgraph to take advantage of Android Navigation.
To actually use It I created an About Fragment Lol
I restructured the code to a more common accepted design pattern of a Master Activity that cycles fragments.
I refactored MapActivity into MapFragment so that I could use the navgraph (I like this thing k dont judge me!)
I displayed multiple ways of calling the API to retrieve data. 
I Designed the application in the MVVM style.  (Model View ViewModel utilizing LiveData to update my views)
I used Kotlin Databinding as well as Dagger Databinding in order to Inject when possible. 
By using the above I was able to take most logical code completely out of the views, having the objects react from the ViewModels themselves. 
I tried to employ good annotation but eventually got tired. About page is lacking lol. 


*## Missed Goals: 
I did not Unit Test the API Layer. I know I could and how to, but I'm pretty beat and got carried away in functionality. 
There are a lot of UI Improvements from the base project I would like to implement, lol but this is suppose to be an assessment so I have to stop >_<