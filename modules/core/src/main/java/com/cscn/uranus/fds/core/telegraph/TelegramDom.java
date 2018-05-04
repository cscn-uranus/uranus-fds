package com.cscn.uranus.fds.core.telegraph;

import java.util.regex.Pattern;

public interface TelegramDom {

  String getHeader();

  String getContent();

  String getTail();

  int length();
}
