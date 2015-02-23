import java.io.*;
import java.lang.Math.*;

public class Matrix
{
	public double [][] matrix;
	public int rows;
	public int cols;


	public Matrix(double[][] matrix)
	{
		this.matrix = matrix;
		this.rows = matrix.length;
		this.cols = matrix[0].length;
	}

	public Matrix(int rows, int cols)
	{
		matrix = new double[rows][cols];
		this.rows = rows;
		this.cols = cols;
		init();
	}

	public void init()
	{
		for(int i=0; i<this.rows; i++)
		{
			for(int j=0; j<this.cols; j++)
			{
				this.matrix[i][j] = 0.0;
			}
		}
	}

	public Matrix transpose(Matrix m)
	{
		Matrix transpose = new Matrix(m.cols, m.rows);
		for(int i=0; i<rows; i++)
		{
			for(int j=0; j<cols; j++)
			{
				transpose.matrix[j][i] = m.matrix[i][j];
			}
		}
		return transpose;
	}

	//test for equality for different sized
	//test for same size, different
	//test for same matrix
	public boolean isequalto(Matrix other)
	{
		if(this.cols != other.cols || this.rows != other.rows)
		{
			return false;
		}

		for(int i=0; i < rows; i++)
		{
			for(int j=0; j<cols; j++)
			{
				if(this.matrix[i][j] != other.matrix[i][j])
				{
					return false;
				}
			}
		}
		return true;
	}

	public double dotProduct(Matrix b)
	{
		if(this.cols != 1 || b.cols != 1 || this.rows != b.rows)
		{
			System.out.println("Cannot calculate dot product. Matrices must both be of size n x 1");
			return 0.0;
		}
		Matrix temp = new Matrix(1, 1);
		temp = transpose(this).multiplyM(b);
		if(temp.matrix[0][0] == 0.0)
		{
			System.out.println("The vectors are perpendicular. Dot product = 0.0.");
		}
		return temp.matrix[0][0];
	}

	public Matrix crossProduct(Matrix b)
	{
		if(this.cols != 1 || b.cols != 1 || this.rows !=3 || b.rows != 3)
		{
			System.out.println("Cannot calculate cross product. Matrices must be of size 3 x 1");
			return null;
		}
		Matrix temp = new Matrix(3,1);
		temp.matrix[0][0] = this.matrix[1][0]*b.matrix[2][0] - this.matrix[2][0]*b.matrix[1][0];
		temp.matrix[1][0] = this.matrix[2][0]*b.matrix[0][0] - this.matrix[0][0]*b.matrix[2][0];
		temp.matrix[2][0] = this.matrix[0][0]*b.matrix[1][0] - this.matrix[1][0]*b.matrix[0][0];

		return temp;
	}

	public Matrix multiplyM(Matrix other)
	{
		if(this.cols != other.rows)
		{
			System.out.println("Cannot multiply the given matrices together due to matrix sizes");
			return null;
		}

		Matrix product = new Matrix(this.rows, other.cols);

		for(int i=0; i<this.rows ;i++)
		{
			for(int j=0; j<other.cols; j++)
			{
				for(int k=0; k<this.cols; k++)
				{
					product.matrix[i][j]+= this.matrix[i][k] * other.matrix[k][j];
				}
			}
		}
		return product;
	}	//end of multiplywith

	public Matrix multiplyC(double constant)
	{
		Matrix product = new Matrix(this.rows, this.cols);

		for(int i=0; i<this.rows; i++)
		{
			for(int j=0; j<this.cols; j++)
			{
				product.matrix[i][j] = this.matrix[i][j] * constant;
			}
		}
		return product;
	}

	public Matrix cofactor(Matrix m)
	{
		if(this.rows != this.cols)
		{
			System.out.println("Cannot calculate cofactor. Must be square matrix.");
			return null;
		}

		Matrix cofactor = new Matrix(this.rows, this.cols);

		for (int i=0;i< this.rows;i++)
		{
			for (int j=0; j< this.cols;j++)
			{
            	cofactor.matrix[i][j] = determinant(submatrix(m, i, j));
            	if((i+j)%2 == 1)
            	{
					cofactor.matrix[i][j] *= (-1.0);
				}
			}
		}
		return cofactor;

	}

	public double determinant(Matrix m)
	{
		if(this.rows != this.cols)
		{
			System.out.println("Cannot calculate determinant. Must be square matrix.");
			return 0.0;
		}
		if(m.rows == 1)
		{
			return m.matrix[0][0];
		}
		if(m.rows == 2)
		{
			return (m.matrix[0][0]*m.matrix[1][1] - m.matrix[1][0]*m.matrix[0][1]);
		}
		double p = 0.0;
		double sum = 0.0;
		for (int i=0; i<m.cols; i++)
		{
			p = m.matrix[0][i] * determinant(submatrix(m, 0, i));
			if((i%2)==1)
			{
				p *= -1.0;
			}
			sum += p;
		}
		return sum;
	}

	public Matrix submatrix(Matrix tosub, int unwantedrow, int unwantedcol)
	{
		Matrix subbed = new Matrix((tosub.rows)-1, (tosub.cols)-1);

		int p = 0;
		for (int i=0; i< tosub.rows; i++)
		{
			if(i==unwantedrow)
				continue;

			int q = 0;
			for(int j=0; j<tosub.cols; j++)
			{
				if(j==unwantedcol)
					continue;

				subbed.matrix[p][q] = tosub.matrix[i][j];
				++q;
			}
			++p;
		}
		return subbed;
	}

	public Matrix inverse(Matrix m)
	{
		if(this.rows != this.cols)
		{
			System.out.println("Cannot calculate inverse. Must be square matrix.");
			return null;
		}
		return transpose(this.cofactor(m)).multiplyC(1.0/this.determinant(m));
	}

	public String toString()
	{
		if(this.matrix == null) return "matrix is null";
		StringBuilder mat = new StringBuilder();
		for(int i=0; i<this.rows; i++)
		{
			for(int j=0; j<this.cols; j++)
			{
				mat.append((this.matrix[i][j]) + "\t");
			}
			mat.append("\n");
		}
		return mat.toString();
	}

}//end