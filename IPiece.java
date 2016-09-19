/**
 * A 3x3 polyomino with a shape of
 * +---+
 * | # |
 * | # |
 * | # |
 * +---+
 * @author Vaibhav Malhotra
 */
package tetris;

import tetris.api.Position;

import java.awt.Color;

public class IPiece extends AbstractPiece {

	public IPiece(Position position, Color[] colors) {
		super(position, colors, 3, 3);
		getRelativeBlocks()[0].setPosition(new Position(0, 1));
		getRelativeBlocks()[1].setPosition(new Position(1, 1));
		getRelativeBlocks()[2].setPosition(new Position(2, 1));
	}
	
}
