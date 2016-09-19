/**
 * This class represents a piece for The Game http://en.wikipedia.org/wiki/The_Game_(mind_game)
 *  (not that game though). 
 *  The piece is unique because the transform() method horizontally flips the piece.
 *   The cycle() method rotates the colors down. 
 * @author vaibhav malhotra
 */
package tetris;

import java.awt.Color;

import tetris.api.IPolyomino;
import tetris.api.Position;
import tetris.impl.Block;

public abstract class AbstractPiece implements IPolyomino {
	private Position position;
	private Block[] blocks;
	private int pieceWidth;

	public AbstractPiece(Position position, Color[] colors, int numBlocks, int pieceWidth) {
		if (colors.length != numBlocks) {
			throw new IllegalArgumentException("The number of colors is not correct for " + this.getClass() + ". There must be " + numBlocks + " colors.");
		}
		blocks = new Block[numBlocks];
		this.pieceWidth = pieceWidth;
		for (int x = 0; x < numBlocks; x++) {
			blocks[x] = new Block(colors[x], new Position(0, 0));
		}
		initializePosition(position);
	}

	/**
	 * Returns the array of blocks as a relative position to the IPolyomino's position.
	 * 
	 * @return
	 */
	protected Block[] getRelativeBlocks() {
		return blocks;
	}

	@Override
	public Position getPosition() {
		return position;
	}

	@Override
	public void initializePosition(Position position) {
		this.position = position;
	}

	@Override
	public Block[] getBlocks() {
		int blockCount = blocks.length;
		Block[] absBlocks = new Block[blockCount];
		for (int x = 0; x < blockCount; x++) {
			int newCol;
			int newRow;
			newCol = blocks[x].getPosition().getCol() + position.getCol();
			newRow = blocks[x].getPosition().getRow() + position.getRow();
			absBlocks[x] = new Block(blocks[x]);
			absBlocks[x].setPosition(new Position(newRow, newCol));
		}
		return absBlocks;
	}

	@Override
	public void shiftDown() {
		position.setRow(position.getRow() + 1);
	}

	@Override
	public void shiftLeft() {
		position.setCol(position.getCol() - 1);
	}

	@Override
	public void shiftRight() {
		position.setCol(position.getCol() + 1);
	}

	@Override
	public void initializeBlocks(Block[] blocks) {
		// I think this function is unused because I found a more modular way to
		// implement the initialization.
		this.blocks = blocks;
	}

	@Override
	public void transform() {
		for (int x = 0; x < blocks.length; x++) {
			Position pos = blocks[x].getPosition();
			int newCol = pieceWidth - pos.getCol() - 1;
			pos.setCol(newCol);
		}
	}

	@Override
	public void cycle() {
		// Don't do anything if the Piece is 0 blocks
		if (blocks.length == 0) {
			return;
		}

		Color tempColor = blocks[blocks.length - 1].getColorHint();
		for (int x = blocks.length - 1; x > 0; x--) {
			blocks[x].setIcon(blocks[x - 1].getColorHint());
		}
		blocks[0].setIcon(tempColor);
	}

	/**
	 * Returns a deep copy of this object.
	 * 
	 * @return a deep copy of this object.
	 */
	public IPolyomino clone() {
		// The built-in cloning mechanism of the Java runtime will
		// create an object of the correct runtime type.
		AbstractPiece cloned = null;
		try {
			cloned = (AbstractPiece) super.clone();
		} catch (CloneNotSupportedException e) {
			// can't happen in this case
		}

		//
		// TODO:
		//
		// Since clone() only gives us a "shallow" copy that shares
		// references with the original, we have to make copies for all
		// instance variables that are references (such as the Position
		// and Block[] attributes). See tetris.example.SamplePiece for an
		// example.
		//
		cloned.position = new Position(position);
		cloned.blocks = new Block[blocks.length];
		for (int x = 0; x < blocks.length; x++) {
			cloned.blocks[x] = new Block(blocks[x]);
		}

		return cloned;
	}

}
