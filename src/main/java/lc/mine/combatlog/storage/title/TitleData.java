package lc.mine.combatlog.storage.title;

public final class TitleData {

    private final Title[] titles;
    private int next = 0;

    public TitleData(Title[] titles) {
        this.titles = titles;
    }

    public Title next() {
        if (titles.length == next+1) {
            next = 0;
            return titles[next];
        }
        return titles[next++];
    }
}
