#!/bin/bash

echo "Starting to execute JDT2Famix"

dir="$1"

while read -r directory || [[ -n "$directory" ]]
  do

    cd "$directory"

    correctPath=$(echo "$directory" | tr '\\' '//')

    rm -rf msefiles

    mkdir -p msefiles

    while read -r hash || [[ -n "$hash" ]]
      do

      git checkout $hash

      java -cp "C:/Users/Pedro Henrique/Documents/IC/JDT2Famix/jdt2famix-1.0.3/*" com.feenk.jdt2famix.injava.Main "$correctPath" "$hash"

      mv *.mse msefiles/

    done < "log.txt"
 

    echo "(JDT2Famix) Done for "basename $directory

    cd ..

  done < "paths.txt"