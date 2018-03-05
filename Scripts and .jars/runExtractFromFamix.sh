#!/bin/bash


echo "Running ExtractFromFamix"

while read -r directory || [[ -n "$directory" ]]
	do

    cd "$directory"

      java -jar ../ExtractFromFamix.jar "$directory"

    echo "(ExtractFromFamix) Done for "basename $directory
    
    cd ..

	done < "paths.txt"