#!/bin/bash
function test(){
	COUNTER=0
	while [ $COUNTER -lt $1 ]; do 
		adb shell am instrument -w -r   -e debug false jiachens.umich.edu.autobrowsing.test/androidx.test.runner.AndroidJUnitRunner
		adb shell am force-stop com.android.chrome
		for element in `adb shell su -c ls /data/user/0/com.android.chrome/`
		do
			if [ "$element" != "lib" ] && [ "$element" != "shared_prefs" ]
			then
				adb shell su -c rm -rf /data/user/0/com.android.chrome/$element
			fi
		done 
		let COUNTER=COUNTER+1 
	done 
}

# first clear cache
adb shell am force-stop com.android.chrome
for element in `adb shell su -c ls /data/user/0/com.android.chrome/`
do
	if [ "$element" != "lib" ] && [ "$element" != "shared_prefs" ]
	then
		adb shell su -c rm -rf /data/user/0/com.android.chrome/$element
	fi
done 

# $1 stands for the test times
test $1
