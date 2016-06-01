/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.co.abel.prax.peterbox.data;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Abel Mapharisa <abel.mapharisa@imqs.co.za>
 */
public class SharedFileBean {

    private List<FileData> dataList = new ArrayList<>();

    private void loadFiles() {
        File dir = new File("C:\\Users\\Public\\sharedFiles");
        try {
            if (!dir.exists()) {
                dir.createNewFile();
            }
        } catch (IOException ex) {
            System.err.println("Error occured while creating dir");
            ex.printStackTrace();
        }

        //read file contents
        if (dir.exists() && dir.canRead()) {
            File[] listFiles = dir.listFiles();
            if (listFiles != null && listFiles.length > 0) {
                System.out.println("total files read: " + listFiles.length);
                for (File file : listFiles) {
                    if (file != null && !file.isDirectory()) {
                        String name = file.getName();
                        String path = file.getPath();
                        FileData data = new FileData(name, path);
                        dataList.add(data);
                    }
                }
            } else {
                System.out.println("there are no files to be  read");
            }
        }
    }

    public List<FileData> getFiles() {
        loadFiles();
        return dataList;
    }
}
