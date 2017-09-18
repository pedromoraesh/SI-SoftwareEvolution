#!/bin/bash

while read -r directory || [[ -n "$directory" ]]
	do

    cd "$directory"

    while read -r hash || [[ -n "$hash" ]]
  		do
  		git checkout $hash

      java -jar ../DeprecatedFinding.jar "$directory" "$hash"

    done < "tag.txt"

    cd ..

	done < "paths.txt"
