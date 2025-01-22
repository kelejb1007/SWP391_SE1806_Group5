create database Novel_Application
use Novel_Application

create table Account(
	accountID int primary key identity(1,1),
	userName nvarchar(50),
	password nvarchar(50),
	regisDate datetime,
	role int check (role IN (0, 1)),  --0(registeredUser) , 1(admin)
)

create table RegisteredUser(
    userID int primary key identity(1,1),
	email nvarchar(255),
	imgURL nvarchar(255),
	membershipType int check (membershipType IN (0, 1)), -- 0(thuong), 1(vip), 
	coin decimal(10,2),
	
	userFullName nvarchar(255),
	numberPhone nvarchar(16),
	dateOfBirth date,
	gender int check (gender IN (0, 1)), -- 0(nu), 1(nam), 
	accountID int foreign key references Account(accountID),
)

create table Novel (
    novelID int primary key identity(1,1), --tu dong tang dan
	title nvarchar(255),
	author nvarchar(255),
    status int check (status IN (0, 1)), --0(dang cap nhat), 1(full), chi chua 2 gia tri 0,1
	imageURL nvarchar(255),
	updateTime datetime,
	publishedDate datetime,
	userID int foreign key references RegisteredUser(userID),
)

create table Chapter (
    chapterID int primary key identity(1,1),
	chapterName nvarchar(255),
	chapNo int,
	fileURL nvarchar(255),
	updateTime datetime,
	publishedDate datetime,
	type int check (type IN (0, 1)), -- 0(thuong), 1(vip)
	novelID int foreign key references Novel(novelID),
)

create table Vip(
    vipID int primary key identity(1,1),
	regisDate datetime,
	expirationDate datetime,    --ngay het han vip
	userID int foreign key references RegisteredUser(userID),
)


create table Reading(
    userID int foreign key references RegisteredUser(userID),
	chapterID int foreign key references Chapter(chapterID),
	primary key (userID, chapterID),
)

create table Favorite(
    userID int foreign key references RegisteredUser(userID),
	novelID int foreign key references Novel(novelID),
	primary key (userID, novelID),
)

create table Viewing(
    userID int foreign key references registeredUser(userID),
	novelID int foreign key references Novel(novelID),
	viewDate datetime,
	primary key (userID, novelID),
)

create table Comment(
    commentID int primary key identity(1,1),
    userID int foreign key references RegisteredUser(userID),
	novelID int foreign key references Novel(novelID),
	content nvarchar(512),
	time datetime,
)

create table Rating(
    userID int foreign key references registeredUser(userID),
	novelID int foreign key references Novel(novelID),
	score int check (score IN (1, 2, 3, 4, 5)),
)




INSERT INTO Novels (title, author, genre, status, rate, imageURL, updateTime)
VALUES (
    N'Kì Tài Giáo Chủ',               -- Tiêu đề
    N'Phong Thất Nguyệt',             -- Tác giả
    N'Tiên Hiệp, Kiếm Hiệp, Huyền Huyễn, Dị Giới, Xuyên Không', -- Thể loại
    1,                                 -- Trạng thái (1: đang cập nhật)
    4,                                 -- Đánh giá (4 sao)
    N'view/assets/images/KiTaGiaoChu.jfif', -- URL hình ảnh
    GETDATE()                          -- Thời gian cập nhật (thời gian hiện tại)
);