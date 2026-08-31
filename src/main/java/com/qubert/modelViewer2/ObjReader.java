package com.qubert.modelViewer2;

import com.qubert.modelViewer2.Data.Vector3D;
import org.bukkit.World;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Scanner;

public class ObjReader {

    public ObjReader(String fileName) throws FileNotFoundException {
        File objFile = new File(fileName);
        if(!objFile.exists()){
            throw new FileNotFoundException("Cannot open obj file that does not exist.");
        }
        Scanner myReader = new Scanner(objFile);

        ArrayList<Vector3D> vertices = new ArrayList<>();
        ArrayList<ArrayList<Integer>> connections = new ArrayList<>();

        while(myReader.hasNextLine()){
            String data = myReader.nextLine();
            char[] dataChars = data.toCharArray();
            if(dataChars.length < 1){
                continue;
            }

            char initialCharacter = dataChars[0];
            switch(initialCharacter){
                case 'v':
                    if(dataChars[1] != ' '){
                        break;
                    }

                    int start = 1;
                    double[] vector = new double[3];

                    for(int i = 0; i < 3; i++){
                        int location = data.indexOf(' ', start);
                        int next = data.indexOf(' ', location);
                        if(next == -1){
                            next = data.length();
                        }

                        vector[i] = Double.parseDouble(data.substring(location+1, next));
                        start = location+1;

                    }

                    vertices.add(new Vector3D(vector[0], vector[1], vector[2]));

                    break;
                case 'f':

                        break;
                }

            }


    }

}
