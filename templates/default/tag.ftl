<#include "layout/layout.ftl">
<@html title="Tags - " + _site.title page_tab="tag">
<div class="panel panel-default">
  <div class="panel-body">
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
  </div>
</div>
</@html>