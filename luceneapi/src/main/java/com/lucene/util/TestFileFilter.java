package com.lucene.util;

import java.io.File;
import java.io.FileFilter;

/**
 * Created by huangjinlong7 on 2017/9/27.
 */
public class TestFileFilter implements FileFilter {
    @Override
    public boolean accept(File pathname) {
        return pathname.getName().toLowerCase().endsWith(".txt");
    }
}
