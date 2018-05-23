

 while read -r dir || [[ -n "$dir" ]]
 do

  correctPath=$(echo "$dir" | tr '\\' '//')

  echo "$correctPath"

  java -jar FormatImports.jar "$correctPath"

done < paths.txt

echo "(FormatImport) Imports formated"