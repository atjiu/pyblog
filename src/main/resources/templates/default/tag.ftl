<#include "layout.ftl">
<@html title="Tags - " + _site.title page_tab=page_tab>
<div class="panel panel-default">
  <div class="panel-body">
    <@tags_tag tag=tag>
      <#list tags as tag>
        <div class="page-header">
          <h2>${tag.name}</h2>
        </div>
        <ul>
          <#list tag.blogs as blog>
            <li class="lead">
              <a href="${blog.url!}index.html">${blog.title!}</a>
            </li>
          </#list>
        </ul>
      </#list>
    </@tags_tag>
  </div>
</div>
</@html>