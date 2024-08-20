package lc.mine.combatlog.storage;

public final class Options {
    private int lcoinsOnKill;
    private int pvpTagTime;
    private int killsToLevelup;

    public void setPvpTagTime(int pvpTagTime) {
        this.pvpTagTime = pvpTagTime;
    }

    public int getPvpTagTime() {
        return pvpTagTime;
    }
    public int getKillsToLevelup() {
        return killsToLevelup;
    }
    public void setKillsToLevelup(int killsToLevelup) {
        this.killsToLevelup = killsToLevelup;
    }
    public void setLcoinsOnKill(int lcoinsOnKill) {
        this.lcoinsOnKill = lcoinsOnKill;
    }
    public int getLcoinsOnKill() {
        return lcoinsOnKill;
    }
}
