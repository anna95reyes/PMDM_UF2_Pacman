package com.example.pacman.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;

import com.example.pacman.DemoSurfaceView;
import com.example.pacman.R;

import java.util.HashMap;

public class Escenari extends GameObject {

    private static final float TOLERANCIA = 0.1f;
    private int midaCella;
    private int files;
    private int columnes;
    private Paint pParet;
    private Paint pGroga;
    private Bitmap mBackground;
    private Canvas mCanvas;
    private int mMida;

    private HashMap<Integer, TipusCasella> tipusCasella;

    private static final int escenari[][] = {
            {0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0},
            {0,   2,   2,   2,   2,   2,   2,   2,   2,   2,   2,   2,   2,   0,   0,   2,   2,   2,   2,   2,   2,   2,   2,   2,   2,   2,   2,   0},
            {0,   2,   0,   0,   0,   0,   2,   0,   0,   0,   0,   0,   2,   0,   0,   2,   0,   0,   0,   0,   0,   2,   0,   0,   0,   0,   2,   0},
            {0,   3,   0,   0,   0,   0,   2,   0,   0,   0,   0,   0,   2,   0,   0,   2,   0,   0,   0,   0,   0,   2,   0,   0,   0,   0,   3,   0},
            {0,   2,   0,   0,   0,   0,   2,   0,   0,   0,   0,   0,   2,   0,   0,   2,   0,   0,   0,   0,   0,   2,   0,   0,   0,   0,   2,   0},
            {0,   2,   2,   2,   2,   2,   2,   2,   2,   2,   2,   2,   2,   2,   2,   2,   2,   2,   2,   2,   2,   2,   2,   2,   2,   2,   2,   0},
            {0,   2,   0,   0,   0,   0,   2,   0,   0,   2,   0,   0,   0,   0,   0,   0,   0,   0,   2,   0,   0,   2,   0,   0,   0,   0,   2,   0},
            {0,   2,   0,   0,   0,   0,   2,   0,   0,   2,   0,   0,   0,   0,   0,   0,   0,   0,   2,   0,   0,   2,   0,   0,   0,   0,   2,   0},
            {0,   2,   2,   2,   2,   2,   2,   0,   0,   2,   2,   2,   2,   0,   0,   2,   2,   2,   2,   0,   0,   2,   2,   2,   2,   2,   2,   0},
            {0,   2,   0,   0,   0,   0,   2,   0,   0,   0,   0,   0,   2,   0,   0,   2,   0,   0,   0,   0,   0,   2,   0,   0,   0,   0,   2,   0},
            {0,   2,   0,   0,   0,   0,   2,   0,   0,   0,   0,   0,   2,   0,   0,   2,   0,   0,   0,   0,   0,   2,   0,   0,   0,   0,   2,   0},
            {0,   2,   0,   0,   0,   0,   2,   0,   0,   2,   2,   2,   2,   2,   2,   2,   2,   2,   2,   0,   0,   2,   0,   0,   0,   0,   2,   0},
            {0,   2,   0,   0,   0,   0,   2,   0,   0,   2,   0,   0,   0,   0,   0,   0,   0,   0,   2,   0,   0,   2,   0,   0,   0,   0,   2,   0},
            {0,   2,   0,   0,   0,   0,   2,   0,   0,   2,   0,   0,   0,   0,   0,   0,   0,   0,   2,   0,   0,   2,   0,   0,   0,   0,   2,   0},
            {700,   2,   2,   2,   2,   2,   2,   2,   2,   601, 2,   2,   2,   2,   2,   2,   2,   2,   602, 2,   2,   2,   2,   2,   2,   2,   2,   701},
            {0,   2,   0,   0,   0,   0,   2,   0,   0,   2,   0,   0,   0,   0,   0,   0,   0,   0,   2,   0,   0,   2,   0,   0,   0,   0,   2,   0},
            {0,   2,   0,   0,   0,   0,   2,   0,   0,   2,   0,   0,   0,   0,   0,   0,   0,   0,   2,   0,   0,   2,   0,   0,   0,   0,   2,   0},
            {0,   2,   0,   0,   0,   0,   2,   0,   0,   2,   2,   2,   2,   2,   2,   2,   2,   2,   2,   0,   0,   2,   0,   0,   0,   0,   2,   0},
            {0,   2,   0,   0,   0,   0,   2,   0,   0,   2,   0,   0,   0,   0,   0,   0,   0,   0,   2,   0,   0,   2,   0,   0,   0,   0,   2,   0},
            {0,   2,   0,   0,   0,   0,   2,   0,   0,   2,   0,   0,   0,   0,   0,   0,   0,   0,   2,   0,   0,   2,   0,   0,   0,   0,   2,   0},
            {0,   2,   2,   2,   2,   2,   2,   2,   2,   603, 2,   2,   2,   0,   0,   2,   2,   2,   604, 2,   2,   2,   2,   2,   2,   2,   2,   0},
            {0,   2,   0,   0,   0,   0,   2,   0,   0,   0,   0,   0,   2,   0,   0,   2,   0,   0,   0,   0,   0,   2,   0,   0,   0,   0,   2,   0},
            {0,   2,   0,   0,   0,   0,   2,   0,   0,   0,   0,   0,   2,   0,   0,   2,   0,   0,   0,   0,   0,   2,   0,   0,   0,   0,   2,   0},
            {0,   3,   2,   2,   0,   0,   2,   2,   2,   2,   2,   2,   2,   600, 2,   2,   2,   2,   2,   2,   2,   2,   0,   0,   2,   2,   3,   0},
            {0,   0,   0,   2,   0,   0,   2,   0,   0,   2,   0,   0,   0,   0,   0,   0,   0,   0,   2,   0,   0,   2,   0,   0,   2,   0,   0,   0},
            {0,   0,   0,   2,   0,   0,   2,   0,   0,   2,   0,   0,   0,   0,   0,   0,   0,   0,   2,   0,   0,   2,   0,   0,   2,   0,   0,   0},
            {0,   2,   2,   2,   2,   2,   2,   0,   0,   2,   2,   2,   2,   0,   0,   2,   2,   2,   2,   0,   0,   2,   2,   2,   2,   2,   2,   0},
            {0,   2,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   2,   0,   0,   2,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   2,   0},
            {0,   2,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   2,   0,   0,   2,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   2,   0},
            {0,   2,   2,   2,   2,   2,   2,   2,   2,   2,   2,   2,   2,   2,   2,   2,   2,   2,   2,   2,   2,   2,   2,   2,   2,   2,   2,   0},
            {0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0}

    };

