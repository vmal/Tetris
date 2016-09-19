package tetris.example;

import tetris.api.IPolyomino;
import tetris.api.IPolyominoGenerator;
import tetris.api.Position;

public class SampleGenerator implements IPolyominoGenerator
{

  @Override
  public IPolyomino getNext()
  {
    // TODO Auto-generated method stub
    return new SamplePiece(new Position(-1, 5));
  }

}
