#!/bin/sh
# A Linux shell script I made to make it easier to test on my machine

rm build/libs/*
./gradlew build \
    && command cp ./build/libs/nospawningonice-*-1.20.1.jar /home/redram/.local/share/multimc/instances/Latest/.minecraft/mods/

true | (command mpv '/home/redram/Music/terminal_beep.wav' > /dev/null 2> /dev/null &)
echo
