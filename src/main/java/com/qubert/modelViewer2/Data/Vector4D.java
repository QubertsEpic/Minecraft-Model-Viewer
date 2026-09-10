package com.qubert.modelViewer2.Data;

public class Vector4D {

    public double x, y, z, l;
    public Vector4D (double x, double y, double z, double l){
        this.x = x;
        this.y = y;
        this.z = z;
        this.l = l;
    }

    public Vector4D(Vector3D vec3d, double l){
        this.x = vec3d.x;
        this.y = vec3d.y;
        this.z = vec3d.z;
        this.l = l;
    }

    public Vector3D toVector3D(){
        return new Vector3D(x, y, z);
    }
}
