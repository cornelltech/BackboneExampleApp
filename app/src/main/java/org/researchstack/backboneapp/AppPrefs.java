package org.researchstack.backboneapp;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.Set;


public class AppPrefs
{
    public static final String HAS_CONSENTED     = "HAS_CONSENTED";
    public static final String CONSENT_NAME      = "CONSENT_NAME";
    public static final String CONSENT_SIGNATURE = "CONSENT_SIGNATURE";
    public static final String HAS_SURVEYED      = "HAS_SURVEYED";
    public static final String SURVEY_RESULT     = "SURVEY_RESULT";
    public static final String YADL_ACTIVITIES      = "YADL_ACTIVITIES";
    public static final String MEDL_ITEMS      = "MEDL_ITEMS";


    //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    // Statics
    //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    private static AppPrefs instance;

    //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    // Field Vars
    //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    private final SharedPreferences prefs;

    AppPrefs(Context context)
    {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static synchronized AppPrefs getInstance(Context context)
    {
        if(instance == null)
        {
            instance = new AppPrefs(context);
        }
        return instance;
    }

    public boolean hasConsented()
    {
        return prefs.getBoolean(HAS_CONSENTED, false);
    }

    public void setHasConsented(boolean consented)
    {
        prefs.edit().putBoolean(HAS_CONSENTED, consented).apply();
    }

    public boolean hasSurveyed()
    {
        return prefs.getBoolean(HAS_SURVEYED, false);
    }

    public void setHasSurveyed(boolean surveyed)
    {
        prefs.edit().putBoolean(HAS_SURVEYED, surveyed).apply();
    }

    public Set<String> getYADLActivities()
    {
        return prefs.getStringSet(YADL_ACTIVITIES, null);
    }

    public void setYADLActivities(Set<String> activities) {
        prefs.edit().putStringSet(YADL_ACTIVITIES, activities).apply();
    }

    public Set<String> getMEDLItems()
    {
        return prefs.getStringSet(MEDL_ITEMS, null);
    }

    public void setMEDLItems(Set<String> activities) {
        prefs.edit().putStringSet(MEDL_ITEMS, activities).apply();
    }
}