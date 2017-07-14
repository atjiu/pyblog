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
public class CategoriesDirective implements TemplateDirectiveModel {

  @Autowired
  private FileUtil fileUtil;

  @Override
  public void execute(Environment environment, Map map, TemplateModel[] templateModels,
                      TemplateDirectiveBody templateDirectiveBody) throws TemplateException, IOException {
    DefaultObjectWrapperBuilder builder = new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_26);

    String c = map.get("category") == null ? null : map.get("category").toString();
    List<String> allCategories = fileUtil.getCategories();

    List categories = new ArrayList<>();
    if(StringUtils.isEmpty(c)) {
      for(String category: allCategories) {
        categories.add(assemblyCategory(category));
      }
    } else {
      categories.add(assemblyCategory(c));
    }

    environment.setVariable("categories", builder.build().wrap(categories));
    templateDirectiveBody.render(environment.getOut());
  }

  public Map assemblyCategory(String catetory) {
    Map map1 = new HashMap();
    List<Blog> blog1 = new ArrayList<>();
    for(Blog blog: fileUtil.getBlogs()) {
      if(blog.getCategories().contains(catetory)) {
        blog1.add(blog);
      }
    }
    map1.put("name", catetory);
    map1.put("blogs", blog1);
    return map1;
  }
}
