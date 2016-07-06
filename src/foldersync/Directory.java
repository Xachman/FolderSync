/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foldersync;

import java.io.File;
import java.io.IOException;

/**
 *
 * @author xach
 */
public class Directory {
    private File directory;
    
    public Directory(String directory) throws IOException{
        this.directory = new File(directory);
        if(!this.directory.isDirectory()) {
            throw new IOException("Not a Direcotry: "+this.directory.getPath());
        }
    }
    
    public File[] listFiles() {
        return directory.listFiles();
    }
    
    public String getPath() {
        return directory.getPath();
    }
    
    
}
