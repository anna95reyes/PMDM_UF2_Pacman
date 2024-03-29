package com.example.pacman.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.util.Log;

import com.example.pacman.DemoSurfaceView;
import com.example.pacman.MainActivity;
import com.example.pacman.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class Escenari extends GameObject {

    private static final float TOLERANCIA = 0.1f;
    private static final int TOLERENCIA_PIXELS = 6;

    private static final int PUNTUACIO_MENJAR_COCO = 10;
    private static final int PUNTUACIO_MENJAR_SUPERCOCO = 50;
    private static final int PUNTUACIO_MENJAR_FANTASMA = 200;

    private static final int MAX_VIDES = 3;
    private static final int TEMPS_FANTASMES_ESPANTATS_EN_SEGONS = 5;

    private int midaCella;
    private int files;
    private int columnes;
    private Paint pParet;
    private Paint pGroga;
    private Paint pScore;
    private Paint pFiPartida;
    private Bitmap mBackground;
    private Canvas mCanvas;
    private int mMida;
    private int mPuntuacio;
    private int mVides;

    private final Point mPosicioIniciPacman;
    private final Point mPosicioIniciBlinky;
    private final Point mPosicioIniciClyde;
    private final Point mPosicioIniciPinky;
    private final Point mPosicioIniciInky;

    private HashMap<Integer, TipusCasella> tipusCasella;

    private SimpleDateFormat mFormatter;

    private static final int escenari[][] = {
            {0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0},
            {0,	2,	2,	2,	2,	2,	2,	2,	2,	0,	0,	2,	2,	2,	2,	2,	2,	2,	2,	0},
            {0,	2,	0,	0,	0,	0,	0,	0,	2,	0,	0,	2,	0,	0,	0,	0,	0,	0,	2,	0},
            {0,	3,	0,	0,	0,	0,	0,	0,	2,	0,	0,	2,	0,	0,	0,	0,	0,	0,	3,	0},
            {0,	2,	0,	0,	0,	0,	0,	0,	2,	0,	0,	2,	0,	0,	0,	0,	0,	0,	2,	0},
            {0,	2,	2,	2,	2,	2,	2,	2,	2,	2,	2,	2,	2,	2,	2,	2,	2,	2,	2,	0},
            {0,	2,	0,	0,	0,	2,	0,	0,	0,	0,	0,	0,	0,	0,	2,	0,	0,	0,	2,	0},
            {0,	2,	0,	0,	0,	2,	0,	0,	0,	0,	0,	0,	0,	0,	2,	0,	0,	0,	2,	0},
            {700,2,	2,	2,	2,	2,	2,	2,	2,	0,	0,	2,	2,	2,	2,	2,	2,	2,	2,	701},
            {0,	2,	0,	0,	0,	0,	0,	0,	2,	0,	0,	2,	0,	0,	0,	0,	0,	0,	2,	0},
            {0,	2,	0,	0,	0,	0,	0,	0,	2,	0,	0,	2,	0,	0,	0,	0,	0,	0,	2,	0},
            {0,	2,	0,	0,	0,	2,	2,	2,	2,	2,	2,	2,	2,	2,	2,	0,	0,	0,	2,	0},
            {0,	2,	0,	0,	0,	2,	0,	0,	0,	0,	0,	0,	0,	0,	2,	0,	0,	0,	2,	0},
            {0,	2,	0,	0,	0,	2,	0,	0,	0,	0,	0,	0,	0,	0,	2,	0,	0,	0,	2,	0},
            {702,2,	2,	2,	2,	601,2,	2,	2,	2,	2,	2,	2,	2,	602,2,	2,	2,	2,	703},
            {0,	2,	0,	0,	0,	2,	0,	0,	0,	0,	0,	0,	0,	0,	2,	0,	0,	0,	2,	0},
            {0,	2,	0,	0,	0,	2,	0,	0,	0,	0,	0,	0,	0,	0,	2,	0,	0,	0,	2,	0},
            {0,	2,	0,	0,	0,	603,2,	2,	2,	2,	2,	2,	2,	2,	604,0,	0,	0,	2,	0},
            {0,	2,	0,	0,	0,	2,	0,	0,	0,	0,	0,	0,	0,	0,	2,	0,	0,	0,	2,	0},
            {0,	2,	0,	0,	0,	2,	0,	0,	0,	0,	0,	0,	0,	0,	2,	0,	0,	0,	2,	0},
            {704,2,	2,	2,	2,	2,	2,	2,	2,	0,	0,	2,	2,	2,	2,	2,	2,	2,	2,	705},
            {0,	2,	0,	0,	0,	0,	0,	0,	2,	0,	0,	2,	0,	0,	0,	0,	0,	0,	2,	0},
            {0,	3,	0,	0,	0,	0,	0,	0,	2,	0,	0,	2,	0,	0,	0,	0,	0,	0,	3,	0},
            {0,	2,	2,	2,	2,	2,	2,	2,	2,	600,2,	2,	2,	2,	2,	2,	2,	2,	2,	0},
            {0,	0,	0,	0,	2,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	2,	0,	0,	0,	0},
            {0,	0,	0,	0,	2,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	2,	0,	0,	0,	0},
            {0,	2,	2,	2,	2,	2,	2,	2,	2,	0,	0,	2,	2,	2,	2,	2,	2,	2,	2,	0},
            {0,	2,	0,	0,	0,	0,	0,	0,	2,	0,	0,	2,	0,	0,	0,	0,	0,	0,	2,	0},
            {0,	2,	0,	0,	0,	0,	0,	0,	2,	0,	0,	2,	0,	0,	0,	0,	0,	0,	2,	0},
            {0,	2,	2,	2,	2,	2,	2,	2,	2,	2,	2,	2,	2,	2,	2,	2,	2,	2,	2,	0},
            {0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0}
    };

    public Escenari(DemoSurfaceView view) {
        super(view);
        mMida = Math.min(view.getWidth(), view.getHeight());

        files = escenari.length;
        columnes = escenari[0].length;
        midaCella = mMida / columnes;

        mPuntuacio = 0;
        mVides = MAX_VIDES;

        //inicialitzacio de pintures
        pParet = new Paint();
        pParet.setColor(view.getResources().getColor(R.color.pared));
        pParet.setStrokeWidth(6);
        pParet.setStyle(Paint.Style.STROKE);

        pGroga = new Paint();
        pGroga.setColor(view.getResources().getColor(R.color.coco));

        pScore = new Paint();
        pScore.setColor(view.getResources().getColor(R.color.white));
        pScore.setTextSize(70);
        pScore.setTextAlign(Paint.Align.CENTER);

        pFiPartida = new Paint();
        pFiPartida.setColor(view.getResources().getColor(R.color.fi_partida));
        pFiPartida.setTextSize(110);
        pFiPartida.setFakeBoldText(true);
        pFiPartida.setTextAlign(Paint.Align.CENTER);

        mBackground = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBackground);
        inicialitzarEscenari(mCanvas);

        inicilitzarHashTipusCasella();
        mPosicioIniciPacman = getPosicioALaGraella(getPosicioInici(TipusCasella.POSICIO_INICI_PACMAN.codi));
        mPosicioIniciBlinky = getPosicioALaGraella(getPosicioInici(TipusCasella.POSICIO_INICI_BLINKY.codi));
        mPosicioIniciClyde = getPosicioALaGraella(getPosicioInici(TipusCasella.POSICIO_INICI_CLYDE.codi));
        mPosicioIniciPinky = getPosicioALaGraella(getPosicioInici(TipusCasella.POSICIO_INICI_PINKY.codi));
        mPosicioIniciInky = getPosicioALaGraella(getPosicioInici(TipusCasella.POSICIO_INICI_INKY.codi));

        mFormatter = new SimpleDateFormat("HH:mm:ss.SS");
    }

    private void inicilitzarHashTipusCasella() {
        tipusCasella = new HashMap<Integer, TipusCasella>();
        tipusCasella.put(TipusCasella.PARET.codi, TipusCasella.PARET);
        tipusCasella.put(TipusCasella.CAMI.codi, TipusCasella.CAMI);
        tipusCasella.put(TipusCasella.COCO.codi, TipusCasella.COCO);
        tipusCasella.put(TipusCasella.SUPERCOCO.codi, TipusCasella.SUPERCOCO);
        tipusCasella.put(TipusCasella.POSICIO_INICI_PACMAN.codi, TipusCasella.POSICIO_INICI_PACMAN);
        tipusCasella.put(TipusCasella.POSICIO_INICI_BLINKY.codi, TipusCasella.POSICIO_INICI_BLINKY);
        tipusCasella.put(TipusCasella.POSICIO_INICI_CLYDE.codi, TipusCasella.POSICIO_INICI_CLYDE);
        tipusCasella.put(TipusCasella.POSICIO_INICI_PINKY.codi, TipusCasella.POSICIO_INICI_PINKY);
        tipusCasella.put(TipusCasella.POSICIO_INICI_INKY.codi, TipusCasella.POSICIO_INICI_INKY);
        tipusCasella.put(TipusCasella.POSICIO_TUNEL1_ESQ.codi, TipusCasella.POSICIO_TUNEL1_ESQ);
        tipusCasella.put(TipusCasella.POSICIO_TUNEL1_DRE.codi, TipusCasella.POSICIO_TUNEL1_DRE);
        tipusCasella.put(TipusCasella.POSICIO_TUNEL2_ESQ.codi, TipusCasella.POSICIO_TUNEL2_ESQ);
        tipusCasella.put(TipusCasella.POSICIO_TUNEL2_DRE.codi, TipusCasella.POSICIO_TUNEL2_DRE);
        tipusCasella.put(TipusCasella.POSICIO_TUNEL3_ESQ.codi, TipusCasella.POSICIO_TUNEL3_ESQ);
        tipusCasella.put(TipusCasella.POSICIO_TUNEL3_DRE.codi, TipusCasella.POSICIO_TUNEL3_DRE);
    }

    // Donada la posicio en pixels d'un personatge ens retorna la posicio a la graella
    public Point getPosicioALaGraella(PointF posicioEnPixels) {
        Point coord = new Point();
        coord.x = Math.round(posicioEnPixels.x / midaCella);
        coord.y = Math.round(posicioEnPixels.y / midaCella);
        return coord;
    }

    public PointF getPosicioEnPixels(Point posGraella) {
        PointF pos = new PointF();
        pos.x = posGraella.x * midaCella;
        pos.y = posGraella.y * midaCella;
        return pos;
    }

    public TipusCasella getCella(Point posGraella) {
        return  tipusCasella.get(escenari[posGraella.y][posGraella.x]);
        //return TipusCasella.values()[escenari[posGraella.y][posGraella.x]];
    }

    public boolean emPucMoureEnDireccio(PointF posicioPixels, Point direccio){
        Point casella = getPosicioALaGraella(posicioPixels);
        if (casella.x == 0 && direccio.x == -1 || casella.x == escenari[0].length-1 && direccio.x == 1
        || casella.y == 0 && direccio.y == -1 || casella.y == escenari.length-1 && direccio.y == 1) {
            return false;
        }
        casella.x += direccio.x;
        casella.y += direccio.y;
        return getCella(casella) != TipusCasella.PARET;
    }

    public Boolean esticALaBocaDelTunel(Point posGraella){
        if (getCella(posGraella) == TipusCasella.POSICIO_TUNEL1_ESQ ||
                getCella(posGraella) == TipusCasella.POSICIO_TUNEL1_DRE ||
                getCella(posGraella) == TipusCasella.POSICIO_TUNEL2_ESQ ||
                getCella(posGraella) == TipusCasella.POSICIO_TUNEL2_DRE ||
                getCella(posGraella) == TipusCasella.POSICIO_TUNEL3_ESQ ||
                getCella(posGraella) == TipusCasella.POSICIO_TUNEL3_DRE) {
            return true;
        }
        return false;
    }

    public PointF creuarTunel(Point posGraella) {
        if (esticALaBocaDelTunel(posGraella)) {
            if (getCella(posGraella) == TipusCasella.POSICIO_TUNEL1_ESQ) {
                return getPosicioEnPixels(posicioGraellaDelTunel(TipusCasella.POSICIO_TUNEL1_DRE));
            } else if (getCella(posGraella) == TipusCasella.POSICIO_TUNEL1_DRE) {
                return getPosicioEnPixels(posicioGraellaDelTunel(TipusCasella.POSICIO_TUNEL1_ESQ));
            } else if (getCella(posGraella) == TipusCasella.POSICIO_TUNEL2_ESQ) {
                return getPosicioEnPixels(posicioGraellaDelTunel(TipusCasella.POSICIO_TUNEL2_DRE));
            } else if (getCella(posGraella) == TipusCasella.POSICIO_TUNEL2_DRE) {
                return getPosicioEnPixels(posicioGraellaDelTunel(TipusCasella.POSICIO_TUNEL2_ESQ));
            } else if (getCella(posGraella) == TipusCasella.POSICIO_TUNEL3_ESQ) {
                return getPosicioEnPixels(posicioGraellaDelTunel(TipusCasella.POSICIO_TUNEL3_DRE));
            } else if (getCella(posGraella) == TipusCasella.POSICIO_TUNEL3_DRE) {
                return getPosicioEnPixels(posicioGraellaDelTunel(TipusCasella.POSICIO_TUNEL3_ESQ));
            }
        }
        return null;
    }

    public Point posicioGraellaDelTunel (TipusCasella tipusCasella) {
        for (int x = 0; x < columnes; x++){
            for (int y = 0; y < files; y++) {
                if (escenari[y][x] == tipusCasella.codi) {
                    return new Point(x, y);
                }
            }
        }
        return null;
    }

    public Boolean heGuanyatLaPartida () {
        for (int x = 0; x < columnes; x++){
            for (int y = 0; y < files; y++) {
                if (escenari[y][x] == TipusCasella.COCO.codi || escenari[y][x] == TipusCasella.SUPERCOCO.codi) {
                    return false;
                }
            }
        }
        return true;
    }

    public void guanyoPartida(Canvas canvas, PointF pixels) {
        if (heGuanyatLaPartida() && mVides > 0){
            dibuixarGuanyarOPerdreITornarEnrrere(canvas, pixels, "WIN!!");
        }
    }

    public void perdoPartida (Canvas canvas, PointF pixels) {
        if (mVides == 0) {
            dibuixarGuanyarOPerdreITornarEnrrere(canvas, pixels, "GAME OVER!!");
        }
    }

    private void dibuixarGuanyarOPerdreITornarEnrrere(Canvas canvas, PointF pixels, String text) {

        canvas.drawText(text, pixels.x, pixels.y, pFiPartida);
        pausarGameObjects();
        MainActivity.handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                MainActivity.mainActivity.finish();
            }
        }, 5000);

    }

    public void pausarGameObjects() {
        for(GameObject b: mView.GameObjects()){
            if (b instanceof Ghost) {
                Ghost ghost = (Ghost) b;
                ghost.mMove = new MovimentJoystick(0, 0);
            } else if (b instanceof Pacman) {
                Pacman pacman = (Pacman) b;
                pacman.mMove = new MovimentJoystick(0, 0);
            }
        }
    }

    public Point getPosicioActualPacman() {
        for(GameObject b: mView.GameObjects()){
            if (b instanceof Pacman) {
                Pacman pacman = (Pacman) b;
                return getPosicioALaGraella(pacman.mPosicio);
            }
        }
        return null;
    }

    public MovimentJoystick getUltimMovimentPacman(){
        for(GameObject b: mView.GameObjects()){
            if (b instanceof Pacman) {
                Pacman pacman = (Pacman) b;
                return pacman.ultimMoviment;
            }
        }
        return null;
    }

    // Retornem true NOMES quan el personatge esta clavat dins de la cel·la.
    // En aquest moment pot fer girs i es controlen col·lisions
    public boolean esticALaCasella(PointF posicioPixels){
        Point posGraella = getPosicioALaGraella(posicioPixels);
        PointF posExacta = getPosicioEnPixels(posGraella);
        return (Math.abs(posicioPixels.x - posExacta.x) < TOLERANCIA &&
            Math.abs(posicioPixels.y - posExacta.y) < TOLERANCIA);
    }

    public void menjarCocos(Point posGraella) {
        if (getCella(posGraella) == TipusCasella.COCO) {
            mPuntuacio += PUNTUACIO_MENJAR_COCO;
            escenari[posGraella.y][posGraella.x] = TipusCasella.CAMI.codi;
        } else if (getCella(posGraella) == TipusCasella.SUPERCOCO) {
            mPuntuacio += PUNTUACIO_MENJAR_SUPERCOCO;
            escenari[posGraella.y][posGraella.x] = TipusCasella.CAMI.codi;
            espantarFantasmes();
        }
    }

    @Override
    public void onDraw(Canvas canvas) {

        canvas.drawBitmap(mBackground, 0, 0, pParet);

        Point posicio = new Point(0, 0);
        for (int x = 0; x < columnes; x++, posicio.x += midaCella){
            posicio.y = 0;
            for (int y = 0; y < files; y++, posicio.y += midaCella) {
                if (escenari[y][x] == TipusCasella.COCO.codi) {
                    dibuixaCoco(canvas, posicio, 6);
                } else if (escenari[y][x] == TipusCasella.SUPERCOCO.codi) {
                    dibuixaCoco(canvas, posicio, 3);
                }

            }
        }

        float xScore = ((midaCella * columnes) / 4);
        float yScore = (midaCella * files) + ((mView.getHeight() - (midaCella * files)) / 2);
        dibuixarScore(canvas, new PointF(xScore, yScore));
        xScore = (((midaCella * columnes) / 4) * 3) - 140;
        yScore = yScore - 60;
        dibuixarVides(canvas, new PointF(xScore, yScore));

        float xFiPartida = ((midaCella * columnes) / 2) - 10;
        float yFiPartida = ((midaCella * files) / 2) - 30;
        guanyoPartida(canvas, new PointF(xFiPartida, yFiPartida));
        perdoPartida(canvas, new PointF(xFiPartida, yFiPartida));

    }

    public void espantarFantasmes() {
        for(GameObject b: mView.GameObjects()){
            if (b instanceof Ghost) {
                //fantasmes
                Ghost ghost = (Ghost) b;
                ghost.setModeEspantat(true);
            }
        }
    }


    public void fantasmesDeixenDeEstarEspantats() {
        for(GameObject b: mView.GameObjects()){
            if (b instanceof Ghost) {
                //fantasmes
                Ghost ghost = (Ghost) b;
                if (ghost.getModeEspantat()) {

                    MainActivity.handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            ghost.setModeEspantat(false);
                        }
                    }, TEMPS_FANTASMES_ESPANTATS_EN_SEGONS * 1000);
                }
            }
        }
    }

    public void xocoAmbFantasmes(PointF posicioPacman, Pacman pacman){
        for(GameObject b: mView.GameObjects()){
            if (b instanceof Ghost) {
                //fantasmes
                Ghost ghost = (Ghost) b;
                PointF posicioGhost = ghost.mPosicio;

                double diferenciaX = Math.abs(posicioPacman.x - posicioGhost.x);
                double diferenciaY = Math.abs(posicioPacman.y - posicioGhost.y);

                if (diferenciaX >= 0 && diferenciaX <=TOLERENCIA_PIXELS && diferenciaY >= 0 && diferenciaY <=TOLERENCIA_PIXELS) {
                    if (ghost.getModeEspantat()) {
                        ghost.mPosicio = tornarFantasmaEspantatPosicioInicial(ghost);
                        ghost.setModeEspantat(false);
                        mPuntuacio += PUNTUACIO_MENJAR_FANTASMA;
                    } else {
                        pacman.mPosicio = getPosicioEnPixels(mPosicioIniciPacman);
                        ghost.mPosicio = tornarFantasmaEspantatPosicioInicial(ghost);
                        mVides--;

                    }
                }
            }
        }
    }

    public PointF tornarFantasmaEspantatPosicioInicial(Ghost ghost){

        if (ghost instanceof Blinky) {
            return getPosicioEnPixels(mPosicioIniciBlinky);
        } else if (ghost instanceof Clyde) {
            return getPosicioEnPixels(mPosicioIniciClyde);
        } else if (ghost instanceof Inky) {
            return getPosicioEnPixels(mPosicioIniciInky);
        } else if (ghost instanceof Pinky) {
            return getPosicioEnPixels(mPosicioIniciPinky);
        }
        return null;
    }

    public void inicialitzarEscenari(Canvas canvas) {
        Point posicio = new Point(0, 0);
        for (int x = 0; x < columnes; x++, posicio.x += midaCella){
            posicio.y = 0;
            for (int y = 0; y < files; y++, posicio.y += midaCella) {
                if (escenari[y][x] == TipusCasella.PARET.codi) {
                    dibuixarParet(canvas, new Point(x, y));
                }

            }
        }
    }

    private void dibuixarScore(Canvas canvas, PointF pixels) {
        canvas.drawText("SCORE: " + mPuntuacio, pixels.x, pixels.y, pScore);
    }

    private void dibuixarVides(Canvas canvas, PointF pixels) {
        Bitmap bitmap = BitmapFactory.decodeResource(mView.getResources(), R.drawable.vides);
        for (int i = 0; i < mVides; i++) {
            canvas.drawBitmap(bitmap, pixels.x, pixels.y, new Paint());
            pixels.x += 100;
        }

    }

    private void dibuixarParet(Canvas canvas, Point p) {

        //NW****N******NE
        //*  A  |  B  *
        //*     |     *
        //W*****O*****E
        //*  C  |  D  *
        //*     |     *
        //SW****S*****SE

        PointF pixels = getPosicioEnPixels(p);
        PointF NW = new PointF(pixels.x,                    pixels.y);
        PointF N = new PointF(pixels.x + midaCella / 2,   pixels.y);
        PointF NE = new PointF(pixels.x + midaCella,      pixels.y);
        // --------------
        PointF W = new PointF(pixels.x,                   pixels.y + midaCella / 2);
        PointF O = new PointF(pixels.x + midaCella / 2, pixels.y + midaCella / 2);
        PointF E = new PointF(pixels.x + midaCella,     pixels.y + midaCella / 2);
        //--------------------------
        PointF SW = new PointF(pixels.x,                  pixels.y + midaCella);
        PointF S = new PointF(pixels.x + midaCella / 2, pixels.y + midaCella);
        PointF SE = new PointF(pixels.x + midaCella,    pixels.y + midaCella);
        //---------------------------

        boolean verticalUP = p.y == 0                  || (escenari[p.y - 1][p.x] != TipusCasella.PARET.ordinal());
        boolean verticalDOWN = p.y == files - 1        || (escenari[p.y + 1][p.x] != TipusCasella.PARET.ordinal());
        boolean horitzontalLEFT = p.x == 0             || (escenari[p.y][p.x - 1] != TipusCasella.PARET.ordinal());
        boolean horitzontalRIGHT = p.x == columnes - 1 || (escenari[p.y][p.x + 1] != TipusCasella.PARET.ordinal());

        //Quadre A
        dibuixaLinies(canvas, N, NW, W, horitzontalLEFT, verticalUP);

        //Quadre B
        dibuixaLinies(canvas, N, NE, E, horitzontalRIGHT, verticalUP);

        //Quadre C
        dibuixaLinies(canvas, S, SW, W, horitzontalLEFT, verticalDOWN);

        //Quadre D
        dibuixaLinies(canvas, S, SE, E, horitzontalRIGHT, verticalDOWN);

    }

    private void dibuixaLinies(Canvas canvas,
                               PointF A, //vertex en horitzontal
                               PointF B, //cantonada
                               PointF C, //vertex en vertical
                               boolean H, Boolean V) {

        if (V && H) {
            Path p = new Path();
            p.moveTo(A.x, A.y);
            p.cubicTo(B.x, B.y, B.x, B.y, C.x, C.y);
            canvas.drawPath(p, pParet);
            //canvas.drawLine(A.x, A.y, C.x, C.y, pParet);
        } else if (V) {
            canvas.drawLine(A.x, A.y, B.x, B.y, pParet);
        } else if (H) {
            canvas.drawLine(C.x, C.y, B.x, B.y, pParet);
        }
    }

    private void dibuixaCoco(Canvas canvas, Point posicio, float reduccio) {
        canvas.drawCircle(posicio.x + midaCella * 0.5f, posicio.y + midaCella * 0.5f,
                midaCella / reduccio, pGroga);
    }

    @Override
    public void tick() {

    }

    public PointF getPosicioInici(int posicio) {

        for (int x = 0; x < columnes; x++){
            for (int y = 0; y < files; y++) {
                if (escenari[y][x] == posicio) {
                     escenari[y][x]= TipusCasella.COCO.codi;
                    return getPosicioEnPixels(new Point(x, y));
                }

            }
        }
        throw new RuntimeException("Quina merda de mapa... no te posicio: " + posicio +" !!!");
    }

    public PointF getPosicioIniciPacman() {
        return getPosicioEnPixels(mPosicioIniciPacman);

    }

    public PointF getPosicioIniciBlinky() {
        return getPosicioEnPixels(mPosicioIniciBlinky);
    }

    public PointF getPosicioIniciClyde() {
        return getPosicioEnPixels(mPosicioIniciClyde);
    }

    public PointF getPosicioIniciPinky() {
        return getPosicioEnPixels(mPosicioIniciPinky);
    }

    public PointF getPosicioIniciInky() {
        return getPosicioEnPixels(mPosicioIniciInky);
    }

    public int getMidaCella() {
        return midaCella;
    }

    public int getVides() {return mVides;}
}
