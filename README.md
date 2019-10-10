# CardSwipeDemo

An Android appliction showing swipable cards made using Kotlin, RxJava 2 and Architecture Components.

## Setup

- Clone the repo
- The URL to JSON file is in `Constants.kt` file. Modify it if required.
- Run the project!

## Development Decisions

1. <a href="https://github.com/janishar/PlaceHolderView">PlaceHolderView</a>: This library makes it quite convenient to develop swipable cards. In this way, I didn't have to reinvent the wheel.
2. <a href="https://github.com/amitshekhariitbhu/Fast-Android-Networking">Rx2AndroidNetworking</a>: Although Retrofit works quite well with RxJava, but the requirement was to make just one API call and hence used this library.
3. MVVM Architecture: Separation of Concerns has been taken care of using MVVM (Model-View-ViewModel) architecture. The code strictly related to the activity lies in the activity itself and rest of the code has been extracted to `Repository` class.
