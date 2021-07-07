package com.example.allinonescore.Data;

public class SubTeam {

    private String nmae,run,ball,four,six,out;

    public SubTeam(String nmae, String run, String ball, String four, String six, String out) {
        this.nmae = nmae;
        this.run = run;
        this.ball = ball;
        this.four = four;
        this.six = six;
        this.out = out;
    }

    public String getNmae() {
        return nmae;
    }

    public void setNmae(String nmae) {
        this.nmae = nmae;
    }

    public String getRun() {
        return run;
    }

    public void setRun(String run) {
        this.run = run;
    }

    public String getBall() {
        return ball;
    }

    public void setBall(String ball) {
        this.ball = ball;
    }

    public String getFour() {
        return four;
    }

    public void setFour(String four) {
        this.four = four;
    }

    public String getSix() {
        return six;
    }

    public void setSix(String six) {
        this.six = six;
    }

    public String getOut() {
        return out;
    }

    public void setOut(String out) {
        this.out = out;
    }
}
