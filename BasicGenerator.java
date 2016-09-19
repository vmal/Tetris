/**
 * This generator generates random Polyominos. It creates the following pieces with the specified probabilities IPiece (60%), DiagonalPiece (20%), and LPiece (20%). The color on each block is randomized.
 * @author Vaibhav Malhotra
 */
package tetris;

import java.awt.Color;
import java.util.Random;

import tetris.api.IPolyomino;
import tetris.api.IPolyominoGenerator;
import tetris.impl.AbstractBlockGame;

public class BasicGenerator implements IPolyominoGenerator {
	/**
	 * The random number generator used for the Polyomio and color selection.
	 */
	private Random rand;

	/**
	 * Constructs a BasicGenerator with the given Random for the Polyomino and color selection.
	 * 
	 * @param rand
	 *            the random number generator for the Polyomino and color selection
	 */
	public BasicGenerator(Random rand) {
		this.rand = rand;
	}

	@Override
	public IPolyomino getNext() {
		int pieceSelection = rand.nextInt(5);
		if (pieceSelection < 3) {
			// This code path has a 60% probability
			return new tetris.IPiece(new tetris.api.Position(-2, 5), getRandColors(3));
		} else if (pieceSelection == 3) {
			// This code path has a 20% probability
			return new tetris.DiagonalPiece(new tetris.api.Position(-1, 5), getRandColors(2));
		} else {
			// This code path has a 20% probability
			return new tetris.LPiece(new tetris.api.Position(-2, 5), getRandColors(4));
		}
	}

	/**
	 * Using rand, generates a single random color from the ones defined in AbstractBlockGame.COLORS
	 * 
	 * @return the randomized color
	 */
	private Color getRandColor() {
		int numColors = AbstractBlockGame.COLORS.length;
		int colorIndex = rand.nextInt(numColors);
		return AbstractBlockGame.COLORS[colorIndex];
	}

	/**
	 * Generates an array of random colors of the specified size.
	 * 
	 * @param numColors
	 *            the number of random colors to generate
	 * @return an array of random colors
	 */
	private Color[] getRandColors(int numColors) {
		Color[] colors = new Color[numColors];
		for (int x = 0; x < numColors; x++) {
			colors[x] = getRandColor();
		}
		return colors;
	}

}
