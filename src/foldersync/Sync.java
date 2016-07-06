/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foldersync;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import static java.nio.file.StandardCopyOption.COPY_ATTRIBUTES;
import java.util.List;

/**
 *
 * @author xach
 */
public class Sync {

    private Directory source;
    private Directory dest;
    private List<String> watch;
    private List<String> dontWatch;

    public Sync(String source, String dest, List<String> watch, List<String> dontWatch) throws IOException {
        this.source = new Directory(source);
        this.watch = watch;
        this.dontWatch = dontWatch;
        try {
            this.dest = new Directory(dest);
        } catch (IOException e) {
            this.dest = makeDir(dest);
        }

    }

    public void syncFolders() throws IOException {

        for (File file : source.listFiles()) {
            if (checkDirectory(file)) {

            //System.out.println("Directory: "+ file.getPath());
                Sync sync = new Sync(file.getPath(), replacePathWithDest(file.getPath()), watchList(), dontWatchList());
                sync.syncFolders();
                
            } else if(!file.isDirectory() && isWachDirectory(file)) {
                //System.out.println("file: "+ file.getPath());
                File target = new File(replacePathWithDest(file.getPath()));
                File sourceFile = file;
                if (targetOld(sourceFile, target)) {
                    Path path = target.toPath();
                    if (target.exists()) {
                        target.delete();
                    }
                    System.out.println("file copied: "+ target.getPath());
                    Files.copy(sourceFile.toPath(), path, COPY_ATTRIBUTES);
                }
            }
        }
    }

    private boolean targetOld(File source, File target) {
        if (!target.exists() || source.lastModified() > target.lastModified()) {
            return true;
        }
        return false;
    }

    private boolean checkDirectory(File file) {
        if (file.isDirectory() && !inDontWatch(file)) {
            return true;
        }
        return false;
    }
    
    private boolean isWachDirectory(File file) {
        String[] directories = file.getParentFile().getPath().split(File.separator);
        for(String directoryName: directories) {
            // System.out.println(directoryName);
            if(equalsWatch(directoryName)) {
                return true;
            }
        }
        return false;
    }
    private boolean equalsWatch(String directory) {
        for (String folderName : watch) {
              //  System.out.println("file: " + directory + " FolderName: " + folderName);
              //  System.out.println(directory.equals(folderName));
            if (directory.equals(folderName)) {

                return true;
            }
        }
        return false;
    }
    private boolean inDontWatch(File file) {
        for (String folderName : dontWatch) {
            if (file.getName().equals(folderName)) {
//                System.out.println("file: " + file.getName() + " FolderName: " + folderName);
//                System.out.println(file.getName() == folderName);
                return true;
            }
        }
        return false;
    }

    private String replacePathWithDest(String path) {
        return path.replace(this.source.getPath(), this.dest.getPath());
    }

    private Directory makeDir(String path) throws IOException {
        File newDir = new File(path);
        newDir.mkdir();
        Directory dir = new Directory(newDir.getPath());
        return dir;
    }

    private List watchList() {
        return watch;
    }

    private List dontWatchList() {
        return dontWatch;
    }
}
