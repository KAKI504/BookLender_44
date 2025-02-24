<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Библиотека</title>
    <link rel="stylesheet" href="css/freemarker.css">
</head>
<body>
    <div class="container">
        <nav class="navigation">
            <#if employee??>
                <a href="/profile">Мой профиль</a> |
            <#else>
                <a href="/login">Войти</a> |
            </#if>
            <a href="/books">Книги</a>
        </nav>

        <h1>Библиотека</h1>
        <div class="table flex flex-col">
            <div class="header flex">
                <div class="column">ID</div>
                <div class="column">Название</div>
                <div class="column">Автор</div>
                <div class="column">Статус</div>
                <div class="column">Действия</div>
            </div>
            <#list books as book>
                <div class="row ${book?item_parity} flex">
                    <div class="column">${book.id}</div>
                    <div class="column">
                        <a href="/book/${book.id}">${book.title}</a>
                    </div>
                    <div class="column">${book.author}</div>
                    <div class="column">${book.status}</div>
                    <div class="column">
                        <#if employee?? && !book.borrowed>
                            <a href="/borrow-book/${book.id}">Взять книгу</a>
                        </#if>
                    </div>
                </div>
            </#list>
        </div>
    </div>
</body>
</html>