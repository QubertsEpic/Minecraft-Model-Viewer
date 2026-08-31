package com.qubert.modelViewer2.Data;

public class Matrix3D {
    public double xx, yx, zx, xy, yy, zy, xz, yz, zz;
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