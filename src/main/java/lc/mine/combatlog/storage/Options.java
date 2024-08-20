package lc.mine.combatlog.storage;

public final class Options {
    private int lcoinsOnKill;
    private int pvpTagTime;

    public void setPvpTagTime(int pvpTagTime) {
        this.pvpTagTime = pvpTagTime;
    }
    public int getPvpTagTime() {
        return pvpTagTime;
    }
    public void setLcoinsOnKill(int lcoinsOnKill) {
        this.lcoinsOnKill = lcoinsOnKill;
    }
    public int getLcoinsOnKill() {
        return lcoinsOnKill;
    }
}
