#!/bin/bash

echo "Starting to format ReffDiff"

while read -r directory || [[ -n "$directory" ]]
	do
		cd "$directory"

		sed -i 's/src\/test\/java\///g' DataFromRefDiff.txt
		sed -i 's/src\/main\/java\///g' DataFromRefDiff.txt
		sed -i 's/\///g' DataFromRefDiff.txt

		mv DataFromRefDiff.txt Transaction-RefDiff.txt

		cd ..

done < "paths.txt"
