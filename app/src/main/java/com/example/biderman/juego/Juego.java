package com.example.biderman.juego;

import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;

import org.cocos2d.actions.base.RepeatForever;
import org.cocos2d.actions.instant.CallFunc;
import org.cocos2d.actions.interval.IntervalAction;
import org.cocos2d.actions.interval.MoveBy;
import org.cocos2d.actions.interval.MoveTo;
import org.cocos2d.actions.interval.ScaleBy;
import org.cocos2d.actions.interval.Sequence;
import org.cocos2d.layers.Layer;
import org.cocos2d.nodes.CocosNode;
import org.cocos2d.nodes.Director;
import org.cocos2d.nodes.Label;
import org.cocos2d.nodes.Scene;
import org.cocos2d.nodes.Sprite;
import org.cocos2d.opengl.CCGLSurfaceView;
import org.cocos2d.types.CCColor3B;
import org.cocos2d.types.CCSize;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by 41824396 on 27/9/2016.
 */
public class Juego {

    CCGLSurfaceView VistaDelJuego;
    CCSize PantallaDelDispositivo;
    Sprite ImagenFondo;
    Sprite ImagenFondoPerdida;
    int cantvidas = 5;
    int canttiros;
    Sprite ImagenArco;
    Sprite ImagenArquero;
    Sprite ImagenPelota;
    Rect PelotaColision;
    Rect ArcoColision;
    Rect ArqueroColision;
    Label lblvidas;
    Label lblmensaje;
    Label lblpuntaje;
    float PosicionFinalX, PosicionFinalY;
    float PosicionInicialX, PosicionIncialY;
    int PuntajeActual = 0;

    public Juego(CCGLSurfaceView VistaDelJuego) {
        this.VistaDelJuego = VistaDelJuego;
    }

    public void ComenzarJuego() {
        Director.sharedDirector().attachInView(this.VistaDelJuego);

        PantallaDelDispositivo = Director.sharedDirector().displaySize();

        Director.sharedDirector().runWithScene(EscenaDelJuego());
    }

    private Scene EscenaDelJuego() {
        Scene EscenaADevolver;
        EscenaADevolver = Scene.node();

        CapaDeFondo MiCapaFondo;
        MiCapaFondo = new CapaDeFondo();

        CapaDelFrente MiCapaFrente;
        MiCapaFrente = new CapaDelFrente();

        EscenaADevolver.addChild(MiCapaFondo, -10);
        EscenaADevolver.addChild(MiCapaFrente, 10);

        return EscenaADevolver;
    }

    private Scene EscenaPerdida() {
        Scene EscenaADevolver;
        EscenaADevolver = Scene.node();

        CapaDeFondoPerdida MiCapaFondo;
        MiCapaFondo = new CapaDeFondoPerdida();

        CapaDeFrentePerdida MiCapaFrente;
        MiCapaFrente = new CapaDeFrentePerdida();

        EscenaADevolver.addChild(MiCapaFondo, -10);
        EscenaADevolver.addChild(MiCapaFrente, 10);

        return EscenaADevolver;
    }

    class CapaDeFondo extends Layer {
        public CapaDeFondo() {
            PonerImagenFondo();
        }

        private void PonerImagenFondo() {
            ImagenFondo = Sprite.sprite("fondo.png");
            ImagenFondo.setPosition(PantallaDelDispositivo.width / 2, PantallaDelDispositivo.height / 2);
            ImagenFondo.runAction(ScaleBy.action(0.01f, 1.4f, 1.5f));
            super.addChild(ImagenFondo);
        }
    }


    class CapaDelFrente extends Layer {
        MoveTo Irderecha, Irizquierda;
        CallFunc FinDelMovimiento;

