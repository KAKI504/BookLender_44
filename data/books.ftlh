<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Библиотека - Список книг</title>
    <link rel="stylesheet" href="/css/styles.css">
</head>
<body>
    <nav class="navigation">
        <div class="container">
            <div class="nav-links">
                <#if employee??>
                    <a href="/profile">Мой профиль</a>
                <#else>
                    <a href="/login">Войти</a>
                </#if>
                <a href="/books">Книги</a>
            </div>
        </div>
    </nav>

    <div class="container">
        <h1>Библиотека</h1>

        <#if error??>
            <div class="notification notification-error">
                ${error}
            </div>
        </#if>

        <#if success??>
            <div class="notification notification-success">
                ${success}
            </div>
        </#if>

        <div class="books-grid">
            <#list books as book>
                <div class="book-card">
                    <!-- Попробуйте прямой абсолютный путь -->
                    <img src="/images/book${book.id}.jpg" alt="${book.title}" class="book-image">
                    <div class="book-info">
                        <h3 class="book-title">
                            <a href="/book/${book.id}">${book.title}</a>
                        </h3>
                        <p class="book-author">${book.author}</p>
                        <span class="book-status status-${book.status}">
                            ${book.status?capitalize}
                        </span>
                        <#if employee?? && !book.borrowed>
                            <a href="/borrow-book/${book.id}" class="button">Взять книгу</a>
                        </#if>
                    </div>
                </div>
            </#list>
        </div>
    </div>
</body>
</html>