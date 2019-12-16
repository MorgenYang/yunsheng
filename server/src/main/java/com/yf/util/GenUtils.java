package com.yf.util;

import com.yf.model.custom.ColumnEntity;
import com.yf.model.custom.GenVo;
import com.yf.model.custom.TableEntity;
import com.yf.model.custom.TsysTables;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 代码生成器   工具类
 */
public class GenUtils {
	
	private static String targetPath = System.getProperty("user.dir");

    public static List<String> getTemplates(){
        List<String> templates = new ArrayList<String>();
        templates.add("generator/template/model/Entity.java.vm");
        templates.add("generator/template/model/EntityExample.java.vm");
        templates.add("generator/template/mapperxml/EntityMapper.xml.vm");
        templates.add("generator/template/service/EntityService.java.vm");
        templates.add("generator/template/mapper/EntityMapper.java.vm");
        templates.add("generator/template/controller/EntityController.java.vm");
        templates.add("generator/template/sql/menu.sql.vm");
        templates.add("generator/template/html/list.html.vm");
        templates.add("generator/template/html/add.html.vm");
        templates.add("generator/template/html/edit.html.vm");
        templates.add("generator/template/说明.txt.vm");
        return templates;
    }

    /**
     * 生成代码
     */
    public static void generatorCode(TsysTables table, List<Map<String, String>> columns, ZipOutputStream zip, GenVo genVo) {
        //配置信息
        Configuration config = getConfig();
        boolean hasBigDecimal = false;
        //判断主键
        boolean pklag=false;
        //表信息
        TableEntity tableEntity = new TableEntity();
        tableEntity.setTableName(table.getTableName());
        tableEntity.setComments(table.getTableComment());
        //表名转换成Java类名
        String className = tableToJava(tableEntity.getTableName(), config.getString("tablePrefix" ));
        tableEntity.setClassName(className);
        tableEntity.setClassname(StringUtils.uncapitalize(className));

        //列信息
        List<ColumnEntity> columsList = new ArrayList<>();
        for(Map<String, String> column : columns){
            ColumnEntity columnEntity = new ColumnEntity();
            columnEntity.setColumnName(column.get("columnName" ));
            columnEntity.setDataType(column.get("dataType" ));
            columnEntity.setComments(column.get("columnComment" ));
            columnEntity.setExtra(column.get("extra" ));

            //列名转换成Java属性名
            String attrName = columnToJava(columnEntity.getColumnName());
            columnEntity.setAttrName(attrName);
            columnEntity.setAttrname(StringUtils.uncapitalize(attrName));

            //列的数据类型，转换成Java类型
            String attrType = config.getString(columnEntity.getDataType(), "unknowType" );
            columnEntity.setAttrType(attrType);
            if (!hasBigDecimal && attrType.equals("BigDecimal" )) {
                hasBigDecimal = true;
            }
            //是否主键
            if ("PRI".equalsIgnoreCase(column.get("columnKey" )) && tableEntity.getPk() == null) {
                tableEntity.setPk(columnEntity);
                pklag=true;
            }

            columsList.add(columnEntity);
        }
        tableEntity.setColumns(columsList);

        //没主键，则第一个字段为主键
        if (tableEntity.getPk() == null) {
            tableEntity.setPk(tableEntity.getColumns().get(0));
        }
        
        String columnsStr="";
        List<String> columnsList=new ArrayList<String>();
        List<ColumnEntity> columnEntities=tableEntity.getColumns();
        for (ColumnEntity columnEntity : columnEntities) {
        	columnsList.add(columnEntity.getColumnName());
		}
        columnsStr=String.join(",", columnsList);
        //设置velocity资源加载器
        Properties prop = new Properties();
        prop.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader" );
        Velocity.init(prop);
        String mainPath = config.getString("mainPath" );
        mainPath = StringUtils.isBlank(mainPath) ? "com.fc.test" : mainPath;
        //封装模板数据
        Map<String, Object> map = new HashMap<>();
        map.put("tableName", tableEntity.getTableName());
        map.put("comments", tableEntity.getComments());
        map.put("pklag", pklag);
        map.put("pk", tableEntity.getPk());
        map.put("className", tableEntity.getClassName());
        map.put("classname", tableEntity.getClassname());
        map.put("pathName", tableEntity.getClassname().toLowerCase());
        map.put("columns", tableEntity.getColumns());
        map.put("columnsStr",columnsStr);
        map.put("hasBigDecimal", hasBigDecimal);
        map.put("mainPath", mainPath);
        map.put("package", genVo.getPackageName());
        map.put("moduleName",genVo.getModuleName());
        map.put("controller_permission_key", genVo.getControllerPermissionKey());
        map.put("author", genVo.getAuthor());
        map.put("email", genVo.getEmail());
        map.put("datetime", DateUtils.format(new Date(), DateUtils.DATE_TIME_PATTERN));
        map.put("SnowflakeIdWorker", SnowflakeIdWorker.class);
        map.put("fuzzyQuery", genVo.getFuzzyQuery());
        VelocityContext context = new VelocityContext(map);

        //获取模板列表
        List<String> templates = getTemplates();
        for (String template : templates) {
            try {
                //添加到zip
            	if(com.yf.util.StringUtils.isNotNull(genVo.getIsdown())) {
            		String filepath=getCoverFileName(template,tableEntity.getClassname() ,tableEntity.getClassName(), config.getString("package" ), config.getString("moduleName" ),config.getString("controller_permission_key"));
            		if(com.yf.util.StringUtils.isNotNull(filepath)) {
            			Template tpl = Velocity.getTemplate(template, "UTF-8" );
            			File file = new File(filepath);
                		if (!file.getParentFile().exists()) {
                            file.getParentFile().mkdirs();
                        }
                        if (!file.exists()) {
                            file.createNewFile();
                        }
                        FileOutputStream outStream = new FileOutputStream(file);
                        OutputStreamWriter writer = new OutputStreamWriter(outStream,"UTF-8");
                        BufferedWriter sw = new BufferedWriter(writer);
                        tpl.merge(context, sw);
                        sw.flush();
                        sw.close();
                        outStream.close();
                        System.out.println("成功生成Java文件:"+filepath);
            		}else {//把sql文件单独处理
            			if(template.contains("menu.sql.vm")||template.contains("说明.txt.vm")) {
            				 StringWriter sw = new StringWriter();
                             Template tpl = Velocity.getTemplate(template, "UTF-8" );
                             tpl.merge(context, sw);
                     		 zip.putNextEntry(new ZipEntry(getFileName(template,tableEntity.getClassname() ,tableEntity.getClassName(), config.getString("package" ), config.getString("moduleName" ),config.getString("controller_permission_key"))));
                             IOUtils.write(sw.toString(), zip, "UTF-8" );
                             IOUtils.closeQuietly(sw);
                             zip.closeEntry();
            			}
            		}
            		
            	}else {
            		//渲染模板
                    StringWriter sw = new StringWriter();
                    Template tpl = Velocity.getTemplate(template, "UTF-8" );
                    tpl.merge(context, sw);
            		zip.putNextEntry(new ZipEntry(getFileName(template,tableEntity.getClassname() ,tableEntity.getClassName(), config.getString("package" ), config.getString("moduleName" ),config.getString("controller_permission_key"))));
                    IOUtils.write(sw.toString(), zip, "UTF-8" );
                    IOUtils.closeQuietly(sw);
                    zip.closeEntry();
            	}
            	
            } catch (IOException e) {
                try {
					throw new Exception("渲染模板失败，表名：" + tableEntity.getTableName()+"\n"+e.getMessage());
				} catch (Exception e1) {
					e1.printStackTrace();
				}
            }
        }
    }


