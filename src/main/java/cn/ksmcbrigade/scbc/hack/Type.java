package cn.ksmcbrigade.scbc.hack;

public enum Type {
    UI("UI"),
    OTHER("OTHER"),
    COMBAT("COMBAT"),
    RENDER("RENDER"),
    MOVE("MOVE"),
    CHAT("CHAT"),
    FUN("FUN");

    final String name;

    Type(String name){
        this.name = name;
    }
}
