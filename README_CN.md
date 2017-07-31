# PyBlog

类似于jekyll的java语言开发的静态博客系统

[English](https://github.com/tomoya92/pyblog/blob/master/README.md) | [中文](https://github.com/tomoya92/pyblog/blob/master/README_CN.md)

# TOC

- [开始](#开始)
    - [快速指南](#快速指南)
    - [安装环境](#安装环境)
    - [基本用法](#基本用法)
    - [目录结构](#目录结构)
    - [配置](#配置)
- [博客内容](#博客内容)
    - [头信息](#头信息)
    - [编写博客](#编写博客)
- [个性化](#个性化)
    - [模板](#模板)
    - [模板静态资源文件](#模板静态资源文件)
    - [常用变量](#常用变量)
    - [常用模板标签](#常用模板标签)
        - [markdown](#markdown)
        - [blogs_tag](#blogs_tag)
    - [对象结构](#对象结构)
        - [Blog](#blog)
        - [Page](#page)

# 开始

## 快速指南

1. 下载[pyblog.tar.gz](https://github.com/tomoya92/pyblog/releases/tag/0.0.1)
2. 解压
3. 运行start.sh文件 `./start.sh`
4. 打开浏览器 http://localhost:4000/ 即可

**注意**

关闭服务：`./shutdown.sh`

如果 `./start.sh` 或 `./shutdown.sh` 运行失败，需要查看一下文件的权限，授权命令：`chmod a+x start.sh shutdown.sh`

## 安装环境

pyblog只需要一个java运行环境，关于java运行环境怎么配置的问题相信 百度/Google 会找到很多文章，按照文章内容操作一下即可

只需要保证 `java -version` 能出现java的版本号即可

**推荐使用java8版本**

## 基本用法

启动服务，然后将博客文件放入 `_posts` 即可自动发布博客

关于配置信息请看下面的配置说明

## 目录结构

```
├── application.yml
├── _posts
│   └── 2017-07-12-hello-world.md
├── pyblog.jar
├── shutdown.sh
├── _sites
├── start.sh
├── static
└── templates
```

| 名称                | 解释                                   |
| ----------------- | ------------------------------------ |
| `application.yml` | pyblog唯一配置文件，配置站点的各种信息               |
| `_posts`          | 博客原文件存放位置，在application.yml里可以配置成其它名字 |
| `pyblog.jar`      | 主程序，无需关注                             |
| `shutdown.sh`     | 关闭服务脚本                               |
| `_site`           | 发布的静态页面存放位置                          |
| `start.sh`        | 启动服务脚本                               |
| `static`          | 模板文件里使用的静态文件存放位置                     |
| `templates`       | 模板文件存在位置，可以添加自己喜欢的其它模板，生成不同风格的博客     |

## 配置

| 名称                  | 解释                                       |
| ------------------- | ---------------------------------------- |
| `server.port`       | 服务端口                                     |
| `debug`             | 是否为开发模式，如果是的话，那么修改 templates 下的文件会即时更新博客页面，仅在开发博客模板的时候开启 |
| `theme`             | 主题（既博客模板）默认是default(default模板位于pyblog.jar里，无法修改)，可以添加自己喜欢的其它模板，生成不同风格的博客 |
| `url`               | 站点访问地址，后面不带 `/`，否则博客链接中会出现 `//` 的现象      |
| `permalink`         | 博客链接风格，默认为 `/:year/:month/:day/:title/` 也就是 _posts 文件夹里文件名字里的 `年/月/日/标题`，请务必写成这样的形式，以防发布的博客页面被覆盖的现象发生 |
| `excerpt_separator` | 博客原文里分割出的摘要，在博客列表里展示用的，默认为四次换行           |
| `page_size`         | 博客列表每页显示的条数                              |
| `post`              | 博客原文存放文件夹的名称                             |
| `static_html`       | 发布的静态页面存放位置名称                            |
| `page`              | 是否使用分页                                   |
| `rss`               | 是否生成feed.xml |
| `links.xx`          | 友情链接，跟base配置一样，可以写任何内容，在模板里直接通过 ${_site.links.xx}取值展示就可以了 |
| `contacts.xx`       | 博主的联系方式，跟base配置一样，可以写任何内容，在模板里直接通过 ${_site.contacts.xx}取值展示就可以了 |
| `analytics.xx`      | 博客访问统计方式，跟base配置一样，可以写任何内容，在模板里直接通过 ${_site.analytics.xx}取值展示就可以了 |
| `comment.xx`        | 博客评论方式，跟base配置一样，可以写任何内容，在模板里直接通过 ${_site.comment.xx}取值展示就可以了 |
| `other.xx`          | 其它变量，可自行配置，跟base配置一样，可以写任何内容，在模板里直接通过 ${_site.other.xx}取值展示就可以了 |

# 博客内容

## 头信息

要求： 按照 YAML 的格式写在两行三虚线之间，下面是一个基本的例子：

```yaml

---
title: hello world
---

```

在这两行的三虚线之间，你可以设置预定义的变量，变量有以下几种：

| 名称           | 解释                          |
| ------------ | --------------------------- |
| `title`      | 博客标题，必需                     |
| `date`       | 博客日期，如果没有，以博客文件前的日期为博客发布的日期 |
| `author`     | 博客发布者名称                     |
| `tags`       | 博客的标签，以空格分割，可以写多个           |
| `categories` | 博客的分类，以空格分割，可以写多个           |

**注意，博客文件请使用UTF-8编码格式**

## 编写博客

博客文件名称必须要按照 `年-月-日-标题.md` 的格式来命名

博客文章包含头信息 + Markdown标记语法的内容，你完全可以放心的使用markdown语法来编写博客，而且现在也只支持markdown语法

博客内容里加上配置文件里配置的 `excerpt_separator` 的内容会分割出博客摘要，在模板里可以使用 `${blog.excerpt}` 获取

# 个性化

## 模板

pyblog使用的模板为freemarker，模板文件放在templates文件夹里, 布局文件放在 `templates/layout` 里，布局文件不会参与生成静态页面

创建一个文件夹，**名字不能为default，同时application.yml里的theme变量也要改成你创建的文件夹的名称** 然后将模板文件放到你创建的文件夹里，比如：

```
templates/
└── default
    ├── 404.ftl
    ├── archive.ftl
    ├── blog.ftl
    ├── category.ftl
    ├── index.ftl
    ├── layout
    │   ├── footer.ftl
    │   ├── header.ftl
    │   ├── layout.ftl
    │   └── paginate.ftl
    └── tag.ftl
```

**注意：archive.ftl, category.ftl, blog.ftl, index.ftl, tag.ftl 这几个文件的名称是固定的，不能修改成其它名字**，其它文件名字可以自行定义，生成的页面就是你设置的文件名，后缀为html

例如：example.ftl -> example.html

这样就可以开发自己的模板了，风格可以完全自己定

**提示，开发模板的时候开启debug模式可以实时的发布哦**

## 模板静态资源文件

模板里要用到的静态资源文件请放在 static 文件夹里，然后在页面里直接引用就可以了

## 常用变量

开发模板的时候要用到的变量汇总

| 名称                   | 解释                       |
| -------------------- | ------------------------ |
| `_site.theme`        | 获取到使用的模板名                |
| `_site.url`          | 站点的访问域名（后面不带`/`）         |
| `_site.page_size`    | 获取每页显示的博客条数              |
| `_site.page`         | 获取是否使用分页                 |
| `_site.links.xx`     | 获取博客站点的友链信息，配置了什么就可以取什么  |
| `_site.contacts.xx`  | 获取博主的联系方式，配置了什么就可以取什么    |
| `_site.analytics.xx` | 获取统计信息，配置了什么就可以取什么       |
| `_site.comment.xx`   | 获取评论的配置信息，配置了什么就可以取什么    |
| `_site.other.xx`     | 获取其它自定义的配置信息，配置了什么就可以取什么 |

## 常用模板标签

### markdown

说明：将markdown语法的文本转成html语法的标签

参数：无

用法：

`${markdown.pegDown(blog.content)}`

### blogs_tag

说明：获取所有博客的标签

参数：无

用法：

```html
<@blogs_tag>
  <#list blogs as blog>
    <p>${blog.title}</p>
  </#list>
</#list>
</@blogs_tag>
```

## 对象结构

### Blog

| 名称         | 类型           | 解释                                       |
| ---------- | ------------ | ---------------------------------------- |
| title      | String       | 博客标题                                     |
| excerpt    | String       | 博客摘要                                     |
| content    | String       | 博客内容                                     |
| url        | String       | 博客详情的访问链接，类似：/2017/07/11/hello-world/    |
| date       | String       | 博客发布日期，如果头信息里没有写上date属性的话，就会取文件名字上的日期作为博客发布时间 |
| author     | String       | 博客发布者                                    |
| categories | List<String> | 博客的类别                                    |
| tags       | List<String> | 博客的标签                                    |
| previous   | Blog         | 当前博客的上一篇                                 |
| next       | Blog         | 当前博客的下一篇                                 |

### Page

| 名称         | 类型         | 解释       |
| ---------- | ---------- | -------- |
| pageNo     | int        | 当前页      |
| pageSize   | int        | 每页显示的条数  |
| list       | List<Blog> | 当前页的博客列表 |
| firstPage  | boolean    | 是否是第一页   |
| lastPage   | boolean    | 是否是最后一页  |
| totalPage  | int        | 总页数      |
| totalCount | int        | 总条数      |