    /**
     * 列名转换成Java属性名
     */
    public static String columnToJava(String columnName) {
        return WordUtils.capitalizeFully(columnName, new char[]{'_'}).replace("_", "" );
    }

    /**
     * 表名转换成Java类名
     */
    public static String tableToJava(String tableName, String tablePrefix) {
        if (StringUtils.isNotBlank(tablePrefix)) {
            tableName = tableName.replaceFirst(tablePrefix, "" );
        }
        return columnToJava(tableName);
    }

    /**
     * 获取配置信息
     */
    public static Configuration getConfig() {
        try {
            return new PropertiesConfiguration("generator/generator.properties" );
        } catch (ConfigurationException e) {
            System.out.println("获取配置文件失败");
        	e.printStackTrace();
        	return null;
        }
    }

    /**
     * 获取文件名
     */
    public static String getFileName(String template,String classname,String className, String packageName, String moduleName,String controller) {
        String packagePath = "main" + File.separator + "java" + File.separator;
        if (StringUtils.isNotBlank(packageName)) {
            packagePath += packageName.replace(".", File.separator) + File.separator;
        }
        if (template.contains("Entity.java.vm")) {//model
            return packagePath+moduleName +File.separator+ "auto" + File.separator + className + ".java";
        }
        if(template.contains("EntityExample.java.vm")) {//modelExample
        	return packagePath+moduleName +File.separator+ "auto" + File.separator + className + "Example.java";
        }
        
        if (template.contains("EntityMapper.java.vm")) {//dao or  mapper
            return packagePath + "mapper" + File.separator + "auto" + File.separator + className + "Mapper.java";
        }
        if (template.contains("EntityMapper.xml.vm")) {//dao or  mapper
            return "mybatis" + File.separator+"auto"+ File.separator + className + "Mapper.xml";
        }
        
        if (template.contains("EntityService.java.vm")) {
            return packagePath + "service" + File.separator + className + "Service.java";
        }
        if(template.contains("EntityController.java.vm")) {
        	 return packagePath + "controller" + File.separator + controller + File.separator + className + "Controller.java";
        }
        if(template.contains("menu.sql.vm")) {
       	 	 return "menu.sql";
        }
        if(template.contains("list.html.vm")) {
        	 return "templates"+File.separator + controller+File.separator + classname+File.separator +"list.html";
        }
        if(template.contains("add.html.vm")) {
       	 	 return "templates"+File.separator + controller+File.separator + classname+File.separator +"add.html";
        }
        if(template.contains("edit.html.vm")) {
       	 	return  "templates"+File.separator + controller+File.separator + classname+File.separator +"edit.html";
        }
        if(template.contains("说明.txt.vm")) {
       	 	return  "说明.txt";
        }
        return null;
    }
    
