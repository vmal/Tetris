/**
 * A Tetris-like falling block game with 3 pieces and randomized colors. The player may flip the piece and cycle the colors. The goal is to connect 3 or more blocks of the same color.
 * @author Vaibhav Malhotra
 */
package tetris;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import tetris.api.IPolyominoGenerator;
import tetris.api.Position;
import tetris.impl.AbstractBlockGame;
import tetris.impl.GridCell;

public class BlockGame extends AbstractBlockGame {
	/**
	 * The current score of the game.
	 */
	private int score = 0;

	/**
	 * Constructs a BlockGame with the lower 8 rows filled with a checker board pattern of random colors.
	 * 
	 * @param generator
	 *            the Generator for selecting polyomios
	 * @param rand
	 *            the random number generator for selecting colors
	 */
	public BlockGame(IPolyominoGenerator generator, Random rand) {
		super(generator);

		// Fill the grid
		for (int row = HEIGHT - 8; row < HEIGHT; row++) {
			for (int col = (row - (HEIGHT - 8)) % 2; col < WIDTH; col += 2) {
				getGrid()[row][col] = new GridCell(getRandColor(rand));
			}
		}
	}

	/**
	 * Constructs a BlockGame with an empty grid.
	 * 
	 * @param generator
	 *            the generator for selecting Polyomios
	 */
	public BlockGame(IPolyominoGenerator generator) {
		super(generator);
	}

	/**
	 * Using rand, generates a single random color from the ones defined in AbstractBlockGame.COLORS
	 * 
	 * @param rand
	 *            the random number generator for selecting the color
	 * @return the randomized color
	 */
	private Color getRandColor(Random rand) {
		int numColors = AbstractBlockGame.COLORS.length;
		int colorIndex = rand.nextInt(numColors);
		return AbstractBlockGame.COLORS[colorIndex];
	}

	@Override
	public ArrayList<Position> determineCellsToCollapse() {
		ArrayList<Position> cellsToCollapse = new ArrayList<>();
		for (int row = 0; row < HEIGHT; row++) {
			for (int col = 0; col < WIDTH; col++) {
				// Check whether the specified cell meets the criteria
				if (checkNeighboringCells(new Position(row, col))) {
					// Add the cell and its neighbors to the list of cells to collapse
					cellsToCollapse.addAll(getMatchingCellsInNeighborhood(new Position(row, col)));
				}
			}
		}
		// Debug print before consolodation
		// System.out.print(cellsToCollapse.size() + " ");
		removeDuplicatePositions(cellsToCollapse);
		// Debug print after consolodation
		// System.out.println(cellsToCollapse.size());
		score += cellsToCollapse.size();
		return cellsToCollapse;
	}

	/**
	 * Removes any duplicate Positions from the array.
	 * 
	 * @param positions
	 *            the array of Positions to clean up
	 */
	private void removeDuplicatePositions(ArrayList<Position> positions) {
		int x = 0;
		// We know that the positions array will be changing size, but all the elements up to x will remain unchanged.
		// This is a while-loop (instead of a for-loop) because the number of iterations is not known (in accordance to the style guidelines for loops).
		while (x < positions.size()) {
			removeMatchingPositions(positions, positions.get(x), x + 1);
			x++;
		}
	}

	/**
	 * Removes any Positions that match the specified position, starting at a point in the array. The items before start remain unchanged.
	 * 
	 * @param positions
	 *            the positions to search and delete from
	 * @param match
	 *            the Position to remove from the positions ArrayList
	 * @param start
	 *            the first index to check and remove duplicates from. If start is equal or greater than the positions size, nothing is done.
	 */
	private void removeMatchingPositions(ArrayList<Position> positions, Position match, int start) {
		int x = start;
		// Note that the array will be modified during this loop.
		while (x < positions.size()) {
			if (match.equals(positions.get(x))) {
				positions.remove(x);
				// We don't need to increment x, because the next Position to check will shift left into the x position. Or at the end of the array, the loop will simply exit.
			} else {
				x++;
			}
		}
	}

	/**
	 * Counts the number of neighboring cells that match the specified cell. If the count is greater than 2, then the criteria is met and this function returns true, otherwise false.
	 * 
	 * @param p
	 *            the specified cell to check neighbors to
	 * @return true if there are at least two matching neighbors, otherwise false
	 */
	private boolean checkNeighboringCells(Position p) {
		// The criteria is at least 2 neighboring cells match. The getMatchingCellsInNeighborhood() method includes the specified cell itself, thus we look for a count of at least 3.
		if (getMatchingCellsInNeighborhood(p).size() > 2) {
			return true;
		}
		return false;
	}

	/**
	 * Checks whether the two specified cells' colors match. If either cell is null, they are "not matching." Positions outside the bound of the grid are allowed and return false.
	 * 
	 * @param p1
	 *            one of the positions of the blocks to compare
	 * @param p2
	 *            the position of the other block to compare
	 * @return true if the cells' colors match. false if either cell is null, the colors do not match, or one of the positions is outside the bounds.
	 */
	private boolean doCellsMatch(Position p1, Position p2) {
		// Bounds checking
		// top
		if (p1.getRow() < 0 || p2.getRow() < 0) {
			return false;
		}
		// left
		if (p1.getCol() < 0 || p2.getCol() < 0) {
			return false;
		}
		// bottom
		if (p1.getRow() >= HEIGHT || p2.getRow() >= HEIGHT) {
			return false;
		}
		// right
		if (p1.getCol() >= WIDTH || p2.getCol() >= WIDTH) {
			return false;
		}

		GridCell g1 = getGrid()[p1.getRow()][p1.getCol()];
		GridCell g2 = getGrid()[p2.getRow()][p2.getCol()];
		// Check whether the cells have a color
		if (g1 == null || g2 == null) {
			return false;
		}
		return g1.matches(g2);
	}

	/**
	 * Gets the neighboring cells that match the specified cell. The specified cell is included in the return ArrayList.
	 * 
	 * @param p
	 *            the position to check around
	 * @return the list of neighboring cells that match the specified cell, including itself
	 */
	private ArrayList<Position> getMatchingCellsInNeighborhood(Position p) {
		ArrayList<Position> matchingCells = new ArrayList<>();
		// Check above
		if (doCellsMatch(p, new Position(p.getRow() - 1, p.getCol()))) {
			matchingCells.add(new Position(p.getRow() - 1, p.getCol()));
		}
		// Check right
		if (doCellsMatch(p, new Position(p.getRow(), p.getCol() + 1))) {
			matchingCells.add(new Position(p.getRow(), p.getCol() + 1));
		}
		// Check below
		if (doCellsMatch(p, new Position(p.getRow() + 1, p.getCol()))) {
			matchingCells.add(new Position(p.getRow() + 1, p.getCol()));
		}
		// Check left
		if (doCellsMatch(p, new Position(p.getRow(), p.getCol() - 1))) {
			matchingCells.add(new Position(p.getRow(), p.getCol() - 1));
		}

		// Add the center cell itself. It always matches
		matchingCells.add(p);

		return matchingCells;
	}

	@Override
	public int determineScore() {
		return score;
	}

}
