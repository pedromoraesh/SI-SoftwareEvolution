#!/bin/bash

echo "Running RefDiff"

while read -r directory || [[ -n "$directory" ]]
	do

    cd "$directory"

    url=$(git config --get remote.origin.url)

    while read -r hash || [[ -n "$hash" ]]
  		do

      java -jar ../RefDiff.jar "$directory" "$url" "$hash"

    done < "log.txt"

    echo "Job done for "basename $directory
    cd ..

	done < "paths.txt"
