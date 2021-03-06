USE [Assignment3_NguyenPhiHung]
GO
ALTER TABLE [dbo].[User] DROP CONSTRAINT [FK__User__UserStatus__4CA06362]
GO
ALTER TABLE [dbo].[User] DROP CONSTRAINT [FK__User__UserRoleID__4AB81AF0]
GO
ALTER TABLE [dbo].[Order] DROP CONSTRAINT [FK__Order__OrderStat__251C81ED]
GO
ALTER TABLE [dbo].[Order] DROP CONSTRAINT [FK__Order__Email__2334397B]
GO
ALTER TABLE [dbo].[Order] DROP CONSTRAINT [FK__Order__DiscountC__2704CA5F]
GO
ALTER TABLE [dbo].[Discount] DROP CONSTRAINT [FK__Discount__Discou__1E6F845E]
GO
ALTER TABLE [dbo].[Car] DROP CONSTRAINT [FK__Car__CarCategory__208CD6FA]
GO
ALTER TABLE [dbo].[User] DROP CONSTRAINT [DF__User__UserStatus__4D94879B]
GO
ALTER TABLE [dbo].[User] DROP CONSTRAINT [DF__User__UserRoleID__4BAC3F29]
GO
ALTER TABLE [dbo].[User] DROP CONSTRAINT [DF__User__CreatedDat__49C3F6B7]
GO
ALTER TABLE [dbo].[Order] DROP CONSTRAINT [DF__Order__OrderStat__2610A626]
GO
ALTER TABLE [dbo].[Order] DROP CONSTRAINT [DF__Order__OrderDate__24285DB4]
GO
ALTER TABLE [dbo].[Order] DROP CONSTRAINT [DF__Order__OrderID__22401542]
GO
ALTER TABLE [dbo].[Feedback] DROP CONSTRAINT [DF__Feedback__Create__54CB950F]
GO
ALTER TABLE [dbo].[Discount] DROP CONSTRAINT [DF__Discount__Discou__1F63A897]
GO
ALTER TABLE [dbo].[Car] DROP CONSTRAINT [DF__Car__CreatedDate__2180FB33]
GO
ALTER TABLE [dbo].[Car] DROP CONSTRAINT [DF__Car__CarID__1F98B2C1]
GO
/****** Object:  Table [dbo].[UserStatus]    Script Date: 2021-03-07 10:00:58 PM ******/
DROP TABLE [dbo].[UserStatus]
GO
/****** Object:  Table [dbo].[UserRole]    Script Date: 2021-03-07 10:00:58 PM ******/
DROP TABLE [dbo].[UserRole]
GO
/****** Object:  Table [dbo].[User]    Script Date: 2021-03-07 10:00:58 PM ******/
DROP TABLE [dbo].[User]
GO
/****** Object:  Table [dbo].[OrderStatus]    Script Date: 2021-03-07 10:00:58 PM ******/
DROP TABLE [dbo].[OrderStatus]
GO
/****** Object:  Table [dbo].[OrderDetail]    Script Date: 2021-03-07 10:00:58 PM ******/
DROP TABLE [dbo].[OrderDetail]
GO
/****** Object:  Table [dbo].[Order]    Script Date: 2021-03-07 10:00:58 PM ******/
DROP TABLE [dbo].[Order]
GO
/****** Object:  Table [dbo].[Feedback]    Script Date: 2021-03-07 10:00:58 PM ******/
DROP TABLE [dbo].[Feedback]
GO
/****** Object:  Table [dbo].[DiscountStatus]    Script Date: 2021-03-07 10:00:58 PM ******/
DROP TABLE [dbo].[DiscountStatus]
GO
/****** Object:  Table [dbo].[Discount]    Script Date: 2021-03-07 10:00:58 PM ******/
DROP TABLE [dbo].[Discount]
GO
/****** Object:  Table [dbo].[CarCategory]    Script Date: 2021-03-07 10:00:58 PM ******/
DROP TABLE [dbo].[CarCategory]
GO
/****** Object:  Table [dbo].[Car]    Script Date: 2021-03-07 10:00:58 PM ******/
DROP TABLE [dbo].[Car]
GO
/****** Object:  UserDefinedFunction [dbo].[fnGetOrderID]    Script Date: 2021-03-07 10:00:58 PM ******/
DROP FUNCTION [dbo].[fnGetOrderID]
GO
/****** Object:  UserDefinedFunction [dbo].[fnGetCarID]    Script Date: 2021-03-07 10:00:58 PM ******/
DROP FUNCTION [dbo].[fnGetCarID]
GO
/****** Object:  UserDefinedFunction [dbo].[fnGetCarID]    Script Date: 2021-03-07 10:00:58 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create function [dbo].[fnGetCarID]()
returns varchar(8)
as
begin
	declare @ID varchar(8)
	if(select count(CarID) from Car) =0
		set @ID = '0'
	else
		select @ID = max (right(CarID, 5)) from Car
		select @ID = case
			when @ID >= 0 and @ID < 9 then 'Car0000' + convert(char, convert(int, @ID) + 1)
			when @ID >= 9 and @ID < 99 then 'Car000' + convert(char, convert(int, @ID) + 1)
			when @ID >= 99 and @ID < 999 then 'Car00' + convert(char, convert(int, @ID) + 1)
			when @ID >= 999 and @ID < 9999 then 'Car0' + convert(char, convert(int, @ID) + 1)
			when @ID >= 9999 and @ID < 99999 then 'Car' + convert(char, convert(int, @ID) + 1)
		end
	return @ID
end
GO
/****** Object:  UserDefinedFunction [dbo].[fnGetOrderID]    Script Date: 2021-03-07 10:00:58 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE function [dbo].[fnGetOrderID]()
returns varchar(8)
as
begin
	declare @ID varchar(8)
	if(select count(OrderID) from [Order]) =0
		set @ID = '0'
	else
		select @ID = max (right(OrderID, 5)) from [Order]
		select @ID = case
			when @ID >= 0 and @ID < 9 then 'OR00000' + convert(char, convert(int, @ID) + 1)
			when @ID >= 9 and @ID < 99 then 'OR0000' + convert(char, convert(int, @ID) + 1)
			when @ID >= 99 and @ID < 999 then 'OR000' + convert(char, convert(int, @ID) + 1)
			when @ID >= 999 and @ID < 9999 then 'OR00' + convert(char, convert(int, @ID) + 1)
			when @ID >= 9999 and @ID < 99999 then 'OR0' + convert(char, convert(int, @ID) + 1)
		end
	return @ID
end
GO
/****** Object:  Table [dbo].[Car]    Script Date: 2021-03-07 10:00:58 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Car](
	[CarID] [varchar](8) NOT NULL,
	[CarName] [nvarchar](500) NOT NULL,
	[Color] [varchar](50) NOT NULL,
	[YearOfManufacture] [varchar](50) NOT NULL,
	[Price] [real] NOT NULL,
	[UnitPrice] [varchar](50) NOT NULL,
	[Quantity] [int] NOT NULL,
	[CarCategoryID] [varchar](10) NOT NULL,
	[CreatedDate] [datetime] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[CarID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[CarCategory]    Script Date: 2021-03-07 10:00:58 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[CarCategory](
	[CarCategoryID] [varchar](10) NOT NULL,
	[CarCategoryName] [varchar](50) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[CarCategoryID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Discount]    Script Date: 2021-03-07 10:00:58 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Discount](
	[DiscountCode] [varchar](50) NOT NULL,
	[DiscountValue] [int] NOT NULL,
	[ExpiredDate] [date] NOT NULL,
	[DiscountStatusID] [varchar](8) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[DiscountCode] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[DiscountStatus]    Script Date: 2021-03-07 10:00:58 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[DiscountStatus](
	[DiscountStatusID] [varchar](8) NOT NULL,
	[DiscountStatusName] [varchar](50) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[DiscountStatusID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Feedback]    Script Date: 2021-03-07 10:00:58 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Feedback](
	[Email] [varchar](100) NOT NULL,
	[CarID] [varchar](8) NOT NULL,
	[Rating] [int] NULL,
	[CreatedDate] [date] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[Email] ASC,
	[CarID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Order]    Script Date: 2021-03-07 10:00:58 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Order](
	[OrderID] [varchar](8) NOT NULL,
	[Email] [varchar](100) NOT NULL,
	[TotalPrice] [float] NOT NULL,
	[OrderDate] [date] NOT NULL,
	[OrderStatusID] [varchar](20) NULL,
	[DiscountCode] [varchar](50) NULL,
PRIMARY KEY CLUSTERED 
(
	[OrderID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[OrderDetail]    Script Date: 2021-03-07 10:00:58 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[OrderDetail](
	[OrderID] [varchar](8) NOT NULL,
	[CarID] [varchar](8) NOT NULL,
	[RentalDate] [date] NOT NULL,
	[ReturnDate] [date] NOT NULL,
	[Amount] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[OrderID] ASC,
	[CarID] ASC,
	[RentalDate] ASC,
	[ReturnDate] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[OrderStatus]    Script Date: 2021-03-07 10:00:58 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[OrderStatus](
	[OrderStatusID] [varchar](20) NOT NULL,
	[OrderStatusName] [varchar](50) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[OrderStatusID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[User]    Script Date: 2021-03-07 10:00:58 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[User](
	[Email] [varchar](100) NOT NULL,
	[Password] [varchar](100) NOT NULL,
	[Name] [nvarchar](200) NOT NULL,
	[Phone] [varchar](50) NOT NULL,
	[Address] [nvarchar](500) NOT NULL,
	[CreatedDate] [date] NOT NULL,
	[UserRoleID] [varchar](50) NOT NULL,
	[UserStatusID] [varchar](50) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[Email] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[UserRole]    Script Date: 2021-03-07 10:00:58 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[UserRole](
	[UserRoleID] [varchar](50) NOT NULL,
	[UserRoleName] [varchar](50) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[UserRoleID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[UserStatus]    Script Date: 2021-03-07 10:00:58 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[UserStatus](
	[UserStatusID] [varchar](50) NOT NULL,
	[UserStatusName] [varchar](50) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[UserStatusID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
INSERT [dbo].[Car] ([CarID], [CarName], [Color], [YearOfManufacture], [Price], [UnitPrice], [Quantity], [CarCategoryID], [CreatedDate]) VALUES (N'Car00001', N'SUZUKI CELERIO 2020', N'white', N'2020', 900000, N'day', 30, N'CC0', CAST(N'2021-02-23T15:31:19.223' AS DateTime))
INSERT [dbo].[Car] ([CarID], [CarName], [Color], [YearOfManufacture], [Price], [UnitPrice], [Quantity], [CarCategoryID], [CreatedDate]) VALUES (N'Car00002', N'CHEVROLET SPARK 2012', N'red', N'2012', 910000, N'day', 30, N'CC0', CAST(N'2021-02-23T15:31:19.227' AS DateTime))
INSERT [dbo].[Car] ([CarID], [CarName], [Color], [YearOfManufacture], [Price], [UnitPrice], [Quantity], [CarCategoryID], [CreatedDate]) VALUES (N'Car00003', N'CHEVROLET SPARK 2017', N'navy', N'2017', 920000, N'day', 30, N'CC0', CAST(N'2021-02-23T15:31:19.230' AS DateTime))
INSERT [dbo].[Car] ([CarID], [CarName], [Color], [YearOfManufacture], [Price], [UnitPrice], [Quantity], [CarCategoryID], [CreatedDate]) VALUES (N'Car00004', N'CHEVROLET SPARK 2018 RED', N'red', N'2018', 930000, N'day', 30, N'CC0', CAST(N'2021-02-23T15:31:19.230' AS DateTime))
INSERT [dbo].[Car] ([CarID], [CarName], [Color], [YearOfManufacture], [Price], [UnitPrice], [Quantity], [CarCategoryID], [CreatedDate]) VALUES (N'Car00005', N'CHEVROLET SPARK 2018 WHITE', N'white', N'2018', 940000, N'day', 30, N'CC0', CAST(N'2021-02-23T15:31:19.230' AS DateTime))
INSERT [dbo].[Car] ([CarID], [CarName], [Color], [YearOfManufacture], [Price], [UnitPrice], [Quantity], [CarCategoryID], [CreatedDate]) VALUES (N'Car00006', N'CHEVROLET SPARK 2018 PALE BLUE', N'pale blue', N'2018', 950000, N'day', 30, N'CC0', CAST(N'2021-02-23T15:31:19.230' AS DateTime))
INSERT [dbo].[Car] ([CarID], [CarName], [Color], [YearOfManufacture], [Price], [UnitPrice], [Quantity], [CarCategoryID], [CreatedDate]) VALUES (N'Car00007', N'KIA MORNING 2016', N'white', N'2016', 960000, N'day', 30, N'CC0', CAST(N'2021-02-23T15:31:19.230' AS DateTime))
INSERT [dbo].[Car] ([CarID], [CarName], [Color], [YearOfManufacture], [Price], [UnitPrice], [Quantity], [CarCategoryID], [CreatedDate]) VALUES (N'Car00008', N'KIA MORNING SI 2020', N'red', N'2020', 970000, N'day', 30, N'CC0', CAST(N'2021-02-23T15:31:19.230' AS DateTime))
INSERT [dbo].[Car] ([CarID], [CarName], [Color], [YearOfManufacture], [Price], [UnitPrice], [Quantity], [CarCategoryID], [CreatedDate]) VALUES (N'Car00009', N'VINFAST FADIL 2019', N'red', N'2019', 980000, N'day', 30, N'CC0', CAST(N'2021-02-23T15:31:19.233' AS DateTime))
INSERT [dbo].[Car] ([CarID], [CarName], [Color], [YearOfManufacture], [Price], [UnitPrice], [Quantity], [CarCategoryID], [CreatedDate]) VALUES (N'Car00010', N'VINFAST FADIL 2020 RED', N'red', N'2020', 990000, N'day', 30, N'CC0', CAST(N'2021-02-23T15:31:19.233' AS DateTime))
INSERT [dbo].[Car] ([CarID], [CarName], [Color], [YearOfManufacture], [Price], [UnitPrice], [Quantity], [CarCategoryID], [CreatedDate]) VALUES (N'Car00011', N'VINFAST FADIL 2020 NAVY', N'navy', N'2020', 890000, N'day', 30, N'CC0', CAST(N'2021-02-23T15:31:19.233' AS DateTime))
INSERT [dbo].[Car] ([CarID], [CarName], [Color], [YearOfManufacture], [Price], [UnitPrice], [Quantity], [CarCategoryID], [CreatedDate]) VALUES (N'Car00012', N'HYUNDAI GRAND I10 2015', N'white', N'2015', 880000, N'day', 30, N'CC0', CAST(N'2021-02-23T15:31:19.233' AS DateTime))
INSERT [dbo].[Car] ([CarID], [CarName], [Color], [YearOfManufacture], [Price], [UnitPrice], [Quantity], [CarCategoryID], [CreatedDate]) VALUES (N'Car00013', N'HYUNDAI GRAND I10 2016', N'brown', N'2016', 870000, N'day', 30, N'CC0', CAST(N'2021-02-23T15:31:19.233' AS DateTime))
INSERT [dbo].[Car] ([CarID], [CarName], [Color], [YearOfManufacture], [Price], [UnitPrice], [Quantity], [CarCategoryID], [CreatedDate]) VALUES (N'Car00014', N'HYUNDAI GRAND I10 2017', N'white', N'2017', 860000, N'day', 30, N'CC0', CAST(N'2021-02-23T15:31:19.237' AS DateTime))
INSERT [dbo].[Car] ([CarID], [CarName], [Color], [YearOfManufacture], [Price], [UnitPrice], [Quantity], [CarCategoryID], [CreatedDate]) VALUES (N'Car00015', N'HYUNDAI GRAND I10 2020', N'white', N'2020', 850000, N'day', 30, N'CC0', CAST(N'2021-02-23T15:31:19.237' AS DateTime))
INSERT [dbo].[Car] ([CarID], [CarName], [Color], [YearOfManufacture], [Price], [UnitPrice], [Quantity], [CarCategoryID], [CreatedDate]) VALUES (N'Car00016', N'MITSUBISHI OUTLANDER 2018', N'gray', N'2018', 840000, N'day', 30, N'CC1', CAST(N'2021-02-23T15:31:19.237' AS DateTime))
INSERT [dbo].[Car] ([CarID], [CarName], [Color], [YearOfManufacture], [Price], [UnitPrice], [Quantity], [CarCategoryID], [CreatedDate]) VALUES (N'Car00017', N'MITSUBISHI XPANDER 2019', N'white', N'2019', 830000, N'day', 30, N'CC1', CAST(N'2021-02-23T15:31:19.240' AS DateTime))
INSERT [dbo].[Car] ([CarID], [CarName], [Color], [YearOfManufacture], [Price], [UnitPrice], [Quantity], [CarCategoryID], [CreatedDate]) VALUES (N'Car00018', N'MITSUBISHI XPANDER 2020', N'black', N'2020', 820000, N'day', 30, N'CC1', CAST(N'2021-02-23T15:31:19.240' AS DateTime))
INSERT [dbo].[Car] ([CarID], [CarName], [Color], [YearOfManufacture], [Price], [UnitPrice], [Quantity], [CarCategoryID], [CreatedDate]) VALUES (N'Car00019', N'MITSUBISHI PAJERO SPORT 2014', N'black', N'2014', 810000, N'day', 30, N'CC1', CAST(N'2021-02-23T15:31:19.240' AS DateTime))
INSERT [dbo].[Car] ([CarID], [CarName], [Color], [YearOfManufacture], [Price], [UnitPrice], [Quantity], [CarCategoryID], [CreatedDate]) VALUES (N'Car00020', N'CHEVROLET TRAILBLAZER 2018', N'black', N'2018', 800000, N'day', 30, N'CC1', CAST(N'2021-02-23T15:31:19.240' AS DateTime))
INSERT [dbo].[Car] ([CarID], [CarName], [Color], [YearOfManufacture], [Price], [UnitPrice], [Quantity], [CarCategoryID], [CreatedDate]) VALUES (N'Car00021', N'CHEVROLET TRAILBLAZER 2019', N'red', N'2019', 790000, N'day', 30, N'CC1', CAST(N'2021-02-23T15:31:19.243' AS DateTime))
INSERT [dbo].[Car] ([CarID], [CarName], [Color], [YearOfManufacture], [Price], [UnitPrice], [Quantity], [CarCategoryID], [CreatedDate]) VALUES (N'Car00022', N'TOYOTA FORTUNER G 2013', N'gray', N'2013', 780000, N'day', 30, N'CC1', CAST(N'2021-02-23T15:31:19.243' AS DateTime))
INSERT [dbo].[Car] ([CarID], [CarName], [Color], [YearOfManufacture], [Price], [UnitPrice], [Quantity], [CarCategoryID], [CreatedDate]) VALUES (N'Car00023', N'TOYOTA FORTUNER G 2016', N'gray', N'2016', 770000, N'day', 30, N'CC1', CAST(N'2021-02-23T15:31:19.243' AS DateTime))
INSERT [dbo].[Car] ([CarID], [CarName], [Color], [YearOfManufacture], [Price], [UnitPrice], [Quantity], [CarCategoryID], [CreatedDate]) VALUES (N'Car00024', N'TOYOTA FORTUNER 2019', N'white', N'2019', 760000, N'day', 30, N'CC1', CAST(N'2021-02-23T15:31:19.243' AS DateTime))
INSERT [dbo].[Car] ([CarID], [CarName], [Color], [YearOfManufacture], [Price], [UnitPrice], [Quantity], [CarCategoryID], [CreatedDate]) VALUES (N'Car00025', N'TOYOTA FORTUNER 2020', N'white', N'2020', 750000, N'day', 30, N'CC1', CAST(N'2021-02-23T15:31:19.243' AS DateTime))
INSERT [dbo].[Car] ([CarID], [CarName], [Color], [YearOfManufacture], [Price], [UnitPrice], [Quantity], [CarCategoryID], [CreatedDate]) VALUES (N'Car00026', N'TOYOTA RUSH 2020', N'brown', N'2020', 740000, N'day', 30, N'CC1', CAST(N'2021-02-23T15:31:19.247' AS DateTime))
INSERT [dbo].[Car] ([CarID], [CarName], [Color], [YearOfManufacture], [Price], [UnitPrice], [Quantity], [CarCategoryID], [CreatedDate]) VALUES (N'Car00027', N'SUZUKI XL7 2020', N'white', N'2020', 730000, N'day', 30, N'CC1', CAST(N'2021-02-23T15:31:19.247' AS DateTime))
INSERT [dbo].[Car] ([CarID], [CarName], [Color], [YearOfManufacture], [Price], [UnitPrice], [Quantity], [CarCategoryID], [CreatedDate]) VALUES (N'Car00028', N'HYUNDAI SANTA FE 2020', N'white', N'2020', 720000, N'day', 30, N'CC1', CAST(N'2021-02-23T15:31:19.247' AS DateTime))
INSERT [dbo].[Car] ([CarID], [CarName], [Color], [YearOfManufacture], [Price], [UnitPrice], [Quantity], [CarCategoryID], [CreatedDate]) VALUES (N'Car00029', N'KIA SORENTO 2017', N'white', N'2017', 710000, N'day', 30, N'CC1', CAST(N'2021-02-23T15:31:19.247' AS DateTime))
INSERT [dbo].[Car] ([CarID], [CarName], [Color], [YearOfManufacture], [Price], [UnitPrice], [Quantity], [CarCategoryID], [CreatedDate]) VALUES (N'Car00030', N'VINFAST LUX SA 2.0 2020', N'red', N'2020', 704000, N'day', 30, N'CC1', CAST(N'2021-02-23T15:31:19.250' AS DateTime))
INSERT [dbo].[Car] ([CarID], [CarName], [Color], [YearOfManufacture], [Price], [UnitPrice], [Quantity], [CarCategoryID], [CreatedDate]) VALUES (N'Car00031', N'NISSAN NAVARA XE 2013', N'gray', N'2020', 690000, N'day', 30, N'CC2', CAST(N'2021-02-23T15:31:19.250' AS DateTime))
INSERT [dbo].[Car] ([CarID], [CarName], [Color], [YearOfManufacture], [Price], [UnitPrice], [Quantity], [CarCategoryID], [CreatedDate]) VALUES (N'Car00032', N'NISSAN NAVARA EL 2017', N'black', N'2017', 680000, N'day', 30, N'CC2', CAST(N'2021-02-23T15:31:19.250' AS DateTime))
INSERT [dbo].[Car] ([CarID], [CarName], [Color], [YearOfManufacture], [Price], [UnitPrice], [Quantity], [CarCategoryID], [CreatedDate]) VALUES (N'Car00033', N'FORD RANGER WILDTRAK 2017', N'orange', N'2017', 670000, N'day', 30, N'CC2', CAST(N'2021-02-23T15:31:19.250' AS DateTime))
INSERT [dbo].[Car] ([CarID], [CarName], [Color], [YearOfManufacture], [Price], [UnitPrice], [Quantity], [CarCategoryID], [CreatedDate]) VALUES (N'Car00034', N'FORD RANGER XLS 2018', N'gray', N'2018', 660000, N'day', 30, N'CC2', CAST(N'2021-02-23T15:31:19.250' AS DateTime))
INSERT [dbo].[Car] ([CarID], [CarName], [Color], [YearOfManufacture], [Price], [UnitPrice], [Quantity], [CarCategoryID], [CreatedDate]) VALUES (N'Car00035', N'FORD RANGER XLS 2020', N'black', N'2020', 650000, N'day', 30, N'CC2', CAST(N'2021-02-23T15:31:19.250' AS DateTime))
INSERT [dbo].[Car] ([CarID], [CarName], [Color], [YearOfManufacture], [Price], [UnitPrice], [Quantity], [CarCategoryID], [CreatedDate]) VALUES (N'Car00036', N'ISUZU D-MAX LS PRESTIGE 2019', N'black', N'2019', 640000, N'day', 30, N'CC2', CAST(N'2021-02-23T15:31:19.253' AS DateTime))
INSERT [dbo].[Car] ([CarID], [CarName], [Color], [YearOfManufacture], [Price], [UnitPrice], [Quantity], [CarCategoryID], [CreatedDate]) VALUES (N'Car00037', N'ISUZU D-MAX LS PRESTIGE 2020', N'red', N'2020', 630000, N'day', 30, N'CC2', CAST(N'2021-02-23T15:31:19.253' AS DateTime))
INSERT [dbo].[Car] ([CarID], [CarName], [Color], [YearOfManufacture], [Price], [UnitPrice], [Quantity], [CarCategoryID], [CreatedDate]) VALUES (N'Car00038', N'CHEVROLET COLORADO LTZ 2017', N'black', N'2017', 620000, N'day', 30, N'CC2', CAST(N'2021-02-23T15:31:19.253' AS DateTime))
INSERT [dbo].[Car] ([CarID], [CarName], [Color], [YearOfManufacture], [Price], [UnitPrice], [Quantity], [CarCategoryID], [CreatedDate]) VALUES (N'Car00039', N'CHEVROLET COLORADO LTZ 2018', N'black', N'2018', 610000, N'day', 30, N'CC2', CAST(N'2021-02-23T15:31:19.253' AS DateTime))
INSERT [dbo].[Car] ([CarID], [CarName], [Color], [YearOfManufacture], [Price], [UnitPrice], [Quantity], [CarCategoryID], [CreatedDate]) VALUES (N'Car00040', N'CHEVROLET COLORADO LT 2019', N'black', N'2019', 600000, N'day', 30, N'CC2', CAST(N'2021-02-23T15:31:19.253' AS DateTime))
INSERT [dbo].[Car] ([CarID], [CarName], [Color], [YearOfManufacture], [Price], [UnitPrice], [Quantity], [CarCategoryID], [CreatedDate]) VALUES (N'Car00041', N'CHEVROLET COLORADO HIGH COUNTRY 2020', N'red', N'2020', 590000, N'day', 30, N'CC2', CAST(N'2021-02-23T15:31:19.257' AS DateTime))
INSERT [dbo].[Car] ([CarID], [CarName], [Color], [YearOfManufacture], [Price], [UnitPrice], [Quantity], [CarCategoryID], [CreatedDate]) VALUES (N'Car00042', N'MITSUBISHI TRITON 2019', N'black', N'2019', 580000, N'day', 30, N'CC2', CAST(N'2021-02-23T15:31:19.257' AS DateTime))
INSERT [dbo].[CarCategory] ([CarCategoryID], [CarCategoryName]) VALUES (N'CC0', N'4-seater car')
INSERT [dbo].[CarCategory] ([CarCategoryID], [CarCategoryName]) VALUES (N'CC1', N'7-seater car')
INSERT [dbo].[CarCategory] ([CarCategoryID], [CarCategoryName]) VALUES (N'CC2', N'pickup truck')
INSERT [dbo].[Discount] ([DiscountCode], [DiscountValue], [ExpiredDate], [DiscountStatusID]) VALUES (N'DC01', 70, CAST(N'2021-03-29' AS Date), N'DS1')
INSERT [dbo].[DiscountStatus] ([DiscountStatusID], [DiscountStatusName]) VALUES (N'DS0', N'still term')
INSERT [dbo].[DiscountStatus] ([DiscountStatusID], [DiscountStatusName]) VALUES (N'DS1', N'expired')
INSERT [dbo].[Feedback] ([Email], [CarID], [Rating], [CreatedDate]) VALUES (N'phihung17k@gmail.com', N'Car00002', 2, CAST(N'2021-03-07' AS Date))
INSERT [dbo].[Feedback] ([Email], [CarID], [Rating], [CreatedDate]) VALUES (N'phihung17k@gmail.com', N'Car00007', 10, CAST(N'2021-03-07' AS Date))
INSERT [dbo].[Order] ([OrderID], [Email], [TotalPrice], [OrderDate], [OrderStatusID], [DiscountCode]) VALUES (N'OR000001', N'phihung17k@gmail.com', 1000000, CAST(N'2021-03-06' AS Date), N'OS0', NULL)
INSERT [dbo].[Order] ([OrderID], [Email], [TotalPrice], [OrderDate], [OrderStatusID], [DiscountCode]) VALUES (N'OR000002', N'phihung17k@gmail.com', 1302000, CAST(N'2021-03-07' AS Date), N'OS1', N'DC01')
INSERT [dbo].[Order] ([OrderID], [Email], [TotalPrice], [OrderDate], [OrderStatusID], [DiscountCode]) VALUES (N'OR000003', N'phihung17k@gmail.com', 1670000, CAST(N'2021-03-07' AS Date), N'OS0', NULL)
INSERT [dbo].[Order] ([OrderID], [Email], [TotalPrice], [OrderDate], [OrderStatusID], [DiscountCode]) VALUES (N'OR000004', N'phihung17k@gmail.com', 1650000, CAST(N'2021-03-07' AS Date), N'OS1', NULL)
INSERT [dbo].[OrderDetail] ([OrderID], [CarID], [RentalDate], [ReturnDate], [Amount]) VALUES (N'OR000001', N'Car00002', CAST(N'2021-03-01' AS Date), CAST(N'2021-03-02' AS Date), 31)
INSERT [dbo].[OrderDetail] ([OrderID], [CarID], [RentalDate], [ReturnDate], [Amount]) VALUES (N'OR000001', N'Car00003', CAST(N'2021-03-06' AS Date), CAST(N'2021-03-28' AS Date), 2)
INSERT [dbo].[OrderDetail] ([OrderID], [CarID], [RentalDate], [ReturnDate], [Amount]) VALUES (N'OR000001', N'Car00004', CAST(N'2021-03-06' AS Date), CAST(N'2021-03-28' AS Date), 15)
INSERT [dbo].[OrderDetail] ([OrderID], [CarID], [RentalDate], [ReturnDate], [Amount]) VALUES (N'OR000001', N'Car00004', CAST(N'2021-03-10' AS Date), CAST(N'2021-03-28' AS Date), 31)
INSERT [dbo].[OrderDetail] ([OrderID], [CarID], [RentalDate], [ReturnDate], [Amount]) VALUES (N'OR000001', N'Car00005', CAST(N'2021-03-06' AS Date), CAST(N'2021-03-28' AS Date), 31)
INSERT [dbo].[OrderDetail] ([OrderID], [CarID], [RentalDate], [ReturnDate], [Amount]) VALUES (N'OR000001', N'Car00006', CAST(N'2021-03-10' AS Date), CAST(N'2021-03-28' AS Date), 31)
INSERT [dbo].[OrderDetail] ([OrderID], [CarID], [RentalDate], [ReturnDate], [Amount]) VALUES (N'OR000001', N'Car00007', CAST(N'2021-03-02' AS Date), CAST(N'2021-03-05' AS Date), 31)
INSERT [dbo].[OrderDetail] ([OrderID], [CarID], [RentalDate], [ReturnDate], [Amount]) VALUES (N'OR000001', N'Car00007', CAST(N'2021-03-03' AS Date), CAST(N'2021-03-06' AS Date), 31)
INSERT [dbo].[OrderDetail] ([OrderID], [CarID], [RentalDate], [ReturnDate], [Amount]) VALUES (N'OR000001', N'Car00007', CAST(N'2021-03-04' AS Date), CAST(N'2021-03-05' AS Date), 31)
INSERT [dbo].[OrderDetail] ([OrderID], [CarID], [RentalDate], [ReturnDate], [Amount]) VALUES (N'OR000002', N'Car00004', CAST(N'2021-03-07' AS Date), CAST(N'2021-03-07' AS Date), 2)
INSERT [dbo].[OrderDetail] ([OrderID], [CarID], [RentalDate], [ReturnDate], [Amount]) VALUES (N'OR000003', N'Car00016', CAST(N'2021-03-07' AS Date), CAST(N'2021-03-07' AS Date), 1)
INSERT [dbo].[OrderDetail] ([OrderID], [CarID], [RentalDate], [ReturnDate], [Amount]) VALUES (N'OR000003', N'Car00017', CAST(N'2021-03-07' AS Date), CAST(N'2021-03-07' AS Date), 1)
INSERT [dbo].[OrderDetail] ([OrderID], [CarID], [RentalDate], [ReturnDate], [Amount]) VALUES (N'OR000004', N'Car00017', CAST(N'2021-03-07' AS Date), CAST(N'2021-03-07' AS Date), 1)
INSERT [dbo].[OrderDetail] ([OrderID], [CarID], [RentalDate], [ReturnDate], [Amount]) VALUES (N'OR000004', N'Car00018', CAST(N'2021-03-07' AS Date), CAST(N'2021-03-07' AS Date), 1)
INSERT [dbo].[OrderStatus] ([OrderStatusID], [OrderStatusName]) VALUES (N'OS0', N'active')
INSERT [dbo].[OrderStatus] ([OrderStatusID], [OrderStatusName]) VALUES (N'OS1', N'inactive')
INSERT [dbo].[User] ([Email], [Password], [Name], [Phone], [Address], [CreatedDate], [UserRoleID], [UserStatusID]) VALUES (N'h@gmail.com', N'1', N'1', N'1234567890', N'a', CAST(N'2021-02-20' AS Date), N'UR1', N'US0')
INSERT [dbo].[User] ([Email], [Password], [Name], [Phone], [Address], [CreatedDate], [UserRoleID], [UserStatusID]) VALUES (N'h17ktb@gmail.com', N'1', N'Nguyễn Phi Hùng', N'0365162027', N'32-34 Cách mạng tháng Tám', CAST(N'2021-03-06' AS Date), N'UR1', N'US1')
INSERT [dbo].[User] ([Email], [Password], [Name], [Phone], [Address], [CreatedDate], [UserRoleID], [UserStatusID]) VALUES (N'phihung17k@gmail.com', N'1', N'1', N'111111111111', N'1', CAST(N'2021-02-20' AS Date), N'UR1', N'US1')
INSERT [dbo].[User] ([Email], [Password], [Name], [Phone], [Address], [CreatedDate], [UserRoleID], [UserStatusID]) VALUES (N'phihungadgjmptw10b5@gmail.com', N'1', N'Nguyen Hung', N'0365162027', N'Tổ', CAST(N'2021-03-04' AS Date), N'UR1', N'US1')
INSERT [dbo].[User] ([Email], [Password], [Name], [Phone], [Address], [CreatedDate], [UserRoleID], [UserStatusID]) VALUES (N'phihungadgjmptw9a9@gmail.com', N'1', N'Nguyen Hung', N'0365162027', N'Tổ', CAST(N'2021-03-04' AS Date), N'UR1', N'US0')
INSERT [dbo].[UserRole] ([UserRoleID], [UserRoleName]) VALUES (N'UR0', N'Admin')
INSERT [dbo].[UserRole] ([UserRoleID], [UserRoleName]) VALUES (N'UR1', N'User')
INSERT [dbo].[UserStatus] ([UserStatusID], [UserStatusName]) VALUES (N'US0', N'new')
INSERT [dbo].[UserStatus] ([UserStatusID], [UserStatusName]) VALUES (N'US1', N'active')
ALTER TABLE [dbo].[Car] ADD  DEFAULT ([dbo].[fnGetCarID]()) FOR [CarID]
GO
ALTER TABLE [dbo].[Car] ADD  DEFAULT (getdate()) FOR [CreatedDate]
GO
ALTER TABLE [dbo].[Discount] ADD  DEFAULT ('DS0') FOR [DiscountStatusID]
GO
ALTER TABLE [dbo].[Feedback] ADD  DEFAULT (getdate()) FOR [CreatedDate]
GO
ALTER TABLE [dbo].[Order] ADD  DEFAULT ([dbo].[fnGetOrderID]()) FOR [OrderID]
GO
ALTER TABLE [dbo].[Order] ADD  DEFAULT (getdate()) FOR [OrderDate]
GO
ALTER TABLE [dbo].[Order] ADD  DEFAULT ('OS0') FOR [OrderStatusID]
GO
ALTER TABLE [dbo].[User] ADD  DEFAULT (getdate()) FOR [CreatedDate]
GO
ALTER TABLE [dbo].[User] ADD  DEFAULT ('UR1') FOR [UserRoleID]
GO
ALTER TABLE [dbo].[User] ADD  DEFAULT ('US0') FOR [UserStatusID]
GO
ALTER TABLE [dbo].[Car]  WITH CHECK ADD FOREIGN KEY([CarCategoryID])
REFERENCES [dbo].[CarCategory] ([CarCategoryID])
GO
ALTER TABLE [dbo].[Discount]  WITH CHECK ADD FOREIGN KEY([DiscountStatusID])
REFERENCES [dbo].[DiscountStatus] ([DiscountStatusID])
GO
ALTER TABLE [dbo].[Order]  WITH CHECK ADD FOREIGN KEY([DiscountCode])
REFERENCES [dbo].[Discount] ([DiscountCode])
GO
ALTER TABLE [dbo].[Order]  WITH CHECK ADD FOREIGN KEY([Email])
REFERENCES [dbo].[User] ([Email])
GO
ALTER TABLE [dbo].[Order]  WITH CHECK ADD FOREIGN KEY([OrderStatusID])
REFERENCES [dbo].[OrderStatus] ([OrderStatusID])
GO
ALTER TABLE [dbo].[User]  WITH CHECK ADD FOREIGN KEY([UserRoleID])
REFERENCES [dbo].[UserRole] ([UserRoleID])
GO
ALTER TABLE [dbo].[User]  WITH CHECK ADD FOREIGN KEY([UserStatusID])
REFERENCES [dbo].[UserStatus] ([UserStatusID])
GO
