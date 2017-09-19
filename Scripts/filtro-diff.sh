#!/bin/bash

dir="C:\Users\Pedro Henrique\Documents\IC\Clones e Scripts"
#dir="/home/facom/Documents/Teste/GIT/Tools/gitDiff"

#entra em cada projeto do Git
while read -r pasta || [[ -n "$pasta" ]]
do
	#var0=$(echo $pasta | awk -F "." '{print $1,$2}')
	#set -- $var0

	projeto="$pasta"

	i=$(($i+1))
	echo $projeto,$i

	pasta=$pasta.txt

	lineImport=""

	#pega cada linha do arq ospntjava para interar no while
	while read -r line || [[ -n "$line" ]]
	do

		if echo "$line" | egrep "^commit[ ][a-z0-9]{40}$"
		then
			#commit=$line
			var1=$(echo $line | awk -F " " '{print $1,$2}')
			set -- $var1
			commit=$2
			echo $commit
		fi

		if echo "$line" | egrep "^diff[ ]--git[ ]*.*$" # diff --git a/x b/x
		then

			var2=$(echo $line | awk -F " " '{print $1,$2,$3,$4}')
			set -- $var2
			path=$(echo $4 | sed 's/^b//') #echo x/asclkc | sed 's/^x//' >> /asclkc
			#aux=$(echo $path | sed 's/.java$//')

			echo "Path : $path"
		fi

		if echo "$line" | egrep "^[+|-]import[ ]*.*;$" #+import cz.msebera.android.httpclient.client.methods.HttpOptions;
		then

			var1=$(echo $line | awk -F " " '{print $1,$2,$3}')
			set -- $var1

			if [ "" == "$3" ]; #não pega os static
				then

				#importtext=$(echo "$line" | sed 's/^[+]import[ ]//' ) #remover +import
				##filtro=$(echo "$importtext" | sed 's/;$//' ) # saids: cz.msebera.android.httpclient.client.methods.HttpOptions
				#echo $importtext
				##lineImport+="$filtro, " #imports+="$filtro, "
				#echo "$projeto$path, $commit, $data, $importtext"

				echo "Projeto --> $projeto"
				echo "$path, $commit, $line" >> "$dir\\diffs\\$projeto.txt" #/home/facom/Documents/Teste/GIT/gitDiff/Imports
			fi
		fi

	done < "C:\Users\Pedro Henrique\Documents\IC\Clones e Scripts\\""$projeto""\\""$projeto.txt"

	echo "FIM de Pegar os IMPORTS do $projeto"

done < "repository.txt"

echo "Tudo "
#done<"$dir/bib-Diff.txt"
