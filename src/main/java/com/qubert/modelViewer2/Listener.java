package com.qubert.modelViewer2;

import com.qubert.modelViewer2.Data.Matrix3D;
import com.qubert.modelViewer2.Data.Matrix4D;
import com.qubert.modelViewer2.Data.Vector3D;
import com.qubert.modelViewer2.Data.Vector4D;
import io.papermc.paper.event.block.BlockBreakBlockEvent;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Listener implements org.bukkit.event.Listener {

    World theWorld;
    Model model;

    Location zero;

    double sinDegrees = 0d;

    private Location PointToLocation (World world, Vector3D loc){
        return new Location(world, loc.x, loc.y, loc.z);
    }

    public Listener(Plugin plugin) throws FileNotFoundException {
        zero = new Location(plugin.getServer().getWorlds().get(0), 0, 100, 0);
        theWorld = plugin.getServer().getWorlds().get(0);
        model = ObjReader("Cube.obj", new Vector3D(0, 100, 0), new Vector3D(0, 0, 0));
        model.BakeLocations(5, theWorld);
        new BukkitRunnable() {
            @Override
            public void run() {
                sinDegrees += 0.2;

                Matrix3D scaleRotMatrix = getMatrix3D();

                Vector3D translationMatrix = new Vector3D(0,0,0);
                translationMatrix = translationMatrix.Sum(model.anchor);
                translationMatrix = translationMatrix.Sum(model.position);

                Matrix4D informationMatrix = new Matrix4D(scaleRotMatrix, translationMatrix);

                for(int i = 0; i < model.edges.length; i++){
                    for(int j = 0; j < model.edges[i].length; j++){

                        Vector4D current14D = new Vector4D(model.vertices[i], 0);
                        Vector4D current24D = new Vector4D(model.vertices[model.edges[i][j]], 0);

                        Vector3D current1 = current14D.MatrixMultiplication(informationMatrix).toVector3D();
                        Vector3D current2 = current24D.MatrixMultiplication(informationMatrix).toVector3D();

                        DrawLine(theWorld, PointToLocation(theWorld, current1), PointToLocation(theWorld, current2), 20);
                    }
                }
            }
        }.runTaskTimerAsynchronously(plugin, 0L, 1L);

    }

    private Matrix3D getMatrix3D() {
        double rads = Math.toRadians(sinDegrees);
        double sin = Math.sin(rads);
        double cos = Math.cos(rads);

        Matrix3D rotMat = new Matrix3D(cos, 0, sin,
                                    0, 1, 0,
                                        -sin, 0, cos);

        double scaleFactor = 1;
        Matrix3D scaleMatrix = new Matrix3D(scaleFactor, scaleFactor ,scaleFactor,
                                            scaleFactor, scaleFactor, scaleFactor,
                                            scaleFactor, scaleFactor, scaleFactor);

        return rotMat.MatrixMultiplication(scaleMatrix);
    }

    private void DrawPoint(World theWorld, Location location){

        theWorld.spawnParticle(Particle.DUST, location, 1, 0, 0, 0, new Particle.DustOptions(Color.BLACK, 4));
    }

    private void DrawLine(World world, Location location1, Location location2, int steps){
        DrawLine(world, location1, location2, steps, Color.BLACK, 2);
    }

    private void DrawLine(World world, Location location1, Location location2, int steps, Color colour){
        DrawLine(world, location1, location2, steps, colour, 2);
    }

    private void DrawLine(World world, Location location1, Location location2, int steps, float size) {
        DrawLine(world, location1, location2, steps, Color.BLACK, size);
    }

    private void DrawLine(World world, Location location1, Location location2, int steps, Color colour, float size){
        double differenceX = (location1.getX() - location2.getX()) / (steps-1);
        double differenceY = (location1.getY() - location2.getY()) / (steps-1);
        double differenceZ = (location1.getZ() - location2.getZ()) / (steps-1);

        for(int i = 0; i < steps; i++){

            Location loc = new Location(world, location1.getX() - (differenceX*i), location1.getY() - (differenceY * i), location1.getZ() - (differenceZ * i));

            world.spawnParticle(Particle.DUST, loc, 1, 0, 0, 0, new Particle.DustOptions(colour, size));
        }
    }

    private Model ObjReader(String fileName, Vector3D pos, Vector3D anc) throws FileNotFoundException {
        File objFile = new File(fileName);
        if (!objFile.exists()) {
            throw new FileNotFoundException("Cannot open obj file that does not exist.");
        }
        Scanner myReader = new Scanner(objFile);

        ArrayList<Vector3D> vertices = new ArrayList<>();
        HashMap<Integer,ArrayList<Integer>> connections = new HashMap<Integer, ArrayList<Integer>>();

        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            char[] dataChars = data.toCharArray();
            if (dataChars.length < 1) {
                continue;
            }

            char initialCharacter = dataChars[0];
            int start;
            switch (initialCharacter) {
                case 'v':
                    if (dataChars[1] != ' ') {
                        break;
                    }

                    start = 1;
                    double[] vector = new double[3];

                    for (int i = 0; i < 3; i++) {
                        int location = data.indexOf(' ', start);
                        int next = data.indexOf(' ', location+1);
                        if (next == -1) {
                            next = data.length();
                        }

                        vector[i] = Double.parseDouble(data.substring(location + 1, next));
                        start = location + 1;

                    }

                    vertices.add(new Vector3D(vector[0], vector[1], vector[2]));

                    break;
                case 'f':
                    if (dataChars[1] != ' ') {
                        break;
                    }
                    ArrayList<Integer> numbers = new ArrayList<>();
                    start = 1;
                    int next = data.indexOf(' ', start+1);

                    while(next != start){
                        String faceData = data.substring(start+1, next);

                        int slashIndex = faceData.indexOf('/');
                        if(slashIndex != -1){
                            faceData = faceData.substring(0, slashIndex);
                        }
                        numbers.add(Integer.parseInt(faceData)-1);

                        start = next;
                        next = data.indexOf(' ', start+1);
                        if(next == -1){
                            next = data.length();
                        }
                    }

                    if(numbers.size() > 2){
                        for(int i = 0; i < numbers.size()-1; i++){
                            ArrayList<Integer> list = new ArrayList<>();
                            if(connections.containsKey(numbers.get(i)))
                            {
                                list = connections.get(numbers.get(i));
                            }
                            list.add(numbers.get(i+1));
                            connections.put(numbers.get(i), list);
                        }
                    }

                    break;
            }

        }
        int[][] connectionArray = new int[vertices.size()][];
        for(int i = 0; i < connectionArray.length; i++){
            if(!connections.containsKey(i)) {
                connectionArray[i] = new int[0];
                continue;
            }

            ArrayList<Integer> values =  connections.get(i);
            connectionArray[i] = new int[values.size()];
            for(int j = 0; j < values.size(); j++){
                if(connections.containsKey(values.get(j))){
                    if(connections.get(values.get(j)).contains(i)){
                        continue;
                    }
                }
                connectionArray[i][j] = values.get(j);
            }
        }
        return new Model(pos, anc, vertices.toArray(new Vector3D[0]), connectionArray);
    }
}
