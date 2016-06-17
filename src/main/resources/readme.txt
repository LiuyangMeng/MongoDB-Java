修改器$inc可以对文档的某个值为数字型（只能为满足要求的数字）的键进行增减的操作
$set用来指定一个键并更新键值，若键不存在并创建
对于内嵌文档在使用$set更新时，使用"."连接的方式
$unset主要是用来删除键,使用修改器$unset时，不论对目标键使用1、0、-1或者具体的字符串等都是可以删除该目标键
$push--向文档的某个数组类型的键添加一个数组元素，不过滤重复的数据。添加时键存在，要求键值类型必须是数组；键不存在，则创建数组类型的键
数组修改器--$ne/$addToSet 要给数组类型键值添加一个元素时，避免在数组中产生重复数据，$ne在有些情况是不通行的
$pop从数组的头或者尾删除数组中的元素 从数组的尾部删除 1:1 从数组的头部 -1:-1 从数组的尾部删除 0:0
$pull从数组中删除满足条件的元素
upsert是一种特殊的更新。当没有符合条件的文档，就以这个条件和更新文档为基础创建一个新的文档，如果找到匹配的文档就正常的更新。
使用upsert，既可以避免竞态问题，也可以减少代码量（update的第三个参数就表示这个upsert，参数为true时）

查询name为null，会查询出name字段不存的数据，如下：
> db.foo.find({name:{$in:[null]}})

name字段加上 $exists:true
> db.foo.find({name:{$in:[null],$exists:true}})

查询name为不为空时(not null )
> db.foo.find({name:{$ne:null}})

使用正则表达式

以下命令使用正则表达式查找包含 w3cschool.cc 字符串的文章：

>db.posts.find({post_text:{$regex:"w3cschool.cc"}})

以上查询也可以写为：

>db.posts.find({post_text:/w3cschool.cc/})

集合中会返回所有包含字符串 w3cschool.cc 的数据，且不区分大小写：
>db.posts.find({post_text:{$regex:"w3cschool.cc",$options:"$i"}})

正则表达式中使用变量。一定要使用eval将组合的字符串进行转换，不能直接将字符串拼接后传入给表达式。否则没有报错信息，只是结果为空！实例如下：

var name=eval("/" + 变量值key +"/i"); 


