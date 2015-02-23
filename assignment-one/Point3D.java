import java.lang.Math.*;

public class Point3D
{
        public int x;
        public int y;
        public int z;

        public Point3D(int x, int y, int z)
        {
                this.x = x;
                this.y = y;
                this.z = z;
        }

        public void rotate(int xrot, int yrot, int zrot)
        {
			double[][] rotateArr = new double[3][3];

			double[][] pointArr = new double[3][1];
			pointArr[0][0] = this.x;
			pointArr[1][0] = this.y;
			pointArr[2][0] = this.z;

			if(xrot != 0)
			{
				rotateArr[0][0] = 1;
				rotateArr[0][1] = 0;
				rotateArr[0][2] = 0;
				rotateArr[1][0] = 0;
				rotateArr[1][1] = 0;
				rotateArr[1][2] = Math.cos(xrot);
				rotateArr[2][0] = Math.sin(xrot)*(-1.0);
				rotateArr[2][1] = Math.sin(xrot);
				rotateArr[2][2] = Math.cos(xrot);

				Matrix point = new Matrix(pointArr);

				Matrix rotater = new Matrix(rotateArr);
				point = rotater.multiplyM(point);

				pointArr[0][0] = point.matrix[0][0];
				pointArr[1][0] = point.matrix[1][0];
				pointArr[2][0] = point.matrix[2][0];
			}

			if(yrot != 0)
			{
				rotateArr[0][0] = Math.cos(yrot);
				rotateArr[0][1] = 0;
				rotateArr[0][2] = Math.sin(yrot);
				rotateArr[1][0] = 0;
				rotateArr[1][1] = 1;
				rotateArr[1][2] = 0;
				rotateArr[2][0] = Math.sin(yrot)*(-1.0);
				rotateArr[2][1] = 0;
				rotateArr[2][2] = Math.cos(yrot);

				Matrix point = new Matrix(pointArr);

				Matrix rotater = new Matrix(rotateArr);
				point = rotater.multiplyM(point);

				pointArr[0][0] = point.matrix[0][0];
				pointArr[1][0] = point.matrix[1][0];
				pointArr[2][0] = point.matrix[2][0];
			}

			if(zrot != 0)
			{
				rotateArr[0][0] = Math.cos(zrot);
				rotateArr[0][1] = Math.sin(zrot)*(-1.0);
				rotateArr[0][2] = 0;
				rotateArr[1][0] = Math.sin(zrot);
				rotateArr[1][1] = Math.cos(zrot);
				rotateArr[1][2] = 0;
				rotateArr[2][0] = 0;
				rotateArr[2][1] = 0;
				rotateArr[2][2] = 1;

				Matrix point = new Matrix(pointArr);

				Matrix rotater = new Matrix(rotateArr);
				point = rotater.multiplyM(point);

				pointArr[0][0] = point.matrix[0][0];
				pointArr[1][0] = point.matrix[1][0];
				pointArr[2][0] = point.matrix[2][0];
			}

			this.x = (int)Math.round(pointArr[0][0]);
			this.y = (int)Math.round(pointArr[1][0]);
			this.z = (int)Math.round(pointArr[2][0]);

        }

		public void scale(int scaleX, int scaleY, int scaleZ)
		{
			Matrix scaler = new Matrix(3,3);
			scaler.matrix[0][0] = scaleX;
			scaler.matrix[1][1] = scaleY;
			scaler.matrix[2][2] = scaleZ;

			double[][] pointArr = new double[3][1];
			pointArr[0][0] = this.x;
			pointArr[1][0] = this.y;
			pointArr[2][0] = this.z;

			Matrix point = new Matrix(pointArr);
			point = scaler.multiplyM(point);

			this.x = (int)Math.round(point.matrix[0][0]);
			this.y = (int)Math.round(point.matrix[1][0]);
			this.z = (int)Math.round(point.matrix[2][0]);
		}

	public void translate(int transX, int transY, int transZ)
	{
		double[][] pointArr = new double[4][1];
		pointArr[0][0] = this.x;
		pointArr[1][0] = this.y;
		pointArr[2][0] = this.z;
		pointArr[3][0] = 1; 		//this is w

		Matrix trans = new Matrix(4,4);
		trans.matrix[0][0] = 1;
		trans.matrix[1][1] = 1;
		trans.matrix[2][2] = 1;
		trans.matrix[3][3] = 1;
		trans.matrix[0][3] = transX;
		trans.matrix[1][3] = transY;
		trans.matrix[2][3] = transZ;

		Matrix point = new Matrix(pointArr);
		point = trans.multiplyM(point);

		this.x = (int)Math.round(point.matrix[0][0]);
		this.y = (int)Math.round(point.matrix[1][0]);
		this.z = (int)Math.round(point.matrix[2][0]);

	}

	public String toString()
	{
 		return "(" + this.x + "," + this.y + "," + this.z + ")";
	}
}