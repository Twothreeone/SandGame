package SandGame;

import java.awt.*;
import java.util.*;

public class SandLab
{
	public static final int EMPTY = 0;
	public static final int METAL = 1;
	public static final int SAND = 2;
	public static final int WATER = 3;
	public static final int GAS = 4;
	public static final int ACID = 5;
	public static final int GLASS = 6;
	public static final int MAGMA = 7;

	private int[][] grid;
	private SandDisplay display;
	private boolean noBounds;

	/**
	 * Constructor for SandLab
	 * 
	 * @param numRows
	 *            The number of rows to start with
	 * @param numCols
	 *            The number of columns to start with;
	 */
	public SandLab(int numRows, int numCols, boolean noBounds)
	{
		String[] names;
		names = new String[8];
		names[EMPTY] = "Empty";
		names[METAL] = "Metal";
		names[SAND] = "Sand";
		names[WATER] = "Water";
		names[GAS] = "Gas";
		names[ACID] = "Acid";
		names[GLASS] = "Glass";
		names[MAGMA] = "Magma";
		grid = new int[numRows][numCols];
		display = new SandDisplay("Falling Sand", numRows, numCols, names);
		this.noBounds = noBounds;
	}

	// called when the user clicks on a location using the given tool
	private void locationClicked(int row, int col, int tool)
	{
		grid[row][col] = tool;
	}

	// copies each element of grid into the display
	public void updateDisplay()
	{
		Color c;
		for (int row = 0; row < grid.length; row++)
		{
			for (int col = 0; col < grid[0].length; col++)
			{
				if (grid[row][col] == METAL)
					c = Color.DARK_GRAY;
				else if (grid[row][col] == SAND)
					c = Color.YELLOW;
				else if (grid[row][col] == WATER)
					c = Color.BLUE;
				else if (grid[row][col] == GAS)
					c = Color.WHITE;
				else if (grid[row][col] == ACID)
					c = Color.GREEN;
				else if (grid[row][col] == GLASS)
					c = Color.LIGHT_GRAY;
				else if (grid[row][col] == MAGMA)
					c = Color.RED;
				else
					c = Color.BLACK;
				display.setColor(row, col, c);
			}
		}
	}

