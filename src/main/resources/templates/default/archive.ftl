<#include "layout.ftl">
<@html title="Archives - " + _site.title page_tab=page_tab>
<div class="panel panel-default">
  <div class="panel-body">
    <@blogs_tag>
      <#if blogs?? && (blogs?size > 0)>
        <#assign year = blogs[0].date?string["yyyy"]>
        <div class="page-header">
          <h2>${year}</h2>
        </div>
      <ul>
        <#list blogs as blog>
          <#if blog.date?string["yyyy"] != year>
            <#assign year = blog.date?string["yyyy"]/>
          </ul>
            <div class="page-header">
              <h2>${year}</h2>
            </div>
          <ul>
          </#if>
          <li class="lead">
            <a href="${blog.url!}index.html">${blog.title!}</a>
          </li>
        </#list>
      </ul>
      </#if>
    </@blogs_tag>
  </div>
</div>
</@html>