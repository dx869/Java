-- phpMyAdmin SQL Dump
-- version 3.5.2.2
-- http://www.phpmyadmin.net
--
-- 主机: 127.0.0.1
-- 生成日期: 2013 年 10 月 10 日 08:37
-- 服务器版本: 5.5.27
-- PHP 版本: 5.4.7

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- 数据库: `399a4`
--

-- --------------------------------------------------------

--
-- 表的结构 `enrolment`
--

CREATE TABLE IF NOT EXISTS `enrolment` (
  `studentid` varchar(200) NOT NULL,
  `subjectid` varchar(200) NOT NULL,
  PRIMARY KEY (`studentid`,`subjectid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- 转存表中的数据 `enrolment`
--

INSERT INTO `enrolment` (`studentid`, `subjectid`) VALUES
('', 'CSCI110'),
('de678', 'CSCI110'),
('dx569', 'CSCI110'),
('dx869', 'CSCI110'),
('er567', 'CSCI110'),
('gew456', 'CSCI110'),
('h124', 'CSCI103'),
('jsas123', 'CSCI110'),
('ml123', 'CSCI103'),
('vwe345', 'CSCI110'),
('w345', 'CSCI103'),
('we123', 'CSCI110'),
('wef123', 'CSCI103'),
('wj345', 'CSCI103');

-- --------------------------------------------------------

--
-- 表的结构 `marks`
--

CREATE TABLE IF NOT EXISTS `marks` (
  `markid` int(11) NOT NULL AUTO_INCREMENT,
  `studentid` varchar(8) NOT NULL,
  `subjectid` varchar(10) NOT NULL,
  `taskid` varchar(10) NOT NULL,
  `mark` int(2) NOT NULL,
  PRIMARY KEY (`markid`),
  KEY `subjectid` (`subjectid`),
  KEY `taskid` (`taskid`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=876 ;

--
-- 转存表中的数据 `marks`
--

INSERT INTO `marks` (`markid`, `studentid`, `subjectid`, `taskid`, `mark`) VALUES
(870, 'vwe345', 'CSCI110', ' test', 10),
(871, 'dx569', 'CSCI110', ' a1', 20),
(872, 'de678', 'CSCI103', 'ent2', 10),
(873, 'de678', 'CSCI103', 'ent2', 10),
(874, 'h124', 'CSCI103', 'a1', 10),
(875, 'wef123', 'CSCI103', 'a1', 20);

-- --------------------------------------------------------

--
-- 表的结构 `staff`
--

CREATE TABLE IF NOT EXISTS `staff` (
  `staffid` varchar(10) NOT NULL DEFAULT '',
  `fullname` varchar(50) DEFAULT NULL,
  `password` varchar(500) DEFAULT NULL,
  `role` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`staffid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- 转存表中的数据 `staff`
--

INSERT INTO `staff` (`staffid`, `fullname`, `password`, `role`) VALUES
('bp007', 'Peter John', '9e94b15ed312fa42232fd87a55db0d39', 'lecturer'),
('da005', 'Andrew Bill', 'ce08becc73195df12d99d761bfbba68d', 'lecturer'),
('gc008', 'Cole Geoff', 'a13ee062eff9d7295bfc800a11f33704', 'tutor'),
('ja004', 'Aldred Joy', '11364907cf269dd2183b64287156072a', 'tutor'),
('la006', 'Austion Luke', '568628e0d993b1973adc718237da6e93', 'tutor'),
('ma002', 'Abbott Mark', '93dd4de5cddba2c733c65f233097f05a', 'tutor'),
('ma003', 'Allan Mike', 'e88a49bccde359f0cabb40db83ba6080', 'lecturer'),
('st001', 'Tim Smith', 'dc5c7986daef50c1e02ab09b442ee34f', 'lecturer');

-- --------------------------------------------------------

--
-- 表的结构 `subject`
--

CREATE TABLE IF NOT EXISTS `subject` (
  `subjectid` varchar(10) NOT NULL DEFAULT '',
  `title` varchar(45) DEFAULT NULL,
  `content` varchar(128) DEFAULT NULL,
  `objectives` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`subjectid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- 转存表中的数据 `subject`
--

INSERT INTO `subject` (`subjectid`, `title`, `content`, `objectives`) VALUES
('CSCI103', 'Algorithms and Problem Solving', 'it is a good subject', 'On successful completion of this subject, students should be able to: 1. Create algorithms for solving simple problems 2. Determ'),
('CSCI110', 'Introduction to W3 Technology', 'a content whichfefwef', 'objectives'),
('CSCI114', 'Procedural Programming', 'CSCI114 introduces the procedural approach to program design and implementation.', 'On successful completion of this subject, students should be able to: 1.Effectively use basic C++ functionality to code simple a'),
('CSCI235', 'Databases', '1. design of relational databases 2. programming of relational databases', '(i) explain the principles of relational database model, (ii) design and implement a simple relational database, (iii) use a num');

-- --------------------------------------------------------

--
-- 表的结构 `tasks`
--

CREATE TABLE IF NOT EXISTS `tasks` (
  `taskid` varchar(10) NOT NULL DEFAULT '',
  `subjectid` varchar(10) NOT NULL DEFAULT '',
  `description` varchar(45) DEFAULT NULL,
  `mark` int(2) DEFAULT NULL,
  PRIMARY KEY (`taskid`,`subjectid`),
  KEY `task_fkey` (`subjectid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- 转存表中的数据 `tasks`
--

INSERT INTO `tasks` (`taskid`, `subjectid`, `description`, `mark`) VALUES
(' a1', 'CSCI110', ' simple', 20),
(' a3', 'CSCI103', ' ', 40),
('a1', 'CSCI103', ' ', 50),
('ent2', 'CSCI103', ' easy mark', 20);

-- --------------------------------------------------------

--
-- 表的结构 `teaching`
--

CREATE TABLE IF NOT EXISTS `teaching` (
  `staffid` varchar(10) NOT NULL DEFAULT '',
  `subjectid` varchar(10) NOT NULL DEFAULT '',
  PRIMARY KEY (`staffid`,`subjectid`),
  KEY `teaching_fkey2` (`subjectid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- 转存表中的数据 `teaching`
--

INSERT INTO `teaching` (`staffid`, `subjectid`) VALUES
('da005', 'CSCI103'),
('da005', 'CSCI110'),
('la006', 'CSCI110'),
('bp007', 'CSCI114'),
('ma002', 'CSCI114'),
('ma002', 'CSCI235');

--
-- 限制导出的表
--

--
-- 限制表 `tasks`
--
ALTER TABLE `tasks`
  ADD CONSTRAINT `task_fkey` FOREIGN KEY (`subjectid`) REFERENCES `subject` (`subjectid`) ON DELETE CASCADE;

--
-- 限制表 `teaching`
--
ALTER TABLE `teaching`
  ADD CONSTRAINT `teaching_fkey1` FOREIGN KEY (`staffid`) REFERENCES `staff` (`staffid`),
  ADD CONSTRAINT `teaching_fkey2` FOREIGN KEY (`subjectid`) REFERENCES `subject` (`subjectid`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
