#!/bin/bash

dir="$1"

bash gitClone.sh

bash getPaths.sh dir

bash getCommits.sh

bash getDiff.sh

bash getMSEFiles.sh dir

bash filter-diffNoImport.sh

bash formatImports.sh

bash runExtractFromFamix.sh

bash runRefDiff.sh

bash formatRefDiff.sh

bash runDeprecatedFindings.sh

bash uknownCount.sh