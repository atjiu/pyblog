package cn.tomoya.util;

import org.pegdown.Extensions;
import org.pegdown.PegDownProcessor;
import org.springframework.stereotype.Component;

@Component
public class MarkdownUtil {

  private final static PegDownProcessor md = new PegDownProcessor(
    Extensions.ALL_OPTIONALS | Extensions.ALL_WITH_OPTIONALS);

  public String pegDown(String content) {
    return md.markdownToHtml(content == null ? "" : content);
  }

}