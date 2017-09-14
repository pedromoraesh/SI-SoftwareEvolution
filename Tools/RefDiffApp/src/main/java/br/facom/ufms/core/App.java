package br.facom.ufms.core;

import java.util.List;

import org.eclipse.jgit.lib.Repository;

import refdiff.core.RefDiff;
import refdiff.core.api.GitService;
import refdiff.core.rm2.model.refactoring.SDRefactoring;
import refdiff.core.util.GitServiceImpl;

public class App {
    public static void main(String[] args) throws Exception {
        RefDiff refDiff = new RefDiff();
        GitService gitService = new GitServiceImpl();
        
        //Data that will be changed by the user
        String path = "C:/Users/Pedro Henrique/Documents/IC/Clones e Scripts/undertow";
        String git_URL = "https://github.com/undertow-io/undertow.git";
        String commit_HASH = "faa15a64b";
        
        ExtractData extract = new ExtractData();
        String content;
        try (Repository repository = gitService.cloneIfNotExists(path, git_URL)) {
            List<SDRefactoring> refactorings = refDiff.detectAtCommit(repository, commit_HASH);
            for (SDRefactoring r : refactorings) {
            	content = r.getRefactoringType().getDisplayName() + ";" + r.getEntityBefore().key() + ";" + r.getEntityAfter().key();
            	content = commit_HASH + content;
            	extract.export(path, content);
                //System.out.printf("%s\t%s\t%s\n", r.getRefactoringType().getDisplayName(), r.getEntityBefore().key(), r.getEntityAfter().key());
            }
        }
    }
}
