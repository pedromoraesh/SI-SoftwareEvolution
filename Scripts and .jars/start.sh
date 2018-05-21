#!/bin/bash

dir="$1"

 while read -r repo || [[ -n "$repo" ]]
 do
  git clone $repo

 done < repos.txt

 for repo in $(ls -d */ | cut -f1 -d'/')
 do
 	echo $dir\\$repo >> paths.txt

 done

 bash getCommits.sh

 bash getDiff.sh

 bash getMSEFiles.sh

 bash filter-diffNoImport.sh

 while read -r dir || [[ -n "$dir" ]]
 do

  correctPath=$(echo "$dir" | tr '\\' '//')

  echo "$correctPath"

  java -jar FormatImports.jar "$correctPath"

done < paths.txt

echo "(FormatImport) Imports formated"

bash runExtractFromFamix.sh

# bash runRefDiff.sh

# bash formatRefDiff.sh

bash uknownCount.sh