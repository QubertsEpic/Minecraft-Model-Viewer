package com.qubert.modelViewer2.Data;

public class Matrix4D {
    public double xx, yx, zx, lx, xy, yy, zy, ly, xz, yz, zz, lz, xl, yl, zl, ll;
    public Matrix4D(double xx, double yx, double zx, double lx,
                    double xy, double yy, double zy, double ly,
                    double xz, double yz, double zz, double lz,
                    double xl, double yl, double zl, double ll
    ){
        this.xx = xx;
        this.xy = xy;
        this.xz = xz;
        this.xl = xl;
        this.yx = yx;
        this.yy = yy;
        this.yz = yz;
        this.yl = yl;
        this.zx = zx;
        this.zy = zy;
        this.zz = zz;
        this.zl = zl;
        this.lx = lx;
        this.ly = ly;
        this.lz = lz;
        this.ll = ll;

    }

    public Matrix4D(Matrix3D rotScale, Vector3D translation){
        this.xx = rotScale.xx;
        this.xy = rotScale.xy;
        this.xz = rotScale.xz;
        this.xl = 0;
        this.yx = rotScale.yx;
        this.yy = rotScale.yy;
        this.yz = rotScale.yz;
        this.yl = 0;
        this.zx = rotScale.zx;
        this.zy = rotScale.zy;
        this.zz = rotScale.zz;
        this.zl = 0;
        this.lx = translation.x;
        this.ly = translation.y;
        this.lz = translation.z;
        this.ll = 1;
    }
}
