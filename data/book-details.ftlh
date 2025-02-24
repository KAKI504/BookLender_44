<!DOCTYPE html>
<html>
<head>
    <title>${book.title}</title>
</head>
<body>
    <h1>${book.title}</h1>
    <p>Автор: ${book.author}</p>
    <p>Статус: ${book.status}</p>
    <#if book.borrowedBy??>
        <p>Взята: ${book.borrowedBy}</p>
    </#if>
    <img src="${book.image}" alt="${book.title}" style="max-width: 300px;">
    <br>
    <a href="/books">Вернуться к списку книг</a>
</body>
</html>