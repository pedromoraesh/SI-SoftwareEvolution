#!/bin/bash

while read -r directory || [[ -n "$directory" ]]
	do
		cd "$directory"

		git tag  > tag.txt

		cd ..

	done < "paths.txt"
