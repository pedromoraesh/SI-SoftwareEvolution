#!/bin/bash

echo "Running DeprecatedFindings"

while read -r directory || [[ -n "$directory" ]]
	do

    cd "$directory"

    while read -r hash || [[ -n "$hash" ]]
  		do
  		git checkout $hash

      java -jar ../DeprecatedFinding.jar "$directory" "$hash"

    done < "log.txt"

    echo "Job done for "basename $directory

    cd ..

	done < "paths.txt"
