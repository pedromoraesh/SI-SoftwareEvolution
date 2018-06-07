 #!/bin/bash

while read -r repo || [[ -n "$repo" ]]
do

 folder=$(echo "$repo" | tr '/' '_')
 echo $folder
 git clone "https://github.com/"$repo $folder

done < repos0-50.txt
$SHELL