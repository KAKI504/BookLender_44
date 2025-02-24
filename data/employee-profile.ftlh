<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Профиль сотрудника - ${employee.name}</title>
    <link rel="stylesheet" href="css/freemarker.css">
    <style>
        .notification {
            padding: 10px;
            margin: 10px 0;
            border-radius: 4px;
            display: none;
        }
        .success {
            background-color: #d4edda;
            color: #155724;
            border: 1px solid #c3e6cb;
        }
        .books-section {
            margin: 20px 0;
            padding: 15px;
            background: #f8f9fa;
            border-radius: 5px;
        }
        .filter-section {
            margin: 10px 0;
            padding: 10px;
            background: #e9ecef;
            border-radius: 4px;
        }
        .book-card {
            display: flex;
            align-items: center;
            padding: 10px;
            margin: 10px 0;
            background: white;
            border-radius: 4px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        .book-image {
            width: 100px;
            height: 150px;
            object-fit: cover;
            margin-right: 15px;
        }
        .book-info {
            flex-grow: 1;
        }
        .book-actions {
            margin-left: 10px;
        }
        .sort-buttons {
            margin: 10px 0;
        }
        .sort-buttons button {
            margin-right: 10px;
            padding: 5px 10px;
            border: none;
            background: #007bff;
            color: white;
            border-radius: 3px;
            cursor: pointer;
        }
        .sort-buttons button:hover {
            background: #0056b3;
        }
    </style>
</head>
<body>
    <div class="container">
        <nav class="navigation">
            <a href="/books">Книги</a> |
            <a href="/profile">Мой профиль</a>
        </nav>

        <div class="notification success" id="notification"></div>

        <h1>Профиль сотрудника</h1>
        <div class="employee-info">
            <h2>${employee.name}</h2>
            <p><strong>Email:</strong> ${employee.email}</p>
            <p><strong>Книг на руках:</strong> ${employee.currentBooksCount}/2</p>
        </div>

        <div class="books-section">
            <h2>Текущие книги</h2>
            <#if employee.currentBooks?has_content>
                <div class="book-list">
                    <#list employee.currentBooks as book>
                        <div class="book-card">
                            <img src="${book.image}" alt="${book.title}" class="book-image">
                            <div class="book-info">
                                <h3><a href="/book/${book.id}">${book.title}</a></h3>
                                <p>${book.author}</p>
                            </div>
                            <div class="book-actions">
                                <a href="/return-book/${book.id}"
                                   class="button"
                                   onclick="showNotification('Книга успешно возвращена')">Вернуть</a>
                            </div>
                        </div>
                    </#list>
                </div>
            <#else>
                <p>У вас нет книг на руках</p>
            </#if>
        </div>

        <div class="books-section">