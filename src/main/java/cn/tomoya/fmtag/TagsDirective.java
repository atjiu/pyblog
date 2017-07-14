package cn.tomoya.fmtag;

import cn.tomoya.model.Blog;
import cn.tomoya.util.FileUtil;
import freemarker.core.Environment;
import freemarker.template.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tomoya on 2017/7/10.
 */
@Component
public class TagsDirective implements TemplateDirectiveModel {

  @Autowired
  private FileUtil fileUtil;

  @Override
  public void execute(Environment environment, Map map, TemplateModel[] templateModels,
                      TemplateDirectiveBody templateDirectiveBody) throws TemplateException, IOException {
    DefaultObjectWrapperBuilder builder = new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_26);

    String t = map.get("tag") == null ? null : map.get("tag").toString();
    List<String> allTags = fileUtil.getTags();

    List tags = new ArrayList<>();
    if(StringUtils.isEmpty(t)) {
      for (String tag : allTags) {
        tags.add(assemblyTag(tag));
      }
    } else {
      tags.add(assemblyTag(t));
    }

    environment.setVariable("tags", builder.build().wrap(tags));
    templateDirectiveBody.render(environment.getOut());
  }

  public Map assemblyTag(String tag) {
    Map map1 = new HashMap();
    List<Blog> blog1 = new ArrayList<>();
    for(Blog blog: fileUtil.getBlogs()) {
      if(blog.getTags().contains(tag)) {
        blog1.add(blog);
      }
    }
    map1.put("name", tag);
    map1.put("blogs", blog1);
    return map1;
  }
}
