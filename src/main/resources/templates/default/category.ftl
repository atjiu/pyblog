<#include "layout.ftl">
<@html title="Categories - " + _site.base.title page_tab=page_tab>
<div class="panel panel-default">
  <div class="panel-body">
    <@categories_tag>
      <#list categories as category>
        <div class="page-header">
          <h2>${category.name}</h2>
        </div>
        <ul>
          <#list category.blogs as blog>
            <li class="lead">
              <a href="${blog.url!}index.html">${blog.title!}</a>
            </li>
          </#list>
        </ul>
      </#list>
    </@categories_tag>
  </div>
</div>
</@html>