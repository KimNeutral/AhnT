package dgsw.hs.kr.ahnt.school;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * School API
 * 전국 교육청 소속 교육기관의 학사일정, 메뉴를 간단히 불러올 수 있습니다.
 *
 * @author HyunJun Kim
 * @version 3.0
 */
public class SchoolMenuParser {
    public SchoolMenuParser() {
    }

    public static List<SchoolMenu> parse(String rawData, Calendar cal) throws SchoolException {
        if(rawData.length() < 1) {
            throw new SchoolException("불러온 데이터가 올바르지 않습니다.");
        } else {
            List<SchoolMenu> monthlyMenu = new ArrayList();
            rawData = rawData.replaceAll("\\s+", "");
            StringBuffer buffer = new StringBuffer();
            boolean inDiv = false;

            try {
                for(int i = 0; i < rawData.length(); ++i) {
                    if(rawData.charAt(i) == 118) {
                        if(inDiv) {
                            buffer.delete(buffer.length() - 4, buffer.length());
                            if(buffer.length() > 0) {
                                cal.set(Calendar.DAY_OF_MONTH, monthlyMenu.size() + 1);
                                monthlyMenu.add(parseDay(buffer.toString(), cal));
                            }

                            buffer.setLength(0);
                        } else {
                            ++i;
                        }

                        inDiv = !inDiv;
                    } else if(inDiv) {
                        buffer.append(rawData.charAt(i));
                    }
                }

                return monthlyMenu;
            } catch (Exception var5) {
                throw new SchoolException("급식 정보 파싱에 실패했습니다. 최신 버전으로 업데이트 해 주세요.");
            }
        }
    }

    private static SchoolMenu parseDay(String rawData, Calendar cal) {
        SchoolMenu menu = new SchoolMenu();
        rawData = rawData.replace("(석식)", "");
        rawData = rawData.replace("(선)", "");
        String[] chunk = rawData.split("<br/>");
        int parsingMode = 0;

        menu.date = (Calendar) cal.clone();
        for(int i = 1; i < chunk.length; ++i) {
            if(chunk[i].trim().length() >= 1) {
                if(chunk[i].equals("[조식]")) {
                    parsingMode = 0;
                    menu.breakfast = "";
                } else if(chunk[i].equals("[중식]")) {
                    parsingMode = 1;
                    menu.lunch = "";
                } else if(chunk[i].equals("[석식]")) {
                    parsingMode = 2;
                    menu.dinner = "";
                } else {
                    switch(parsingMode) {
                        case 0:
                            if(menu.breakfast.length() > 1) {
                                menu.breakfast = menu.breakfast + "\n" + chunk[i];
                            } else {
                                menu.breakfast = menu.breakfast + chunk[i];
                            }
                            break;
                        case 1:
                            if(menu.lunch.length() > 1) {
                                menu.lunch = menu.lunch + "\n" + chunk[i];
                            } else {
                                menu.lunch = menu.lunch + chunk[i];
                            }
                            break;
                        case 2:
                            if(menu.dinner.length() > 1) {
                                menu.dinner = menu.dinner + "\n" + chunk[i];
                            } else {
                                menu.dinner = menu.dinner + chunk[i];
                            }
                    }
                }
            }
        }

        return menu;
    }
}
