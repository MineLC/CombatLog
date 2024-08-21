package lc.mine.combatlog.storage;

import java.util.Set;

public final class Options {
    private int lcoinsOnKill;
    private int pvpTagTime;
    private int killsToLevelup;
    private Set<String> blockCommands;

    public void setPvpTagTime(int pvpTagTime) {
        this.pvpTagTime = pvpTagTime;
    }
    public Set<String> getBlockCommands() {
        return blockCommands;
    }
    public void setBlockCommands(Set<String> blockCommands) {
        this.blockCommands = blockCommands;
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
