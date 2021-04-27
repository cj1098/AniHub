#!/bin/bash
#https://developer.android.com/studio/command-line/adb.html#stopping

createDeepLink() {
	adb shell am start -a android.intent.action.VIEW -n com.xfinity.cloudtvr/com.xfinity.common.view.LaunchActivity -d "http://tv.xfinity.com/$1/$2"
}

getRidOfDumbImports() {
	git checkout .idea/codeStyles/Project.xml
	git checkout .idea/codeStyles/codeStyleConfig.xml
}

createDeepLinkUrl() {
	adb shell am start -a android.intent.action.VIEW -n com.xfinity.cloudtvr/com.xfinity.common.view.LaunchActivity -d "$1"
}

startXTV() {
	adb shell monkey -p com.xfinity.cloudtvr -c android.intent.category.LAUNCHER 1
}

stopXTV() {
	adb shell am force-stop com.xfinity.cloudtvr
}

clearXTV() {
	adb shell pm clear com.xfinity.cloudtvr
}

killAllProcesses() {
	adb kill-all
}

installXTV() {
	adb install -t /Users/mercury/AndroidStudioProjects/android-cloud-tvr/xtv-app/build/outputs/apk/comcastProductionCompat/debug/xtv-app-comcast-production-compat-debug.apk
}

uninstallXTV() {
	adb shell pm uninstall com.xfinity.cloudtvr
}

uninstallXTVButKeepData() {
	adb shell pm uninstall -k com.xfinity.cloudtvr
}

rebootDevice() {
	adb reboot
}

rebootBootloader() {
	adb reboot-bootloader
}

listDevices() {
	adb devices
}

takeScreenShot() {
	adb shell screencap /sdcard/screen.png
	adb pull /sdcard/screen.png /Users/mercury/Downloads/
}

takeVideo() {
	adb shell screenrecord /sdcard/video.mp4
	adb pull /sdcard/video.mp4 /Users/mercury/Downloads/
}

restartADB() {
	adb kill-server
	adb start-server
}

pruneLocal() {
	echo "These branches will be deleted locally"
	git branch --merged $(git rev-parse develop) | egrep -v "(^\*|^\s*(master|develop|release)$)"
	echo "Continue? (Y/N): " 
	read confirm
	if [[ $confirm == Y || $confirm == y ]] {
		git branch --merged $(git rev-parse develop) | egrep -v "(^\*|^\s*(master|develop|release)$)" | xargs git branch -d
		echo "branches deleted successfully"
	} else {
		echo "Exiting"
	}
}

pruneRemote() {
	echo "These branches will be deleted on origin"
	git branch -r --merged | grep -v develop | sed 's/origin\///' | xargs
	echo "Continue? (Y/N): " 
	read confirm
	if [[ $confirm == Y || $confirm == y ]] {
		git branch -r --merged | grep -v develop | sed 's/origin\///' | xargs -n 1 git push --delete origin
		echo "branches deleted successfully"
	} else {
		echo "Exiting"
	}
}

pruneAll() {
	echo "These branches will be deleted locally and on origin"
	git branch --merged $(git rev-parse develop) | egrep -v "(^\*|^\s*(master|develop|release)$)"
	echo "Continue? (Y/N): " 
	read confirm
	if [[ $confirm == Y || $confirm == y ]] {
		git branch --merged $(git rev-parse develop) | egrep -v "(^\*|^\s*(master|develop|release)$)" | xargs git push --delete origin
		git branch --merged $(git rev-parse develop) | egrep -v "(^\*|^\s*(master|develop|release)$)" | xargs git branch -d
		echo "branches deleted successfully"
	} else {
		echo "Exiting"
	}
}