    /**
     * 获取覆盖路径
     */
    public static String getCoverFileName(String template,String classname,String className, String packageName, String moduleName,String controller) {
        String packagePath =targetPath+File.separator+"src"+File.separator + "main" + File.separator + "java" + File.separator;
        String resourcesPath=targetPath+File.separator+"src"+File.separator + "main" + File.separator+"resources"+ File.separator;;
        if (StringUtils.isNotBlank(packageName)) {
            packagePath += packageName.replace(".", File.separator) + File.separator;
        }
        if (template.contains("Entity.java.vm")) {//model
            return packagePath+moduleName +File.separator+ "auto" + File.separator + className + ".java";
        }
        if(template.contains("EntityExample.java.vm")) {//modelExample
        	return packagePath+moduleName +File.separator+ "auto" + File.separator + className + "Example.java";
        }
        
        if (template.contains("EntityMapper.java.vm")) {//dao or  mapper
            return packagePath + "mapper" + File.separator + "auto" + File.separator + className + "Mapper.java";
        }
        if (template.contains("EntityMapper.xml.vm")) {//dao or  mapper
            return resourcesPath+"mybatis" + File.separator+"auto"+ File.separator + className + "Mapper.xml";
        }
        
        if (template.contains("EntityService.java.vm")) {
            return packagePath + "service" + File.separator + className + "Service.java";
        }
        if(template.contains("EntityController.java.vm")) {
        	 return packagePath + "controller" + File.separator + controller + File.separator + className + "Controller.java";
        }
        if(template.contains("list.html.vm")) {
        	 return resourcesPath+"templates"+File.separator + controller+File.separator + classname+File.separator +"list.html";
        }
        if(template.contains("add.html.vm")) {
       	 	 return resourcesPath+"templates"+File.separator + controller+File.separator + classname+File.separator +"add.html";
        }
        if(template.contains("edit.html.vm")) {
       	 	return  resourcesPath+"templates"+File.separator + controller+File.separator + classname+File.separator +"edit.html";
        }
        return null;
    }
}