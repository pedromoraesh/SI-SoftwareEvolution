#!/bin/bash

dir="$1"

 for repo in $(ls -d */ | cut -f1 -d'/')
 do
 	echo $dir\\$repo >> paths.txt

 done