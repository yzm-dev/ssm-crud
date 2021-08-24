/*

 Source Server         : Yu-God
 Source Server Type    : MySQL
 Source Server Version : 80023
 Source Host           : localhost:3306
 Source Schema         : ssm_crud

 Target Server Type    : MySQL
 Target Server Version : 80023
 File Encoding         : 65001

 Date: 24/08/2021 11:04:31
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for emp_dept
-- ----------------------------
DROP TABLE IF EXISTS `emp_dept`;
CREATE TABLE `emp_dept`  (
  `dep_id` int(0) NOT NULL,
  `department` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`dep_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of emp_dept
-- ----------------------------
INSERT INTO `emp_dept` VALUES (1, '开发部');
INSERT INTO `emp_dept` VALUES (2, '测试部');
INSERT INTO `emp_dept` VALUES (3, '运维部');
INSERT INTO `emp_dept` VALUES (4, '实施部');

SET FOREIGN_KEY_CHECKS = 1;
