#!/bin/bash

echo "Starting to extract git tags"

while read -r directory || [[ -n "$directory" ]]
	do
		cd "$directory"

		git tag  > tag.txt

		echo "Job done for "basename $directory

		cd ..

	done < "paths.txt"
