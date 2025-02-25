<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${book.title} - Библиотека</title>
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
        <div class="books-section">
            <h1>${book.title}</h1>
            <div class="book-details">
                <div class="book-cover">
                    <!-- Попробуйте прямой абсолютный путь -->
                    <img src="/images/book${book.id}.jpg" alt="${book.title}" style="max-width: 300px;">
                </div>
                <div class="book-info">
                    <p><strong>Автор:</strong> ${book.author}</p>
                    <p><strong>Статус:</strong> <span class="book-status status-${book.status}">${book.status?capitalize}</span></p>
                    <#if book.borrowedBy??>
                        <p><strong>Взята:</strong> ${book.borrowedBy}</p>
                    </#if>
                    <#if book.isbn??>
                        <p><strong>ISBN:</strong> ${book.isbn}</p>
                    </#if>
                    <#if book.year??>
                        <p><strong>Год издания:</strong> ${book.year}</p>
                    </#if>
                    <#if book.genre??>
                        <p><strong>Жанр:</strong> ${book.genre}</p>
                    </#if>
                    <#if book.pageCount??>
                        <p><strong>Количество страниц:</strong> ${book.pageCount}</p>
                    </#if>
                    <#if book.description??>
                        <p><strong>Описание:</strong> ${book.description}</p>
                    </#if>

                    <div class="book-actions">
                        <a href="/books" class="button">Вернуться к списку книг</a>
                        <#if employee?? && !book.borrowed>
                            <a href="/borrow-book/${book.id}" class="button">Взять книгу</a>
                        </#if>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>