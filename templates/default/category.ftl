<#include "layout/layout.ftl">
<@html title="Categories - " + _site.title page_tab="category">
<div class="panel panel-default">
  <div class="panel-body">
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
  </div>
</div>
</@html>