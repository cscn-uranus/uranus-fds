package com.cscn.uranus.fds.core.telegraph;

import java.util.regex.Pattern;

public interface TelegramMapper {
  void readValue(String input);

  Pattern regexPattern();
}
