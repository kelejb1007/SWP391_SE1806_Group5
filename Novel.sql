create database Novel_Application
use Novel_Application

CREATE TABLE Account(
    accountID INT IDENTITY(1,1) PRIMARY KEY,
    username NVARCHAR(50) UNIQUE NOT NULL,
    password NVARCHAR(255) NOT NULL,
    creationDate DATETIME2 DEFAULT GETDATE(),
    isAdmin BIT DEFAULT 0,
    imageUML NVARCHAR(255),
    fullName NVARCHAR(100),
    email NVARCHAR(100) UNIQUE NOT NULL,
    numberPhone NVARCHAR(15),
    dateOfBirth DATE,
    gender NVARCHAR(10) CHECK (gender IN ('Male', 'Female', 'Other')),
    isBanned BIT DEFAULT 0,
    banReason TEXT,
    banDate DATETIME,
    lastLogin DATETIME
);

CREATE TABLE Genre(
    genreID INT IDENTITY(1,1) PRIMARY KEY,
    genreName NVARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE Novel(
    novelID INT IDENTITY(1,1) PRIMARY KEY,
    novelName NVARCHAR(255) NOT NULL,
    author INT NOT NULL,
    imageURL NVARCHAR(255),
    novelDescription TEXT,
    totalChapter INT DEFAULT 0,
    publishedDate DATETIME2 DEFAULT GETDATE(),
    novelStatus NVARCHAR(10) DEFAULT 'pending' CHECK (novelStatus IN ('pending', 'approved', 'rejected')),
    averageRating FLOAT,
    isLocked BIT DEFAULT 0,
    FOREIGN KEY (author) REFERENCES Account(accountID)
);

CREATE TABLE Genre_Novel(
    genreID INT NOT NULL,
    novelID INT NOT NULL,
    PRIMARY KEY (genreID, novelID),
    FOREIGN KEY (genreID) REFERENCES Genre(genreID),
    FOREIGN KEY (novelID) REFERENCES Novel(novelID)
);

CREATE TABLE Chapter(
    chapterID INT IDENTITY(1,1) PRIMARY KEY,
    novelID INT NOT NULL,
    chapterNumber INT NOT NULL,
    chapterName NVARCHAR(255),
    fileURL NVARCHAR(200),
    chapterCreatedDate DATETIME2 DEFAULT GETDATE(),
    isLocked BIT DEFAULT 0,
    FOREIGN KEY (novelID) REFERENCES Novel(novelID)
);

CREATE TABLE Comment(
    commentID INT IDENTITY(1,1) PRIMARY KEY,
    accountID INT NOT NULL,
    novelID INT NOT NULL,
    commentContent TEXT NOT NULL,
    commentDate DATETIME2 DEFAULT GETDATE(),
    parentCmtID INT NULL,
    FOREIGN KEY (accountID) REFERENCES Account(accountID),
    FOREIGN KEY (novelID) REFERENCES Novel(novelID)
);

CREATE TABLE Rate(
    ratingID INT IDENTITY(1,1) PRIMARY KEY,
    accountID INT NOT NULL,
    novelID INT NOT NULL,
    score INT CHECK(score BETWEEN 1 AND 5),
    ratingDate DATETIME2 DEFAULT GETDATE(),
    FOREIGN KEY (accountID) REFERENCES Account(accountID),
    FOREIGN KEY (novelID) REFERENCES Novel(novelID)
);

CREATE TABLE Notification(
    notificationID INT IDENTITY(1,1) PRIMARY KEY,
    accountID INT NOT NULL,
    noticeName NVARCHAR(15) NOT NULL,
    typeNoti NVARCHAR(15) CHECK (typeNoti IN ('new_chapter', 'comment', 'tag', 'report')),
    noticeContent TEXT NOT NULL,
    novelID INT NULL,
    commentID INT NULL,
    createdDate DATETIME2 DEFAULT GETDATE(),
    isRead BIT DEFAULT 0,
    FOREIGN KEY (accountID) REFERENCES Account(accountID),
    FOREIGN KEY (novelID) REFERENCES Novel(novelID),
    FOREIGN KEY (commentID) REFERENCES Comment(commentID)
);

CREATE TABLE ReadingHistory(
    readingID INT IDENTITY(1,1) PRIMARY KEY,
    accountID INT NOT NULL,
    novelID INT NOT NULL,
    chapterID INT NULL,
    lastReadDate DATETIME2 DEFAULT GETDATE(),
    FOREIGN KEY (accountID) REFERENCES Account(accountID),
    FOREIGN KEY (novelID) REFERENCES Novel(novelID),
    FOREIGN KEY (chapterID) REFERENCES Chapter(chapterID)
);

CREATE TABLE Favorite(
    favoriteID INT IDENTITY(1,1) PRIMARY KEY,
    accountID INT NOT NULL,
    novelID INT NOT NULL,
    isFavorite BIT DEFAULT 0,
    FOREIGN KEY (accountID) REFERENCES Account(accountID),
    FOREIGN KEY (novelID) REFERENCES Novel(novelID)
);

CREATE TABLE views(
    viewID INT IDENTITY(1,1) PRIMARY KEY,
    accountID INT NULL,
    novelID INT NOT NULL,
    viewDate DATETIME2 DEFAULT GETDATE(),
    FOREIGN KEY (accountID) REFERENCES Account(accountID),
    FOREIGN KEY (novelID) REFERENCES Novel(novelID)
);






