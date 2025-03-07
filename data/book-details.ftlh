<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${book.title} - Библиотека</title>
    <link rel="stylesheet" href="/css/styles.css">
    <style>

        .enhanced-book-details {
            display: flex;
            flex-direction: column;
            align-items: center;
            gap: 30px;
            width: 100%;
            background-color: white;
            border-radius: 15px;
            padding: 40px;
            box-shadow: 0 10px 30px rgba(0, 0, 0, 0.15);
            margin: 30px 0;
        }

        @media (min-width: 768px) {
            .enhanced-book-details {
                flex-direction: row;
                align-items: flex-start;
            }
        }

        .book-cover-container {
            flex-shrink: 0;
            margin-bottom: 20px;
        }

        .book-cover-container img {
            max-width: 300px;
            border-radius: 10px;
            box-shadow: 0 15px 30px rgba(0, 0, 0, 0.3);
            transition: transform 0.3s ease;
        }

        .book-cover-container img:hover {
            transform: scale(1.03);
        }

        .book-info-container {
            flex-grow: 1;
            width: 100%;
            text-align: left;
        }

        .book-title {
            font-size: 32px;
            margin-bottom: 10px;
            color: #333;
            border-bottom: 2px solid #ffca3a;
            padding-bottom: 10px;
        }

        .book-author {
            font-size: 22px;
            color: #ff8c38;
            margin-bottom: 20px;
        }

        .book-metadata {
            display: grid;
            grid-template-columns: 1fr;
            gap: 15px;
            margin-bottom: 25px;
            padding: 20px;
            background-color: #f9f9f9;
            border-radius: 10px;
        }

        @media (min-width: 768px) {
            .book-metadata {
                grid-template-columns: 1fr 1fr;
            }
        }

        .metadata-item {
            margin: 0;
        }

        .metadata-label {
            font-weight: bold;
            margin-right: 10px;
            color: #555;
        }

        .book-status-enhanced {
            display: inline-block;
            padding: 8px 15px;
            border-radius: 30px;
            font-size: 16px;
            font-weight: bold;
            margin: 10px 0;
        }

        .status-available {
            background-color: rgba(46, 204, 113, 0.2);
            color: #27ae60;
            border: 1px solid #27ae60;
        }

        .status-borrowed {
            background-color: rgba(231, 76, 60, 0.2);
            color: #c0392b;
            border: 1px solid #c0392b;
        }

        .book-description {
            line-height: 1.7;
            font-size: 18px;
            margin-top: 20px;
            padding: 20px;
            background-color: #f9f9f9;
            border-radius: 10px;
            border-left: 4px solid #ffca3a;
        }

        .book-actions {
            display: flex;
            flex-wrap: wrap;
            justify-content: center;
            gap: 15px;
            margin-top: 30px;
        }

        .book-actions .button {
            min-width: 150px;
            font-size: 16px;
            padding: 12px 25px;
            text-align: center;
        }

        .borrow-button {
            background-color: #2ecc71;
            color: white !important;
        }

        .borrow-button:hover {
            background-color: #27ae60;
        }

        .return-button {
            background-color: #e74c3c;
            color: white !important;
        }
    </style>
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
                <a href="/employees">Сотрудники</a>
            </div>
        </div>
    </nav>

    <div class="container">
        <div class="books-section">
            <h1>${book.title}</h1>

            <div class="enhanced-book-details">
                <div class="book-cover-container">
                    <img src="/images/book${book.id}.jpg"
                         alt="${book.title}"
                         onerror="this.onerror=null; this.src='/images/book${book.id}.jpeg';">
                </div>

                <div class="book-info-container">
                    <h2 class="book-title">${book.title}</h2>
                    <p class="book-author">Автор: ${book.author}</p>

                    <div class="book-metadata">
                        <p class="metadata-item">
                            <span class="metadata-label">Статус:</span>
                            <span class="book-status-enhanced status-${book.status}">
                                ${book.status?capitalize}
                            </span>
                        </p>

                        <#if book.borrowedBy??>
                            <p class="metadata-item">
                                <span class="metadata-label">Взята:</span>
                                ${book.borrowedBy}
                            </p>
                        </#if>



                        <#if book.year??>
                            <p class="metadata-item">
                                <span class="metadata-label">Год издания:</span>
                                ${book.year}
                            </p>
                        </#if>

                        <#if book.genre??>
                            <p class="metadata-item">
                                <span class="metadata-label">Жанр:</span>
                                ${book.genre}
                            </p>
                        </#if>

                        <#if book.pageCount??>
                            <p class="metadata-item">
                                <span class="metadata-label">Страниц:</span>
                                ${book.pageCount}
                            </p>
                        </#if>
                    </div>

                    <#if book.description??>
                        <div class="book-description">
                            <span class="metadata-label">Описание:</span><br>
                            ${book.description}
                        </div>
                    </#if>

                    <div class="book-actions">
                        <a href="/books" class="button">Вернуться к списку книг</a>
                        <#if employee?? && !book.borrowed>
                            <a href="/borrow-book/${book.id}" class="button borrow-button">Взять книгу</a>
                        </#if>
                        <#if employee?? && book.borrowed && book.borrowedBy == employee.name>
                            <a href="/return-book/${book.id}" class="button return-button">Вернуть книгу</a>
                        </#if>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>