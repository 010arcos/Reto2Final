package com.example.reto2javafx;

public class Premio {
    private String tipo;
    private Categoria tipoTorneo;
    private int puesto;
    private int importe;
    private int rankingI;
    private int prioridad;
    private String nombreF;
    private String namePremioShow;

public Premio (int puesto, String nombreF, int importe){
    this.puesto=puesto;
    this.nombreF=nombreF;
    this.importe=importe;
}
    public Premio(String tipo, Categoria categoria, int puesto, int importe, int rankingI, int prioridad) {
        this.tipo = tipo;
        this.tipoTorneo = categoria;
        this.puesto = puesto;
        this.importe = importe;
        this.rankingI = rankingI;
        this.prioridad= prioridad;
    }
    public Premio(String tipo, Categoria categoria, int puesto, int importe, int rankingI, int prioridad, String nombreF) {
        this.tipo = tipo;
        this.tipoTorneo = categoria;
        this.puesto = puesto;
        this.importe = importe;
        this.rankingI = rankingI;
        this.prioridad= prioridad;
        this.nombreF= nombreF;

    }
    public Premio(String tipo, Categoria categoria, int puesto, int importe, String nombreF){
    this.tipo = tipo;
        this.tipoTorneo = categoria;
        this.puesto = puesto;
        this.importe = importe;
        this.nombreF= nombreF;

    }

    public String getNombreF() {
        return nombreF;
    }

    public void setNombreF(String nombreF) {
        this.nombreF = nombreF;
    }

    public String getNamePremioShow() {
        return namePremioShow;
    }

    public void setNamePremioShow(String namePremioShow) {
        this.namePremioShow = namePremioShow;
    }

    public Categoria getTipoTorneo() {
        return tipoTorneo;
    }

    public void setTipoTorneo(Categoria tipoTorneo) {
        this.tipoTorneo = tipoTorneo;
    }

    public int getPuesto() {
        return puesto;
    }

    public void setPuesto(int puesto) {
        this.puesto = puesto;
    }

    public int getImporte() {
        return importe;
    }

    public void setImporte(int importe) {
        this.importe = importe;
    }

    public int getRankingI() {
        return rankingI;
    }

    public void setRankingI(int rankingI) {
        this.rankingI = rankingI;
    }


    public enum Categoria {
        A, B
    }

    public Premio(String tipo, String namePremioShow) {   /*modifcar yt añadir nombre, para mostra en optar */

    this.tipo = tipo;
    this.namePremioShow = namePremioShow;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getPrioridad(){return prioridad;}

    public void setPrioridad(int prioridad){this.prioridad=prioridad;}

}

