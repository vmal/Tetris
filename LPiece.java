/**
 * A 3x3 polyomino with a shape of
 * +---+
 * |## |
 * | # |
 * | # |
 * +---+
 * @author Vaibhav Malhotra
 */
package tetris;

import tetris.api.Position;

import java.awt.Color;

public class LPiece extends AbstractPiece {

	public LPiece(Position position, Color[] colors) {
		super(position, colors, 4, 3);
		getRelativeBlocks()[0].setPosition(new Position(0, 0));
		getRelativeBlocks()[1].setPosition(new Position(0, 1));
		getRelativeBlocks()[2].setPosition(new Position(1, 1));
		getRelativeBlocks()[3].setPosition(new Position(2, 1));
	}

}