        public CapaDelFrente() {
                Log.d("holas" , "entra a capa frente");
            this.setIsTouchEnabled(true);

            ColocarLabel();
            ColocarPuntaje();
            PonerPelota();
            PonerArco();
            ImagenArquero = Sprite.sprite("arquero.png");
            ImagenArquero.runAction(ScaleBy.action(0.01f, 0.4f, 0.4f));
            ArqueroColision = new Rect();
            super.addChild(ImagenArquero);


            TimerTask DetectarColision;
            DetectarColision = new TimerTask() {

                @Override
                public void run() {
                    /*PelotaColision.set((int)(ImagenPelota.getPositionX()-ImagenPelota.getWidth()/2), (int)(ImagenPelota.getPositionY()-ImagenPelota.getHeight()/2),(int)(ImagenPelota.getPositionX()+ImagenPelota.getWidth()/2), (int)(ImagenPelota.getPositionY()+ImagenPelota.getHeight()/2));
                    ArcoColision.set((int)(ImagenArco.getPositionX()-ImagenArco.getWidth()/2), (int)(ImagenArco.getPositionY()-ImagenArco.getHeight()/2),(int)(ImagenArco.getPositionX()+ImagenArco.getWidth()/2), (int)(ImagenArco.getPositionY()+ImagenArco.getHeight()/2));
                    ArqueroColision.set((int)(ImagenArquero.getPositionX()-ImagenArquero.getWidth()/2), (int)(ImagenArquero.getPositionY()-ImagenArquero.getHeight()/2),(int)(ImagenArquero.getPositionX()+ImagenArquero.getWidth()/2), (int)(ImagenArquero.getPositionY()+ImagenArquero.getHeight()/2));
*/
                    PelotaColision.set((int)(ImagenPelota.getPositionX()-ImagenPelota.getWidth()/2), (int)(ImagenPelota.getPositionY()+ImagenPelota.getHeight()/2),(int)(ImagenPelota.getPositionX()+ImagenPelota.getWidth()/2), (int)(ImagenPelota.getPositionY()-ImagenPelota.getHeight()/2));
                    ArcoColision.set((int)(ImagenArco.getPositionX()-ImagenArco.getWidth()/2), (int)(ImagenArco.getPositionY()+ImagenArco.getHeight()/2),(int)(ImagenArco.getPositionX()+ImagenArco.getWidth()/2), (int)(ImagenArco.getPositionY()-ImagenArco.getHeight()/2));
                    ArqueroColision.set((int)(ImagenArquero.getPositionX()-ImagenArquero.getWidth()/2), (int)(ImagenArquero.getPositionY()+ImagenArquero.getHeight()/2),(int)(ImagenArquero.getPositionX()+ImagenArquero.getWidth()/2), (int)(ImagenArquero.getPositionY()-ImagenArquero.getHeight()/2));


                    Log.d("PelotaColision", PelotaColision.toString());
                    Log.d("ArcoColision", ArcoColision.toString());
                    if (PelotaColision.intersect(ArcoColision)) {
                        Log.d("finnn", "colisionaarco");
                        PuntajeActual += 10;
                        lblpuntaje.setString("puntos:" + PuntajeActual);
                        removeChild(ImagenPelota, true);
                        PonerPelota();
                    }
                    if (PelotaColision.intersect(ArqueroColision)) {
                        Log.d("finnn", "colisionarquero");
                        cantvidas--;
                        lblvidas.setString("Vidas:" + cantvidas);
                    }

                   /*if(!PelotaColision.intersect(ArqueroColision) && !PelotaColision.intersect(ArcoColision)){
                        Log.d("finnn", "no colisiona con nada");
                        cantvidas--;
                        lblvidas.setString("Vidas:" + cantvidas);
                        removeChild(ImagenPelota, true);
                        PonerPelota();
                    }*/

                    if (cantvidas == 0){Director.sharedDirector().replaceScene(EscenaPerdida());}

                    }
            };

            Timer RelojDetectaColision = new Timer();
            RelojDetectaColision.schedule(DetectarColision, 0, 300);

            TimerTask tareaponerarquero;
            tareaponerarquero = new TimerTask() {

                @Override
                public void run() {
                    PonerArquero();
                }
            };

            Timer RelojPonedorArquero = new Timer();
            RelojPonedorArquero.schedule(tareaponerarquero, 0, 4000);
        }

        private void ColocarLabel() {
            lblvidas = Label.label("Vidas:" + cantvidas, "Verdana", 30);
            lblvidas.setPosition(PantallaDelDispositivo.width / 2, 100);
            super.addChild(lblvidas);
        }

        private void ColocarPuntaje() {
            lblpuntaje = Label.label("Puntaje:" + PuntajeActual, "Verdana", 30);
            lblpuntaje.setPosition(PantallaDelDispositivo.width / 2, 150);
            super.addChild(lblpuntaje);
        }

