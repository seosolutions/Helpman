package com.utk.user.helpman;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by user on 14-03-2016.
 */
public class HelpmanUtil {
    public static String getProperty(String key, Context mContext) throws IOException {
        Properties properties = new Properties();
        AssetManager assetManager = mContext.getAssets();
        InputStream inputStream = assetManager.open("config.properties");
        properties.load(inputStream);
        return properties.getProperty(key);
    }
}
