#!/bin/bash

IFS=";"
while read -r line || [[ -n "$line" ]]
	do
	
  	campos=($line)
    
    echo "${campos[0]};${campos[1]};${campos[3]};${campos[4]}" > DeprecatedWithMsg.txt

	done < "DeprecatedWithMsgg.txt"

$SHELL