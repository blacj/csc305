import java.util.Scanner;


public class Test
{
	public static void main(String[] args)
	{
		boolean running = true;

		Scanner in = new Scanner(System.in);
		System.out.println("Creating matrix A");
		System.out.print("Input # rows: ");
		int rows = in.nextInt();
		System.out.print("Input # cols: ");
		int cols = in.nextInt();
		Matrix a = new Matrix(rows, cols);
		a.matrix = buildMatrix(a, in);

		System.out.println("Creating matrix B");
		System.out.print("Input # rows: ");
		rows = in.nextInt();
		System.out.print("Input # cols: ");
		cols = in.nextInt();
		Matrix b = new Matrix(rows, cols);
		b.matrix = buildMatrix(b, in);

		System.out.println("Creating 3D Point P");
		System.out.print("Input x value: ");
		int pX = in.nextInt();
		System.out.print("Input y value: ");
		int pY = in.nextInt();
		System.out.print("Input z value: ");
		int pZ = in.nextInt();
		Point3D p = new Point3D(pX, pY, pZ);
		System.out.println("Resulting point P: " + p.toString());

		while(running)
		{
			printOptions();
			int command = in.nextInt();

			//multiply A B
			if(command == 1)
			{
				Matrix result = new Matrix(a.rows, b.cols);
				result = a.multiplyM(b);
				if(result != null)
				{
					System.out.println(result.toString());
				}
			}
			//multiply A by constant
			else if(command == 2)
			{
				System.out.print("Enter constant to multiply A by > ");
				int constant = in.nextInt();
				Matrix result = new Matrix(a.rows, a.cols);
				result = a.multiplyC(constant);
				System.out.println(result.toString());
			}
			//transpose of A
			else if(command == 3)
			{
				Matrix result = new Matrix(a.cols, a.rows);
				result = a.transpose(a);
				System.out.println(result.toString());
			}
			//cofactor A
			else if(command == 4)
			{
				Matrix result = new Matrix(a.rows, a.cols);
				result = a.cofactor(a);
				if(result != null)
				{
					System.out.println(result.toString());
				}
			}
			//determinant
			else if(command == 5)
			{
				double d = a.determinant(a);
			}
			//A==B?
			else if(command == 6)
			{
				boolean equal = a.isequalto(b);
				if(equal)
				{
					System.out.println("A is equal to B");
				}
				else
				{
					System.out.println("A is not equal to B");
				}

			}
			//inverse of A
			else if(command == 7)
			{
				Matrix result = new Matrix(a.rows, a.cols);
				result = a.inverse(a);
				if(result != null)
				{
					System.out.println(result.toString());
				}

			}
			//a dot b
			else if(command == 8)
			{
				double dot = a.dotProduct(b);
				if(dot != 0.0)
				{
					System.out.println("A dot B = " + dot );
				}

			}
			//a cross b
			else if(command == 9)
			{
				Matrix result = new Matrix(a.rows, a.cols);
				result = a.crossProduct(b);
				if(result != null)
				{
					System.out.println(result.toString());
				}

			}
			//rotate P
			else if(command == 10)
			{
				System.out.print("Input degree to rotate along x axis: ");
				int rotx = in.nextInt();
				System.out.print("Input degree to rotate along y axis: ");
				int roty = in.nextInt();
				System.out.print("Input degree to rotate along z axis: ");
				int rotz = in.nextInt();
				p.rotate(rotx, roty, rotz);
				System.out.println("New P: " + p.toString());
			}
			//scale P
			else if(command == 11)
			{
				System.out.print("Input x value for scale: ");
				int scalex = in.nextInt();
				System.out.print("Input y value for scale: ");
				int scaley = in.nextInt();
				System.out.print("Input z value for scale: ");
				int scalez = in.nextInt();
				p.scale(scalex, scaley, scalez);
				System.out.println("New P: " + p.toString());
			}
			//translate P
			else if(command == 12)
			{
				System.out.print("Input x value for translation: ");
				int tranx = in.nextInt();
				System.out.print("Input y value for translation: ");
				int trany = in.nextInt();
				System.out.print("Input z value for translation: ");
				int tranz = in.nextInt();
				p.translate(tranx, trany, tranz);
				System.out.println("New P: " + p.toString());
			}
			//exit
			else if(command == 13)
			{
				System.out.println("EXITING PROGRAM...");
				running = false;
			}
			else
			{
				System.out.println("Unknown command, try again");
			}
		}
	}

	public static double[][] buildMatrix(Matrix m, Scanner in)
	{
		for(int i=0; i<m.rows; i++)
		{
			for(int j=0; j<m.cols; j++)
			{
				System.out.print("Input digit: row " + i + ", col" + j + ":");
				m.matrix[i][j] = in.nextInt();
			}
		}
		System.out.println("\n Resulting matrix: \n" + m.toString());
		return m.matrix;
	}

        public static void printOptions()
        {
                System.out.println("1. Multiply matrices AB");
                System.out.println("2. Multiply matrix A by constant");
                System.out.println("3. Transpose A");
                System.out.println("4. Cofactor matrix of A");
                System.out.println("5. Determinant of A");
                System.out.println("6. Test equality A=B");
                System.out.println("7. Inverse A");
                System.out.println("8. A dot B");
                System.out.println("9. A cross B");
                System.out.println("10. Rotate point P");
                System.out.println("11. Scale point P");
                System.out.println("12. Translate point P");
                System.out.println("13. exit");
                System.out.print("Enter integer value of desired command > ");
        }

}