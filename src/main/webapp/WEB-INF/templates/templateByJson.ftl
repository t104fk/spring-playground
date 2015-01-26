<!DOCTYPE html>
<meta charset="utf-8">
<title>template with model!</title>
<article>
<p>${command.name!} page!</p>
<p>username=${command.user.username!}</p>
<p>mail=${command.user.mailAddress!}</p>
<p>gender=${command.attribute.gender}(${command.attribute.gender.value})</p>
<p>blood=${command.attribute.blood}(${command.attribute.blood.value})</p>
</article>