        private void PonerArco() {
            ImagenArco = Sprite.sprite("arco.png");
            PosicionIncialY = PantallaDelDispositivo.height - PantallaDelDispositivo.height / 4;
            PosicionInicialX = PantallaDelDispositivo.width / 2;
            ImagenArco.setPosition(PosicionInicialX, PosicionIncialY);
            ImagenArco.runAction(ScaleBy.action(0.01f, 1.3f, 1.8f));
            ArcoColision = new Rect();
            super.addChild(ImagenArco);
        }

        private void PonerPelota() {
            ImagenPelota = Sprite.sprite("pelota.png");
            ImagenPelota.setPosition(PantallaDelDispositivo.width - PantallaDelDispositivo.width / 2, PantallaDelDispositivo.height / 4);
            ImagenPelota.runAction(ScaleBy.action(0.01f, 0.1f, 0.1f));
            PelotaColision = new Rect();
            super.addChild(ImagenPelota);
        }

        private void PonerArquero() {
            PosicionFinalX = PosicionInicialX;
            PosicionFinalY = PosicionIncialY;

            ImagenArquero.setPosition(PosicionInicialX - ImagenArco.getWidth() / 2, PosicionIncialY - ImagenArco.getHeight() / 2);

            Irderecha = MoveTo.action(2, PosicionInicialX + ImagenArco.getWidth() / 2, PosicionIncialY - ImagenArco.getHeight() / 2);
            Irizquierda = MoveTo.action(2, PosicionInicialX - ImagenArco.getWidth() / 2, PosicionIncialY - ImagenArco.getHeight() / 2);
            FinDelMovimiento = CallFunc.action(this, "FinDelTrayecto");

            IntervalAction secuencia = Sequence.actions(Irderecha, Irizquierda, FinDelMovimiento);
            ImagenArquero.runAction(secuencia);
            Log.d("arranca", "sequencia");

        }

        public void FinDelTrayecto(CocosNode Objetollamador) {

            IntervalAction secuencia;
            secuencia = Sequence.actions(Irderecha, Irizquierda, FinDelMovimiento);
            ImagenArquero.runAction(secuencia);
        }


        public boolean ccTouchesBegan(MotionEvent event) {
            return true;
        }

        public boolean ccTouchesMoved(MotionEvent event) {

            MoverPelota(event.getX(), event.getY());
            return true;
        }

        void MoverPelota(float DestinoX, float DestinoY) {

            ImagenPelota.runAction(MoveTo.action(1, DestinoX, PantallaDelDispositivo.height - DestinoY));
        }

        public boolean ccTouchesEnded(MotionEvent event) {
            return true;
        }
    }

    class CapaDeFondoPerdida extends Layer {
        public CapaDeFondoPerdida() {
            PonerImagenPerdida();
        }

        private void PonerImagenPerdida() {
            ImagenFondoPerdida = Sprite.sprite("fondoperdida.png");
            ImagenFondoPerdida.setPosition(PantallaDelDispositivo.width / 2, PantallaDelDispositivo.height / 2);
            ImagenFondoPerdida.runAction(ScaleBy.action(0.01f, 1.4f, 1.2f));
            super.addChild(ImagenFondoPerdida);
        }

    }

    class CapaDeFrentePerdida extends Layer {
        public CapaDeFrentePerdida() {
            ColocarMensajePerdida();
            ColocarPuntaje();
        }

        private void ColocarMensajePerdida() {
           lblmensaje = Label.label("Has Perdido!   ):", "Verdana", 100);
            lblmensaje.setPosition(PantallaDelDispositivo.width/2, 400);
            super.addChild(lblmensaje);
        }
        private void ColocarPuntaje() {
            lblpuntaje = Label.label("Puntaje:" + PuntajeActual, "Verdana", 30);
            lblpuntaje.setPosition(PantallaDelDispositivo.width / 2, 150);
            super.addChild(lblpuntaje);
        }

        @Override
        public boolean ccTouchesBegan(MotionEvent event) {
            Director.sharedDirector().replaceScene(EscenaDelJuego());
            return true;
        }
    }
}