#!/bin/bash

echo "Running DeprecatedFindings"

while read -r directory || [[ -n "$directory" ]]
	do

	correctPath=$(echo "$directory" | tr '\\' '//')
	echo "> "$correctPath
	
    cd "$directory"

    while read -r hash || [[ -n "$hash" ]]
  		do
  		git checkout -f $hash

      java -jar ../DeprecatedFinding.jar "$correctPath" "$hash"

      echo "> "$hash

    done < "tag.txt"

    echo "Job done for "basename $directory

    cd ..

	done < "paths.txt"

$SHELL
