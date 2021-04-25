package com.booklistiy.book;

import java.util.Locale;

public class Language {
    private static Language mInstance= null;

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String lang;

    protected Language(){}

    public static synchronized Language getInstance() {
        if(null == mInstance){
            mInstance = new Language();
            mInstance.setLang(Locale.getDefault().getLanguage());
        }
        return mInstance;
    }
}
