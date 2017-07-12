<#macro html title page_tab=''>
<!doctype html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport"
        content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
  <meta http-equiv="X-UA-Compatible" content="ie=edge">

  <title>${title!_site.base.title}</title>
  <link href="//cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
  <link href="//cdn.bootcss.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
  <script src="//cdn.bootcss.com/jquery/2.2.2/jquery.min.js"></script>
  <script src="//cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
  <style>
    .navbar{border:0;}
    .navbar-default{background-color:#fff;}
    body {background-color:#eee;}
    .container img {max-width: 100%;}
    .page-header{margin-top:0}
    pre {padding:0;color:#fff;}
    .hljs{display:block;overflow-x:auto;padding:.5em;background:#23241f}
    .hljs,.hljs-tag,.hljs-subst{color:#f8f8f2}
    .hljs-strong,.hljs-emphasis{color:#a8a8a2}
    .hljs-bullet,.hljs-quote,.hljs-number,.hljs-regexp,.hljs-literal,.hljs-link{color:#ae81ff}
    .hljs-code,.hljs-title,.hljs-section,.hljs-selector-class{color:#a6e22e}
    .hljs-strong{font-weight:700}
    .hljs-emphasis{font-style:italic}
    .hljs-keyword,.hljs-selector-tag,.hljs-name,.hljs-attr{color:#f92672}
    .hljs-symbol,.hljs-attribute{color:#66d9ef}
    .hljs-params,.hljs-class .hljs-title{color:#f8f8f2}
    .hljs-string,.hljs-type,.hljs-built_in,.hljs-builtin-name,.hljs-selector-id,.hljs-selector-attr,.hljs-selector-pseudo,.hljs-addition,.hljs-variable,.hljs-template-variable{color:#e6db74}
    .hljs-comment,.hljs-deletion,.hljs-meta{color:#75715e}
  </style>
  <#--baidu_analytics-->
  <#if _site.analytics?? && (_site.analytics.baidu?length > 0)>
  <script>
    var _hmt = _hmt || [];
    (function() {
      var hm = document.createElement("script");
      hm.src = "//hm.baidu.com/hm.js?${_site.analytics.baidu}";
      var s = document.getElementsByTagName("script")[0];
      s.parentNode.insertBefore(hm, s);
    })();
  </script>
  </#if>
  <#--google_analytics-->
  <#if _site.analytics?? && (_site.analytics.google?length >0)>
  <script>
    // google analytics
    (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
          (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
        m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
    })(window,document,'script','https://www.google-analytics.com/analytics.js','ga');
    ga('create', '${_site.analytics.google}', 'auto');
    ga('send', 'pageview');
  </script>
  </#if>

</head>
<body>
  <#include "header.ftl"/>
  <@header page_tab=page_tab/>
<div class="container">
  <#nested />
</div>
  <#include "footer.ftl"/>

<script src="//cdn.bootcss.com/highlight.js/9.12.0/highlight.min.js"></script>
<script>hljs.initHighlightingOnLoad();</script>
</body>
</html>
</#macro>