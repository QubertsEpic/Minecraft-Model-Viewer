package com.qubert.modelViewer2;

import com.qubert.modelViewer2.Data.Vector3D;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;

public class Model {

    Vector3D position;
    Vector3D anchor;

    Vector3D[] vertices;
    int[][] edges;

    public Model(Vector3D position, Vector3D anchor, Vector3D[] vertices, int[][] edges){
        this.position = position;
        this.anchor = anchor;
        this.vertices = vertices;
        this.edges = edges;
    }

    public Chunk getChunk(World world){
        return new Location(world, position.x, position.y, position.z).getChunk();
    }


}
