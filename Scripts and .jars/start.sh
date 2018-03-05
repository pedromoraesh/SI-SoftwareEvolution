#!/bin/bash

dir="C:\Users\Pedro Henrique\Documents\IC\Clones e Scripts"

while read repo; 
do
  git clone $repo

done < repos.txt

for repo in $(ls -d */ | cut -f1 -d'/')
do
	echo $dir\\$repo >> paths.txt

done

bash takeLogs.sh

bash takeDiff.sh

bash takeMSEFiles.sh

bash filter-diffNoImport.sh

bash runExtractFromFamix.sh

bash runRefDiff.sh

bash runDeprecatedFindings.sh



