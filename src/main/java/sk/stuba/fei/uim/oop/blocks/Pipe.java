package sk.stuba.fei.uim.oop.blocks;

import java.awt.*;

public abstract class Pipe {
    protected boolean isConnected;
    protected int x;
    protected int y;
    protected int size;
    protected boolean isHighlighted = false;

    public Pipe(int x, int y, int size){
        this.x = x*size;
        this.y = y*size;
        this.size = size;
    }

    public void setHighlighted(boolean highlighted){
        isHighlighted = highlighted;
    }
    public boolean isHighlighted(){
        return isHighlighted;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void setConnected(boolean connected) {
        isConnected = connected;
    }

    public abstract boolean canConnectTo(Pipe other);

    public abstract void rotate();
    public abstract void redrawPipe();
    public abstract void draw(Graphics g);
}