package com.continuum.continuum;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    // déclaration de l'objet définissant la boucle principale de déplacement et de rendu
    private GameLoopThread gameLoopThread;
    private Balle balle;

    // création de la surface de dessin
    public GameView(Context context) {
        super(context);
        getHolder().addCallback(this);
        gameLoopThread = new GameLoopThread(this);

        // création d'un objet "balle", dont on définira la largeur/hauteur
        // selon la largeur ou la hauteur de l'écran
        balle = new Balle(this.getContext());
    }

    // Fonction qui "dessine" un écran de jeu
    public void doDraw(Canvas canvas) {
        if(canvas==null)
        {return;}

        // on efface l'écran, en blanc
        canvas.drawColor(Color.WHITE);

        // on dessine la balle
        balle.draw(canvas);
    }

    // Fonction appelée par la boucle principale (gameLoopThread)
    // On gère ici le déplacement des objets
    public void update() {
        balle.moveWithCollisionDetection();
    }

    // Fonction obligatoire de l'objet SurfaceView
    // Fonction appelée immédiatement après la création de l'objet SurfaceView
    @Override
    public void surfaceCreated(
            SurfaceHolder surfaceHolder) {
        // création du processus com.continuum.continuum.GameLoopThread si cela n'est pas fait
        if(gameLoopThread.getState()==Thread.State.TERMINATED) {
            gameLoopThread=new GameLoopThread(this);
        }
        gameLoopThread.setRunning(true);
        gameLoopThread.start();
    }

    // Fonction obligatoire de l'objet SurfaceView
    // Fonction appelée juste avant que l'objet ne soit détruit.
    // on tente ici de stopper le processus de gameLoopThread
    @Override
    public void surfaceDestroyed(
            SurfaceHolder surfaceHolder) {
        boolean retry = true;
        gameLoopThread.setRunning(false);
        while (retry) {
            try {
                gameLoopThread.join();
                retry = false;
            }
            catch (InterruptedException e) {}
        }
    }

    // Gère les touchés sur l'écran
    @Override
    public boolean onTouchEvent(
            MotionEvent event) {
        int currentX = (int)
                event.getX();
        int currentY = (int)
                event.getY();

        switch (event.getAction
                ()) {

            // code exécuté lorsque le doigt touche l'écran.
            case MotionEvent.ACTION_DOWN:
                // si le doigt touche la balle :
                if(currentX >= balle.getX() &&
                        currentX <= balle.getX()+balle.getBalleW() &&
                        currentY >= balle.getY() && currentY <= balle.getY
                        ()+balle.getBalleH() ) {

                    // on arrête de déplacer la balle
                    balle.setMove(false);
                }
                break;

            // code exécuté lorsque le doight glisse sur l'écran.
            case MotionEvent.ACTION_MOVE:
                // on déplace la balle sous le doigt du joueur
                // si elle est déjà sous son doigt (oui si on a setMove à false)
                if(!balle.isMoving()) {
                    balle.setX(currentX);
                    balle.setY(currentY);
                }
                break;

            // lorsque le doigt quitte l'écran
            case MotionEvent.ACTION_UP:
                // on reprend le déplacement de la balle
                balle.setMove(true);
        }

        return true;
// On retourne "true" pour indiquer qu'on a géré l'évènement
    }

        // Fonction obligatoire de l'objet SurfaceView
        // Fonction appelée à la CREATION et MODIFICATION et ONRESUME de l'écran
        // nous obtenons ici la largeur/hauteur de l'écran en pixels
        @Override
        public void surfaceChanged(
                SurfaceHolder surfaceHolder, int i, int w, int h) {
            balle.resize(w,h);
            // on définit la taille de la balle selon la taille de l'écran
        }
    } // class com.continuum.continuum.GameView