package util;

import org.aspectj.util.FileUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

public class ControllerApi {

    public static void makeOneClassApi(String clazzName) throws IOException {
        String urlHeader = analyzeTypeAnnotation(clazzName);
        List<API> apis = analyzeMethod(clazzName);
        apis.forEach(api -> api.setUrlHeader(urlHeader));
        formFile(apis, clazzName);
    }

    public static void formFile(List<API> apis, String clazzName) throws IOException {
        String apiString = formApiString(apis, clazzName);
        File file = new File("C:\\Users\\May\\Desktop", "API");
        if (!file.exists()) {
            file.createNewFile();
        }
        FileUtil.writeAsString(file, apiString);
    }

    public static String formApiString(List<API> apis, String clazzName) {
        StringBuilder apiBuilder = new StringBuilder("#### " + clazzName);
        apis.forEach(api -> {
            String url = api.getUrlHeader() + "/" + api.getUrlAfter();
            String method = api.getMethods();
            Map parameterMap = api.getParameterMap();
            apiBuilder.append("\n").append(method)
                    .append("  ").append(url)
                    .append("\n").append("请求参数列表:").append("\n")
                    .append("|").append("参数").append("|")
                    .append("参数类型").append("|")
                    .append("描述").append("|")
                    .append("\n").append("|")
                    .append("----").append("|")
                    .append("----").append("|")
                    .append("----").append("|")
                    .append("\n");
            parameterMap.forEach((parameterName, type) -> {
/*                String typeString = (String)type;
                String[] typeArray = typeString.split("\\.");
                for (String s : typeArray) {
                    System.out.println(s);
                }
                String subType = typeArray[typeString.length()-1];*/
                apiBuilder.append("|").append(parameterName)
                        .append("|").append(type)
                        .append("|").append(" ")
                        .append("|").append("\n");
            });
            apiBuilder.append("\n");
        });
        return apiBuilder.toString();
    }

    public static String analyzeTypeAnnotation(String clazzName) {
        Class<?> clazz = makeInstanseByClazzName(clazzName);
        RequestMapping requestMappingOnType = clazz.getAnnotation(RequestMapping.class);
        String urlHeader = requestMappingOnType.value()[0];
        return urlHeader;
    }

    public static List<API> analyzeMethod(String clazzName) {
        List<API> apis = new ArrayList<>();
        Class<?> clazz = Objects.requireNonNull(makeInstanseByClazzName(clazzName));
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            Optional<API> apiOptionl = analyzeMethodAnnotation(method);
            if (apiOptionl.isPresent()) {
                API api = apiOptionl.get();
                Map parameterMap = getMethodParameterInfo(method);
                api.setParameterMap(parameterMap);
                apis.add(api);
            }
        }
        return apis;
    }

    public static Optional<API> analyzeMethodAnnotation(Method method) {
        API api = null;
        RequestMapping requestMappingOnMethod = method.getAnnotation(RequestMapping.class);
        Optional<RequestMapping> requestMapping = Optional.ofNullable(requestMappingOnMethod);
        if (!requestMapping.isPresent()) {
            return Optional.ofNullable(api);
        }
        api = new API();
        String urlAfter = requestMappingOnMethod.value()[0];
        api.setUrlAfter(urlAfter);
        RequestMethod[] submitMethods = requestMappingOnMethod.method();
        RequestMethod submitMethod = submitMethods[0];
        api.setMethods(submitMethod.toString());
        return Optional.of(api);
    }

    public static Map getMethodParameterInfo(Method method) {
        Map<String, String> parameterMap = new HashMap<>();
        Parameter[] parameters = method.getParameters();
        for (Parameter parameter : parameters) {
            String type = parameter.getType().toString();
            String parameterName = parameter.getName();
            parameterMap.put(parameterName, type);
        }
        return parameterMap;
    }


    public static Class<?> makeInstanseByClazzName(String clazzString) {
        Class<?> clazz = null;
        try {
            clazz = Class.forName(clazzString);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return clazz;
    }

    public static void main(String[] args) throws IOException {
        List<String> clazzs = new ArrayList<>();
        clazzs.add("com.minibox.controller.coupon.CouponController");
        ControllerApi.makeOneClassApi(clazzs.get(0));
    }
}
