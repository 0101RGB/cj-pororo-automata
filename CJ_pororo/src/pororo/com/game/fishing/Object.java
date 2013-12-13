// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Object.java

package pororo.com.game.fishing;


public class Object
{

    public Object()
    {
    }

    public boolean isKeyInput()
    {
        if(!isAnimation)
            return true;
        if(!isAniState)
            return true;
        return !isKeyControll;
    }

    public boolean isAnimation;
    public boolean isAniState;
    public boolean isKeyControll;
    
    public int totalAniCnt;
    public int aniIndex;
    
    public int posX;
    public int posY;
    
//    public int moveX;
//    public int moveY;
//    public int cellIndexX;
//    public int cellIndexY;
}
