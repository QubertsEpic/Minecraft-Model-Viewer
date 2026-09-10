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

    public Vector4D MatrixMultiplication(Matrix4D matrix){
        return new Vector4D(
                matrix.xx * x + matrix.yx * y + matrix.zx * z + matrix.lx * l,
                matrix.xy * x + matrix.yy * y + matrix.zy * z + matrix.ly * l,
                matrix.xz * x + matrix.yz * y + matrix.zz * z + matrix.lz * l,
                matrix.xl * x + matrix.yl * y + matrix.zl * z + matrix.ll * l
        );
    }

    public Vector4D Sum(Vector4D toAdd){
        return new Vector4D(x + toAdd.x, y + toAdd.y, z + toAdd.z, l + toAdd.l);
    }

    public Vector4D Dot(Vector4D toDot){
        return new Vector4D(x * toDot.x, y * toDot.y, z * toDot.z, l * toDot.l);
    }
}
