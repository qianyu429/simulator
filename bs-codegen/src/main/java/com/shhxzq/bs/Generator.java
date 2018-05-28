package com.shhxzq.bs;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.utility.StringUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @author kangyonggan
 * @since 16/5/9
 */
public class Generator {

    private Configuration cfg;
    private static final String PATH_SETVICE = "bs-core/src/main/java/com/shhxzq/bs/";
    private static final String PATH_CONTROLLER = "bs-web/src/main/java/com/shhxzq/bs/";
    private static Map<String, String> modules = new HashMap();

    static {
        modules.put("0", "Web");
        modules.put("1", "Dashboard");
        modules.put("2", "Admin");
    }

    /**
     * 模板文件默认在 resources/template
     *
     * @throws Exception
     */
    public void init() throws Exception {
        cfg = new Configuration();
        cfg.setClassForTemplateLoading(this.getClass(), "/template");
    }

    /**
     * 模块名称，首字母小写
     *
     * @param domain 对象名称
     * @param type   ajax或者普通模式
     * @param module 模块名称
     * @throws Exception
     */
    public void process(String domain, String type, String module) throws Exception {

        Map<String, Object> root = new HashMap();

        root.put("package", "com.shhxzq.bs");
        root.put("lower_domain", domain);
        root.put("type", type);
        domain = StringUtil.capitalize(domain);
        root.put("domain", domain);
        root.put("module", modules.get(module));

        log("domain is %s", domain);

        StringBuffer fileName = new StringBuffer();
        fileName.append(PATH_SETVICE).append("service").append("/").append(domain).append("Service.java");
        Template template = cfg.getTemplate("Service.java.ftl");
        buildTemplate(root, fileName.toString(), template);
        log("service fileName is %s", fileName.toString());

        fileName = new StringBuffer();
        fileName.append(PATH_SETVICE).append("service").append("/").append("impl").append("/").append(domain).append("ServiceImpl.java");
        template = cfg.getTemplate("ServiceImpl.java.ftl");
        buildTemplate(root, fileName.toString(), template);

        log("service impl fileName is %s", fileName.toString());

        fileName = new StringBuffer();
        fileName.append(PATH_CONTROLLER).append("controller").append("/").append(modules.get(module).toLowerCase()).append("/");
        if (!modules.get(module).equals("Web")) {
            fileName.append(modules.get(module));
        }
        fileName.append(domain).append("Controller.java");
        template = cfg.getTemplate("Controller.java.ftl");
        buildTemplate(root, fileName.toString(), template);

        log("controller fileName is %s", fileName.toString());

        log("generator code success!!!");

    }

    private void buildTemplate(Map root, String fileName, Template template) throws Exception {
        Writer out = new OutputStreamWriter(new FileOutputStream(new File(fileName).getAbsolutePath()), "UTF-8");
        template.process(root, out);
    }

    private static void log(String msg, String... val) {
        System.out.println("================= " + String.format(msg, val));
    }

    public static void main(String[] args) throws Exception {

        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入小写字母开头的 domain class name (如:user): ");
        String domain = scanner.next();

        scanner = new Scanner(System.in);
        System.out.println("请输入增改查模式是 ajax(0) 或者 common(1): ");
        String type = scanner.next();

        scanner = new Scanner(System.in);
        System.out.println("请输入模块名是 web(0) 或者 dashboard(1) 或者 admin(2): ");
        String module = scanner.next();

        Generator generator = new Generator();
        generator.init();
        generator.process(domain, type, module);

        log("build template success.");
    }
}
