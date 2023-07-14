package com.anmol.clashking;

public class leaderboard_model {
    String name,url,kills;
    public leaderboard_model(){

    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getKills() {
        return kills;
    }

    public void setKills(String kills) {
        this.kills = kills;
    }

    public leaderboard_model(String name, String url, String kills) {
        this.name = name;
        this.url = url;
        this.kills = kills;
    }
}
