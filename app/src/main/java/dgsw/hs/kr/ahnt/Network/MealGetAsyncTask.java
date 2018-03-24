package dgsw.hs.kr.ahnt.Network;

import android.app.Activity;
import android.os.AsyncTask;
import android.view.View;

import java.util.Calendar;
import java.util.List;

import dgsw.hs.kr.ahnt.Adapter.MealPagerAdapter;
import dgsw.hs.kr.ahnt.Helper.MealHelper;
import dgsw.hs.kr.ahnt.Interface.IPassValue;
import dgsw.hs.kr.ahnt.Interface.IProgressBarControl;
import dgsw.hs.kr.ahnt.school.School;
import dgsw.hs.kr.ahnt.school.SchoolException;
import dgsw.hs.kr.ahnt.school.SchoolMenu;
import dgsw.hs.kr.ahnt.school.SchoolMonthlyMenu;

/**
 * Created by neutral on 23/03/2018.
 *
 * 원하는 년도와 월을 넣은 Calendar를 인자로 받아
 * 그 달의 급식 메뉴의 리스트를 반환한다.
 */
public class MealGetAsyncTask extends AsyncTask<Calendar, Integer, SchoolMonthlyMenu> {

    Calendar cur;
    IPassValue passable;

    public MealGetAsyncTask(IPassValue passable) {
        this.passable = passable;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (passable instanceof IProgressBarControl) {
            ((IProgressBarControl)passable).showProgressBar();
        }
    }

    @Override
    protected SchoolMonthlyMenu doInBackground(Calendar... calendars) {
        School api = new School(School.Type.HIGH, School.Region.DAEGU, "D100000282");

        if (calendars.length <= 0) {
            return null;
        }

        cur = calendars[0];
        if (cur == null) {
            return null;
        }

        try {
            SchoolMonthlyMenu menu = api.getMonthlyMenu(cur.get(Calendar.YEAR), cur.get(Calendar.MONTH) + 1);

            return menu;
        } catch (SchoolException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(SchoolMonthlyMenu menu) {
        super.onPostExecute(menu);

        if (cur != null) {
            String mealCode = MealHelper.createMealCode(cur);

            if (passable instanceof IPassValue) {
                ((IPassValue)passable).passValue(menu);
            }

            if (passable instanceof IProgressBarControl) {
                ((IProgressBarControl)passable).hideProgressBar();
            }
        }
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        if (passable instanceof IProgressBarControl) {
            ((IProgressBarControl)passable).setProgressBarValue(values[0]);
        }
    }
}