<#macro paginate currentPage totalPage actionUrl>
  <#if (totalPage <= 0) || (currentPage > totalPage)><#return></#if>
  <#local startPage = currentPage - 2>
  <#if (startPage < 1)><#local startPage = 1></#if>

  <#local endPage = currentPage + 2>
  <#if (endPage > totalPage)><#local endPage = totalPage></#if>
<ul class="pagination <#if totalPage == 1>hidden-xs hidden-sm hidden-md hidden-lg</#if>" style="margin:0 0 15px 0;">
  <#if (currentPage <= 3)>
    <#local startPage = 1>
  </#if>
  <#if ((totalPage - currentPage) < 2)>
    <#local endPage = totalPage>
  </#if>

  <#if (currentPage == 1)>
  <#else>
    <li><a href="${actionUrl}page1/index.html">&lt;&lt;</a></li>
    <li><a href="${actionUrl}page#{currentPage - 1}/index.html">&lt;</a></li>
  </#if>

  <#list startPage..endPage as i>
    <#if currentPage == i>
      <li class="active"><a class="disabled">#{i}</a></li>
    <#else>
      <li><a href="${actionUrl}page#{i}/index.html">#{i}</a></li>
    </#if>
  </#list>

  <#if (currentPage == totalPage)>
  <#else>
    <li><a href="${actionUrl}page#{currentPage + 1}/index.html">&gt;</a></li>
    <li><a href="${actionUrl}page#{totalPage}/index.html">&gt;&gt;</a></li>
  </#if>
</ul>
</#macro>