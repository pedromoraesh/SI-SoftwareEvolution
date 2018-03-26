#!/bin/bash


while read -r directory || [[ -n "$directory" ]]
	do
		cd "$directory"

		cd "diffResults"

		grep -o '__UNKNOWN__' Transaction-CBO.txt | wc -l > UknownCount.txt

		cd ../..

	done < "paths.txt"