    public Escenari(DemoSurfaceView view) {
        super(view);
        mMida = Math.min(view.getWidth(), view.getHeight());

        files = escenari.length;
        columnes = escenari[0].length;
        midaCella = mMida / columnes;

        //inicialitzacio de pintures
        pParet = new Paint();
        pParet.setColor(view.getResources().getColor(R.color.pared));
        pParet.setStrokeWidth(5);
        pParet.setStyle(Paint.Style.STROKE);

        pGroga = new Paint();
        pGroga.setColor(view.getResources().getColor(R.color.coco));

        mBackground = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBackground);
        inicialitzarEscenari(mCanvas);

        inicilitzarHashTipusCasella();
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
        tipusCasella.put(TipusCasella.POSICIO_INICI_TUNEL.codi, TipusCasella.POSICIO_INICI_TUNEL);
        tipusCasella.put(TipusCasella.POSICIO_FI_TUNEL.codi, TipusCasella.POSICIO_FI_TUNEL);
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
        casella.x += direccio.x;
        casella.y += direccio.y;
        return getCella(casella) != TipusCasella.PARET;
    }

    public boolean esticALaBocaDelTunel(Point posGraella){
        if (getCella(posGraella) == TipusCasella.POSICIO_INICI_TUNEL &&
            emPucMoureEnDireccio(getPosicioEnPixels(posGraella), new Point(-1, 0))) {
            return true;
        } else if (getCella(posGraella) == TipusCasella.POSICIO_FI_TUNEL &&
                emPucMoureEnDireccio(getPosicioEnPixels(posGraella), new Point(1, 0))) {
            return true;
        }
        return false;
    }

    public void creuarTunel(Point posGraella) {
        if (esticALaBocaDelTunel(posGraella) && getCella(posGraella) == TipusCasella.POSICIO_INICI_TUNEL) {
            escenari[posGraella.y][posGraella.x] = TipusCasella.POSICIO_FI_TUNEL.codi;
        } else if (esticALaBocaDelTunel(posGraella) && getCella(posGraella) == TipusCasella.POSICIO_FI_TUNEL) {
            escenari[posGraella.y][posGraella.x] = TipusCasella.POSICIO_INICI_TUNEL.codi;
        }
    }

    // Retornem true NOMES quan el personatge esta clavat dins de la cel·la.
    // En aquest moment pot fer girs i es controlen col·lisions
    public boolean esticALaCasella(PointF posicioPixels){
        Point posGraella = getPosicioALaGraella(posicioPixels);
        PointF posExacta = getPosicioEnPixels(posGraella);
        return (Math.abs(posicioPixels.x - posExacta.x) < TOLERANCIA &&
            Math.abs(posicioPixels.y - posExacta.y) < TOLERANCIA);
    }

    @Override
    public void onDraw(Canvas canvas) {

        canvas.drawBitmap(mBackground, 0,0 , pParet);

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

    /*public PointF getPosicioIniciGhost() {

        for (int x = 0; x < columnes; x++){
            for (int y = 0; y < files; y++) {
                if (escenari[y][x] != TipusCasella.PARET.ordinal())
                    return getPosicioEnPixels(new Point(x, y));
            }
        }
        throw new RuntimeException("Quina merda de mapa... no té cap camí!!!");
    }*/

    public PointF getPosicioInici(int posicio) {

        for (int x = 0; x < columnes; x++){
            for (int y = 0; y < files; y++) {
                if (escenari[y][x] == posicio) {
                    escenari[y][x] = TipusCasella.COCO.codi;
                    return getPosicioEnPixels(new Point(x, y));
                }

            }
        }
        throw new RuntimeException("Quina merda de mapa... no te posicio: " + posicio +" !!!");
    }

    public PointF getPosicioIniciPacman() {
        return getPosicioInici(TipusCasella.POSICIO_INICI_PACMAN.codi);
    }

    public PointF getPosicioIniciBlinky() {
        return getPosicioInici(TipusCasella.POSICIO_INICI_BLINKY.codi);
    }

    public PointF getPosicioIniciClyde() {
        return getPosicioInici(TipusCasella.POSICIO_INICI_CLYDE.codi);
    }

    public PointF getPosicioIniciPinky() {
        return getPosicioInici(TipusCasella.POSICIO_INICI_PINKY.codi);
    }

    public PointF getPosicioIniciInky() {
        return getPosicioInici(TipusCasella.POSICIO_INICI_INKY.codi);
    }

    public int getMidaCella() {
        return midaCella;
    }
}
