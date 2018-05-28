package kr.hs.dgsw.flow.school;

import java.util.Calendar;
import java.util.List;

/**
 * Created by neutral on 23/03/2018.
 */

public class SchoolMonthlyMenu {
    private final Calendar date;
    private final List<SchoolMenu> menus;

    public SchoolMonthlyMenu(Calendar date, List<SchoolMenu> menus) {
        this.date = date;
        this.menus = menus;
    }

    public Calendar getDate() {
        return date;
    }

    public List<SchoolMenu> getMenus() {
        return menus;
    }
}
