#!/bin/bash

while read -r directory || [[ -n "$directory" ]]
	do
		cd "$directory"

		git log --full-diff -p --raw --minimal  > $(basename "$directory").txt

		cd ..

	done < "paths.txt"
