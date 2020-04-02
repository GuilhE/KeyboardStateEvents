# KeyboardStateEvents

Get notified through LiveData when Keyboard is open or closed

## Getting started

The first step is to include KeyboardStateEvents into your project, for example, as a Gradle compile dependency:

```groovy
implementation 'com.github.guilhe:keyboard-state-events:${LATEST_VERSION}'
```
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.guilhe/keyboard-state-events/badge.svg)](https://search.maven.org/search?q=g:com.github.guilhe%20AND%20keyboard-state-events) [![Download](https://api.bintray.com/packages/gdelgado/androidKey/boardStateEvents/images/download.svg)](https://bintray.com/gdelgado/android/KeyboardStateEvents/_latestVersion)

## Sample usage

Call `bindKeyboardStateEvents()` in you `AppCompatActivity` or `FragmentActivity` and observe `KeyboardLiveData.state` to get notified:
```java
class MainActivity : AppCompatActivity() {

      override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ...
        KeyboardLiveData.state.observe(this, Observer {...})
        bindKeyboardStateEvents()
    }
}
```
Just that and you're done! 😎

Also, some handy extension functions:  
```java
ComponentActivity.toggleKeyboard()
ComponentActivity.dismissKeyboard()
ComponentActivity.isKeyboardOpen()
ViewGroup.isKeyboardOpen()
```

<img src="https://raw.githubusercontent.com/Guilhe/KeyboardStateEvents/master/sample.gif" alt="Sample"/>
    
## Dependencies
- [androidx.lifecycle:lifecycle-livedata-ktx](https://developer.android.com/jetpack/androidx/releases/lifecycle)
- [androidx.activity:activity-ktx](https://developer.android.com/jetpack/androidx/releases/activity)


## Bugs and Feedback

For bugs, questions and discussions please use the [Github Issues](https://github.com/GuilhE/KeyboardStateEvents/issues).

## Binaries
Additional binaries and dependency information for can be found [here](https://search.maven.org/artifact/com.github.guilhe/keyboard-state-events).  
<a href='https://bintray.com/gdelgado/android/KeyboardStateEvents?source=watch' alt='Get automatic notifications about new "KeyboardStateEvents" versions'><img src='https://www.bintray.com/docs/images/bintray_badge_bw.png'></a>

## LICENSE

Copyright (c) 2020-present GuilhE

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

<http://www.apache.org/licenses/LICENSE-2.0>

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
