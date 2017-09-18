#!/bin/bash

while read -r directory || [[ -n "$directory" ]]
	do
		cd "$directory"

		git log --pretty=%h  > log.txt

		cd ..

	done < "paths.txt"
