#!/bin/bash

dir="C:\Users\Pedro Henrique\Documents\IC\Clones e Scripts"
#dir="C:\Users\"username"\Documents\IC\Clones e Scripts"

#enter in each project, configured in line 59
while read -r folder || [[ -n "$folder" ]]
do

	project="$folder"

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
			echo $commit
		fi

		if echo "$line" | egrep "^diff[ ]--git[ ]*.*$" # diff --git a/x b/x
		then

			var2=$(echo $line | awk -F " " '{print $1,$2,$3,$4}')
			set -- $var2

			path=$(echo $4 | sed 's/^b//') #echo x/asclkc | sed 's/^x//' >> /asclkc
			echo "Path : $path"

			kind=$(echo $3 | awk -F "." '{print $NF}')
			set -- $kind
			echo "Type: $kind"

		fi
		if [ "java" == "$kind" ]; #don't take statics
			then
				if echo "$line" | egrep "^[+|-]import[ ]*.*;$" #+|-import com.nostra13.universalimageloader.core.assist.ContentLengthInputStream;
				then

					var1=$(echo $line | awk -F " " '{print $1,$2,$3}')
					set -- $var1
					noimport=${1%import}

					echo "Project --> $project"
					echo "$path, $commit, $noimport $2" >> "$dir\\Diffs\\$project"NoImport".txt" #....Diff/"gitProjectNoImport.txt"
				fi
		fi
	done < "$dir\\$project\\$project.txt"

	echo "Job Finished for $project"

#.txt with repositories
done < "repository.txt"

echo "All"
#done<"$dir/bib-Diff.txt"
