#!/bin/bash

echo "Starting to extract git diff"

while read -r directory || [[ -n "$directory" ]]
	do
		cd "$directory"

		git log --full-diff -p --raw --minimal  > $(basename "$directory").txt

		echo "Job done for "basename $directory

		cd ..

	done < "paths.txt"
