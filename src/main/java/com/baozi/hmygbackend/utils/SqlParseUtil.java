package com.baozi.hmygbackend.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;

/**
 * JSON格式的数据多种多样，也很难做出一个统一模板来，只能具体情况具体分析
 * （比如有的列表字段存放不同类型的数据，列表和字段互相嵌套等）
 */
public class SqlParseUtil {

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        String tableName = "tb_goods_detail"; // 表名
        String readFile = "C:\\Users\\11493\\Desktop\\生活优购\\goodsDetail.txt"; // 读取文件路劲
        String writeFile = "C:\\Users\\11493\\Desktop\\生活优购\\goodsDetail.sql"; // 写入文件路劲
        goodsDetail(tableName, readFile, writeFile);
        System.out.println("结束");
        System.out.println("setLengthTest()耗时:" + (System.currentTimeMillis() - startTime) + "ms");
    }

    public static void createSQLFile(String tableName, String readFile, String writeFile) {
        try (
                InputStreamReader isr = new InputStreamReader(new FileInputStream(readFile));
                BufferedReader br = new BufferedReader(isr);

                OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(writeFile,true));
                BufferedWriter bw = new BufferedWriter(osw);
        ) {
            String line = null;
            String template = null;
            int stack = 0;
            ArrayList<String> list = new ArrayList<>();
            LinkedHashMap<String, String> map = new LinkedHashMap<>();
            boolean isCreate = false;
            StringBuilder sql = new StringBuilder();
            while ((line = br.readLine()) != null) {
                line = line.trim();
                switch (line) {
                    case "{":
                        stack++;
                        break;
                    case "}":
                    case "},":
                    case "\"children\": [":
                        if (!isCreate) {
                            StringBuilder create = new StringBuilder("DROP TABLE IF EXISTS " + tableName + ";\n\n");
                            create.append("CREATE TABLE " + tableName + "(\n");
                            final Iterator<String> it = map.keySet().iterator();
                            while (it.hasNext()) {
                                String key = it.next();
                                String va = map.get(key);
                                if (va.contains("\"")) {
                                    create.append("\t" + key).append(" VARCHAR(512) NULL,\n");
                                } else if (va.equals("true") || va.equals("false")) {
                                    create.append("\t" + key).append(" BOOLEAN NULL,\n");
                                } else { // 数字
                                    create.append("\t" + key).append(" BIGINT(15) NULL,\n");
                                }
                            }
                            create.delete(create.length() - 2, create.length()); // 去掉while循环添加的，和\n
                            create.append("\n)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='" + tableName + "';\n\n");
                            bw.write(create.toString());
                            isCreate = true;

                        }
                        if (stack == 1) {
                            template = "INSERT INTO " + tableName + "(";
                            final Iterator<String> it = map.keySet().iterator();
                            while (it.hasNext()) {
                                String key = it.next();
                                template += key + ",";
                            }
                            template = template.substring(0, template.length() - 1) + ")VALUES("; // 去掉最后的,
                            sql.append(template);
                            for (String s : list) {
                                sql.append(s).append(",");
                            }
                            sql.delete(sql.length() - 1, sql.length());
                            sql.append(");\n");
                            bw.write(sql.toString());
                            sql.setLength(0);   // 使用setLength方法清空
                            map.clear();
                            stack = 0;
                            list.clear();
                        }
                        break;
                    default:
                        if (stack == 1) {
                            String value = line.substring(line.indexOf(":") + 1).trim();
//                            String[] split = value.split(",");
//                            list.add(split[0]);
                            if (value.contains(",")) {
                                value = value.substring(0, value.length() - 1);// 去掉最后的“,”
                            }
                            list.add(value);

                            // 防止有的json字段缺失，所以每次循环都要记录map
                            String key = line.substring(0, line.indexOf(":")).trim();
                            key = key.substring(1, key.length() - 1);
                            map.put(key, value);

                        }

                }
            }
            bw.close();
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void goodsDetail(String tableName, String readFile, String writeFile) {
        try (
                InputStreamReader isr = new InputStreamReader(new FileInputStream(readFile));
                BufferedReader br = new BufferedReader(isr);

                OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(writeFile,true));
                BufferedWriter bw = new BufferedWriter(osw);
        ) {
            String line = null;
            String template = null;
            int stack = 0;
            ArrayList<String> list = new ArrayList<>();
            LinkedHashMap<String, String> map = new LinkedHashMap<>();
            boolean isCreate = true;
            StringBuilder sql = new StringBuilder();
            while ((line = br.readLine()) != null) {
                line = line.trim();
                switch (line) {
                    case "{":
                    case "\"attrs\":[":
                    case "\"pics\":[":
                        stack++;
                        break;
                    case "\"attrs\":[],":
                    case "\"pics\":[],":
                        break;
                    case "}":
                    case "},":
                    case "],":
                    case "\"children\":[":
                        if (stack != 1) {
                            stack--;
                            break;
                        }
                        // 不要建表语句，因为有个字段特别长，需要用到TEXT类型
//                        if (!isCreate) {
//                            StringBuilder create = new StringBuilder("DROP TABLE IF EXISTS " + tableName + ";\n\n");
//                            create.append("CREATE TABLE " + tableName + "(\n");
//                            final Iterator<String> it = map.keySet().iterator();
//                            while (it.hasNext()) {
//                                String key = it.next();
//                                String va = map.get(key);
//                                if (va.contains("\"")) {
//                                    create.append("\t" + key).append(" VARCHAR(512) NULL,\n");
//                                } else if (va.equals("true") || va.equals("false")) {
//                                    create.append("\t" + key).append(" BOOLEAN NULL,\n");
//                                } else { // 数字
//                                    create.append("\t" + key).append(" BIGINT(15) NULL,\n");
//                                }
//                            }
//                            create.delete(create.length() - 2, create.length()); // 去掉while循环添加的，和\n
//                            create.append("\n)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='" + tableName + "';\n\n");
//                            bw.write(create.toString());
//                            isCreate = true;
//
//                        }

                        if (stack == 1) {
                            template = "INSERT INTO " + tableName + "(";
                            final Iterator<String> it = map.keySet().iterator();
                            while (it.hasNext()) {
                                String key = it.next();
                                template += key + ",";
                            }
                            template = template.substring(0, template.length() - 1) + ")VALUES("; // 去掉最后的,
                            sql.append(template);
                            for (String s : list) {
                                sql.append(s).append(",");
                            }
                            sql.delete(sql.length() - 1, sql.length());
                            sql.append(");\n");
                            bw.write(sql.toString());
                            sql.setLength(0);   // 使用setLength方法清空
                            map.clear();
                            stack = 0;
                            list.clear();
                        }
                        break;
                    default:
                        if (stack == 1) {
                            String value = line.substring(line.indexOf(":") + 1).trim();
                            if (value.contains(",")) {
                                value = value.substring(0, value.length() - 1);// 去掉最后的“,”
                            }
                            list.add(value);

                            // 防止有的json字段缺失，所以每次循环都要记录map
                            if (line.contains(":")) {
                                String key = line.substring(0, line.indexOf(":")).trim();
                                key = key.substring(1, key.length() - 1);
                                map.put(key, value);
                            }


                        }

                }
            }
            bw.close();
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void getCategories(String tableName, String readFile, String writeFile) {
        try (
                InputStreamReader isr = new InputStreamReader(new FileInputStream(readFile));
                BufferedReader br = new BufferedReader(isr);

                OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(writeFile));
                BufferedWriter bw = new BufferedWriter(osw);
        ) {
            String line = null;
            String template = null;
            int stack = 0;
            ArrayList<String> list = new ArrayList<>();
            LinkedHashMap<String, String> map = new LinkedHashMap<>();
            boolean isCreate = false;
            StringBuilder sql = new StringBuilder();
            while ((line = br.readLine()) != null) {
                line = line.trim();
                switch (line) {
                    case "{":
                        stack++;
                        break;
                    case "},":
                    case "}":
                    case "\"children\": [":
                        if (!isCreate) {
                            StringBuilder create = new StringBuilder("DROP TABLE IF EXISTS " + tableName + ";\n\n");
                            create.append("CREATE TABLE " + tableName + "(\n");
                            final Iterator<String> it = map.keySet().iterator();
                            while (it.hasNext()) {
                                String key = it.next();
                                String va = map.get(key);
                                if (va.contains("\"")) {
                                    create.append("\t" + key).append(" VARCHAR(512) NULL,\n");
                                } else if (va.equals("true") || va.equals("false")) {
                                    create.append("\t" + key).append(" BOOLEAN NULL,\n");
                                } else { // 数字
                                    create.append("\t" + key).append(" BIGINT(15) NULL,\n");
                                }
                            }
                            create.delete(create.length() - 2, create.length()); // 去掉while循环添加的，和\n
                            create.append("\n)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='" + tableName + "';\n\n");
                            bw.write(create.toString());
                            isCreate = true;

                        }
                        if (stack == 1) {
                            template = "INSERT INTO " + tableName + "(";
                            final Iterator<String> it = map.keySet().iterator();
                            while (it.hasNext()) {
                                String key = it.next();
                                template += key + ",";
                            }
                            template = template.substring(0, template.length() - 1) + ")VALUES("; // 去掉最后的,
                            sql.append(template);
                            for (String s : list) {
                                sql.append(s).append(",");
                            }
                            sql.delete(sql.length() - 1, sql.length());
                            sql.append(");\n");
                            bw.write(sql.toString());
                            sql.setLength(0);   // 使用setLength方法清空
                            map.clear();
                            stack = 0;
                            list.clear();
                        }
                        break;
                    default:
                        if (stack == 1) {
                            String value = line.substring(line.indexOf(":") + 1).trim();
//                            String[] split = value.split(",");
//                            list.add(split[0]);
                            if (value.contains(",")) {
                                value = value.substring(0, value.length() - 1);// 去掉最后的“,”
                            }
                            list.add(value);

                            // 防止有的json字段缺失，所以每次循环都要记录map
                            String key = line.substring(0, line.indexOf(":")).trim();
                            key = key.substring(1, key.length() - 1);
                            map.put(key, value);
                        }
                }
            }
            bw.close();
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
