package lc.mine.combatlog.storage;

import java.util.Set;

import lc.mine.combatlog.storage.title.TitleData;

public final class Options {
    private int lcoinsOnKill;
    private int pvpTagTime;
    private int killsToLevelup;
    private Set<String> blockCommands;
    private boolean instantRespawn;

    private TitleData titleData;

    public void setPvpTagTime(int pvpTagTime) {
        this.pvpTagTime = pvpTagTime;
    }
    public Set<String> getBlockCommands() {
        return blockCommands;
    }
    public TitleData getTitleData() {
        return titleData;
    }
    public void setTitleData(TitleData titleData) {
        this.titleData = titleData;
    }
    public void setBlockCommands(Set<String> blockCommands) {
        this.blockCommands = blockCommands;
    }
    public int getPvpTagTime() {
        return pvpTagTime;
    }
    public void setInstantRespawn(boolean instantRespawn) {
        this.instantRespawn = instantRespawn;
    }
    public boolean getInstantRespawn() {
        return this.instantRespawn;
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
