/**
 * A 2x2 polyomino with the shape of
 * +--+
 * |# |
 * | #|
 * +--+
 * @author Vaibhav Malhotra
 */
package tetris;

import tetris.api.Position;

import java.awt.Color;

public class DiagonalPiece extends AbstractPiece {

	public DiagonalPiece(Position position, Color[] colors) {
		super(position, colors, 2, 2);
		getRelativeBlocks()[0].setPosition(new Position(0, 0));
		getRelativeBlocks()[1].setPosition(new Position(1, 1));
	}

}
