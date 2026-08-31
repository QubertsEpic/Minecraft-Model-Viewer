package com.qubert.modelViewer2;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.NotDirectoryException;
import java.util.ArrayList;

public class ModelIndexer {

    public String FolderLocation;
    public ArrayList<Model> ModelList;

    public ModelIndexer(String folderLocation) throws NotDirectoryException {
        File folder = new File(folderLocation);
        if(!folder.exists()){
            throw new NotDirectoryException("Unable to open directory.");
        }
        FolderLocation = folderLocation;
        Index();
    }

    public void Index(){

    }


}
