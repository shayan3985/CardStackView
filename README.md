![Logo](https://github.com/shynline/CardStackView/blob/master/images/logo.png)

# CardStackView [ ACTIVE ]
    -- read realise notes before migrating to this library

![Platform](http://img.shields.io/badge/platform-android-blue.svg?style=flat)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)


# Overview

![Overview](https://github.com/shynline/CardStackView/blob/master/images/overview-github.gif)

# Contents

- [Features](#features)
    - [Manual Swipe](#manual-swipe)
    - [Automatic Swipe](#automatic-swipe)
    - [Move to Origin](#move-to-origin)
    - [Reverse](#reverse)
    - [StackFrom](#stackfrom)
    - [VisibleCount](#visiblecount)
    - [ElevationEnabled](#elevationenabled)
    - [TranslationDiff](#translationdiff)
    - [ScaleDiff](#scalediff)
    - [Overlay](#overlay)
    - [SwipeEnabled](#swipeenabled)
    - [SwipeDirection](#swipedirection)
    - [SwipeThreshold](#swipethreshold)
- [Installation](#installation)
- [License](#license)

# Features

## Manual Swipe

![ManualSwipe](https://github.com/shynline/CardStackView/blob/master/images/manual-swipe.gif)

## Automatic Swipe

![AutomaticSwipe](https://github.com/shynline/CardStackView/blob/master/images/automatic-swipe.gif)

Custom animation is available when automatic swiping!

```java
CardStackView#swipe(SwipeDirection, AnimatorSet)
```

## Move to Origin

![MoveToOrigin](https://github.com/shynline/CardStackView/blob/master/images/move-to-origin.gif)

## Reverse

![Reverse](https://github.com/shynline/CardStackView/blob/master/images/reverse.gif)

```java
CardStackView#reverse();
```

## StackFrom

| Default | Value | Sample |
| :----: | :----: | :----: |
| ✅ | Top | ![StackFrom-Top](https://github.com/shynline/CardStackView/blob/master/images/stack-from-top.png) |
| | Bottom | ![StackFrom-Bottom](https://github.com/shynline/CardStackView/blob/master/images/stack-from-bottom.png) |

```xml
<com.shynline.android.cardstackview.CardStackView
    app:stackFrom="top"
    or
    app:stackFrom="bottom"/>
```

```java
CardStackView#setStackFrom(StackFrom.Top);
or
CardStackView#setStackFrom(StackFrom.Bottom);
```

## VisibleCount

This value must be greater than 0.

| Default | Value | Sample |
| :----: | :----: | :----: |
| ✅ | 3 | ![VisibleCount-3](https://github.com/shynline/CardStackView/blob/master/images/visible-count-3.png) |
| | 4 | ![VisibleCount-4](https://github.com/shynline/CardStackView/blob/master/images/visible-count-4.png) |

```xml
<com.shynline.android.cardstackview.CardStackView
    app:visibleCount="3"
    or
    app:visibleCount="4"/>
```

```java
CardStackView#setVisibleCount(3);
or
CardStackView#setVisibleCount(4);
```

## ElevationEnabled

| Default | Value | Sample |
| :----: | :----: | :----: |
| ✅ | true | ![ElevationEnabled](https://github.com/shynline/CardStackView/blob/master/images/elevation-enabled.png) |
| | false | ![ElevationDisabled](https://github.com/shynline/CardStackView/blob/master/images/elevation-disabled.png) |

```xml
<com.shynline.android.cardstackview.CardStackView
    app:elevationEnabled="true"
    or
    app:elevationEnabled="false"/>
```

```java
CardStackView#setElevationEnabled(true);
or
CardStackView#setElevationEnabled(false);
```

## TranslationDiff

The unit of TranslationDiff is **dp**.

| Default | Value | Sample |
| :----: | :----: | :----: |
| | 8dp | ![TranslationDiff-18](https://github.com/shynline/CardStackView/blob/master/images/translation-diff-8.png) |
| ✅ | 12dp | ![TranslationDiff-12](https://github.com/shynline/CardStackView/blob/master/images/translation-diff-12.png) |
| | 16dp | ![TranslationDiff-16](https://github.com/shynline/CardStackView/blob/master/images/translation-diff-16.png) |

```xml
<com.shynline.android.cardstackview.CardStackView
    app:translationDiff="12"/>
```

```java
CardStackView#setTranslationDiff(12f);
```

## ScaleDiff

The range of ScaleDiff is 0.0 - 1.0.

| Default | Value | Sample |
| :----: | :----: | :----: |
| ✅ | 0.02 | ![ScaleDiff-2](https://github.com/shynline/CardStackView/blob/master/images/scale-diff-2.png) |
| | 0.1 | ![ScaleDiff-10](https://github.com/shynline/CardStackView/blob/master/images/scale-diff-10.png) |

```xml
<com.shynline.android.cardstackview.CardStackView
    app:scaleDiff="0.02"/>
```

```java
CardStackView#setScaleDiff(0.02f);
```

## Overlay

| Value | Sample |
| :----: | :----: |
| Left | ![Overlay-Left](https://github.com/shynline/CardStackView/blob/master/images/overlay-left.png) |
| Right | ![Overlay-Right](https://github.com/shynline/CardStackView/blob/master/images/overlay-right.png) |

```xml
<com.shynline.android.cardstackview.CardStackView
    app:leftOverlay="@layout/overlay_left"
    or
    app:rightOverlay="@layout/overlay_right"/>
```

```java
CardStackView#setLeftOverlay(R.layout.overlay_left);
or
CardStackView#setRightOverlay(R.layout.overlay_right);
```

## SwipeEnabled

| Default | Value | Sample |
| :----: | :----: | :----: |
| ✅ | true | ![SwipeEnabled](https://github.com/shynline/CardStackView/blob/master/images/swipe-enabled.gif) |
| | false | ![SwipeDisabled](https://github.com/shynline/CardStackView/blob/master/images/swipe-disabled.gif) |

```xml
<com.shynline.android.cardstackview.CardStackView
    app:swipeEnabled="true"
    or
    app:swipeEnabled="false"/>
```

```java
CardStackView#setSwipeEnabled(true);
or
CardStackView#setSwipeEnabled(false);
```

## SwipeDirection

| Default | Value | Sample |
| :----: | :----: | :----: |
| ✅ | Freedom | ![SwipeDirection-Freedom](https://github.com/shynline/CardStackView/blob/master/images/swipe-direction-freedom.gif) |
| | Horizontal | ![SwipeDirection-Horizontal](https://github.com/shynline/CardStackView/blob/master/images/swipe-direction-horizontal.gif) |
| | Vertical | ![SwipeDirection-Vertical](https://github.com/shynline/CardStackView/blob/master/images/swipe-direction-vertical.gif) |

```xml
<com.shynline.android.cardstackview.CardStackView
    app:swipeDirection="freedom"
    or
    app:swipeDirection="horizontal"
    or
    app:swipeDirection="vertical"/>
```

```java
CardStackView#setSwipeDirection(SwipeDirection.FREEDOM);
or
CardStackView#setSwipeDirection(SwipeDirection.HORIZONTAL);
or
CardStackView#setSwipeDirection(SwipeDirection.VERTICAL);
```

## SwipeThreshold

The range of SwipeThreshold is 0.0 - 1.0.

| Default | Value | Sample |
| :----: | :----: | :----: |
| ✅ | 0.75 | ![SwipeThreshold-75%](https://github.com/shynline/CardStackView/blob/master/images/swipe-threshold-75.gif) |
| | 0.1 | ![SwipeThreshold-10%](https://github.com/shynline/CardStackView/blob/master/images/swipe-threshold-10.gif) |

```xml
<com.shynline.android.cardstackview.CardStackView
    app:swipeThreshold="0.75"/>
```

```java
CardStackView#setSwipeThreshold(0.75f);
```

# Installation

```groovy
dependencies {
    implementation 'com.github.shynline:CardStackView:2.0.1'
}
```

# License

```
Copyright 2017 yuyakaido

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
