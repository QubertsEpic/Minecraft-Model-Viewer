package com.qubert.modelViewer2.Data;

public class Vector3D {
    public double x, y, z;
    public Vector3D(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public Vector3D MatrixMultiplication(Matrix3D matrix){
        return new Vector3D(
                matrix.xx * x + matrix.yx * y + matrix.zx * z,
                matrix.xy * x + matrix.yy * y + matrix.zy * z,
                matrix.xz * x + matrix.yz * y + matrix.zz * z
        );
    }

    public Vector3D Sum(Vector3D toAdd){
        return new Vector3D(x + toAdd.x, y + toAdd.y, z + toAdd.z);
    }

    public Vector3D Dot(Vector3D toDot){
        return new Vector3D(x * toDot.x, y * toDot.y, z * toDot.z);
    }

}