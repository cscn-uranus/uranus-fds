package com.cscn.uranus.fds.core.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileUtil {
  private static String inputStreamToString(InputStream is) throws IOException {
    StringBuilder sb = new StringBuilder();
    String line;
    BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
    while ((line = br.readLine()) != null) {
      sb.append(line);
    }
    br.close();
    return sb.toString();
  }

  public static String readStringFromFile(String filePath) {
    File file = new File(filePath);
    try {
      return inputStreamToString(new FileInputStream(file));
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }
}
