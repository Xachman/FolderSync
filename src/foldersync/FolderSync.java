/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foldersync;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import org.json.simple.parser.ParseException;

/**
 *
 * @author xach
 */
public class FolderSync {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        //JSONParser parser = new JSONParser();
        
        try {
            Options options = new Options(args[0]);
            List<HashMap> watchList = options.watchesList();
            while(true) {
                for(HashMap watch: watchList) {
                    // System.out.println(watch);
                    Sync sync = new Sync((String) watch.get("sourceDir"), (String) watch.get("destDir"), (List) watch.get("watch"), (List) watch.get("dontwatch"));
                    sync.syncFolders();
                } 
                Thread.sleep(100);
            }
        } catch(IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } catch(ParseException e) {
            e.printStackTrace();
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }
    
}