	// Step 5,7
	// called repeatedly.
	// causes one random particle in grid to maybe do something.
	public void step()
	{
		int row = (int) (Math.random() * grid.length);
		int col = (int) (Math.random() * grid[0].length);
		if (grid[row][col] == SAND)
		{
			if (row < grid.length - 1)
			{
				if (grid[row + 1][col] != METAL && grid[row + 1][col] != GLASS)
				{
					int temp = grid[row][col];
					grid[row][col] = grid[row + 1][col];
					grid[row + 1][col] = temp;
				}
			}
			else if (noBounds && row == grid.length - 1)
				grid[row][col] = EMPTY;
		}
		else if (grid[row][col] == WATER)
		{
			int direction = (int) (Math.random() * 5);
			if (direction == 0)
			{
				if (col > 0 && grid[row][col - 1] == EMPTY)
				{
					grid[row][col] = EMPTY;
					grid[row][col - 1] = WATER;
				}
				else if (col > 0 && grid[row][col - 1] == GAS)
				{
					grid[row][col] = GAS;
					grid[row][col - 1] = WATER;
				}
				else if (col > 0 && grid[row][col - 1] == MAGMA)
					grid[row][col] = GAS;
				else if (noBounds && col == 0)
					grid[row][col] = EMPTY;
			}
			else if (direction == 1)
			{
				if (col < grid[0].length - 1 && grid[row][col + 1] == EMPTY)
				{
					grid[row][col] = EMPTY;
					grid[row][col + 1] = WATER;
				}
				else if (col < grid[0].length - 1 && grid[row][col + 1] == GAS)
				{
					grid[row][col] = GAS;
					grid[row][col + 1] = WATER;
				}
				else if (col < grid[0].length - 1 && grid[row][col + 1] == MAGMA)
					grid[row][col] = GAS;
				else if (noBounds && col == grid[0].length - 1)
					grid[row][col] = EMPTY;
			}
			else
			{
				if (row < grid.length - 1 && grid[row + 1][col] == EMPTY)
				{
					grid[row][col] = EMPTY;
					grid[row + 1][col] = WATER;
				}
				else if (row < grid.length - 1 && grid[row + 1][col] == GAS)
				{
					grid[row][col] = GAS;
					grid[row + 1][col] = WATER;
				}
				else if (row < grid.length - 1 && grid[row + 1][col] == MAGMA)
					grid[row][col] = GAS;
				else if (noBounds && row == grid.length - 1)
					grid[row][col] = EMPTY;
			}
		}
		else if (grid[row][col] == GAS)
		{
			int direction = (int) (Math.random() * 5);
			if (direction == 0)
			{
				if (col > 0 && grid[row][col - 1] == EMPTY)
				{
					grid[row][col] = EMPTY;
					grid[row][col - 1] = GAS;
				}
				else if (noBounds && col == 0)
					grid[row][col] = EMPTY;
			}
			else if (direction == 1)
			{
				if (col < grid[0].length - 1 && grid[row][col + 1] == EMPTY)
				{
					grid[row][col] = EMPTY;
					grid[row][col + 1] = GAS;
				}
				else if (noBounds && row == grid[0].length - 1)
					grid[row][col] = EMPTY;
			}
			else
			{
				if (row > 0 && grid[row - 1][col] == EMPTY)
				{
					grid[row][col] = EMPTY;
					grid[row - 1][col] = GAS;
				}
				else if (noBounds && row == 0)
					grid[row][col] = EMPTY;
			}
		}
		else if (grid[row][col] == ACID)
		{
			int direction = (int) (Math.random() * 5);
			if (direction == 0)
			{
				if (col > 0 && grid[row][col - 1] < ACID)
				{
					grid[row][col] = EMPTY;
					grid[row][col - 1] = ACID;
				}
				else if (noBounds && col == 0)
					grid[row][col] = EMPTY;
			}
			else if (direction == 1)
			{
				if (col < grid[0].length - 1 && grid[row][col + 1] < ACID)
				{
					grid[row][col] = EMPTY;
					grid[row][col + 1] = ACID;
				}
				else if (noBounds && col == grid[0].length - 1)
					grid[row][col] = EMPTY;
			}
			else
			{
				if (row < grid.length - 1 && grid[row + 1][col] < ACID)
				{
					grid[row][col] = EMPTY;
					grid[row + 1][col] = ACID;
				}
				else if (noBounds && row == grid.length - 1)
					grid[row][col] = EMPTY;
			}
		}
		else if (grid[row][col] == MAGMA)
		{
			int direction = (int) (Math.random() * 5);
			if (direction == 0)
			{
				if (col > 0)
				{
					if (grid[row][col - 1] == EMPTY || grid[row][col - 1] == SAND || grid[row][col - 1] == ACID || grid[row][col - 1] == GLASS)
					{
						grid[row][col] = EMPTY;
						grid[row][col - 1] = MAGMA;
					}
				}
				if (col > 0)
				{
					if (grid[row][col - 1] == GAS || grid[row][col - 1] == WATER)
					{
						grid[row][col] = GAS;
						grid[row][col - 1] = MAGMA;
					}
				}
				else if (noBounds && col == 0)
					grid[row][col] = EMPTY;
			}
			else if (direction == 1)
			{
				if (col < grid[0].length - 1)
				{
					if (grid[row][col + 1] == EMPTY || grid[row][col + 1] == SAND || grid[row][col + 1] == ACID || grid[row][col + 1] == GLASS)
					{
						grid[row][col] = EMPTY;
						grid[row][col + 1] = MAGMA;
					}
				}
				if (col < grid[0].length - 1)
				{
					if (grid[row][col + 1] == GAS || grid[row][col + 1] == WATER)
					{
						grid[row][col] = GAS;
						grid[row][col + 1] = MAGMA;
					}
				}
				else if (noBounds && col == grid[0].length - 1)
					grid[row][col] = EMPTY;
			}
			else
			{
				if (row < grid.length - 1)
				{
					if (grid[row + 1][col] == EMPTY || grid[row + 1][col] == SAND || grid[row + 1][col] == ACID || grid[row + 1][col] == GLASS)
					{
						grid[row][col] = EMPTY;
						grid[row + 1][col] = MAGMA;
					}
				}
				if (row < grid.length - 1)
				{
					if (grid[row + 1][col] == GAS || grid[row + 1][col] == WATER)
					{
						grid[row][col] = GAS;
						grid[row + 1][col] = MAGMA;
					}
				}
				else if (noBounds && row == grid.length - 1)
					grid[row][col] = EMPTY;
			}
		}
	}

	// do not modify this method!
	public void run()
	{
		while (true) // infinite loop
		{
			for (int i = 0; i < display.getSpeed(); i++)
			{
				step();
			}
			updateDisplay();
			display.repaint();
			display.pause(1); // wait for redrawing and for mouse
			int[] mouseLoc = display.getMouseLocation();
			if (mouseLoc != null) // test if mouse clicked
			{
				locationClicked(mouseLoc[0], mouseLoc[1], display.getTool());
			}
		}
	}
}
