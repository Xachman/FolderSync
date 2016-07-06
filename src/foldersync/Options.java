/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foldersync;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author xach
 */
public class Options {
    
    private List<JSONObject> watches;
    private String source;
    private String dest;
    
    public Options(String config) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        JSONObject options =  (JSONObject) parser.parse(new String(Files.readAllBytes(new File(config).toPath())));
        watches = (JSONArray) options.get("watches");
        source = (String) options.get("source");
        dest = (String) options.get("dest");
    }
    
    public List watchesList() {
        List<HashMap> returnList = new ArrayList<HashMap>();
        
        for (JSONObject watch: watches) {
            HashMap hm = new HashMap();
            hm.put("sourceDir", source+watch.get("dir"));
            hm.put("destDir", dest+watch.get("dir"));
            hm.put("watch", (List) watch.get("watchlist"));
            hm.put("dontwatch", (List) watch.get("dontwatch"));
            hm.put("dir", source+watch.get("dir"));
            returnList.add(hm);
        }
        
        return returnList;
    }
}
