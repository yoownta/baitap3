USE [master]
GO

IF NOT EXISTS (SELECT * FROM sys.databases WHERE name = 'SStoreDB')
BEGIN
    CREATE DATABASE [SStoreDB];
END
GO

USE [SStoreDB]
GO

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'User')
BEGIN
    CREATE TABLE [dbo].[User](
        [id] [int] IDENTITY(1,1) NOT NULL,
        [avatar] [nvarchar](500) NULL,
        [code] [nvarchar](10) NULL,
        [codeExpiry] [datetime2](7) NULL,
        [createddate] [date] NULL,
        [email] [nvarchar](150) NOT NULL,
        [fullname] [nvarchar](100) NOT NULL,
        [password] [nvarchar](255) NOT NULL,
        [phone] [nvarchar](20) NULL,
        [roleid] [int] NULL,
        [status] [int] NULL,
        [username] [nvarchar](50) NOT NULL,
        CONSTRAINT [PK_User] PRIMARY KEY CLUSTERED ([id] ASC)
    );
END
GO

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'categories')
BEGIN
    CREATE TABLE [dbo].[categories](
        [CategoryId] [int] IDENTITY(1,1) NOT NULL,
        [CategoryName] [nvarchar](255) NOT NULL,
        [Images] [nvarchar](500) NULL,
        [Status] [int] NOT NULL,
        CONSTRAINT [PK_categories] PRIMARY KEY CLUSTERED ([CategoryId] ASC)
    );
END
GO

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'products')
BEGIN
    CREATE TABLE [dbo].[products](
        [ProductId] [int] IDENTITY(1,1) NOT NULL,
        [CreateDate] [datetime2](7) NULL,
        [Description] [nvarchar](max) NULL,
        [Images] [nvarchar](500) NULL,
        [Price] [float] NOT NULL,
        [ProductName] [nvarchar](255) NOT NULL,
        [Quantity] [int] NOT NULL,
        [Status] [int] NOT NULL,
        [CategoryId] [int] NULL,
        CONSTRAINT [PK_products] PRIMARY KEY CLUSTERED ([ProductId] ASC),
        CONSTRAINT [FK_products_categories] FOREIGN KEY([CategoryId]) REFERENCES [dbo].[categories] ([CategoryId])
    );
END
GO
