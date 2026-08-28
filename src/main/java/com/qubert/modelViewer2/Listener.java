package com.qubert.modelViewer2;

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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Listener implements org.bukkit.event.Listener {

    List<Location> playerLocations = new ArrayList<Location>();
    List<Location> blockLookingAt = new ArrayList<Location>();

    World theWorld;

    Vector3D anchor = new Vector3D(0, 100, 0);

    Vector3D[] verticies = new Vector3D[]{ new Vector3D(0, 0, 0), new Vector3D(10, 0, 0), new Vector3D(0, 0, 10),
            new Vector3D(10, 0, 10), new Vector3D(0, 10, 0), new Vector3D(10, 10, 0), new Vector3D(0, 10, 10), new Vector3D(10, 10, 10) };
    int[][] connections = new int[][]{
            { 1, 2, 4, 7 }, { 5, 3, 6 } , { 6, 3 }, { 7 }, {5, 6}, {7}, {7}
    };

    public class Vector3D {
        double x;
        double y;
        double z;
        public Vector3D(double x, double y, double z){
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event){
        Location loc = event.getPlayer().getLocation();
        anchor = new Vector3D(loc.getX(), loc.getY() + 4, loc.getZ());
    }

    public class Matrix3D {
        double xx, yx, zx, xy, yy, zy, xz, yz, zz;
        public Matrix3D(double xx, double yx, double zx,
                        double xy, double yy, double zy,
                        double xz, double yz, double zz
        ){
            this.xx = xx;
            this.xy = xy;
            this.xz = xz;
            this.yx = yx;
            this.yy = yy;
            this.yz = yz;
            this.zx = zx;
            this.zy = zy;
            this.zz = zz;
        }
    }


    Location zero;

    double sinDegrees = 0d;

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent joining){

        Player player = joining.getPlayer();
        player.sendMessage("Hello There!");
        UUID playerID = player.getUniqueId();
        if(playerLocations.contains(playerID)){
           return;
        }


        playerLocations.add(player.getLocation());
        blockLookingAt.add(player.getTargetBlock(null, 100).getLocation());
        player.sendMessage("Particle has been added. There are now " + playerLocations.size() + " particles.");
    }


    @EventHandler
    public void onBreakBlockEvent(BlockBreakBlockEvent event){
        zero = event.getBlock().getLocation();
    }

    private Vector3D sumPoint(Vector3D vector3D1, Vector3D vector3D2){
        return new Vector3D(vector3D1.x + vector3D2.x, vector3D1.y + vector3D2.y, vector3D1.z + vector3D2.z);
    }

    private Location PointToLocation (World world, Vector3D loc){
        return new Location(world, loc.x, loc.y, loc.z);
    }

    private Vector3D dotPoint(Vector3D vector3D1, Vector3D vector3D2){
        return new Vector3D(vector3D1.x * vector3D2.x, vector3D1.y * vector3D2.y, vector3D1.z * vector3D2.z);
    }

    private Vector3D vectorMatrixAddition(Vector3D vec1, Matrix3D mat1){
        return new Vector3D(    vec1.x * mat1.xx + vec1.y * mat1.yx + vec1.z * mat1.zx,
                                vec1.x * mat1.xy + vec1.y * mat1.yy + vec1.z * mat1.zy,
                                vec1.x * mat1.xz + vec1.y * mat1.yz + vec1.z * mat1.zz);
    }

    public Listener(Plugin plugin){
        zero = new Location(plugin.getServer().getWorlds().get(0), 0, 100, 0);
        theWorld = plugin.getServer().getWorlds().get(0);
        new BukkitRunnable() {
            @Override
            public void run() {
                sinDegrees += 0.2;

                double rads = Math.toRadians(sinDegrees);
                double sin = Math.sin(rads);
                double cos = Math.cos(rads);

                Matrix3D rotMat = new Matrix3D(cos, 0, sin,
                                            0, 1, 0,
                                                -sin, 0, cos);

                for(int i = 0; i < connections.length; i++){
                    for(int j = 0; j < connections[i].length; j++){

                        //Set the model anchor to the centre
                        Vector3D current1 = sumPoint(verticies[i], new Vector3D(-5, 0, -5));
                        Vector3D current2 = sumPoint(verticies[connections[i][j]], new Vector3D(-5, 0, -5));
                        //apply the rotational matrix to the model
                        current1 = vectorMatrixAddition(current1, rotMat);
                        current2 = vectorMatrixAddition(current2, rotMat);
                        //Transform the model to the proper location
                        current1 = sumPoint(current1, anchor);
                        current2 = sumPoint(current2, anchor);


                        DrawLine(theWorld, PointToLocation(theWorld, current1), PointToLocation(theWorld, current2), 10);
                    }
                }
            }
        }.runTaskTimerAsynchronously(plugin, 0L, 1L);

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
            world.spawnParticle(Particle.DUST, new Location(world, location1.getX() - (differenceX*i), location1.getY() - (differenceY * i), location1.getZ() - (differenceZ * i)), 5, 0, 0, 0, new Particle.DustOptions(colour, size));
        }
    }
}
