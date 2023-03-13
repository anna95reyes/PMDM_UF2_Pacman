package com.example.pacman.model;

public enum TipusCasella {
    PARET(0),
    CAMI(1),
    COCO(2),
    SUPERCOCO(3),
    POSICIO_INICI_PACMAN(600),
    POSICIO_INICI_BLINKY(601),
    POSICIO_INICI_CLYDE(602),
    POSICIO_INICI_PINKY(603),
    POSICIO_INICI_INKY(604),
    POSICIO_TUNEL_ESQ(700),
    POSICIO_TUNEL_DRE(701);

    public int codi;
    TipusCasella (int codi) {
        this.codi = codi;
    }
}
