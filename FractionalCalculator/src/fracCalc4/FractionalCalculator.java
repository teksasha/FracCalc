package fracCalc4;

import java.util.Scanner;

public class FractionalCalculator
{

	public static String convertToFraction(String input)
	{
		String returnValue = input;
		if (input.indexOf("_") != -1)
		{
			// Find parts of number
			int underscore = input.indexOf("_");
			String integer = input.substring(0, underscore);
			String fraction = input.substring(underscore + 1);
			int slash = fraction.indexOf("/");
			// Find parts of fraction
			String num = fraction.substring(0, slash);
			String den = fraction.substring(slash + 1);
			// Convert string to int
			int i = Integer.parseInt(integer);
			int n = Integer.parseInt(num);
			int d = Integer.parseInt(den);
			// Converts mixed number to fraction
			int newNum = 0;
			if (i > 0)
			{
				newNum = (i * d) + n;
			}
			// Converts negative mixed number to fraction
			if (i < 0)
			{
				newNum = (i * d) - n;
			}

			// returns improper fraction
			returnValue = (newNum + "/" + den);
		}
		else if (input.indexOf("/") == -1)
		{
			returnValue = input + "/1";
		}
		return returnValue;
	}

	public static int gcf(int small, int big)
	{
		int greatest = 1;

		for (int i = 1; i <= Math.abs(small); i++)
		{
			if (small % i == 0 && big % i == 0)
			{
				greatest = i;
			}
		}
		return greatest;
	}

	public static String reduceFraction(int numerator, int denominator)
	{
		int gcf = gcf(numerator, denominator);
		int redNum = numerator / gcf;
		int redDen = denominator / gcf;
		String redFrac = redNum + "/" + redDen;
		return redFrac;
	}

	public static String reduce(String fraction)
	{
		int slash = fraction.indexOf("/");
		String num = fraction.substring(0, slash);
		String den = fraction.substring(slash + 1);
		int numerator = Integer.parseInt(num);
		int denominator = Integer.parseInt(den);
		String output = reduceFraction(numerator, denominator);
		return output;
	}

	public static String convertToMixed(String fraction)
	{
		int slash = fraction.indexOf("/");
		String num = fraction.substring(0, slash);
		String den = fraction.substring(slash + 1);
		int numerator = Integer.parseInt(num);
		int denominator = Integer.parseInt(den);
		String output = "";
		if (numerator % denominator == 0)
		{
			// convert to integer
			int result = numerator / denominator;
			output = Integer.toString(result);
		}
		else if (Math.abs(numerator) > Math.abs(denominator) && !(numerator % denominator == 0))
		{
			// convert to mixed number
			int integer = numerator / denominator;
			int fracNum = Math.abs(numerator % denominator);
			String mixNum = integer + "_" + (fracNum + "/" + denominator);
			output = mixNum;
		}
		else
		{
			output = fraction;
		}
		return output;
	}

	public static String calculate(String left, String operator, String right)
	{
		// Find parts of left fraction
		int lSlash = left.indexOf("/");
		String lNum = left.substring(0, lSlash);
		String lDen = left.substring(lSlash + 1);

		// Find parts of right fraction
		int rSlash = right.indexOf("/");
		String rNum = right.substring(0, rSlash);
		String rDen = right.substring(rSlash + 1);

		// Convert left to int
		int lN = Integer.parseInt(lNum);
		int lD = Integer.parseInt(lDen);

		// Convert right to int
		int rN = Integer.parseInt(rNum);
		int rD = Integer.parseInt(rDen);

		String output = "";

		// Multiplication
		if (operator.equals("*"))
		{
			int calcNum = (lN * rN);
			int calcDen = (lD * rD);
			// String frac = calcNum + "/" + calcDen;
			// String redCalc = reduce(frac);
			String redCalc = reduceFraction(calcNum, calcDen);
			output = (redCalc);
		}

		// Division
		else if (operator.equals("/"))
		{
			int calcNum = (lN * rD);
			int calcDen = (lD * rN);
			String redCalc = reduceFraction(calcNum, calcDen);
			output = (redCalc);
		}

		// Addition
		else if (operator.equals("+"))
		{
			// Runs if denominators are different
			if (lD != rD)
			{
				// Computes common denominator
				int comDen = lD * rD;
				// Computes numerator
				int sumNum = (lN * rD) + (rN * lD);
				String redCalc = reduceFraction(sumNum, comDen);
				output = (redCalc);
			}
			// Runs if denominators are same
			else
			{
				// Computes numerator
				int sumNum = lN + rN;
				String redCalc = reduceFraction(sumNum, lD);
				output = (redCalc);
			}
		}

		// Subtraction
		else if (operator.equals("-"))
		{
			// Runs if denominators are different
			if (lD != rD)
			{
				// Computes common denominator
				int comDen = lD * rD;
				// Computes numerator
				int diffNum = (lN * rD) - (rN * lD);
				String redCalc = reduceFraction(diffNum, comDen);
				output = (redCalc);
			}
			// Runs if denominators are same
			else
			{
				// Computes numerator
				int diffNum = lN - rN;
				String redCalc = reduceFraction(diffNum, lD);
				output = (redCalc);
			}
		}

		// Unknown operator
		else
		{
			output = ("Operator \"" + operator + "\" is not recognized");
		}
		return output;
	}

	public static void main(String[] args)
	{
		System.out.println("Welcome to the Fraction Calculator!");
		Scanner console = new Scanner(System.in);
		System.out.print("Enter an expression (or \"quit\"): ");
		String input = console.nextLine();
		while (!input.equals("quit"))
		{
			// Fraction
					int space = input.indexOf(" ");
					int spaceTwo = space + 2;
					String leftOp = input.substring(0, space);
					String operator = input.substring(space + 1, spaceTwo);
					String rightOp = input.substring(spaceTwo + 1);

					// Mixed/Whole number
					// Call reduce here
					leftOp = convertToFraction(leftOp);
					rightOp = convertToFraction(rightOp);

					// Results
					String result = calculate(leftOp, operator, rightOp);
					result = convertToMixed(result);
					System.out.println(result);
					System.out.print("Enter an expression (or \"quit\"): ");
					input = console.nextLine();
			}
		System.out.println("Goodbye!");
	}
}