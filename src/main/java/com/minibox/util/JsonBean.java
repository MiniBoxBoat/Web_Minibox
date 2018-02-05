package com.minibox.util;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class JsonBean implements Serializable {

    private Info info;
    private List<Module> item;

    @Data
    public class Info {
        private String name;
    }

    @Data
    public class Module {
        private String name;
        private String description;
        private List<Method> item;

        @Data
        public class Method {
            private String name;
            private Request request;
            private String description;

            @Data
            public class Request {
                private String method;
                private Body body;
                private Url url;
                private String description;

                @Data
                public class Body {
                    private List<RequestParameter> urlencoded;

                    @Data
                    public class RequestParameter {
                        private String key;
                        private String value;
                        private String description;
                    }
                }

                @Data
                public class Url {
                    private String raw;
                }
            }
        }
    }
}
