#!/bin/bash

echo "Starting to extract git log"

while read -r directory || [[ -n "$directory" ]]
	do
		cd "$directory"

		git log --reverse --pretty=%h --first-parent > log.txt

		echo "Job done for "basename $directory

		cd ..

	done < "paths.txt"
