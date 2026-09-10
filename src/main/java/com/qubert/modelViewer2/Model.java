package com.qubert.modelViewer2;

import com.qubert.modelViewer2.Data.Vector3D;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;

import java.awt.*;
import java.util.ArrayList;

public class Model {

    Vector3D position;
    Vector3D anchor;

    Vector3D[] vertices;
    int[][] edges;

    public ArrayList<Location> bakedLocations;

    public Model(Vector3D position, Vector3D anchor, Vector3D[] vertices, int[][] edges){
        this.position = position;
        this.anchor = anchor;
        this.vertices = vertices;
        this.edges = edges;
    }

    public void BakeLocations(int steps, World world){
        if(steps < 1)
            return;

        bakedLocations = new ArrayList<>();
        for(int i = 0; i < edges.length; i++){
            for(int j = 0; j < edges[i].length; j++){

                Vector3D x = vertices[i];
                Vector3D y = vertices[edges[i][j]];


                double xDiff = (x.x - y.x)/steps;
                double yDiff = (x.y - y.y)/steps;
                double zDiff = (x.z - y.z)/steps;

                for(int k = 0; k < steps; k++){
                    bakedLocations.add(new Location(world, x.x - (xDiff*i), x.y - (yDiff*i), x.z - (zDiff*i)));
                }
            }
        }
    }

    public Chunk getChunk(World world){
        return new Location(world, position.x, position.y, position.z).getChunk();
    }



}
