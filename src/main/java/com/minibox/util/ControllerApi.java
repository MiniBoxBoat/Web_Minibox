package com.minibox.util;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Data
public class ControllerApi {

    private JsonBean jsonBean;
    private String header;
    private List<JsonBean.Module> modules = new ArrayList<>();
    private List<String> moduleNames = new ArrayList<>();
    private String markdownString = "";

    public void formFile(String toPath) throws IOException {
        File file = new File(toPath);
        FileUtils.writeStringToFile(file, markdownString);
    }

    private void getJsonBean(Path path) throws IOException {
        List<String> strs = Files.readAllLines(path);
        StringBuilder builder = new StringBuilder();
        strs.forEach(str -> builder.append(str));
        String json = builder.toString();
        jsonBean = JSON.parseObject(json, JsonBean.class);
    }

    private void getModuleParameters() {
        header = jsonBean.getInfo().getName();
        modules = jsonBean.getItem();
        modules.forEach(module -> moduleNames.add(module.getName()));
    }

    public String getApiString(Path path) throws IOException {
        getJsonBean(path);
        getModuleParameters();
        List<JsonBean.Module> modules = jsonBean.getItem();
        JsonBean.Module[] moduleArray = modules.toArray(new JsonBean.Module[modules.size()]);
        for (JsonBean.Module module : moduleArray) {
            markdownString += getOneModuleApi(module);
        }
        return markdownString;
    }

    private String getOneModuleApi(JsonBean.Module module) {
        String str = "\n#### " + module.getDescription() + "\n";
        List<JsonBean.Module.Method> methods = module.getItem();
        JsonBean.Module.Method[] methodArray = methods.toArray(new JsonBean.Module.Method[methods.size()]);
        for (JsonBean.Module.Method method : methodArray) {
            str += getOneMethodApi(method) + "\n";
        }
        return str;
    }

    private String getOneMethodApi(JsonBean.Module.Method method) {
        String str = "##### " + method.getRequest().getDescription() + "\n";
        System.out.println(method.getName());
        JsonBean.Module.Method.Request request = method.getRequest();
        str += request.getMethod() + "  ";
        str += request.getUrl().getRaw() + "\n";
        str += "请求参数： \n";
        JsonBean.Module.Method.Request.Body body = request.getBody();
        List<JsonBean.Module.Method.Request.Body.RequestParameter> requestParameters;
        requestParameters = body.getUrlencoded();
        if (requestParameters != null) {
            str += "|请求参数|参数类型|参数描述|\n" + "|---|---|---|\n";


//        System.out.println(method.getName());
            JsonBean.Module.Method.Request.Body.RequestParameter[] requestParameterArray =
                    requestParameters.toArray(new JsonBean.Module.Method.Request.Body.RequestParameter[requestParameters.size()]);
            for (JsonBean.Module.Method.Request.Body.RequestParameter requestParameter : requestParameterArray) {
                str += "|" + requestParameter.getKey() + "|" + requestParameter.getKey().getClass() + "|"
                        + requestParameter.getDescription() + "|\n";
            }
        }
        return str;
    }

    public static void main(String[] args) throws IOException {
        Path fromPath = Paths.get("C:\\Users\\May\\Desktop", "minibox.json");
        ControllerApi controllerApi = new ControllerApi();
        String apiString = controllerApi.getApiString(fromPath);
        String replaceString = apiString.replace("class java.lang.String", "String");
        String replaceNull = apiString.replace("null", "");
        String replace = apiString.replace("localhost:8081", "jay86.box.com:8080");
        File file = new File("C:\\Users\\May\\Desktop", "API");
        FileUtils.writeStringToFile(file, replace);
    }
}