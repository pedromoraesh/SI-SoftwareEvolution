#!/bin/bash

echo "Filtering imports on git diff"

while read -r path || [[ -n "$path" ]]
do
	
	dir="${path%/*}"
	correctPath=$(echo "$dir" | tr '//' '\\')
	project=$(basename "$path")

	i=$(($i+1))
	echo $project,$i

	lineImport=""

	kind=""

	#read each line of the diff.txt. configured in line 54
	while read -r line || [[ -n "$line" ]]
	do

		if echo "$line" | egrep "^commit[ ][a-z0-9]{40}$"
		then
			#commit=$line
			var1=$(echo $line | awk -F " " '{print $1,$2}')
			set -- $var1
			commit=$2
			#echo $commit
		fi

		if echo "$line" | egrep "^diff[ ]--git[ ]*.*$" # diff --git a/x b/x
		then

			var2=$(echo $line | awk -F " " '{print $1,$2,$3,$4}')
			set -- $var2

			path=$(echo $4 | sed 's/^b//') #echo x/asclkc | sed 's/^x//' >> /asclkc
			#echo "Path : $path"

			kind=$(echo $3 | awk -F "." '{print $NF}')
			set -- $kind
			#echo "Type: $kind"

		fi
		if [ "java" == "$kind" ]; #don't take statics
			then
				if echo "$line" | egrep "^[+|-]import[ ]*.*;$" #+|-import com.nostra13.universalimageloader.core.assist.ContentLengthInputStream;
				then

					var1=$(echo $line | awk -F " " '{print $1,$2,$3}')
					set -- $var1
					noimport=${1%import}

					#echo "Project --> $project"
					mkdir -p $project/imports
					echo "$path, $commit, $noimport $2" >> "$correctPath\\$project\\imports\\$project.txt" #....Diff/"gitProjectNoImport.txt"
				fi
		fi
	done < "$correctPath\\$project\\$project.txt"

	echo "Job Finished for $project"

#.txt with repositories
done < "paths.txt"

echo "All"
#done<"$dir/bib-Diff.txt"
