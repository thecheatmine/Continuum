package com.continuum.continuum;

public class Personnage{

    public int x,y;
    private int persoH, persoL;
    public int speed = 20;

    public Personnage(){
        x=0; y=0;
    }

    public void moveWithSpeed(){
        x+=speed;

        // si x dépasse la largeur de l'écran, on inverse le déplacement
        if(x > 500){speed=-speed;}

        // si x passe à gauche de l'écran, on inverse le déplacement
        if(x<-500){speed=-speed;}

    }

}

