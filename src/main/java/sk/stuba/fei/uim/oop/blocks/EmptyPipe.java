package sk.stuba.fei.uim.oop.blocks;

import java.awt.Color;
import java.awt.Graphics;

public class EmptyPipe extends Pipe {

    public EmptyPipe(int x, int y, int size) {
        super(x,y,size);
    }

    public boolean canConnectTo(Pipe other) {
        return false;
    }

    public void rotate() {
    }
    @Override
    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(x, y, size, size);
        g.setColor(Color.BLACK);
        g.drawLine(x,y,x+size,y);
        g.drawLine(x+size,y,x+size,y+size);
        g.drawLine(x,y+size,x+size,y+size);
        g.drawLine(x,y,x,y+size);
    }
}
