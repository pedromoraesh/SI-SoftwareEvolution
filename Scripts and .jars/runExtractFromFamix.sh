#!/bin/bash


echo "Running ExtractFromFamix"

while read -r dir || [[ -n "$dir" ]]
	do

	correctPath=$(echo "$dir" | tr '\\' '//')

    java -jar ExtractFromFamix.jar "$correctPath"

    repository=basename $directory

    echo "(ExtractFromFamix) Done for $repository"

	done < "paths.txt"